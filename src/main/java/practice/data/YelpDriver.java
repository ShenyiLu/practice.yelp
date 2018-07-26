package practice.data;

public class YelpDriver {
    public static void main(String[] args){
        YelpStore store = new YelpStore();

        store.addUser("user-id", "Bob1");

        store.addBusiness("bus-id1", "Bus Name", "Austin", "TX",  12.345, 98.765);
        store.addBusiness("bus-id2", "Bus Name", "Portland", "OR",  12.345, 98.765);

        store.addReview("bus-id1", 2, "Bad review", "2011-11-11", "user-id");
        store.addReview("bus-id2", 5, "Good review", "2011-11-10", "user-id");

        System.out.println(store.toString());
    }
}
