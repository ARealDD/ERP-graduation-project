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

public class ManageClientCRUDServiceImpl implements ManageClientCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageClientCRUDServiceImpl() {
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
	public boolean createClient(int id, String name, String address, String contact, String phonenumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get client
		Client client = null;
		//no nested iterator --  iterator: any previous:any
		for (Client cli : (List<Client>)EntityManager.getAllInstancesOf("Client"))
		{
			if (cli.getId() == id)
			{
				client = cli;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(client) == true) 
		{ 
			/* Logic here */
			Client cli = null;
			cli = (Client) EntityManager.createObject("Client");
			cli.setId(id);
			cli.setName(name);
			cli.setAddress(address);
			cli.setContact(contact);
			cli.setPhoneNumber(phonenumber);
			EntityManager.addObject("Client", cli);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			cli.getId() == id
			 && 
			cli.getName() == name
			 && 
			cli.getAddress() == address
			 && 
			cli.getContact() == contact
			 && 
			cli.getPhoneNumber() == phonenumber
			 && 
			StandardOPs.includes(((List<Client>)EntityManager.getAllInstancesOf("Client")), cli)
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
		//string parameters: [name, address, contact, phonenumber]
		//all relevant vars : cli
		//all relevant entities : Client
	} 
	 
	static {opINVRelatedEntity.put("createClient", Arrays.asList("Client"));}
	
	@SuppressWarnings("unchecked")
	public Client queryClient(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get client
		Client client = null;
		//no nested iterator --  iterator: any previous:any
		for (Client cli : (List<Client>)EntityManager.getAllInstancesOf("Client"))
		{
			if (cli.getId() == id)
			{
				client = cli;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(client) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return client;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyClient(int id, String name, String address, String contact, String phonenumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get client
		Client client = null;
		//no nested iterator --  iterator: any previous:any
		for (Client cli : (List<Client>)EntityManager.getAllInstancesOf("Client"))
		{
			if (cli.getId() == id)
			{
				client = cli;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(client) == false) 
		{ 
			/* Logic here */
			client.setId(id);
			client.setName(name);
			client.setAddress(address);
			client.setContact(contact);
			client.setPhoneNumber(phonenumber);
			
			
			refresh();
			// post-condition checking
			if (!(client.getId() == id
			 && 
			client.getName() == name
			 && 
			client.getAddress() == address
			 && 
			client.getContact() == contact
			 && 
			client.getPhoneNumber() == phonenumber
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
		//string parameters: [name, address, contact, phonenumber]
		//all relevant vars : client
		//all relevant entities : Client
	} 
	 
	static {opINVRelatedEntity.put("modifyClient", Arrays.asList("Client"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteClient(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get client
		Client client = null;
		//no nested iterator --  iterator: any previous:any
		for (Client cli : (List<Client>)EntityManager.getAllInstancesOf("Client"))
		{
			if (cli.getId() == id)
			{
				client = cli;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(client) == false && StandardOPs.includes(((List<Client>)EntityManager.getAllInstancesOf("Client")), client)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Client", client);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Client>)EntityManager.getAllInstancesOf("Client")), client)
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
		//all relevant vars : client
		//all relevant entities : Client
	} 
	 
	static {opINVRelatedEntity.put("deleteClient", Arrays.asList("Client"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
