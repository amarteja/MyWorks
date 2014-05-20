package weather;

import java.io.File;
import java.util.*;

public class Weather
{     
    
    public static void main(String[] args)
    {
        ProcessWeather processweather = new ProcessWeather();
        TreeSet<WeatherDetails> weatherSet = new 
                TreeSet<WeatherDetails>();
        try
        {
            List<String> woeidList = processweather.readWoeids();
            long startTime = System.currentTimeMillis();
            for(String woeid : woeidList)
            {
                WeatherDetails data = 
                  processweather.processWoeid(woeid);
                weatherSet.add(data);
            }
            long timeTaken = System.currentTimeMillis() - startTime;

            for(WeatherDetails weatherDetails : weatherSet)
            { 
                weatherDetails.printData();
            }

            System.out.println();
            processweather.printWeatherData();
            System.out.println();
            System.out.println("Time taken : " + timeTaken/1000 + "s");
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
            System.exit(0); 
        }
    }
}