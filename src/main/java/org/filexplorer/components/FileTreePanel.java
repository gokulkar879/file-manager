package org.filexplorer.components;

import org.filexplorer.MainFrame;
import org.filexplorer.models.FileNode;
import org.filexplorer.models.FileTreeModel;
import org.filexplorer.models.TreeModel;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class FileTreePanel extends JPanel {

    private MainFrame mainFrame;
    private JTree fileTree;
    private JScrollPane scrollPane;
    private FileTreeModel fileTreeModel;

    public FileTreePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {

        File[] roots = File.listRoots();
        FileNode fileNode = new FileNode("Computer", null);
        for(File root: roots) {
            fileNode.add(new FileNode(root.getPath(), root));
        }

        //initialise the tree model
        fileTreeModel = new FileTreeModel(fileNode);

        fileTree = new JTree(fileTreeModel);
        fileTree.setRootVisible(true);
        fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        scrollPane = new JScrollPane(fileTree);
        scrollPane.setPreferredSize(new Dimension(250, 0));

        // Custom cell renderer for file icons
        fileTree.setCellRenderer(new FileTreeCellRenderer());

        // Expand root node by default
        fileTree.expandRow(0);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Folders"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {

        //adding the execution for expanding the nodes
        fileTree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                TreePath path = event.getPath();
                if(Objects.nonNull(path)) {
                    FileNode node = (FileNode) path.getLastPathComponent();
                    System.out.println(path);
                    if(!fileTree.isExpanded(path)) {
                        node.loadChildren();
                        fileTreeModel.reload(node);
                    }
                }
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                System.out.println("Folder closed");
            }
        });
    }

    public void refresh() {
        TreePath currentPath = fileTree.getSelectionPath();
        if(Objects.nonNull(currentPath)) {
            FileNode node = (FileNode) currentPath.getLastPathComponent();

            if(Objects.nonNull(node.getFile()) && node.getFile().isDirectory()) {
                node.refresh();
                fileTreeModel.nodeStructureChanged(node);
            }
        }
    }

    private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            if (value instanceof FileNode) {
                FileNode node = (FileNode) value;
                setText(node.getDisplayName());

                // Set appropriate icon
                if (node.getFile() == null) {
                    setIcon(UIManager.getIcon("FileView.computerIcon"));
                } else if (node.getFile().isDirectory()) {
                    setIcon(expanded ?
                            UIManager.getIcon("FileView.directoryIcon") :
                            UIManager.getIcon("FileView.directoryIcon"));
                } else {
                    setIcon(UIManager.getIcon("FileView.fileIcon"));
                }
            }

            return this;
        }
    }

}


