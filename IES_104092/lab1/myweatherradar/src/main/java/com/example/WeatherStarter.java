package com.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ListIterator;

import com.example.client.IpmaData;
import com.example.client.IpmaCityForecast; //may need to adapt package name
import com.example.client.IpmaNames;
import com.example.client.IpmaService;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    private static int CITY_ID_AVEIRO;
    private static String cityName;


    public static void  main(String[] args ) {

        if (args.length != 1){
            System.err.println("The city ID must be the only argument!");
            System.exit(1);
        }

        try{
            CITY_ID_AVEIRO = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            System.err.println("The argument must be an integer!");
            System.exit(1);
        }
        
        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);

        // prepare the call to remote endpoint
        Call<IpmaCityForecast> callSync1 = service.getForecastForACity(CITY_ID_AVEIRO);
        Call<IpmaNames> callSync2 = service.getCityNames();


        try {
            Response<IpmaCityForecast> apiResponse = callSync1.execute();
            IpmaCityForecast forecast = apiResponse.body();

            //obter todas as cidades
            Response<IpmaNames> apiResponse2 = callSync2.execute();
            IpmaNames names = apiResponse2.body();


            ListIterator<IpmaData> it = names.getData().listIterator();

            while(it.hasNext()){
                IpmaData data = it.next();

                if(data.getGlobalIdLocal() == CITY_ID_AVEIRO){
                    cityName = data.getLocal();
                    break;
                }
            }

            if (forecast != null && cityName != null) {
                var firstDay = forecast.getData().listIterator().next();

                System.out.printf("City: %s %n", cityName);
                System.out.printf( "Date: %s %n", firstDay.getForecastDate());
                System.out.printf("Min Temperature: %s %n", firstDay.getTMin());
                System.out.printf("Max Temperature: %s %n", firstDay.getTMax());
                System.out.printf("ProbRaining: %s", firstDay.getPrecipitaProb());

            } else {
                System.out.println( "No results for this request!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}