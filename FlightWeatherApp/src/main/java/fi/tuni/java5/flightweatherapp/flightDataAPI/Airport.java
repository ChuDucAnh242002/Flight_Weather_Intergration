package fi.tuni.java5.flightweatherapp.flightDataAPI;

/**
 *
 * @author Chu Duc Anh
 */
public class Airport {
    public String name;
    public String id;
    public String time;

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Airport's name: ").append(this.name).append(", Airport's id: ").append(this.id).append(", Airport's time: ").append(this.time).append("\n");
        String airportAsString = builder.toString();
        
        return airportAsString;
    }
}
