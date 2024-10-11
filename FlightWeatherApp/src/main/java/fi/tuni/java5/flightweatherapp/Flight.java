
package fi.tuni.java5.flightweatherapp;

import java.util.ArrayList;
import java.util.Date;


public class Flight {
    
    public int flightID;
    public String departure_airport;
    public String destination_airport;
    public ArrayList<String> layovers;
    public Date departure_time;
    public Date arrival_time;
    public boolean is_overnight;
    public boolean is_often_delayed;
    
    public Flight(
            int id,
            String dep_airport,
            String des_airport,
            ArrayList<String> layovers_,
            Date dep_time,
            Date arr_time,
            boolean overnight,
            boolean often_delayed
            ){
        
        flightID = id;
        departure_airport = dep_airport;
        destination_airport = des_airport;
        layovers = layovers_;
        departure_time = dep_time;
        arrival_time = arr_time;
        is_overnight = overnight;
        is_often_delayed = often_delayed;
    }
    
    public int get_flight_id(){
        return flightID;
    }
    public String get_departure_airport(){
        return departure_airport;
    }
    public String get_destination_airport(){
        return destination_airport;
    }
    public ArrayList<String> get_layovers(){
        return layovers;
    }
    public Date get_departure_time(){
        return departure_time;
    }
    public Date get_arrival_time(){
        return arrival_time;
    }
    public boolean is_overnight(){
        return is_overnight;
    }
    public boolean is_often_delayed(){
        return is_often_delayed;
    }
    
}
