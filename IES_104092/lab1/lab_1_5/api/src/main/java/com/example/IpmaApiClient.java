package com.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class IpmaApiClient {
    private Retrofit retrofit;
    private IpmaService service;

    public IpmaApiClient() {
        retrofit = new Retrofit.Builder()
            .baseUrl("http://api.ipma.pt/open-data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        service = retrofit.create(IpmaService.class);
    }

    private List<String> getAvailableCities() {
        IpmaCities cities = getCitiesData();
        List<String> cityList = new ArrayList<String>();
        ListIterator<City> citiesIterator = cities.getData().listIterator();
        while (citiesIterator.hasNext()) {
            cityList.add(citiesIterator.next().getLocal());
        }

        return cityList;
    }

    public void displayCities() {
        List<String> cityList = getAvailableCities();

        System.out.println();
        System.out.println("--- Available Cities ---");
        for(String s : cityList) {
            System.out.println(" - " + s);
        }
        System.out.println();
    }

    private IpmaCityForecast getCityForecast(String cityName) {
        IpmaCityForecast forecast = null;
        IpmaCities cities = getCitiesData();
        ListIterator<City> citiesIterator = cities.getData().listIterator();

        int city_id = 0;
        while(citiesIterator.hasNext()) {
            City city = citiesIterator.next();
            if(city.getLocal().matches(cityName)) {
                city_id = city.getGlobalIdLocal();
                break;
            }
        }

        if(city_id == 0) return null;

        forecast = getCityForecastFromId(city_id);

        return forecast;
    }

    public void displayCityForecast(String cityName) {
        IpmaCityForecast forecast = getCityForecast(cityName);
        if(forecast == null) {
            System.out.println("That city is not available.");
            return;
        }

        List<Forecast> forecastList = forecast.getData();
        
        System.out.println();
        System.out.println("--- Weather Forecast for " + cityName + " ---");
        for(Forecast df : forecastList) {
            System.out.println("    --- " + df.getForecastDate() + " ---");
            System.out.printf("MaxTemp: %4.1f ºC%n", Double.parseDouble(df.getTMax()));
            System.out.printf("MinTemp: %4.1f ºC%n", Double.parseDouble(df.getTMin()));
            System.out.printf("ProbPrecip: %2.1f %% %n", Double.parseDouble(df.getPrecipitaProb()));
            System.out.println();
        }
    }

    private IpmaCities getCitiesData() {
        Call<IpmaCities> callCities = service.getDistritsIslands();
        IpmaCities cities = null;

        try {
            Response<IpmaCities> responseCities = callCities.execute();
            cities = responseCities.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cities;
    }

    private IpmaCityForecast getCityForecastFromId(int cityId) {
        Call<IpmaCityForecast> callForecast = service.getForecastForACity(cityId);
        IpmaCityForecast forecast = null;

        try  {
            Response<IpmaCityForecast> responseForecast = callForecast.execute();
            forecast = responseForecast.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecast;
    }
}