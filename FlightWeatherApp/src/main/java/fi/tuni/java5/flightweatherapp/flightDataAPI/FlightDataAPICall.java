package fi.tuni.java5.flightweatherapp.flightDataAPI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author ASUS
 */
public class FlightDataAPICall {
    
    /**
     * The function makes a request by FlightDataRequest class
     * 
     * @param FlightDataRequest 
     * @return FlightDataResponse the response contain of best flights
     */
    public static FlightDataResponse RequestFlightDataAPI(FlightDataRequest flightDataRequest) {
        try {
            
            String apiUri = generateAPIURI(flightDataRequest);
            if (apiUri == null){
                System.out.println("Invalid request");
                return null;
            }
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUri))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            
            Gson gson = new Gson();
            
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            
            if(jsonResponse.has("error")) {
                String errorMessage = jsonResponse.get("error").getAsString();
                System.out.println("Error: " + errorMessage);
                return null;
            }
            
            FlightDataResponse flightDataResponse = new FlightDataResponse();
            
            JsonArray bestFlightArray = jsonResponse.get("best_flights").getAsJsonArray();
            if(bestFlightArray.size() != 0) {
                for(JsonElement bestFlightElement : bestFlightArray) {
                    FlightData bestFlight = gson.fromJson(bestFlightElement, FlightData.class);
                    flightDataResponse.addBestFlight(bestFlight);
                }
            }
            
            JsonArray otherFlightArray = jsonResponse.get("other_flights").getAsJsonArray();
            if(otherFlightArray.size() != 0) {
                for(JsonElement otherFlightElement : otherFlightArray) {
                    FlightData otherFlight = gson.fromJson(otherFlightElement, FlightData.class);
                    flightDataResponse.addOtherFlight(otherFlight);
                }
            }
            
            return flightDataResponse;
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Flight API call error");
            e.printStackTrace();
            
            return null;
        }
    }
    
    private static String generateAPIURI(FlightDataRequest flightDataRequest) {
        String engine = flightDataRequest.getEngine();
        String apiKey = flightDataRequest.getApiKey();
        
        StringBuilder apiUriBuilder = new StringBuilder();
        apiUriBuilder.append("https://serpapi.com/search.json?")
                     .append("engine=").append(engine)
                     .append("&api_key=").append(apiKey);
        
        String departureId = flightDataRequest.getDepartureId();
        if(departureId == null) {
            System.out.println("Departure Id can't be empty");
            return null;
        } 
        else {
            apiUriBuilder.append("&departure_id=").append(departureId);
        }
        
        
        String arrivalId = flightDataRequest.getArrivalId();
        if(arrivalId == null) {
            System.out.println("Arrival Id can't be empty");
            return null;
        }
        else {
            apiUriBuilder.append("&arrival_id=").append(arrivalId);
        }
        
        String outboundDate = flightDataRequest.getOutboundDate();
        if(outboundDate == null) {
            System.out.println("Outbound date can't be empty");
            return null;
        }
        else {
            apiUriBuilder.append("&outbound_date=").append(outboundDate);
        }
        
        String type = String.valueOf(flightDataRequest.getType());
        String returnDate = flightDataRequest.getReturnDate();
        if(type.equals("1") && returnDate == null){
            System.out.println("Return date can't be empty in round trip");
            return null;
        }
        else if(type.equals("1") && returnDate != null) {
            apiUriBuilder.append("&return_date=").append(returnDate);
        } else if(type.equals("2")){
            apiUriBuilder.append("&type=").append(type);
        }
        
        String currency = flightDataRequest.getCurrency();
        String adults = String.valueOf(flightDataRequest.getAdults());
        String children = String.valueOf(flightDataRequest.getChildren());
        String stops = String.valueOf(flightDataRequest.getStops());
        String maxPrice = String.valueOf(flightDataRequest.getMaxPrice());
        if(!currency.equals("USD")){
            apiUriBuilder.append("&currency=").append(currency);
        }
        if(flightDataRequest.getAdults() != 1){
            apiUriBuilder.append("&adults=").append(adults);
        }
        if(flightDataRequest.getChildren() != 0){
            apiUriBuilder.append("&children=").append(children);
        }
        if(flightDataRequest.getStops() != 0){
            apiUriBuilder.append("&stops=").append(stops);
        }
        if(flightDataRequest.getMaxPrice() != 0){
            apiUriBuilder.append("&max_price=").append(maxPrice);
        }
        
        String apiUri = apiUriBuilder.toString();
        return apiUri;
    }
}
