module fi.tuni.java5.flightweatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens fi.tuni.java5.flightweatherapp to javafx.fxml;
    exports fi.tuni.java5.flightweatherapp;
}
