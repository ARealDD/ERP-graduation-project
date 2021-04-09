package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Map;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class EntityManager {

	private static Map<String, List> AllInstance = new HashMap<String, List>();
	
	private static List<Store> StoreInstances = new LinkedList<Store>();
	private static List<ProductCatalog> ProductCatalogInstances = new LinkedList<ProductCatalog>();
	private static List<CashDesk> CashDeskInstances = new LinkedList<CashDesk>();
	private static List<Sale> SaleInstances = new LinkedList<Sale>();
	private static List<Cashier> CashierInstances = new LinkedList<Cashier>();
	private static List<SalesLineItem> SalesLineItemInstances = new LinkedList<SalesLineItem>();
	private static List<Item> ItemInstances = new LinkedList<Item>();
	private static List<Payment> PaymentInstances = new LinkedList<Payment>();
	private static List<CashPayment> CashPaymentInstances = new LinkedList<CashPayment>();
	private static List<CardPayment> CardPaymentInstances = new LinkedList<CardPayment>();
	private static List<OrderEntry> OrderEntryInstances = new LinkedList<OrderEntry>();
	private static List<Supplier> SupplierInstances = new LinkedList<Supplier>();
	private static List<OrderProduct> OrderProductInstances = new LinkedList<OrderProduct>();

	
	/* Put instances list into Map */
	static {
		AllInstance.put("Store", StoreInstances);
		AllInstance.put("ProductCatalog", ProductCatalogInstances);
		AllInstance.put("CashDesk", CashDeskInstances);
		AllInstance.put("Sale", SaleInstances);
		AllInstance.put("Cashier", CashierInstances);
		AllInstance.put("SalesLineItem", SalesLineItemInstances);
		AllInstance.put("Item", ItemInstances);
		AllInstance.put("Payment", PaymentInstances);
		AllInstance.put("CashPayment", CashPaymentInstances);
		AllInstance.put("CardPayment", CardPaymentInstances);
		AllInstance.put("OrderEntry", OrderEntryInstances);
		AllInstance.put("Supplier", SupplierInstances);
		AllInstance.put("OrderProduct", OrderProductInstances);
	} 
		
	/* Save State */
	public static void save(File file) {
		
		try {
			
			ObjectOutputStream stateSave = new ObjectOutputStream(new FileOutputStream(file));
			
			stateSave.writeObject(StoreInstances);
			stateSave.writeObject(ProductCatalogInstances);
			stateSave.writeObject(CashDeskInstances);
			stateSave.writeObject(SaleInstances);
			stateSave.writeObject(CashierInstances);
			stateSave.writeObject(SalesLineItemInstances);
			stateSave.writeObject(ItemInstances);
			stateSave.writeObject(PaymentInstances);
			stateSave.writeObject(CashPaymentInstances);
			stateSave.writeObject(CardPaymentInstances);
			stateSave.writeObject(OrderEntryInstances);
			stateSave.writeObject(SupplierInstances);
			stateSave.writeObject(OrderProductInstances);
			
			stateSave.close();
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* Load State */
	public static void load(File file) {
		
		try {
			
			ObjectInputStream stateLoad = new ObjectInputStream(new FileInputStream(file));
			
			try {
				
				StoreInstances =  (List<Store>) stateLoad.readObject();
				AllInstance.put("Store", StoreInstances);
				ProductCatalogInstances =  (List<ProductCatalog>) stateLoad.readObject();
				AllInstance.put("ProductCatalog", ProductCatalogInstances);
				CashDeskInstances =  (List<CashDesk>) stateLoad.readObject();
				AllInstance.put("CashDesk", CashDeskInstances);
				SaleInstances =  (List<Sale>) stateLoad.readObject();
				AllInstance.put("Sale", SaleInstances);
				CashierInstances =  (List<Cashier>) stateLoad.readObject();
				AllInstance.put("Cashier", CashierInstances);
				SalesLineItemInstances =  (List<SalesLineItem>) stateLoad.readObject();
				AllInstance.put("SalesLineItem", SalesLineItemInstances);
				ItemInstances =  (List<Item>) stateLoad.readObject();
				AllInstance.put("Item", ItemInstances);
				PaymentInstances =  (List<Payment>) stateLoad.readObject();
				AllInstance.put("Payment", PaymentInstances);
				CashPaymentInstances =  (List<CashPayment>) stateLoad.readObject();
				AllInstance.put("CashPayment", CashPaymentInstances);
				CardPaymentInstances =  (List<CardPayment>) stateLoad.readObject();
				AllInstance.put("CardPayment", CardPaymentInstances);
				OrderEntryInstances =  (List<OrderEntry>) stateLoad.readObject();
				AllInstance.put("OrderEntry", OrderEntryInstances);
				SupplierInstances =  (List<Supplier>) stateLoad.readObject();
				AllInstance.put("Supplier", SupplierInstances);
				OrderProductInstances =  (List<OrderProduct>) stateLoad.readObject();
				AllInstance.put("OrderProduct", OrderProductInstances);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/* create object */  
	public static Object createObject(String Classifer) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method createObjectMethod = c.getDeclaredMethod("create" + Classifer + "Object");
			return createObjectMethod.invoke(c);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* add object */  
	public static Object addObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectMethod = c.getDeclaredMethod("add" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) addObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	
	/* add objects */  
	public static Object addObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectsMethod = c.getDeclaredMethod("add" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) addObjectsMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* Release object */
	public static boolean deleteObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) deleteObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/* Release objects */
	public static boolean deleteObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) deleteObjectMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}		 	
	
	 /* Get all objects belongs to same class */
	public static List getAllInstancesOf(String ClassName) {
			 return AllInstance.get(ClassName);
	}	

   /* Sub-create object */
	public static Store createStoreObject() {
		Store o = new Store();
		return o;
	}
	
	public static boolean addStoreObject(Store o) {
		return StoreInstances.add(o);
	}
	
	public static boolean addStoreObjects(List<Store> os) {
		return StoreInstances.addAll(os);
	}
	
	public static boolean deleteStoreObject(Store o) {
		return StoreInstances.remove(o);
	}
	
	public static boolean deleteStoreObjects(List<Store> os) {
		return StoreInstances.removeAll(os);
	}
	public static ProductCatalog createProductCatalogObject() {
		ProductCatalog o = new ProductCatalog();
		return o;
	}
	
	public static boolean addProductCatalogObject(ProductCatalog o) {
		return ProductCatalogInstances.add(o);
	}
	
	public static boolean addProductCatalogObjects(List<ProductCatalog> os) {
		return ProductCatalogInstances.addAll(os);
	}
	
	public static boolean deleteProductCatalogObject(ProductCatalog o) {
		return ProductCatalogInstances.remove(o);
	}
	
	public static boolean deleteProductCatalogObjects(List<ProductCatalog> os) {
		return ProductCatalogInstances.removeAll(os);
	}
	public static CashDesk createCashDeskObject() {
		CashDesk o = new CashDesk();
		return o;
	}
	
	public static boolean addCashDeskObject(CashDesk o) {
		return CashDeskInstances.add(o);
	}
	
	public static boolean addCashDeskObjects(List<CashDesk> os) {
		return CashDeskInstances.addAll(os);
	}
	
	public static boolean deleteCashDeskObject(CashDesk o) {
		return CashDeskInstances.remove(o);
	}
	
	public static boolean deleteCashDeskObjects(List<CashDesk> os) {
		return CashDeskInstances.removeAll(os);
	}
	public static Sale createSaleObject() {
		Sale o = new Sale();
		return o;
	}
	
	public static boolean addSaleObject(Sale o) {
		return SaleInstances.add(o);
	}
	
	public static boolean addSaleObjects(List<Sale> os) {
		return SaleInstances.addAll(os);
	}
	
	public static boolean deleteSaleObject(Sale o) {
		return SaleInstances.remove(o);
	}
	
	public static boolean deleteSaleObjects(List<Sale> os) {
		return SaleInstances.removeAll(os);
	}
	public static Cashier createCashierObject() {
		Cashier o = new Cashier();
		return o;
	}
	
	public static boolean addCashierObject(Cashier o) {
		return CashierInstances.add(o);
	}
	
	public static boolean addCashierObjects(List<Cashier> os) {
		return CashierInstances.addAll(os);
	}
	
	public static boolean deleteCashierObject(Cashier o) {
		return CashierInstances.remove(o);
	}
	
	public static boolean deleteCashierObjects(List<Cashier> os) {
		return CashierInstances.removeAll(os);
	}
	public static SalesLineItem createSalesLineItemObject() {
		SalesLineItem o = new SalesLineItem();
		return o;
	}
	
	public static boolean addSalesLineItemObject(SalesLineItem o) {
		return SalesLineItemInstances.add(o);
	}
	
	public static boolean addSalesLineItemObjects(List<SalesLineItem> os) {
		return SalesLineItemInstances.addAll(os);
	}
	
	public static boolean deleteSalesLineItemObject(SalesLineItem o) {
		return SalesLineItemInstances.remove(o);
	}
	
	public static boolean deleteSalesLineItemObjects(List<SalesLineItem> os) {
		return SalesLineItemInstances.removeAll(os);
	}
	public static Item createItemObject() {
		Item o = new Item();
		return o;
	}
	
	public static boolean addItemObject(Item o) {
		return ItemInstances.add(o);
	}
	
	public static boolean addItemObjects(List<Item> os) {
		return ItemInstances.addAll(os);
	}
	
	public static boolean deleteItemObject(Item o) {
		return ItemInstances.remove(o);
	}
	
	public static boolean deleteItemObjects(List<Item> os) {
		return ItemInstances.removeAll(os);
	}
	public static Payment createPaymentObject() {
		Payment o = new Payment();
		return o;
	}
	
	public static boolean addPaymentObject(Payment o) {
		return PaymentInstances.add(o);
	}
	
	public static boolean addPaymentObjects(List<Payment> os) {
		return PaymentInstances.addAll(os);
	}
	
	public static boolean deletePaymentObject(Payment o) {
		return PaymentInstances.remove(o);
	}
	
	public static boolean deletePaymentObjects(List<Payment> os) {
		return PaymentInstances.removeAll(os);
	}
	public static CashPayment createCashPaymentObject() {
		CashPayment o = new CashPayment();
		return o;
	}
	
	public static boolean addCashPaymentObject(CashPayment o) {
		return CashPaymentInstances.add(o);
	}
	
	public static boolean addCashPaymentObjects(List<CashPayment> os) {
		return CashPaymentInstances.addAll(os);
	}
	
	public static boolean deleteCashPaymentObject(CashPayment o) {
		return CashPaymentInstances.remove(o);
	}
	
	public static boolean deleteCashPaymentObjects(List<CashPayment> os) {
		return CashPaymentInstances.removeAll(os);
	}
	public static CardPayment createCardPaymentObject() {
		CardPayment o = new CardPayment();
		return o;
	}
	
	public static boolean addCardPaymentObject(CardPayment o) {
		return CardPaymentInstances.add(o);
	}
	
	public static boolean addCardPaymentObjects(List<CardPayment> os) {
		return CardPaymentInstances.addAll(os);
	}
	
	public static boolean deleteCardPaymentObject(CardPayment o) {
		return CardPaymentInstances.remove(o);
	}
	
	public static boolean deleteCardPaymentObjects(List<CardPayment> os) {
		return CardPaymentInstances.removeAll(os);
	}
	public static OrderEntry createOrderEntryObject() {
		OrderEntry o = new OrderEntry();
		return o;
	}
	
	public static boolean addOrderEntryObject(OrderEntry o) {
		return OrderEntryInstances.add(o);
	}
	
	public static boolean addOrderEntryObjects(List<OrderEntry> os) {
		return OrderEntryInstances.addAll(os);
	}
	
	public static boolean deleteOrderEntryObject(OrderEntry o) {
		return OrderEntryInstances.remove(o);
	}
	
	public static boolean deleteOrderEntryObjects(List<OrderEntry> os) {
		return OrderEntryInstances.removeAll(os);
	}
	public static Supplier createSupplierObject() {
		Supplier o = new Supplier();
		return o;
	}
	
	public static boolean addSupplierObject(Supplier o) {
		return SupplierInstances.add(o);
	}
	
	public static boolean addSupplierObjects(List<Supplier> os) {
		return SupplierInstances.addAll(os);
	}
	
	public static boolean deleteSupplierObject(Supplier o) {
		return SupplierInstances.remove(o);
	}
	
	public static boolean deleteSupplierObjects(List<Supplier> os) {
		return SupplierInstances.removeAll(os);
	}
	public static OrderProduct createOrderProductObject() {
		OrderProduct o = new OrderProduct();
		return o;
	}
	
	public static boolean addOrderProductObject(OrderProduct o) {
		return OrderProductInstances.add(o);
	}
	
	public static boolean addOrderProductObjects(List<OrderProduct> os) {
		return OrderProductInstances.addAll(os);
	}
	
	public static boolean deleteOrderProductObject(OrderProduct o) {
		return OrderProductInstances.remove(o);
	}
	
	public static boolean deleteOrderProductObjects(List<OrderProduct> os) {
		return OrderProductInstances.removeAll(os);
	}
  
}

