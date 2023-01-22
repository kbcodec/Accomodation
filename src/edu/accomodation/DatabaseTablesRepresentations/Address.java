package edu.accomodation.DatabaseTablesRepresentations;

/**
 * Klasa reprezentująca adres, w tym identyfikator, miasto, kod pocztowy, ulicę i numer.
 */
public class Address {


    /** id_address - id adresu w bazie danych */
    private int id_address;

    /** city - miasto adresu */
    private String city;

    /** zip_code - kod pocztowy adresu */
    private String zip_code;

    /** street - ulica adresu */
    private String street;

    /** number  - numer adresu */
    private String number;

    //SETTERS

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    /**
     * metoda, która zwraca ciąg reprezentujący adres
     * @return ciąg reprezentujący adres
     */
    @Override
    public String toString() {
        return "Address{" +
                "id_address=" + id_address +
                ", city='" + city + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
