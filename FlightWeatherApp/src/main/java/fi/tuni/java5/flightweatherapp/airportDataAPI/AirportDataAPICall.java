package fi.tuni.java5.flightweatherapp.airportDataAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 *
 * @author Chu Duc Anh
 */
public class AirportDataAPICall {
    private static final String RAPID_API_KEY = "bce51e3fb7mshe11b0268d9ee852p13c20fjsnc74bdd3f0278";
    private static final String RAPID_API_HOST = "aiport-data.p.rapidapi.com";
    
    public static AirportDataResponse RequestAirportData(){
        AirportDataResponse airportDataResponse = new AirportDataResponse();
        return airportDataResponse;
    }
    
    public static AirportDataResponse RequestAirportData(String iata_code) {
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://aiport-data.p.rapidapi.com/getAirportByIATA?iataCode=" + iata_code))
		.header("x-rapidapi-key", RAPID_API_KEY)
		.header("x-rapidapi-host", RAPID_API_HOST)
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
           
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
            JsonObject airport = jsonArray.get(0).getAsJsonObject();
            
            double lat = airport.get("latitude_deg").getAsDouble();
            double lon = airport.get("longitude_deg").getAsDouble();
            
            AirportDataResponse airportDataResponse = new AirportDataResponse(iata_code, lat, lon);
            return airportDataResponse;
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
