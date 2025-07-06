package org.filexplorer.models;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class FileNode extends DefaultMutableTreeNode {

    private File file;
    private String displayName;
    private boolean loaded;

    public FileNode(String displayName, File file) {
        this.file = file;
        this.displayName = displayName;
        this.loaded = false;
    }

    public File getFile() {
        return file;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean isLeaf() {
        return Objects.nonNull(file) && file.isFile();
    }

    @Override
    public String toString() {
        return displayName;
    }

    public void refresh() {
        if (loaded) {
            removeAllChildren();
            loaded = false;
            loadChildren();
        }
    }

    public void loadChildren() {
        if(!loaded && Objects.nonNull(file) && file.isDirectory()) {
            loaded = true;
            File[] children = file.listFiles();
            if(Objects.nonNull(children)) {
                Arrays.sort(children, (a, b) -> {
                    if(a.isDirectory() && !b.isDirectory()) return -1;
                    if(!a.isDirectory() && b.isDirectory()) return 1;
                    return a.getName().compareToIgnoreCase(b.getName());
                });

                for(File child: children) {
                    if(!child.isHidden()) {
                        add(new FileNode(child.getName(), child));
                    }
                }
            }
        }
    }
}
