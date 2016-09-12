
/**
 * This class the main page of our application
 */
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Home extends JFrame implements ActionListener {
	/**
	 * Instance Variables
	 */
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	private JTextField businessNameField;
	private JTextField cityField;

	private JLabel businessNameLabel;
	private JLabel cityLabel;
	private JLabel discountLabel;

	private JButton searchButton;
	private JButton recommedButton;

	private JCheckBox discountBox;

	private JLabel background;

	/**
	 * Constructor
	 */
	public Home() {
		getContentPane().setLayout(null);

		background = new JLabel();
		ImageIcon background_image = new ImageIcon("foodImage.jpg");
		// JLabel label = new JLabel();
		background.setBounds(0, 0, WIDTH, HEIGHT);
		// label.setLayout(new FlowLayout(FlowLayout.CENTER));
		background.setLayout(null);
		background.setIcon(background_image);
		getContentPane().add(background);

		businessNameLabel = new JLabel("Business Name:");
		getContentPane().add(businessNameLabel);
		businessNameLabel.setBounds(60, 109, 200, 20);
		businessNameLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		businessNameLabel.setForeground(Color.white);
		background.add(businessNameLabel);

		businessNameField = new JTextField();
		getContentPane().add(businessNameField);
		businessNameField.setText("Starbucks");
		businessNameField.setColumns(20);
		businessNameField.setBounds(235, 109, 250, 20);
		background.add(businessNameField);

		cityLabel = new JLabel("City:");
		getContentPane().add(cityLabel);
		cityLabel.setBounds(170, 141, 100, 20);
		cityLabel.setForeground(Color.white);
		cityLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		background.add(cityLabel);

		cityField = new JTextField();
		getContentPane().add(cityField);
		cityField.setText("San Francisco");
		cityField.setColumns(20);
		cityField.setBounds(235, 141, 250, 20);
		background.add(cityField);

		discountLabel = new JLabel("Discount: ");
		getContentPane().add(discountLabel);
		discountLabel.setBounds(120, 173, 150, 20);
		discountLabel.setForeground(Color.white);
		discountLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		background.add(discountLabel);

		searchButton = new JButton("Search");
		getContentPane().add(searchButton);
		searchButton.addActionListener(this);
		searchButton.setBounds(133, 205, 350, 20);
		searchButton.setFont(new Font("Courier New", Font.BOLD, 16));
		background.add(searchButton);

		recommedButton = new JButton("Recommend");
		getContentPane().add(recommedButton);
		recommedButton.addActionListener(this);
		recommedButton.setBounds(133, 237, 350, 20);
		recommedButton.setFont(new Font("Courier New", Font.BOLD, 16));
		background.add(recommedButton);

		discountBox = new JCheckBox();
		getContentPane().add(discountBox);
		discountBox.setBounds(235, 173, 250, 20);
		background.add(discountBox);

		setTitle("Homepage");
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Action listener for search button and recommend button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			String term = businessNameField.getText().trim();
			String location = cityField.getText().trim();
			boolean discount = discountBox.isSelected();
			this.setVisible(false);
			List<Restaurant> restaurants = YelpAPI.getInstance().searchForBusinessesByLocation(term, location, discount,
					YelpAPI.Best_Matched);
			ListPage listpage = new ListPage(this);
			listpage.setRestaurants(restaurants);
			listpage.refresh();
		}

		if (e.getSource() == recommedButton) {
			this.setVisible(false);
			new Recommendation(this);
		}
	}
}
