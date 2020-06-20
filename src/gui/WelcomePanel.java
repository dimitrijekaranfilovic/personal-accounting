package gui;

import managers.ManagerFactory;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class WelcomePanel extends JPanel {
        public JButton nextBtn;
        private ManagerFactory managerFactory;

        public WelcomePanel(ManagerFactory managerFactory)
        {
            //this.setLayout(new MigLayout());
            this.managerFactory = managerFactory;
            this.nextBtn = new JButton(this.managerFactory.resourceManager.nextIcon);

            JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);

            //this.add(new JLabel(), "split 3");
            welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
            welcomeLabel.setFont(new FontUIResource(welcomeLabel.getFont().getName(), Font.BOLD, 16));
            this.add(welcomeLabel);
            //this.add(new JLabel(), "wrap");


            String text = "We've detected that application has not been " +
                    "configured.\nYou will have to enter at least one currency " +
                    "on the next \npanel before actual usage.";
            JTextArea field = new JTextArea();
            field.setText(text);
            field.setEditable(false);
           // JLabel textLabel = new JLabel("<html>" + text1 + "<br/>" + text2 + "</html>");
            //this.add(new JLabel(), "wrap");
            this.add(field, BorderLayout.CENTER);
          //  this.add(new JLabel(), "split 3");
            this.nextBtn.setHorizontalAlignment(JButton.CENTER);
            this.add(this.nextBtn);
           // this.add(new JLabel(), "wrap");


        }


}
