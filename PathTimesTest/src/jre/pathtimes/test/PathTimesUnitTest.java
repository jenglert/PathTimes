package jre.pathtimes.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.test.AndroidTestCase;

public class PathTimesUnitTest extends AndroidTestCase {
	
	/**
	 * Special assert method that ensure that two calendar instances are very close.
	 * @param expected the expected calendar instance
	 * @param actual the actual calendar instance.
	 */
	public void assertClose(Calendar expected, Calendar actual) {
		if (actual == null) {
			fail("Actual is null.");
		}
		
		if (expected.get(Calendar.HOUR_OF_DAY) == actual.get(Calendar.HOUR_OF_DAY)
				&& expected.get(Calendar.MINUTE) == actual.get(Calendar.MINUTE)) {
			return;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
		
		fail("Cals not equal. Exp[" + format.format(expected.getTime()) + "] act[" + format.format(actual.getTime()) + "]");
	}

}
