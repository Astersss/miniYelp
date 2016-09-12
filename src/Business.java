/**
 * This class is the interface for the business categories The interface is
 * supposed to support different kinds of categories We can extend our APP into
 * other business categories besides restaurant
 * 
 * @author user
 *
 */

public interface Business {

	public String getName();

	public String getImageURL();

	public String getCategory();

	public String getAddress();

	public double getRating();

	public String getPhone();

	public int getReviewCount();

}
