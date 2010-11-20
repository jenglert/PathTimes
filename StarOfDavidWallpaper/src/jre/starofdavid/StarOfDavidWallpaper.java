package jre.starofdavid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class StarOfDavidWallpaper extends WallpaperService {

	Handler handler = new Handler();
	
	private static final float scale = 200;
	
	@Override
	public Engine onCreateEngine() {
		return new StarOfDavidEngine();
	}

	public class StarOfDavidEngine extends Engine {
	
		private Paint paint = new Paint();
		private long startTime;
		private boolean visible;
		private float centerX;
		private float centerY;
		private float offset;
		
        private final Runnable drawStarOfDavid = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
		
		StarOfDavidEngine() {
            // Create a Paint to draw the lines for our cube
            final Paint paint = this.paint;
            paint.setColor(0xFFFFB90F);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(10);
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
            handler.removeCallbacks(drawStarOfDavid);
        }
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawStarOfDavid);
        }
		
		@Override
		public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (this.visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawStarOfDavid);
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
                    drawStarOfDavid(c);
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }
            
            handler.removeCallbacks(drawStarOfDavid);
            if (visible) {
            		handler.postDelayed(drawStarOfDavid, 1000 / 25);
            }
		}

		private void drawStarOfDavid(Canvas c) {
            c.save();
            c.drawColor(0xf00f0000);

			float yrot = (0.5f - offset) * 1f;
            
            c.translate(centerX, centerY);
            c.skew(0, yrot);
            
            float timeAngle = (((float)SystemClock.elapsedRealtime() - startTime) / 3000) % (2 * (float)Math.PI);
            
            for (int i = 0; i < 6; i++) {
				drawLine(c, 
						(float)((i * ( Math.PI / 3)) % ( Math.PI * 2)), 
						(float)(((i + 2) * (Math.PI / 3)) % (Math.PI * 2)), 
						timeAngle); 
            }
            
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