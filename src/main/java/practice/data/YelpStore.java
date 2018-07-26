package practice.data;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

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
	TreeSet<NameObject> businessNameSet;
//	TreeMap<String, String> businessNameMap;
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
		businessNameSet = new TreeSet<>(new NameObjectComparator());
//		businessNameMap = new TreeMap<>();
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
		System.out.println("add review's business Id: " + businessId);
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
//		if (!businessIdMap.containsKey(businessId) && !businessNameMap.containsKey(name)){
		if (!businessIdMap.containsKey(businessId)){
			businessArray.add(newBusiness);
			businessIdMap.put(businessId, newBusiness);
			businessNameSet.add(new NameObject(name, businessId));
//			businessNameMap.put(name, businessId);
			System.out.println("add new business's business Id: " + businessId);
			return true;
		} else {
			System.out.println("duplicate business ID");
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
//		if (!businessIdMap.containsKey(businessId) && !businessNameMap.containsKey(name)){
		if (!businessIdMap.containsKey(businessId)){
			businessArray.add(newBusiness);
			businessIdMap.put(businessId, newBusiness);
			businessNameSet.add(new NameObject(name, businessId));
//			businessNameMap.put(name, businessId);
			System.out.println("add new business's business Id: " + businessId);
			return true;
		} else {
			System.out.println("duplicate business ID");
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
//		if (!businessIdMap.containsKey(businessId) && !businessNameMap.containsKey(name)){
		if (!businessIdMap.containsKey(businessId)){
			businessArray.add(newBusiness);
			businessIdMap.put(businessId, newBusiness);
			businessNameSet.add(new NameObject(name, businessId));
//			businessNameMap.put(name, businessId);
			System.out.println("add new business's business Id: " + businessId);
			return true;
		} else {
			System.out.println("duplicate business ID");
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
//		Iterator it = businessNameMap.keySet().iterator();
		Iterator it = businessNameSet.iterator();

		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()){
			NameObject temp = (NameObject) it.next();
			// no need to get name here?
//			String businessName = temp.getName();
			String businessId = temp.getBusinessId();
			buffer.append(businessToString(businessId));
			buffer.append(reviewToString(businessId));
		}
		return buffer.toString();
	}

	/**
	 * toString method for a business.
	 * @param businessId
	 * @return
	 */
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

	/**
	 * toString method for an array of reviews.
	 * @param businessId
	 * @return
	 */
	private String reviewToString(String businessId){
		StringBuffer buffer = new StringBuffer();

		if (!reviewMap.containsKey(businessId)){
			System.out.println("No businessId in review: " + businessId);
			return "";
		}

		TreeSet reviewSet = reviewMap.get(businessId);
		Iterator it = reviewSet.iterator();
		String currentReview;
		while (it.hasNext()){
			JsonObject review = (JsonObject) it.next();
			currentReview = review.get("rating").getAsString();
			currentReview += " - ";
			String userId = review.get("userId").getAsString();
			if(userIdMap.containsKey(userId)){
				currentReview += userIdMap.get(userId);
			} else {
				currentReview += "";
			}
			currentReview += ": " + review.get("review").getAsString() + "\n";
			buffer.append(currentReview);
		}

		return buffer.toString();
	}

	/**
	 * store a pair of business Id and name
	 */
	class NameObject{
		private String name;
		private String businessId;
		NameObject(String name, String businessId){
			this.name = name;
			this.businessId = businessId;
		}

		String getName(){
			return name;
		}

		String getBusinessId(){
			return businessId;
		}
	}

	/**
	 * compare NameObject, which compares business name
	 */
	class NameObjectComparator implements Comparator<NameObject>{
		public int compare(NameObject name1, NameObject name2){
			return name1.getName().compareTo(name2.getName());
		}
	}

	/**
	 * comparator to sort review objects by date.
	 */
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
		// got Path output method from here
		// https://stackoverflow.com/questions/6998905/java-bufferedwriter-object-with-utf-8
		File file = fname.toFile();
		try {
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			out.write(toString());
			out.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

} 
