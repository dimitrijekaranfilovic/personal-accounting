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
    private JLabel styleLabel, languageLabel;


    public SettingsPanel(ManagerFactory managerFactory, JFrame parent) {
        this.managerFactory = managerFactory;
        this.parent = parent;
        this.setLayout(new MigLayout());
        this.lookAndFeelsBox = new JComboBox<>();
        this.languageBox = new JComboBox<>();
        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);

        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("nimbus"));
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("metal"));
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("system_default"));

        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("serbian"));
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("english"));

        this.styleLabel = new JLabel(this.managerFactory.settingsManager.getWord("style"));
        this.languageLabel = new JLabel(this.managerFactory.settingsManager.getWord("language"));

        this.add(this.styleLabel, "split 2");
        this.add(this.lookAndFeelsBox, "wrap");
        this.add(this.languageLabel, "split 2");
        this.add(this.languageBox, "wrap");
        this.add(this.backBtn);

       this.lookAndFeelsBox.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               //System.out.println(e.getItem() + " " + e.getStateChange());
              // managerFactory.lookAndFeelManager.changeLookAndFeel()
               if(e.getStateChange() == ItemEvent.SELECTED){
                   managerFactory.lookAndFeelManager.changeLookAndFeel(parent, e.getItem().toString());
                   managerFactory.settingsManager.style = e.getItem().toString();
               }
           }
       });

       this.languageBox.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange() == ItemEvent.SELECTED){
                   managerFactory.settingsManager.updateLocale(e.getItem().toString());
               }
           }
       });

    }

}
