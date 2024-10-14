package app;

import javax.swing.*;
import java.awt.*;

public class WebKeywordReviewApp extends JFrame {
    public WebKeywordReviewApp() {
        setTitle("Web Keyword and Review App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Panel for navigation
        JPanel menuPanel = new JPanel(new GridLayout(5, 1));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        JButton addKeywordBtn = new JButton("Add Keyword");
        addKeywordBtn.setBackground(Color.CYAN);
        addKeywordBtn.setForeground(Color.BLACK);

        JButton discoverBtn = new JButton("Discover");
        discoverBtn.setBackground(Color.ORANGE);
        discoverBtn.setForeground(Color.BLACK);

        JButton reviewBtn = new JButton("Review");
        reviewBtn.setBackground(Color.PINK);
        reviewBtn.setForeground(Color.BLACK);

        JButton webFinderBtn = new JButton("Web Finder");
        webFinderBtn.setBackground(Color.YELLOW);
        webFinderBtn.setForeground(Color.BLACK);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);

        menuPanel.add(addKeywordBtn);
        menuPanel.add(discoverBtn);
        menuPanel.add(reviewBtn);
        menuPanel.add(webFinderBtn);
        menuPanel.add(exitBtn);

        add(menuPanel, BorderLayout.WEST);

        // Main content area
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // Switch Panels
        addKeywordBtn.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new AddKeywordPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        discoverBtn.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new DiscoverPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        reviewBtn.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new ReviewPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        webFinderBtn.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new WebFinderPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WebKeywordReviewApp app = new WebKeywordReviewApp();
            app.setVisible(true);
        });
    }
}
