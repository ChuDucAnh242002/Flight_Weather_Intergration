
package fi.tuni.java5.flightweatherapp.settingManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fi.tuni.java5.flightweatherapp.Flight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Reads and wites data for Favorites and Preferences objects to and from a JSON file.
 * @author Kalle Hirvijärvi
 */
public class SaveData {
    
    // from storing the data
    private Favorites favorites = new Favorites();
    private Preferences preferences;
    
    // name of target file
    // needs to be inside root directory of the project
    private static final String file_name = "settings.json";
    
    // formatting used for Date objects
    private static final String date_format = "yyyy-MM-dd HH:mm:ss z";
    
    public SaveData(){
        
        //reads data from the file and saves it in a JSON object
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonObject data = gson.fromJson(jsonContent.toString(), JsonObject.class);
            parse_json_object(data);
        }
        catch (IOException e) {
            System.err.println("Error: file not found: " + e);
        } 
        catch (JsonParseException e) {
            System.err.println("Error: cannot parse JSON: " + e);
        }
        // if the string that represents currency in the JSON file is invalid:
        catch (Exception e) {
            System.err.println(e);
        }
    
    }
    /**
     * Parses the JSON object and saves data to private attributes: favorites and preferences
     * @param data JSON object to be parsed
     * @throws Exception if the string that represents currency in the JSON file is invalid
     */
    private void parse_json_object(JsonObject data) throws Exception{
        
        // go through all the favorite flight
        for (JsonElement flight_json : data.get("favorites").getAsJsonArray()){
            
            // read all indivitual properties from the flight JSON object
            int flightID = flight_json.getAsJsonObject().get("flightID").getAsInt();
            String departure_airport = flight_json.getAsJsonObject().get("dep_airport").getAsString();
            String destination_airport = flight_json.getAsJsonObject().get("des_airport").getAsString();
            Double flight_price = flight_json.getAsJsonObject().get("price").getAsDouble();
            ArrayList<String> layovers = get_layovers(flight_json.getAsJsonObject().get("layovers").getAsJsonArray());
            Date departure_time = to_date(flight_json.getAsJsonObject().get("dep_time").getAsString());
            Date arrival_time = to_date(flight_json.getAsJsonObject().get("arr_time").getAsString());
            boolean is_overnight = flight_json.getAsJsonObject().get("overnight").getAsBoolean();
            boolean is_often_delayed = flight_json.getAsJsonObject().get("often_delayed").getAsBoolean();
            
            // create Flight obejct
            Flight new_flight = new Flight(
                    flightID,
                    departure_airport,
                    destination_airport,
                    flight_price,
                    layovers,
                    departure_time,
                    arrival_time,
                    is_overnight,
                    is_often_delayed            
                );
            // and save it to Favorites object
            favorites.set_new_favorite(new_flight);
        }
         
        JsonObject pref = data.get("preferences").getAsJsonObject();
        
        // read all indivitua properties from preferences JSON object
        String currency = pref.get("currency").getAsString();
        Double max_price = pref.get("max_price").getAsDouble();
        int number_of_layovers = pref.get("layovers").getAsInt();
        
        // and save it to new preferences object
        preferences = new Preferences(string_to_currency(currency), max_price, number_of_layovers);
        
    }
    /**
     * converts JSON array of strings to ArrayList
     * @param layovers_json JSON array to be converted
     * @return result: resulting ArrayList
     */
    private ArrayList<String> get_layovers(JsonArray layovers_json){
        ArrayList<String> result = new ArrayList<>();
        for (JsonElement layover_json : layovers_json){
            result.add(layover_json.getAsString());
        }
        return result;
    }
    /**
     * converts ArrayList to JSON array of strings
     * @param layovers ArrayList to be converted
     * @return result: resulting JSON array
     */
    private JsonArray get_layovers_as_json(ArrayList<String> layovers){
        JsonArray result = new JsonArray();
        for (String layover : layovers){
            result.add(layover);
        }
        return result; 
    }
    
    /**
     * Written with AI
     * converts Date objects to String
     * needed to save dates in a JSON file
     * @param date Date object to be converted
     * @return converted String
     */
    public static String date_to_string(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(date_format);
        return formatter.format(date);
    }
    
    /**
     * Written with AI
     * converts String to Date objects
     * needed to save dates in a JSON file
     * @param date_str String to be converted
     * @return converted Date object
     */
    public static Date to_date(String date_str){
        SimpleDateFormat formatter = new SimpleDateFormat(date_format);
        Date date = null;
        try {
            date = formatter.parse(date_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * @return saved Favorites object
     */
    public Favorites get_favorites(){
        return favorites;
    }
    /**
     * @return saved Preferences object
     */
    public Preferences get_preferences(){
        return preferences;
    }
    /**
     * converts String to Enum Currency
     * needed to save enum to JSON file
     * @param input String to be converted
     * @return converted Currency
     * @throws Exception if given string does not represent a valid currency
     */
    private Currency string_to_currency(String input) throws Exception{
         switch(input){
            case "USD":
                return Currency.US_DOLLAR;
            case "EUR":
                return Currency.EURO;
            case "GBP":
                return Currency.BR_POUND;
            default:
                throw new Exception("invalid currency: " + input);       
        }       
    }
    /**
     * converts Enum Currency to Sring
     * needed to save enum to JSON file
     * @param input Currency to be converted
     * @return converted String
     */
    private String currency_to_string(Currency input){
        switch(input){
            case US_DOLLAR:
                return "USD";
            case EURO:
                return "EUR";
            case BR_POUND:
                return "GBP";    
            default:
                return null;
        }
    }
    /**
     * writes data from Favorite and Preferences objects to JSON file
     * @param fav Favorite obejct
     * @param pref Preferences object
     */
    public void write_data(Favorites fav, Preferences pref){
        
        // I don't know how this works but this is needed here
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        // saves all favorite flight to JSON array
        JsonArray fav_result = new JsonArray();     // for all favorite flights
        for (Flight flight : fav.get_favorite_flights_by_recent()){
            JsonObject flight_json = new JsonObject();  // for saving new flight
            
            flight_json.addProperty("flightID",         flight.get_flight_id());
            flight_json.addProperty("dep_airport",      flight.get_departure_airport());
            flight_json.addProperty("des_airport",      flight.get_destination_airport());
            flight_json.addProperty("price",            flight.get_price());
            
            flight_json.add("layovers",                 gson.toJsonTree(get_layovers_as_json(flight.get_layovers())));
            
            flight_json.addProperty("dep_time",         date_to_string(flight.get_departure_time()));
            flight_json.addProperty("arr_time",         date_to_string(flight.get_arrival_time()));
            flight_json.addProperty("overnight",        flight.is_overnight());
            flight_json.addProperty("often_delayed",    flight.is_often_delayed());
            
            fav_result.add(flight_json);
        }
        // saves preferences to JSON object
        JsonObject pref_result = new JsonObject();
        pref_result.addProperty("currency", currency_to_string(pref.get_currency()));
        pref_result.addProperty("max_price", pref.get_max_price());
        pref_result.addProperty("layovers", pref.get_leyovers());
        
        JsonObject result = new JsonObject();
        result.add("favorites", fav_result);
        result.add("preferences", pref_result);
        
        // writes result to JSON file        
        try (FileWriter writer = new FileWriter(file_name)) {
            String jsonString = gson.toJson(result);
            writer.write(jsonString);
        }
        catch (IOException e){
            System.err.println("target file not found: " +  e);
        }
    }
}
