package fi.tuni.java5.flightweatherapp.weatherAPI;

import static fi.tuni.java5.flightweatherapp.PrimaryController.getApiKey;
import fi.tuni.java5.flightweatherapp.interfaces.RequestInterface;

/**
 * The `CurrentAndForecastWeatherRequest` class represents a request for current and forecast weather data.
 * It contains the latitude, longitude, API key, and exclude parameter for the request.
 *
 * @author Nguyen Quang Duc
 * @version 1.0
 * @since 2023-12-06
 */
public class CurrentAndForecastWeatherRequest implements RequestInterface {
    // The lat of the location
    private double lat;
    // The lon of the location
    private double lon;
    // The API key
    private String appid;
    // The exclude parameter
    private String exclude;
    
    /**
     * Constructs a new `CurrentAndForecastWeatherRequest` object with default values.
     * The default values are:
     * - `appid`: the API key obtained from `StartScreenController.getApiKey()`.
     * - `lat`: 20.9147215.
     * - `lon`: 106.6735556.
     * - `exclude`: "minutely".
     */
    public CurrentAndForecastWeatherRequest() {
        this.appid = getApiKey();
        this.lat = 20.9147215;
        this.lon = 106.6735556;
        this.exclude = "minutely";
    }
    
    /**
     * Constructs a new `CurrentAndForecastWeatherRequest` object with the specified values.
     *
     * @param appid   The API key to be used for the request.
     * @param lat     The latitude of the location.
     * @param lon     The longitude of the location.
     * @param exclude The exclude parameter for the request.
     */
    public CurrentAndForecastWeatherRequest(String appid, double lat,
            double lon, String exclude) {
        this.appid = appid;
        this.lat = lat;
        this.lon = lon;
        this.exclude = exclude;
    }
    
    /**
     * Constructs a new `CurrentAndForecastWeatherRequest` object with the specified latitude and longitude.
     * The default values are:
     * - `appid`: the API key obtained from `StartScreenController.getApiKey()`.
     * - `exclude`: "minutely".
     *
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     */
    public CurrentAndForecastWeatherRequest(double lat, double lon)
    {
        this.appid = getApiKey();
        this.lat = lat;
        this.lon = lon;
        this.exclude = "minutely";
    }

    /**
     * Returns the API key used for the request.
     *
     * @return The API key used for the request.
     */
    public String getAppId() {
        return appid;
    }

    /**
     * Sets the API key to be used for the request.
     *
     * @param appid The API key to be used for the request.
     */
    public void setAppId(String appid) {
        this.appid = appid;
    }

    /**
     * Returns the latitude of the location.
     *
     * @return The latitude of the location.
     */
    public double getLatitude() {
        return lat;
    }

    /**
     * Sets the latitude of the location.
     *
     * @param lat The latitude of the location.
     */
    public void setLatitude(double lat) {
        this.lat = lat;
    }

    /**
     * Returns the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public double getLongitude() {
        return lon;
    }

    /**
     * Sets the longitude of the location.
     *
     * @param lon The longitude of the location.
     */
    public void setLongitude(double lon) {
        this.lon = lon;
    }

    /**
     * Returns the exclude parameter for the request.
     *
     * @return The exclude parameter for the request.
     */
    public String getExclude() {
        return exclude;
    }

    /**
     * Sets the exclude parameter for the request.
     *
     * @param exclude The exclude parameter for the request.
     */
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }
}
