package managers;

import org.jfree.chart.JFreeChart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Class that handles outside resources(such as icons).
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */

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
    //private String basePath = "./src/icons/";
    private String basePath = "icons/";
    private ClassLoader classLoader;


    /**
     * Class constructor. Initializes all icons.
     * */

    ResourceManager(){
        this.classLoader = this.getClass().getClassLoader();
        this.okIcon = new ImageIcon(this.classLoader.getResource(basePath + "accept.png"));
        this.backIcon = new ImageIcon(this.classLoader.getResource(basePath + "skip-back.png"));
        this.addIcon = new ImageIcon(this.classLoader.getResource(basePath + "plus-2.png"));
        this.activitiesHistoryIcon = new ImageIcon(this.classLoader.getResource(basePath + "stock-market-analysis.png"));
        this.balancesHistoryIcon = new ImageIcon(this.classLoader.getResource(basePath + "balance-2.png"));
        this.settingsIcon = new ImageIcon(this.classLoader.getResource(basePath + "settings.png"));
        this.helpIcon = new ImageIcon(this.classLoader.getResource(basePath + "help-2.png"));
        this.addCurrencyIcon = new ImageIcon(this.classLoader.getResource(basePath + "currency-1.png"));
        this.printIcon = new ImageIcon(this.classLoader.getResource(basePath + "printer-2.png"));
        this.nextIcon = new ImageIcon(this.classLoader.getResource(basePath + "play.png"));
        this.moneyIcon = new ImageIcon(this.classLoader.getResource(basePath + "wallet-1.png"));
        this.pieChartIcon = new ImageIcon(this.classLoader.getResource(basePath + "piechart.png"));
        //this.saveIcon = new ImageIcon(this.classLoader.getResource(basePath  + "save.png"));
    }

    public void saveChart(JFreeChart chart, String path, String filename){
        BufferedImage objBufferedImage=chart.createBufferedImage(600,800);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteArray=bas.toByteArray();
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
            File outputFile = new File(path + System.getProperty("file.separator") + filename + ".png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
