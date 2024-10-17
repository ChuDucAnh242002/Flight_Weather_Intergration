module fi.tuni.java5.flightweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;

    opens fi.tuni.java5.flightweatherapp to javafx.fxml;
    exports fi.tuni.java5.flightweatherapp;
    
    opens fi.tuni.java5.flightweatherapp.weatherAPI to com.google.gson;
    opens fi.tuni.java5.flightweatherapp.airportDataAPI to com.google.gson;
    opens fi.tuni.java5.flightweatherapp.flightDataAPI to com.google.gson;
}
