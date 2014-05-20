mkdir classes
javac -d classes WeatherDetails.java Weather.java ProcessWeather.java
java -cp classes weather.Weather
/bin/rm -rf classes
