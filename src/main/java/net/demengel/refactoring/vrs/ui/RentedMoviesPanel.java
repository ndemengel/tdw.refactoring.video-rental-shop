package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.helper.RentingHelper;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class RentedMoviesPanel extends JPanel {

    private AbstractTableModel m_model;
    private List mDisplayedMovies;
    private String m_filterString = "";
    private boolean m_lateReturnsOnly;

    public RentedMoviesPanel() {
        setLayout(new BorderLayout());
        
        //// filter panel
        JPanel filterPanel = new JPanel(new FlowLayout());
        // field label
        JLabel label = new JLabel("Filter by...");
        filterPanel.add(label);
        label = new JLabel("Title:");
        filterPanel.add(label);
        // filter field
        final JTextField jTextField = new JTextField();
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent pE) {
                // if field content has changed
                if (!jTextField.getText().equals(m_filterString)) {
                    m_filterString = jTextField.getText();
                    
                    mDisplayedMovies = new ArrayList();

                    if (m_lateReturnsOnly) {
                        // get all movies
                        List movies = RentingHelper.getInstance().getLateReturnsWithTitle();
                        // loop on them
                        for (int i = 0; i < movies.size(); i++) {
                            Renting movie = (Renting) movies.get(i);
                            // if movie title contains user entry
                            if (movie.getMovieTitle().toLowerCase().contains(m_filterString.toLowerCase()))
                                // add it to the list of movies to display
                                mDisplayedMovies.add(movie);
                        }
                    }
                    else {
                        // get all movies
                        List movies = RentingHelper.getInstance().getCurrentRentingsWithTitle();
                        // loop on them
                        for (int i = 0; i < movies.size(); i++) {
                            Renting movie = (Renting) movies.get(i);
                            // if movie title contains user entry
                            if (movie.getMovieTitle().toLowerCase().contains(m_filterString.toLowerCase()))
                                // add it to the list of movies to display
                                mDisplayedMovies.add(movie);
                        }
                    }
                 
                    // notifies table of data change
                    m_model.fireTableDataChanged();
                }
            }
        });
        jTextField.setColumns(20);
        filterPanel.add(jTextField);
        
        final JCheckBox lCb = new JCheckBox("Late returns");
        lCb.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent pArg0) {
                // TODO Auto-generated method stub
                
                // if field content has changed
                if (lCb.isSelected() != m_lateReturnsOnly) {
                    m_lateReturnsOnly = lCb.isSelected();
                            
                    mDisplayedMovies = new ArrayList();

                    if (m_lateReturnsOnly) {
                        // get all movies
                        List movies = RentingHelper.getInstance().getLateReturnsWithTitle();
                        // loop on them
                        for (int i = 0; i < movies.size(); i++) {
                            Renting movie = (Renting) movies.get(i);
                            // if movie title contains user entry
                            if (movie.getMovieTitle().toLowerCase().contains(m_filterString.toLowerCase()))
                                // add it to the list of movies to display
                                mDisplayedMovies.add(movie);
                        }
                    }
                    else {
                        // get all movies
                        List movies = RentingHelper.getInstance().getCurrentRentingsWithTitle();
                        // loop on them
                        for (int i = 0; i < movies.size(); i++) {
                            Renting movie = (Renting) movies.get(i);
                            // if movie title contains user entry
                            if (movie.getMovieTitle().toLowerCase().contains(m_filterString.toLowerCase()))
                                // add it to the list of movies to display
                                mDisplayedMovies.add(movie);
                        }
                    }
                 
                    // notifies table of data change
                    m_model.fireTableDataChanged();
                }
            }
            
        });
        filterPanel.add(lCb);
        
        add(filterPanel, BorderLayout.NORTH);
        
        //// Table model
        m_model = new AbstractTableModel() {
        
            /**
             * Returns the name of the column at the given index.
             * @param pColumnIdx the column index
             * @return the name
             */
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
                    name = "Customer #";
                }
                if (pColumnIdx == 3) {
                    name = "Date";
                }
                return name;
            }
        
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value = null;
                switch (columnIndex) {
                // Code
                case 0:
                    value = ((Renting) mDisplayedMovies.get(rowIndex)).getMovieCode();
                    break;
                // Title
                case 1:
                    value = ((Renting) mDisplayedMovies.get(rowIndex)).getMovieTitle();
                    break;
                // Customer
                case 2:
                    value =  ((Renting) mDisplayedMovies.get(rowIndex)).getCustomerNumber();
                    break;
                // Price
                case 3:
                    value = new SimpleDateFormat("yyyy/MM/dd").format(((Renting) mDisplayedMovies.get(rowIndex)).getRentingDate());
                    break;
                }
                return value;
            }
        
            @Override
            public int getRowCount() {
                if (mDisplayedMovies != null) {
                    return mDisplayedMovies.size();
                }
                return 0;
            }
        
            @Override
            public int getColumnCount() {
                return 4;
            }
        };
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        // initializes table with all movies
        mDisplayedMovies = RentingHelper.getInstance().getCurrentRentingsWithTitle();
    }
  
}
