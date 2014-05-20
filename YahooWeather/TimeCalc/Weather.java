package weather;

import java.io.File;
import java.util.*;

public class Weather
{     
    public static List<String> readWoeids() throws Exception
    {
        List<String> woeidList = new ArrayList<String>();
        Scanner line = new Scanner(new File("sample.txt"));
        while(line.hasNext())
        {
            woeidList.add(line.next());
        }
        line.close();
        return woeidList;
    }

    public static void printWeatherData(ProcessWeather processweather)
    {
        System.out.println("Current Highest temperature is "
                + processweather.getMaxTemp().curTemp + " F in city "
                + processweather.getMaxTemp().city + " in State "
                + processweather.getMaxTemp().state);
        System.out.println();
        System.out.println("Current Lowest temperature is "
                + processweather.getMinTemp().curTemp + " F in city " 
                + processweather.getMinTemp().city + " in State " 
                + processweather.getMinTemp().state);
        System.out.println();
    }

    public static void main(String[] args)
    {
        ProcessWeather processweather = new ProcessWeather();
        TreeSet<WeatherDetails> weatherSet = new 
                TreeSet<WeatherDetails>();
        try
        {
            List<String> woeidList = readWoeids();
            //long totTime = 0;
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
                //totTime += weatherDetails.timeTaken;
            }

//            System.out.println();
//            printWeatherData(processweather);
//            System.out.println();
//            System.out.println("Time taken : " + timeTaken + "ms");
//            System.out.println("Time taken for parsing: " + totTime + "ms");
            
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
            System.exit(0); 
        }
    }
}