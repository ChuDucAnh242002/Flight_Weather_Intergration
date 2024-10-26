
package fi.tuni.java5.flightweatherapp.settingManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Airport;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Layover;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    
    }
    /**
     * Parses the JSON object and saves data to private attributes: favorites and preferences
     * @param data JSON object to be parsed
     * @throws Exception if the string that represents currency in the JSON file is invalid
     */
    private void parse_json_object(JsonObject data){
        
        for (SearchResultCard new_result : read_flights_from_array(data.get("favorites").getAsJsonArray())){
            favorites.set_new_favorite(new_result);
        }
         
        JsonObject pref = data.get("preferences").getAsJsonObject();
        
        // read all indivitua properties from preferences JSON object
        String currency = pref.get("currency").getAsString();
        Double max_price = pref.get("max_price").getAsDouble();
        int number_of_layovers = pref.get("num_of_layovers").getAsInt();
        
        // and save it to new preferences object
        preferences = new Preferences(currency, max_price, number_of_layovers);
        
    }
    private List<SearchResultCard> read_flights_from_array(JsonArray input){
        
        List<SearchResultCard> result = new ArrayList<>();
        // go through all the favorite flight
        for (JsonElement flight_card_json : input){
            
            JsonObject current = flight_card_json.getAsJsonObject();
            
            List<Flight> flight_list = new ArrayList<>();
            // read all indivitual properties from the flight JSON object
            for (JsonElement flight_json : current.get("flights").getAsJsonArray()){
                JsonObject current_flight = flight_json.getAsJsonObject();
                
                String dep_airport_name = current_flight.get("dep_airport_name").getAsString();
                String dep_airport_id = current_flight.get("dep_airport_id").getAsString();
                String dep_airport_time = current_flight.get("dep_airport_time").getAsString();
                String arr_airport_name = current_flight.get("arr_airport_name").getAsString();
                String arr_airport_id = current_flight.get("arr_airport_id").getAsString();
                String arr_airport_time = current_flight.get("arr_airport_time").getAsString();
                int flight_duration = current_flight.get("duration").getAsInt();
                String airplane = current_flight.get("airplane").getAsString();
                String airline_logo = current_flight.get("airline_logo").getAsString();
                String travel_class = current_flight.get("class").getAsString();
                String flight_number = current_flight.get("flight_num").getAsString();
                List<String> tickets_sold_by = JSONArray_to_List(current_flight.get("tickets_sold_by").getAsJsonArray());
                List<String> extentions = JSONArray_to_List(current_flight.get("extensions").getAsJsonArray());
                
                Flight new_flight = new Flight();
                Airport dep_airport = new Airport();
                Airport arr_airport = new Airport();
                
                dep_airport.name = dep_airport_name;
                dep_airport.id = dep_airport_id;
                dep_airport.time = dep_airport_time;
                new_flight.departure_airport = dep_airport;
                
                arr_airport.name = arr_airport_name;
                arr_airport.id = arr_airport_id;
                arr_airport.time = arr_airport_time;
                new_flight.arrival_airport = arr_airport;
                
                new_flight.duration = flight_duration;
                new_flight.airplane = airplane;
                new_flight.airline_logo = airline_logo;
                new_flight.travel_class = travel_class;
                new_flight.flight_number = flight_number;
                new_flight.ticket_also_sold_by = tickets_sold_by;
                new_flight.extensions = extentions;
                
                flight_list.add(new_flight);
            }
            List<Layover> layover_list = new ArrayList<>();
            for (JsonElement layover_json : current.get("layovers").getAsJsonArray()){
                JsonObject current_layover = layover_json.getAsJsonObject();
                
                int l_duration = current_layover.get("duration").getAsInt();
                String layover_name = current_layover.get("name").getAsString();
                String layover_id = current_layover.get("layover_id").getAsString();
                
                Layover new_layover = new Layover();
                new_layover.duration = l_duration;
                new_layover.name = layover_name;
                new_layover.id = layover_id;
                
                layover_list.add(new_layover);
                
            }
            
            int total_duration = current.get("total_duration").getAsInt();
            int carbon_emissions = current.get("emissions").getAsInt();
            double  price = current.get("price").getAsDouble();
            String type = current.get("type").getAsString();
            String airline_logo = current.get("airline_logo").getAsString();
            String departure_token = current.get("dep_token").getAsString();
            
            
            // create Flight obejct
            SearchResultCard new_flight = new SearchResultCard();
            
            new_flight.flights = flight_list;
            new_flight.layovers = layover_list;
            
            new_flight.total_duration = total_duration;
            new_flight.carbon_emission = carbon_emissions;
            new_flight.price = price;
            new_flight.type = type;
            new_flight.airline_logo = airline_logo;
            new_flight.departure_token = departure_token;
            
            // and save it to Favorites object
            result.add(new_flight);
        }        
        return result;
        
    }
    
    private JsonArray write_flights_to_JSONArray(List<SearchResultCard> input){
        
        JsonArray result = new JsonArray();
        
        for (SearchResultCard result_card : input){
            JsonObject result_card_json = new JsonObject();
            
            JsonArray flights = new JsonArray();
            for (Flight flight : result_card.getFlights()){
                JsonObject flight_json = new JsonObject();
                
                flight_json.addProperty("dep_airport_name", flight.getDepartureAirport().name);
                flight_json.addProperty("dep_airport_id", flight.getDepartureAirport().id);
                flight_json.addProperty("dep_airport_time", flight.getDepartureAirport().time);
                
                flight_json.addProperty("arr_airport_name", flight.getArrivalAirport().name);
                flight_json.addProperty("arr_airport_id", flight.getArrivalAirport().id);
                flight_json.addProperty("arr_airport_time", flight.getArrivalAirport().time);
                
                flight_json.addProperty("duration", flight.getDuration());
                flight_json.addProperty("airplane", flight.getAirplane());
                flight_json.addProperty("airline_logo", flight.getAirlineLogo());
                flight_json.addProperty("class", flight.getTravelClass());
                flight_json.addProperty("flight_num", flight.getFlightNumber());
                
                flight_json.add("tickets_sold_by", List_to_JSONArray(flight.getTicketAlsoSoldBy()));
                flight_json.add("extentions", List_to_JSONArray(flight.getExtensions()));
                
                flights.add(flight_json);
            }
            JsonArray layovers = new JsonArray();
            for (Layover layover : result_card.getLayovers()){
                JsonObject layover_json = new JsonObject();
                
                layover_json.addProperty("duration", layover.getDuration());
                layover_json.addProperty("name", layover.getName());
                layover_json.addProperty("layover_id", layover.getId());
                
                layovers.add(layover_json);
            }
            result_card_json.add("flights", flights);
            result_card_json.add("layovers", layovers);
            
            result_card_json.addProperty("total_duration", result_card.getTotalDuration());
            result_card_json.addProperty("emissions", result_card.getTotalDuration());
            result_card_json.addProperty("price", result_card.getPrice());
            result_card_json.addProperty("type", result_card.getType());
            result_card_json.addProperty("airline_logo", result_card.getAirlineLogo());
            result_card_json.addProperty("dep_token", result_card.getDepartureToken());
            
            result.add(result_card_json);
        }
        return result;
    }
    
    /**
     * converts JSON array of strings to ArrayList
     * @param layovers_json JSON array to be converted
     * @return result: resulting ArrayList
     */
    private List<String> JSONArray_to_List(JsonArray layovers_json){
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
    private JsonArray List_to_JSONArray(List<String> layovers){
        JsonArray result = new JsonArray();
        for (String layover : layovers){
            result.add(layover);
        }
        return result; 
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
     * writes data from Favorite and Preferences objects to JSON file
     * @param fav Favorite obejct
     * @param pref Preferences object
     */
    public void write_data(Favorites fav, Preferences pref){
        
        // I don't know how this works but this is needed here
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        // saves preferences to JSON object
        JsonObject pref_result = new JsonObject();
        pref_result.addProperty("currency", pref.get_currency());
        pref_result.addProperty("max_price", pref.get_max_price());
        pref_result.addProperty("num_oflayovers", pref.get_leyovers());
        
        JsonObject result = new JsonObject();
        result.add("favorites", write_flights_to_JSONArray(favorites.get_favorite_flights_by_dep_time().stream().collect(Collectors.toList())));
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
