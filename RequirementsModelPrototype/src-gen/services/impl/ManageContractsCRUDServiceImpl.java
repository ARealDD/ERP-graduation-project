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

public class ManageContractsCRUDServiceImpl implements ManageContractsCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageContractsCRUDServiceImpl() {
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
	public boolean createContracts(int id, String buyer, String packing, LocalDate dateofshipment, String portofshipment, String portofdestination, String insurance, LocalDate effectivedate) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get contracts
		Contracts contracts = null;
		//no nested iterator --  iterator: any previous:any
		for (Contracts con : (List<Contracts>)EntityManager.getAllInstancesOf("Contracts"))
		{
			if (con.getId() == id)
			{
				contracts = con;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(contracts) == true) 
		{ 
			/* Logic here */
			Contracts con = null;
			con = (Contracts) EntityManager.createObject("Contracts");
			con.setId(id);
			con.setBuyer(buyer);
			con.setPacking(packing);
			con.setDateOfShipment(dateofshipment);
			con.setPortOfShipment(portofshipment);
			con.setPortOfDestination(portofdestination);
			con.setInsurance(insurance);
			con.setEffectiveDate(effectivedate);
			EntityManager.addObject("Contracts", con);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			con.getId() == id
			 && 
			con.getBuyer() == buyer
			 && 
			con.getPacking() == packing
			 && 
			con.getDateOfShipment().equals(dateofshipment)
			 && 
			con.getPortOfShipment() == portofshipment
			 && 
			con.getPortOfDestination() == portofdestination
			 && 
			con.getInsurance() == insurance
			 && 
			con.getEffectiveDate().equals(effectivedate)
			 && 
			StandardOPs.includes(((List<Contracts>)EntityManager.getAllInstancesOf("Contracts")), con)
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
		//string parameters: [buyer, packing, portofshipment, portofdestination, insurance]
		//all relevant vars : con
		//all relevant entities : Contracts
	} 
	 
	static {opINVRelatedEntity.put("createContracts", Arrays.asList("Contracts"));}
	
	@SuppressWarnings("unchecked")
	public Contracts queryContracts(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get contracts
		Contracts contracts = null;
		//no nested iterator --  iterator: any previous:any
		for (Contracts con : (List<Contracts>)EntityManager.getAllInstancesOf("Contracts"))
		{
			if (con.getId() == id)
			{
				contracts = con;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(contracts) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return contracts;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyContracts(int id, String buyer, String packing, LocalDate dateofshipment, String portofshipment, String portofdestination, String insurance, LocalDate effectivedate) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get contracts
		Contracts contracts = null;
		//no nested iterator --  iterator: any previous:any
		for (Contracts con : (List<Contracts>)EntityManager.getAllInstancesOf("Contracts"))
		{
			if (con.getId() == id)
			{
				contracts = con;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(contracts) == false) 
		{ 
			/* Logic here */
			contracts.setId(id);
			contracts.setBuyer(buyer);
			contracts.setPacking(packing);
			contracts.setDateOfShipment(dateofshipment);
			contracts.setPortOfShipment(portofshipment);
			contracts.setPortOfDestination(portofdestination);
			contracts.setInsurance(insurance);
			contracts.setEffectiveDate(effectivedate);
			
			
			refresh();
			// post-condition checking
			if (!(contracts.getId() == id
			 && 
			contracts.getBuyer() == buyer
			 && 
			contracts.getPacking() == packing
			 && 
			contracts.getDateOfShipment().equals(dateofshipment)
			 && 
			contracts.getPortOfShipment() == portofshipment
			 && 
			contracts.getPortOfDestination() == portofdestination
			 && 
			contracts.getInsurance() == insurance
			 && 
			contracts.getEffectiveDate().equals(effectivedate)
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
		//string parameters: [buyer, packing, portofshipment, portofdestination, insurance]
		//all relevant vars : contracts
		//all relevant entities : Contracts
	} 
	 
	static {opINVRelatedEntity.put("modifyContracts", Arrays.asList("Contracts"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteContracts(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get contracts
		Contracts contracts = null;
		//no nested iterator --  iterator: any previous:any
		for (Contracts con : (List<Contracts>)EntityManager.getAllInstancesOf("Contracts"))
		{
			if (con.getId() == id)
			{
				contracts = con;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(contracts) == false && StandardOPs.includes(((List<Contracts>)EntityManager.getAllInstancesOf("Contracts")), contracts)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Contracts", contracts);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Contracts>)EntityManager.getAllInstancesOf("Contracts")), contracts)
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
		//all relevant vars : contracts
		//all relevant entities : Contracts
	} 
	 
	static {opINVRelatedEntity.put("deleteContracts", Arrays.asList("Contracts"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
