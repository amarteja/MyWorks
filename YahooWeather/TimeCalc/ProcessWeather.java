package weather;

import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
	
/*
Venkat:
Web request time 4.61E-4
XML processing time 0.0
Web request time 6.0E-5
XML processing time 0.0
Web request time 5.3E-5
XML processing time 0.0
...
*/

public class ProcessWeather
{
  private WeatherDetails maxTemp =
    new WeatherDetails("", "", 0, 0, "", -100000/*,0*/);
  private WeatherDetails minTemp =
    new WeatherDetails("", "", 0, 0, "", 100000/*,0*/);
  
  public WeatherDetails processWoeid(String woeid) throws Exception
  {
    long startTimeWebReq = System.nanoTime();
    String url = "http://weather.yahooapis.com/forecastrss?w=WOEID&u=f";
    String newUrl = url.replace("WOEID", woeid);
    URL urlContent = new URL(newUrl);
    long endTimeWebReq = System.nanoTime();
    System.out.println(
      "Web request time " + (endTimeWebReq - startTimeWebReq)/1.0e9);
        
    long startTime = System.nanoTime();
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

    long endTime = System.nanoTime();
    System.out.println(
      "XML processing time " + (endTime - startTime)/1.0e9);
    
    WeatherDetails data = new WeatherDetails(
      state, city, high, low, condition, curTemp/*, timeTaken*/);
      
    long minMaxstart = System.nanoTime();
    processMinMax(data, maxTemp, minTemp);
    long minMaxend = System.nanoTime();
    System.out.println(
      "MinMax processing time " + (minMaxend - minMaxstart)/1.0e9);
    return data;
  }
        
  private void processMinMax(WeatherDetails data, WeatherDetails maxTemp, WeatherDetails minTemp) 
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

  public WeatherDetails getMaxTemp() 
  {
      return maxTemp;
  }

  public WeatherDetails getMinTemp() 
  {
      return minTemp;
  }

}