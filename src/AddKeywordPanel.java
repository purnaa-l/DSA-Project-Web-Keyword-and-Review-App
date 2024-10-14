package app;

import javax.swing.*;
import java.awt.*;

public class AddKeywordPanel extends JPanel {
	private DefaultListModel<String> keywordListModel; /*
														 * It is of type DefaultListModel<String>, which is a data model
														 * used by a JList in Swing to hold the items (in this case,
														 * strings representing keywords) that will be displayed in the
														 * list. It manages the list of items dynamically, allowing for
														 * additions and removals.
														 */
	private JList<String> keywordList;
	private JTextField keywordField;

	public AddKeywordPanel() {
		setLayout(new BorderLayout());

		keywordField = new JTextField("Enter the keyword here, and press Add Keyword!");
		JButton addButton = new JButton("Add Keyword");
		keywordListModel = new DefaultListModel<>();
		keywordList = new JList<>(
				keywordListModel); /*
									 * The JList is still necessary to provide a visual representation of the
									 * keywords. We use DefaultListModel for the JList to reflect changes in
									 * real-time as keywords are added.
									 */

		addButton.addActionListener(e -> {
			String keyword = keywordField.getText();
			if (keyword.equals("Enter the keyword here, and press Add Keyword!"))
				JOptionPane.showMessageDialog(this,
						"This is the placeholder! Kindly delete this, and re-enter your keyword!");
			else if (!keyword.isEmpty() && !keywordListModel.contains(keyword)) {
				keywordListModel.addElement(keyword);
				keywordField.setText("");
			} else if (keywordListModel.contains(keyword))
				JOptionPane.showMessageDialog(this, "We already have this keyword in the database!");
			else
				JOptionPane.showMessageDialog(this, "You cannot submit an empty keyword!");
		});
		add(keywordField, BorderLayout.NORTH); /* alignment for the boxes in the screen */
		add(new JScrollPane(keywordList), BorderLayout.CENTER);
		add(addButton, BorderLayout.SOUTH);
	}
}
