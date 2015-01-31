/**
 * 
 */
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

/**
 * @author kgary
 *
 */
public class AddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		EntryFormBean entryData = (EntryFormBean)form;
		MessageBean mBean = new MessageBean();

		// The bizlogic's default behavior is to simply overwrite entries with the same phone
		// number. We don't want to do that so we'll do a safety check first
		PhoneBook theBook = PhoneBook.getPhoneBook();
		PhoneEntry entry  = theBook.findPhoneEntry(entryData.getPhoneNumber());
		
		if (entry == null) { // what we expect
			theBook.addEntry(entryData.getFirstName(), entryData.getLastName(), entryData.getPhoneNumber());
			mBean.setMessage("Successfully added new entry to phonebook");
		} else {
			mBean.setMessage("Unable to complete add operation");  
		}
		request.getSession().setAttribute("message", mBean);
		return mapping.findForward("success");
	}
}
