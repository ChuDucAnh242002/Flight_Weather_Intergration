package fi.tuni.java5.flightweatherapp.airportDataAPI;
import fi.tuni.java5.flightweatherapp.interfaces.ResponseInterface;

/**
 * The AirportDataResponse contains of data of the airport
 * and will be called by AirportDataAPICall
 * 
 * @author Chu Duc Anh
 */
public class AirportDataResponse implements ResponseInterface {
    private String icao;
    private String iata;
    private String name;
    private String city;
    private String region;
    private String country;
    private int elevation_ft;
    private double latitude;
    private double longitude;
    private String timezone;
    
    public AirportDataResponse(){
        this.icao = "EFHK";
        this.iata = "HEL";
        this.name = "Helsinki Vantaa Airport";
        this.city = "Helsinki";
        this.region = "Uusimaa";
        this.country = "FI";
        this.elevation_ft = Integer.parseInt("179");
        this.latitude = Double.parseDouble("60.317199707");
        this.longitude = Double.parseDouble("24.963300705");
        this.timezone = "Europe/Helsinki";
    }
    
    public AirportDataResponse(String icao, String iata, String name, String city, String region, String country,
                   String elevation_ft, String latitude, String longitude, String timezone) {
        this.icao = icao;
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.region = region;
        this.country = country;
        this.elevation_ft = Integer.parseInt(elevation_ft);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.timezone = timezone;
    }
    
    public String getIcao() {
        return icao;
    }

    public String getIata() {
        return iata;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public int getElevation_ft() {
        return elevation_ft;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }
    
    @Override
    public String toString() {
        return "Airport{" +
                "icao='" + icao + '\'' +
                ", iata='" + iata + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", elevation_ft='" + elevation_ft + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
