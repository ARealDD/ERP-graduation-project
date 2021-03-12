package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class DeliveryNotification implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private LocalDate EffectiveDate;
	private String Details;
	
	/* all references */
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public LocalDate getEffectiveDate() {
		return EffectiveDate;
	}	
	
	public void setEffectiveDate(LocalDate effectivedate) {
		this.EffectiveDate = effectivedate;
	}
	public String getDetails() {
		return Details;
	}	
	
	public void setDetails(String details) {
		this.Details = details;
	}
	
	/* all functions for reference*/
	


}
