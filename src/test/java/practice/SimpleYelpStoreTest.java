package practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import practice.data.YelpStore;

public class SimpleYelpStoreTest {

	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddReview() {
		String testName = "testSimpleAddReview";
		YelpStore store = new YelpStore();

		store.addReview("bus-id", 2, "Bad review", "2011-11-11", "user-id");		
		String expected = "";
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddBusiness() {
		String testName = "testSimpleAddBusiness";
		YelpStore store = new YelpStore();

		store.addBusiness("bus-id", "Bus Name", "Austin", "TX", 12.345, 98.765);
		String expected = "Bus Name - Austin, TX (12.345, 98.765) ()";

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAddUser() {
		String testName = "testSimpleAddUser";
		YelpStore store = new YelpStore();

		store.addUser("user-id", "Bob1");
		String expected = "";
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testSimpleAdd() {
		String testName = "testSimpleAdd";
		YelpStore store = new YelpStore();

		store.addUser("user-id", "Bob1");
		store.addReview("bus-id", 2, "Bad review", "2011-11-11", "user-id");		
		store.addBusiness("bus-id", "Bus Name", "Austin", "TX",  12.345, 98.765);

		String expected = "Bus Name - Austin, TX (12.345, 98.765) ()\n" +
				"2 - Bob1: Bad review";

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testAddMissingUser() {
		String testName = "testAddMissingUser";
		YelpStore store = new YelpStore();

		store.addUser("user-id", "Bob1");
		store.addReview("bus-id", 2, "Bad review", "2011-11-11", "user-id");		
		store.addReview("bus-id", 5, "Good review", "2011-11-10", "user-id2");		
		store.addBusiness("bus-id", "Bus Name", "Austin", "TX",  12.345, 98.765);

		String expected = "Bus Name - Austin, TX (12.345, 98.765) ()\n" + 
				"5 - : Good review\n" + 
				"2 - Bob1: Bad review";

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());
	}


	@Test(timeout = TestUtils.TIMEOUT)
	public void testAddMissingBusiness() {
		String testName = "testAddMissingBusiness";
		YelpStore store = new YelpStore();

		store.addUser("userId", "Bob1");
		store.addReview("bus-id", 2, "Bad review", "2011-11-11", "user-id");		

		String expected = "";
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());
	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testTwoBusinessesSameName() {
		String testName = "testTwoBusinessesSameName";
		YelpStore store = new YelpStore();

		store.addUser("user-id", "Bob1");

		store.addBusiness("bus-id1", "Bus Name", "Austin", "TX",  12.345, 98.765);
		store.addBusiness("bus-id2", "Bus Name", "Portland", "OR",  12.345, 98.765);

		store.addReview("bus-id1", 2, "Bad review", "2011-11-11", "user-id");		
		store.addReview("bus-id2", 5, "Good review", "2011-11-10", "user-id");

		String expected = "Bus Name - Austin, TX (12.345, 98.765) ()\n" + 
				"2 - Bob1: Bad review\n\n" +
				"Bus Name - Portland, OR (12.345, 98.765) ()\n" +
				"5 - Bob1: Good review";

		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expected, store.toString().trim());

	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testInvalidRating() {

		String testName = "testInvalidRating";
		YelpStore store = new YelpStore();

		boolean value = store.addReview("bus-id", 8, "Bad review", "2011-11-11", "user-id");
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), false, value);

	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testInvalidDate() {

		String testName = "testInvalidDate";
		YelpStore store = new YelpStore();

		boolean value = store.addReview("bus-id", 2, "Bad review", "2011-11", "user-id");
		Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), false, value);

	}

	@Test(timeout = TestUtils.TIMEOUT)
	public void testShortReviewsOnly() {

		String testName = "testShortReviewsOnly";
		YelpStore store = new YelpStore();

		Scanner infile = null;
		try {
			infile = new Scanner(new FileReader(TestUtils.INPUT_DIR + File.separator + "/yelp_academic_dataset.json"));
		} catch (FileNotFoundException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" +
					" Error opening infile: %s%n", testName, e.getMessage()));
		}

		JsonParser parser = new JsonParser();
		while(infile.hasNextLine()) {
			String line = infile.nextLine();
			JsonObject json = null;
			json = (JsonObject) parser.parse(line);
			String type = json.get("type").getAsString();
			if(type.equals("review")) {
				String text =  json.get("text").getAsString();
				if(text.length() > 140 || text.contains(System.getProperty("line.separator"))) {
					continue;
				}

				store.addReview(
						json.get("business_id").getAsString(), 
						json.get("stars").getAsInt(), 
						json.get("text").getAsString(), 
						json.get("date").getAsString(), 
						json.get("user_id").getAsString());
			} else if(type.equals("business")) {

				Double lat = json.get("latitude").getAsDouble();
				Double lon = json.get("longitude").getAsDouble();
				JsonArray neighborhoods = (JsonArray)json.get("neighborhoods");

				store.addBusiness(
						json.get("business_id").getAsString(), 
						json.get("name").getAsString(), 
						json.get("city").getAsString(), 
						json.get("state").getAsString(), 
						lat, lon, neighborhoods);
			} else if(type.equals("user")) {
				store.addUser(json.get("user_id").getAsString(), json.get("name").getAsString());
			}			
		}

		Path expected = FileSystems.getDefault().getPath(TestUtils.OUTPUT_DIR + File.separator + "shortreviews.txt");
		Path actual = FileSystems.getDefault().getPath(TestUtils.RESULT_DIR + File.separator + "shortreviews.txt");
		try {
			Files.deleteIfExists(actual);
		} catch (IOException e1) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" +
					" Error deleting file: %s%n", testName, e1.getMessage()));
		}

		store.printToFile(actual);
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" +
					" File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" +
					" Mismatched Line: %d%n", testName, -count));
		}

	}
}
