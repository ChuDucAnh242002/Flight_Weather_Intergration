
package fi.tuni.java5.flightweatherapp.settingManagement;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fi.tuni.java5.flightweatherapp.Flight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Kalle Hirvijärvi
 */
public class SaveData {
    
    private Favorites favorites = new Favorites();
    private Preferences preferences;
    private static final String file_name = "settings.json";
    private static final String date_format = "yyyy-MM-dd HH:mm:ss z";
    
    public SaveData(){
        

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
            System.err.println("Error: file not found ");
        } 
        catch (JsonParseException e) {
            System.err.println("Error: cannot parse JSON: ");
        }
    
    }
    
    private void parse_json_object(JsonObject data){
        
        for (JsonElement flight_json : data.get("favorites").getAsJsonArray()){
            
            int flightID = flight_json.getAsJsonObject().get("flightID").getAsInt();
            String departure_airport = flight_json.getAsJsonObject().get("dep_airport").getAsString();
            String destination_airport = flight_json.getAsJsonObject().get("des_airport").getAsString();
            Double flight_price = flight_json.getAsJsonObject().get("price").getAsDouble();
            ArrayList<String> layovers = get_layovers(flight_json.getAsJsonObject().get("layovers").getAsJsonArray());
            Date departure_time = to_date(flight_json.getAsJsonObject().get("dep_time").getAsString());
            Date arrival_time = to_date(flight_json.getAsJsonObject().get("arr_time").getAsString());
            boolean is_overnight = flight_json.getAsJsonObject().get("overnight").getAsBoolean();
            boolean is_often_delayed = flight_json.getAsJsonObject().get("often_delayed").getAsBoolean();
            
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
            favorites.set_new_favorite(new_flight);
        }
    }
    
    private ArrayList<String> get_layovers(JsonArray layovers_json){
        ArrayList<String> result = new ArrayList<>();
        for (JsonElement layover_json : layovers_json){
            result.add(layover_json.getAsString());
        }
        return result;
    }
    
    
    // written with AI
    public static String date_to_string(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(date_format);
        return formatter.format(date);
    }
    
    // written with AI
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
    public Favorites get_favorites(){
        return favorites;
    }
}
