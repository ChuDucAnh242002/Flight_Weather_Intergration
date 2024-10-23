package fi.tuni.java5.flightweatherapp.weatherAPI;

import static fi.tuni.java5.flightweatherapp.PrimaryController.getApiKey;

/**
 * The `GeoCodingRequest` class represents the request parameters for the OpenWeatherMap GeoCoding API call.
 * It contains three fields: `appid`, `cityName`, and `limit`.
 * 
 * The `appid` field represents the API key for the API call.
 * The `cityName` field represents the name of the city for which the API call is being made.
 * The `limit` field represents the maximum number of search results to be returned by the API call.
 * 
 * The `getAppid` method returns the API key for the API call.
 * The `setAppId` method sets the API key for the API call.
 * The `getCityName` method returns the name of the city for which the API call is being made.
 * The `setCityName` method sets the name of the city for which the API call is being made.
 * The `getLimit` method returns the maximum number of search results to be returned by the API call.
 * The `setLimit` method sets the maximum number of search results to be returned by the API call.
 * 
 * @author Nguyen Quang Duc
 * @version 1.0
 * @since 2023-12-06
 */
public class GeoCodingRequest {
    private String appid;
    private String cityName;
    private int limit;
    
    /**
     * Constructs a new `GeoCodingRequest` object with default values.
     * The default values are:
     * - `appid`: the API key obtained from `StartScreenController.getApiKey()`.
     * - `cityName`: "Da Nang".
     * - `limit`: 3.
     */
    public GeoCodingRequest()
    {
        this.appid = getApiKey();
        this.cityName = "Da Nang";
        this.limit = 3;
    }
    
    /**
     * Constructs a new `GeoCodingRequest` object with the specified values.
     *
     * @param appid The API key to be used for the API call.
     * @param cityName The name of the city for which the API call is being made.
     * @param limit The maximum number of search results to be returned by the API call.
     */
    public GeoCodingRequest(String appid, String cityName, int limit)
    {
        this.appid = appid;
        this.cityName = cityName;
        this.limit = limit;
    }
    
    /**
     * Returns the API key used for the API call.
     *
     * @return The API key used for the API call.
     */
    public String getAppid() {
        return appid;
    }

    /**
     * Sets the API key to be used for the API call.
     *
     * @param appid The API key to be used for the API call.
     */
    public void setAppId(String appid) {
        this.appid = appid;
    }

    /**
     * Returns the name of the city for which the API call is being made.
     *
     * @return The name of the city for which the API call is being made.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the name of the city for which the API call is being made.
     *
     * @param cityName The name of the city for which the API call is being made.
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Returns the maximum number of search results to be returned by the API call.
     *
     * @return The maximum number of search results to be returned by the API call.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the maximum number of search results to be returned by the API call.
     *
     * @param limit The maximum number of search results to be returned by the API call.
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
