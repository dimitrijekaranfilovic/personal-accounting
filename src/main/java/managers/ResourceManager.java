package managers;

import javax.swing.*;


/**
 * Class that handles outside resources(such as icons).
 *
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 */

public class ResourceManager {
    public final ImageIcon okIcon;
    public final ImageIcon backIcon;
    public final ImageIcon addIcon;
    public final ImageIcon activitiesHistoryIcon;
    public final ImageIcon balancesHistoryIcon;
    public final ImageIcon settingsIcon;
    public final ImageIcon helpIcon;
    public final ImageIcon addCurrencyIcon;
    public final ImageIcon printIcon;
    public final ImageIcon nextIcon;
    public final ImageIcon moneyIcon;
    public final ImageIcon pieChartIcon;
    public final ImageIcon saveIcon;
    public final ImageIcon graphIcon;
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



    private ImageIcon loadIcon(String iconName) {
        return new ImageIcon(this.classLoader.getResource("icons/" + iconName));
    }
}
