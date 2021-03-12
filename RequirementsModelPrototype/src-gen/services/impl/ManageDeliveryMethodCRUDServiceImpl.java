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

public class ManageDeliveryMethodCRUDServiceImpl implements ManageDeliveryMethodCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageDeliveryMethodCRUDServiceImpl() {
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
	public boolean createDeliveryMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get deliverymethod
		DeliveryMethod deliverymethod = null;
		//no nested iterator --  iterator: any previous:any
		for (DeliveryMethod del : (List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod"))
		{
			if (del.getId() == id)
			{
				deliverymethod = del;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(deliverymethod) == true) 
		{ 
			/* Logic here */
			DeliveryMethod del = null;
			del = (DeliveryMethod) EntityManager.createObject("DeliveryMethod");
			del.setId(id);
			del.setName(name);
			EntityManager.addObject("DeliveryMethod", del);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			del.getId() == id
			 && 
			del.getName() == name
			 && 
			StandardOPs.includes(((List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod")), del)
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
		//all relevant vars : del
		//all relevant entities : DeliveryMethod
	} 
	 
	static {opINVRelatedEntity.put("createDeliveryMethod", Arrays.asList("DeliveryMethod"));}
	
	@SuppressWarnings("unchecked")
	public DeliveryMethod queryDeliveryMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get deliverymethod
		DeliveryMethod deliverymethod = null;
		//no nested iterator --  iterator: any previous:any
		for (DeliveryMethod del : (List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod"))
		{
			if (del.getId() == id)
			{
				deliverymethod = del;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(deliverymethod) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return deliverymethod;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyDeliveryMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get deliverymethod
		DeliveryMethod deliverymethod = null;
		//no nested iterator --  iterator: any previous:any
		for (DeliveryMethod del : (List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod"))
		{
			if (del.getId() == id)
			{
				deliverymethod = del;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(deliverymethod) == false) 
		{ 
			/* Logic here */
			deliverymethod.setId(id);
			deliverymethod.setName(name);
			
			
			refresh();
			// post-condition checking
			if (!(deliverymethod.getId() == id
			 && 
			deliverymethod.getName() == name
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
		//all relevant vars : deliverymethod
		//all relevant entities : DeliveryMethod
	} 
	 
	static {opINVRelatedEntity.put("modifyDeliveryMethod", Arrays.asList("DeliveryMethod"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteDeliveryMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get deliverymethod
		DeliveryMethod deliverymethod = null;
		//no nested iterator --  iterator: any previous:any
		for (DeliveryMethod del : (List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod"))
		{
			if (del.getId() == id)
			{
				deliverymethod = del;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(deliverymethod) == false && StandardOPs.includes(((List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod")), deliverymethod)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("DeliveryMethod", deliverymethod);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<DeliveryMethod>)EntityManager.getAllInstancesOf("DeliveryMethod")), deliverymethod)
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
		//all relevant vars : deliverymethod
		//all relevant entities : DeliveryMethod
	} 
	 
	static {opINVRelatedEntity.put("deleteDeliveryMethod", Arrays.asList("DeliveryMethod"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
