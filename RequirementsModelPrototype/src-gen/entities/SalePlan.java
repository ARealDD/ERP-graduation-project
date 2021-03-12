package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class SalePlan implements Serializable {
	
	/* all primary attributes */
	private LocalDate StartDate;
	private LocalDate EndDate;
	private int Id;
	private float TotalCost;
	private float TotalPrice;
	private float Profits;
	
	/* all references */
	private List<PlanLineProduct> SalePlantoPlanLineProduct = new LinkedList<PlanLineProduct>(); 
	
	/* all get and set functions */
	public LocalDate getStartDate() {
		return StartDate;
	}	
	
	public void setStartDate(LocalDate startdate) {
		this.StartDate = startdate;
	}
	public LocalDate getEndDate() {
		return EndDate;
	}	
	
	public void setEndDate(LocalDate enddate) {
		this.EndDate = enddate;
	}
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public float getTotalCost() {
		return TotalCost;
	}	
	
	public void setTotalCost(float totalcost) {
		this.TotalCost = totalcost;
	}
	public float getTotalPrice() {
		return TotalPrice;
	}	
	
	public void setTotalPrice(float totalprice) {
		this.TotalPrice = totalprice;
	}
	public float getProfits() {
		return Profits;
	}	
	
	public void setProfits(float profits) {
		this.Profits = profits;
	}
	
	/* all functions for reference*/
	public List<PlanLineProduct> getSalePlantoPlanLineProduct() {
		return SalePlantoPlanLineProduct;
	}	
	
	public void addSalePlantoPlanLineProduct(PlanLineProduct planlineproduct) {
		this.SalePlantoPlanLineProduct.add(planlineproduct);
	}
	
	public void deleteSalePlantoPlanLineProduct(PlanLineProduct planlineproduct) {
		this.SalePlantoPlanLineProduct.remove(planlineproduct);
	}
	


}
