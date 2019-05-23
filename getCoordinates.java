package kbs2;

import java.util.ArrayList;
import java.util.Map;


// Sources
// http://julien.gunnm.org/geek/programming/2015/09/13/how-to-get-geocoding-information-in-java-without-google-maps-api/
// https://dzone.com/articles/google-maps-java-swing

public class getCoordinates {

    private String[] address;
    int x = 1;

    public getCoordinates(){
        this.address = getDbAddress();
    }

    public String[] getDbAddress(){

//        SELECT Query
        ArrayList<ArrayList<String>> rows = DBConnection.selectQuery("SELECT DeliveryPostalCode, CustomerID FROM customers WHERE longitude IS NULL");

        String[] result = new String[rows.size()];

        //Array for addresses
        for(int i = 0; i < rows.size(); i++){
            result[i] = lonlat(rows.get(i));
        }
        return result;
    }

    public String lonlat(ArrayList<String> AddressList){
        Map<String, Double> coords;
        String CustomerID;

        //get Customer ID and converts addresses to long/lat
        CustomerID = AddressList.get(1);
        coords = OpenStreetMapUtils.getInstance().getCoordinates(AddressList.get(0));

        // updates database to add long/lat
        int update = DBConnection.executeQuery("UPDATE customers SET latitude = "+coords.get("lat")+", longitude = "+coords.get("lon")+" WHERE CustomerID =  "+CustomerID+"");
        if (update > 0) {
            System.out.println("Added long and lat to Db. " + x++);
        } else {
            System.out.println(DBConnection.statusMsg); //check status(also error info)
        }
        return("latitude :" + coords.get("lat"))+(" longitude:" + coords.get("lon")+ ", " + CustomerID);
    }

    public String[] getAddress() {
        return address;
    }
}
