package org.filexplorer;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

         //Ensure GUI is created on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Failed to start File Explorer: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

//        File[] roots = File.listRoots();
//        Arrays.stream(roots).forEach(file -> {
//            System.out.print(file.listFiles().toString());
//        });
//        System.out.println(String.format("Roots: [%s]", roots));
    }
}