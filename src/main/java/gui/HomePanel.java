package gui;

import display.Display;
import event.Observer;
import event.UpdateEvent;
import managers.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents home panel, from which other activities are launched.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class HomePanel extends JPanel implements Observer {
    /**
     * available currencies
     * */
    private ArrayList<String> currencies;
    private final ResourceManager resourceManager;
    private final SettingsManager settingsManager;
    private final CurrencyManager currencyManager;
    private final ActivityManager activityManager;
    private final BalanceManager balanceManager;
    public final JButton addActivityBtn;
    public final JButton balancesHistoryBtn;
    public final JButton activitiesHistoryBtn;
    public final JButton addCurrencyButton;
    public final JButton settingsButton;
    public final JButton helpBtn;
    public final JComboBox<String> currenciesBox;
    public final JTextField balanceField;
    public final HashMap<String, Integer> currencyValueMap;
    private final JEditorPane editorPane;

    public HomePanel() throws SQLException, ClassNotFoundException {
        this.currencyValueMap = new HashMap<>();
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.currencyManager = ManagerFactory.createCurrencyManager();
        this.activityManager = ManagerFactory.createActivityManager();
        this.balanceManager = ManagerFactory.createBalanceManager();

        this.addActivityBtn = new JButton(this.resourceManager.addIcon);
        this.balancesHistoryBtn = new JButton(this.resourceManager.balancesHistoryIcon);
        this.activitiesHistoryBtn = new JButton(this.resourceManager.activitiesHistoryIcon);
        this.addCurrencyButton = new JButton(this.resourceManager.addCurrencyIcon);
        this.settingsButton = new JButton(this.resourceManager.settingsIcon);
        this.helpBtn = new JButton(this.resourceManager.helpIcon);
        this.editorPane = setUpEditorPane();

        this.addActivityBtn.setToolTipText(this.settingsManager.getWord("add_activity"));
        this.activitiesHistoryBtn.setToolTipText(this.settingsManager.getWord("activities_history"));
        this.addCurrencyButton.setToolTipText(this.settingsManager.getWord("add_currency"));
        this.balancesHistoryBtn.setToolTipText(this.settingsManager.getWord("balances_history"));
        this.settingsButton.setToolTipText(this.settingsManager.getWord("settings"));
        this.helpBtn.setToolTipText(this.settingsManager.getWord("help"));

        this.currencies = this.currencyManager.getCurrencies();
        this.currencyManager.addObserver(this);
        this.activityManager.addObserver(this);
        this.settingsManager.addObserver(this);

        this.balanceField = new JTextField(50);
        Dimension fieldDimension = new Dimension(90, 30);
        this.balanceField.setMinimumSize(fieldDimension);
        this.balanceField.setMaximumSize(fieldDimension);

        this.currenciesBox = new JComboBox<>();
        for(String s : this.currencies){
            this.currenciesBox.addItem(s);
            this.currencyValueMap.put(s, this.balanceManager.getLatestBalance(s));
        }
        if(this.currencies.size() > 0){
            balanceField.setText(Display.amountDisplay(this.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        this.balanceField.setEditable(false);

        this.currenciesBox.addItemListener(ie->{
            String currency = (String)ie.getItem();
            this.balanceField.setText(Display.amountDisplay(this.currencyValueMap.get(currency)));

        });
        helpBtn.addActionListener(ae-> JOptionPane.showMessageDialog(null, editorPane, this.settingsManager.getWord("help"), JOptionPane.INFORMATION_MESSAGE));

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
            this.currencies = this.currencyManager.getCurrencies();
            for(String s : currencies){
                this.currencyValueMap.put(s, this.balanceManager.getLatestBalance(s));
                this.currenciesBox.addItem(s);
            }
            balanceField.setText(Display.amountDisplay(this.balanceManager.getLatestBalance(this.currencies.get(0))));
        }
        else if(e.getSource() instanceof ActivityManager am){
            int newValue = 0;
                if(am.activity.getActivityVersion().equalsIgnoreCase("-")){
                    newValue = this.currencyValueMap.get(am.activity.getCurrency()) - am.activity.getAmount();
                }
                else if(am.activity.getActivityVersion().equalsIgnoreCase("+")){
                    newValue = this.currencyValueMap.get(am.activity.getCurrency()) + am.activity.getAmount();
                }
                this.currencyValueMap.put(am.activity.getCurrency(), newValue);
                //TODO: pazi na sledece 2 linije
                //this.managerFactory.balanceManager.addBalance(am.activity.getCurrency(), newValue);
                this.balanceManager.updateCurrentBalance(am.activity.getCurrency(), newValue);
                int balance = this.currencyValueMap.get((String)currenciesBox.getSelectedItem());
                this.balanceField.setText(Display.amountDisplay(balance));
        }

        else if(e.getSource() instanceof SettingsManager){
            this.addActivityBtn.setToolTipText(this.settingsManager.getWord("add_activity"));
            this.activitiesHistoryBtn.setToolTipText(this.settingsManager.getWord("activities_history"));
            this.addCurrencyButton.setToolTipText(this.settingsManager.getWord("add_currency"));
            this.balancesHistoryBtn.setToolTipText(this.settingsManager.getWord("balances_history"));
            this.settingsButton.setToolTipText(this.settingsManager.getWord("settings"));
            this.helpBtn.setToolTipText(this.settingsManager.getWord("help"));
        }
    }

    /**
     * Function that sets up editorPane used in help button's JOptionPane.
     * @return editorPane with HyperLinkListener.
     * */
    //TODO: stavi u properties ovaj tekst koji ide uz link
    private JEditorPane setUpEditorPane(){
        Font font = this.helpBtn.getFont();
        String style = "font-family:" + font.getFamily() + ";" + "font-weight:" + (font.isBold() ? "bold" : "normal") + ";" +
                "font-size:" + font.getSize() + "pt;";
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
