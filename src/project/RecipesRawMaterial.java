package project;

/**
 * MovieBooking is the main class for the movie ticket booking 
 * application. It creates a database object and the GUI to
 * interface to the database.
 */
public class RecipesRawMaterial {
    public static void main(String[] args) {
        Database db = new Database();
        new GUI1(db);
    }
}
