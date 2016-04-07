//package project;
//
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//import javax.swing.BoxLayout;
//import javax.swing.DefaultListModel;
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//import javax.swing.ListSelectionModel;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//import project.BookingPane.ActionHandler;
//
//public class Recipes extends BasicPane {
//
//	/**
//	 * A label showing the name of the current user.
//	 */
//	private JLabel currentUserNameLabel;
//	/**
//	 * The list model for the recipes name list.
//	 */
//	private DefaultListModel<String> nameListModel;
//	/**
//	 * The recipes name list.
//	 */
//	private JList<String> nameList;
//	/**
//	 * The list model for the ingredient amount list.
//	 */
//	private DefaultListModel<String> ingredientListModel;
//
//	/**
//	 * The ingredient amount list.
//	 */
//	private JList<String> ingredientList;
//	/**
//	 * The text fields where the recipe data is shown.
//	 */
//	private JTextField[] fields;
//	/**
//	 * The number of the movie name field.
//	 */
//	private static final int RECIPE_NAME = 0;
//
//	/**
//	 * The number of the performance date field.
//	 */
//	private static final int INGREDIENT_NAME = 1;
//
//	/**
//	 * The number of the movie theater field.
//	 */
//	private static final int INGREDIENT_AMOUNT = 2;
//
//	/**
//	 * The number of the 'number of free seats' field.
//	 */
//	private static final int INGREDIENT_STORED = 3;
//
//	/**
//	 * The total number of fields.
//	 */
//	private static final int NBR_FIELDS = 4;
//
//	public Recipes(Database db) {
//		super(db);
//	}
//
//	public JComponent createLeftPanel() {
//		nameListModel = new DefaultListModel<String>();
//
//		nameList = new JList<String>(nameListModel);
//		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		nameList.setPrototypeCellValue("123456789012");
//		nameList.addListSelectionListener(new NameSelectionListener());
//		JScrollPane p1 = new JScrollPane(nameList);
//
//		ingredientListModel = new DefaultListModel<String>();
//
//		ingredientList = new JList<String>(ingredientListModel);
//		ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		ingredientList.setPrototypeCellValue("123456789012");
//		ingredientList.addListSelectionListener(new DateSelectionListener());
//		JScrollPane p2 = new JScrollPane(ingredientList);
//
//		JPanel p = new JPanel();
//		p.setLayout(new GridLayout(1, 2));
//		p.add(p1);
//		p.add(p2);
//		return p;
//	}
//
//	/**
//	 * Create the top panel, containing the fields with the ingredients data.
//	 * 
//	 * @return The top panel.
//	 */
//	public JComponent createTopPanel() {
//		String[] texts = new String[NBR_FIELDS];
//		texts[RECIPE_NAME] = "Recipe";
//		texts[INGREDIENT_NAME] = "Ingredient";
//		texts[INGREDIENT_AMOUNT] = "Ingredient amount in recipe";
//		texts[INGREDIENT_STORED] = "Amount of ingredient stored";
//
//		fields = new JTextField[NBR_FIELDS];
//		for (int i = 0; i < fields.length; i++) {
//			fields[i] = new JTextField(20);
//			fields[i].setEditable(false);
//		}
//
//		JPanel input = new InputPanel(texts, fields);
//
//		JPanel p1 = new JPanel();
//		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
//		p1.add(new JLabel("Current user: "));
//		currentUserNameLabel = new JLabel("");
//		p1.add(currentUserNameLabel);
//
//		JPanel p = new JPanel();
//		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//		p.add(p1);
//		p.add(input);
//		return p;
//	}
//
//	/**
//	 * Create the bottom panel, containing the order more-button and the message
//	 * line.
//	 * 
//	 * @return The bottom panel.
//	 */
//	public JComponent createBottomPanel() {
//		JButton[] buttons = new JButton[1];
//		buttons[0] = new JButton("Order more");
//		return new ButtonAndMessagePanel(buttons, messageLabel,
//				new ActionHandler());
//	}
//
//	/**
//	 * Perform the entry actions of this pane: clear all fields, fetch the
//	 * recipe names from the database and display them in the name list.
//	 */
//	public void entryActions() {
//		clearMessage();
//		// currentUserNameLabel.setText(CurrentUser.instance().getCurrentUserId());
//		fillNameList();
//		clearFields();
//	}
//
//	/**
//	 * Fetch recipes names from the database and display them in the name list.
//	 */
//	private void fillNameList() {
//		nameListModel.removeAllElements();
//		ArrayList<String> recipes = db.getRecipesList();
//		for (String m : recipes) {
//			nameListModel.addElement(m);
//		}
//	}
//
//	/**
//	 * Fetch ingredient dates from the database and display them in the date
//	 * list.
//	 */
//	private void fillIngredientList(String recipe) {
//		ingredientListModel.removeAllElements();
//		ArrayList<String> dates = db.getIngredientList(recipe);
//		for (String d : dates) {
//			ingredientListModel.addElement(d);
//		}
//	}
//
//	/**
//	 * Clear all text fields.
//	 */
//	private void clearFields() {
//		for (int i = 0; i < fields.length; i++) {
//			fields[i].setText("");
//		}
//	}
//
//	class NameSelectionListener implements ListSelectionListener {
//		/**
//		 * Called when the user selects a name in the name list. Fetches
//		 * performance dates from the database and displays them in the date
//		 * list.
//		 * 
//		 * @param e
//		 *            The selected list item.
//		 */
//		public void valueChanged(ListSelectionEvent e) {
//			if (nameList.isSelectionEmpty()) {
//				return;
//			}
//			String recipeName = nameList.getSelectedValue();
//			fillIngredientList(recipeName);
//		}
//	}
//
//	/**
//	 * A class that listens for clicks in the date list.
//	 */
//	class DateSelectionListener implements ListSelectionListener {
//		/**
//		 * Called when the user selects a name in the date list. Fetches
//		 * performance data from the database and displays it in the text
//		 * fields.
//		 * 
//		 * @param e
//		 *            The selected list item.
//		 */
//		public void valueChanged(ListSelectionEvent e) {
//			if (nameList.isSelectionEmpty()
//					|| ingredientList.isSelectionEmpty()) {
//				return;
//			}
//			String recipeName = nameList.getSelectedValue();
//			String ingName = ingredientList.getSelectedValue();
//			ArrayList<String> perfInfo = db.getIngredientInfo(recipeName,
//					ingName);
//			fields[RECIPE_NAME].setText(recipeName);
//			fields[INGREDIENT_NAME].setText(ingName);
//			fields[INGREDIENT_AMOUNT].setText(String.valueOf(perfInfo.get(1)));
//			fields[INGREDIENT_STORED].setText(String.valueOf(perfInfo.get(0)));
//
//		}
//	}
//
//	/**
//	 * A class that listens for button clicks.
//	 */
//	class ActionHandler implements ActionListener {
//		/**
//		 * Called when the user clicks the Book ticket button. Books a ticket
//		 * for the current user to the selected performance (adds a booking to
//		 * the database).
//		 * 
//		 * @param e
//		 *            The event object (not used).
//		 */
//		public void actionPerformed(ActionEvent e) {
//			if (nameList.isSelectionEmpty()
//					|| ingredientList.isSelectionEmpty()) {
//				return;
//			}
//			// if (!CurrentUser.instance().isLoggedIn()) {
//			// displayMessage("Must login first");
//			// return;
//			// }
//			String movieName = nameList.getSelectedValue();
//			String ingName = ingredientList.getSelectedValue();
//			String user = CurrentUser.instance().getCurrentUserId();
//
//			int i = db.orderIngredient(ingName);
//			if (i != 0) {
//				displayMessage(ingName + " has been orderd.");
//				fields[INGREDIENT_STORED].setText(String.valueOf(i + 1000));
//			} else {
//				displayMessage("Order failed");
//			}
//		}
//	}
//}
