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

/**
 * @author kgary
 *
 */
public class CopyOfEditAction extends Action {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
            					   HttpServletRequest request, HttpServletResponse response)
	  	throws Exception {
		  
		  EntryFormBean entryData = (EntryFormBean)form;
		  MessageBean mBean = new MessageBean();
		  
		  String oldPhone = request.getParameter("phone");
		  // The bizlogic service object does the old phone versus new phone checking
		  boolean rval = PhoneBook.getPhoneBook().editEntry(oldPhone, entryData.getPhoneNumber(),
				  							 entryData.getFirstName(), entryData.getLastName());
		  if (rval) {				  
				  mBean.setMessage("Successfully edited phonebook");
		  } else {
			mBean.setMessage("Unable to complete edit operation");  
		  }
		  request.setAttribute("message", mBean);
		  return mapping.findForward("success");
	  }
}
