package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import display.Display;
import entities.Activity;
import managers.ManagerFactory;
import managers.ResourceManager;
import managers.SettingsManager;
import models.ActivityModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Class that represents panel used for displaying filtered activities.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class DisplayActivitiesPanel extends JPanel {
    /**
     * list of activities to be displayed
     * */
    private ArrayList<Activity> activities;
    private final JTable table;
    public final JButton backBtn;
    public final JButton printBtn;
    public final JButton pieBtn;
    private final ResourceManager resourceManager;
    private final SettingsManager settingsManager;

    public DisplayActivitiesPanel() throws SQLException, ClassNotFoundException {
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.activities = new ArrayList<>();
        this.table = new JTable(new ActivityModel(this.activities));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.backBtn = new JButton(this.resourceManager.backIcon);
        this.printBtn = new JButton(this.resourceManager.saveIcon);
        this.pieBtn = new JButton(this.resourceManager.pieChartIcon);

        this.setLayout(new BorderLayout());

        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);
        JPanel panel2 = new JPanel(new MigLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        panel2.add(this.backBtn, "split 3");
        panel2.add(this.pieBtn);
        panel2.add(this.printBtn);

        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        mainPanel.add(panel2);

        this.add(mainPanel, BorderLayout.SOUTH);


    }
    public boolean createTable(String path, String filename){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + System.getProperty("file.separator") + filename + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table);
            document.add(table);
            document.close();
            return true;
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addTableHeader(PdfPTable table){
        Stream.of(this.settingsManager.getWord("description"), this.settingsManager.getWord("amount"), this.settingsManager.getWord("currency"), this.settingsManager.getWord("date"), this.settingsManager.getWord("activity"))
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }



    private void addRows(PdfPTable table){
        for(Activity a : this.activities){
            table.addCell(a.getDescription());
            table.addCell(Display.amountDisplay(a.getAmount()));
            table.addCell(a.getCurrency());
            table.addCell(Display.dateDisplay(a.getTime()));
            table.addCell(a.getActivityVersion());
        }
    }

    /**
     * Function called after {@link ActivitiesFilterPanel#search()} function finishes and
     * if the {@link ActivitiesFilterPanel#activities} size is larger than zero.
     * @param activities activities to be displayed
     * */
    public void setActivities(ArrayList<Activity> activities) throws SQLException, ClassNotFoundException {
        this.activities = activities;
        ActivityModel am = new ActivityModel(this.activities);
        am.updateColumnNames();
        TableRowSorter<ActivityModel> sorter = new TableRowSorter<>(am);
        this.table.setRowSorter(sorter);
        this.table.setModel(am);
        sorter.setComparator(1, (String s1, String s2)-> (int) (Double.parseDouble(s1) - Double.parseDouble(s2)));
    }
}
