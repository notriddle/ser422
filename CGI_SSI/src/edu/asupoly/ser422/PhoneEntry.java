package edu.asupoly.ser422;

public class PhoneEntry {
    private String firstname;
    private String lastname;
    private String phone;

    public PhoneEntry(String name, String lname, String phone)
    {
	this.firstname  = name;
	this.lastname  = lname;
	this.phone = phone;
    }

    public String toString()
    { return firstname + "\n" + lastname + "\n" + phone; }
}



