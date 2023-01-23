package edu.accomodation.DatabaseTablesRepresentations;

import javax.security.auth.kerberos.EncryptionKey;

/**
 * Klasa reprezentująca tabele User z bazy danych
 */
public class User {

    /** Unikalny identyfikator użytkownika */
    private int id;

    /** Login użytkownika */
    private String login;

    /** Zahaszowane hasło użytkownika */
    private String password;

    /** Adres e-mail użytkownika */
    private String email;

    /** Imię użytkownika */
    private String firstName;

    /** Nazwisko użytkownika */
    private String lastName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
