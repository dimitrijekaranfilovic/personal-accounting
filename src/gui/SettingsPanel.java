package gui;

import managers.ManagerFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SettingsPanel extends JPanel {
    private ManagerFactory managerFactory;
    public JComboBox<String> lookAndFeelsBox;
    public JButton backBtn;
    private JFrame parent;


    public SettingsPanel(ManagerFactory managerFactory, JFrame parent){
        this.managerFactory = managerFactory;
        this.parent = parent;
        this.setLayout(new MigLayout());
        this.lookAndFeelsBox = new JComboBox<>();
        this.backBtn = new JButton(this.managerFactory.resourceManager.backIcon);
        this.lookAndFeelsBox.addItem("Nimbus");
        this.lookAndFeelsBox.addItem("Metal");
        this.lookAndFeelsBox.addItem("System default");

        this.add(new JLabel("Style"), "split 2");
        this.add(this.lookAndFeelsBox, "wrap");
        this.add(this.backBtn);

        this.lookAndFeelsBox.addItemListener(ie->
            this.managerFactory.lookAndFeelManager.changeLookAndFeel(this.parent, (String)this.lookAndFeelsBox.getSelectedItem()));





    }


}
