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
import android.view.SurfaceHolder;
import android.widget.Toast;

public class FractalWallpaper extends WallpaperService {

	Handler handler = new Handler();
	
	public static final String PREFERENCES_NAME = "FRACTAL_PREFERENCES";
	
	private static int decreasePerLevel = 7;
	private static int initialLength = 70;
	private static final int MINIMUM_LENGTH = 10;
	private static final int TIME_BETWEEN_STEPS = 1000;
	private static int[] colors;
	
	private static final int MAX_STEPS = initialLength / decreasePerLevel + ((initialLength % decreasePerLevel) > 0 ? 1 : 0);
	
	
	private Map<Integer, List<FractalCall>> calls = new HashMap<Integer, List<FractalCall>>();
	private Map<Integer, Double> randomAngles = new HashMap<Integer, Double>();
	
	private static Map<String, int[]> colorRanges = null;
	
	static {
		int[] warm = {
			0xff1A0B05, 0xff794410, 0xffCC3F00, 0xffF87B00, 0xffFFFFFF	
		};
		
		int[] cold = {
			0xff143B56, 0xffA2BBDE, 0xff90BFF6, 0xffFFFFFF, 0xffA8A593
		};
		
		int[] hippy = {
			0xff5F3814, 0xffFF0000, 0xffFFFF00, 0xffFFFF00, 0xffD2ABC9
		};
		
		int[] reggae = {
			0xff1D4618, 0xffFFFF00, 0xffFF0000, 0xffD9BD00, 0xffBB6838
		};
		
		int[] bloodOrange = {
			0xff0F0006, 0xff380000, 0xff7C0000, 0xffD40000, 0xffFF5E00
		};
		
		colorRanges = new HashMap<String, int[]>();
		colorRanges.put("warm", warm);
		colorRanges.put("cold", cold);
		colorRanges.put("hippy", hippy);
		colorRanges.put("reggae", reggae);
		colorRanges.put("bloodOrange", bloodOrange);
		
		colors = warm;
	}
	
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
		private SharedPreferences prefs;
		
        private final Runnable drawFractal = new Runnable() {
            public void run() {
                drawFrame(true);
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
            
            prefs = FractalWallpaper.this.getSharedPreferences(PREFERENCES_NAME, 0);
            prefs.registerOnSharedPreferenceChangeListener(this);
            onSharedPreferenceChanged(prefs, null);
		}
		
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                float xStep, float yStep, int xPixels, int yPixels) {
            drawFrame(false);
        }
	
        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawFractal);
//            Toast.makeText(getBaseContext(), "onDestroy", 100).show();
        }
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawFractal);
//            Toast.makeText(getBaseContext(), "onSurfaceDestroy", 100).show();
        }
		
		@Override
		public void onVisibilityChanged(boolean visible) {
//			Toast.makeText(getBaseContext(), "onVisibilityChanged", 100).show();
            this.visible = visible;
            if (this.visible) {
                drawFrame(true);
            } else {
                handler.removeCallbacks(drawFractal);
            }
		}

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        	Toast.makeText(getBaseContext(), "onSurfaceChanged", 100).show();
            super.onSurfaceChanged(holder, format, width, height);
            // store the center of the surface, so we can draw the cube in the right spot
            drawFrame(false);
            
            startY = (float)height / 2;
            startX = (float)width / 2;
        }

		private void drawFrame(boolean clear) {
			if (startX == null || startY == null) {
				return;
			}
			
			if (calls.get(stepIteration()) == null) {
				List<FractalCall> callList = new ArrayList<FractalCall>();
				List<FractalCall> callSide = drawFractal(startX, startY, 0, initialLength, 0);
				if (callSide != null) {
					callList.addAll(callSide);
				}
				callSide = drawFractal(startX, startY, Math.PI, initialLength, 0);
				if (callSide != null) {
					callList.addAll(callSide);
				}
	            calls.put(stepIteration(), callList);
			}
			
			final SurfaceHolder holder = getSurfaceHolder();
		
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null)  {
                	
                		c.save();
                		
                		// Clear the canvas when we are done drawing
                		if (currentStep() == 0 || clear) {
                			c.drawColor(0xff000000);
                			
                			// Clear out the containers that hold step based information
                			calls.clear();
                			randomAngles.clear();
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
					//Log.e("drawing", "From " + call.x + " to " + call.y + " @ " + call.currentAngle + " with length:" + call.length + " and color " + call.color);
					drawLine(c, call.x, call.y, call.currentAngle, call.length);
				}
			}
		}
		
		private List<FractalCall> drawFractal(double startX, double startY, double currentAngle, float length, int step) {
//			Log.e("fractal", "X:" + startX + " Y:" + startY + " angle:" + currentAngle + " len:" + length + " step:" + step);
			// Stop processing if we are not at the current step or the length that we would draw is too short.
			if (length <= MINIMUM_LENGTH) {
				return null;
			}
			
			double leftAngle = currentAngle + randomAngle();
			double rightAngle = currentAngle - randomAngle();
			
			List<FractalCall> fractals = new ArrayList<FractalCall>();
			
			fractals.add(new FractalCall(startX, startY, leftAngle, length, randomColor(), step));
			fractals.add(new FractalCall(startX, startY,  rightAngle, length, randomColor(), step));
			
			List<FractalCall> fracs = 
				drawFractal(startX + length * Math.sin(leftAngle), startY + length * Math.cos(leftAngle), leftAngle, 
						length - decreasePerLevel, step + 1);
			if (fracs != null) {
				fractals.addAll(fracs);
			}
			fracs = drawFractal(startX + length * Math.sin(rightAngle), startY + length * Math.cos(rightAngle), rightAngle,
					length - decreasePerLevel, step + 1);
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
			return colors[(int) (Math.round(Math.random() * colors.length) % colors.length)];
		}
		
		private double randomAngle() {
			if (randomAngles.get(stepIteration()) == null) {
				randomAngles.put(stepIteration(), Math.PI / ((Math.random() * 8  + 2)));
			}
			
			return randomAngles.get(stepIteration());
		}
		
		private void resetAnimation() {
			startTime = SystemClock.elapsedRealtime();
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
//			Toast.makeText(getBaseContext(), "onPreferencesChanged", 100).show();
			
			initialLength = FractalWallpaperSettings.getInitialLineLength(sharedPreferences, getBaseContext());
			decreasePerLevel = FractalWallpaperSettings.getDecreasePerLevel(sharedPreferences, getBaseContext());
			colors = colorRanges.get(sharedPreferences.getString("fractal_colorPalettes", "warm"));
			
			resetAnimation();
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