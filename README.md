# DSA-Project-Web-Keyword-and-Review-App

This web keyword and review app is a DSA project built entirely using Java, utilizing GUI components from the Swing and AWT libraries. The application provides users with an interface to manage keywords, discover new URLs, and leave reviews for websites based on their experiences.

The Web Keyword and Review App consists of several panels, each providing distinct functionalities:

### **Add Keyword**
-Users can add new keywords to a list. The application ensures that duplicate keywords cannot be added.
-It provides an interactive text field for entering keywords and a button to submit them.

### **Discover**
-Users can input a URL and associate keywords with that URL.
-This feature allows users to update a dataset with relevant URLs and corresponding keywords, saving the information to a file.

### **Review**
-Users can select a website from a dropdown list and leave a review and star rating.
-The application saves the reviews in a structured format, allowing users to provide feedback on different websites.

### **Web Finder**
-Users can search for reviews based on keywords or website URLs.
-This feature fetches and displays all the relevant reviews associated with the entered search term.

## Technology Stack
-Programming Language: Java
-GUI Framework: Swing and AWT
-File Handling: Java I/O for reading and writing text files

## Project Structure
The project is organized into multiple classes, each responsible for a specific feature:
-AddKeywordPanel: Handles keyword addition functionality.
-DiscoverPanel: Manages the discovery of URLs and keywords.
-ReviewPanel: Manages the submission and display of reviews.
-WebFinderPanel: Provides the functionality to find reviews based on keywords or website URLs.
-WebKeywordReviewApp: The main application window that integrates all panels and handles navigation.
