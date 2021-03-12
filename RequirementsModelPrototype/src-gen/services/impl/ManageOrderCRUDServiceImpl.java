package services.impl;

import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;

public class ManageOrderCRUDServiceImpl implements ManageOrderCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageOrderCRUDServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
			
	/* all get and set functions for temp property*/
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	public void refresh() {
		SalesManagementSystemSystem salesmanagementsystemsystem_service = (SalesManagementSystemSystem) ServiceManager.getAllInstancesOf("SalesManagementSystemSystem").get(0);
	}
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public boolean createOrder(int id, boolean iscompleted, String paymentinformation, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getId() == id)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == true) 
		{ 
			/* Logic here */
			Order ord = null;
			ord = (Order) EntityManager.createObject("Order");
			ord.setId(id);
			ord.setIsCompleted(iscompleted);
			ord.setPaymentInformation(paymentinformation);
			ord.setAmount(amount);
			EntityManager.addObject("Order", ord);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			ord.getId() == id
			 && 
			ord.getIsCompleted() == iscompleted
			 && 
			ord.getPaymentInformation() == paymentinformation
			 && 
			ord.getAmount() == amount
			 && 
			StandardOPs.includes(((List<Order>)EntityManager.getAllInstancesOf("Order")), ord)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [paymentinformation]
		//all relevant vars : ord
		//all relevant entities : Order
	} 
	 
	static {opINVRelatedEntity.put("createOrder", Arrays.asList("Order"));}
	
	@SuppressWarnings("unchecked")
	public Order queryOrder(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getId() == id)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return order;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyOrder(int id, boolean iscompleted, String paymentinformation, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getId() == id)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == false) 
		{ 
			/* Logic here */
			order.setId(id);
			order.setIsCompleted(iscompleted);
			order.setPaymentInformation(paymentinformation);
			order.setAmount(amount);
			
			
			refresh();
			// post-condition checking
			if (!(order.getId() == id
			 && 
			order.getIsCompleted() == iscompleted
			 && 
			order.getPaymentInformation() == paymentinformation
			 && 
			order.getAmount() == amount
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [paymentinformation]
		//all relevant vars : order
		//all relevant entities : Order
	} 
	 
	static {opINVRelatedEntity.put("modifyOrder", Arrays.asList("Order"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteOrder(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getId() == id)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == false && StandardOPs.includes(((List<Order>)EntityManager.getAllInstancesOf("Order")), order)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Order", order);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Order>)EntityManager.getAllInstancesOf("Order")), order)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : order
		//all relevant entities : Order
	} 
	 
	static {opINVRelatedEntity.put("deleteOrder", Arrays.asList("Order"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
