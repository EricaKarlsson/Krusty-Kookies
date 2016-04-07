package project;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Order extends BasicPane {
	/**
	 * A label showing the name of the current user.
	 */
	private JLabel currentUserNameLabel;

	/**
	 * Amount to order.
	 */
	private int count = 0;

	/**
	 * The list model for the customer name list.
	 */
	private DefaultListModel<String> customerListModel;

	/**
	 * The customer name list.
	 */
	private JList<String> customerList;

	/**
	 * The list model for the recipe name list.
	 */
	private DefaultListModel<String> recipeListModel;

	/**
	 * The recipe name list.
	 */
	private JList<String> recipeList;

	/**
	 * The text fields where the movie data is shown.
	 */
	private JTextField[] fields;

	/**
	 * The number of the movie name field.
	 */
	private static final int CUSTOMER_NAME = 0;

	/**
	 * The number of the performance date field.
	 */
	private static final int RECIPE_NAME = 1;

	/**
	 * The number of the movie theater field.
	 */
	private static final int AMOUNT = 2;

	// /**
	// * The number of the 'number of free seats' field.
	// */
	// private static final int FREE_SEATS = 3;
	//
	/**
	 * The total number of fields.
	 */
	private static final int NBR_FIELDS = 3;

	public Order(Database db) {
		super(db);

	}

	public JComponent createLeftPanel() {


		recipeListModel = new DefaultListModel<String>();

		recipeList = new JList<String>(recipeListModel);
		recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recipeList.setPrototypeCellValue("123456789012");
		recipeList.addListSelectionListener(new DateSelectionListener());
		JScrollPane p2 = new JScrollPane(recipeList);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(p2);
		return p;
	}

	/**
	 * Create the top panel, containing the fields with the performance data.
	 * 
	 * @return The top panel.
	 */
	public JComponent createTopPanel() {
		String[] texts = new String[NBR_FIELDS];
		texts[CUSTOMER_NAME] = "Customer";
		texts[RECIPE_NAME] = "Cookie";
		texts[AMOUNT] = "Amount of pallets";

		fields = new JTextField[NBR_FIELDS];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
			fields[i].setEditable(false);
		}

		JPanel input = new InputPanel(texts, fields);

		 JPanel p1 = new JPanel();
		 p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		 p1.add(new JLabel("Current user: "));
		 currentUserNameLabel = new JLabel("");
		 p1.add(currentUserNameLabel);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(input);
		return p;
	}

	/**
	 * Create the bottom panel, containing the book ticket-button and the
	 * message line.
	 * 
	 * @return The bottom panel.
	 */
	public JComponent createBottomPanel() {
		JButton[] buttons = new JButton[1];
		buttons[0] = new JButton("Add more pallets");
		return new ButtonAndMessagePanel(buttons, messageLabel,
				new ActionHandler());
	}
	

	/**
	 * Perform the entry actions of this pane: clear all fields, fetch the
	 * recipe names from the database and display them in the name list.
	 */
	public void entryActions() {
		clearMessage();
		currentUserNameLabel.setText(CurrentUser.instance().getCurrentUserId());
		fillRecipeList();
		clearFields();
	}

	/**
	 * Fetch recipes names from the database and display them in the name list.
	 */
	private void fillRecipeList() {
		recipeListModel.removeAllElements();
		ArrayList<String> recipes = db.getRecipesList();
		for (String m : recipes) {
			recipeListModel.addElement(m);
		}
	}


	/**
	 * Clear all text fields.
	 */
	private void clearFields() {
		for (int i = 0; i < fields.length; i++) {
			fields[i].setText("");
		}
	}


	/**
	 * A class that listens for clicks in the recipe list.
	 */
	class DateSelectionListener implements ListSelectionListener {
		/**
		 * Called when the user selects a name in the date list. Fetches
		 * performance data from the database and displays it in the text
		 * fields.
		 *
		 * @param e
		 *            The selected list item.
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (recipeList.isSelectionEmpty()) {
				return;
			}
			String customerName = CurrentUser.instance().getCurrentUserId();
			String recipeName = recipeList.getSelectedValue();
			String recipe = recipeList.getSelectedValue();
			fields[CUSTOMER_NAME].setText(customerName);
			fields[RECIPE_NAME].setText(recipeName);
			fields[AMOUNT].setText(String.valueOf(count));

		}
	}

	/**
	 * A class that listens for button clicks.
	 */
	class ActionHandler implements ActionListener {
		/**
		 * Called when the user clicks the Book ticket button. Books a ticket
		 * for the current user to the selected performance (adds a booking to
		 * the database).
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			if (recipeList.isSelectionEmpty()) {
				return;
			}
			if (!CurrentUser.instance().isLoggedIn()) {
				displayMessage("Must login first");
				return;
			}
			String recipeName = recipeList.getSelectedValue();
//			String date = dateList.getSelectedValue();
			String user = CurrentUser.instance().getCurrentUserId();
			
			int i = db.createOrder();
//			if (i != 0) {
//				displayMessage("TICKET BOOKED FOR " + "'" + movieName + "'" + " on " + date);
//				fields[FREE_SEATS].setText(String.valueOf(i-1));
//			} else {
//				displayMessage("FAILED. NO TICKETS LEFT");
//			}
			fields[AMOUNT].setText(String.valueOf(count + 1));
			count++;
		}
	}
		
	
}
