package org.filexplorer.models;

import javax.swing.tree.DefaultTreeModel;

public class FileTreeModel extends DefaultTreeModel {

    public FileTreeModel(FileNode root) {
        super(root);
    }

    @Override
    public Object getChild(Object parent, int index) {
        FileNode node = (FileNode) parent;
        return node.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        FileNode node = (FileNode) parent;
        return node.getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        FileNode fileNode = (FileNode) node;
        return fileNode.isLeaf();
    }
}
