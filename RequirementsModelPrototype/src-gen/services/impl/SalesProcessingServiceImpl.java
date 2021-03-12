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

public class SalesProcessingServiceImpl implements SalesProcessingService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public SalesProcessingServiceImpl() {
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
	public boolean makeNewOrder(int buyId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get buyer
		Client buyer = null;
		//no nested iterator --  iterator: any previous:any
		for (Client bu : (List<Client>)EntityManager.getAllInstancesOf("Client"))
		{
			if (bu.getId() == buyId)
			{
				buyer = bu;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(buyer) == false && StandardOPs.includes(((List<Client>)EntityManager.getAllInstancesOf("Client")), buyer) && (StandardOPs.oclIsundefined(currentOrder) == true || (StandardOPs.oclIsundefined(currentOrder) == false && currentOrder.getIsCompleted() == true))) 
		{ 
			/* Logic here */
			Order o = null;
			o = (Order) EntityManager.createObject("Order");
			o.setBuyer(buyer);
			buyer.addContainedOrders(o);
			o.setIsCompleted(false);
			EntityManager.addObject("Order", o);
			this.setCurrentOrder(o);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			o.getBuyer() == buyer
			 && 
			StandardOPs.includes(buyer.getContainedOrders(), o)
			 && 
			o.getIsCompleted() == false
			 && 
			StandardOPs.includes(((List<Order>)EntityManager.getAllInstancesOf("Order")), o)
			 && 
			this.getCurrentOrder() == o
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
		//all relevant vars : this o
		//all relevant entities :  Order
	} 
	 
	static {opINVRelatedEntity.put("makeNewOrder", Arrays.asList("","Order"));}
	
	@SuppressWarnings("unchecked")
	public boolean addProduct(int id, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get product
		Product product = null;
		//no nested iterator --  iterator: any previous:any
		for (Product pr : (List<Product>)EntityManager.getAllInstancesOf("Product"))
		{
			if (pr.getId() == id)
			{
				product = pr;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentOrder) == false && currentOrder.getIsCompleted() == false && StandardOPs.oclIsundefined(product) == false) 
		{ 
			/* Logic here */
			OrderLineProduct olp = null;
			olp = (OrderLineProduct) EntityManager.createObject("OrderLineProduct");
			currentOrderLine = olp;
			olp.setBelongedOrder(currentOrder);
			currentOrder.addContainedOrderLine(olp);
			olp.setQuantity(quantity);
			olp.setBelongedProduct(product);
			olp.setSubAmount(product.getPrice()*quantity);
			EntityManager.addObject("OrderLineProduct", olp);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			currentOrderLine == olp
			 && 
			olp.getBelongedOrder() == currentOrder
			 && 
			StandardOPs.includes(currentOrder.getContainedOrderLine(), olp)
			 && 
			olp.getQuantity() == quantity
			 && 
			olp.getBelongedProduct() == product
			 && 
			olp.getSubAmount() == product.getPrice()*quantity
			 && 
			StandardOPs.includes(((List<OrderLineProduct>)EntityManager.getAllInstancesOf("OrderLineProduct")), olp)
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
		//all relevant vars : olp
		//all relevant entities : OrderLineProduct
	} 
	 
	static {opINVRelatedEntity.put("addProduct", Arrays.asList("OrderLineProduct"));}
	
	@SuppressWarnings("unchecked")
	public boolean generateContract(String packing, LocalDate dateOfShipment, String portOfShipment, String portOfDestination, String insurance, LocalDate effectiveDate) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(currentOrder) == false && currentOrder.getIsComplete() == false) 
		{ 
			/* Logic here */
			Contracts con = null;
			con = (Contracts) EntityManager.createObject("Contracts");
			con.setId(currentOrder.getId());
			con.setBelongedOrder(currentOrder);
			con.setPacking(packing);
			con.setDateOfShipment(dateOfShipment);
			con.setPortOfShipment(portOfShipment);
			con.setPortOfDestination(portOfDestination);
			con.setInsurance(insurance);
			con.setEffectiveDate(effectiveDate);
			EntityManager.addObject("Contracts", con);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			con.getId() == currentOrder.getId()
			 && 
			con.getBelongedOrder() == currentOrder
			 && 
			con.getPacking() == packing
			 && 
			con.getDateOfShipment().equals(dateOfShipment)
			 && 
			con.getPortOfShipment() == portOfShipment
			 && 
			con.getPortOfDestination() == portOfDestination
			 && 
			con.getInsurance() == insurance
			 && 
			con.getEffectiveDate() == effectiveDate
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
		//string parameters: [packing, portOfShipment, portOfDestination, insurance]
		//all relevant vars : con
		//all relevant entities : Contracts
	} 
	 
	static {opINVRelatedEntity.put("generateContract", Arrays.asList("Contracts"));}
	
	@SuppressWarnings("unchecked")
	public boolean authorization() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (services.authorizationProcessing()) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
		
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public boolean generateOrder() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
		
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	
	
	/* temp property for controller */
	private OrderLineProduct currentOrderLine;
	private Order currentOrder;
			
	/* all get and set functions for temp property*/
	public OrderLineProduct getCurrentOrderLine() {
		return currentOrderLine;
	}	
	
	public void setCurrentOrderLine(OrderLineProduct currentorderline) {
		this.currentOrderLine = currentorderline;
	}
	public Order getCurrentOrder() {
		return currentOrder;
	}	
	
	public void setCurrentOrder(Order currentorder) {
		this.currentOrder = currentorder;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
