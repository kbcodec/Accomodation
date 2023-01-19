package edu.accomodation.DatabaseTablesRepresentations;

public class Hotel {

    private int id_hotel;
    private String name;
    private String description;
    private String type;
    private String category;
    private int id_address;
    private String phone;
    private String web;
    private String email;
    private double latitude;
    private double longitude;
    private int max_quest;
    private int num_of_rooms;
    private String picture1;
    private String picture2;
    private String picture3;
    private Address address;


    //SETTERS

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMax_quest(int max_quest) {
        this.max_quest = max_quest;
    }

    public void setNum_of_rooms(int num_of_rooms) {
        this.num_of_rooms = num_of_rooms;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    //GETTERS


    public int getId_hotel() {
        return id_hotel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public int getId_address() {
        return id_address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWeb() {
        return web;
    }

    public String getEmail() {
        return email;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getMax_quest() {
        return max_quest;
    }

    public int getNum_of_rooms() {
        return num_of_rooms;
    }

    public String getPicture1() {
        return picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id_hotel=" + id_hotel +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", id_address=" + id_address +
                ", phone='" + phone + '\'' +
                ", web='" + web + '\'' +
                ", email='" + email + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", max_quest=" + max_quest +
                ", num_of_rooms=" + num_of_rooms +
                ", picture1='" + picture1 + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", picture3='" + picture3 + '\'' +
                ", address='" + address.toString() +'\'' +
                '}';
    }
}