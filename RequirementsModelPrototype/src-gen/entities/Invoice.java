package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Invoice implements Serializable {
	
	/* all primary attributes */
	private int Id;
	private String Title;
	private LocalDate EffecitveDate;
	private float Amount;
	
	/* all references */
	
	/* all get and set functions */
	public int getId() {
		return Id;
	}	
	
	public void setId(int id) {
		this.Id = id;
	}
	public String getTitle() {
		return Title;
	}	
	
	public void setTitle(String title) {
		this.Title = title;
	}
	public LocalDate getEffecitveDate() {
		return EffecitveDate;
	}	
	
	public void setEffecitveDate(LocalDate effecitvedate) {
		this.EffecitveDate = effecitvedate;
	}
	public float getAmount() {
		return Amount;
	}	
	
	public void setAmount(float amount) {
		this.Amount = amount;
	}
	
	/* all functions for reference*/
	


}
