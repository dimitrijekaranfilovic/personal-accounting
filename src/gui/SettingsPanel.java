package gui;

import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Class that represents a panel used for changing app's settings, such as
 * LookAndFeel and language.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public class SettingsPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    public JComboBox<String> lookAndFeelsBox, languageBox;
    public JButton backBtn, okBtn;
    private JFrame parent;
    private JLabel styleLabel, languageLabel;

    public SettingsPanel(ManagerFactory managerFactory, JFrame parent){
        this.managerFactory = managerFactory;
        this.parent = parent;
        this.setLayout(new MigLayout());
        this.lookAndFeelsBox = new JComboBox<>();
        this.languageBox = new JComboBox<>();
        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);
        this.okBtn = new JButton(this.managerFactory.resourceManager.okIcon);
        this.managerFactory.settingsManager.addObserver(this);

        //adding all available lookAndFeels
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("nimbus"));
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("metal"));
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("system_default"));
        //this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("mcwin"));

        //adding all available languages
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("serbian"));
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("english"));
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("serbian_cyrillic"));

        this.styleLabel = new JLabel(this.managerFactory.settingsManager.getWord("style"));
        this.languageLabel = new JLabel(this.managerFactory.settingsManager.getWord("language"));


        this.add(this.styleLabel, "split 2");
        this.add(this.lookAndFeelsBox, "wrap");
        this.add(this.languageLabel, "split 2");
        this.add(this.languageBox, "wrap");
        this.add(this.backBtn, "split 2");
        this.add(this.okBtn);

        //after okBtn is clicked, parameters specified in combo boxes are used and changes are applied
        this.okBtn.addActionListener(ae->{
            managerFactory.lookAndFeelManager.changeLookAndFeel(parent, lookAndFeelsBox.getSelectedItem().toString());
            managerFactory.settingsManager.style = lookAndFeelsBox.getSelectedItem().toString();
            managerFactory.settingsManager.updateLocale(languageBox.getSelectedItem().toString());
        });
    }

    /**
     * Function that need to be defined after implementing {@link event.Observer} interface.
     * @param e UpdateEvent : event's source is {@link managers.SettingsManager}. Text inside labels
     * is changed according to specified language.
     * */
    @Override
    public void updatePerformed(UpdateEvent e) {
            this.styleLabel.setText(managerFactory.settingsManager.getWord("style"));
            this.languageLabel.setText(managerFactory.settingsManager.getWord("language"));
            this.languageBox.removeAllItems();

            //adding all available languages
            this.languageBox.addItem(managerFactory.settingsManager.getWord("serbian"));
            this.languageBox.addItem(managerFactory.settingsManager.getWord("english"));
            this.languageBox.addItem(managerFactory.settingsManager.getWord("serbian_cyrillic"));

            //this.lookAndFeelsBox.removeAllItems();
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("nimbus"));
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("metal"));
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("system_default"));
    }
}
