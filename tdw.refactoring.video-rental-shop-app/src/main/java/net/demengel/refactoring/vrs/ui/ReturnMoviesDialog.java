package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.helper.CustomerHelper;
import net.demengel.refactoring.vrs.helper.ExistingRentalsFacade;
import net.demengel.refactoring.vrs.helper.RentalHelper;
import net.demengel.refactoring.vrs.util.PriceUtils;

public class ReturnMoviesDialog extends ModalDialog {

    private Customer mSelectedCustomer;
    private AbstractTableModel m_model;

    public ReturnMoviesDialog(Customer pSelectedCustomer) {
        super("Return Movies");
        setName("returnMovies");
        mSelectedCustomer = pSelectedCustomer;

        setLayout(new BorderLayout());

        final List<Rental> rentals = CustomerHelper.getInstance().getCurrentRentalsForCustomer(mSelectedCustomer.getAccountNumber());
        RentalHelper.getInstance().addTitlesToRentals(rentals);

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
                if (rowIndex == rentals.size()) {
                    switch (columnIndex) {
                    case 0:
                        value = "Total";
                        break;
                    case 2:
                        double total = 0;
                        for (int i = 0; i < rentals.size(); i++) {
                            total += (Double) getValueAt(i, 3);
                        }
                        value = total;
                        break;
                    }
                }
                else {
                    switch (columnIndex) {
                    // Code
                    case 0:
                        value = ((Rental) rentals.get(rowIndex)).getMovieCode();
                        break;
                    // Title
                    case 1:
                        value = ((Rental) rentals.get(rowIndex)).getMovieTitle();
                        break;
                    // Date
                    case 2:
                        value = new SimpleDateFormat("yyyy/MM/dd").format(((Rental) rentals.get(rowIndex)).getRentalDate());
                        break;
                    // Price
                    case 3:
                        Rental lRental = (Rental) rentals.get(rowIndex);
                        List codes = new ArrayList();
                        codes.add(lRental.getMovieCode());
                        Movie lMovie = (Movie) MovieDao.getInstance().getMoviesByCodes(codes).get(0);
                        value = PriceUtils.getRentalPrice(lMovie, lRental.getRentalDate());
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (rentals != null) {
                    return rentals.size() + 1;
                }
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }
        };
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton printButton = new JButton("Validate");
        printButton.setName("validateReturnsButton");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pArg0) {
                try {
                    new ExistingRentalsFacade(mSelectedCustomer, rentals, new Date()).saveReturns();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ReturnMoviesDialog.this, "An unexpected error occurred", "Error!!!", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
        bottomPanel.add(printButton);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        center();
    }
}
