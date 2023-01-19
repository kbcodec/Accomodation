package edu.accomodation.DatabaseTablesRepresentations;

public class Address {
    private int id_address;
    private String city;
    private String zip_code;
    private String street;
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
