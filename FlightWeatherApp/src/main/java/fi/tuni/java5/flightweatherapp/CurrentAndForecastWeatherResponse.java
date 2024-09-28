package fi.tuni.java5.flightweatherapp;

/**
 * The `CurrentAndForecastWeatherResponse` class represents the One Call API 3.0 response.
 * It contains the latitude, longitude, timezone, timezone offset, current weather data,
 * minute forecast for the next hour, hourly forecast for the next 48 hours, daily forecast
 * for the next 8 days, and government weather alerts.
 *
 * @author Nguyen Quang Duc
 * @version 2.0
 * @since 2023-12-06
 */
public class CurrentAndForecastWeatherResponse {

    /**
     * The latitude of the location
     */
    public double lat;

    /**
     * The longitude of the location
     */
    public double lon;

    /**
     * The timezone of the location
     */
    public String timezone;

    /**
     * The timezone offset in seconds
     */
    public int timezone_offset;

    /**
     * The current weather data
     */
    public Current current;

    /**
     * The minute forecast for the next hour
     */
    public Minutely[] minutely;

    /**
     * The hourly forecast for the next 48 hours
     */
    public Hourly[] hourly;

    /**
     * The daily forecast for the next 8 days
     */
    public Daily[] daily;

    /**
     * The government weather alerts
     */
    public Alert[] alerts;

    /**
     * The `Current` class represents the current weather data.
     * It contains the current time, sunrise time, sunset time, temperature, atmospheric pressure,
     * humidity, dew point, UV index, cloudiness, visibility, wind speed, wind direction, wind gust,
     * weather condition, and precipitation volume for the last hour.
     */
    public static class Current {

        /**
         * The current time, Unix, UTC
         */
        public long dt;

        /**
         * The sunrise time, Unix, UTC
         */
        public long sunrise;

        /**
         * The sunset time, Unix, UTC
         */
        public long sunset;

        /**
         * The current temperature
         */
        public double temp;

        /**
         * The current temperature accounting for human perception
         */
        public double feels_like;

        /**
         * The atmospheric pressure on the sea level, hPa
         */
        public int pressure;

        /**
         * The humidity percentage
         */
        public int humidity;

        /**
         * The atmospheric temperature below which water droplets begin to condense
         */
        public double dew_point;

        /**
         * The UV index
         */
        public double uvi;

        /**
         * The cloudiness percentage
         */
        public int clouds;

        /**
         * The average visibility, meters
         */
        public int visibility;

        /**
         * The wind speed
         */
        public double wind_speed;

        /**
         * The wind direction, degrees (meteorological)
         */
        public int wind_deg;

        /**
         * The wind gust
         */
        public double wind_gust;

        /**
         * The weather condition
         */
        public Weather[] weather;

        /**
         * The precipitation volume for the last hour, mm
         */
        public Precipitation rain;

        /**
         * The snow volume for the last hour, mm
         */
        public Precipitation snow;

        /**
         * A class to represent the precipitation volume.
         */
        public static class Precipitation {

            /**
             * The precipitation volume for the last hour, mm
             */
            public double _1h;
        }
    }

    /**
     * The `Minutely` class represents the minute forecast for the next hour.
     * It contains the time of the forecasted data and the precipitation volume.
     */
    public static class Minutely {

        /**
         * The time of the forecasted data, Unix, UTC
         */
        public long dt;

        /**
         * The precipitation volume, mm
         */
        public double precipitation;
    }

    /**
     * The `Hourly` class represents the hourly forecast for the next 48 hours.
     * It contains the time of the forecasted data, temperature, atmospheric pressure,
     * humidity, dew point, UV index, cloudiness, visibility, wind speed, wind direction,
     * wind gust, weather condition, probability of precipitation, and precipitation volume
     * for the last hour.
     */
    public static class Hourly {

        /**
         * The time of the forecasted data, Unix, UTC
         */
        public long dt;

        /**
         * The temperature
         */
        public double temp;

        /**
         * The temperature accounting for human perception
         */
        public double feels_like;

        /**
         * The atmospheric pressure on the sea level, hPa
         */
        public int pressure;

        /**
         * The humidity percentage
         */
        public int humidity;

        /**
         * The atmospheric temperature below which water droplets begin to condense
         */
        public double dew_point;

        /**
         * The UV index
         */
        public double uvi;

        /**
         * The cloudiness percentage
         */
        public int clouds;

        /**
         * The average visibility, meters
         */
        public int visibility;

        /**
         * The wind speed
         */
        public double wind_speed;

        /**
         * The wind direction, degrees (meteorological)
         */
        public int wind_deg;

        /**
         * The wind gust
         */
        public double wind_gust;

        /**
         * The weather condition
         */
        public Weather[] weather;

        /**
         * The probability of precipitation
         */
        public double pop;

        /**
         * The precipitation volume for the last hour, mm
         */
        public Precipitation rain;

        /**
         * The snow volume for the last hour, mm
         */
        public Precipitation snow;

        /**
         * A class to represent the precipitation volume.
         */
        public static class Precipitation {

            /**
             * The precipitation volume for the last hour, mm
             */
            public double _1h;
        }
    }

    /**
     * The `Daily` class represents the daily forecast for the next 8 days.
     * It contains the time of the forecasted data, sunrise time, sunset time, moonrise time,
     * moonset time, moon phase, human-readable description of the weather conditions for the day,
     * temperature, atmospheric pressure, humidity, dew point, wind speed, wind direction, wind gust,
     * weather condition, cloudiness, probability of precipitation, precipitation volume, snow volume,
     * and UV index.
     */
    public static class Daily {

        /**
         * The time of the forecasted data, Unix, UTC
         */
        public long dt;

        /**
         * The sunrise time, Unix, UTC
         */
        public long sunrise;

        /**
         * The sunset time, Unix, UTC
         */
        public long sunset;

        /**
         * The moonrise time, Unix, UTC
         */
        public long moonrise;

        /**
         * The moonset time, Unix, UTC
         */
        public long moonset;

        /**
         * The moon phase
         */
        public double moon_phase;

        /**
         * The human-readable description of the weather conditions for the day
         */
        public String summary;

        /**
         * The temperature
         */
        public Temperature temp;

        /**
         * The temperature accounting for human perception
         */
        public Temperature feels_like;

        /**
         * The atmospheric pressure on the sea level, hPa
         */
        public int pressure;

        /**
         * The humidity percentage
         */
        public int humidity;

        /**
         * The atmospheric temperature below which water droplets begin to condense
         */
        public double dew_point;

        /**
         * The wind speed
         */
        public double wind_speed;

        /**
         * The wind direction, degrees (meteorological)
         */
        public int wind_deg;

        /**
         * The wind gust
         */
        public double wind_gust;

        /**
         * The weather condition
         */
        public Weather[] weather;

        /**
         * The cloudiness percentage
         */
        public int clouds;

        /**
         * The probability of precipitation
         */
        public double pop;

        /**
         * The precipitation volume, mm
         */
        public double rain;

        /**
         * The snow volume, mm
         */
        public double snow;

        /**
         * The UV index
         */
        public double uvi;

        /**
         * A class to represent the temperature.
         */
        public static class Temperature {

            /**
             * The temperature for the day
             */
            public double day;

            /**
             * The minimum temperature
             */
            public double min;

            /**
             * The maximum temperature
             */
            public double max;

            /**
             * The temperature for the night
             */
            public double night;

            /**
             * The temperature for the evening
             */
            public double eve;

            /**
             * The temperature for the morning
             */
            public double morn;
        }
    }

    /**
     * The `Weather` class represents the weather condition.
     * It contains the weather condition id, group of weather parameters,
     * weather condition within the group, and weather icon id.
     */
    public static class Weather {

        /**
         * The weather condition id
         */
        public int id;

        /**
         * The group of weather parameters
         */
        public String main;

        /**
         * The weather condition within the group
         */
        public String description;

        /**
         * The weather icon id
         */
        public String icon;
    }

    /**
     * The `Alert` class represents the government weather alerts.
     * It contains the name of the alert source, alert event name, start time of the alert,
     * end time of the alert, alert description, and alert tags.
     */
    public static class Alert {

        /**
         * The name of the alert source
         */
        public String sender_name;

        /**
         * The alert event name
         */
        public String event;

        /**
         * The start time of the alert, Unix, UTC
         */
        public long start;

        /**
         * The end time of the alert, Unix, UTC
         */
        public long end;

        /**
         * The alert description
         */
        public String description;

        /**
         * The alert tags
         */
        public String[] tags;
    }
}

