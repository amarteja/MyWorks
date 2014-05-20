mkdir classes
javac -d classes WeatherDetails.java Weather.java ProcessWeather.java
java -cp classes weather.Weather

javac -d classes WeatherDetails.java WeatherOpt.java ProcessWeather.java
java -cp classes weather.WeatherOpt
/bin/rm -rf classes
