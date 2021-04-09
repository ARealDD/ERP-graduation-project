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

public class CoCoMESystemImpl implements CoCoMESystem, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public CoCoMESystemImpl() {
		services = new ThirdPartyServicesImpl();
	}

	public void refresh() {
		ProcessSaleService processsaleservice_service = (ProcessSaleService) ServiceManager
				.getAllInstancesOf("ProcessSaleService").get(0);
		processsaleservice_service.setCurrentCashDesk(currentCashDesk);
		processsaleservice_service.setCurrentStore(currentStore);
		ManageStoreCRUDService managestorecrudservice_service = (ManageStoreCRUDService) ServiceManager
				.getAllInstancesOf("ManageStoreCRUDService").get(0);
		managestorecrudservice_service.setCurrentCashDesk(currentCashDesk);
		managestorecrudservice_service.setCurrentStore(currentStore);
		ManageProductCatalogCRUDService manageproductcatalogcrudservice_service = (ManageProductCatalogCRUDService) ServiceManager
				.getAllInstancesOf("ManageProductCatalogCRUDService").get(0);
		manageproductcatalogcrudservice_service.setCurrentCashDesk(currentCashDesk);
		manageproductcatalogcrudservice_service.setCurrentStore(currentStore);
		ManageCashDeskCRUDService managecashdeskcrudservice_service = (ManageCashDeskCRUDService) ServiceManager
				.getAllInstancesOf("ManageCashDeskCRUDService").get(0);
		managecashdeskcrudservice_service.setCurrentCashDesk(currentCashDesk);
		managecashdeskcrudservice_service.setCurrentStore(currentStore);
		ManageCashierCRUDService managecashiercrudservice_service = (ManageCashierCRUDService) ServiceManager
				.getAllInstancesOf("ManageCashierCRUDService").get(0);
		managecashiercrudservice_service.setCurrentCashDesk(currentCashDesk);
		managecashiercrudservice_service.setCurrentStore(currentStore);
		ManageItemCRUDService manageitemcrudservice_service = (ManageItemCRUDService) ServiceManager
				.getAllInstancesOf("ManageItemCRUDService").get(0);
		manageitemcrudservice_service.setCurrentCashDesk(currentCashDesk);
		manageitemcrudservice_service.setCurrentStore(currentStore);
		ManageSupplierCRUDService managesuppliercrudservice_service = (ManageSupplierCRUDService) ServiceManager
				.getAllInstancesOf("ManageSupplierCRUDService").get(0);
		managesuppliercrudservice_service.setCurrentCashDesk(currentCashDesk);
		managesuppliercrudservice_service.setCurrentStore(currentStore);
		CoCoMEOrderProducts cocomeorderproducts_service = (CoCoMEOrderProducts) ServiceManager
				.getAllInstancesOf("CoCoMEOrderProducts").get(0);
		cocomeorderproducts_service.setCurrentCashDesk(currentCashDesk);
		cocomeorderproducts_service.setCurrentStore(currentStore);
	}			
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public boolean openCashDesk(int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cd
		CashDesk cd = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk s : (List<CashDesk>)EntityManager.getAllInstancesOf("CashDesk"))
		{
			if (s.getId() == cashDeskID)
			{
				cd = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cd) == false && cd.getIsOpened() == false && StandardOPs.oclIsundefined(currentStore) == false && currentStore.getIsOpened() == true) 
		{ 
			/* Logic here */
			this.setCurrentCashDesk(cd);
			cd.setIsOpened(true);
			
			
			refresh();
			// post-condition checking
			if (!(this.getCurrentCashDesk() == cd
			 && 
			cd.getIsOpened() == true
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
		//all relevant vars : cd this
		//all relevant entities : CashDesk 
	} 
	 
	static {opINVRelatedEntity.put("openCashDesk", Arrays.asList("CashDesk",""));}
	
	@SuppressWarnings("unchecked")
	public boolean closeCashDesk(int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cd
		CashDesk cd = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk s : (List<CashDesk>)EntityManager.getAllInstancesOf("CashDesk"))
		{
			if (s.getId() == cashDeskID)
			{
				cd = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cd) == false && cd.getIsOpened() == true && StandardOPs.oclIsundefined(currentStore) == false && currentStore.getIsOpened() == true) 
		{ 
			/* Logic here */
			this.setCurrentCashDesk(cd);
			cd.setIsOpened(false);
			
			
			refresh();
			// post-condition checking
			if (!(this.getCurrentCashDesk() == cd
			 && 
			cd.getIsOpened() == false
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
		//all relevant vars : cd this
		//all relevant entities : CashDesk 
	} 
	 
	static {opINVRelatedEntity.put("closeCashDesk", Arrays.asList("CashDesk",""));}
	
	@SuppressWarnings("unchecked")
	public boolean openStore(int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get sto
		Store sto = null;
		//no nested iterator --  iterator: any previous:any
		for (Store s : (List<Store>)EntityManager.getAllInstancesOf("Store"))
		{
			if (s.getId() == storeID)
			{
				sto = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(sto) == false && sto.getIsOpened() == false) 
		{ 
			/* Logic here */
			this.setCurrentStore(sto);
			sto.setIsOpened(true);
			
			
			refresh();
			// post-condition checking
			if (!(this.getCurrentStore() == sto
			 && 
			sto.getIsOpened() == true
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
		//all relevant vars : this sto
		//all relevant entities :  Store
	} 
	 
	static {opINVRelatedEntity.put("openStore", Arrays.asList("","Store"));}
	
	@SuppressWarnings("unchecked")
	public boolean closeStore(int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get sto
		Store sto = null;
		//no nested iterator --  iterator: any previous:any
		for (Store s : (List<Store>)EntityManager.getAllInstancesOf("Store"))
		{
			if (s.getId() == storeID)
			{
				sto = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(sto) == false && sto.getIsOpened() == true) 
		{ 
			/* Logic here */
			sto.setIsOpened(false);
			
			
			refresh();
			// post-condition checking
			if (!(sto.getIsOpened() == false
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
		//all relevant vars : sto
		//all relevant entities : Store
	} 
	 
	static {opINVRelatedEntity.put("closeStore", Arrays.asList("Store"));}
	
	@SuppressWarnings("unchecked")
	public boolean changePrice(int barcode, float newPrice) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get item
		Item item = null;
		//no nested iterator --  iterator: any previous:any
		for (Item i : (List<Item>)EntityManager.getAllInstancesOf("Item"))
		{
			if (i.getBarcode() == barcode)
			{
				item = i;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(item) == false) 
		{ 
			/* Logic here */
			item.setPrice(newPrice);
			
			
			refresh();
			// post-condition checking
			if (!(item.getPrice() == newPrice
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
		//all relevant vars : item
		//all relevant entities : Item
	} 
	 
	static {opINVRelatedEntity.put("changePrice", Arrays.asList("Item"));}
	
	@SuppressWarnings("unchecked")
	public boolean receiveOrderedProduct(int orderID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get op
		OrderProduct op = null;
		//no nested iterator --  iterator: any previous:any
		for (OrderProduct i : (List<OrderProduct>)EntityManager.getAllInstancesOf("OrderProduct"))
		{
			if (i.getId() == orderID)
			{
				op = i;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
		/* service reference */
		/* service temp attribute */
		/* objects in definition */
		OrderProduct Pre_op = SerializationUtils.clone(op);

		/* check precondition */
		if (StandardOPs.oclIsundefined(op) == false) 
		{ 
			/* Logic here */
			op.setOrderStatus(OrderStatus.RECEIVED);
			//no nested iterator --  iterator: forAll
			for (OrderEntry oe : op.getContainedEntries())
			{
				oe.getItem().setStockNumber(oe.getItem().getStockNumber()+oe.getQuantity());
			}
			
			
			refresh();
			// post-condition checking
			if (!(op.getOrderStatus() == OrderStatus.RECEIVED
			 && 
			((Predicate<List>) (list) -> {	
				Iterator<OrderEntry> oeIt =  list.iterator();
				Iterator<OrderEntry> Pre_oeIt =  Pre_op.getContainedEntries().iterator();
				OrderEntry oe = null;
				OrderEntry Pre_oe = null;
					while (oeIt.hasNext() && Pre_oeIt.hasNext()) {
					oe = oeIt.next();
					Pre_oe = Pre_oeIt.next();
					if (!(oe.getItem().getStockNumber() == Pre_oe.getItem().getStockNumber()+oe.getQuantity())) {
						return false;
					}
				}
				return true;
			}).test(op.getContainedEntries())
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
		//all relevant vars : op oe
		//all relevant entities : OrderProduct OrderEntry
	} 
	 
	static {opINVRelatedEntity.put("receiveOrderedProduct", Arrays.asList("OrderProduct","OrderEntry"));}
	
	@SuppressWarnings("unchecked")
	public List<Supplier> listSuppliers() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
			
			refresh(); return ((List<Supplier>)EntityManager.getAllInstancesOf("Supplier"));
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	@SuppressWarnings("unchecked")
	public List<Item> showStockReports() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
			
			refresh(); return ((List<Item>)EntityManager.getAllInstancesOf("Item"));
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	
	
	/* temp property for controller */
	private CashDesk currentCashDesk;
	private Store currentStore;
			
	/* all get and set functions for temp property*/
	public CashDesk getCurrentCashDesk() {
		return currentCashDesk;
	}	
	
	public void setCurrentCashDesk(CashDesk currentcashdesk) {
		this.currentCashDesk = currentcashdesk;
	}
	public Store getCurrentStore() {
		return currentStore;
	}	
	
	public void setCurrentStore(Store currentstore) {
		this.currentStore = currentstore;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
