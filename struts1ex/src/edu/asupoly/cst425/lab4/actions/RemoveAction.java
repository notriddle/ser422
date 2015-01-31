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

import edu.asupoly.cst425.lab4.formbeans.MessageBean;
import edu.asupoly.cst425.lab4.model.PhoneBook;
import edu.asupoly.cst425.lab4.model.PhoneEntry;

/**
 * @author kgary
 *
 */
public class RemoveAction extends Action {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
				   HttpServletRequest request, HttpServletResponse response)
	  	throws Exception {
		  
		  String phone = request.getParameter("phone");
		  MessageBean mBean = new MessageBean();
		 
		  if (phone == null || phone.length() == 0) {
			  mBean.setMessage("No phone number provided, no removal");
		  } else if (!PhoneBook.getPhoneBook().removeEntry(phone)) {
			  mBean.setMessage("No phone entry for phone number " + phone);
		  } else {
			  // we're in business
			  mBean.setMessage("Phone entry removed");
		  }
		  request.getSession().setAttribute("message", mBean);
		  return mapping.findForward("success");
	  }
}
