package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.helper.RentingHelper;

public class PastRentingsDialog extends ModalDialog {

    public PastRentingsDialog(Customer pSelCust) {
        super("Past Rentings");

        setLayout(new BorderLayout());
        
        final List<Renting> lRentings = RentingDao.getInstance().findRentingsForCustomer(pSelCust.getAccountNumber());
        RentingHelper.getInstance().addTitlesToRentings(lRentings);

        AbstractTableModel model = new AbstractTableModel() {

            @Override
            public String getColumnName(int pColumnIdx) {
                String name = "";
                if (pColumnIdx == 0) {
                    name = "Code";
                }
                else if (pColumnIdx == 1) {
                    name = "Title";
                }
                else if (pColumnIdx == 2) {
                    name = "Renting Date";
                }
                else if (pColumnIdx == 3) {
                    name = "Return Date";
                }
                return name;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                Object value = null;
                switch (columnIndex) {
                // Code
                case 0:
                    value = lRentings.get(rowIndex).getMovieCode();
                    break;
                // Title
                case 1:
                    value = lRentings.get(rowIndex).getMovieTitle();
                    break;
                // Renting Date
                case 2:
                    value = lSimpleDateFormat.format(lRentings.get(rowIndex).getRentingDate());
                    break;
                // Return Date
                case 3:
                    if (lRentings.get(rowIndex).getReturnDate() == null) {
                        value = null;
                    }
                    else {
                        value = lSimpleDateFormat.format(lRentings.get(rowIndex).getReturnDate());
                    }
                    break;
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (lRentings != null) {
                    return lRentings.size();
                }
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }
        };
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        
        pack();
        center();
    }

}
