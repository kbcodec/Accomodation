package edu.accomodation.DBHandling;

import java.sql.*;

/**
 * Klasa DBConnection obsługuje połączenie z bazą danych.
 */
public class DBConnection {
    /** Obiekt Connection utrzymuje połączenie z bazą danych.*/
    private Connection conn;

    /*
     * Konstruktor DBConnection konfiguruje połączenie z bazą danych
     */
    public DBConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://bgmbookingserver.database.windows.net:1433;database=bookingDB;user=bookingadmin@bgmbookingserver;password=VbWdLRs4bAdG8$M;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda zwraca bieżące połączenie z bazą danych.
     */
    public Connection getConnection() {
        return conn;
    }




}