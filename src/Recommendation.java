
/**
 * This class is the recommendation page that
 * gives user several features cities and food categories to choose
 * and make recommendations for them
 * It would be very helpful for the users that have no idea what to eat in the city they visit
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Recommendation extends JFrame implements ActionListener {
	/**
	 * Instance Variables
	 */
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	private List<String> cities;
	private List<String> categories;

	private JComboBox<String> cityBox;
	private JComboBox<String> categoryBox;

	private JLabel cityLabel;
	private JLabel categoryLabel;

	private JButton recommendButton;
	private JButton homepageButton;

	private Home home;

	private JLabel background;

	/**
	 * Constructor
	 * 
	 * @param home
	 */
	public Recommendation(Home home) {
		this.home = home;

		setLayout(null);

		background = new JLabel();
		ImageIcon background_image = new ImageIcon("italian-food.jpg");
		// JLabel label = new JLabel();
		background.setBounds(0, 0, WIDTH, HEIGHT);
		// label.setLayout(new FlowLayout(FlowLayout.CENTER));
		background.setLayout(null);
		background.setIcon(background_image);
		getContentPane().add(background);

		cities = new ArrayList<String>();
		cities.add("New York");
		cities.add("Philadelphia");
		cities.add("San Francisco");
		cities.add("Seattle");
		cities.add("Boston");
		cities.add("Los Angeles");

		categories = new ArrayList<String>();
		categories.add("Japanese");
		categories.add("Chinese");
		categories.add("Korean");
		categories.add("Italian");
		categories.add("American");
		categories.add("French");

		categoryLabel = new JLabel("Category:");
		categoryLabel.setBounds(237, 110, 100, 20);
		add(categoryLabel);

		categoryLabel.setBounds(150, 110, 100, 20);
		// categoryLabel.setForeground(Color.DARK_GRAY);
		background.add(categoryLabel);

		categoryBox = new JComboBox<String>();
		for (String category : categories) {
			categoryBox.addItem(category);
		}

		categoryBox.setBounds(300, 110, 250, 20);
		add(categoryBox);

		cityLabel = new JLabel("City:");
		cityLabel.setBounds(267, 140, 50, 20);
		add(cityLabel);

		categoryBox.setBounds(220, 110, 250, 20);
		background.add(categoryBox);

		cityLabel = new JLabel("City:");
		cityLabel.setBounds(150, 140, 50, 20);
		// cityLabel.setForeground(Color.DARK_GRAY);
		background.add(cityLabel);

		cityBox = new JComboBox<String>();
		for (String city : cities) {
			cityBox.addItem(city);
		}

		cityBox.setBounds(300, 140, 250, 20);
		add(cityBox);

		recommendButton = new JButton("Recommend");
		recommendButton.addActionListener(this);
		recommendButton.setBounds(200, 170, 350, 20);
		add(recommendButton);

		homepageButton = new JButton("Back to Homepage");
		homepageButton.addActionListener(this);
		homepageButton.setBounds(200, 200, 350, 20);
		add(homepageButton);

		cityBox.setBounds(220, 140, 250, 20);
		background.add(cityBox);

		recommendButton = new JButton("Recommend");
		recommendButton.addActionListener(this);
		recommendButton.setBounds(140, 170, 350, 20);
		background.add(recommendButton);

		homepageButton = new JButton("Back to Homepage");
		homepageButton.addActionListener(this);
		homepageButton.setBounds(140, 200, 350, 20);
		background.add(homepageButton);

		setTitle("Recommendation");
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Action Listener for recommend button and home page button
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == recommendButton) {
			String location = (String) cityBox.getSelectedItem();
			String category = (String) categoryBox.getSelectedItem();
			this.setVisible(false);
			List<Restaurant> restaurants = YelpAPI.getInstance().searchForBusinessesByLocation(category, location,
					false, YelpAPI.Best_Matched);
			ListPage listpage = new ListPage(home);
			listpage.setRestaurants(restaurants);
			listpage.refresh();
		}

		if (e.getSource() == homepageButton) {
			home.setVisible(true);
			this.setVisible(false);
			this.dispose();
		}
	}
}
