package gui;

import javax.swing.*;
import java.awt.*;

public class ChooseFolderPanel extends JPanel {

    public String path;

    public ChooseFolderPanel(JFrame parent , String title) {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.path = chooser.getSelectedFile().getAbsolutePath();
        }
    }
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
}