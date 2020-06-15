package gui;

import event.Observer;
import event.UpdateEvent;
import managers.ManagerFactory;

import javax.swing.*;

public class BalancesFilterPanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;


    @Override
    public void updatePerformed(UpdateEvent e) {

    }
}
