package gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import display.Display;
import entities.Balance;
import managers.ManagerFactory;
import managers.ResourceManager;
import managers.SettingsManager;
import models.BalanceModel;
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
 * Class that represents panel used for displaying filtered balances.
 * @author Dimitrije Karanfilovic
 * @since 22.06.2020.
 * */


public class DisplayBalancesPanel extends JPanel{
    /**
     * list of balances to be displayed
     * */
    private ArrayList<Balance> balances;
    private final JTable table;
    public final JButton backBtn;
    public final JButton printBtn;
    public final JButton graphBtn;
    private final ResourceManager resourceManager;
    private final SettingsManager settingsManager;

    DisplayBalancesPanel() throws SQLException, ClassNotFoundException {
        this.balances = new ArrayList<>();
        this.resourceManager = ManagerFactory.createResourceManager();
        this.settingsManager = ManagerFactory.createSettingsManager();
        this.table = new JTable(new BalanceModel(this.balances));
        this.table.getTableHeader().setReorderingAllowed(false);

        this.backBtn = new JButton(this.resourceManager.backIcon);
        this.printBtn = new JButton(this.resourceManager.saveIcon);
        this.graphBtn = new JButton(this.resourceManager.graphIcon);

        this.setLayout(new BorderLayout());
        JScrollPane tablePane = new JScrollPane(this.table);
        this.add(tablePane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel(new MigLayout());
        panel1.add(this.backBtn, "split 3");
        panel1.add(this.graphBtn);
        panel1.add(this.printBtn, "wrap");

        mainPanel.add(panel1);

        this.add(mainPanel, BorderLayout.SOUTH);
    }
    public boolean createTable(String path, String filename){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + System.getProperty("file.separator") + filename + ".pdf"));
            document.open();

            PdfPTable table = new PdfPTable(3);
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
        Stream.of(this.settingsManager.getWord("amount"), this.settingsManager.getWord("currency"), this.settingsManager.getWord("date"))
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table){
        for(Balance b : this.balances){
            table.addCell(Display.amountDisplay(b.getAmount()));
            table.addCell(b.getCurrency());
            table.addCell(Display.dateDisplay(b.getDateTime()));
        }
    }

    /**
     * Function called after {@link BalancesFilterPanel#search()} function finishes and
     * if the {@link BalancesFilterPanel#balances} size is larger than zero.
     * @param balances activities to be displayed
     * */
    public void setBalances(ArrayList<Balance> balances) throws SQLException, ClassNotFoundException {
        this.balances = balances;
        BalanceModel bm = new BalanceModel(this.balances);
        bm.updateColumnNames();
        TableRowSorter<BalanceModel> sorter = new TableRowSorter<>(bm);
        this.table.setRowSorter(sorter);
        this.table.setModel(bm);
        sorter.setComparator(0, (String s1, String s2)-> (int) (Double.parseDouble(s1) - Double.parseDouble(s2)));
    }
}
