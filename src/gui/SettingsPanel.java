package gui;

import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SettingsPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    public JComboBox<String> lookAndFeelsBox;
    public JComboBox<String> languageBox;
    public JButton backBtn, okBtn;
    private JFrame parent;
    private JLabel styleLabel, languageLabel;
    private String serbian, english;

    //private JRadioButton buttons;
    private ButtonGroup buttonGroup;
    //private JRadioButton english, serbian, mne, de, gre;



    public SettingsPanel(ManagerFactory managerFactory, JFrame parent){
        this.managerFactory = managerFactory;
        this.parent = parent;
        this.setLayout(new MigLayout());
        this.lookAndFeelsBox = new JComboBox<>();
        this.languageBox = new JComboBox<>();
        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);
        this.okBtn = new JButton(this.managerFactory.resourceManager.okIcon);
        this.managerFactory.settingsManager.addObserver(this);

        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("nimbus"));
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("metal"));
        this.lookAndFeelsBox.addItem(this.managerFactory.settingsManager.getWord("system_default"));

        //this.serbian = this.managerFactory.settingsManager.getWord("serbian");
        //this.english = this.managerFactory.settingsManager.getWord("english");
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("serbian"));
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("english"));
        this.languageBox.addItem(this.managerFactory.settingsManager.getWord("serbian_cyrillic"));

        this.styleLabel = new JLabel(this.managerFactory.settingsManager.getWord("style"));
        this.languageLabel = new JLabel(this.managerFactory.settingsManager.getWord("language"));

        /*this.serbian = new JRadioButton(this.managerFactory.settingsManager.getWord("serbian"));
        this.english = new JRadioButton(this.managerFactory.settingsManager.getWord("english"));
        this.mne = new JRadioButton("Crna Gora");
        this.de = new JRadioButton("Njemacka");
        this.gre = new JRadioButton("Grcka");

        this.buttonGroup = new ButtonGroup();
        this.buttonGroup.add(this.serbian);
        this.buttonGroup.add(this.english);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.add(this.serbian);
        buttonPanel.add(this.english);
        buttonPanel.add(this.mne);
        buttonPanel.add(this.de);
        buttonPanel.add(this.gre);*/

        //JScrollPane scrollPane = new JScrollPane();
        //scrollPane.setSize(this.getWidth() + 20, this.getHeight() / 2);



        this.add(this.styleLabel, "split 2");
        this.add(this.lookAndFeelsBox, "wrap");
        this.add(this.languageLabel, "split 2");
        this.add(this.languageBox, "wrap");
        this.add(this.backBtn, "split 2");
        this.add(this.okBtn);

        this.okBtn.addActionListener(ae->{
            managerFactory.lookAndFeelManager.changeLookAndFeel(parent, lookAndFeelsBox.getSelectedItem().toString());
            managerFactory.settingsManager.style = lookAndFeelsBox.getSelectedItem().toString();
            //System.out.println("Izabrani jezik: " + languageBox.getSelectedItem().toString());
            managerFactory.settingsManager.updateLocale(languageBox.getSelectedItem().toString());


        });


       /*this.lookAndFeelsBox.addItemListener(new ItemListener() {
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
               //if(e.getStateChange() == ItemEvent.)

               if(e.getStateChange() == ItemEvent.SELECTED){
                   managerFactory.settingsManager.updateLocale(e.getItem().toString());
               }
           }
       });*/


       //this.languageBox.addItemListener(ie->managerFactory.settingsManager.updateLocale(languageBox.getSelectedItem().toString()));



    }

    @Override
    public void updatePerformed(UpdateEvent e) {
            this.styleLabel.setText(managerFactory.settingsManager.getWord("style"));
            this.languageLabel.setText(managerFactory.settingsManager.getWord("language"));
            //System.out.println("Mijenjeam languageLabel u: " + managerFactory.settingsManager.getWord("language"));
            this.languageBox.removeAllItems();
            //this.lookAndFeelsBox.removeAllItems();
            //System.out.println("USAO U SETTINGS LISTENER!");
            this.serbian = managerFactory.settingsManager.getWord("serbian");
            this.english = managerFactory.settingsManager.getWord("english");

            //adding available languages
            this.languageBox.addItem(managerFactory.settingsManager.getWord("serbian"));
            this.languageBox.addItem(managerFactory.settingsManager.getWord("english"));
            this.languageBox.addItem(managerFactory.settingsManager.getWord("serbian_cyrillic"));

            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("nimbus"));
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("metal"));
            //this.lookAndFeelsBox.addItem(managerFactory.settingsManager.getWord("system_default"));


    }
}
