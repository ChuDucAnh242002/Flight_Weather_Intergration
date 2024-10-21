
package fi.tuni.java5.flightweatherapp.settingManagement;

/**
 * @author Kalle Hirvijärvi
 * 
 * stores preferences currency, max price and the number of layovers
 * All values must be set in the constructor
 */
public class Preferences {
    
    private Currency currency_;
    private Double max_price_;
    private int layovers_;
    
    
    public Preferences(Currency currency, Double max_price, int layovers){
        currency_ = currency;
        max_price_ = max_price;
        layovers_ = layovers;
    }
    public Currency get_currency(){
        return currency_;
    }
    public Double get_max_price(){
        return max_price_;
    }
    public int get_leyovers(){
        return layovers_;
    }
    
    public void set_currency(Currency currency){
        currency_ = currency;
    }
    public void set_max_price(Double max_price){
        max_price_ = max_price;
    }
    public void set_layovers(int layovers){
        layovers_ = layovers;
    }
   
}
