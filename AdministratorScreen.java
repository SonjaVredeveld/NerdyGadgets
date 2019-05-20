package kbs2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

public class AdministratorScreen extends JFrame implements ActionListener, TableModelListener {

    private JTabbedPane JTPAdminTabs;
    private JTable JTStock;
    private JTable JTCustomers;
    private JTable JTOrders;
    private JFrame frame;
    private JButton JBlogout;
    private JButton JBedit;
    private ButtonEditor button;
    private String buttonType;
    private ArrayList<Product> products;
    private ArrayList<Customer> customers;
    private User user;

    public void setButton(String type) {
        this.buttonType = type;
    }

    public String getButton() {
        return buttonType;
    }

    public AdministratorScreen(User user) {
        frame = new JFrame();
        frame.setTitle("Systeembeheer");
        frame.setLayout(new FlowLayout());
        this.user = user;

        // data in PRODUCTEN tab
        Object[][] dataProducts = this.getStockRows();

        String[] columnProducts = {"Product_ID", "Productnaam", "Voorraad", "Prijs p/s", "edit"};

        JTStock = new JTable(dataProducts, columnProducts);
        JTStock.setFillsViewportHeight(true);

        JScrollPane spProducts = new JScrollPane(JTStock);
        spProducts.setPreferredSize(new Dimension(775, 450));
        JPanel panelProducts = new JPanel();
        panelProducts.setLayout(new BorderLayout());
        panelProducts.add(spProducts);

        JTStock.getColumn("edit").setCellRenderer(new ButtonRenderer());
        JTStock.getColumn("edit").setCellEditor(new ButtonEditor(new JCheckBox()));
        JTStock.getModel().addTableModelListener(this);

        Object[][] dataCustomers = this.getStockRows();

        String[] columnCustomers = {"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "edit"};

        JTCustomers = new JTable(dataCustomers, columnCustomers);
        JTCustomers.setFillsViewportHeight(true);

        JScrollPane spCustomers = new JScrollPane(JTCustomers);
        JPanel panelCustomers = new JPanel();
        panelCustomers.setLayout(new BorderLayout());
        panelCustomers.add(spCustomers);

        // Button for EDIT
        JBedit = new JButton("Edit");
        JTCustomers.getColumn("edit").setCellRenderer(new ButtonRenderer());
        JTCustomers.getColumn("edit").setCellEditor(new ButtonEditor(new JCheckBox()));
        JTCustomers.getModel().addTableModelListener(this);

        // data in ORDERS tab
        Object[][] dataOrders = this.getOrderRows();

        String[] columnOrders = {"Ordernummer", "KlantID", "Klantnaam"};

        JTOrders = new JTable(dataOrders, columnOrders);
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
        frame.setSize(800, 600);
        frame.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void editStock() {
        System.out.println("Start dialog");
        EditStock dialoog = new EditStock(this);
        dialoog.setVisible(true);
    }

    private Object[][] getStockRows() {
        this.products = Product.getProducts();
        Object[][] rows = new Object[products.size()][6];
        for (int i = 0; i < products.size(); i++) {
            rows[i] = new Object[]{"123", "dit", "12", "Prijs", "editProducts"};
        }

        return rows;
    }

    private Object[][] getCustomerRows() {
        this.customers = Customer.getCustomers();
        Object[][] rows = new Object[customers.size()][6];
        for (int i = 0; i < customers.size(); i++) {
            rows[i] = new Object[]{"Voornaam", "Achternaam", "Adres", "Woonplaats", "Afleveradres", "editCustomers"};
        }

        return rows;
    }

    private Object[][] getOrderRows() {
        ArrayList<Order> orders = Order.getOrders();
        Object[][] rows = new Object[orders.size()][6];
        for (int i = 0; i < orders.size(); i++) {
            rows[i] = new Object[]{};
        }

        return rows;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBlogout) {
            this.user = null;
            new LoginScreen();
            dispose();
        } else if (e.getSource() == products) {
            System.out.println("Products button clicked");
        }
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        int columnIndex = tme.getColumn();

        /*TODO:
        check if its the btn that is pressed
        get the clicked row object
        create the dialog and pass the object
        after this refresh the table.
         */
        if (tme.getSource() == this.JTCustomers) {
            System.out.println("customers");
            System.out.println(this.JTCustomers.getEditingRow());
            System.out.println(this.customers.get(this.JTCustomers.getEditingRow() - 1));
        } else if (tme.getSource() == this.JTStock) {
            System.out.println("products");
            System.out.println(this.JTStock.getEditingRow());
            System.out.println(this.products.get(this.JTStock.getEditingRow() - 1));

        } else {
            System.out.println("something else is striggering");
        }

    }

}
