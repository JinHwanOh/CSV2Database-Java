/*
 Programmed By: Jin Hwan Oh
 Date: 12 August 2015
 */
package csv;

/**
 *
 * @author Jin Oh
 */
public class Person {

    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String city;
    private String province;
    private String postal;
    private String phone1;
    private String phone2;
    private String email;
    private String web;

    // No-arg constructor
    public Person() {
    }

    // Constructor 
    public Person(String firstName, String lastName, String companyName, String address, String city, String provice, String postal, String phone1, String phone2, String emil, String web) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.province = provice;
        this.postal = postal;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = emil;
        this.web = web;
    }

    // getters   


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostal() {
        return postal;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getEmail() {
        return email;
    }

    public String getWeb() {
        return web;
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String provice) {
        this.province = provice;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public void setEmail(String emil) {
        this.email = emil;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    // Returns true this equals to Object obj
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Person person = (Person) obj;
        if ((firstName == person.getFirstName() || (firstName != null) && firstName.equals(person.getFirstName()))
                && (lastName == person.getLastName() || (lastName != null) && lastName.equals(person.getLastName()))
                && (companyName == person.getCompanyName() || (companyName != null) && companyName.equals(person.getCompanyName()))
                && (address == person.getAddress() || (address != null) && address.equals(person.getAddress()))
                && (city == person.getCity() || (city != null) && city.equals(person.getCity()))
                && (province == person.getProvince() || (province != null) && province.equals(person.getProvince()))
                && (postal == person.getPostal() || (postal != null) && postal.equals(person.getPostal()))
                && (phone1 == person.getPhone1() || (phone1 != null) && phone1.equals(person.getPhone1()))
                && (phone2 == person.getPhone2() || (phone2 != null) && phone2.equals(person.getPhone2()))
                && (email == person.getEmail() || (email != null) && email.equals(person.getEmail()))
                && (web == person.getWeb() || (web != null) && web.equals(person.getWeb()))) {
            return true;
        }
        return false;
    }
}
