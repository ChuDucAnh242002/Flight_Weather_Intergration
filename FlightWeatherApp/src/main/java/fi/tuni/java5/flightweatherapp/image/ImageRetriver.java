package fi.tuni.java5.flightweatherapp.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * ImageRetriver class will get the image from the URL link
 * and store it into FXML Image or ImageView component
 * 
 * @author Chu Duc Anh
 */
public class ImageRetriver {
    
    /**
     * The function retrieve image from the URL link
     * 
     * @param String the URL link retrieved from the Flight of SearchResultCard
     * @return Image the FXML image component got from the URL String
     */
    public static Image retrieveImage(String urlString) {
        if (urlString == null || urlString.isEmpty()) {
            System.out.println("URL is null or empty");
            return null;
        }
        
        try {
            URL url = new URL(urlString);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
            // Check if the response code is 200 (OK)
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Failed to fetch image. HTTP error code: " + connection.getResponseCode());
                return null;
            }
            
            InputStream inputStream = connection.getInputStream();
            
            return new Image(inputStream);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while retrieving the image: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * The function retrieve image from the URL link
     * 
     * @param String the URL link retrieved from the Flight of SearchResultCard
     * @return ImageView the FXML image view component got from the URL String
     */
    public static ImageView retriveImageView(String urlString) { 
        Image image = retrieveImage(urlString);
        if (image == null) {
            return null;
        }
        
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
