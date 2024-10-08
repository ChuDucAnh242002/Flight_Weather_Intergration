package fi.tuni.java5.flightweatherapp.weatherAPI;

import java.util.Map;

/**
 * The `GeoCodingResponse` class represents the response for the OpenWeatherMap GeoCoding API call.
 * It contains five fields: `name`, `localNames`, `lat`, `lon`, `country`, and `state`.
 * 
 * The `name` field represents the name of the location.
 * The `localNames` field represents the name of the location in different languages.
 * The `lat` field represents the latitude of the location.
 * The `lon` field represents the longitude of the location.
 * The `country` field represents the country of the location.
 * The `state` field represents the state of the location.
 * 
 * @author Nguyen Quang Duc
 * @version 1.0
 * @since 2023-12-06
 */
public class GeoCodingResponse 
{
    // The name of the location

    /**
     *
     */
    public String name;
    // The name of the location in different languages

    /**
     *
     */
    public Map<String, String> localNames;
    // The latitude of the location

    /**
     *
     */
    public double lat;
    // The longitude of the location

    /**
     *
     */
    public double lon;
    // Country of the location

    /**
     *
     */
    public String country;
    // State of the location

    /**
     *
     */
    public String state;
}
