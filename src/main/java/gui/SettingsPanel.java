package gui;

import event.Observer;
import event.UpdateEvent;
import managers.LookAndFeelManager;
import managers.ManagerFactory;
import managers.ResourceManager;
import managers.SettingsManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Class that represents a panel used for changing app's settings, such as
 * LookAndFeel and language.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public class SettingsPanel extends JPanel implements Observer {
    public final JComboBox<String> lookAndFeelsBox;
    public final JComboBox<String> languageBox;
    public final JButton backBtn;
    public final JButton okBtn;
    private final JLabel styleLabel, languageLabel;
    private final ResourceManager resourceManager;
    private final SettingsManager settingsManager;
    private final LookAndFeelManager lookAndFeelManager;

    public SettingsPanel(JFrame parent) throws SQLException, ClassNotFoundException {
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.lookAndFeelManager = ManagerFactory.createLookAndFeelManager();
        this.setLayout(new MigLayout());
        this.lookAndFeelsBox = new JComboBox<>();
        this.languageBox = new JComboBox<>();
        this.backBtn = new JButton(this.resourceManager.backIcon);
        this.okBtn = new JButton(this.resourceManager.okIcon);
        this.settingsManager.addObserver(this);

        //adding all available lookAndFeels
        this.lookAndFeelsBox.addItem(this.settingsManager.getWord("nimbus"));
        this.lookAndFeelsBox.addItem(this.settingsManager.getWord("metal"));
        this.lookAndFeelsBox.addItem(this.settingsManager.getWord("system_default"));
        this.lookAndFeelsBox.addItem(this.settingsManager.getWord("mcwin"));

        //adding all available languages
        this.languageBox.addItem(this.settingsManager.getWord("serbian"));
        this.languageBox.addItem(this.settingsManager.getWord("english"));
        this.languageBox.addItem(this.settingsManager.getWord("slovenian"));
        //this.languageBox.addItem(this.managerFactory.settingsManager.getWord("serbian_cyrillic"));

        this.styleLabel = new JLabel(this.settingsManager.getWord("style"));
        this.languageLabel = new JLabel(this.settingsManager.getWord("language"));


        this.add(this.styleLabel, "split 2");
        this.add(this.lookAndFeelsBox, "wrap");
        this.add(this.languageLabel, "split 2");
        this.add(this.languageBox, "wrap");
        this.add(this.backBtn, "split 2");
        this.add(this.okBtn);

        //after okBtn is clicked, parameters specified in combo boxes are used and changes are applied
        this.okBtn.addActionListener(ae->{
            lookAndFeelManager.changeLookAndFeel(parent, lookAndFeelsBox.getSelectedItem().toString());
            settingsManager.style = lookAndFeelsBox.getSelectedItem().toString();
            settingsManager.updateLocale(languageBox.getSelectedItem().toString());
        });
    }

    /**
     * Function that need to be defined after implementing {@link event.Observer} interface.
     * @param e event's source is {@link managers.SettingsManager}. Text inside labels
     * is changed according to specified language.
     * */
    @Override
    public void updatePerformed(UpdateEvent e) {
            this.styleLabel.setText(this.settingsManager.getWord("style"));
            this.languageLabel.setText(this.settingsManager.getWord("language"));
            this.languageBox.removeAllItems();

            //adding all available languages
            this.languageBox.addItem(this.settingsManager.getWord("serbian"));
            this.languageBox.addItem(this.settingsManager.getWord("english"));
            this.languageBox.addItem(this.settingsManager.getWord("slovenian"));
            //this.languageBox.addItem(managerFactory.settingsManager.getWord("serbian_cyrillic"));

            //this.lookAndFeelsBox.removeAllItems();
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("nimbus"));
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("metal"));
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("system_default"));
    }
}
