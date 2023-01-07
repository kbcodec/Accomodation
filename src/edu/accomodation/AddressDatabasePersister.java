package edu.accomodation;

import edu.accomodation.DBHandling.DBConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDatabasePersister {
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();

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
}
