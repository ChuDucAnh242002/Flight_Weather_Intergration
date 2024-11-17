
package fi.tuni.java5.flightweatherapp.settingManagement;

/**
 * @author Kalle Hirvijärvi
 * 
 * stores preferences currency, max price and the number of layovers
 * All values must be set in the constructor
 */
public class Preferences {
    
    private String currency_;
    private String temp_unit;   // unit of temperature
    private Double max_price_;  
    private int layovers_;      // max number of layovers
    
    /**
     * paramaters set object attributes
     * @param currency
     * @param temperature_unit
     * @param max_price
     * @param layovers 
     * @throws IllegalArgumentException when currency or temperature unit is not valid
     */
    public Preferences(String currency, String temperature_unit, Double max_price, int layovers){
        currency_ = currency;
        temp_unit = temperature_unit;
        max_price_ = max_price;
        layovers_ = layovers;
        if (!validate_currency(currency)){
            throw new IllegalArgumentException();
        }
        if (!validate_temperature_unit(temperature_unit)){
            throw new IllegalArgumentException();
        }
    }
    // getters
    public String get_currency(){
        return currency_;
    }
    public String get_temperature_unit(){
        return temp_unit;
    }
    public Double get_max_price(){
        return max_price_;
    }
    public int get_leyovers(){
        return layovers_;
    }
    
    // setters for currency and temperature unit setters validate
    // the parameter and return boolean value:
    // valid: true
    // invalid: false
    public boolean set_currency(String currency){
        if (validate_currency(currency)){
            currency_ = currency;
            return true;
        }
        return false;
    }
    public boolean set_temperature_unit(String temperature_unit){
        if (validate_temperature_unit(temperature_unit)){
            temp_unit = temperature_unit;
            return true;
        }
        return false;
    }
    
    // other setters
    public void set_max_price(Double max_price){
        max_price_ = max_price;
    }
    public void set_layovers(int layovers){
        layovers_ = layovers;
    }   
    /**
     * @param input
     * @return true if input is "C" or "F"
     */
    private boolean validate_temperature_unit(String input){
        return input.compareTo("C") == 0 || input.compareTo("F") == 0;
    }
    /**
     * @param input
     * @return true of input is "EUR" or "USD" or "GBP"
     */
    private boolean validate_currency(String input){
        return input.compareTo("EUR") == 0 || input.compareTo("USD") == 0 || input.compareTo("GBP") == 0;
    }
}
