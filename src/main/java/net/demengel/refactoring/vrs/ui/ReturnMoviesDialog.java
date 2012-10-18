package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.demengel.refactoring.vrs.Main;
import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.helper.CustomerHelper;
import net.demengel.refactoring.vrs.helper.RentingHelper;
import net.demengel.refactoring.vrs.util.PriceUtils;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class ReturnMoviesDialog extends ModalDialog {

    private Customer mSelectedCustomer;
    private AbstractTableModel m_model;

    public ReturnMoviesDialog(Customer pSelectedCustomer) {
        super("Return Movies");
        mSelectedCustomer = pSelectedCustomer;

        setLayout(new BorderLayout());

        add(new JLabel("Work in progress..."), BorderLayout.NORTH);

        final List<Renting> rentings = CustomerHelper.getInstance().getCurrentRentingsForCustomer(mSelectedCustomer.getAccountNumber());
        RentingHelper.getInstance().addTitlesToRentings(rentings);

        m_model = new AbstractTableModel() {

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
                    name = "Date";
                }
                else if (pColumnIdx == 3) {
                    name = "Price";
                }
                return name;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value = null;
                switch (columnIndex) {
                // Code
                case 0:
                    value = ((Renting) rentings.get(rowIndex)).getMovieCode();
                    break;
                // Title
                case 1:
                    value = ((Renting) rentings.get(rowIndex)).getMovieTitle();
                    break;
                // Date
                case 2:
                    value = new SimpleDateFormat("yyyy/MM/dd").format(((Renting) rentings.get(rowIndex)).getRentingDate());
                    break;
                // Price
                case 3:
                    Renting lRenting = (Renting) rentings.get(rowIndex);
                    List codes = new ArrayList();
                    codes.add(lRenting.getMovieCode());
                    Movie lMovie = (Movie) MovieDao.getInstance().getMoviesByCodes(codes).get(0);
                    value = PriceUtils.getRental(lMovie, lRenting.getRentingDate());
                    break;
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (rentings != null) {
                    return rentings.size();
                }
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }
        };
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        pack();
        center();
    }
}
