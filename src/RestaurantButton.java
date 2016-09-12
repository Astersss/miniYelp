
/**
 * This class shows the brief info for each restaurant
 * click each restaurant user can see detail page for that restaurant
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class RestaurantButton extends JButton implements ActionListener {
	/**
	 * Instance Variables
	 */
	private static final int IMAGE_WIDTH = 80;

	private Restaurant restaurant;
	private ListPage listpage;

	private JLabel imageIcon;

	/**
	 * Constructor
	 * 
	 * @param restaurant
	 * @param index
	 * @param listpage
	 */
	public RestaurantButton(Restaurant restaurant, int index, ListPage listpage) {
		this.restaurant = restaurant;
		this.listpage = listpage;

		this.setBounds(30, 100 + index * 100, 500, 100);

		imageIcon = new JLabel();
		imageIcon.setBounds(0, 0, IMAGE_WIDTH, IMAGE_WIDTH);
		add(imageIcon);

		this.addActionListener(this);
	}

	/**
	 * Setter for restaurant
	 * 
	 * @param restaurant
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.imageIcon.setIcon(null);
		this.restaurant = restaurant;

		if (restaurant != null)
			new DownloadImageThread().start();
	}

	/**
	 * show the name, category, address info on the button
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (restaurant != null) {
			setEnabled(true);
			g.drawString(restaurant.getName(), 100, 30);
			g.drawString(restaurant.getCategory(), 100, 50);
			g.drawString(restaurant.getAddress(), 100, 70);
		} else {
			setEnabled(false);
		}
	}

	/**
	 * download images for each restaurant
	 * 
	 * @author user
	 *
	 */
	class DownloadImageThread extends Thread {
		@Override
		public void run() {
			try {
				System.out.println("Downloading " + restaurant.getImageURL() + "...");
				ImageIcon icon = new ImageIcon(new URL(restaurant.getImageURL()));
				icon.setImage(icon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_WIDTH, Image.SCALE_DEFAULT));
				imageIcon.setIcon(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * show detail page of the restaurant only when the restaurant exists
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (restaurant != null)
			new Detail(restaurant, listpage);
	}
}
