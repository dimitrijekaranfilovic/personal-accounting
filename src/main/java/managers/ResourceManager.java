package managers;

import javax.swing.*;


/**
 * Class that handles outside resources(such as icons).
 *
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 */

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
    public ImageIcon pieChartIcon;
    public ImageIcon saveIcon;
    public ImageIcon graphIcon;
    private final ClassLoader classLoader;


    /**
     * Class constructor. Initializes all icons.
     */

    ResourceManager() {
        this.classLoader = this.getClass().getClassLoader();
        this.okIcon = loadIcon("accept.png");
        this.backIcon = loadIcon("skip-back.png");
        this.addIcon = loadIcon("plus-2.png");
        this.activitiesHistoryIcon = loadIcon("stock-market-analysis.png");
        this.balancesHistoryIcon = loadIcon("balance-2.png");
        this.settingsIcon = loadIcon("settings.png");
        this.helpIcon = loadIcon("help-2.png");
        this.addCurrencyIcon = loadIcon("currency-1.png");
        this.printIcon = loadIcon("printer-2.png");
        this.nextIcon = loadIcon("play.png");
        this.moneyIcon = loadIcon("wallet-1.png");
        this.pieChartIcon = loadIcon("piechart.png");
        this.saveIcon = loadIcon("save.png");
        this.graphIcon = loadIcon("graph.png");
    }


//    private ImageIcon loadIcon(String iconName) {
//        return new ImageIcon("icons/" + iconName);
//    }

    private ImageIcon loadIcon(String iconName) {
        return new ImageIcon(this.classLoader.getResource("icons/" + iconName));
    }
}
