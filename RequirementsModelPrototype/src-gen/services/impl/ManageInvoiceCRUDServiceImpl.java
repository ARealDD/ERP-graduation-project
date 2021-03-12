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

public class ManageInvoiceCRUDServiceImpl implements ManageInvoiceCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageInvoiceCRUDServiceImpl() {
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
	public boolean createInvoice(int id, String title, LocalDate effecitvedate, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get invoice
		Invoice invoice = null;
		//no nested iterator --  iterator: any previous:any
		for (Invoice invo : (List<Invoice>)EntityManager.getAllInstancesOf("Invoice"))
		{
			if (invo.getId() == id)
			{
				invoice = invo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(invoice) == true) 
		{ 
			/* Logic here */
			Invoice invo = null;
			invo = (Invoice) EntityManager.createObject("Invoice");
			invo.setId(id);
			invo.setTitle(title);
			invo.setEffecitveDate(effecitvedate);
			invo.setAmount(amount);
			EntityManager.addObject("Invoice", invo);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			invo.getId() == id
			 && 
			invo.getTitle() == title
			 && 
			invo.getEffecitveDate().equals(effecitvedate)
			 && 
			invo.getAmount() == amount
			 && 
			StandardOPs.includes(((List<Invoice>)EntityManager.getAllInstancesOf("Invoice")), invo)
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
		//string parameters: [title]
		//all relevant vars : invo
		//all relevant entities : Invoice
	} 
	 
	static {opINVRelatedEntity.put("createInvoice", Arrays.asList("Invoice"));}
	
	@SuppressWarnings("unchecked")
	public Invoice queryInvoice(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get invoice
		Invoice invoice = null;
		//no nested iterator --  iterator: any previous:any
		for (Invoice invo : (List<Invoice>)EntityManager.getAllInstancesOf("Invoice"))
		{
			if (invo.getId() == id)
			{
				invoice = invo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(invoice) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return invoice;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyInvoice(int id, String title, LocalDate effecitvedate, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get invoice
		Invoice invoice = null;
		//no nested iterator --  iterator: any previous:any
		for (Invoice invo : (List<Invoice>)EntityManager.getAllInstancesOf("Invoice"))
		{
			if (invo.getId() == id)
			{
				invoice = invo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(invoice) == false) 
		{ 
			/* Logic here */
			invoice.setId(id);
			invoice.setTitle(title);
			invoice.setEffecitveDate(effecitvedate);
			invoice.setAmount(amount);
			
			
			refresh();
			// post-condition checking
			if (!(invoice.getId() == id
			 && 
			invoice.getTitle() == title
			 && 
			invoice.getEffecitveDate().equals(effecitvedate)
			 && 
			invoice.getAmount() == amount
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
		//string parameters: [title]
		//all relevant vars : invoice
		//all relevant entities : Invoice
	} 
	 
	static {opINVRelatedEntity.put("modifyInvoice", Arrays.asList("Invoice"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteInvoice(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get invoice
		Invoice invoice = null;
		//no nested iterator --  iterator: any previous:any
		for (Invoice invo : (List<Invoice>)EntityManager.getAllInstancesOf("Invoice"))
		{
			if (invo.getId() == id)
			{
				invoice = invo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(invoice) == false && StandardOPs.includes(((List<Invoice>)EntityManager.getAllInstancesOf("Invoice")), invoice)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Invoice", invoice);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Invoice>)EntityManager.getAllInstancesOf("Invoice")), invoice)
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
		//all relevant vars : invoice
		//all relevant entities : Invoice
	} 
	 
	static {opINVRelatedEntity.put("deleteInvoice", Arrays.asList("Invoice"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
