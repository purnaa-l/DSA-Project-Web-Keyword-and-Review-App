package app;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class DiscoverPanel extends JPanel {
	private JTextField urlField;
	private JButton updateButton;
	private JTextArea keywordsArea;

	public DiscoverPanel() {
		// Setup the panel layout and components
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel urlLabel = new JLabel("Enter URL:");
		urlField = new JTextField("Enter the URL here and the associated keywords below!");
		updateButton = new JButton("Update Dataset");
		keywordsArea = new JTextArea(5, 40);
		keywordsArea.setLineWrap(true);
		keywordsArea.setWrapStyleWord(true);

		add(urlLabel);
		add(urlField);
		add(new JScrollPane(keywordsArea));
		add(updateButton);

		// Action listener for the button to update the dataset
		updateButton.addActionListener(e -> {
			String url = urlField.getText();
			String keywords = keywordsArea.getText();
			if (!url.isEmpty() && !keywords.isEmpty()) {
				saveKeywordsToFile(url, keywords);
				JOptionPane.showMessageDialog(this, "Dataset updated!");
				urlField.setText(""); // Clear input fields
				keywordsArea.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Please enter both URL and Keywords.");
			}
		});
	}

	// Save the URL and keywords to keywords.txt
	private void saveKeywordsToFile(String url, String keywords) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("keywords.txt", true))) {
			// Clean and format keywords
			String formattedKeywords = keywords.replaceAll("\\s+", " ").trim(); // Replace multiple spaces with a single
																				// space
			writer.write(url + " - " + formattedKeywords); // Write URL and keywords in the correct format
			writer.newLine();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
