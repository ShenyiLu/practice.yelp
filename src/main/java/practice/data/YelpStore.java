package practice.data;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Data structure to store information about businesses, users, and reviews.
 *
 */
public class YelpStore {

	// TODO: Define data members here.
	JsonArray businessArray;
	JsonArray userArray;
	JsonArray reviewArray;
	String pattern;

	HashMap<String, JsonObject> businessIdMap;
	TreeMap<String, String> businessNameMap;
	HashMap<String, String> userIdMap;
	HashMap<String, TreeSet> reviewMap;
	
	/**
	 * Constructor. Create an empty YelpStore.
	 */
	public YelpStore() {
		businessArray = new JsonArray();
		userArray = new JsonArray();
		reviewArray = new JsonArray();
		pattern = "yyyy-MM-dd";

		businessIdMap = new HashMap<>();
		businessNameMap = new TreeMap<>();
		userIdMap = new HashMap<>();
		reviewMap = new HashMap<>();
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
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.parse(date);
		} catch (ParseException iae) {
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

		if (reviewMap.containsKey(businessId)){
			TreeSet temp = reviewMap.get(businessId);
			temp.add(newReview);
			reviewMap.put(businessId, temp);
		} else {
			TreeSet<JsonObject> newSet = new TreeSet<>(new ReviewComparator());
			newSet.add(newReview);
			reviewMap.put(businessId, newSet);
		}
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
		JsonObject newBusiness = new JsonObject();
		newBusiness.addProperty("businessId", businessId);
		newBusiness.addProperty("name", name);
		newBusiness.addProperty("city", city);
		newBusiness.addProperty("state", state);
		newBusiness.addProperty("lat", lat);
		newBusiness.addProperty("lon", lon);


		// check duplicate
		if (!businessIdMap.containsKey(businessId) && !businessNameMap.containsKey(name)){
			businessArray.add(newBusiness);
			businessIdMap.put(businessId, newBusiness);
			businessNameMap.put(name, businessId);
			return true;
		} else {
			System.out.println("duplicate business ID or name");
			return false;
		}
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

		JsonObject newBusiness = new JsonObject();
		newBusiness.addProperty("businessId", businessId);
		newBusiness.addProperty("name", name);
		newBusiness.addProperty("city", city);
		newBusiness.addProperty("state", state);
		newBusiness.addProperty("lat", lat);
		newBusiness.addProperty("lon", lon);
		newBusiness.add("neighborhoods", neighborhoods);

		// check duplicate
		if (!businessIdMap.containsKey(businessId) && !businessNameMap.containsKey(name)){
			businessArray.add(newBusiness);
			businessIdMap.put(businessId, newBusiness);
			businessNameMap.put(name, businessId);
			return true;
		} else {
			System.out.println("duplicate business ID or name");
			return false;
		}
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
		JsonObject newBusiness = new JsonObject();
		newBusiness.addProperty("businessId", businessId);
		newBusiness.addProperty("name", name);
		newBusiness.addProperty("city", city);
		newBusiness.addProperty("state", state);
		newBusiness.addProperty("lat", lat);
		newBusiness.addProperty("lon", lon);
		// parse neighborhoods string
		String[] temp = neighborhoods.split(",");
		JsonArray neighborhoodsArray = new JsonArray();
		for(int i = 0; i < temp.length; i++) {
			neighborhoodsArray.add(temp[i]);
		}
		newBusiness.add("neighborhoods", neighborhoodsArray);

		// check duplicate
		if (!businessIdMap.containsKey(businessId) && !businessNameMap.containsKey(name)){
			businessArray.add(newBusiness);
			businessIdMap.put(businessId, newBusiness);
			businessNameMap.put(name, businessId);
			return true;
		} else {
			System.out.println("duplicate business ID or name");
			return false;
		}
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

		// check duplicate
		if (!userIdMap.containsKey(userId)){
			userArray.add(newUser);
			userIdMap.put(userId, name);
			return true;
		} else {
			return false;
		}

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
		// Sort deepcopy of our array as needed
//		ArrayList<JsonObject> sortedBusinessArray = sortBusinessArray();
//		ArrayList<JsonObject> sortedReviewArray = sortReviewArray();
		Iterator it = businessNameMap.keySet().iterator();
		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()){
			String businessName = (String) it.next();
			String businessId = businessNameMap.get(businessName);
			buffer.append(businessToString(businessId));
			buffer.append(reviewToString(businessId));
		}
		return buffer.toString();
	}

	private String reviewToString(String businessId){

		return "\n";
	}

	private String businessToString(String businessId){
		JsonObject business = businessIdMap.get(businessId);
		String name = business.get("name").getAsString();
		String city = business.get("city").getAsString();
		String state = business.get("state").getAsString();
		String lat = business.get("lat").getAsString();
		String lon = business.get("lon").getAsString();
		String neighborhoods = "";

		if (business.has("neighborhoods")){
			JsonArray neighborArray = business.get("neighborhoods").getAsJsonArray();
			if (neighborArray.size() == 1){
				neighborhoods = neighborArray.get(0).getAsString();
			} else {
				for (int i = 0; i < neighborArray.size() - 1; i++){
					neighborhoods += (neighborArray.get(0).getAsString() + ", ");
				}
				neighborhoods += neighborArray.get(neighborArray.size() - 1).getAsString();
			}

		}
		return name + " - " + city + ", " + state + " (" + lat + ", " + lon + ") (" + neighborhoods + ")\n";
	}

	

	class ReviewComparator implements Comparator<JsonObject>{
		public int compare(JsonObject review1, JsonObject review2){
			String date1 = "";
			String date2 = "";
			try {
				date1 = review1.getAsJsonPrimitive("date").getAsString();
				date2 = review2.getAsJsonPrimitive("date").getAsString();
			} catch (JsonIOException e){
				System.out.println(e);
				return 0;
			}

			Date fdate1;
			Date fdate2;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				fdate1 = sdf.parse(date1);
				fdate2 = sdf.parse(date2);
			} catch (ParseException iae) {
				System.out.println(iae);
				return 0;
			}
			return fdate1.compareTo(fdate2);
		}
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
