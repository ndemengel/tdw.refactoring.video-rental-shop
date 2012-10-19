package net.demengel.refactoring.vrs.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.util.PriceUtils;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class AvailableMoviesPanel extends JPanel {

    private AbstractTableModel m_model;
    private List mDisplayedMovies;
    private String m_filterString = "";

    public AvailableMoviesPanel() {
        setLayout(new BorderLayout());
        
        //// filter panel
        JPanel filterPanel = new JPanel(new FlowLayout());
        // field label
        JLabel label = new JLabel("Filter:");
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
        
                    // get all movies
                    List movies = MovieDao.getInstance().listAllMovies();
                    // loop on them
                    for (int i = 0; i < movies.size(); i++) {
                        Movie movie = (Movie) movies.get(i);
                        // if movie title contains user entry
                        if (movie.getTitle().toLowerCase().contains(m_filterString.toLowerCase()))
                            // add it to the list of movies to display
                            mDisplayedMovies.add(movie);
                    }
                 
                    // notifies table of data change
                    m_model.fireTableDataChanged();
                }
            }
        });
        jTextField.setColumns(50);
        filterPanel.add(jTextField);
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
                    name = "Title";
                }
                else if (pColumnIdx == 1) {
                    name = "Release Year";
                }
                else if (pColumnIdx == 2) {
                    name = "Available Quantity";
                }
                if (pColumnIdx == 3) {
                    name = "Price";
                }
                if (pColumnIdx == 4) {
                    name = "Director";
                }
                else if (pColumnIdx == 5) {
                    name = "Duration";
                }
                return name;
            }
        
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value = null;
                switch (columnIndex) {
                // Title
                case 0:
                    value = ((Movie) mDisplayedMovies.get(rowIndex)).getTitle();
                    break;
                // Release Year
                case 1:
                    Calendar cal = Calendar.getInstance();
                    cal.setTime( ((Movie) mDisplayedMovies.get(rowIndex)).getReleaseDate());
                    // only displays release year
                    value = cal.get(Calendar.YEAR);
                    break;
                // Available Quantity
                case 2:
                    Movie l_movie = (Movie) mDisplayedMovies.get(rowIndex);
                    // get total owned quantity
                    int l_ownedQuantity = l_movie.getOwnedQuantity();
                    // get current rentings for the movie
                    List<Renting> l_CurRentings = RentingDao.getInstance().findCurrentRentingsForMovie(l_movie.getCode());
                    //the difference is the availbale qty
                    value = l_ownedQuantity - l_CurRentings.size();
                    break;
                // Price
                case 3:
                    Movie lMovie = (Movie) mDisplayedMovies.get(rowIndex);
                    value = PriceUtils.getRental(lMovie, new Date());
                    break;
                // Director
                case 4:
                    value = ((Movie) mDisplayedMovies.get(rowIndex)).getDirector();
                    break;
                // Duration
                case 5:
                    value = ((Movie) mDisplayedMovies.get(rowIndex)).getDuration();
                    break;
                // Cast
                case 6:
                    StringBuffer sb = new StringBuffer();
                    for(Iterator castIterator = ((Movie) mDisplayedMovies.get(rowIndex)).getCast().iterator(); castIterator.hasNext(); ) {
                        if (sb.length() != 0)
                            sb.append(", ");
                        sb.append(castIterator.next());
                    }
                    value = sb.toString();
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
                return 7;
            }
        };
        add(new JScrollPane(new JTable(m_model)), BorderLayout.CENTER);

        // initializes table with all movies
        mDisplayedMovies = MovieDao.getInstance().listAllMovies();
    }
  
}
