package edu.asupoly.cst425.lab4.model;

import java.io.*;
import java.util.*;

public class PhoneBook {
	public static final String DEFAULT_FILENAME = "/phonebook.txt";

	private Map<String, PhoneEntry> _pbook = new HashMap<String, PhoneEntry>();

	// we are now a Singleton
	private static PhoneBook _thisBook = null;
	
	public static PhoneBook getPhoneBook() {
		if (_thisBook == null) {
			try {
				_thisBook = new PhoneBook();
			} catch (Exception exc) {
				exc.printStackTrace();  // not good handling, just debugging
			}
		}
		return _thisBook;
	}
	
	private PhoneBook() throws Exception {
		this(DEFAULT_FILENAME); 
	}
	private PhoneBook(String fname) throws Exception {
		this(Class.forName("edu.asupoly.cst425.lab4.model.PhoneBook").getResourceAsStream(fname));
	}
	private PhoneBook(InputStream is) throws IOException {
		this(new BufferedReader(new InputStreamReader(is)));
	}
	private PhoneBook(BufferedReader br) throws IOException {	
		String name = null;
		String lname = null;
		String phone = null;

		try {
			String nextLine = null;
			while ( (nextLine=br.readLine()) != null)
			{
				name  = nextLine;
				lname = br.readLine();
				phone = br.readLine();
				addEntry(name, lname, phone);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IOException("Could not process phonebook file");
		}
	}

	public boolean removeEntry(String phone) {
		return _pbook.remove(phone) != null;
	}
	
	public boolean editEntry(String oldphone, String newphone, String fname, String lname) {
		PhoneEntry pentry = _pbook.get(oldphone);
		
		if (pentry == null) {
			return false;
		}
		if (newphone == null || newphone.length() == 0 || newphone.equals(oldphone)) {
			pentry.setFirstname(fname);
			pentry.setLastname(lname);
		} else {  // we added a new phone number, need to modify the phone book
			pentry = new PhoneEntry(fname, lname, newphone);
			_pbook.remove(oldphone);
			_pbook.put(newphone, pentry);
		}
		return true;
	}
	
	public void addEntry(String fname, String lname, String phone)
	{ 
		addEntry(phone, new PhoneEntry(fname, lname, phone));
	}

	public void addEntry(String number, PhoneEntry entry)
	{ _pbook.put(number, entry); }

	public PhoneEntry[] listEntries() {
		return listEntries(null);
	}
	
	public PhoneEntry[] listEntries(String lnameFilter)
	{
		ArrayList<PhoneEntry> rval = new ArrayList<PhoneEntry>();
		PhoneEntry nextEntry = null;
		for (Iterator<PhoneEntry> iter = _pbook.values().iterator(); iter.hasNext();) {
			nextEntry = iter.next();
			if (lnameFilter == null || lnameFilter.length() == 0 || 
					nextEntry.getLastname().indexOf(lnameFilter) != -1) {
				rval.add(nextEntry);
			}
		}
		return rval.toArray(new PhoneEntry[0]);
	}
	
	public PhoneEntry findPhoneEntry(String phone) {
		return _pbook.get(phone);
	}
}
