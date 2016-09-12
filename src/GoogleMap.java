
/**
 * This class helps to show google map
 * on our application
 */
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class GoogleMap {
	/**
	 * Instance Variables
	 */
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;

	/**
	 * In map.html file default zoom value is set to 4.
	 */
	private static int zoomValue = 4;
	Restaurant restaurant;

	/**
	 * Constructor
	 * 
	 * @param restaurant
	 */
	public GoogleMap(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * The method is used for loading google map by calling map.html and then
	 * adding marker on the map by calling browser.excecuteJavaScript() map.html
	 * is actually written in javascript form
	 * 
	 * @throws IOException
	 */
	public void setBrowser() throws IOException {
		final Browser browser = new Browser();
		BrowserView browserView = new BrowserView(browser);
		DecimalFormat df = new DecimalFormat("0.000000");

		String latitude = df.format(restaurant.getLatitude());
		String longitude = df.format(restaurant.getLongitude());
		String location = latitude + "," + longitude;
		System.out.println(location);

		JFrame frame = new JFrame("map.html");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(browserView, BorderLayout.CENTER);
		frame.setSize(900, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		File f = new File("");
		String d = f.getCanonicalPath();
		System.out.println(d);
		browser.loadURL("file://" + d + "/src/map.html");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		browser.executeJavaScript("var myLatlng = new google.maps.LatLng(" + location + ");\n" +

		"var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n" + "    map: map,\n" + "});"
				+ "marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');\n"
				+ "markers.push(marker);\n");
	}

}