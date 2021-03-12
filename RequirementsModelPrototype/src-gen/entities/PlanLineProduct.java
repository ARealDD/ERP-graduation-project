package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class PlanLineProduct implements Serializable {
	
	/* all primary attributes */
	private float Cost;
	private int Id;
	private int Expected;
	private float SalesCommission;
	
	/* all references */
	private Product BelongedProduct; 
	private SalePlan ContainedLine; 
	
	/* all get and set functions */
	public float getCost() {
		return Cost;
	}	
	
	public void setCost(float cost) {
		this.Cost = cost;
	}
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public int getExpected() {
		return Expected;
	}	
	
	public void setExpected(int expected) {
		this.Expected = expected;
	}
	public float getSalesCommission() {
		return SalesCommission;
	}	
	
	public void setSalesCommission(float salescommission) {
		this.SalesCommission = salescommission;
	}
	
	/* all functions for reference*/
	public Product getBelongedProduct() {
		return BelongedProduct;
	}	
	
	public void setBelongedProduct(Product product) {
		this.BelongedProduct = product;
	}			
	public SalePlan getContainedLine() {
		return ContainedLine;
	}	
	
	public void setContainedLine(SalePlan saleplan) {
		this.ContainedLine = saleplan;
	}			
	


}
