package gui;

import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SettingsPanel extends JPanel {
    private ManagerFactory managerFactory;
    public JComboBox<String> lookAndFeelsBox;
    public JComboBox<String> languageBox;
    public JButton backBtn;
    private JFrame parent;


    public SettingsPanel(ManagerFactory managerFactory, JFrame parent) {
        this.managerFactory = managerFactory;
        this.parent = parent;
        this.setLayout(new MigLayout());
        this.lookAndFeelsBox = new JComboBox<>();
        this.languageBox = new JComboBox<>();
        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.lookAndFeelsBox.addItem("Nimbus");
        this.lookAndFeelsBox.addItem("Metal");
        this.lookAndFeelsBox.addItem("System default");

        this.languageBox.addItem("Serbian");
        this.languageBox.addItem("English");

        this.add(new JLabel("Style"), "split 2");
        this.add(this.lookAndFeelsBox, "wrap");
        this.add(new JLabel("Language"), "split 2");
        this.add(this.languageBox, "wrap");
        this.add(this.backBtn);

       this.lookAndFeelsBox.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               //System.out.println(e.getItem() + " " + e.getStateChange());
              // managerFactory.lookAndFeelManager.changeLookAndFeel()
               if(e.getStateChange() == ItemEvent.SELECTED)
                   managerFactory.lookAndFeelManager.changeLookAndFeel(parent, e.getItem().toString());
           }
       });

    }
}
