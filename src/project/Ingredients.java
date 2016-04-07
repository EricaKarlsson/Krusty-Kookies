package project;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import project.BookingPane.NameSelectionListener;

public class Ingredients extends BasicPane{
	
	/**
	 * The list model for the ingredient name list.
	 */
	private DefaultListModel<String> nameListModel;

	/**
	 * The ingredient name list.
	 */
	private JList<String> nameList;

	public Ingredients(Database db) {
		super(db);	
	}
	
	public JComponent createLeftPanel() {
		nameListModel = new DefaultListModel<String>();

		nameList = new JList<String>(nameListModel);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.setPrototypeCellValue("123456789012");
		nameList.addListSelectionListener(new NameSelectionListener());
		
		JScrollPane p1 = new JScrollPane(nameList);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(p1);
		return p;
	}
	/**
	 * Perform the entry actions of this pane: clear all fields, fetch the ingredient
	 * names from the database and display them in the name list.
	 */
	public void entryActions() {
		clearMessage();
		// currentUserNameLabel.setText(CurrentUser.instance().getCurrentUserId());
		fillIngredientList();
		// clearFields();
	}
	/**
	 * Fetch ingredient dates from the database and display them in the date
	 * list.
	 */
	private void fillIngredientList() {
		nameListModel.removeAllElements();
		ArrayList<String> dates = db.getIngredientsList();
		for (String d : dates) {
			nameListModel.addElement(d);
		}
	}

	/**
	 * A class that listens for clicks in the ingredient list.
	 */
	class NameSelectionListener implements ListSelectionListener {
		/**
		 * Called when the user selects a name in the name list. Fetches
		 * performance dates from the database and displays them in the date
		 * list.
		 * 
		 * @param e
		 *            The selected list item.
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (nameList.isSelectionEmpty()) {
				return;
			}
			String movieName = nameList.getSelectedValue();
			/* --- insert own code here --- */
		}
	}


}
