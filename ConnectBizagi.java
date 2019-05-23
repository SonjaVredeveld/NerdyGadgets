package kbs2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// install .jar file (FILES - mssql-jdbc-7.2.2.jre11.jar) in classpath

public class ConnectBizagi {
    private static String host = "jdbc:sqlserver://localhost:1433;databseName=NerdyGadgets";
    private static String username = "sa";
    private static String password = "Password123";
    private static String sql = "SELECT Productnummer FROM NerdyGadgets.dbo.Retourproces;";
    
    private ConnectBizagi() {
        }
    
    protected static Connection updateStockItemHoldings() {
        ArrayList<String> items = new ArrayList<>();
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=NerdyGadgets;user=sa;password=Password123";
        
    // start connection
          try (
            Connection con = DriverManager.getConnection(connectionUrl); 
            Statement stmt = con.createStatement();) 
          {
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("PRODUCTGEGEVENS UPDATEN: ");
            
        // loop through the data in the result set.
            while (rs.next()) {
                items.add(rs.getString(1));
            }
        // Check for duplicates.
            Map<String, Integer> duplicates = new HashMap<String, Integer>();
            for (Object dup : items) {
                if(duplicates.containsKey(dup)) {
                    duplicates.put((String) dup, duplicates.get(dup) + 1);
                } else {
                    duplicates.put((String) dup, 1);
                }
            }
            // println.
              for (Map.Entry<String, Integer> entry : duplicates.entrySet()){
                  System.out.println("Productnummer " + entry.getKey() + " = " + entry.getValue() + "x toevoegen");
              }
        }
          
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
