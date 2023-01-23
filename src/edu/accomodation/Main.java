package edu.accomodation;

import edu.accomodation.Forms.MainMenu;

import java.io.IOException;

/**
 * Klasa główna.
 * Służy do wyswietlenia zawartosci formatek projektu
 * Główne funkcjonalności to przeglądanie dostępnych w bazie danych ofert hoteli
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) throws IOException {
        new MainMenu("Accomodation").setVisible(true);
    }
}
