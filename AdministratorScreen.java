
package nerdyg;

import java.awt.BorderLayout;
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
    
    public AdministratorScreen() {
    frame = new JFrame();    
    frame.setTitle("Systeembeheer");
    frame.setLayout(new FlowLayout());
    
    // data in PRODUCTEN tab 
    
    String dataProducten[][] = { 
        {"123","dit","Prijs", "edit"},    
        {"1234","Shirt","Prijs", "edit"}, 
        
    }; 
    
    String columnProducten[] = {"Product_ID","Productnaam","Prijs p/s", "edit"};         
    
    JTStock = new JTable(dataProducten,columnProducten);
    JTStock.setFillsViewportHeight(true);
    
    JScrollPane spProducten = new JScrollPane(JTStock); 
    spProducten.setPreferredSize(new Dimension(775,450));
    JPanel panelProducten = new JPanel();
        panelProducten.setLayout(new BorderLayout());
        panelProducten.add(spProducten);
        
        
    // data in KLANTGEGEVENS tab
    
    String dataKlanten[][] = { 
        {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"},    
        {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"}, 
        
    }; 
    
    String columnKlanten[] = {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"};
    
    JTCustomers = new JTable(dataKlanten,columnKlanten);   
    JTCustomers.setFillsViewportHeight(true);
    
    JScrollPane spCustomers = new JScrollPane(JTCustomers); 
    JPanel panelCustomers = new JPanel();
        panelCustomers.setLayout(new BorderLayout());
        panelCustomers.add(spCustomers);
        
        
    // data in ORDERS tab 
    
    String dataOrders[][] = { 
        {"1234", "765", "443", "Klantnaam"}, 
        {"1234", "765", "443", "Klantnaam"},
        {"1234", "765", "443", "Klantnaam"},
        {"1234", "765", "443", "Klantnaam"},
    }; 
    
    String columnOrders[] = {"Ordernummer", "Productnummer", "KlantID", "Klantnaam"};
    
    JTOrders = new JTable(dataOrders,columnOrders);   
    JTOrders.setFillsViewportHeight(true);
    
    JScrollPane spOrders = new JScrollPane(JTOrders); 
    JPanel panelOrders = new JPanel();
        panelOrders.setLayout(new BorderLayout());
        panelOrders.add(spOrders);
    
    JTPAdminTabs = new JTabbedPane();
    JTPAdminTabs.add("Producten", panelCustomers);
    JTPAdminTabs.add("Klantgegevens", panelProducten);
    JTPAdminTabs.add("Ordegegevens", panelOrders);
    

    
    
    // LOGOUT button
    JBlogout = new JButton("Logout");
    
    
    
    frame.add(JTPAdminTabs);
    frame.add(JBlogout);
    frame.setSize(800,600);    
    frame.setVisible(true);
}
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBlogout) {
            dispose();
        }
    }
    
    
}
