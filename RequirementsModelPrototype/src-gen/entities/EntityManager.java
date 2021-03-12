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
	
	private static List<Contracts> ContractsInstances = new LinkedList<Contracts>();
	private static List<Client> ClientInstances = new LinkedList<Client>();
	private static List<Order> OrderInstances = new LinkedList<Order>();
	private static List<Invoice> InvoiceInstances = new LinkedList<Invoice>();
	private static List<BillOfLading> BillOfLadingInstances = new LinkedList<BillOfLading>();
	private static List<DeliveryNotification> DeliveryNotificationInstances = new LinkedList<DeliveryNotification>();
	private static List<ExchangeNotification> ExchangeNotificationInstances = new LinkedList<ExchangeNotification>();
	private static List<OrderMethod> OrderMethodInstances = new LinkedList<OrderMethod>();
	private static List<ClientGroup> ClientGroupInstances = new LinkedList<ClientGroup>();
	private static List<DeliveryMethod> DeliveryMethodInstances = new LinkedList<DeliveryMethod>();
	private static List<Product> ProductInstances = new LinkedList<Product>();
	private static List<OrderLineProduct> OrderLineProductInstances = new LinkedList<OrderLineProduct>();
	private static List<PlanLineProduct> PlanLineProductInstances = new LinkedList<PlanLineProduct>();
	private static List<SalePlan> SalePlanInstances = new LinkedList<SalePlan>();

	
	/* Put instances list into Map */
	static {
		AllInstance.put("Contracts", ContractsInstances);
		AllInstance.put("Client", ClientInstances);
		AllInstance.put("Order", OrderInstances);
		AllInstance.put("Invoice", InvoiceInstances);
		AllInstance.put("BillOfLading", BillOfLadingInstances);
		AllInstance.put("DeliveryNotification", DeliveryNotificationInstances);
		AllInstance.put("ExchangeNotification", ExchangeNotificationInstances);
		AllInstance.put("OrderMethod", OrderMethodInstances);
		AllInstance.put("ClientGroup", ClientGroupInstances);
		AllInstance.put("DeliveryMethod", DeliveryMethodInstances);
		AllInstance.put("Product", ProductInstances);
		AllInstance.put("OrderLineProduct", OrderLineProductInstances);
		AllInstance.put("PlanLineProduct", PlanLineProductInstances);
		AllInstance.put("SalePlan", SalePlanInstances);
	} 
		
	/* Save State */
	public static void save(File file) {
		
		try {
			
			ObjectOutputStream stateSave = new ObjectOutputStream(new FileOutputStream(file));
			
			stateSave.writeObject(ContractsInstances);
			stateSave.writeObject(ClientInstances);
			stateSave.writeObject(OrderInstances);
			stateSave.writeObject(InvoiceInstances);
			stateSave.writeObject(BillOfLadingInstances);
			stateSave.writeObject(DeliveryNotificationInstances);
			stateSave.writeObject(ExchangeNotificationInstances);
			stateSave.writeObject(OrderMethodInstances);
			stateSave.writeObject(ClientGroupInstances);
			stateSave.writeObject(DeliveryMethodInstances);
			stateSave.writeObject(ProductInstances);
			stateSave.writeObject(OrderLineProductInstances);
			stateSave.writeObject(PlanLineProductInstances);
			stateSave.writeObject(SalePlanInstances);
			
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
				
				ContractsInstances =  (List<Contracts>) stateLoad.readObject();
				AllInstance.put("Contracts", ContractsInstances);
				ClientInstances =  (List<Client>) stateLoad.readObject();
				AllInstance.put("Client", ClientInstances);
				OrderInstances =  (List<Order>) stateLoad.readObject();
				AllInstance.put("Order", OrderInstances);
				InvoiceInstances =  (List<Invoice>) stateLoad.readObject();
				AllInstance.put("Invoice", InvoiceInstances);
				BillOfLadingInstances =  (List<BillOfLading>) stateLoad.readObject();
				AllInstance.put("BillOfLading", BillOfLadingInstances);
				DeliveryNotificationInstances =  (List<DeliveryNotification>) stateLoad.readObject();
				AllInstance.put("DeliveryNotification", DeliveryNotificationInstances);
				ExchangeNotificationInstances =  (List<ExchangeNotification>) stateLoad.readObject();
				AllInstance.put("ExchangeNotification", ExchangeNotificationInstances);
				OrderMethodInstances =  (List<OrderMethod>) stateLoad.readObject();
				AllInstance.put("OrderMethod", OrderMethodInstances);
				ClientGroupInstances =  (List<ClientGroup>) stateLoad.readObject();
				AllInstance.put("ClientGroup", ClientGroupInstances);
				DeliveryMethodInstances =  (List<DeliveryMethod>) stateLoad.readObject();
				AllInstance.put("DeliveryMethod", DeliveryMethodInstances);
				ProductInstances =  (List<Product>) stateLoad.readObject();
				AllInstance.put("Product", ProductInstances);
				OrderLineProductInstances =  (List<OrderLineProduct>) stateLoad.readObject();
				AllInstance.put("OrderLineProduct", OrderLineProductInstances);
				PlanLineProductInstances =  (List<PlanLineProduct>) stateLoad.readObject();
				AllInstance.put("PlanLineProduct", PlanLineProductInstances);
				SalePlanInstances =  (List<SalePlan>) stateLoad.readObject();
				AllInstance.put("SalePlan", SalePlanInstances);
				
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
	public static Contracts createContractsObject() {
		Contracts o = new Contracts();
		return o;
	}
	
	public static boolean addContractsObject(Contracts o) {
		return ContractsInstances.add(o);
	}
	
	public static boolean addContractsObjects(List<Contracts> os) {
		return ContractsInstances.addAll(os);
	}
	
	public static boolean deleteContractsObject(Contracts o) {
		return ContractsInstances.remove(o);
	}
	
	public static boolean deleteContractsObjects(List<Contracts> os) {
		return ContractsInstances.removeAll(os);
	}
	public static Client createClientObject() {
		Client o = new Client();
		return o;
	}
	
	public static boolean addClientObject(Client o) {
		return ClientInstances.add(o);
	}
	
	public static boolean addClientObjects(List<Client> os) {
		return ClientInstances.addAll(os);
	}
	
	public static boolean deleteClientObject(Client o) {
		return ClientInstances.remove(o);
	}
	
	public static boolean deleteClientObjects(List<Client> os) {
		return ClientInstances.removeAll(os);
	}
	public static Order createOrderObject() {
		Order o = new Order();
		return o;
	}
	
	public static boolean addOrderObject(Order o) {
		return OrderInstances.add(o);
	}
	
	public static boolean addOrderObjects(List<Order> os) {
		return OrderInstances.addAll(os);
	}
	
	public static boolean deleteOrderObject(Order o) {
		return OrderInstances.remove(o);
	}
	
	public static boolean deleteOrderObjects(List<Order> os) {
		return OrderInstances.removeAll(os);
	}
	public static Invoice createInvoiceObject() {
		Invoice o = new Invoice();
		return o;
	}
	
	public static boolean addInvoiceObject(Invoice o) {
		return InvoiceInstances.add(o);
	}
	
	public static boolean addInvoiceObjects(List<Invoice> os) {
		return InvoiceInstances.addAll(os);
	}
	
	public static boolean deleteInvoiceObject(Invoice o) {
		return InvoiceInstances.remove(o);
	}
	
	public static boolean deleteInvoiceObjects(List<Invoice> os) {
		return InvoiceInstances.removeAll(os);
	}
	public static BillOfLading createBillOfLadingObject() {
		BillOfLading o = new BillOfLading();
		return o;
	}
	
	public static boolean addBillOfLadingObject(BillOfLading o) {
		return BillOfLadingInstances.add(o);
	}
	
	public static boolean addBillOfLadingObjects(List<BillOfLading> os) {
		return BillOfLadingInstances.addAll(os);
	}
	
	public static boolean deleteBillOfLadingObject(BillOfLading o) {
		return BillOfLadingInstances.remove(o);
	}
	
	public static boolean deleteBillOfLadingObjects(List<BillOfLading> os) {
		return BillOfLadingInstances.removeAll(os);
	}
	public static DeliveryNotification createDeliveryNotificationObject() {
		DeliveryNotification o = new DeliveryNotification();
		return o;
	}
	
	public static boolean addDeliveryNotificationObject(DeliveryNotification o) {
		return DeliveryNotificationInstances.add(o);
	}
	
	public static boolean addDeliveryNotificationObjects(List<DeliveryNotification> os) {
		return DeliveryNotificationInstances.addAll(os);
	}
	
	public static boolean deleteDeliveryNotificationObject(DeliveryNotification o) {
		return DeliveryNotificationInstances.remove(o);
	}
	
	public static boolean deleteDeliveryNotificationObjects(List<DeliveryNotification> os) {
		return DeliveryNotificationInstances.removeAll(os);
	}
	public static ExchangeNotification createExchangeNotificationObject() {
		ExchangeNotification o = new ExchangeNotification();
		return o;
	}
	
	public static boolean addExchangeNotificationObject(ExchangeNotification o) {
		return ExchangeNotificationInstances.add(o);
	}
	
	public static boolean addExchangeNotificationObjects(List<ExchangeNotification> os) {
		return ExchangeNotificationInstances.addAll(os);
	}
	
	public static boolean deleteExchangeNotificationObject(ExchangeNotification o) {
		return ExchangeNotificationInstances.remove(o);
	}
	
	public static boolean deleteExchangeNotificationObjects(List<ExchangeNotification> os) {
		return ExchangeNotificationInstances.removeAll(os);
	}
	public static OrderMethod createOrderMethodObject() {
		OrderMethod o = new OrderMethod();
		return o;
	}
	
	public static boolean addOrderMethodObject(OrderMethod o) {
		return OrderMethodInstances.add(o);
	}
	
	public static boolean addOrderMethodObjects(List<OrderMethod> os) {
		return OrderMethodInstances.addAll(os);
	}
	
	public static boolean deleteOrderMethodObject(OrderMethod o) {
		return OrderMethodInstances.remove(o);
	}
	
	public static boolean deleteOrderMethodObjects(List<OrderMethod> os) {
		return OrderMethodInstances.removeAll(os);
	}
	public static ClientGroup createClientGroupObject() {
		ClientGroup o = new ClientGroup();
		return o;
	}
	
	public static boolean addClientGroupObject(ClientGroup o) {
		return ClientGroupInstances.add(o);
	}
	
	public static boolean addClientGroupObjects(List<ClientGroup> os) {
		return ClientGroupInstances.addAll(os);
	}
	
	public static boolean deleteClientGroupObject(ClientGroup o) {
		return ClientGroupInstances.remove(o);
	}
	
	public static boolean deleteClientGroupObjects(List<ClientGroup> os) {
		return ClientGroupInstances.removeAll(os);
	}
	public static DeliveryMethod createDeliveryMethodObject() {
		DeliveryMethod o = new DeliveryMethod();
		return o;
	}
	
	public static boolean addDeliveryMethodObject(DeliveryMethod o) {
		return DeliveryMethodInstances.add(o);
	}
	
	public static boolean addDeliveryMethodObjects(List<DeliveryMethod> os) {
		return DeliveryMethodInstances.addAll(os);
	}
	
	public static boolean deleteDeliveryMethodObject(DeliveryMethod o) {
		return DeliveryMethodInstances.remove(o);
	}
	
	public static boolean deleteDeliveryMethodObjects(List<DeliveryMethod> os) {
		return DeliveryMethodInstances.removeAll(os);
	}
	public static Product createProductObject() {
		Product o = new Product();
		return o;
	}
	
	public static boolean addProductObject(Product o) {
		return ProductInstances.add(o);
	}
	
	public static boolean addProductObjects(List<Product> os) {
		return ProductInstances.addAll(os);
	}
	
	public static boolean deleteProductObject(Product o) {
		return ProductInstances.remove(o);
	}
	
	public static boolean deleteProductObjects(List<Product> os) {
		return ProductInstances.removeAll(os);
	}
	public static OrderLineProduct createOrderLineProductObject() {
		OrderLineProduct o = new OrderLineProduct();
		return o;
	}
	
	public static boolean addOrderLineProductObject(OrderLineProduct o) {
		return OrderLineProductInstances.add(o);
	}
	
	public static boolean addOrderLineProductObjects(List<OrderLineProduct> os) {
		return OrderLineProductInstances.addAll(os);
	}
	
	public static boolean deleteOrderLineProductObject(OrderLineProduct o) {
		return OrderLineProductInstances.remove(o);
	}
	
	public static boolean deleteOrderLineProductObjects(List<OrderLineProduct> os) {
		return OrderLineProductInstances.removeAll(os);
	}
	public static PlanLineProduct createPlanLineProductObject() {
		PlanLineProduct o = new PlanLineProduct();
		return o;
	}
	
	public static boolean addPlanLineProductObject(PlanLineProduct o) {
		return PlanLineProductInstances.add(o);
	}
	
	public static boolean addPlanLineProductObjects(List<PlanLineProduct> os) {
		return PlanLineProductInstances.addAll(os);
	}
	
	public static boolean deletePlanLineProductObject(PlanLineProduct o) {
		return PlanLineProductInstances.remove(o);
	}
	
	public static boolean deletePlanLineProductObjects(List<PlanLineProduct> os) {
		return PlanLineProductInstances.removeAll(os);
	}
	public static SalePlan createSalePlanObject() {
		SalePlan o = new SalePlan();
		return o;
	}
	
	public static boolean addSalePlanObject(SalePlan o) {
		return SalePlanInstances.add(o);
	}
	
	public static boolean addSalePlanObjects(List<SalePlan> os) {
		return SalePlanInstances.addAll(os);
	}
	
	public static boolean deleteSalePlanObject(SalePlan o) {
		return SalePlanInstances.remove(o);
	}
	
	public static boolean deleteSalePlanObjects(List<SalePlan> os) {
		return SalePlanInstances.removeAll(os);
	}
  
}

