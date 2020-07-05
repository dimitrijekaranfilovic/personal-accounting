package gui;

import managers.ManagerFactory;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;


public class ChooseFolderPanel extends JPanel {

    private JFrame parent;
    private JFileChooser chooser;
    public String path;

    public ChooseFolderPanel(JFrame parent, ManagerFactory managerFactory , String title, String filename, JFreeChart chart) {
       this.parent = parent;
   // }

    //public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            /*System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());*/
            this.path = chooser.getSelectedFile().getAbsolutePath();
            managerFactory.resourceManager.saveChart(chart, path, filename);
            ;
        } /*else {
            System.out.println("No Selection ");
        }*/
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
}