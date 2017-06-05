
package googleurlshortener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Rubens Rangel e Rafael kogler
 */
public class GoogleURLShortener {

    //POST https://www.googleapis.com/urlshortener/v1/url?key={YOUR_API_KEY}
    //GET https://www.googleapis.com/urlshortener/v1/url?shortUrl=https%3A%2F%2Fgoo.gl%2FuUOEtl&key={YOUR_API_KEY}
 
    
    public static final String GOOGLE_URL_SHORTENER = "https://www.googleapis.com/urlshortener/v1/url?";
    public static final String API_KEY = "AIzaSyDFuJ6p73p49wVr-FGsNmYuUJPP1JcaQ6I";

    public static String solicitaShortenUrl(String longUrl){
 
        String data = "{\"longUrl\": \"" + longUrl + "\"}";
        try {
            URL url = new URL(GOOGLE_URL_SHORTENER + "key=" + API_KEY);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "toolbar");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
            output.write(data);
            output.flush();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = response.readLine()) != null) {
                result += line;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(result, Map.class);

            output.close();
            response.close();

            return (String) map.get("id");

        } catch (Exception e) {
           return longUrl;
        }

    }
 
    public static String solicitaLongUrl(String shortUrl){
        
        try {
            
            URL url = new URL(GOOGLE_URL_SHORTENER + "shortUrl="+ shortUrl + "&key=" + API_KEY);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "toolbar");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = response.readLine()) != null) {
                result += line;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(result, Map.class);

            response.close();

            return (String) map.get("longUrl");

        } catch (Exception e) {
           return shortUrl;
        }
        
    }
   
    public static void main(String[] args) throws Exception{
        //String longURL = "https://console.developers.google.com";
        
        String shortURL = "https://goo.gl/oPjEOY";
        
       //System.out.println("Short Url: "+solicitaShortenUrl(longURL));
        
        System.out.println("Long Url: "+solicitaLongUrl(shortURL));
        
    }
    
}