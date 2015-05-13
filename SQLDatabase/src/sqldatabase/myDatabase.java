/*
 * @author      Dau Ting Lam
 * @subject     CSCI 211 - Database Project
 * @date        April 10, 2013
 * @duration    10 HR, 400 lines
 */
package sqldatabase;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import javax.swing.event.*;

public class myDatabase extends JApplet {
    private JButton jbtView     = new JButton("View");
    private JButton jbtUpdate   = new JButton("Update");
    private JButton jbtDelete   = new JButton("Delete");
    private JButton jbtInsert   = new JButton("Insert");
    private JButton jbtClear    = new JButton("Clear");
    //private JPanel jPanel        = new JPanel(); useless code
    private JComboBox jcbvinList = new JComboBox();
    private Statement stmt;                         // Statement for executing queries
    InputControlPanel inputP = new InputControlPanel(); // Input Panel
    SideButtons sbP             = new SideButtons();    // Button Panel

/* Main method */    
    public static void main(String[] args) {
        myDatabase applet = new myDatabase();
        JFrame frame = new JFrame();
        frame.setTitle("Java Application Interface for Database");
        frame.setSize(360,400);
        frame.getContentPane().add(applet, BorderLayout.CENTER); // add applet to frame
        applet.init();                                  // initialize applet
        applet.start();                                 // starts the applet
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }   
    
/* Initialize the applet */ @Override    
    public void init() {
    // Initialize database connection and create a Statement object
    initializeDB();
    
    //add(jPanel = inputP, BorderLayout.CENTER);
    add(inputP, BorderLayout.CENTER);
    add(sbP, BorderLayout.WEST);
    jbtView.addActionListener(new viewListener());      // adds ActionListener          
    jbtUpdate.addActionListener(new updateListener());
    jcbvinList.addPopupMenuListener(new getVinListener());
    jcbvinList.addActionListener(new setVinListener());
    jbtDelete.addActionListener(new deleteListener());
    jbtInsert.addActionListener(new insertListener());
    jbtClear.addActionListener(new clearListener());       
}

/* Initialize connection and statement */
    private void initializeDB() {
        try {     
            Class.forName("com.mysql.jdbc.Driver");             // Load the JDBC driver
            System.out.println("Driver loaded");  
            Connection connection = DriverManager.getConnection // Establish connection
            ("jdbc:mysql://localhost/dealership", "root", "Mysql");
            System.out.println("Database connected");
            stmt = connection.createStatement();                // Create a statement
        }
        catch (Exception ex) {
        ex.printStackTrace();
        }
}

/* a method to clear any data from the input boxes after deletion */    
    public void clearBoxes() {
        inputP.vin.setText("");
        inputP.model.setText("");
        inputP.make.setText("");
        inputP.color.setText("");
        inputP.baySize.setText("");
        inputP.cabSize.setText("");
        inputP.price.setText("");
        inputP.year.setText("");
}

/* search if vin is in the database */
    public boolean peek(String vin) {
            boolean peeker = false;                             // flag to control
            System.out.println("peeking");           
                try {
                String queryString = "SELECT make, model, color, year, "
                        + "cabSize, baySize, price "
                        + "FROM Truck "
                        + "WHERE Truck.VIN = '" + vin + "'"; 
                ResultSet rset = stmt.executeQuery(queryString);    // execute query
                if(rset.next()) {                               // checks if vin exist
                    peeker = true;
                    inputP.make.setText(rset.getString(1));
                    inputP.model.setText(rset.getString(2));
                    inputP.color.setText(rset.getString(3));
                    inputP.year.setText(rset.getString(4));
                    inputP.cabSize.setText(rset.getString(5));
                    inputP.baySize.setText(rset.getString(6));
                    inputP.price.setText(rset.getString(7));
                }
                else { peeker = false; }
                }
                catch (SQLException ex) { ex.printStackTrace(); }
                return peeker;
}      
 
 /* sets the vin selected from JComboBox */   
class setVinListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        System.out.println("setVin");
        inputP.vin.setText("");
        String temp = (jcbvinList.getSelectedItem()==null) ? ("") 
                        : jcbvinList.getSelectedItem().toString();
        inputP.vin.setText(temp);
    }
}
 
/* gets all the vin from the database */   
class getVinListener implements PopupMenuListener {
     public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        System.out.println("getVin");
         try {
                String queryString = "SELECT * FROM Truck "; 
                ResultSet rset = stmt.executeQuery(queryString);
                StringBuilder temp = new StringBuilder(); 
                /*if (rset.next()) {
                for (int x = 0; x < rset.getRow(); x++){  temp.append(rset.getString(1) + " "); }
                String temp2 = temp.toString();
                String[] vins = temp2.split(" ");
                jcbvinList.removeAllItems();
                for (int i = 0; i < vins.length; i++) 
                    jcbvinList.addItem(vins[i]);
                System.out.println("  performed"); 
                }*/
                jcbvinList.removeAllItems();
                while (rset.next()) {          
                String s = rset.getString(1); 
                jcbvinList.addItem(s); 
                } 
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        catch (NullPointerException ex) { JOptionPane.showMessageDialog(null,
                                                    "Error: NullPointer");}
        }
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            //System.out.print("InVisible");            
        }
        public void popupMenuCanceled(PopupMenuEvent e) {
            //System.out.print("Canceled");            
        }
}

class deleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.print("Delete action listened");      
            if (inputP.vin.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Error: Empty VIN#");
            }
            else { 
                String vin = inputP.vin.getText();
                try {
                String queryString = "DELETE from Truck "
                                        + "WHERE Truck.VIN = '" + vin + "'"; 
                stmt.execute(queryString);
                clearBoxes();
                }
                catch (SQLException ex) { ex.printStackTrace(); }
             } System.out.println(" & performed");
    }
}

class clearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.print("Clear action listened");           
                try {
                String queryString = "TRUNCATE Truck";
                stmt.execute(queryString);
                clearBoxes();
                }
                catch (SQLException ex) { ex.printStackTrace(); }
              System.out.println(" & performed");
    }
}

class insertListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.print("Insert action listened");      
            String vin = inputP.vin.getText();
            try {
                InputControlPanel popup = new InputControlPanel(vin);
                int result = JOptionPane.showConfirmDialog(null, popup, 
                        " Insert new Entry", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                        vin = popup.vin.getText();
                        String mk = popup.make.getText();
                        String ml = popup.model.getText();
                        String c = popup.color.getText();
                        String y = popup.year.getText();
                        String cs = popup.cabSize.getText();
                        String bs = popup.baySize.getText();
                        String p = popup.price.getText();
                if (peek(vin)==false) {
                String queryString = "INSERT into Truck VALUES ('" + vin +  "', '" 
                        + mk + "', '" + ml + "',  '" + c + "', '"
                        + ((y.equals("")) ? ("1960-01-01"): Double.parseDouble(cs)) 
                        + "', '" + ((cs.equals("")) ? 0: Double.parseDouble(cs)) + "', '" 
                        + ((bs.equals("")) ? 0: Double.parseDouble(bs)) 
                        + "', '" + ((p.equals("")) ? 0: Double.parseDouble(p)) + "')";
                stmt.execute(queryString); 
                }
                else { JOptionPane.showMessageDialog(null, 
                        "Error: VIN# already exists! Try another.");
             }
            }
            }
            catch (SQLException ex) { ex.printStackTrace(); }
              System.out.println(" & performed");
    }
}

class viewListener implements ActionListener {
        boolean peeker;
        public void actionPerformed(ActionEvent e) {
            System.out.print("View action listened");           
            if (inputP.vin.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Error: Empty VIN#");
            }
            else { 
                String vin = inputP.vin.getText();
                try {
                String queryString = "SELECT make, model, color, year, "
                        + "cabSize, baySize, price "
                        + "FROM Truck "
                        + "WHERE Truck.VIN = '" + vin + "'"; 
                ResultSet rset = stmt.executeQuery(queryString);
                
                if(rset.next()) {
                    peeker = true;
                    inputP.make.setText(rset.getString(1));
                    inputP.model.setText(rset.getString(2));
                    inputP.color.setText(rset.getString(3));
                    inputP.year.setText(rset.getString(4));
                    inputP.cabSize.setText(rset.getString(5));
                    inputP.baySize.setText(rset.getString(6));
                    inputP.price.setText(rset.getString(7));
                }
                else { JOptionPane.showMessageDialog(null, "Not found"); peeker = false; }
                }
                catch (SQLException ex) { ex.printStackTrace(); }
            } System.out.println(" & performed");
        } 
} 

class updateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Update action listened");
            
            if (inputP.vin.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Error: Empty VIN#");
            }
            else { 
                String vin = inputP.vin.getText();
                viewListener peek = new viewListener(); peek.actionPerformed(e);
                //try {
                if (peek(vin)) {
                try {
                
                InputControlPanel popup = new InputControlPanel(vin);
                popup.make.setText( inputP.make.getText());
                popup.model.setText( inputP.model.getText());
                popup.color.setText( inputP.color.getText());
                popup.year.setText( inputP.year.getText());
                popup.cabSize.setText( inputP.cabSize.getText());
                popup.baySize.setText( inputP.baySize.getText());
                popup.price.setText( inputP.price.getText()); 
                
                int result = JOptionPane.showConfirmDialog(null, popup, " Update Info",
                                                    JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                        String mk = popup.make.getText();
                        String ml = popup.model.getText();
                        String c = popup.color.getText();
                        String y = popup.year.getText();
                        String cs = popup.cabSize.getText();
                        String bs = popup.baySize.getText();
                        String p = popup.price.getText();
                
                String queryString = "UPDATE Truck SET vin = '" + vin + "', make = '" + mk 
                        + "', model = '" + ml + "', color = '" + c + "', cabSize = '" + cs 
                        + "', baySize = '"  + bs + "', price = '" 
                        + p + "' WHERE Truck.VIN = '" + vin + "'"; 
                stmt.execute(queryString); 
                new viewListener().actionPerformed(e);
                }                     
             /*  if(rset.next()) {
                    inputP.make.setText(rset.getString(1));
                    inputP.model.setText(rset.getString(2));
                    inputP.color.setText(rset.getString(3));
                    inputP.year.setText(rset.getString(4));
                    inputP.cabSize.setText(rset.getString(5));
                    inputP.baySize.setText(rset.getString(6));
                    inputP.price.setText(rset.getString(7));
                } 
                new viewListener().actionPerformed(e);
                else { JOptionPane.showMessageDialog(null, "Not found"); } */      
                }
                catch (SQLException ex) { ex.printStackTrace(); } System.out.println(
                                                                " \t & performed");
                }
                else { JOptionPane.showMessageDialog(null, "Error: VIN# doesn't exits.");
                }
               //catch (SQLException ex) { ex.printStackTrace(); } } 
        }
} }

/* JPanel user input class */    
private class InputControlPanel extends JPanel {                           
      private JTextField vin    = new JTextField();
      private JTextField make   = new JTextField();
      private JTextField model  = new JTextField();
      private JTextField color  = new JTextField();
      private JTextField year   = new JTextField();
      private JTextField cabSize    = new JTextField();
      private JTextField baySize    = new JTextField();
      private JTextField price  = new JTextField();
      
    public InputControlPanel() {
      JPanel p1 = new JPanel();
      p1.setLayout(new GridLayout(1,3));
      p1.add(vin); 
      p1.add(jcbvinList);

      setLayout(new GridLayout(8,2));     
      add(new JLabel(" VIN #: ")); 
      add(p1);
      add(new JLabel(" Make: "));
      add(make); make.setEditable(false);
      add(new JLabel(" Model: "));
      add(model); model.setEditable(false);
      add(new JLabel(" Color: "));
      add(color ); color.setEditable(false);
      add(new JLabel(" Year:"));
      add(year ); year.setEditable(false);
      add(new JLabel(" Cab size: "));
      add(cabSize ); cabSize.setEditable(false);
      add(new JLabel(" Bay size: "));
      add(baySize ); baySize.setEditable(false);
      add(new JLabel(" Price: "));
      add(price ); price.setEditable(false);
      setBorder(new TitledBorder("To Start, Enter The VIN # and press the buttons"));
    }
    
    public InputControlPanel(String str) {
      setLayout(new GridLayout(8,2));     
      add(new JLabel(" VIN #: ")); vin.setText(str);
      add(vin);
      add(new JLabel(" Make: "));
      add(make); make.setEditable(true);
      add(new JLabel(" Model: "));
      add(model); model.setEditable(true);
      add(new JLabel(" Color: "));
      add(color ); color.setEditable(true);
      add(new JLabel(" Year(YYYY-DD-MM):"));
      add(year ); year.setEditable(true);
      add(new JLabel(" Cab size: #"));
      add(cabSize ); cabSize.setEditable(true);
      add(new JLabel(" Bay size: #"));
      add(baySize ); baySize.setEditable(true);
      add(new JLabel(" Price: $"));
      add(price ); price.setEditable(true);
      setBorder(new TitledBorder("Enter Data (Fill everything)"));
    }
 } 
 
/* a Panel on the side for buttons */
private class SideButtons extends JPanel {
    public SideButtons() {
      setLayout(new GridLayout(5,1, 5, 10)); 
      jbtView.setFont(new Font("Arial", Font.BOLD, 16));
      jbtUpdate.setFont(new Font("Arial", Font.BOLD, 16));
      jbtInsert.setFont(new Font("Arial", Font.BOLD, 16));
      jbtDelete.setFont(new Font("Arial", Font.BOLD, 16));
      jbtClear.setFont(new Font("Arial", Font.BOLD, 16));
      add(jbtView); 
      add(jbtUpdate);
      add(jbtInsert);
      add(jbtDelete);
      add(jbtClear);
    }
} 
}