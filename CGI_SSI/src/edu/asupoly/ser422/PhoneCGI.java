package edu.asupoly.ser422;

import java.util.*;

public class PhoneCGI {
    private static PhoneBook _pbook = 
	new PhoneBook(PhoneBook.DEFAULT_FILENAME);

    public static void main(String[] args)
    {
	// We assume a GET here
	String queryString = System.getenv("QUERY_STRING");
	String cookieString = System.getenv("HTTP_COOKIE");
	String[] parsedArgs = parseQueryString(queryString);
	try {
	    if (parsedArgs[0].equals("Add"))
		{
		    PhoneEntry pentry = new PhoneEntry(parsedArgs[1],
						       parsedArgs[2],
						       parsedArgs[3]);
		    
		    _pbook.addEntry(parsedArgs[3], pentry);
		    _pbook.savePhoneBook();
		    System.out.println("Entry added to phonebook");
		}
	    else if (parsedArgs[0].equals("List"))
		{
		    String[] entries = _pbook.listEntries();
		    for (int i = 0; i < entries.length; i++)
			System.out.println("<b>" + i + ":</b> " + 
					   entries[i] + "<br>");
		}
	    else {
		System.out.println("Your cookie is " + cookieString);
	    }
	}
	catch (Exception exc)
	    {
		System.out.println("Java exception satisfying request");
		exc.printStackTrace();
	    }
    }

    private static String[] parseQueryString(String queryString)
    {
	String[] rval = new String[4];
	int count = 0;
	StringTokenizer st = new StringTokenizer(queryString, "&");
	String s = null;
	int ind = 0;
	while (st.hasMoreTokens() && count < 4)
	{
		s = st.nextToken();
		ind = s.indexOf("=");
		s = s.substring(ind+1);
		// very simple parse replace of encoded space
		s = s.replace('+', ' ');
		rval[count++] = s;
		if (s.equals("Sleep")) {  // this is a hack to show how the process blocks
			try {
				Thread.sleep(10000);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	 }
	return rval;
    }
}
