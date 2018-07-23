package practice.data;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;

/**
 * Data structure to store information about businesses, users, and reviews.
 *
 */
public class YelpStore {

	// TODO: Define data members here.
	JsonArray businessArray;
	JsonArray userArray;
	JsonArray reviewArray;
	
	/**
	 * Constructor. Create an empty YelpStore.
	 */
	public YelpStore() {
		businessArray = new JsonArray();
		userArray = new JsonArray();
		reviewArray = new JsonArray();
	}

	/**
	 * Add a new review.
	 * @param businessId - ID of the business reviewed.
	 * @param rating - integer rating 1-5.
	 * @param review - text of the review.
	 * @param date - date of the review in the format yyyy-MM-dd, e.g., 2015-05-25.
	 * @param userId - ID of the user writing the review.
	 * @return true if successful, false if unsuccessful because of invalid date or rating.
	 */
	public boolean addReview(String businessId, int rating, String review, String date, String userId) {

		// check rating range
		if (rating < 1 || rating > 5){
			return false;
		}
		// check date
		if (date.length() != 10){
			return false;
		}

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date);
		} catch (IllegalArgumentException iae) {
			return false;
		}

		// add new review
		JsonObject newReview = new JsonObject();
		newReview.addProperty("businessId", businessId);
		newReview.addProperty("rating", rating);
		newReview.addProperty("review", review);
		newReview.addProperty("date", date);
		newReview.addProperty("userId", userId);

		reviewArray.add(newReview);
		return true;
	}

	/**
	 * Add a new business. Assumes the business has no neighborhood information.
	 * @param businessId - ID of the business.
	 * @param name - name of the business.
	 * @param city - city where the business is located.
	 * @param state - state where the business is located.
	 * @param lat - latitude of business location.
	 * @param lon - longitude of business location.
	 * @return true if successful.
	 */
	public boolean addBusiness(String businessId, String name, String city, String state, double lat, double lon) {
		JsonObject newReview = new JsonObject();
		newReview.addProperty("businessId", businessId);
		newReview.addProperty("name", name);
		newReview.addProperty("city", city);
		newReview.addProperty("state", state);
		newReview.addProperty("lat", lat);
		newReview.addProperty("lon", lon);

		businessArray.add(newReview);
		return true;
	}

	/**
	 * Add a new business.
	 * @param businessId - ID of the business.
	 * @param name - name of the business.
	 * @param city - city where the business is located.
	 * @param state - state where the business is located.
	 * @param lat - latitude of business location.
	 * @param lon - longitude of business location.
	 * @param neighborhoods - JSONArray containing a list of neighborhoods where the business is located.
	 * @return true if successful.
	 */
	public boolean addBusiness(String businessId, String name, String city, String state, double lat, double lon, JsonArray neighborhoods) {

		JsonObject newReview = new JsonObject();
		newReview.addProperty("businessId", businessId);
		newReview.addProperty("name", name);
		newReview.addProperty("city", city);
		newReview.addProperty("state", state);
		newReview.addProperty("lat", lat);
		newReview.addProperty("lon", lon);
		newReview.add("neighborhoods", neighborhoods);

		businessArray.add(newReview);
		return true;
	}

	/**
	 * Add a new business.
	 * @param businessId - ID of the business.
	 * @param name - name of the business.
	 * @param city - city where the business is located.
	 * @param state - state where the business is located.
	 * @param lat - latitude of business location.
	 * @param lon - longitude of business location.
	 * @param neighborhoods - comma separated String containing a list of neighborhoods
	 * @return true if successful.
	 */
	public boolean addBusiness(String businessId, String name, String city, String state, double lat, double lon, String neighborhoods) {
		JsonObject newReview = new JsonObject();
		newReview.addProperty("businessId", businessId);
		newReview.addProperty("name", name);
		newReview.addProperty("city", city);
		newReview.addProperty("state", state);
		newReview.addProperty("lat", lat);
		newReview.addProperty("lon", lon);
		// parse neighborhoods string
		String[] temp = neighborhoods.split(",");
		JsonArray neighborhoodsArray = new JsonArray();
		for(int i = 0; i < temp.length; i++) {
			neighborhoodsArray.add(temp[i]);
		}
		newReview.add("neighborhoods", neighborhoodsArray);

		businessArray.add(newReview);
		return true;

	}


	/**
	 * Add a new user.
	 * @param userId - ID of the user.
	 * @param name - name of the user (e.g., Sami R.)
	 * @return true if successful.
	 */
	public boolean addUser(String userId, String name) {
		JsonObject newUser = new JsonObject();
		newUser.addProperty("userId", userId);
		newUser.addProperty("name", name);
		userArray.add(newUser);
		return true;
	}

	/**
	 * Return a string representation of the data store. Format must be as follows:
	 	Business1 Name - City, State (lat, lon) (neighborhood1, neighborhood2)
		Rating - User: Review
		Rating - User: review
		Business2 Name - City, State (lat, lon) (neighborhood1, neighborhood2)
		Rating - User: Review
		Rating - User: review
	 * Adhere to the following rules in generating the output:
	 * 1. Business Names must be sorted alphabetically.
	 * 2. Ratings for a particular business must be sorted by date.
	 * 3. Only reviews for businesses that have been added will be displayed.
	 * 4. If a review is written by user U and no user U has been added to the store the review will appear with no name.
	 * @return string representation of the data store
	 */
	public String toString() {
		//REPLACE WITH YOUR CODE.
		return null;
	}

	/**
	 * Save the string representation of the data store to the file specified by fname.
	 * This method must use the YelpStore toString method.
	 * @param fname - path specifying where to save the output.
	 */
	public void printToFile(Path fname) {
		//REPLACE WITH YOUR CODE.
	}

} 
