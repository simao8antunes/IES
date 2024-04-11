package com.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherForecastMain {
    private static Scanner sc = new Scanner(System.in);
    private static IpmaApiClient client = new IpmaApiClient();

    public static void  main(String[] args) {
        int op = 0;
        boolean loop = true;

        while(loop) {
            System.out.println("1 - Available cities");
            System.out.println("2 - Choose a city");
            System.out.println("3 - Exit");
            System.out.print("Answer: ");
            
            try {
                op = sc.nextInt();
                sc.nextLine();
    
                switch(op) {
                    case 1:
                        client.displayCities();
                        break;
                    case 2:
                        System.out.print("Enter city name: ");
                        String cityName = sc.nextLine();
                        client.displayCityForecast(cityName);
                        break;
                    case 3:
                        loop = false;
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option!");
                sc.nextLine();
            }

        }
    }
}