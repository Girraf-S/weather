package com.example.weather;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloController {
    @FXML
    private Label weatherText;
    @FXML
    private TextField textField;
    @FXML
    protected void onHelloButtonClick() throws Exception {
        Map<String, City> map = init();
        String cityName=textField.getText();
        City city = map.get(cityName);
        if(city==null){
            weatherText.setText("No city in list (Minsk, Pinsk,\n" +
                    "Brest, Baranoviczy, Ivaceviczy,\n" +
                    "Gomel, Grodno, Mozyr, Polock,\nVitebsk.");
            return;
        }
        URL url =
                new URL(String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=780fb9a3ab67b0b17f51796d81f9846c",
                        city.getLat(), city.getLon())
                );
        System.out.println(String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=780fb9a3ab67b0b17f51796d81f9846c",
                city.getLat(), city.getLon()));
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(url.openStream()));

        String str = bufferedReader.readLine();
        StringBuilder stringBuilder = getTemperature(str);
        stringBuilder.append(getWindSpeedAndHumidity(str));
        weatherText.setText(stringBuilder.toString());
    }
    private Map<String, City> init(){
        Map<String, City> map = new HashMap();
        map.put("Minsk", new City("Minsk","53.9000000","27.5666700"));
        map.put("Brest",new City("Brest","52.0975500","23.6877500"));
        map.put("Grodno",new City("Grodno","53.6884000","23.8258000"));
        map.put("Gomel",new City("Gomel","52.4345000","30.9754000"));
        map.put("Vitebsk", new City("Vitebsk","55.1904000","30.2049000"));
        map.put("Mogilev", new City("Mogilev","53.9168000","30.3449000"));
        map.put("Pinsk", new City("Pinsk","52.1229000","26.0951000"));
        map.put("Ivaceviczy", new City("Ivaceviczy","52.7090000","25.3401000"));
        map.put("Mozyr", new City("Mozyr","52.0495000","29.2456000"));
        map.put("Polock", new City("Polock","55.4879000","28.7856000"));
        map.put("Baranoviczy", new City("Baranoviczy","53.1327000","26.0139000"));
        return map;
    }
    private StringBuilder getTemperature(String str) throws Exception {

        JSONObject jsonObject1 = new JSONObject(str);
        str = String.valueOf(jsonObject1.get("main"));
        JSONObject jsonObject2 = new JSONObject(str);

        String temp = String.valueOf(jsonObject2.get("temp"));
        String feelTemp = String.valueOf(jsonObject2.get("feels_like"));
        String minTemp = String.valueOf(jsonObject2.get("temp_min"));
        String maxTemp = String.valueOf(jsonObject2.get("temp_max"));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current temperature: "+Math.round(Double.valueOf(temp)-273)+"°C\n");
        stringBuilder.append("Minimal temperature: "+Math.round(Double.valueOf(minTemp)-273)+"°C\n");
        stringBuilder.append("Maximal temperature: "+Math.round(Double.valueOf(maxTemp)-273)+"°C\n");
        stringBuilder.append("Feel temperature: "+Math.round(Double.valueOf(feelTemp)-273)+"°C\n");
        return stringBuilder;
    }

    private StringBuilder getWindSpeedAndHumidity(String str){
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject jsonObject1 = new JSONObject(str);
        String str1 = String.valueOf(jsonObject1.get("main"));
        JSONObject jsonObject2 = new JSONObject(str1);

        String humidity = String.valueOf(jsonObject2.get("humidity"));

        String str2 = String.valueOf(jsonObject1.get("wind"));
        JSONObject jsonObject3 = new JSONObject(str2);
        String speed = String.valueOf(jsonObject3.get("speed"));

        stringBuilder.append("Wind speed: "+ speed+"m/s\n");
        stringBuilder.append("Humidity: "+ humidity+"%\n");
        return stringBuilder;
    }
}