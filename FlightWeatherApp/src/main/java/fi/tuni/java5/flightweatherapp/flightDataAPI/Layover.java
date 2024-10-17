package fi.tuni.java5.flightweatherapp.flightDataAPI;

/**
 *
 * @author Chu Duc Anh
 */
public class Layover {
    public int duration;
    public String name;
    public String id;

    // Getters
    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("Layover Details: \n");
    
        sb.append("Duration: ").append(duration).append(" minutes\n");
        sb.append("Name: ").append(name != null ? name : "N/A").append("\n");
        sb.append("ID: ").append(id != null ? id : "N/A").append("\n");
    
        return sb.toString();
    }
}
