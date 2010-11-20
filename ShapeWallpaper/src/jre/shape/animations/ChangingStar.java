package jre.shape.animations;

import jre.shape.Animation;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class ChangingStar implements Animation {

	private static final float scale = 200;
	
	private Paint paint = new Paint();
	
	private int[]  colors = { 0xffff0000, 0xff0000ff, 0xff00ff00};
	
	public ChangingStar() {
        // Create a Paint to draw the lines for our cube
        final Paint paint = this.paint;
        paint.setColor(0xffff0000);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	public void draw(Canvas c, float time) {
            c.drawColor(0xf00f0000);
            
            paint.setColor(colors[(Math.round(time) / 4) % 3]);

            Integer lines = Math.round((time / 2) % 10) + 5;
            
            Log.i("jim", new Float(time).toString() + " - " + lines.toString());
			
			float angleDifference = ((float) Math.PI * 2) / lines;
			
			for (int i = 0; i < lines; i++)  {
				drawLine(c, i * angleDifference, ((i + ((lines % 3) + 1 )) * angleDifference) % 360, time);
			}
			
	}
	
	private void drawLine(Canvas c, float angle1, float angle2, float timeAngle) {

		float x1 = (float) (Math.cos(timeAngle + angle1) * scale);
		float y1 = (float) (Math.sin(timeAngle + angle1) * scale);
		float x2 = (float) (Math.cos(timeAngle + angle2) * scale);
		float y2 = (float) (Math.sin(timeAngle + angle2) * scale);
		
		c.drawLine(x1, y1,x2, y2, paint);
	}
}
