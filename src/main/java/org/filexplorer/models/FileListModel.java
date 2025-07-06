package org.filexplorer.models;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class FileListModel extends AbstractTableModel {

    private final String[] columns = {"Name", "Size", "Type", "Date Modified"};
    private File[] files = new File[0];

    public void setFiles(File[] files) {
        this.files = Objects.nonNull(files) ? files : new File[0];

        Arrays.sort(files, (a, b) -> {
            if(a.isDirectory() && !b.isDirectory()) return -1;
            if(!a.isDirectory() && b.isDirectory()) return 1;

            return a.getName().compareToIgnoreCase(b.getName());
        });
    }

    @Override
    public int getRowCount() {
        return files.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int x, int y) {
        File file = files[x];

        switch(y) {
            case 0: return file;
            case 1: return file.isDirectory() ? 0 : file.length();
            case 2: return file.isDirectory() ? "Folder" : getFileType(file);
            case 3: return new Date(file.lastModified());
            default: return "";
        }
    }

    public File getFileAt(int row) {
        return files[row];
    }

    private String getFileType(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot > 0 && lastDot < name.length() - 1) {
            return name.substring(lastDot + 1).toUpperCase() + " File";
        }
        return "File";
    }
}
