package jre.shape.animations;

import jre.shape.Animation;
import android.graphics.Canvas;
import android.graphics.Paint;

public class RedStarAnimation implements Animation {

	private static final float scale = 200;
	
	private float[] angles = {0, 1.2566f, 2.51327f, 3.769911f, 5.02654f};
	
	private Paint paint = new Paint();
	
	public RedStarAnimation() {
        // Create a Paint to draw the lines for our cube
        final Paint paint = this.paint;
        paint.setColor(0xffff0000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	public void draw(Canvas c, float time) {
            c.drawColor(0xf00f0000);

			drawLine(c, angles[0], angles[2], time);
			drawLine(c, angles[2], angles[4], time);
			drawLine(c, angles[4], angles[1], time);
			drawLine(c, angles[1], angles[3], time);
			drawLine(c, angles[3], angles[0], time);
			
			
	}
	
	private void drawLine(Canvas c, float angle1, float angle2, float timeAngle) {

		float x1 = (float) (Math.cos(timeAngle + angle1) * scale);
		float y1 = (float) (Math.sin(timeAngle + angle1) * scale);
		float x2 = (float) (Math.cos(timeAngle + angle2) * scale);
		float y2 = (float) (Math.sin(timeAngle + angle2) * scale);
		
		c.drawLine(x1, y1,x2, y2, paint);
	}
}
