package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class OrderLineProduct implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private int Quantity;
	private float SubAmount;
	
	/* all references */
	private Product BelongedProduct; 
	private Order BelongedOrder; 
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public int getQuantity() {
		return Quantity;
	}	
	
	public void setQuantity(int quantity) {
		this.Quantity = quantity;
	}
	public float getSubAmount() {
		return SubAmount;
	}	
	
	public void setSubAmount(float subamount) {
		this.SubAmount = subamount;
	}
	
	/* all functions for reference*/
	public Product getBelongedProduct() {
		return BelongedProduct;
	}	
	
	public void setBelongedProduct(Product product) {
		this.BelongedProduct = product;
	}			
	public Order getBelongedOrder() {
		return BelongedOrder;
	}	
	
	public void setBelongedOrder(Order order) {
		this.BelongedOrder = order;
	}			
	


}
