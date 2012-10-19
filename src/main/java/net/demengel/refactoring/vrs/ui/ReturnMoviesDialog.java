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
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.helper.CustomerHelper;
import net.demengel.refactoring.vrs.helper.ExistingRentingsFacade;
import net.demengel.refactoring.vrs.helper.RentingHelper;
import net.demengel.refactoring.vrs.util.PriceUtils;

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
                if (rowIndex == rentings.size()) {
                    switch (columnIndex) {
                    case 0:
                        value = "Total";
                        break;
                    case 2:
                        double total = 0;
                        for (int i = 0; i < rentings.size(); i++) {
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
                }
                return value;
            }

            @Override
            public int getRowCount() {
                if (rentings != null) {
                    return rentings.size() + 1;
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
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pArg0) {
                try {
                    new ExistingRentingsFacade(mSelectedCustomer, rentings, new Date()).saveReturns();
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
