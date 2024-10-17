package fi.tuni.java5.flightweatherapp.flightDataAPI;

/**
 *
 * @author Chu Duc Anh
 */
public class CarbonEmissions {
    public int this_flight;
    public int typical_for_this_route;
    public int difference_percent;

    // Getters
    public int getThisFlight() {
        return this_flight;
    }

    public int getTypicalForThisRoute() {
        return typical_for_this_route;
    }

    public int getDifferencePercent() {
        return difference_percent;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Carbon emission on this flight: ").append(String.valueOf(this_flight)).append("\n")
               .append("Carbon emission typical for this route: ").append(String.valueOf(typical_for_this_route)).append("\n")
               .append("Carbon emission different percentage: ").append(String.valueOf(difference_percent)).append("\n");
               
        
        String carbonEmissionAsString = builder.toString();
        return carbonEmissionAsString;
        
    }
}
