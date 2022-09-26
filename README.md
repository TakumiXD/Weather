# WeatherApp
A weather forecasting android app created with Android Studio (Java). This project also uses a MVP architecture. 

### Screenshots
![gif-1](https://user-images.githubusercontent.com/85015271/177485442-2ca423c1-5755-4286-988e-abf9547b72cc.gif)

### Features
- See the current temperature, conditions, forecast, and other metrics of the user's location.
- See the temperature, conditions, forecast, and other metrics of a searched location (by city name). 
- Add and delete favorite locations.

### Sources, Libraries, and Dependencies
- [AndroidJUnit4](https://developer.android.com/reference/androidx/test/runner/AndroidJUnit4) for unit testing
- [Location](https://developer.android.com/reference/android/location/package-summary) to get user location and location permissions
- [Open Weather API](https://openweathermap.org/api) to get weather data
- [Picasso](https://github.com/square/picasso) to load images on my app provided by the [Open Weather website](https://openweathermap.org/weather-conditions)
- [Room](https://developer.android.com/jetpack/androidx/releases/room) for database access and persistence for favorites feature
- [Volley](https://google.github.io/volley/) to translate JSON text given by Open Weather API to something that could be used by this app

### Usage
1. Clone this repository and open it on Android Studio. 
2. Go to the [OpenWeatherMap](https://openweathermap.org/price#weather) website, choose a plan, and generate an API key. Once you get the API key, copy it.
3. Go back to the Android Studio project. Go to `Weather/app/src/main/java/com/example/weatherapp/weatherdata/WeatherDataService.java`. Paste your API key on line 27.     
![img-1](https://user-images.githubusercontent.com/85015271/177504197-6436aa6b-97ea-47a5-98e0-b948a4e5ee44.PNG)    
4. Feel free to run the app using an emulator or physical device!

### Disclaimer
If the user does not grant location permissions, the app will not work as intended.



