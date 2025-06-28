package org.example;

import org.example.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private ToolBar toolBar;
    private AddressBar addressBar;
    private FileTreePanel fileTreePanel;
    private FileListPanel fileListPanel;
    private StatusBar statusBar;
    private JSplitPane splitPane;

    public MainFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
    }

    private void initializeComponents() {
        toolBar = new ToolBar(this);
        addressBar = new AddressBar(this);
        fileTreePanel = new FileTreePanel(this);
        fileListPanel = new FileListPanel(this);
        statusBar = new StatusBar();

        // Create split pane for tree and list
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                fileTreePanel, fileListPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.3);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Top panel with toolbar and address bar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(toolBar, BorderLayout.NORTH);
        topPanel.add(addressBar, BorderLayout.SOUTH);

        // Add components to main frame
        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        // Window closing handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });

        // Setup cross-component communication
        fileTreePanel.addSelectionListener(path -> {
            fileListPanel.displayDirectory(path);
            addressBar.setCurrentPath(path);
            statusBar.setCurrentPath(path);
        });
    }

    private void configureFrame() {
        setTitle("File Explorer");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);

        // Set application icon (if available)
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/folder.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Icon not found, continue without it
        }
    }

    private void onWindowClosing() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit File Explorer?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            // Cleanup resources if needed
            dispose();
            System.exit(0);
        }
    }

    // Getter methods for components (for cross-component communication)
    public FileTreePanel getFileTreePanel() { return fileTreePanel; }
    public FileListPanel getFileListPanel() { return fileListPanel; }
    public StatusBar getStatusBar() { return statusBar; }
    public AddressBar getAddressBar() { return addressBar; }

    // Methods for toolbar actions
    public void navigateUp() {
        fileTreePanel.navigateUp();
    }

    public void navigateHome() {
        fileTreePanel.navigateHome();
    }

    public void refresh() {
        fileTreePanel.refresh();
        fileListPanel.refresh();
    }
}