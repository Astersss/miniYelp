
/**
 * This class is for the list page, which shows the list of results give back by yelp api
 * it includes the brief information and featured image of restaurants
 * and also we reserve a dynamically-updated panel to show ads!
 */
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import org.scribe.model.OAuthRequest;
//import org.scribe.model.OAuthRequest;

//zimport com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParameterList;

public class ListPage extends JFrame implements ActionListener {
	/**
	 * Instance Variables
	 */
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int SIZE = 6;

	private RestaurantButton[] restaurantButtons;

	private JButton homepageButton;
	private JButton previousButton;
	private JButton nextButton;

	private JLabel adLabel;
	private Home home;

	private List<Restaurant> restaurants;
	private int offset;
	private JLabel background;

	/**
	 * Constructor
	 * 
	 * @param home
	 */
	public ListPage(Home home) {
		this.home = home;
		this.offset = 0;
		init();
	}

	/**
	 * init the list page
	 */
	private void init() {
		setLayout(null);

		background = new JLabel();
		ImageIcon background_image = new ImageIcon("food-large.jpg");
		background.setBounds(0, 0, WIDTH, HEIGHT);
		background.setLayout(null);
		background.setIcon(background_image);
		getContentPane().add(background);

		restaurantButtons = new RestaurantButton[SIZE];
		for (int i = 0; i < SIZE; i++) {
			restaurantButtons[i] = new RestaurantButton(null, i, this);
			restaurantButtons[i].addActionListener(this);
			add(restaurantButtons[i]);
			background.add(restaurantButtons[i]);
		}

		homepageButton = new JButton("Back to Homepage");
		homepageButton.addActionListener(this);
		homepageButton.setBounds(500, 20, 150, 20);

		add(homepageButton);

		adLabel = new JLabel();
		adLabel.setBounds(570, 100, 185, 600);
		add(adLabel);

		background.add(homepageButton);

		adLabel = new JLabel();
		adLabel.setBounds(570, 100, 185, 600);
		background.add(adLabel);

		previousButton = new JButton("<<");
		previousButton.addActionListener(this);
		previousButton.setBounds(30, 720, 225, 20);
		add(previousButton);
		background.add(previousButton);

		nextButton = new JButton(">>");
		nextButton.addActionListener(this);
		nextButton.setBounds(305, 720, 225, 20);
		add(nextButton);
		background.add(nextButton);

		setTitle("List");
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add an empty restaurants list
		this.restaurants = new ArrayList<Restaurant>();

		// changes the ad image per 10 seconds
		new AdThread().start();
	}

	/**
	 * setter for restaurant
	 * 
	 * @param restaurants
	 */
	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
		this.offset = 0;
	}

	/**
	 * This method is used to switch result pages shows at most 18 results, 6 in
	 * one page use previous button and next button to switch pages
	 */
	public void refresh() {
		if (offset == 0)
			this.previousButton.setEnabled(false);
		else
			this.previousButton.setEnabled(true);
		if (offset + SIZE < restaurants.size())
			this.nextButton.setEnabled(true);
		else
			this.nextButton.setEnabled(false);
		int i = 0;

		for (; i < SIZE && offset + i < restaurants.size(); i++) {
			restaurantButtons[i].setRestaurant(restaurants.get(i + offset));
			restaurantButtons[i].repaint();
		}
		while (i < 6) {
			restaurantButtons[i].setRestaurant(null);
			restaurantButtons[i].repaint();
			i++;
		}
	}

	/**
	 * Action Listener for homepage button, next button and previous button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof RestaurantButton) {
			for (int i = 0; i < 6; i++) {
				restaurantButtons[i].repaint();
			}
		}

		if (e.getSource() == homepageButton) {
			backToHomepage();
		}

		if (e.getSource() == nextButton) {
			offset += SIZE;
			refresh();
		}

		if (e.getSource() == previousButton) {
			offset -= SIZE;
			refresh();
		}
	}

	/**
	 * show home page
	 */
	public void backToHomepage() {
		home.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}

	/**
	 * show ads images
	 * 
	 * @param index
	 */
	public void setADImage(int index) {
		ImageIcon icon = new ImageIcon("ad" + index + ".jpg");
		icon.setImage(icon.getImage().getScaledInstance(185, 600, Image.SCALE_DEFAULT));
		adLabel.setIcon(icon);
	}

	/**
	 * Switch between ads iamges
	 * 
	 * @author user
	 *
	 */
	class AdThread extends Thread {
		private int index = 0;

		@Override
		public void run() {
			while (true) {
				index++;
				if (index > 5) {
					index = 1;
				}

				setADImage(index);

				try {
					// updates every 5 seconds
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
