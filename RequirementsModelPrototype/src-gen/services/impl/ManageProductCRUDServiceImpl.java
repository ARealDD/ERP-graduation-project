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

public class ManageProductCRUDServiceImpl implements ManageProductCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageProductCRUDServiceImpl() {
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
	public boolean createProduct(int id, String name, float price) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get product
		Product product = null;
		//no nested iterator --  iterator: any previous:any
		for (Product pro : (List<Product>)EntityManager.getAllInstancesOf("Product"))
		{
			if (pro.getId() == id)
			{
				product = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(product) == true) 
		{ 
			/* Logic here */
			Product pro = null;
			pro = (Product) EntityManager.createObject("Product");
			pro.setId(id);
			pro.setName(name);
			pro.setPrice(price);
			EntityManager.addObject("Product", pro);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			pro.getId() == id
			 && 
			pro.getName() == name
			 && 
			pro.getPrice() == price
			 && 
			StandardOPs.includes(((List<Product>)EntityManager.getAllInstancesOf("Product")), pro)
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
		//all relevant vars : pro
		//all relevant entities : Product
	} 
	 
	static {opINVRelatedEntity.put("createProduct", Arrays.asList("Product"));}
	
	@SuppressWarnings("unchecked")
	public Product queryProduct(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get product
		Product product = null;
		//no nested iterator --  iterator: any previous:any
		for (Product pro : (List<Product>)EntityManager.getAllInstancesOf("Product"))
		{
			if (pro.getId() == id)
			{
				product = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(product) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return product;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyProduct(int id, String name, float price) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get product
		Product product = null;
		//no nested iterator --  iterator: any previous:any
		for (Product pro : (List<Product>)EntityManager.getAllInstancesOf("Product"))
		{
			if (pro.getId() == id)
			{
				product = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(product) == false) 
		{ 
			/* Logic here */
			product.setId(id);
			product.setName(name);
			product.setPrice(price);
			
			
			refresh();
			// post-condition checking
			if (!(product.getId() == id
			 && 
			product.getName() == name
			 && 
			product.getPrice() == price
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
		//all relevant vars : product
		//all relevant entities : Product
	} 
	 
	static {opINVRelatedEntity.put("modifyProduct", Arrays.asList("Product"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteProduct(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get product
		Product product = null;
		//no nested iterator --  iterator: any previous:any
		for (Product pro : (List<Product>)EntityManager.getAllInstancesOf("Product"))
		{
			if (pro.getId() == id)
			{
				product = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(product) == false && StandardOPs.includes(((List<Product>)EntityManager.getAllInstancesOf("Product")), product)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Product", product);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Product>)EntityManager.getAllInstancesOf("Product")), product)
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
		//all relevant vars : product
		//all relevant entities : Product
	} 
	 
	static {opINVRelatedEntity.put("deleteProduct", Arrays.asList("Product"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
