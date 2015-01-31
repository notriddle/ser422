package edu.asupoly.cst425.lab4.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.asupoly.cst425.lab4.formbeans.EntryFormBean;
import edu.asupoly.cst425.lab4.formbeans.MessageBean;
import edu.asupoly.cst425.lab4.model.PhoneBook;
import edu.asupoly.cst425.lab4.model.PhoneEntry;

public class EditSetupAction extends Action {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)
	  	throws Exception {
		
		  EntryFormBean entryForm = (EntryFormBean)form;
		  String phone = request.getParameter("entry");
		  PhoneEntry entry = null;
		  boolean noSuchPhone = true;
		  String result = "success";
		  
		  if (phone != null) {
			  entry = PhoneBook.getPhoneBook().findPhoneEntry(phone);
			  if (entry != null) {
				  noSuchPhone = false;
				  request.getSession().setAttribute("oldphone", phone);
				  entryForm.setFirstName(entry.getFirstname());
				  entryForm.setLastName(entry.getLastname());
				  entryForm.setPhoneNumber(phone);
			  }
		  }
		  if (noSuchPhone) {
			  result = "nophone";
			  request.getSession().setAttribute("message", 
					  new MessageBean("Could not find phone entry for requested number"));
		  } 
		  return mapping.findForward(result);
	  }
}
