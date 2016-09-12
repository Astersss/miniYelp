
/**
 * This class is supposed to display details of the restaurant selected by user
 * User can see information about name, category, phone number, rating and review count 
 * of the restaurant they're interested.
 * Also they can choose to see the location on Google Map with the embedded Google Map API.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Detail extends JFrame implements ActionListener {
	/**
	 * Instance Variables
	 */
	private static final int WIDTH = 600;
	private static final int HEIGHT = 300;
	private static final int IMAGE_WIDTH = 150;

	private ListPage listpage;
	private Restaurant restaurant;

	private JLabel imageLabel;
	private JButton homepageButton;
	private JButton mapButton;

	private JLabel nameLabel;
	private JLabel categoryLabel;
	private JLabel phoneLabel;
	private JLabel addressLabel;
	private JLabel ratingLabel;
	private JLabel reviewLabel;
	private GoogleMap googleMap;

	private JLabel background;

	/**
	 * Constructor
	 * 
	 * @param restaurant
	 * @param listpage
	 */
	public Detail(Restaurant restaurant, ListPage listpage) {

		this.restaurant = restaurant;
		this.listpage = listpage;
		this.googleMap = new GoogleMap(restaurant);
		setLayout(null);
		getContentPane().setLayout(null);

		homepageButton = new JButton("Back to Homepage");
		homepageButton.addActionListener(this);
		homepageButton.setBounds(30, 20, 200, 20);
		add(homepageButton);

		mapButton = new JButton("Find it on Google Map");
		mapButton.addActionListener(this);
		mapButton.setBounds(360, 20, 200, 20);
		add(mapButton);

		imageLabel = new JLabel();
		imageLabel.setBounds(400, 80, IMAGE_WIDTH, IMAGE_WIDTH);
		add(imageLabel);
		new DownloadImageThread().start();

		nameLabel = new JLabel("Name: " + restaurant.getName());
		nameLabel.setBounds(30, 60, 200, 20);
		nameLabel.setForeground(Color.white);
		add(nameLabel);

		categoryLabel = new JLabel("Category:" + restaurant.getCategory());
		categoryLabel.setBounds(30, 90, 200, 20);
		categoryLabel.setForeground(Color.white);
		add(categoryLabel);

		phoneLabel = new JLabel("Phone:" + restaurant.getPhone());
		phoneLabel.setBounds(30, 120, 200, 20);
		phoneLabel.setForeground(Color.white);
		add(phoneLabel);

//		addressLabel = new JLabel("Address:" + restaurant.getAddress());
//		addressLabel.setBounds(30, 150, 500, 20);
//		addressLabel.setForeground(Color.white);
//		add(addressLabel);
		addressLabel = new JLabel("Deal:" + restaurant.getDeal());
		addressLabel.setBounds(30, 150, 500, 20);
		addressLabel.setForeground(Color.white);
		add(addressLabel);
		
		ratingLabel = new JLabel("Rating:" + String.format("%.2f", restaurant.getRating()));
		ratingLabel.setBounds(30, 180, 200, 20);
		ratingLabel.setForeground(Color.white);
		add(ratingLabel);

		reviewLabel = new JLabel("Review Count: " + restaurant.getReviewCount());
		reviewLabel.setBounds(30, 210, 200, 20);
		reviewLabel.setForeground(Color.white);
		add(reviewLabel);

		background = new JLabel();
		ImageIcon background_image = new ImageIcon("italian-food.jpg");
		background.setBounds(0, 0, WIDTH, HEIGHT);
		background.setLayout(null);
		background.setIcon(background_image);
		getContentPane().add(background);

		setTitle("Detail");
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	/**
	 * This action listener make user choose to go back to the homepage or see
	 * the restaurant on Google Map
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == homepageButton) {
			listpage.backToHomepage();
			this.setVisible(false);
			this.dispose();
		}
		if (e.getSource() == mapButton) {
			try {
				this.googleMap.setBrowser();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This class is used to download and set the image to the detail page
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
				imageLabel.setIcon(icon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

}
