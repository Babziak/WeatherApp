package com.example.weatheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weatheapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText miastoEditText;
    Button pobierzButton;
    TextView wynikTextView;

    String apiKey = "api klucz"; // 🔁 Zamień na swój klucz API z https://openweathermap.org

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Zezwolenie na zapytania sieciowe w głównym wątku (dla prostoty – NIE dla produkcji!)
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        setContentView(R.layout.activity_main);

        miastoEditText = findViewById(R.id.miastoEditText);
        pobierzButton = findViewById(R.id.pobierzButton);
        wynikTextView = findViewById(R.id.wynikTextView);

        pobierzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String miasto = miastoEditText.getText().toString();
                String wynik = pobierzPogode(miasto);
                wynikTextView.setText(wynik);
            }
        });
    }

    private String pobierzPogode(String miasto) {
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

            // Wyszukaj temperaturę i opis
            String dane = sb.toString();
            String temp = dane.split("\"temp\":")[1].split(",")[0];
            String opis = dane.split("\"description\":\"")[1].split("\"")[0];

            return "Temperatura: " + temp + "°C\nOpis: " + opis;

        } catch (Exception e) {
            return "Błąd: " + e.getMessage();
        }
    }

    public String pobierzPogodeFromJson(String jsonResponse) {
        try {
            // Parsowanie JSON
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject main = jsonObject.getJSONObject("main");
            double temp = main.getDouble("temp");

            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            String description = weatherArray.getJSONObject(0).getString("description");

            // Zwrócenie przetworzonych danych
            return "Temperatura: " + temp + "°C\nOpis: " + description;

        } catch (Exception e) {
            // Jeśli wystąpi błąd, zwróć null
            return null;
        }
    }
}
