package jre.fractal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class FractalWallpaper extends WallpaperService {

	Handler handler = new Handler();
	
	private static final int DECREASE_PER_LEVEL = 7;
	private static final int INITIAL_LENGTH = 70;
	private static final int MINIMUM_LENGTH = 10;
	private static final int TIME_BETWEEN_STEPS = 1000;
	private static final double ANGLE_DELTA = Math.PI / 8;
	
	private static final int MAX_STEPS = INITIAL_LENGTH / DECREASE_PER_LEVEL + ((INITIAL_LENGTH % DECREASE_PER_LEVEL) > 0 ? 1 : 0);
	
	private static int iterationCount = 0;
	
	private Map<Integer, List<FractalCall>> calls = new HashMap<Integer, List<FractalCall>>();
	private Map<Integer, Double> randomAngles = new HashMap<Integer, Double>();

	private static final int[] COLORS = {
			0xffff0000,
			0xff00ff00,
			0xff0000ff,
			0xffff00ff,
			0xff00ffff,
			0xffffffff
	};
	
	@Override
	public Engine onCreateEngine() {
		return new FractalEngine();
	}

	public class FractalEngine extends Engine
		implements SharedPreferences.OnSharedPreferenceChangeListener {
		private Paint paint = new Paint();
		private boolean visible;
		private Float startY = null;
		private Float startX = null;
		private long startTime;
		
		
        private final Runnable drawFractal = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
		
		FractalEngine() {
            // Create a Paint to draw the lines for our cube
            final Paint paint = this.paint;
            paint.setColor(0xffff0000);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(3);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            startTime = SystemClock.elapsedRealtime();
		}
		
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                float xStep, float yStep, int xPixels, int yPixels) {
            drawFrame();
        }
	
        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawFractal);
        }
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawFractal);
        }
		
		@Override
		public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (this.visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawFractal);
            }
		}

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            // store the center of the surface, so we can draw the cube in the right spot
            drawFrame();
            
            startY = (float)height / 2;
            startX = (float)width / 2;
        }

		private void drawFrame() {
			if (startX == null || startY == null) {
				return;
			}
			
			if (calls.get(stepIteration()) == null) {
	            List<FractalCall> callList = drawFractal(startX, startY, 0, INITIAL_LENGTH, 0);
	            callList.addAll(drawFractal(startX, startY, Math.PI, INITIAL_LENGTH, 0));
	            calls.put(stepIteration(), callList);
			}
			
			final SurfaceHolder holder = getSurfaceHolder();
		
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null)  {
                		iterationCount = 0;
                	
                		c.save();
                		
                		// Clear the canvas when we are done drawing
                		if (currentStep() == 0) {
                			c.drawColor(0xff000000);
                		}

            			drawCalls(c, currentStep());
                		
                		c.restore();
                		
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }
            
            handler.removeCallbacks(drawFractal);
            if (visible) {
            		handler.postDelayed(drawFractal, 100);
            }
		}

		private void drawCalls(Canvas c, int step) {
			if (calls.get(stepIteration()) == null)
				return;
			for (FractalCall call: calls.get(stepIteration())) {
				if (call.step <= step) {
					paint.setColor(call.color);
					drawLine(c, call.x, call.y, call.currentAngle, call.length);
				}
			}
		}
		
		private List<FractalCall> drawFractal(double startX, double startY, double currentAngle, float length, int step) {
			// Stop processing if we are not at the current step or the length that we would draw is too short.
			if (length <= MINIMUM_LENGTH) {
				return null;
			}
			
			Log.e("max_steps", String.valueOf(MAX_STEPS));
			
			// Increment the count of the number of iterations we have experienced.
			iterationCount++;
			
			double leftAngle = currentAngle + randomAngle();
			double rightAngle = currentAngle - randomAngle();
			
//			drawLine(c, startX, startY, leftAngle, length);
//			drawLine(c, startX, startY, rightAngle, length);
			
			List<FractalCall> fractals = new ArrayList<FractalCall>();
			
			fractals.add(new FractalCall(startX, startY, leftAngle, length, randomColor(), step));
			fractals.add(new FractalCall(startX, startY,  rightAngle, length, randomColor(), step));
			
			List<FractalCall> fracs = 
				drawFractal(startX + length * Math.sin(leftAngle), startY + length * Math.cos(leftAngle), leftAngle, 
						length - DECREASE_PER_LEVEL, step + 1);
			if (fracs != null) {
				fractals.addAll(fracs);
			}
			fracs = drawFractal(startX + length * Math.sin(rightAngle), startY + length * Math.cos(rightAngle), rightAngle,
					length - DECREASE_PER_LEVEL, step + 1);
			if (fracs != null) {
				fractals.addAll(fracs);
			}
		
			return fractals;
		}
		
		private void drawLine(Canvas c, double x1, double y1, double angle, float length) {	
			c.drawLine((float) x1, (float) y1,
					(float)(x1 + length * Math.sin(angle)), 
					(float)(y1 + length * Math.cos(angle)), paint);
		}
		
		private long elapsedTime() {
			return SystemClock.elapsedRealtime() - startTime;
		}
		
		private int steps() {
			return Math.round(elapsedTime() / TIME_BETWEEN_STEPS);
		}
		
		private int currentStep() {
			return steps() % MAX_STEPS;
		}
		
		private int stepIteration() {
			return (int) Math.round(Math.floor(steps() / MAX_STEPS));
		}
		
		private int randomColor() { 
			return COLORS[(int) (Math.round(Math.random() * COLORS.length) % COLORS.length)];
		}
		
		private double randomAngle() {
			if (randomAngles.get(stepIteration()) == null) {
				randomAngles.put(stepIteration(), Math.PI / ((Math.random() * 8  + 2)));
			}
			
			return randomAngles.get(stepIteration());
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			
		}
	}
	
	/**
	 * A class that represents a call to draw a fractal that we still need to make.
	 */
	public class FractalCall {
		private double x;
		private double y;
		private double currentAngle;
		private float length;
		private int color;
		private int step;
		
		/**
		 * Constructs a fractal call object.
		 */
		public FractalCall(double x, double y, double currentAngle, float length, int color, int step) {
			this.x = x;
			this.y = y;
			this.currentAngle = currentAngle;
			this.length = length;
			this.step = step;
			this.color = color;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}



		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		public double getCurrentAngle() {
			return currentAngle;
		}
		public void setCurrentAngle(double currentAngle) {
			this.currentAngle = currentAngle;
		}
		public float getLength() {
			return length;
		}
		public void setLength(float length) {
			this.length = length;
		}
		public int getStep() {
			return step;
		}
		public void setStep(int step) {
			this.step = step;
		}
	}
}