package edu.accomodation.DatabasePerisisters;

import edu.accomodation.DatabaseTablesRepresentations.Address;
import edu.accomodation.DBHandling.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* Klasa służy do odczytywania, wyświetlania i pobierania miast z adresów w bazie danych.
 * Wykorzystuje obiekt DBConnection do nawiązania połączenia z bazą danych
*/


public class AddressDatabasePersister {

    /** Obiekt DBConnection służący do nawiązywania połączenia z bazą danych. */
    DBConnection dbConn = new DBConnection();

    /** Obiekt Connection używany do wykonywania instrukcji SQL */
    Connection conn = dbConn.getConnection();

    /**
     * Odczytuje pojedynczy adres z bazy danych o podanym ID.
     * @param ID identyfikator adresu do odczytania
     * @return obiekt Address zawierający informacje o adresie
     * @throws SQLException, jeśli wystąpi błąd bazy danych
     */
    public Address readAddress(int ID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id_address, city, zip_code, street, number FROM Address WHERE id_address = ?;");
        stmt.setInt(1, ID);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        Address listedAddress = new Address();

        listedAddress.setId_address(rs.getInt("id_address"));
        listedAddress.setCity(rs.getString("city"));
        listedAddress.setZip_code(rs.getString("zip_code"));
        listedAddress.setStreet(rs.getString("street"));
        listedAddress.setNumber(rs.getString("number"));

        return listedAddress;
    }

    /**
     * Wyświetla listę wszystkich adresów z bazy danych.
     * @return matchesAddresses lista obiektów Address zawierających wszystkie adresy w bazie danych
     * @throws SQLException, jeśli wystąpi błąd bazy danych
     */
    public List<Address> listAddresses() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM address");

        ResultSet rs = stmt.executeQuery();

        List<Address> matchesAddresses = new ArrayList<>();
        while (rs.next()) {
            Address listedAddress = new Address();
            listedAddress.setId_address(rs.getInt("id_address"));
            listedAddress.setCity(rs.getString("city"));
            listedAddress.setZip_code(rs.getString("zip_code"));
            listedAddress.setStreet(rs.getString("street"));
            listedAddress.setNumber(rs.getString("number"));
            matchesAddresses.add(listedAddress);
        }

        return matchesAddresses;
    }

    /**
     * Wyświetla listę wszystkich odrębnych miast z adresów w bazie danych.
     * @return result lista ciągów zawierających nazwy miast
     * @throws SQLException w przypadku wystąpienia błędu bazy danych
     * */
    public List<String> listCitiesFromAddresses() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT city FROM address");
        ResultSet rs = stmt.executeQuery();
        List<String> result = new ArrayList<>();
        while(rs.next()) {
            String address = rs.getString("city");
            result.add(address);
        }
        return result;
    }
}
