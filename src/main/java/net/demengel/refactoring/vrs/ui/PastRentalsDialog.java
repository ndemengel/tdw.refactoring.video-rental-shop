package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.RentalDao;
import net.demengel.refactoring.vrs.helper.RentalHelper;

public class PastRentalsDialog extends ModalDialog {

    public PastRentalsDialog(Customer pSelCust) {
        super("Past Rentals");

        setLayout(new BorderLayout());
        
        final List<Rental> lRentals = RentalDao.getInstance().findRentalsForCustomer(pSelCust.getAccountNumber());
        RentalHelper.getInstance().addTitlesToRentals(lRentals);

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
                    name = "Rental Date";
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
                    value = lRentals.get(rowIndex).getMovieCode();
                    break;
                // Title
                case 1:
                    value = lRentals.get(rowIndex).getMovieTitle();
                    break;
                // Rental Date
                case 2:
                    value = lSimpleDateFormat.format(lRentals.get(rowIndex).getRentalDate());
                    break;
                // Return Date
                case 3:
                    if (lRentals.get(rowIndex).getReturnDate() == null) {
                        value = null;
                    }
                    else {
                        value = lSimpleDateFormat.format(lRentals.get(rowIndex).getReturnDate());
                    }
                    break;
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (lRentals != null) {
                    return lRentals.size();
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
