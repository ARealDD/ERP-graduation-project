package services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import services.*;
	
public class ServiceManager {
	
	private static Map<String, List> AllServiceInstance = new HashMap<String, List>();
	
	private static List<SalesManagementSystemSystem> SalesManagementSystemSystemInstances = new LinkedList<SalesManagementSystemSystem>();
	private static List<ThirdPartyServices> ThirdPartyServicesInstances = new LinkedList<ThirdPartyServices>();
	private static List<ManageContractsCRUDService> ManageContractsCRUDServiceInstances = new LinkedList<ManageContractsCRUDService>();
	private static List<ManageClientCRUDService> ManageClientCRUDServiceInstances = new LinkedList<ManageClientCRUDService>();
	private static List<ManageOrderCRUDService> ManageOrderCRUDServiceInstances = new LinkedList<ManageOrderCRUDService>();
	private static List<ManageInvoiceCRUDService> ManageInvoiceCRUDServiceInstances = new LinkedList<ManageInvoiceCRUDService>();
	private static List<ManageBillOfLadingCRUDService> ManageBillOfLadingCRUDServiceInstances = new LinkedList<ManageBillOfLadingCRUDService>();
	private static List<ManageProductCRUDService> ManageProductCRUDServiceInstances = new LinkedList<ManageProductCRUDService>();
	private static List<SalesProcessingService> SalesProcessingServiceInstances = new LinkedList<SalesProcessingService>();
	
	static {
		AllServiceInstance.put("SalesManagementSystemSystem", SalesManagementSystemSystemInstances);
		AllServiceInstance.put("ThirdPartyServices", ThirdPartyServicesInstances);
		AllServiceInstance.put("ManageContractsCRUDService", ManageContractsCRUDServiceInstances);
		AllServiceInstance.put("ManageClientCRUDService", ManageClientCRUDServiceInstances);
		AllServiceInstance.put("ManageOrderCRUDService", ManageOrderCRUDServiceInstances);
		AllServiceInstance.put("ManageInvoiceCRUDService", ManageInvoiceCRUDServiceInstances);
		AllServiceInstance.put("ManageBillOfLadingCRUDService", ManageBillOfLadingCRUDServiceInstances);
		AllServiceInstance.put("ManageProductCRUDService", ManageProductCRUDServiceInstances);
		AllServiceInstance.put("SalesProcessingService", SalesProcessingServiceInstances);
	} 
	
	public static List getAllInstancesOf(String ClassName) {
			 return AllServiceInstance.get(ClassName);
	}	
	
	public static SalesManagementSystemSystem createSalesManagementSystemSystem() {
		SalesManagementSystemSystem s = new SalesManagementSystemSystemImpl();
		SalesManagementSystemSystemInstances.add(s);
		return s;
	}
	public static ThirdPartyServices createThirdPartyServices() {
		ThirdPartyServices s = new ThirdPartyServicesImpl();
		ThirdPartyServicesInstances.add(s);
		return s;
	}
	public static ManageContractsCRUDService createManageContractsCRUDService() {
		ManageContractsCRUDService s = new ManageContractsCRUDServiceImpl();
		ManageContractsCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageClientCRUDService createManageClientCRUDService() {
		ManageClientCRUDService s = new ManageClientCRUDServiceImpl();
		ManageClientCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageOrderCRUDService createManageOrderCRUDService() {
		ManageOrderCRUDService s = new ManageOrderCRUDServiceImpl();
		ManageOrderCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageInvoiceCRUDService createManageInvoiceCRUDService() {
		ManageInvoiceCRUDService s = new ManageInvoiceCRUDServiceImpl();
		ManageInvoiceCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageBillOfLadingCRUDService createManageBillOfLadingCRUDService() {
		ManageBillOfLadingCRUDService s = new ManageBillOfLadingCRUDServiceImpl();
		ManageBillOfLadingCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageProductCRUDService createManageProductCRUDService() {
		ManageProductCRUDService s = new ManageProductCRUDServiceImpl();
		ManageProductCRUDServiceInstances.add(s);
		return s;
	}
	public static SalesProcessingService createSalesProcessingService() {
		SalesProcessingService s = new SalesProcessingServiceImpl();
		SalesProcessingServiceInstances.add(s);
		return s;
	}
}	
