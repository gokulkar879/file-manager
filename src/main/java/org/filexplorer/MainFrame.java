package org.filexplorer;

import org.filexplorer.components.FileExplorePanel;
import org.filexplorer.components.FileTreePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private FileExplorePanel fileExplorePanel;
    private FileTreePanel fileTreepanel;
    private JSplitPane jSplitPane;

    public MainFrame() {
        fileTreepanel = new FileTreePanel(this);
        fileExplorePanel = new FileExplorePanel(this);
        initializeComponents();
        setupLayout();
        configureFrame();
        setupEventListeners();
    }

    private void initializeComponents() {

        //TODO: Set min size for each pane
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileTreepanel, fileExplorePanel);
        jSplitPane.setDividerLocation(350);
    }

    //setting layout
    private void setupLayout() {

        setLayout(new BorderLayout());

        add(jSplitPane, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }

    private void configureFrame() {
        setTitle("File Explorer");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
    }

    public FileTreePanel getFileTreepanel() {
        return fileTreepanel;
    }

    public FileExplorePanel getFileExplorePanel() {
        return fileExplorePanel;
    }

    public void refresh() {
        fileTreepanel.refresh();
//        fileExplorePanel.refresh();
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
}
