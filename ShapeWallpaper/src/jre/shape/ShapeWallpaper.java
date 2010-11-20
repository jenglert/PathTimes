package jre.shape;

import jre.shape.animations.ChangingStar;
import jre.shape.animations.RedStarAnimation;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class ShapeWallpaper extends WallpaperService {

	Handler handler = new Handler();
	
	@Override
	public Engine onCreateEngine() {
		return new ShapeEngine();
	}

	public class ShapeEngine extends Engine {
	

		private long startTime;
		private boolean visible;
		private float centerX;
		private float centerY;
		private float offset;
		
        private final Runnable drawingThread = new Runnable() {
            public void run() {
                drawFrame();
            }
        };
		
		ShapeEngine() {
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
            handler.removeCallbacks(drawingThread);
        }
        
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible = false;
            handler.removeCallbacks(drawingThread);
        }
		
		@Override
		public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (this.visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawingThread);
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
			float yrot = (0.5f - offset) * 1f;
			float timeAngle = (((float)SystemClock.elapsedRealtime() - startTime) / 2000);
			
            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                		c.save();
                		
                    c.translate(centerX, centerY);
                    c.skew(0, yrot);
                		
                    // draw something
                    new ChangingStar().draw(c, timeAngle);
                    
                    c.restore();
                }
            } finally {
                if (c != null) holder.unlockCanvasAndPost(c);
            }
            
            handler.removeCallbacks(drawingThread);
            if (visible) {
            		handler.postDelayed(drawingThread, 1000 / 25);
            }
		}
	}
}