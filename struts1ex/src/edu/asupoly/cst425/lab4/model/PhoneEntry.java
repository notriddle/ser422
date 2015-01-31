package edu.asupoly.cst425.lab4.model;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String toString()
	{ return firstname + "\n" + lastname + "\n" + phone; }
}



