package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class BillOfLading implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private String Consignee;
	private String CommodityList;
	private String ContractObject;
	private float Quantity;
	private float TotalPrice;
	private String DeadlineForPerformance;
	private String LocationForPerformance;
	private String MethodForPerformance;
	
	/* all references */
	private DeliveryTerm DT; 
	private Order BelongedOrder; 
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public String getConsignee() {
		return Consignee;
	}	
	
	public void setConsignee(String consignee) {
		this.Consignee = consignee;
	}
	public String getCommodityList() {
		return CommodityList;
	}	
	
	public void setCommodityList(String commoditylist) {
		this.CommodityList = commoditylist;
	}
	public String getContractObject() {
		return ContractObject;
	}	
	
	public void setContractObject(String contractobject) {
		this.ContractObject = contractobject;
	}
	public float getQuantity() {
		return Quantity;
	}	
	
	public void setQuantity(float quantity) {
		this.Quantity = quantity;
	}
	public float getTotalPrice() {
		return TotalPrice;
	}	
	
	public void setTotalPrice(float totalprice) {
		this.TotalPrice = totalprice;
	}
	public String getDeadlineForPerformance() {
		return DeadlineForPerformance;
	}	
	
	public void setDeadlineForPerformance(String deadlineforperformance) {
		this.DeadlineForPerformance = deadlineforperformance;
	}
	public String getLocationForPerformance() {
		return LocationForPerformance;
	}	
	
	public void setLocationForPerformance(String locationforperformance) {
		this.LocationForPerformance = locationforperformance;
	}
	public String getMethodForPerformance() {
		return MethodForPerformance;
	}	
	
	public void setMethodForPerformance(String methodforperformance) {
		this.MethodForPerformance = methodforperformance;
	}
	
	/* all functions for reference*/
	public DeliveryTerm getDT() {
		return DT;
	}	
	
	public void setDT(DeliveryTerm deliveryterm) {
		this.DT = deliveryterm;
	}			
	public Order getBelongedOrder() {
		return BelongedOrder;
	}	
	
	public void setBelongedOrder(Order order) {
		this.BelongedOrder = order;
	}			
	


}
