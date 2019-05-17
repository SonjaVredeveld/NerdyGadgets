
package kbs2;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdministratorScreen extends JFrame implements ActionListener{
    private JTabbedPane JTPAdminTabs;
    private JTable JTStock;
    private JTable JTCustomers;
    private JTable JTOrders;
    private JFrame frame;
    private JButton JBlogout;
    private JButton JBedit;
    private ButtonEditor button;
    private String buttonType;
    private JCheckBox products, customers;
    
    public void setButton(String type) {
        this.buttonType = type;
    };
    
    public String getButton() {
        return buttonType;
    }
    
    public AdministratorScreen() {
        frame = new JFrame();    
        frame.setTitle("Systeembeheer");
        frame.setLayout(new FlowLayout());

        // data in PRODUCTEN tab 

        String dataProducts[][] = { 
            {"123","dit","12","Prijs", "editProducts"},    
            {"1234","Shirt", "765","Prijs", "editProducts"}, 

        }; 

        String columnProducts[] = {"Product_ID","Productnaam", "Voorraad","Prijs p/s", "edit"};         

        JTStock = new JTable(dataProducts,columnProducts);
            JTStock.setFillsViewportHeight(true);

        JScrollPane spProducts = new JScrollPane(JTStock); 
            spProducts.setPreferredSize(new Dimension(775,450));
        JPanel panelProducts = new JPanel();
            panelProducts.setLayout(new BorderLayout());
            panelProducts.add(spProducts);

    //Button in the EDIT column   
        products = new JCheckBox();
        
//        JBedit = new JButton("Edit");
            JTStock.getColumn("edit").setCellRenderer(new ButtonRenderer());
//            JCheckBox checkBox = null;
            JTStock.getColumn("edit").setCellEditor(new ButtonEditor(products, "products")); 
        products.addActionListener(this);
    // data in KLANTGEGEVENS tab

        String dataCustomers[][] = { 
            {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"},    
            {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"}, 
        }; 

        String columnCustomers[] = {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"};

        JTCustomers = new JTable(dataCustomers,columnCustomers);   
            JTCustomers.setFillsViewportHeight(true);

        JScrollPane spCustomers = new JScrollPane(JTCustomers); 
        JPanel panelCustomers = new JPanel();
            panelCustomers.setLayout(new BorderLayout());
            panelCustomers.add(spCustomers);

    // Button for EDIT 
        customers = new JCheckBox();

        JBedit = new JButton("Edit");
            JTCustomers.getColumn("edit").setCellRenderer(new ButtonRenderer());
            //JCheckBox checkBox = null;
            JTCustomers.getColumn("edit").setCellEditor(new ButtonEditor(customers, "customers"));   
   
    //    // data in ORDERS tab 

        String dataOrders[][] = { 
            {"1234", "443", "Klantnaam"}, 
            {"1234", "443", "Klantnaam"},
            {"1234", "443", "Klantnaam"},
            {"1234", "443", "Klantnaam"},
        }; 

        String columnOrders[] = {"Ordernummer", "KlantID", "Klantnaam"};

        JTOrders = new JTable(dataOrders,columnOrders);   
            JTOrders.setFillsViewportHeight(true);

        JScrollPane spOrders = new JScrollPane(JTOrders); 
        JPanel panelOrders = new JPanel();
            panelOrders.setLayout(new BorderLayout());
            panelOrders.add(spOrders);

        JTPAdminTabs = new JTabbedPane();
            JTPAdminTabs.add("Producten", panelProducts);
            JTPAdminTabs.add("Klantgegevens", panelCustomers);
            JTPAdminTabs.add("Ordegegevens", panelOrders);

        // LOGOUT button
        JBlogout = new JButton("Logout");

        frame.add(JTPAdminTabs);
        frame.add(JBlogout);
        frame.setSize(800,600);    
        frame.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void editStock() {
        System.out.println("Start dialog");
        EditStock dialoog = new EditStock(this);
        dialoog.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBlogout) {
            dispose();
        } else if (e.getSource() == products) {
            System.out.println("Products button clicked");
        }
    }
    
    
}
