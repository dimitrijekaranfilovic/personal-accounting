package managers;

import javax.swing.*;
import java.awt.*;

/**
 * Class that handles LookAndFeel and dimension change.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class LookAndFeelManager {
    /**
     * home panel dimension
     * */
    public Dimension homeDimension;
    /**
     * add activity panel dimension
     * */
    public Dimension addActivityDimension;
    /**
     * activity history panel dimension
     * */
    public Dimension activityHistoryDimension;
    /**
     * balance panel dimension
     * */
    public Dimension balanceHistoryDimension;
    /**
     * add currency panel dimension
     * */
    public Dimension addCurrencyDimension;
    /**
     * display balances panel dimension
     * */
    public Dimension displayBalancesDimension;
    /**
     * display activities dimension
     * */
    public Dimension displayActivitiesDimension;
    /**
     * welcome panel dimension
     * */
    public Dimension welcomeDimension;
    /**
     * settings panel dimension
     * */
    public Dimension settingsDimension;


    public Dimension pieChartDimension;
    public Dimension balancesGraphDimenion;

    /**
     * Class constructor. Initializes default dimension values.
     * */

    LookAndFeelManager(){
        this.homeDimension = new Dimension(180,150);
        this.addActivityDimension = new Dimension(330, 270);
        this.activityHistoryDimension = new Dimension(270, 370);
        this.balanceHistoryDimension = new Dimension(270, 250);
        this.addCurrencyDimension = new Dimension(350, 150);
        this.displayBalancesDimension = new Dimension(400, 300);
        this.displayActivitiesDimension = new Dimension(500, 400);
        this.welcomeDimension = new Dimension(380, 180);
        this.settingsDimension = new Dimension(200, 150);
        this.pieChartDimension = new Dimension(820, 500);
        this.balancesGraphDimenion = new Dimension(840, 510);

    }

    /**
     * Function that updates a dimension.
     * @param d  dimension to be updated
     * @param width  new dimension width
     * @param height  new dimension height
     * */

    private void updateDimension(Dimension d, int width, int height){
        d.width = width;
        d.height = height;
    }


    /**
     * Function that changes LookAndFeel and updates dimensions.
     * @param frame   frame to be updated
     * @param name  LookAndFeel name
     * @return indicator whether the change was successful
     * */
    public boolean changeLookAndFeel(JFrame frame, String name){
        try{
            if(name.equalsIgnoreCase("nimbus"))
            {

                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                updateDimension(this.homeDimension, 180, 150); //width 180
                updateDimension(this.addActivityDimension, 330, 270);
                updateDimension(this.activityHistoryDimension, 270, 370);
                updateDimension(this.balanceHistoryDimension, 270, 250);
                updateDimension(this.addCurrencyDimension, 350, 150);
                updateDimension(this.displayBalancesDimension, 400, 300);
                updateDimension(this.displayActivitiesDimension, 500, 400);
                updateDimension(this.welcomeDimension, 380, 180);
                updateDimension(this.settingsDimension, 200, 150);
                updateDimension(this.pieChartDimension, 820, 510);
                updateDimension(this.balancesGraphDimenion, 840, 510);

            }
            else if(name.equalsIgnoreCase("metal")){

                UIManager.setLookAndFeel(UIManager
                        .getCrossPlatformLookAndFeelClassName());
                updateDimension(this.homeDimension, 190, 150); //width 180
                updateDimension(this.addActivityDimension, 330, 270);
                updateDimension(this.activityHistoryDimension, 270, 350);
                updateDimension(this.balanceHistoryDimension, 270, 250);
                updateDimension(this.addCurrencyDimension, 350, 150);
                updateDimension(this.displayBalancesDimension, 400, 300);
                updateDimension(this.displayActivitiesDimension, 500, 400);
                updateDimension(this.welcomeDimension, 380, 180);
                updateDimension(this.settingsDimension, 200, 150);
                updateDimension(this.pieChartDimension, 820, 500);
                updateDimension(this.balancesGraphDimenion, 840, 510);


            }
            else if(name.equalsIgnoreCase("system default")) {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
                if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
                    updateDimension(this.homeDimension, 180, 150); //width 180
                    updateDimension(this.addActivityDimension, 310, 280);
                    updateDimension(this.activityHistoryDimension, 270, 380);
                    updateDimension(this.balanceHistoryDimension, 270, 270);
                    updateDimension(this.addCurrencyDimension, 300, 150);
                    updateDimension(this.displayBalancesDimension, 400, 300);
                    updateDimension(this.displayActivitiesDimension, 500, 400);
                    updateDimension(this.welcomeDimension, 380, 180);
                    updateDimension(this.settingsDimension, 210, 142);
                    updateDimension(this.pieChartDimension, 820, 505);
                    updateDimension(this.balancesGraphDimenion, 840, 510);


                } else {
                    updateDimension(this.homeDimension, 180, 150); //width 180
                    updateDimension(this.addActivityDimension, 310, 260);
                    updateDimension(this.activityHistoryDimension, 270, 330);
                    updateDimension(this.balanceHistoryDimension, 270, 250);
                    updateDimension(this.addCurrencyDimension, 300, 150);
                    updateDimension(this.displayBalancesDimension, 400, 300);
                    updateDimension(this.displayActivitiesDimension, 500, 400);
                    updateDimension(this.welcomeDimension, 380, 180);
                    updateDimension(this.settingsDimension, 200, 130);
                    updateDimension(this.pieChartDimension, 820, 500);
                    updateDimension(this.balancesGraphDimenion, 840, 510);


                }
            }
            else if(name.equalsIgnoreCase("mcwin")){
                UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
                updateDimension(this.homeDimension, 180, 130); //width 180
                updateDimension(this.addActivityDimension, 310, 260);
                updateDimension(this.activityHistoryDimension, 270, 330);
                updateDimension(this.balanceHistoryDimension, 270, 250);
                updateDimension(this.addCurrencyDimension, 300, 150);
                updateDimension(this.displayBalancesDimension, 400, 300);
                updateDimension(this.displayActivitiesDimension, 500, 400);
                updateDimension(this.welcomeDimension, 380, 180);
                updateDimension(this.settingsDimension, 200, 130);
                updateDimension(this.pieChartDimension, 820, 500);
                updateDimension(this.balancesGraphDimenion, 840, 510);


            }
        }
        catch(ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e){
            return false;
        }
        SwingUtilities.updateComponentTreeUI(frame);
        return true;
    }
}
