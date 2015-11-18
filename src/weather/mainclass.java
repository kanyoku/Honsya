package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by mist36 on 2015/11/18.
 */
public class mainclass {

    public static void main(String args[]){
        executeGet();

    }

    private static void executeGet(){
        System.out.println("========start========");
        try{
            URL url = new URL("http://weather.livedoor.com/forecast/webservice/json/v1?city=130010");
            HttpURLConnection connection = null;

            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);

                         BufferedReader reader = new BufferedReader(isr)) {
                            String line;

                            while ((line = reader.readLine()) != null){
                                System.out.println(line);
                            }
                    }
            }finally{
                if(connection != null){
                    connection.disconnect();
                }
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ProtocolException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("========end========");
    }


}
