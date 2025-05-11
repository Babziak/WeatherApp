package com.example.weatheapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PogodaServiceTest {

    private PogodaService pogodaService;

    @Before
    public void setUp() {
        // Inicjalizacja PogodaService przed każdym testem
        pogodaService = new PogodaService();
    }

    @Test
    public void testPobierzPogodeCorrectData() {
        // Mockowanie odpowiedzi API (simulacja poprawnych danych)
        String jsonResponse = "{ \"main\": { \"temp\": 25.0 }, \"weather\": [ { \"description\": \"clear sky\" } ] }";

       

        // Wywołanie metody, która zwraca dane pogodowe
        String wynik = pogodaService.pobierzPogode("Warszawa");

        // Sprawdzenie poprawności odpowiedzi
        assertEquals("Temperatura: 25.0°C\nOpis: clear sky", wynik);
    }

    @Test
    public void testPobierzPogodeErrorHandling() {
        // Mockowanie sytuacji, kiedy API zwróci błąd lub pustą odpowiedź
        String invalidJsonResponse = "{ \"weather\": [ { \"description\": \"clear sky\" } ] }"; // Brak pola temp

        // Sprawdzenie, że metoda zwróci błąd
        String wynik = pogodaService.pobierzPogode("Warszawa");

        // Zależnie od implementacji, może to być komunikat błędu
        assertTrue(wynik.contains("Błąd"));
    }
}
