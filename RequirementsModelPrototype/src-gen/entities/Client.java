package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Client implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private String Name;
	private String Address;
	private String Contact;
	private String PhoneNumber;
	
	/* all references */
	private ClientGroup CG; 
	private List<Order> ContainedOrders = new LinkedList<Order>(); 
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public String getAddress() {
		return Address;
	}	
	
	public void setAddress(String address) {
		this.Address = address;
	}
	public String getContact() {
		return Contact;
	}	
	
	public void setContact(String contact) {
		this.Contact = contact;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}	
	
	public void setPhoneNumber(String phonenumber) {
		this.PhoneNumber = phonenumber;
	}
	
	/* all functions for reference*/
	public ClientGroup getCG() {
		return CG;
	}	
	
	public void setCG(ClientGroup clientgroup) {
		this.CG = clientgroup;
	}			
	public List<Order> getContainedOrders() {
		return ContainedOrders;
	}	
	
	public void addContainedOrders(Order order) {
		this.ContainedOrders.add(order);
	}
	
	public void deleteContainedOrders(Order order) {
		this.ContainedOrders.remove(order);
	}
	


}
