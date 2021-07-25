package gui;

import managers.ManagerFactory;
import managers.ResourceManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

/**
 * Class that represents simple welcome screen.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

public class WelcomePanel extends JPanel {
        public final JButton nextBtn;
        private final ResourceManager resourceManager;

        public WelcomePanel() {
            this.resourceManager = ManagerFactory.createResourceManager();
            this.nextBtn = new JButton(this.resourceManager.nextIcon);

            JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);

            welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
            welcomeLabel.setFont(new FontUIResource(welcomeLabel.getFont().getName(), Font.BOLD, 16));
            this.add(welcomeLabel);

            String text = """
                    We've detected that application has not been configured.
                    You will have to enter at least one currency on the next\s
                    panel before actual usage.""";
            JTextArea field = new JTextArea();
            field.setText(text);
            field.setEditable(false);

            this.add(field, BorderLayout.CENTER);
            this.nextBtn.setHorizontalAlignment(JButton.CENTER);
            this.add(this.nextBtn);
        }
}
