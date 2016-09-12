/**
 * This is the class of restaurant
 * @author user
 *
 */
public class Restaurant implements Business {

	/**
	 * Instance Variables
	 */
	private String imageURL;
	private String name;
	private String category;
	private String address;
	private double rating;
	private String phone;
	private int reviewCount;
	private double latitude;
	private double longitude;
	private String deal;

	/**
	 * constructor
	 * @param imageURL
	 * @param name
	 * @param category
	 * @param address
	 * @param rating
	 * @param phone
	 * @param reviewCount
	 * @param latitude
	 * @param longitude
	 * @param deal
	 */
	public Restaurant(String imageURL, String name, String category, String address, double rating, String phone,
			int reviewCount, double latitude, double longitude, String deal) {
		this.imageURL = imageURL;
		this.name = name;
		this.category = category;
		this.address = address;
		this.rating = rating;
		this.phone = phone;
		this.reviewCount = reviewCount;
		this.deal = deal;
		this.latitude = latitude;
		this.longitude = longitude;

	}

	@Override
	public String toString() {
		return name + " " + address;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the reviewCount
	 */
	public int getReviewCount() {
		return reviewCount;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the deal
	 */
	public String getDeal() {
		return deal;
	}
}
