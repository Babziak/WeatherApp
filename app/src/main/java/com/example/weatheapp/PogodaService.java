package com.example.weatheapp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PogodaService {

    private String apiKey = "5a30404e608d991cd846dad9056cb7e1"; // 🔁 Zamień na swój klucz API z OpenWeather

    public String pobierzPogode(String miasto) {
        try {
            String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" + miasto + "&appid=" + apiKey + "&units=metric&lang=pl";
            URL url = new URL(urlStr);

            HttpURLConnection polaczenie = (HttpURLConnection) url.openConnection();
            polaczenie.setRequestMethod("GET");
            polaczenie.connect();

            InputStream in = polaczenie.getInputStream();
            BufferedReader czytnik = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String linia;

            while ((linia = czytnik.readLine()) != null) {
                sb.append(linia);
            }

            // Parsowanie danych
            String dane = sb.toString();
            String temp = dane.split("\"temp\":")[1].split(",")[0];
            String opis = dane.split("\"description\":\"")[1].split("\"")[0];

            return "Temperatura: " + temp + "°C\nOpis: " + opis;

        } catch (Exception e) {
            return "Błąd: " + e.getMessage();
        }
    }
}
