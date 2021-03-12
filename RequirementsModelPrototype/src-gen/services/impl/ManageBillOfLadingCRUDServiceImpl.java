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

public class ManageBillOfLadingCRUDServiceImpl implements ManageBillOfLadingCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageBillOfLadingCRUDServiceImpl() {
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
	public boolean createBillOfLading(int id, String consignee, String commoditylist, String contractobject, float quantity, float totalprice, String deadlineforperformance, String locationforperformance, String methodforperformance) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get billoflading
		BillOfLading billoflading = null;
		//no nested iterator --  iterator: any previous:any
		for (BillOfLading bil : (List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading"))
		{
			if (bil.getId() == id)
			{
				billoflading = bil;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(billoflading) == true) 
		{ 
			/* Logic here */
			BillOfLading bil = null;
			bil = (BillOfLading) EntityManager.createObject("BillOfLading");
			bil.setId(id);
			bil.setConsignee(consignee);
			bil.setCommodityList(commoditylist);
			bil.setContractObject(contractobject);
			bil.setQuantity(quantity);
			bil.setTotalPrice(totalprice);
			bil.setDeadlineForPerformance(deadlineforperformance);
			bil.setLocationForPerformance(locationforperformance);
			bil.setMethodForPerformance(methodforperformance);
			EntityManager.addObject("BillOfLading", bil);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			bil.getId() == id
			 && 
			bil.getConsignee() == consignee
			 && 
			bil.getCommodityList() == commoditylist
			 && 
			bil.getContractObject() == contractobject
			 && 
			bil.getQuantity() == quantity
			 && 
			bil.getTotalPrice() == totalprice
			 && 
			bil.getDeadlineForPerformance() == deadlineforperformance
			 && 
			bil.getLocationForPerformance() == locationforperformance
			 && 
			bil.getMethodForPerformance() == methodforperformance
			 && 
			StandardOPs.includes(((List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading")), bil)
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
		//string parameters: [consignee, commoditylist, contractobject, deadlineforperformance, locationforperformance, methodforperformance]
		//all relevant vars : bil
		//all relevant entities : BillOfLading
	} 
	 
	static {opINVRelatedEntity.put("createBillOfLading", Arrays.asList("BillOfLading"));}
	
	@SuppressWarnings("unchecked")
	public BillOfLading queryBillOfLading(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get billoflading
		BillOfLading billoflading = null;
		//no nested iterator --  iterator: any previous:any
		for (BillOfLading bil : (List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading"))
		{
			if (bil.getId() == id)
			{
				billoflading = bil;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(billoflading) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return billoflading;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean modifyBillOfLading(int id, String consignee, String commoditylist, String contractobject, float quantity, float totalprice, String deadlineforperformance, String locationforperformance, String methodforperformance) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get billoflading
		BillOfLading billoflading = null;
		//no nested iterator --  iterator: any previous:any
		for (BillOfLading bil : (List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading"))
		{
			if (bil.getId() == id)
			{
				billoflading = bil;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(billoflading) == false) 
		{ 
			/* Logic here */
			billoflading.setId(id);
			billoflading.setConsignee(consignee);
			billoflading.setCommodityList(commoditylist);
			billoflading.setContractObject(contractobject);
			billoflading.setQuantity(quantity);
			billoflading.setTotalPrice(totalprice);
			billoflading.setDeadlineForPerformance(deadlineforperformance);
			billoflading.setLocationForPerformance(locationforperformance);
			billoflading.setMethodForPerformance(methodforperformance);
			
			
			refresh();
			// post-condition checking
			if (!(billoflading.getId() == id
			 && 
			billoflading.getConsignee() == consignee
			 && 
			billoflading.getCommodityList() == commoditylist
			 && 
			billoflading.getContractObject() == contractobject
			 && 
			billoflading.getQuantity() == quantity
			 && 
			billoflading.getTotalPrice() == totalprice
			 && 
			billoflading.getDeadlineForPerformance() == deadlineforperformance
			 && 
			billoflading.getLocationForPerformance() == locationforperformance
			 && 
			billoflading.getMethodForPerformance() == methodforperformance
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
		//string parameters: [consignee, commoditylist, contractobject, deadlineforperformance, locationforperformance, methodforperformance]
		//all relevant vars : billoflading
		//all relevant entities : BillOfLading
	} 
	 
	static {opINVRelatedEntity.put("modifyBillOfLading", Arrays.asList("BillOfLading"));}
	
	@SuppressWarnings("unchecked")
	public boolean deleteBillOfLading(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get billoflading
		BillOfLading billoflading = null;
		//no nested iterator --  iterator: any previous:any
		for (BillOfLading bil : (List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading"))
		{
			if (bil.getId() == id)
			{
				billoflading = bil;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(billoflading) == false && StandardOPs.includes(((List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading")), billoflading)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("BillOfLading", billoflading);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<BillOfLading>)EntityManager.getAllInstancesOf("BillOfLading")), billoflading)
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
		//all relevant vars : billoflading
		//all relevant entities : BillOfLading
	} 
	 
	static {opINVRelatedEntity.put("deleteBillOfLading", Arrays.asList("BillOfLading"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
