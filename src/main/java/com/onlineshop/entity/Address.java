package com.onlineshop.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private int addressId;

    @Column(name="firstname")
    @NotBlank(message = "is required")
    private String firstname;

    @Column(name="lastname")
    @NotBlank(message = "is required")
    private String lastname;

    @Column(name="street_name")
    @NotBlank(message = "is required")
    private String streetName;

    @Column(name="street_number")
    @NotBlank(message = "is required")
    @Pattern(regexp="^[A-Z0-9/]*$", message = "wrong format")
    private String streetNumber;

    @Column(name="zip_code")
    @NotBlank(message = "is required")
    @Pattern(regexp="[0-9]{2}-[0-9]{3}", message = "wrong format")
    private String zipCode;

    @Column(name="city")
    @NotBlank(message = "is required")
    private String city;

    public Address() {
    }

    public Address(String firstname, String lastname, String streetName, String streetNumber, String zipCode, String city) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
