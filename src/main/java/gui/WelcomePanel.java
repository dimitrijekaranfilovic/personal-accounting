package gui;

import managers.ManagerFactory;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

/**
 * Class that represents simple welcome screen.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public class WelcomePanel extends JPanel {
        public JButton nextBtn;
        private ManagerFactory managerFactory;

        public WelcomePanel(ManagerFactory managerFactory) {
            this.managerFactory = managerFactory;
            this.nextBtn = new JButton(this.managerFactory.resourceManager.nextIcon);

            JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);

            welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
            welcomeLabel.setFont(new FontUIResource(welcomeLabel.getFont().getName(), Font.BOLD, 16));
            this.add(welcomeLabel);

            String text = "We've detected that application has not been " +
                    "configured.\nYou will have to enter at least one currency " +
                    "on the next \npanel before actual usage.";
            JTextArea field = new JTextArea();
            field.setText(text);
            field.setEditable(false);

            this.add(field, BorderLayout.CENTER);
            this.nextBtn.setHorizontalAlignment(JButton.CENTER);
            this.add(this.nextBtn);
        }
}
