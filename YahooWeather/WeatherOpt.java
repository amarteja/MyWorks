package weather;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WeatherOpt
{     

    public static List<Future<WeatherDetails>> 
    ServiceExecute(Collection<Callable<WeatherDetails>> XmlGetParse)
            throws InterruptedException
    {
        ExecutorService executor = Executors.newFixedThreadPool(25);
        List<Future<WeatherDetails>> results = 
                 executor.invokeAll(XmlGetParse);
        executor.shutdown();
        return results;
    }

    public static void main(String[] args)
    {
        TreeSet<WeatherDetails> weatherSet = new 
                TreeSet<WeatherDetails>();
        try
        {   
            ProcessWeather process = new ProcessWeather();
            List<String> woeidList = process.readWoeids();
            long startTime = System.currentTimeMillis();

            Collection<Callable<WeatherDetails>> XmlGetParse = 
                 new ArrayList<Callable<WeatherDetails>>();

            for(String woeid : woeidList)
            {
                XmlGetParse.add(process.new FastProcess(woeid));
            }

            List<Future<WeatherDetails>> results = 
                 ServiceExecute(XmlGetParse);

            for(Future<WeatherDetails> result : results)
            {
                WeatherDetails data = result.get();
                weatherSet.add(data);
            }

            long timeTaken = System.currentTimeMillis() - startTime;

            for(WeatherDetails weatherDetails : weatherSet)
            { 
                weatherDetails.printData();
            }

            System.out.println();
            process.printWeatherData();
            System.out.println();
            System.out.println("Time taken after optimization : " 
                    + timeTaken/1000   + "s");
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
            System.exit(0); 
        }
    }

}