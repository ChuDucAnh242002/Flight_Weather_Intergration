package fi.tuni.java5.flightweatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

/**
 * The `GeoCodingAPICall` class represents a call to the OpenWeatherMap GeoCoding API.
 * It contains the request parameters for the API call and a method to request current weather data.
 * 
 * The `GeoCodingRequest` class represents the request parameters for the API call.
 * The `GeoCodingResponse` class represents the response for the API call.
 * 
 * @author Nguyen Quang Duc
 * @version 1.0
 * @since 2023-12-06
 */
public class GeoCodingAPICall 
{

    /**
     *
     */
    public static GeoCodingRequest searchPlaceRequest;

    /**
     * Requests current weather data from the OpenWeatherMap GeoCoding API.
     *
     * @return An array of `GeoCodingResponse` objects containing the search results.
     */
    public static GeoCodingResponse[] RequestCurrentWeatherData() {
        try {        
            String apiUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + searchPlaceRequest.getCityName() + 
                    "&appid=" + searchPlaceRequest.getAppid()+ "&limit=" + searchPlaceRequest.getLimit();

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // JSON response
            String jsonResponse = response.toString();

            // Parse the JSON using Gson
            Gson gson = new Gson();
            GeoCodingResponse[] searchResponses = gson.fromJson(jsonResponse, GeoCodingResponse[].class);

            connection.disconnect();
            
            return searchResponses;
            
        } catch (Exception e) {
            e.printStackTrace();
            
            return new GeoCodingResponse[0];
        }
    }
}
