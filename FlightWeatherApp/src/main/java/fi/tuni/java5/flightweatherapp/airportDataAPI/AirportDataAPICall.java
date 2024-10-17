package fi.tuni.java5.flightweatherapp.airportDataAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * The AirportAPICall class contains method to interact with the Ninja API 
 * The class get airport-related data such as airport's name, IATA code, latitude, and longitude
 * 
 * 
 * @author Chu Duc Anh
 */
public class AirportDataAPICall {
    private static final String NINJA_API_KEY = "oCFqyGRKgTOJXwbzxwZMIg==zdgkY01L1T5vEVJ9";
    
    /**
     * The function makes a request by airport's name to get the airport's data
     * 
     * @param String airport's name default is "Helsinki"
     * @return List<AirportDataResponse> The list of airport data response related to given airport's name
     * The airport data response contains of airport's name, IATA code, latitude, and longitude
     */
    public static List<AirportDataResponse> RequestAirportDataByAirportName(){
        
        return getAirportDataByAirportName("Helsinki");
    }
    
    public static List<AirportDataResponse> RequestAirportDataByAirportName(String name) {
        return getAirportDataByAirportName(name);
    } 
    
    /**
     * The function makes a request by airport's IATA code to get the airport's data
     * 
     * @param String airport's IATA code, default is "HEL
     * @return List<AirportDataResponse> The list of airport data response related to given airport's IATA code
     * The airport data response contains of airport's name, IATA code, latitude, and longitude
     */
    public static List<AirportDataResponse> RequestAirportDataByAirportCode() {
        return getAirportDataByAirportCode("HEL");
    }
    
    public static List<AirportDataResponse> RequestAirportDataByAirportCode(String iata) {
        return getAirportDataByAirportCode(iata);
    }
    
    private static List<AirportDataResponse> getAirportDataByAirportName(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.api-ninjas.com/v1/airports?name=" + name))
                .header("X-Api-Key", NINJA_API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        
        return getAirportData(request);
    }
    
    private static List<AirportDataResponse> getAirportDataByAirportCode(String iata) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.api-ninjas.com/v1/airports?iata=" + iata))
                .header("X-Api-Key", NINJA_API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        
        return getAirportData(request);
    }
    
    private static List<AirportDataResponse> getAirportData(HttpRequest request) {
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
