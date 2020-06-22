package managers;

import javax.swing.*;

public class ResourceManager {
    public ImageIcon okIcon;
    public ImageIcon backIcon;
    public ImageIcon addIcon;
    public ImageIcon activitiesHistoryIcon;
    public ImageIcon balancesHistoryIcon;
    public ImageIcon settingsIcon;
    public ImageIcon helpIcon;
    public ImageIcon addCurrencyIcon;
    public ImageIcon printIcon;
    public ImageIcon nextIcon;
    public ImageIcon moneyIcon;

    ResourceManager(){
        this.okIcon = new ImageIcon("resources/accept.png");
        this.backIcon = new ImageIcon("resources/skip-back.png");
        this.addIcon = new ImageIcon("resources/plus-2.png");
        this.activitiesHistoryIcon = new ImageIcon("resources/stock-market-analysis.png");
        this.balancesHistoryIcon = new ImageIcon("resources/balance-2.png");
        this.settingsIcon = new ImageIcon("resources/settings.png");
        this.helpIcon = new ImageIcon("resources/help-2.png");
        this.addCurrencyIcon = new ImageIcon("resources/currency-1.png");
        this.printIcon = new ImageIcon("resources/printer-2.png");
        this.nextIcon = new ImageIcon("resources/play.png");
        this.moneyIcon = new ImageIcon("resources/wallet-1.png");
    }
}
