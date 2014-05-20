import scala.xml._
import java.net._
import scala.io.Source
import java.lang._
import scala.actors._
import Actor._

class WeatherDetail(val state : String, val city : String, 
  val high : String, val low : String, val currentCondition : String, 
  val curTemp: Integer)
  
val maxTemp = new WeatherDetail("", "", "0", "0", "", -100000);
val minTemp = new WeatherDetail("", "", "0", "0", "", 100000);  

    
def processWoeid(woeid : String) : WeatherDetail = {
  val yahooUrl = "http://weather.yahooapis.com/forecastrss?w=WOEID&u=f"
  val newYahooUrl = yahooUrl.replace("WOEID", woeid);
  val xmlString = Source.fromURL(new URL(newYahooUrl)).mkString
  val xml = XML.loadString(xmlString)

  val city = xml \\ "location" \\ "@city"
  val state = xml \\ "location" \\ "@region"
  val condition = xml \\ "condition" \\ "@text"
  val temperature = xml \\ "condition" \\ "@temp"
  val high = (xml \\ "forecast")(0) \ "@high" 
  val low = (xml \\ "forecast")(0) \ "@low"
  val curTemp = temperature.toString.toInt
  new WeatherDetail( state.toString, city.toString, 
      high.toString, low.toString, condition.toString, curTemp);                                                    
}  

def yweatherNormal(lines : List[String]) = {
    lines.foldLeft(
      (List[WeatherDetail](), minTemp, maxTemp)) { (weatherdetails, line) =>
        val data =  processWoeid(line)
        val minData =  
         if(data.curTemp.intValue() <  weatherdetails._2.curTemp.intValue()) 
          data else weatherdetails._2
        val maxData = 
         if(data.curTemp.intValue() >  weatherdetails._3.curTemp.intValue()) 
          data else weatherdetails._3
        (data :: weatherdetails._1, minData, maxData)
    }
}

def yweatherOptimize(lines : List[String]) = {
    val caller = self
    lines.foreach { line => 
        actor {caller ! processWoeid(line)}
    }
    lines.foldLeft(
	  (List[WeatherDetail](), minTemp, maxTemp)) { (weatherdetails, line) => 
       receive { case(data: WeatherDetail) => 
        val minData = 
          if(data.curTemp.intValue() < weatherdetails._2.curTemp.intValue())
            data else weatherdetails._2 
        val maxData = 
          if(data.curTemp.intValue() >  weatherdetails._3.curTemp.intValue()) 
            data else weatherdetails._3 
        (data :: weatherdetails._1, minData, maxData)                                                     
       }
    }
}

def timeitandPrint(yweatherMethod : List[String] => 
       (List[WeatherDetail], WeatherDetail , WeatherDetail),
       lines : List[String],version: String) = {
    val Start = System.currentTimeMillis()
    val weatherdetails = yweatherMethod(lines)
    val wdsorted = 
        weatherdetails._1.sortBy (data => (data.state, data.city))
    val End = System.currentTimeMillis()
	
    wdsorted.foreach { x => printf("%-5s %-13s %8s %10s %19s %n",
    x.state, x.city, x.high, x.low, x.currentCondition) }

    println();
    println("Current Highest temperature is "
            + weatherdetails._3.curTemp + " F in city "
            + weatherdetails._3.city + " in State "
            + weatherdetails._3.state);

    println();
    println("Current Lowest temperature is "
            + weatherdetails._2.curTemp + " F in city " 
            + weatherdetails._2.city + " in State " 
            + weatherdetails._2.state)
            
    println()           
    println("Time taken by " + version + " version : " +
                                 (End - Start)/1000 + "s") 
    println()           
}

val lines = Source.fromFile("sample.txt").getLines.toList
timeitandPrint(yweatherNormal,lines,"Normal")
timeitandPrint(yweatherOptimize,lines,"Optimized")
