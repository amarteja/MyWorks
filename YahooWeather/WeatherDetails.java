package weather;

public class WeatherDetails implements Comparable<WeatherDetails>
{
   public final String state;
   public final String city;
   public final int high;
   public final int low;
   public final String currentCondition;
   public final int curTemp;
    
   public WeatherDetails(String stateValue, String cityValue,
      int highValue, int lowValue, String currentConditionValue, 
      int curTempValue)
   {
      high = highValue;
      low = lowValue;
      city = cityValue;
      state = stateValue;
      currentCondition = currentConditionValue;
      curTemp = curTempValue;
   }
    
   public void printData()
   {
      System.out.printf("%-5s %-13s %8s %10s %19s %n", 
         state, city, high, low, currentCondition);
   }
   
   
   public int compareTo(WeatherDetails value) 
   {
      int result = this.state.compareTo(value.state);
      return (result==0) ? this.city.compareTo(value.city) : result;
   } 

}