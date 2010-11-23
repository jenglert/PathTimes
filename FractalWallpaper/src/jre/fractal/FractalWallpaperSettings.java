package jre.fractal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class FractalWallpaperSettings extends PreferenceActivity 
	implements SharedPreferences.OnSharedPreferenceChangeListener {
	
	public static final Integer DEFAULT_INITIAL_LENGTH = 70;
	public static final Integer DEFAULT_LINE_DECREASE = 7;
	
	private static final Integer MAXIMUM_INITIAL_LENGTH_DIVIDED_BY_DECREASE = 6;

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			int initialLineLength = getInitialLineLength(sharedPreferences, null);
			int lineDecrease = getDecreasePerLevel(sharedPreferences, null);
			
			if (initialLineLength / lineDecrease > 6) {
				Toast.makeText(
					getBaseContext(),
					"The initial line length and line decrease amount create a design too deep.  "
							+ "Please either decrease the initial line length or increase the decrease per level.",
					3000);
				
				// Reset the settings to the default set of settings.
				Editor edit = sharedPreferences.edit();
				edit.putInt("fractal_initialLineLength", DEFAULT_INITIAL_LENGTH);
				edit.putInt("fractal_lineLengthDecrease", DEFAULT_LINE_DECREASE);
				edit.commit();
			}
	}
	
	/**
	 * Retrieves the initial line length for the fractal.
	 */
	public static int getInitialLineLength(SharedPreferences sharedPreferences, Context context) {
		int initialLength = FractalWallpaperSettings.DEFAULT_INITIAL_LENGTH;
		try {
			initialLength = Integer.valueOf(sharedPreferences.getString("fractal_initialLineLength", DEFAULT_INITIAL_LENGTH.toString()));
		}
		catch (NumberFormatException nfe) {
			if (context != null) {
				Toast.makeText(context, "Enter a number for initial line length.", 1000).show();
			}
		}
		
		return initialLength;
	}
	
	public static int getDecreasePerLevel(SharedPreferences sharedPreferences, Context context) {
		int decreasePerLevel = FractalWallpaperSettings.DEFAULT_LINE_DECREASE;
		try {
			decreasePerLevel = Integer.valueOf(sharedPreferences.getString(
					"fractal_lineLengthDecrease",
					FractalWallpaperSettings.DEFAULT_LINE_DECREASE.toString()));
		}
		catch (NumberFormatException nfe) {
			if (context != null) {
				Toast.makeText(context, "Enter a numer for line decrease amount.", 1000).show();
			}
		}
		
		return decreasePerLevel;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.fractal_settings);
		getPreferenceManager().setSharedPreferencesName(FractalWallpaper.PREFERENCES_NAME);
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
}
