package jre.pentagram;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class PentagramWallpaper extends WallpaperService {

	Handler handler = new Handler();
	
	private static final float scale = 200;
	
	private float[] angles = {0, 1.2566f, 2.51327f, 3.769911f, 5.02654f};
	
	@Override
	public Engine onCreateEngine() {
		return new PentagramEngine();
	}

	public class PentagramEngine extends Engine {
	
		private Paint paint = new Paint();
		private long startTime;
		private boolean visible;
		private float centerX;
		private float centerY;
		private float offset;
		
        private final Runnable drawPentagram = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
		
		PentagramEngine() {
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
            offset = xOffset;
            drawFrame();
        }
	
        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawPentagram);
        }
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawPentagram);
        }
		
		@Override
		public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (this.visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawPentagram);
            }
		}

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            // store the center of the surface, so we can draw the cube in the right spot
            centerX = width/2.0f;
            centerY = height/2.0f;
            drawFrame();
        }

		private void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                    // draw something
                    drawPentagram(c);
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }
            
            handler.removeCallbacks(drawPentagram);
            if (visible) {
            		handler.postDelayed(drawPentagram, 1000 / 25);
            }
		}

		private void drawPentagram(Canvas c) {
            c.save();
            c.drawColor(0xf00f0000);

			float yrot = (0.5f - offset) * 1f;
            
            c.translate(centerX, centerY);
            c.skew(0, yrot);
            
            float timeAngle = (((float)SystemClock.elapsedRealtime() - startTime) / 2000) % (2 * (float)Math.PI);
            
			drawLine(c, angles[0], angles[2], timeAngle);
			drawLine(c, angles[2], angles[4], timeAngle);
			drawLine(c, angles[4], angles[1], timeAngle);
			drawLine(c, angles[1], angles[3], timeAngle);
			drawLine(c, angles[3], angles[0], timeAngle);
			
			c.drawCircle(0, 0, 175, paint);
			
			c.restore();
		}
		
		private void drawLine(Canvas c, float angle1, float angle2, float timeAngle) {

			float x1 = (float) (Math.cos(timeAngle + angle1) * scale);
			float y1 = (float) (Math.sin(timeAngle + angle1) * scale);
			float x2 = (float) (Math.cos(timeAngle + angle2) * scale);
			float y2 = (float) (Math.sin(timeAngle + angle2) * scale);
		
			
			c.drawLine(x1, y1,x2, y2, paint);
		}
	}
}