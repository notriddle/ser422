/**
 * 
 */
package edu.asupoly.cst425.lab4.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.asupoly.cst425.lab4.model.PhoneBook;
import edu.asupoly.cst425.lab4.model.PhoneEntry;

/**
 * @author kgary
 *
 */
public class ListAction extends Action {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
              					   HttpServletRequest request, HttpServletResponse response)
	  	throws Exception {
	  
		  PhoneEntry[] phoneNumbers = null;
		  HttpSession session = request.getSession();
		  String oldLastName = (String)session.getAttribute("findFilter");
		  String lastName = request.getParameter("lastname");
		  
		  if (lastName == null) {
			  lastName = oldLastName; // default to existing filter
		  }
		  
		  // reset the filter
		  session.setAttribute("findFilter", lastName);
		  
		  // Check for a find filter
		  if (lastName != null && lastName.length() > 0) {
			  phoneNumbers = PhoneBook.getPhoneBook().listEntries(lastName);
		  } else {
			  phoneNumbers = PhoneBook.getPhoneBook().listEntries();
		  }
		
		  if (phoneNumbers != null && phoneNumbers.length > 0) {
			  // make sure the result is available to the view
			  request.setAttribute("phoneNumbers", phoneNumbers);
		  }
		  return mapping.findForward("showlist");
	  }
}
