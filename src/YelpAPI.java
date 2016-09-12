
/**
 * This class is back-end API Caller, which send request and and receive response
 * from server.
 * see https://www.yelp.com/developers/documentation/v2/examples for reference
 * the part of code we refer to the code sample is "hard" part, which possibly all
 * developers who want to develop based on yelp API have to use.
 */
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class YelpAPI {

	/**
	 * Instance Variables
	 */
	public static final int Best_Matched = 0;
	public static final int Highest_Rated = 2;

	private static final String API_HOST = "api.yelp.com";
	private static final int SEARCH_LIMIT = 18;
	private static final String SEARCH_PATH = "/v2/search";

	/*
	 * Update OAuth credentials below from the Yelp Developers API site:
	 * http://www.yelp.com/developers/getting_started/api_access
	 */
	private static final String CONSUMER_KEY = "SQB3braoJ_-Bw77zXkPMPQ";
	private static final String CONSUMER_SECRET = "_cdVdk-i1PkCDq_FiC82ws8FKSc";
	private static final String TOKEN = "m_1c5I24wiqZOc5wqOVmucy4vxG8h4GH";
	private static final String TOKEN_SECRET = "xiQid3LYj_OtWpsPx350jsq54ao";

	OAuthService service;
	Token accessToken;

	private OAuthRequest lastRequest;

	private static YelpAPI api = null;

	/**
	 * Design Pattern singleton
	 * 
	 * @return
	 */
	public static YelpAPI getInstance() {
		if (api == null)
			api = new YelpAPI();
		return api;
	}

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * @param consumerKey
	 *            Consumer key
	 * @param consumerSecret
	 *            Consumer secret
	 * @param token
	 *            Token
	 * @param tokenSecret
	 *            Token secret
	 */
	public YelpAPI() {
		this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET)
				.build();
		this.accessToken = new Token(TOKEN, TOKEN_SECRET);
	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * <p>
	 * See
	 * <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp
	 * Search API V2</a> for more info.
	 * 
	 * @param term
	 *            <tt>String</tt> of the search term to be queried
	 * @param location
	 *            <tt>String</tt> of the location
	 * @return <tt>List</tt> restaurants
	 */
	public List<Restaurant> searchForBusinessesByLocation(String term, String location, boolean discount, int sort) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("location", location);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
		if (discount)
			request.addQuerystringParameter("deals_filter", Boolean.toString(discount));
		request.addQuerystringParameter("sort", String.valueOf(sort));

		String searchResponseJSON = sendRequestAndGetResponse(request);

		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.out.println(searchResponseJSON);
		}
		JSONArray businesses = (JSONArray) response.get("businesses");

		return getRestaurants(businesses);
	}

	/**
	 * Creates and returns an {@link OAuthRequest} based on the API endpoint
	 * specified.
	 * 
	 * @param path
	 *            API endpoint to be queried
	 * @return <tt>OAuthRequest</tt>
	 */
	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
		return request;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 * 
	 * @param request
	 *            {@link OAuthRequest} corresponding to the API request
	 * @return <tt>String</tt> body of API response
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
		System.out.println("Querying " + request.getCompleteUrl() + " ...");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();

		lastRequest = request;

		return response.getBody();
	}

	/**
	 * Getter for lastRequest
	 * 
	 * @return
	 */
	public OAuthRequest getLastRequest() {
		return lastRequest;
	}

	/**
	 * this method constructs a list of restaurant which includes the detail
	 * information of the restaurant and return it
	 * 
	 * @param businesses
	 * @return List<Restaurant>
	 */
	private static List<Restaurant> getRestaurants(JSONArray businesses) {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();

		for (int i = 0; i < businesses.size(); i++) {
			try {
				JSONObject business = (JSONObject) businesses.get(i);

				String imageURL = business.get("image_url").toString();
				String name = business.get("name").toString();
				JSONArray categories = (JSONArray) business.get("categories");
				String category = "";
				for (int j = 0; j < categories.size(); j++) {
					category += ((JSONArray) categories.get(j)).get(0).toString();
					if (j < categories.size() - 1)
						category += ", ";
				}
				String address1 = ((JSONArray) ((JSONObject) business.get("location")).get("display_address"))
						.toString();// get(0).
				String address = address1.replaceAll("[\"]", " ");

				String phone = "";
				if (business.get("phone") != null)
					phone = business.get("phone").toString();
				
				String deal = "";
				if (business.get("deals") != null) {
					deal = ((JSONObject) ((JSONArray) business.get("deals")).get(0)).get("title").toString();
					System.out.println("deals " + deal);
				} else {
					deal = "no deal available";
				}
				double rating = Double.parseDouble(business.get("rating").toString());
				int reviewCount = Integer.parseInt(business.get("review_count").toString());

				double latitude = Double
						.parseDouble(((JSONObject) ((JSONObject) business.get("location")).get("coordinate"))
								.get("latitude").toString());
				double longitude = Double
						.parseDouble(((JSONObject) ((JSONObject) business.get("location")).get("coordinate"))
								.get("longitude").toString());
				Restaurant restaurant = new Restaurant(imageURL, name, category, address, rating, phone, reviewCount,
						latitude, longitude, deal);

				restaurants.add(restaurant);
			} catch (Exception e) {
				System.out.println("Error");
				continue;
			}
		}

		return restaurants;
	}
}
