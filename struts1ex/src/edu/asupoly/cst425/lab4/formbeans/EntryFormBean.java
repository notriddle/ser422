/**
 * 
 */
package edu.asupoly.cst425.lab4.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author kgary
 *
 */
@SuppressWarnings("serial")
public class EntryFormBean extends ActionForm {

	private String firstName = null;
	private String lastName = null;
	private String phoneNumber = null;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 
	 */
	public EntryFormBean() {
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (isNotValidName(getFirstName())) {
			errors.add("firstName",
					new ActionMessage("firstName.invalid"));
		}
		if (isNotValidName(getLastName())) {
			errors.add("lastName",
					new ActionMessage("lastName.invalid"));
		}
		if (isNotValidPhone(getPhoneNumber())) {
			errors.add("phoneNumber",
					new ActionMessage("phoneNumber.invalid"));
		}
		return(errors);
	}

	private boolean isNotValidName(String name) {
		return isMissing(name);
	}
	private boolean isNotValidPhone(String phone) {
		return isMissing(phone) || (phone.trim().length() != 7 && phone.trim().length() != 10); 
	}
	private boolean isMissing(String value) {
		return((value == null) || (value.trim().equals("")));
	}
	
	public String toString() {
		return getFirstName() + ", " + getLastName() + ", " + getPhoneNumber();
	}
}
