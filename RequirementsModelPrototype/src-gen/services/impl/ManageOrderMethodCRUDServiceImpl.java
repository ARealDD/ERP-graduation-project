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

public class ManageOrderMethodCRUDServiceImpl implements ManageOrderMethodCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageOrderMethodCRUDServiceImpl() {
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
	public boolean createOrderMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ordermethod
		OrderMethod ordermethod = null;
		//no nested iterator --  iterator: any previous:any
		for (OrderMethod ord : (List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod"))
		{
			if (ord.getId() == id)
			{
				ordermethod = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(ordermethod) == true) 
		{ 
			/* Logic here */
			OrderMethod ord = null;
			ord = (OrderMethod) EntityManager.createObject("OrderMethod");
			ord.setId(id);
			ord.setName(name);
			EntityManager.addObject("OrderMethod", ord);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			ord.getId() == id
			 && 
			ord.getName() == name
			 && 
			StandardOPs.includes(((List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod")), ord)
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
		//string parameters: [name]
		//all relevant vars : ord
		//all relevant entities : OrderMethod
	} 
	 
	static {opINVRelatedEntity.put("createOrderMethod", Arrays.asList("OrderMethod"));}
	
	@SuppressWarnings("unchecked")
	public OrderMethod queryOrderMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ordermethod
		OrderMethod ordermethod = null;
		//no nested iterator --  iterator: any previous:any
		for (OrderMethod ord : (List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod"))
		{
			if (ord.getId() == id)
			{
				ordermethod = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(ordermethod) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return ordermethod;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyOrderMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ordermethod
		OrderMethod ordermethod = null;
		//no nested iterator --  iterator: any previous:any
		for (OrderMethod ord : (List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod"))
		{
			if (ord.getId() == id)
			{
				ordermethod = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(ordermethod) == false) 
		{ 
			/* Logic here */
			ordermethod.setId(id);
			ordermethod.setName(name);
			
			
			refresh();
			// post-condition checking
			if (!(ordermethod.getId() == id
			 && 
			ordermethod.getName() == name
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
		//string parameters: [name]
		//all relevant vars : ordermethod
		//all relevant entities : OrderMethod
	} 
	 
	static {opINVRelatedEntity.put("modifyOrderMethod", Arrays.asList("OrderMethod"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteOrderMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ordermethod
		OrderMethod ordermethod = null;
		//no nested iterator --  iterator: any previous:any
		for (OrderMethod ord : (List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod"))
		{
			if (ord.getId() == id)
			{
				ordermethod = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(ordermethod) == false && StandardOPs.includes(((List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod")), ordermethod)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("OrderMethod", ordermethod);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<OrderMethod>)EntityManager.getAllInstancesOf("OrderMethod")), ordermethod)
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
		//all relevant vars : ordermethod
		//all relevant entities : OrderMethod
	} 
	 
	static {opINVRelatedEntity.put("deleteOrderMethod", Arrays.asList("OrderMethod"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
