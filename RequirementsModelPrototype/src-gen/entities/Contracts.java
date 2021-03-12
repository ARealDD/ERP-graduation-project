package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Contracts implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private String Buyer;
	private String Packing;
	private LocalDate DateOfShipment;
	private String PortOfShipment;
	private String PortOfDestination;
	private String Insurance;
	private LocalDate EffectiveDate;
	
	/* all references */
	private Client ContractstoClient; 
	private Order BelongedOrder; 
	private Invoice InvoiceOfContract; 
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public String getBuyer() {
		return Buyer;
	}	
	
	public void setBuyer(String buyer) {
		this.Buyer = buyer;
	}
	public String getPacking() {
		return Packing;
	}	
	
	public void setPacking(String packing) {
		this.Packing = packing;
	}
	public LocalDate getDateOfShipment() {
		return DateOfShipment;
	}	
	
	public void setDateOfShipment(LocalDate dateofshipment) {
		this.DateOfShipment = dateofshipment;
	}
	public String getPortOfShipment() {
		return PortOfShipment;
	}	
	
	public void setPortOfShipment(String portofshipment) {
		this.PortOfShipment = portofshipment;
	}
	public String getPortOfDestination() {
		return PortOfDestination;
	}	
	
	public void setPortOfDestination(String portofdestination) {
		this.PortOfDestination = portofdestination;
	}
	public String getInsurance() {
		return Insurance;
	}	
	
	public void setInsurance(String insurance) {
		this.Insurance = insurance;
	}
	public LocalDate getEffectiveDate() {
		return EffectiveDate;
	}	
	
	public void setEffectiveDate(LocalDate effectivedate) {
		this.EffectiveDate = effectivedate;
	}
	
	/* all functions for reference*/
	public Client getContractstoClient() {
		return ContractstoClient;
	}	
	
	public void setContractstoClient(Client client) {
		this.ContractstoClient = client;
	}			
	public Order getBelongedOrder() {
		return BelongedOrder;
	}	
	
	public void setBelongedOrder(Order order) {
		this.BelongedOrder = order;
	}			
	public Invoice getInvoiceOfContract() {
		return InvoiceOfContract;
	}	
	
	public void setInvoiceOfContract(Invoice invoice) {
		this.InvoiceOfContract = invoice;
	}			
	


}
