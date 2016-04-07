package project;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 * MovieGUI is the user interface to the movie database. It sets up the main
 * window and connects to the database.
 */
public class GUI1 {
	/**
	 * db is the database object
	 */
	private Database db;

	/**
	 * tabbedPane is the contents of the window. It consists of two panes, User
	 * login and Book tickets.
	 */
	private JTabbedPane tabbedPane;

	/**
	 * Create a GUI object and connect to the database.
	 * 
	 * @param db
	 *            The database.
	 */
	public GUI1(Database db) {
		this.db = db;

		JFrame frame = new JFrame("MovieBooking");
		tabbedPane = new JTabbedPane();

		UserLoginPane userLoginPane = new UserLoginPane(db);
		tabbedPane.addTab("User login", null, userLoginPane,
				"Log in as a new user");
		
//		Recipes recipesPane = new Recipes(db);
//		tabbedPane.addTab("Recipes", null, recipesPane, "Recipes");

//		BookingPane bookingPane = new BookingPane(db);
//		tabbedPane.addTab("Book ticket", null, bookingPane, "Book a ticket");

		Ingredients ingredientsPane = new Ingredients(db);
		tabbedPane.addTab("Ingredients", null, ingredientsPane, "Ingredients");

		Order orderPane = new Order(db);
		tabbedPane.addTab("Order", null, orderPane, "Order");

		tabbedPane.setSelectedIndex(0);

		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addChangeListener(new ChangeHandler());
		frame.addWindowListener(new WindowHandler());

		frame.setSize(500, 400);
		frame.setVisible(true);

		userLoginPane.displayMessage("Connecting to database ...");
		
		/* --- change code here --- */
		/* --- change xxx to your user name, yyy to your password --- */
		if (db.openConnection("db39", "yaw535mr")) {
			userLoginPane.displayMessage("Connected to database");
		} else {
			userLoginPane.displayMessage("Could not connect to database");
		}
	}

	/**
	 * ChangeHandler is a listener class, called when the user switches panes.
	 */
	class ChangeHandler implements ChangeListener {
		/**
		 * Called when the user switches panes. The entry actions of the new
		 * pane are performed.
		 * 
		 * @param e
		 *            The change event (not used).
		 */
		public void stateChanged(ChangeEvent e) {
			BasicPane selectedPane = (BasicPane) tabbedPane
					.getSelectedComponent();
			selectedPane.entryActions();
		}
	}

	/**
	 * WindowHandler is a listener class, called when the user exits the
	 * application.
	 */
	class WindowHandler extends WindowAdapter {
		/**
		 * Called when the user exits the application. Closes the connection to
		 * the database.
		 * 
		 * @param e
		 *            The window event (not used).
		 */
		public void windowClosing(WindowEvent e) {
			db.closeConnection();
			System.exit(0);
		}
	}
}
