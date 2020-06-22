package managers;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelManager {
    public Dimension homeDimension;
    public Dimension addActivityDimension;
    public Dimension activityHistoryDimension;
    public Dimension balanceHistoryDimension;
    public Dimension addCurrencyDimension;
    public Dimension displayBalancesDimension;
    public Dimension displayActivitiesDimension;
    public Dimension welcomeDimension;
    public Dimension settingsDimension;

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

    }

    private void updateDimension(Dimension d, int width, int height){
        d.width = width;
        d.height = height;
    }

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
            }
            else if(name.equalsIgnoreCase("system default")){
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
                updateDimension(this.homeDimension, 180, 150); //width 180
                updateDimension(this.addActivityDimension, 310, 260);
                updateDimension(this.activityHistoryDimension, 270, 330);
                updateDimension(this.balanceHistoryDimension, 270, 250);
                updateDimension(this.addCurrencyDimension, 300, 150);
                updateDimension(this.displayBalancesDimension, 400, 300);
                updateDimension(this.displayActivitiesDimension, 500, 400);
                updateDimension(this.welcomeDimension, 380, 180);
                updateDimension(this.settingsDimension, 200, 130);
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
