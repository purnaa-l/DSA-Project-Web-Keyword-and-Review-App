package app;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ReviewPanel extends JPanel {
	private JTextArea reviewArea;
	private JComboBox<String> starRating;

	// Load unique websites from the keywords.txt file
	private String[] loadWebsitesFromFile(String filename) {
		Set<String> websiteSet = new HashSet<>(); // Use a Set to avoid duplicates
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Extract URL before the first space or dash
				String[] parts = line.split(" - ")[0].split(" ");
				if (parts.length > 0)
					websiteSet.add(parts[0]); // Add the website to the set
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return websiteSet.toArray(new String[0]); // Convert Set to Array
	}

	public ReviewPanel() {
		setLayout(new BorderLayout());

		// Load unique websites from keywords.txt
		String[] websites = loadWebsitesFromFile("keywords.txt");
		JComboBox<String> websiteList = new JComboBox<>(websites);

		starRating = new JComboBox<>(new String[] { "1 Star", "2 Stars", "3 Stars", "4 Stars", "5 Stars" });

		reviewArea = new JTextArea("Write your review here!");
		reviewArea.setLineWrap(true);
		reviewArea.setWrapStyleWord(true);
		reviewArea.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Set font for better readability
		reviewArea.setPreferredSize(new Dimension(400, 150)); // Set preferred size

		reviewArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Your Review"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Add padding around the text area

		JButton submitButton = new JButton("Submit Review");
		submitButton.setFont(new Font("Times New Roman", Font.BOLD, 14)); // Set font for the button
		submitButton.setPreferredSize(new Dimension(150, 40)); // Set preferred size for the button

		// Add action listener to the submit button
		submitButton.addActionListener(e -> {
			String website = websiteList.getSelectedItem().toString();
			String review = reviewArea.getText();
			String stars = starRating.getSelectedItem().toString();
			if (review.equals("Write your review here!"))
				JOptionPane.showMessageDialog(this,
						"This is the placeholder! Kindly delete this, and re-enter your review!");
			else if (!review.isEmpty()) {
				saveReview(website, review, stars);
				JOptionPane.showMessageDialog(this, "Thank you for your valuable review");
				reviewArea.setText(""); // Clear after submission
			} else {
				JOptionPane.showMessageDialog(this, "Please submit a valid review!");
			}
		});

		// Adding components with spacing
		add(websiteList, BorderLayout.NORTH);
		add(new JScrollPane(reviewArea), BorderLayout.CENTER);
		add(starRating, BorderLayout.SOUTH);
		add(submitButton, BorderLayout.EAST);

		// Add some padding around the panel
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set padding for the panel
	}

	// Save the review to a file
	private void saveReview(String website, String review, String stars) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("reviews.txt", true))) {
			writer.write(website + " - Rating: " + stars + " - Review: " + review + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
