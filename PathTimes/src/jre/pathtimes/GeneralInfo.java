package jre.pathtimes;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Renders the general info tab.
 * 
 * @author jimenglert
 *
 */
public class GeneralInfo extends Activity {

	/**
	 * Prepares the general information.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.generalinfo);
		
		WebView generalInfo = (WebView) findViewById(R.id.generalInfoWebView);

		Resources myResources = getResources();
		InputStream is = myResources.openRawResource(R.raw.general_info);
		
		String content;
		try {
			content = IOUtils.toString(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		generalInfo.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "UTF-8", null);
	}
}
