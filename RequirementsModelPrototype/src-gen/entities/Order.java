package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Order implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private boolean IsCompleted;
	private String PaymentInformation;
	private float Amount;
	
	/* all references */
	private BillOfLading BillOfLadingOfOrder; 
	private DeliveryNotification DN; 
	private List<ExchangeNotification> EN = new LinkedList<ExchangeNotification>(); 
	private OrderMethod OT; 
	private Client Buyer; 
	private List<OrderLineProduct> ContainedOrderLine = new LinkedList<OrderLineProduct>(); 
	private Contracts ContractOfOrder; 
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public boolean getIsCompleted() {
		return IsCompleted;
	}	
	
	public void setIsCompleted(boolean iscompleted) {
		this.IsCompleted = iscompleted;
	}
	public String getPaymentInformation() {
		return PaymentInformation;
	}	
	
	public void setPaymentInformation(String paymentinformation) {
		this.PaymentInformation = paymentinformation;
	}
	public float getAmount() {
		return Amount;
	}	
	
	public void setAmount(float amount) {
		this.Amount = amount;
	}
	
	/* all functions for reference*/
	public BillOfLading getBillOfLadingOfOrder() {
		return BillOfLadingOfOrder;
	}	
	
	public void setBillOfLadingOfOrder(BillOfLading billoflading) {
		this.BillOfLadingOfOrder = billoflading;
	}			
	public DeliveryNotification getDN() {
		return DN;
	}	
	
	public void setDN(DeliveryNotification deliverynotification) {
		this.DN = deliverynotification;
	}			
	public List<ExchangeNotification> getEN() {
		return EN;
	}	
	
	public void addEN(ExchangeNotification exchangenotification) {
		this.EN.add(exchangenotification);
	}
	
	public void deleteEN(ExchangeNotification exchangenotification) {
		this.EN.remove(exchangenotification);
	}
	public OrderMethod getOT() {
		return OT;
	}	
	
	public void setOT(OrderMethod ordermethod) {
		this.OT = ordermethod;
	}			
	public Client getBuyer() {
		return Buyer;
	}	
	
	public void setBuyer(Client client) {
		this.Buyer = client;
	}			
	public List<OrderLineProduct> getContainedOrderLine() {
		return ContainedOrderLine;
	}	
	
	public void addContainedOrderLine(OrderLineProduct orderlineproduct) {
		this.ContainedOrderLine.add(orderlineproduct);
	}
	
	public void deleteContainedOrderLine(OrderLineProduct orderlineproduct) {
		this.ContainedOrderLine.remove(orderlineproduct);
	}
	public Contracts getContractOfOrder() {
		return ContractOfOrder;
	}	
	
	public void setContractOfOrder(Contracts contracts) {
		this.ContractOfOrder = contracts;
	}			
	


}
