package weather;

import java.net.*;
import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import weather.WeatherDetails;
import java.util.concurrent.Callable;

public class ProcessWeather 
{
  private WeatherDetails maxTemp =
    new WeatherDetails("", "", 0, 0, "", -100000);
  private WeatherDetails minTemp =
    new WeatherDetails("", "", 0, 0, "", 100000);
  
  WeatherDetails dataOpt;

  public class FastProcess implements Callable<WeatherDetails>
  {
    private String woeid;

    FastProcess(String woeid)
    {
      this.woeid = woeid;
    }

    public WeatherDetails call() throws Exception
    {
      dataOpt = processWoeid(woeid);
      return dataOpt;
    }
  }
  
  public WeatherDetails processWoeid(String woeid) throws Exception
  {
    String url = "http://weather.yahooapis.com/forecastrss?w=WOEID&u=f";
    String newUrl = url.replace("WOEID", woeid);
    URL urlContent = new URL(newUrl);
    Thread.sleep(1);    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(urlContent.openStream());
        
    NodeList locationData = doc.getElementsByTagName("yweather:location");
    NodeList conditionData = doc.getElementsByTagName("yweather:condition");
    NodeList temperatureData = doc.getElementsByTagName("yweather:forecast");
     
    Element locationAttributes = (Element)locationData.item(0);
    Element conditionAttribute = (Element)conditionData.item(0);
    Element temperatureAttributes = (Element)temperatureData.item(0);
    
    String city = locationAttributes.getAttribute("city");
    String state = locationAttributes.getAttribute("region");
    String condition = conditionAttribute.getAttribute("text");
    int high = Integer.parseInt(temperatureAttributes.getAttribute("high"));
    int low = Integer.parseInt(temperatureAttributes.getAttribute("low")); 
    int curTemp = Integer.parseInt(conditionAttribute.getAttribute("temp"));
    
    WeatherDetails data = new WeatherDetails(
      state, city, high, low, condition, curTemp);
      
    processMinMax(data, maxTemp, minTemp);
    return data;
  }
        
  private synchronized void processMinMax(WeatherDetails data, 
                   WeatherDetails maxTemp, WeatherDetails minTemp) 
  {
    if( data.curTemp < minTemp.curTemp)
    {
        this.minTemp = data;
    }
    if( data.curTemp > maxTemp.curTemp)
    {
        this.maxTemp = data;
    }
  }
  
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
  
  public void printWeatherData()
  {
    System.out.println("Current Highest temperature is "
           + getMaxTemp().curTemp + " F in city "
           + getMaxTemp().city + " in State "
           + getMaxTemp().state);
    System.out.println();
    System.out.println("Current Lowest temperature is "
           + getMinTemp().curTemp + " F in city " 
           + getMinTemp().city + " in State " 
           + getMinTemp().state);
    System.out.println();
  }
    
  public WeatherDetails getMaxTemp() 
  {
    return maxTemp;
  }

  public WeatherDetails getMinTemp() 
  {
    return minTemp;
  }

}