package app;

import java.awt.Color;
import java.awt.Font;
// For file input and output
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// For data structures (List, Map, etc.)
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// For GUI components
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WebFinderPanel extends JPanel {
	private JTextField searchField; // Text field for user input
	private JTextField keywordField; // Text field for keyword input
	private JButton findButton; // Button to initiate search
	private JButton discoverButton; // Button to discover keywords
	private JTextArea resultsArea; // Area to display search results
	private Set<String> keywords; // Set to store keywords for validation

	public WebFinderPanel() {
		// Set layout to vertical box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Label for the search input field
		JLabel searchLabel = new JLabel("Website URL please:");
		JLabel keywordLabel = new JLabel("Discover Reviews by Keyword:");

		// Initialize search text field with 20 columns
		searchField = new JTextField(20);
		keywordField = new JTextField(20); // Text field for keyword discovery

		// Initialize find and discover buttons
		findButton = new JButton("Find");
		discoverButton = new JButton("Discover Keywords");

		// Initialize results text area with 10 rows and 30 columns
		resultsArea = new JTextArea(10, 30);
		resultsArea.setLineWrap(true); // Enable line wrapping
		resultsArea.setWrapStyleWord(true); // Wrap at word boundaries
		resultsArea.setEditable(false); // Make results area non-editable

		// Add components to the panel
		add(searchLabel);
		add(searchField);
		add(findButton);
		add(keywordLabel);
		add(keywordField);
		add(discoverButton);
		add(new JScrollPane(resultsArea)); // Add scroll pane for results area

		// Action listener for the "Find" button
		findButton.addActionListener(e -> {
			String searchTerm = searchField.getText().trim(); // Get input and trim spaces
			if (!searchTerm.isEmpty() && keywords.contains(searchTerm)) {
				findReviews(searchTerm); // Call method to find reviews
			} else {
				JOptionPane.showMessageDialog(this, "Please enter a valid keyword or website."); // Alert for invalid
																									// input
			}
		});

		// Action listener for the "Discover Keywords" button
		discoverButton.addActionListener(e -> {
			String keyword = keywordField.getText().trim(); // Get keyword input
			if (!keyword.isEmpty()) {
				discoverReviewsByKeyword(keyword); // Call method to discover reviews
			} else {
				JOptionPane.showMessageDialog(this, "Please enter a keyword."); // Alert for empty input
			}
		});

		// Aesthetic improvements: Set background and font styles
		setBackground(new Color(230, 230, 255)); // Light purple background
		searchLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Bold font for label
		searchField.setFont(new Font("Arial", Font.PLAIN, 14)); // Plain font for input
		findButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font for button
		keywordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Bold font for keyword label
		keywordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Plain font for keyword input
		discoverButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font for discover button
		resultsArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Plain font for results

		// Load unique websites from keywords.txt
		keywords = loadWebsitesFromKeywords();
	}

	// Load unique websites from keywords.txt
	private Set<String> loadWebsitesFromKeywords() {
		Set<String> uniqueWebsites = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("keywords.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String website = line.split(" - ")[0].trim(); // Extract the website part
				uniqueWebsites.add(website); // Add to set for uniqueness
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Unique Websites Loaded: " + uniqueWebsites);
		return uniqueWebsites; // Return the set of unique websites
	}

	// Find and display reviews from reviews.txt based on the search term
	private void findReviews(String searchTerm) {
		resultsArea.setText(""); // Clear previous results
		Map<String, List<String>> websiteReviews = new HashMap<>(); // Store reviews per website
		Map<String, Integer> websiteStars = new HashMap<>(); // Store total stars per website

		// Read reviews from the file
		try (BufferedReader reader = new BufferedReader(new FileReader("reviews.txt"))) {
			String line; // Variable to hold each line from the file
			while ((line = reader.readLine()) != null) {
				if (line.toLowerCase().contains(searchTerm.toLowerCase())) { // Case-insensitive check
					String[] parts = line.split(" - "); // Split the line into parts
					if (parts.length >= 3) { // Ensure there are enough parts
						String website = parts[0].trim(); // Get the website URL
						String starsPart = parts[1].split(":")[1].trim(); // Get the star rating part
						int stars = Integer.parseInt(starsPart.substring(0, 1)); // Extract the first character as stars

						// Store each review with its stars in a formatted way
						String review = "Stars: " + stars + " - " + parts[2].trim(); // Format the review
						websiteReviews.putIfAbsent(website, new ArrayList<>());
						websiteReviews.get(website).add(review); // Add the formatted review
						websiteStars.put(website, websiteStars.getOrDefault(website, 0) + stars); // Update total stars
																									// if needed
					}
				}
			}

			// Check if any results were found
			if (websiteReviews.isEmpty()) {
				resultsArea.setText("No reviews found for " + searchTerm); // Message for no results
			} else {
				// Display reviews for each website
				websiteReviews.forEach((website, reviews) -> {
					resultsArea.append(website + ":\n"); // Display website
					reviews.forEach(review -> {
						resultsArea.append(review + "\n"); // Display each formatted review
					});
					resultsArea.append("\n"); // Add a new line after each website's reviews for better readability
				});
			}
		} catch (IOException ex) {
			ex.printStackTrace(); // Print stack trace for exceptions
		}
	}

	// Discover reviews by keyword
	private void discoverReviewsByKeyword(String keyword) {
		resultsArea.setText(""); // Clear previous results
		Map<String, List<String>> keywordReviews = new HashMap<>(); // Store reviews by keyword

		// Read reviews from the file
		try (BufferedReader reader = new BufferedReader(new FileReader("reviews.txt"))) {
			String line; // Variable to hold each line from the file
			while ((line = reader.readLine()) != null) {
				if (line.toLowerCase().contains(keyword.toLowerCase())) { // Case-insensitive check
					String[] parts = line.split(" - "); // Split the line into parts
					if (parts.length >= 3) { // Ensure there are enough parts
						String website = parts[0].trim(); // Get the website URL
						String starsPart = parts[1].split(":")[1].trim(); // Get the star rating part
						String review = parts[2].trim(); // Get the review text
						String formattedReview = "Rating: " + starsPart + " - Review: " + review; // Format the review

						// Store reviews by keyword
						keywordReviews.putIfAbsent(website, new ArrayList<>());
						keywordReviews.get(website).add(formattedReview); // Add the formatted review
					}
				}
			}

			// Check if any results were found
			if (keywordReviews.isEmpty()) {
				resultsArea.setText("No reviews found for keyword: " + keyword); // Message for no results
			} else {
				// Display reviews for each website related to the keyword
				keywordReviews.forEach((website, reviews) -> {
					resultsArea.append(website + ":\n"); // Display website
					reviews.forEach(review -> {
						resultsArea.append(review + "\n"); // Display each formatted review
					});
					resultsArea.append("\n"); // Add a new line after each website's reviews for better readability
				});
			}
		} catch (IOException ex) {
			ex.printStackTrace(); // Print stack trace for exceptions
		}
	}
}
