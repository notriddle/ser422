/**
 * 
 */
package edu.asupoly.cst425.lab4.formbeans;

/**
 * @author kgary
 *
 */
public class MessageBean {

	private String message = "";
	
	/**
	 * 
	 */
	public MessageBean() {
	}
	public MessageBean(String msg) {
		setMessage(msg);
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
