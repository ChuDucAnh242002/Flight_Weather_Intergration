package fi.tuni.java5.flightweatherapp.airportDataAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


/**
 *
 * @author Chu Duc Anh
 */
public class AirportDataAPICall {
    private static final String NINJA_API_KEY = "oCFqyGRKgTOJXwbzxwZMIg==zdgkY01L1T5vEVJ9";
    
    public static List<AirportDataResponse> RequestAirportData(){
        
        return getAirportData("Helsinki");
    }
    
    public static List<AirportDataResponse> RequestAirportData(String name) {
        return getAirportData(name);
    } 
    
    public static List<AirportDataResponse> getAirportData(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.api-ninjas.com/v1/airports?name=" + name))
                .header("X-Api-Key", NINJA_API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            
            Gson gson = new Gson();
            
            Type airportListType = new TypeToken<List<AirportDataResponse>>(){}.getType();
            
            List<AirportDataResponse> airports = gson.fromJson(response.body(), airportListType);
            return airports;
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Airport Data API call error");
            e.printStackTrace();
            return null;
        }
    }
}
