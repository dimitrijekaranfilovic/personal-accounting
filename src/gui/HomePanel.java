package gui;

import display.Display;
import event.Observer;
import event.UpdateEvent;
import managers.ActivityManager;
import managers.CurrencyManager;
import managers.ManagerFactory;
import managers.SettingsManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents home panel, from which other activities are launched.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class HomePanel extends JPanel implements Observer {
    private ManagerFactory managerFactory;
    /**
     * available currencies
     * */
    private ArrayList<String> currencies;
    public JButton addActivityBtn, balancesHistoryBtn, activitiesHistoryBtn, addCurrencyButton, settingsButton, helpBtn;
    public JComboBox<String> currenciesBox;
    public JTextField balanceField;
    public HashMap<String, Integer> currencyValueMap;
    private JEditorPane editorPane;

    public HomePanel(ManagerFactory managerFactory){
        this.currencyValueMap = new HashMap<>();
        this.managerFactory = managerFactory;

        this.addActivityBtn = new JButton(this.managerFactory.resourceManager.addIcon);
        this.balancesHistoryBtn = new JButton(this.managerFactory.resourceManager.balancesHistoryIcon);
        this.activitiesHistoryBtn = new JButton(this.managerFactory.resourceManager.activitiesHistoryIcon);
        this.addCurrencyButton = new JButton(this.managerFactory.resourceManager.addCurrencyIcon);
        this.settingsButton = new JButton(this.managerFactory.resourceManager.settingsIcon);
        this.helpBtn = new JButton(this.managerFactory.resourceManager.helpIcon);
        this.editorPane = setUpEditorPane();

        this.addActivityBtn.setToolTipText(this.managerFactory.settingsManager.getWord("add_activity"));
        this.activitiesHistoryBtn.setToolTipText(this.managerFactory.settingsManager.getWord("activities_history"));
        this.addCurrencyButton.setToolTipText(this.managerFactory.settingsManager.getWord("add_currency"));
        this.balancesHistoryBtn.setToolTipText(this.managerFactory.settingsManager.getWord("balances_history"));
        this.settingsButton.setToolTipText(this.managerFactory.settingsManager.getWord("settings"));
        this.helpBtn.setToolTipText(this.managerFactory.settingsManager.getWord("help"));

        this.currencies = this.managerFactory.currencyManager.getCurrencies();
        this.managerFactory.currencyManager.addObserver(this);
        this.managerFactory.activityManager.addObserver(this);
        this.managerFactory.settingsManager.addObserver(this);

        this.balanceField = new JTextField(50);
        Dimension fieldDimension = new Dimension(90, 30);
        this.balanceField.setMinimumSize(fieldDimension);
        this.balanceField.setMaximumSize(fieldDimension);

        this.currenciesBox = new JComboBox<>();
        for(String s : this.currencies){
            this.currenciesBox.addItem(s);
            this.currencyValueMap.put(s, this.managerFactory.balanceManager.getLatestBalance(s));
        }
        if(this.currencies.size() > 0){
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        this.balanceField.setEditable(false);

        this.currenciesBox.addItemListener(ie->{
            String currency = (String)ie.getItem();
            this.balanceField.setText(Display.amountDisplay(this.currencyValueMap.get(currency)));

        });
        helpBtn.addActionListener(ae-> JOptionPane.showMessageDialog(null, editorPane, this.managerFactory.settingsManager.getWord("help"), JOptionPane.INFORMATION_MESSAGE));

        this.setLayout(new MigLayout());

        this.add(this.balanceField, "split 2");
        this.add(this.currenciesBox, "wrap");

        this.add(this.addActivityBtn, "split 3");
        this.add(this.activitiesHistoryBtn);
        this.add(this.balancesHistoryBtn, "wrap");

        this.add(this.addCurrencyButton, "split 3");
        this.add(this.settingsButton);
        this.add(helpBtn, "wrap");
        this.setPreferredSize(this.getPreferredSize());


    }

    /**
     * Function that needs to be defined after implementing the {@link event.Observer} interface.
     * @param e if the event's source is {@link managers.CurrencyManager},
     * currency box's content is updated. If the source is {@link managers.ActivityManager},
     *  current balance display is updated. If the source is {@link managers.SettingsManager},
     *  buttons' tooltips are updated according to the specified language.
     *
     * */
    @Override
    public void updatePerformed(UpdateEvent e) {
        if(e.getSource() instanceof CurrencyManager){
            this.currenciesBox.removeAllItems();
            this.currencies = this.managerFactory.currencyManager.getCurrencies();
            for(String s : currencies){
                this.currencyValueMap.put(s, this.managerFactory.balanceManager.getLatestBalance(s));
                this.currenciesBox.addItem(s);
            }
            balanceField.setText(Display.amountDisplay(this.managerFactory.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        else if(e.getSource() instanceof ActivityManager){
                ActivityManager am = (ActivityManager)e.getSource();
                int newValue = 0;
                if(am.activity.getActivityVersion().equalsIgnoreCase("-")){
                    newValue = this.currencyValueMap.get(am.activity.getCurrency()) - am.activity.getAmount();
                }
                else if(am.activity.getActivityVersion().equalsIgnoreCase("+")){
                    newValue = this.currencyValueMap.get(am.activity.getCurrency()) + am.activity.getAmount();
                }
                this.currencyValueMap.put(am.activity.getCurrency(), newValue);
                //TODO: pazi na sledece 2 linije
                this.managerFactory.balanceManager.addBalance(am.activity.getCurrency(), newValue);
                this.managerFactory.balanceManager.updateCurrentBalance(am.activity.getCurrency(), newValue);
                int balance = this.currencyValueMap.get((String)currenciesBox.getSelectedItem());
                this.balanceField.setText(Display.amountDisplay(balance));
        }

        else if(e.getSource() instanceof SettingsManager){
            this.addActivityBtn.setToolTipText(this.managerFactory.settingsManager.getWord("add_activity"));
            this.activitiesHistoryBtn.setToolTipText(this.managerFactory.settingsManager.getWord("activities_history"));
            this.addCurrencyButton.setToolTipText(this.managerFactory.settingsManager.getWord("add_currency"));
            this.balancesHistoryBtn.setToolTipText(this.managerFactory.settingsManager.getWord("balances_history"));
            this.settingsButton.setToolTipText(this.managerFactory.settingsManager.getWord("settings"));
            this.helpBtn.setToolTipText(this.managerFactory.settingsManager.getWord("help"));
        }
    }

    /**
     * Function that sets up editorPane used in help button's JOptionPane.
     * @return editorPane with HyperLinkListener.
     * */
    //TODO: stavi u properties ovaj tekst koji ide uz link
    private JEditorPane setUpEditorPane(){
        Font font = this.helpBtn.getFont();
        StringBuilder style = new StringBuilder("font-family:" + font.getFamily() + ";");
        style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
        style.append("font-size:" + font.getSize() + "pt;");
        JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">" //
                + "If you have any difficulties, visit <a href=\"https://github.com/dimitrijekaranfilovic/personal-accounting/\"> this page.</a><br>" +
                "Dimitrije Karanfilovic 2020" //
                + "</body></html>");
        ep.addHyperlinkListener(he->{
            if(he.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
                openWebpage(he.getURL());
        });
        ep.setEditable(false);
        return ep;
    }


    /**
     * Function that tries to open the uri passed from {@link gui.HomePanel#openWebpage(URL)} function.
     * @param uri URI
     * @return indicator whether the uri was opened successfully.
     * */
    private boolean openWebpage(URI uri){
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Function that tries to open url.
     * @param url url passed from JEditorPane's HyperLinkListener
     * @return indicator whether the url was opened successfully.
     * */
    private boolean openWebpage(URL url){
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
