package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.time.LocalDate;
import java.util.LinkedList;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import gui.supportclass.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import services.*;
import services.impl.*;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

import entities.*;

public class PrototypeController implements Initializable {


	DateTimeFormatter dateformatter;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		salesmanagementsystemsystem_service = ServiceManager.createSalesManagementSystemSystem();
		thirdpartyservices_service = ServiceManager.createThirdPartyServices();
		managecontractscrudservice_service = ServiceManager.createManageContractsCRUDService();
		manageclientcrudservice_service = ServiceManager.createManageClientCRUDService();
		manageordercrudservice_service = ServiceManager.createManageOrderCRUDService();
		manageinvoicecrudservice_service = ServiceManager.createManageInvoiceCRUDService();
		managebillofladingcrudservice_service = ServiceManager.createManageBillOfLadingCRUDService();
		manageproductcrudservice_service = ServiceManager.createManageProductCRUDService();
		salesprocessingservice_service = ServiceManager.createSalesProcessingService();
				
		this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	   	 //prepare data for contract
	   	 prepareData();
	   	 
	   	 //generate invariant panel
	   	 genereateInvairantPanel();
	   	 
		 //Actor Threeview Binding
		 actorTreeViewBinding();
		 
		 //Generate
		 generatOperationPane();
		 genereateOpInvariantPanel();
		 
		 //prilimariry data
		 try {
			DataFitService.fit();
		 } catch (PreconditionException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
		 
		 //generate class statistic
		 classStatisicBingding();
		 
		 //generate object statistic
		 generateObjectTable();
		 
		 //genereate association statistic
		 associationStatisicBingding();

		 //set listener 
		 setListeners();
	}
	
	/**
	 * deepCopyforTreeItem (Actor Generation)
	 */
	TreeItem<String> deepCopyTree(TreeItem<String> item) {
		    TreeItem<String> copy = new TreeItem<String>(item.getValue());
		    for (TreeItem<String> child : item.getChildren()) {
		        copy.getChildren().add(deepCopyTree(child));
		    }
		    return copy;
	}
	
	/**
	 * check all invariant and update invariant panel
	 */
	public void invairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}				
			}
			
			for (Entry<String, Label> inv : service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * check op invariant and update op invariant panel
	 */		
	public void opInvairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : op_entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
			for (Entry<String, Label> inv : op_service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
	*	generate op invariant panel 
	*/
	public void genereateOpInvariantPanel() {
		
		opInvariantPanel = new HashMap<String, VBox>();
		op_entity_invariants_label_map = new LinkedHashMap<String, Label>();
		op_service_invariants_label_map = new LinkedHashMap<String, Label>();
		
		VBox v;
		List<String> entities;
		v = new VBox();
		
		//entities invariants
		entities = SalesProcessingServiceImpl.opINVRelatedEntity.get("makeNewOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("makeNewOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesProcessingService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("makeNewOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesProcessingServiceImpl.opINVRelatedEntity.get("addProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("addProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesProcessingService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("addProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesProcessingServiceImpl.opINVRelatedEntity.get("generateContract");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("generateContract" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesProcessingService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("generateContract", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesProcessingServiceImpl.opINVRelatedEntity.get("generateOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("generateOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesProcessingService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("generateOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("salesPlanManagement");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("salesPlanManagement" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("salesPlanManagement", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("deliveryNotification");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deliveryNotification" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deliveryNotification", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("exchangeNotification");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("exchangeNotification" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("exchangeNotification", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("itemsPricesMaintenance");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("itemsPricesMaintenance" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("itemsPricesMaintenance", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("postingOfAccount");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("postingOfAccount" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("postingOfAccount", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("invoiceManagement");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("invoiceManagement" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("invoiceManagement", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("contractTerminationAndSettlement");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("contractTerminationAndSettlement" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("contractTerminationAndSettlement", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("orderTerminationAndSettlement");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("orderTerminationAndSettlement" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("orderTerminationAndSettlement", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("salesCommissionManagement");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("salesCommissionManagement" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("salesCommissionManagement", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesProcessingServiceImpl.opINVRelatedEntity.get("authorization");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("authorization" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesProcessingService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("authorization", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ThirdPartyServicesImpl.opINVRelatedEntity.get("authorizationProcessing");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("authorizationProcessing" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ThirdPartyServices")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("authorizationProcessing", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageContractsCRUDServiceImpl.opINVRelatedEntity.get("createContracts");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createContracts" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageContractsCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createContracts", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageContractsCRUDServiceImpl.opINVRelatedEntity.get("queryContracts");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryContracts" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageContractsCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryContracts", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageContractsCRUDServiceImpl.opINVRelatedEntity.get("modifyContracts");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyContracts" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageContractsCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyContracts", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageContractsCRUDServiceImpl.opINVRelatedEntity.get("deleteContracts");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteContracts" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageContractsCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteContracts", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageInvoiceCRUDServiceImpl.opINVRelatedEntity.get("createInvoice");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createInvoice" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageInvoiceCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createInvoice", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageInvoiceCRUDServiceImpl.opINVRelatedEntity.get("queryInvoice");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryInvoice" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageInvoiceCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryInvoice", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageInvoiceCRUDServiceImpl.opINVRelatedEntity.get("modifyInvoice");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyInvoice" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageInvoiceCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyInvoice", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageInvoiceCRUDServiceImpl.opINVRelatedEntity.get("deleteInvoice");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteInvoice" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageInvoiceCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteInvoice", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBillOfLadingCRUDServiceImpl.opINVRelatedEntity.get("createBillOfLading");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createBillOfLading" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBillOfLadingCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createBillOfLading", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBillOfLadingCRUDServiceImpl.opINVRelatedEntity.get("queryBillOfLading");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryBillOfLading" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBillOfLadingCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryBillOfLading", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBillOfLadingCRUDServiceImpl.opINVRelatedEntity.get("modifyBillOfLading");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyBillOfLading" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBillOfLadingCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyBillOfLading", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBillOfLadingCRUDServiceImpl.opINVRelatedEntity.get("deleteBillOfLading");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteBillOfLading" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBillOfLadingCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteBillOfLading", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageClientCRUDServiceImpl.opINVRelatedEntity.get("createClient");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createClient" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageClientCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createClient", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageClientCRUDServiceImpl.opINVRelatedEntity.get("queryClient");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryClient" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageClientCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryClient", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageClientCRUDServiceImpl.opINVRelatedEntity.get("modifyClient");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyClient" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageClientCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyClient", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageClientCRUDServiceImpl.opINVRelatedEntity.get("deleteClient");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteClient" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageClientCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteClient", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("createOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("queryOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("modifyOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageOrderCRUDServiceImpl.opINVRelatedEntity.get("deleteOrder");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteOrder" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageOrderCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteOrder", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageProductCRUDServiceImpl.opINVRelatedEntity.get("createProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageProductCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageProductCRUDServiceImpl.opINVRelatedEntity.get("queryProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageProductCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageProductCRUDServiceImpl.opINVRelatedEntity.get("modifyProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageProductCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageProductCRUDServiceImpl.opINVRelatedEntity.get("deleteProduct");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteProduct" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageProductCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteProduct", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SalesManagementSystemSystemImpl.opINVRelatedEntity.get("manageItemsPrices");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("manageItemsPrices" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SalesManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("manageItemsPrices", v);
		
		
	}
	
	
	/*
	*  generate invariant panel
	*/
	public void genereateInvairantPanel() {
		
		service_invariants_label_map = new LinkedHashMap<String, Label>();
		entity_invariants_label_map = new LinkedHashMap<String, Label>();
		
		//entity_invariants_map
		VBox v = new VBox();
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			Label l = new Label(inv.getKey());
			l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			service_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		//entity invariants
		for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
			
			String INVname = inv.getKey();
			Label l = new Label(INVname);
			if (INVname.contains("AssociationInvariants")) {
				l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #099b17 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			} else {
				l.setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
			}	
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			entity_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		ScrollPane scrollPane = new ScrollPane(v);
		scrollPane.setFitToWidth(true);
		all_invariant_pane.setMaxHeight(850);
		
		all_invariant_pane.setContent(scrollPane);
	}	
	
	
	
	/* 
	*	mainPane add listener
	*/
	public void setListeners() {
		 mainPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			 
			 	if (newTab.getText().equals("System State")) {
			 		System.out.println("refresh all");
			 		refreshAll();
			 	}
		    
		    });
	}
	
	
	//checking all invariants
	public void checkAllInvariants() {
		
		invairantPanelUpdate();
	
	}	
	
	//refresh all
	public void refreshAll() {
		
		invairantPanelUpdate();
		classStatisticUpdate();
		generateObjectTable();
	}
	
	
	//update association
	public void updateAssociation(String className) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber();
		}
		
	}
	
	public void updateAssociation(String className, int index) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber(index);
		}
		
	}	
	
	public void generateObjectTable() {
		
		allObjectTables = new LinkedHashMap<String, TableView>();
		
		TableView<Map<String, String>> tableContracts = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableContracts_Id = new TableColumn<Map<String, String>, String>("Id");
		tableContracts_Id.setMinWidth("Id".length()*10);
		tableContracts_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_Id);
		TableColumn<Map<String, String>, String> tableContracts_Buyer = new TableColumn<Map<String, String>, String>("Buyer");
		tableContracts_Buyer.setMinWidth("Buyer".length()*10);
		tableContracts_Buyer.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Buyer"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_Buyer);
		TableColumn<Map<String, String>, String> tableContracts_Packing = new TableColumn<Map<String, String>, String>("Packing");
		tableContracts_Packing.setMinWidth("Packing".length()*10);
		tableContracts_Packing.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Packing"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_Packing);
		TableColumn<Map<String, String>, String> tableContracts_DateOfShipment = new TableColumn<Map<String, String>, String>("DateOfShipment");
		tableContracts_DateOfShipment.setMinWidth("DateOfShipment".length()*10);
		tableContracts_DateOfShipment.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("DateOfShipment"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_DateOfShipment);
		TableColumn<Map<String, String>, String> tableContracts_PortOfShipment = new TableColumn<Map<String, String>, String>("PortOfShipment");
		tableContracts_PortOfShipment.setMinWidth("PortOfShipment".length()*10);
		tableContracts_PortOfShipment.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("PortOfShipment"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_PortOfShipment);
		TableColumn<Map<String, String>, String> tableContracts_PortOfDestination = new TableColumn<Map<String, String>, String>("PortOfDestination");
		tableContracts_PortOfDestination.setMinWidth("PortOfDestination".length()*10);
		tableContracts_PortOfDestination.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("PortOfDestination"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_PortOfDestination);
		TableColumn<Map<String, String>, String> tableContracts_Insurance = new TableColumn<Map<String, String>, String>("Insurance");
		tableContracts_Insurance.setMinWidth("Insurance".length()*10);
		tableContracts_Insurance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Insurance"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_Insurance);
		TableColumn<Map<String, String>, String> tableContracts_EffectiveDate = new TableColumn<Map<String, String>, String>("EffectiveDate");
		tableContracts_EffectiveDate.setMinWidth("EffectiveDate".length()*10);
		tableContracts_EffectiveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EffectiveDate"));
		    }
		});	
		tableContracts.getColumns().add(tableContracts_EffectiveDate);
		
		//table data
		ObservableList<Map<String, String>> dataContracts = FXCollections.observableArrayList();
		List<Contracts> rsContracts = EntityManager.getAllInstancesOf("Contracts");
		for (Contracts r : rsContracts) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getBuyer() != null)
				unit.put("Buyer", String.valueOf(r.getBuyer()));
			else
				unit.put("Buyer", "");
			if (r.getPacking() != null)
				unit.put("Packing", String.valueOf(r.getPacking()));
			else
				unit.put("Packing", "");
			if (r.getDateOfShipment() != null)
				unit.put("DateOfShipment", r.getDateOfShipment().format(dateformatter));
			else
				unit.put("DateOfShipment", "");
			if (r.getPortOfShipment() != null)
				unit.put("PortOfShipment", String.valueOf(r.getPortOfShipment()));
			else
				unit.put("PortOfShipment", "");
			if (r.getPortOfDestination() != null)
				unit.put("PortOfDestination", String.valueOf(r.getPortOfDestination()));
			else
				unit.put("PortOfDestination", "");
			if (r.getInsurance() != null)
				unit.put("Insurance", String.valueOf(r.getInsurance()));
			else
				unit.put("Insurance", "");
			if (r.getEffectiveDate() != null)
				unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
			else
				unit.put("EffectiveDate", "");

			dataContracts.add(unit);
		}
		
		tableContracts.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableContracts.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Contracts", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableContracts.setItems(dataContracts);
		allObjectTables.put("Contracts", tableContracts);
		
		TableView<Map<String, String>> tableClient = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableClient_Id = new TableColumn<Map<String, String>, String>("Id");
		tableClient_Id.setMinWidth("Id".length()*10);
		tableClient_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableClient.getColumns().add(tableClient_Id);
		TableColumn<Map<String, String>, String> tableClient_Name = new TableColumn<Map<String, String>, String>("Name");
		tableClient_Name.setMinWidth("Name".length()*10);
		tableClient_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableClient.getColumns().add(tableClient_Name);
		TableColumn<Map<String, String>, String> tableClient_Address = new TableColumn<Map<String, String>, String>("Address");
		tableClient_Address.setMinWidth("Address".length()*10);
		tableClient_Address.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Address"));
		    }
		});	
		tableClient.getColumns().add(tableClient_Address);
		TableColumn<Map<String, String>, String> tableClient_Contact = new TableColumn<Map<String, String>, String>("Contact");
		tableClient_Contact.setMinWidth("Contact".length()*10);
		tableClient_Contact.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Contact"));
		    }
		});	
		tableClient.getColumns().add(tableClient_Contact);
		TableColumn<Map<String, String>, String> tableClient_PhoneNumber = new TableColumn<Map<String, String>, String>("PhoneNumber");
		tableClient_PhoneNumber.setMinWidth("PhoneNumber".length()*10);
		tableClient_PhoneNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("PhoneNumber"));
		    }
		});	
		tableClient.getColumns().add(tableClient_PhoneNumber);
		
		//table data
		ObservableList<Map<String, String>> dataClient = FXCollections.observableArrayList();
		List<Client> rsClient = EntityManager.getAllInstancesOf("Client");
		for (Client r : rsClient) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			if (r.getAddress() != null)
				unit.put("Address", String.valueOf(r.getAddress()));
			else
				unit.put("Address", "");
			if (r.getContact() != null)
				unit.put("Contact", String.valueOf(r.getContact()));
			else
				unit.put("Contact", "");
			if (r.getPhoneNumber() != null)
				unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
			else
				unit.put("PhoneNumber", "");

			dataClient.add(unit);
		}
		
		tableClient.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableClient.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Client", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableClient.setItems(dataClient);
		allObjectTables.put("Client", tableClient);
		
		TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableOrder_Id = new TableColumn<Map<String, String>, String>("Id");
		tableOrder_Id.setMinWidth("Id".length()*10);
		tableOrder_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_Id);
		TableColumn<Map<String, String>, String> tableOrder_IsCompleted = new TableColumn<Map<String, String>, String>("IsCompleted");
		tableOrder_IsCompleted.setMinWidth("IsCompleted".length()*10);
		tableOrder_IsCompleted.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsCompleted"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_IsCompleted);
		TableColumn<Map<String, String>, String> tableOrder_PaymentInformation = new TableColumn<Map<String, String>, String>("PaymentInformation");
		tableOrder_PaymentInformation.setMinWidth("PaymentInformation".length()*10);
		tableOrder_PaymentInformation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("PaymentInformation"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_PaymentInformation);
		TableColumn<Map<String, String>, String> tableOrder_Amount = new TableColumn<Map<String, String>, String>("Amount");
		tableOrder_Amount.setMinWidth("Amount".length()*10);
		tableOrder_Amount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Amount"));
		    }
		});	
		tableOrder.getColumns().add(tableOrder_Amount);
		
		//table data
		ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
		List<Order> rsOrder = EntityManager.getAllInstancesOf("Order");
		for (Order r : rsOrder) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			unit.put("IsCompleted", String.valueOf(r.getIsCompleted()));
			if (r.getPaymentInformation() != null)
				unit.put("PaymentInformation", String.valueOf(r.getPaymentInformation()));
			else
				unit.put("PaymentInformation", "");
			unit.put("Amount", String.valueOf(r.getAmount()));

			dataOrder.add(unit);
		}
		
		tableOrder.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableOrder.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Order", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableOrder.setItems(dataOrder);
		allObjectTables.put("Order", tableOrder);
		
		TableView<Map<String, String>> tableInvoice = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableInvoice_Id = new TableColumn<Map<String, String>, String>("Id");
		tableInvoice_Id.setMinWidth("Id".length()*10);
		tableInvoice_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableInvoice.getColumns().add(tableInvoice_Id);
		TableColumn<Map<String, String>, String> tableInvoice_Title = new TableColumn<Map<String, String>, String>("Title");
		tableInvoice_Title.setMinWidth("Title".length()*10);
		tableInvoice_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
		    }
		});	
		tableInvoice.getColumns().add(tableInvoice_Title);
		TableColumn<Map<String, String>, String> tableInvoice_EffecitveDate = new TableColumn<Map<String, String>, String>("EffecitveDate");
		tableInvoice_EffecitveDate.setMinWidth("EffecitveDate".length()*10);
		tableInvoice_EffecitveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EffecitveDate"));
		    }
		});	
		tableInvoice.getColumns().add(tableInvoice_EffecitveDate);
		TableColumn<Map<String, String>, String> tableInvoice_Amount = new TableColumn<Map<String, String>, String>("Amount");
		tableInvoice_Amount.setMinWidth("Amount".length()*10);
		tableInvoice_Amount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Amount"));
		    }
		});	
		tableInvoice.getColumns().add(tableInvoice_Amount);
		
		//table data
		ObservableList<Map<String, String>> dataInvoice = FXCollections.observableArrayList();
		List<Invoice> rsInvoice = EntityManager.getAllInstancesOf("Invoice");
		for (Invoice r : rsInvoice) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getTitle() != null)
				unit.put("Title", String.valueOf(r.getTitle()));
			else
				unit.put("Title", "");
			if (r.getEffecitveDate() != null)
				unit.put("EffecitveDate", r.getEffecitveDate().format(dateformatter));
			else
				unit.put("EffecitveDate", "");
			unit.put("Amount", String.valueOf(r.getAmount()));

			dataInvoice.add(unit);
		}
		
		tableInvoice.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableInvoice.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Invoice", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableInvoice.setItems(dataInvoice);
		allObjectTables.put("Invoice", tableInvoice);
		
		TableView<Map<String, String>> tableBillOfLading = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableBillOfLading_Id = new TableColumn<Map<String, String>, String>("Id");
		tableBillOfLading_Id.setMinWidth("Id".length()*10);
		tableBillOfLading_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_Id);
		TableColumn<Map<String, String>, String> tableBillOfLading_Consignee = new TableColumn<Map<String, String>, String>("Consignee");
		tableBillOfLading_Consignee.setMinWidth("Consignee".length()*10);
		tableBillOfLading_Consignee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Consignee"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_Consignee);
		TableColumn<Map<String, String>, String> tableBillOfLading_CommodityList = new TableColumn<Map<String, String>, String>("CommodityList");
		tableBillOfLading_CommodityList.setMinWidth("CommodityList".length()*10);
		tableBillOfLading_CommodityList.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CommodityList"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_CommodityList);
		TableColumn<Map<String, String>, String> tableBillOfLading_ContractObject = new TableColumn<Map<String, String>, String>("ContractObject");
		tableBillOfLading_ContractObject.setMinWidth("ContractObject".length()*10);
		tableBillOfLading_ContractObject.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ContractObject"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_ContractObject);
		TableColumn<Map<String, String>, String> tableBillOfLading_Quantity = new TableColumn<Map<String, String>, String>("Quantity");
		tableBillOfLading_Quantity.setMinWidth("Quantity".length()*10);
		tableBillOfLading_Quantity.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Quantity"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_Quantity);
		TableColumn<Map<String, String>, String> tableBillOfLading_TotalPrice = new TableColumn<Map<String, String>, String>("TotalPrice");
		tableBillOfLading_TotalPrice.setMinWidth("TotalPrice".length()*10);
		tableBillOfLading_TotalPrice.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("TotalPrice"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_TotalPrice);
		TableColumn<Map<String, String>, String> tableBillOfLading_DeadlineForPerformance = new TableColumn<Map<String, String>, String>("DeadlineForPerformance");
		tableBillOfLading_DeadlineForPerformance.setMinWidth("DeadlineForPerformance".length()*10);
		tableBillOfLading_DeadlineForPerformance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("DeadlineForPerformance"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_DeadlineForPerformance);
		TableColumn<Map<String, String>, String> tableBillOfLading_LocationForPerformance = new TableColumn<Map<String, String>, String>("LocationForPerformance");
		tableBillOfLading_LocationForPerformance.setMinWidth("LocationForPerformance".length()*10);
		tableBillOfLading_LocationForPerformance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LocationForPerformance"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_LocationForPerformance);
		TableColumn<Map<String, String>, String> tableBillOfLading_MethodForPerformance = new TableColumn<Map<String, String>, String>("MethodForPerformance");
		tableBillOfLading_MethodForPerformance.setMinWidth("MethodForPerformance".length()*10);
		tableBillOfLading_MethodForPerformance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("MethodForPerformance"));
		    }
		});	
		tableBillOfLading.getColumns().add(tableBillOfLading_MethodForPerformance);
		
		//table data
		ObservableList<Map<String, String>> dataBillOfLading = FXCollections.observableArrayList();
		List<BillOfLading> rsBillOfLading = EntityManager.getAllInstancesOf("BillOfLading");
		for (BillOfLading r : rsBillOfLading) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getConsignee() != null)
				unit.put("Consignee", String.valueOf(r.getConsignee()));
			else
				unit.put("Consignee", "");
			if (r.getCommodityList() != null)
				unit.put("CommodityList", String.valueOf(r.getCommodityList()));
			else
				unit.put("CommodityList", "");
			if (r.getContractObject() != null)
				unit.put("ContractObject", String.valueOf(r.getContractObject()));
			else
				unit.put("ContractObject", "");
			unit.put("Quantity", String.valueOf(r.getQuantity()));
			unit.put("TotalPrice", String.valueOf(r.getTotalPrice()));
			if (r.getDeadlineForPerformance() != null)
				unit.put("DeadlineForPerformance", String.valueOf(r.getDeadlineForPerformance()));
			else
				unit.put("DeadlineForPerformance", "");
			if (r.getLocationForPerformance() != null)
				unit.put("LocationForPerformance", String.valueOf(r.getLocationForPerformance()));
			else
				unit.put("LocationForPerformance", "");
			if (r.getMethodForPerformance() != null)
				unit.put("MethodForPerformance", String.valueOf(r.getMethodForPerformance()));
			else
				unit.put("MethodForPerformance", "");

			dataBillOfLading.add(unit);
		}
		
		tableBillOfLading.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableBillOfLading.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("BillOfLading", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableBillOfLading.setItems(dataBillOfLading);
		allObjectTables.put("BillOfLading", tableBillOfLading);
		
		TableView<Map<String, String>> tableDeliveryNotification = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableDeliveryNotification_Id = new TableColumn<Map<String, String>, String>("Id");
		tableDeliveryNotification_Id.setMinWidth("Id".length()*10);
		tableDeliveryNotification_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableDeliveryNotification.getColumns().add(tableDeliveryNotification_Id);
		TableColumn<Map<String, String>, String> tableDeliveryNotification_EffectiveDate = new TableColumn<Map<String, String>, String>("EffectiveDate");
		tableDeliveryNotification_EffectiveDate.setMinWidth("EffectiveDate".length()*10);
		tableDeliveryNotification_EffectiveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EffectiveDate"));
		    }
		});	
		tableDeliveryNotification.getColumns().add(tableDeliveryNotification_EffectiveDate);
		TableColumn<Map<String, String>, String> tableDeliveryNotification_Details = new TableColumn<Map<String, String>, String>("Details");
		tableDeliveryNotification_Details.setMinWidth("Details".length()*10);
		tableDeliveryNotification_Details.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Details"));
		    }
		});	
		tableDeliveryNotification.getColumns().add(tableDeliveryNotification_Details);
		
		//table data
		ObservableList<Map<String, String>> dataDeliveryNotification = FXCollections.observableArrayList();
		List<DeliveryNotification> rsDeliveryNotification = EntityManager.getAllInstancesOf("DeliveryNotification");
		for (DeliveryNotification r : rsDeliveryNotification) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getEffectiveDate() != null)
				unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
			else
				unit.put("EffectiveDate", "");
			if (r.getDetails() != null)
				unit.put("Details", String.valueOf(r.getDetails()));
			else
				unit.put("Details", "");

			dataDeliveryNotification.add(unit);
		}
		
		tableDeliveryNotification.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableDeliveryNotification.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("DeliveryNotification", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableDeliveryNotification.setItems(dataDeliveryNotification);
		allObjectTables.put("DeliveryNotification", tableDeliveryNotification);
		
		TableView<Map<String, String>> tableExchangeNotification = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableExchangeNotification_Id = new TableColumn<Map<String, String>, String>("Id");
		tableExchangeNotification_Id.setMinWidth("Id".length()*10);
		tableExchangeNotification_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableExchangeNotification.getColumns().add(tableExchangeNotification_Id);
		TableColumn<Map<String, String>, String> tableExchangeNotification_EffectiveDate = new TableColumn<Map<String, String>, String>("EffectiveDate");
		tableExchangeNotification_EffectiveDate.setMinWidth("EffectiveDate".length()*10);
		tableExchangeNotification_EffectiveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("EffectiveDate"));
		    }
		});	
		tableExchangeNotification.getColumns().add(tableExchangeNotification_EffectiveDate);
		TableColumn<Map<String, String>, String> tableExchangeNotification_Details = new TableColumn<Map<String, String>, String>("Details");
		tableExchangeNotification_Details.setMinWidth("Details".length()*10);
		tableExchangeNotification_Details.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Details"));
		    }
		});	
		tableExchangeNotification.getColumns().add(tableExchangeNotification_Details);
		
		//table data
		ObservableList<Map<String, String>> dataExchangeNotification = FXCollections.observableArrayList();
		List<ExchangeNotification> rsExchangeNotification = EntityManager.getAllInstancesOf("ExchangeNotification");
		for (ExchangeNotification r : rsExchangeNotification) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getEffectiveDate() != null)
				unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
			else
				unit.put("EffectiveDate", "");
			if (r.getDetails() != null)
				unit.put("Details", String.valueOf(r.getDetails()));
			else
				unit.put("Details", "");

			dataExchangeNotification.add(unit);
		}
		
		tableExchangeNotification.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableExchangeNotification.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("ExchangeNotification", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableExchangeNotification.setItems(dataExchangeNotification);
		allObjectTables.put("ExchangeNotification", tableExchangeNotification);
		
		TableView<Map<String, String>> tableOrderTerm = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableOrderTerm_Id = new TableColumn<Map<String, String>, String>("Id");
		tableOrderTerm_Id.setMinWidth("Id".length()*10);
		tableOrderTerm_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableOrderTerm.getColumns().add(tableOrderTerm_Id);
		TableColumn<Map<String, String>, String> tableOrderTerm_Name = new TableColumn<Map<String, String>, String>("Name");
		tableOrderTerm_Name.setMinWidth("Name".length()*10);
		tableOrderTerm_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableOrderTerm.getColumns().add(tableOrderTerm_Name);
		
		//table data
		ObservableList<Map<String, String>> dataOrderTerm = FXCollections.observableArrayList();
		List<OrderTerm> rsOrderTerm = EntityManager.getAllInstancesOf("OrderTerm");
		for (OrderTerm r : rsOrderTerm) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");

			dataOrderTerm.add(unit);
		}
		
		tableOrderTerm.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableOrderTerm.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("OrderTerm", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableOrderTerm.setItems(dataOrderTerm);
		allObjectTables.put("OrderTerm", tableOrderTerm);
		
		TableView<Map<String, String>> tableClientGroup = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableClientGroup_Id = new TableColumn<Map<String, String>, String>("Id");
		tableClientGroup_Id.setMinWidth("Id".length()*10);
		tableClientGroup_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableClientGroup.getColumns().add(tableClientGroup_Id);
		TableColumn<Map<String, String>, String> tableClientGroup_Name = new TableColumn<Map<String, String>, String>("Name");
		tableClientGroup_Name.setMinWidth("Name".length()*10);
		tableClientGroup_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableClientGroup.getColumns().add(tableClientGroup_Name);
		
		//table data
		ObservableList<Map<String, String>> dataClientGroup = FXCollections.observableArrayList();
		List<ClientGroup> rsClientGroup = EntityManager.getAllInstancesOf("ClientGroup");
		for (ClientGroup r : rsClientGroup) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");

			dataClientGroup.add(unit);
		}
		
		tableClientGroup.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableClientGroup.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("ClientGroup", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableClientGroup.setItems(dataClientGroup);
		allObjectTables.put("ClientGroup", tableClientGroup);
		
		TableView<Map<String, String>> tableDeliveryTerm = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableDeliveryTerm_Id = new TableColumn<Map<String, String>, String>("Id");
		tableDeliveryTerm_Id.setMinWidth("Id".length()*10);
		tableDeliveryTerm_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableDeliveryTerm.getColumns().add(tableDeliveryTerm_Id);
		TableColumn<Map<String, String>, String> tableDeliveryTerm_Name = new TableColumn<Map<String, String>, String>("Name");
		tableDeliveryTerm_Name.setMinWidth("Name".length()*10);
		tableDeliveryTerm_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableDeliveryTerm.getColumns().add(tableDeliveryTerm_Name);
		
		//table data
		ObservableList<Map<String, String>> dataDeliveryTerm = FXCollections.observableArrayList();
		List<DeliveryTerm> rsDeliveryTerm = EntityManager.getAllInstancesOf("DeliveryTerm");
		for (DeliveryTerm r : rsDeliveryTerm) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");

			dataDeliveryTerm.add(unit);
		}
		
		tableDeliveryTerm.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableDeliveryTerm.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("DeliveryTerm", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableDeliveryTerm.setItems(dataDeliveryTerm);
		allObjectTables.put("DeliveryTerm", tableDeliveryTerm);
		
		TableView<Map<String, String>> tableProduct = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableProduct_Id = new TableColumn<Map<String, String>, String>("Id");
		tableProduct_Id.setMinWidth("Id".length()*10);
		tableProduct_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_Id);
		TableColumn<Map<String, String>, String> tableProduct_Name = new TableColumn<Map<String, String>, String>("Name");
		tableProduct_Name.setMinWidth("Name".length()*10);
		tableProduct_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_Name);
		TableColumn<Map<String, String>, String> tableProduct_Price = new TableColumn<Map<String, String>, String>("Price");
		tableProduct_Price.setMinWidth("Price".length()*10);
		tableProduct_Price.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Price"));
		    }
		});	
		tableProduct.getColumns().add(tableProduct_Price);
		
		//table data
		ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
		List<Product> rsProduct = EntityManager.getAllInstancesOf("Product");
		for (Product r : rsProduct) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			unit.put("Price", String.valueOf(r.getPrice()));

			dataProduct.add(unit);
		}
		
		tableProduct.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableProduct.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Product", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableProduct.setItems(dataProduct);
		allObjectTables.put("Product", tableProduct);
		
		TableView<Map<String, String>> tableOrderLineProduct = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableOrderLineProduct_Id = new TableColumn<Map<String, String>, String>("Id");
		tableOrderLineProduct_Id.setMinWidth("Id".length()*10);
		tableOrderLineProduct_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
		    }
		});	
		tableOrderLineProduct.getColumns().add(tableOrderLineProduct_Id);
		TableColumn<Map<String, String>, String> tableOrderLineProduct_Quantity = new TableColumn<Map<String, String>, String>("Quantity");
		tableOrderLineProduct_Quantity.setMinWidth("Quantity".length()*10);
		tableOrderLineProduct_Quantity.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Quantity"));
		    }
		});	
		tableOrderLineProduct.getColumns().add(tableOrderLineProduct_Quantity);
		TableColumn<Map<String, String>, String> tableOrderLineProduct_SubAmount = new TableColumn<Map<String, String>, String>("SubAmount");
		tableOrderLineProduct_SubAmount.setMinWidth("SubAmount".length()*10);
		tableOrderLineProduct_SubAmount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SubAmount"));
		    }
		});	
		tableOrderLineProduct.getColumns().add(tableOrderLineProduct_SubAmount);
		
		//table data
		ObservableList<Map<String, String>> dataOrderLineProduct = FXCollections.observableArrayList();
		List<OrderLineProduct> rsOrderLineProduct = EntityManager.getAllInstancesOf("OrderLineProduct");
		for (OrderLineProduct r : rsOrderLineProduct) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			unit.put("Id", String.valueOf(r.getId()));
			unit.put("Quantity", String.valueOf(r.getQuantity()));
			unit.put("SubAmount", String.valueOf(r.getSubAmount()));

			dataOrderLineProduct.add(unit);
		}
		
		tableOrderLineProduct.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableOrderLineProduct.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("OrderLineProduct", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableOrderLineProduct.setItems(dataOrderLineProduct);
		allObjectTables.put("OrderLineProduct", tableOrderLineProduct);
		

		
	}
	
	/* 
	* update all object tables with sub dataset
	*/ 
	public void updateContractsTable(List<Contracts> rsContracts) {
			ObservableList<Map<String, String>> dataContracts = FXCollections.observableArrayList();
			for (Contracts r : rsContracts) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getBuyer() != null)
					unit.put("Buyer", String.valueOf(r.getBuyer()));
				else
					unit.put("Buyer", "");
				if (r.getPacking() != null)
					unit.put("Packing", String.valueOf(r.getPacking()));
				else
					unit.put("Packing", "");
				if (r.getDateOfShipment() != null)
					unit.put("DateOfShipment", r.getDateOfShipment().format(dateformatter));
				else
					unit.put("DateOfShipment", "");
				if (r.getPortOfShipment() != null)
					unit.put("PortOfShipment", String.valueOf(r.getPortOfShipment()));
				else
					unit.put("PortOfShipment", "");
				if (r.getPortOfDestination() != null)
					unit.put("PortOfDestination", String.valueOf(r.getPortOfDestination()));
				else
					unit.put("PortOfDestination", "");
				if (r.getInsurance() != null)
					unit.put("Insurance", String.valueOf(r.getInsurance()));
				else
					unit.put("Insurance", "");
				if (r.getEffectiveDate() != null)
					unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
				else
					unit.put("EffectiveDate", "");
				dataContracts.add(unit);
			}
			
			allObjectTables.get("Contracts").setItems(dataContracts);
	}
	public void updateClientTable(List<Client> rsClient) {
			ObservableList<Map<String, String>> dataClient = FXCollections.observableArrayList();
			for (Client r : rsClient) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getAddress() != null)
					unit.put("Address", String.valueOf(r.getAddress()));
				else
					unit.put("Address", "");
				if (r.getContact() != null)
					unit.put("Contact", String.valueOf(r.getContact()));
				else
					unit.put("Contact", "");
				if (r.getPhoneNumber() != null)
					unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
				else
					unit.put("PhoneNumber", "");
				dataClient.add(unit);
			}
			
			allObjectTables.get("Client").setItems(dataClient);
	}
	public void updateOrderTable(List<Order> rsOrder) {
			ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
			for (Order r : rsOrder) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				unit.put("IsCompleted", String.valueOf(r.getIsCompleted()));
				if (r.getPaymentInformation() != null)
					unit.put("PaymentInformation", String.valueOf(r.getPaymentInformation()));
				else
					unit.put("PaymentInformation", "");
				unit.put("Amount", String.valueOf(r.getAmount()));
				dataOrder.add(unit);
			}
			
			allObjectTables.get("Order").setItems(dataOrder);
	}
	public void updateInvoiceTable(List<Invoice> rsInvoice) {
			ObservableList<Map<String, String>> dataInvoice = FXCollections.observableArrayList();
			for (Invoice r : rsInvoice) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getTitle() != null)
					unit.put("Title", String.valueOf(r.getTitle()));
				else
					unit.put("Title", "");
				if (r.getEffecitveDate() != null)
					unit.put("EffecitveDate", r.getEffecitveDate().format(dateformatter));
				else
					unit.put("EffecitveDate", "");
				unit.put("Amount", String.valueOf(r.getAmount()));
				dataInvoice.add(unit);
			}
			
			allObjectTables.get("Invoice").setItems(dataInvoice);
	}
	public void updateBillOfLadingTable(List<BillOfLading> rsBillOfLading) {
			ObservableList<Map<String, String>> dataBillOfLading = FXCollections.observableArrayList();
			for (BillOfLading r : rsBillOfLading) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getConsignee() != null)
					unit.put("Consignee", String.valueOf(r.getConsignee()));
				else
					unit.put("Consignee", "");
				if (r.getCommodityList() != null)
					unit.put("CommodityList", String.valueOf(r.getCommodityList()));
				else
					unit.put("CommodityList", "");
				if (r.getContractObject() != null)
					unit.put("ContractObject", String.valueOf(r.getContractObject()));
				else
					unit.put("ContractObject", "");
				unit.put("Quantity", String.valueOf(r.getQuantity()));
				unit.put("TotalPrice", String.valueOf(r.getTotalPrice()));
				if (r.getDeadlineForPerformance() != null)
					unit.put("DeadlineForPerformance", String.valueOf(r.getDeadlineForPerformance()));
				else
					unit.put("DeadlineForPerformance", "");
				if (r.getLocationForPerformance() != null)
					unit.put("LocationForPerformance", String.valueOf(r.getLocationForPerformance()));
				else
					unit.put("LocationForPerformance", "");
				if (r.getMethodForPerformance() != null)
					unit.put("MethodForPerformance", String.valueOf(r.getMethodForPerformance()));
				else
					unit.put("MethodForPerformance", "");
				dataBillOfLading.add(unit);
			}
			
			allObjectTables.get("BillOfLading").setItems(dataBillOfLading);
	}
	public void updateDeliveryNotificationTable(List<DeliveryNotification> rsDeliveryNotification) {
			ObservableList<Map<String, String>> dataDeliveryNotification = FXCollections.observableArrayList();
			for (DeliveryNotification r : rsDeliveryNotification) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getEffectiveDate() != null)
					unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
				else
					unit.put("EffectiveDate", "");
				if (r.getDetails() != null)
					unit.put("Details", String.valueOf(r.getDetails()));
				else
					unit.put("Details", "");
				dataDeliveryNotification.add(unit);
			}
			
			allObjectTables.get("DeliveryNotification").setItems(dataDeliveryNotification);
	}
	public void updateExchangeNotificationTable(List<ExchangeNotification> rsExchangeNotification) {
			ObservableList<Map<String, String>> dataExchangeNotification = FXCollections.observableArrayList();
			for (ExchangeNotification r : rsExchangeNotification) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getEffectiveDate() != null)
					unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
				else
					unit.put("EffectiveDate", "");
				if (r.getDetails() != null)
					unit.put("Details", String.valueOf(r.getDetails()));
				else
					unit.put("Details", "");
				dataExchangeNotification.add(unit);
			}
			
			allObjectTables.get("ExchangeNotification").setItems(dataExchangeNotification);
	}
	public void updateOrderTermTable(List<OrderTerm> rsOrderTerm) {
			ObservableList<Map<String, String>> dataOrderTerm = FXCollections.observableArrayList();
			for (OrderTerm r : rsOrderTerm) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataOrderTerm.add(unit);
			}
			
			allObjectTables.get("OrderTerm").setItems(dataOrderTerm);
	}
	public void updateClientGroupTable(List<ClientGroup> rsClientGroup) {
			ObservableList<Map<String, String>> dataClientGroup = FXCollections.observableArrayList();
			for (ClientGroup r : rsClientGroup) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataClientGroup.add(unit);
			}
			
			allObjectTables.get("ClientGroup").setItems(dataClientGroup);
	}
	public void updateDeliveryTermTable(List<DeliveryTerm> rsDeliveryTerm) {
			ObservableList<Map<String, String>> dataDeliveryTerm = FXCollections.observableArrayList();
			for (DeliveryTerm r : rsDeliveryTerm) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataDeliveryTerm.add(unit);
			}
			
			allObjectTables.get("DeliveryTerm").setItems(dataDeliveryTerm);
	}
	public void updateProductTable(List<Product> rsProduct) {
			ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
			for (Product r : rsProduct) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Price", String.valueOf(r.getPrice()));
				dataProduct.add(unit);
			}
			
			allObjectTables.get("Product").setItems(dataProduct);
	}
	public void updateOrderLineProductTable(List<OrderLineProduct> rsOrderLineProduct) {
			ObservableList<Map<String, String>> dataOrderLineProduct = FXCollections.observableArrayList();
			for (OrderLineProduct r : rsOrderLineProduct) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				unit.put("Id", String.valueOf(r.getId()));
				unit.put("Quantity", String.valueOf(r.getQuantity()));
				unit.put("SubAmount", String.valueOf(r.getSubAmount()));
				dataOrderLineProduct.add(unit);
			}
			
			allObjectTables.get("OrderLineProduct").setItems(dataOrderLineProduct);
	}
	
	/* 
	* update all object tables
	*/ 
	public void updateContractsTable() {
			ObservableList<Map<String, String>> dataContracts = FXCollections.observableArrayList();
			List<Contracts> rsContracts = EntityManager.getAllInstancesOf("Contracts");
			for (Contracts r : rsContracts) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getBuyer() != null)
					unit.put("Buyer", String.valueOf(r.getBuyer()));
				else
					unit.put("Buyer", "");
				if (r.getPacking() != null)
					unit.put("Packing", String.valueOf(r.getPacking()));
				else
					unit.put("Packing", "");
				if (r.getDateOfShipment() != null)
					unit.put("DateOfShipment", r.getDateOfShipment().format(dateformatter));
				else
					unit.put("DateOfShipment", "");
				if (r.getPortOfShipment() != null)
					unit.put("PortOfShipment", String.valueOf(r.getPortOfShipment()));
				else
					unit.put("PortOfShipment", "");
				if (r.getPortOfDestination() != null)
					unit.put("PortOfDestination", String.valueOf(r.getPortOfDestination()));
				else
					unit.put("PortOfDestination", "");
				if (r.getInsurance() != null)
					unit.put("Insurance", String.valueOf(r.getInsurance()));
				else
					unit.put("Insurance", "");
				if (r.getEffectiveDate() != null)
					unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
				else
					unit.put("EffectiveDate", "");
				dataContracts.add(unit);
			}
			
			allObjectTables.get("Contracts").setItems(dataContracts);
	}
	public void updateClientTable() {
			ObservableList<Map<String, String>> dataClient = FXCollections.observableArrayList();
			List<Client> rsClient = EntityManager.getAllInstancesOf("Client");
			for (Client r : rsClient) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getAddress() != null)
					unit.put("Address", String.valueOf(r.getAddress()));
				else
					unit.put("Address", "");
				if (r.getContact() != null)
					unit.put("Contact", String.valueOf(r.getContact()));
				else
					unit.put("Contact", "");
				if (r.getPhoneNumber() != null)
					unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
				else
					unit.put("PhoneNumber", "");
				dataClient.add(unit);
			}
			
			allObjectTables.get("Client").setItems(dataClient);
	}
	public void updateOrderTable() {
			ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
			List<Order> rsOrder = EntityManager.getAllInstancesOf("Order");
			for (Order r : rsOrder) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				unit.put("IsCompleted", String.valueOf(r.getIsCompleted()));
				if (r.getPaymentInformation() != null)
					unit.put("PaymentInformation", String.valueOf(r.getPaymentInformation()));
				else
					unit.put("PaymentInformation", "");
				unit.put("Amount", String.valueOf(r.getAmount()));
				dataOrder.add(unit);
			}
			
			allObjectTables.get("Order").setItems(dataOrder);
	}
	public void updateInvoiceTable() {
			ObservableList<Map<String, String>> dataInvoice = FXCollections.observableArrayList();
			List<Invoice> rsInvoice = EntityManager.getAllInstancesOf("Invoice");
			for (Invoice r : rsInvoice) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getTitle() != null)
					unit.put("Title", String.valueOf(r.getTitle()));
				else
					unit.put("Title", "");
				if (r.getEffecitveDate() != null)
					unit.put("EffecitveDate", r.getEffecitveDate().format(dateformatter));
				else
					unit.put("EffecitveDate", "");
				unit.put("Amount", String.valueOf(r.getAmount()));
				dataInvoice.add(unit);
			}
			
			allObjectTables.get("Invoice").setItems(dataInvoice);
	}
	public void updateBillOfLadingTable() {
			ObservableList<Map<String, String>> dataBillOfLading = FXCollections.observableArrayList();
			List<BillOfLading> rsBillOfLading = EntityManager.getAllInstancesOf("BillOfLading");
			for (BillOfLading r : rsBillOfLading) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getConsignee() != null)
					unit.put("Consignee", String.valueOf(r.getConsignee()));
				else
					unit.put("Consignee", "");
				if (r.getCommodityList() != null)
					unit.put("CommodityList", String.valueOf(r.getCommodityList()));
				else
					unit.put("CommodityList", "");
				if (r.getContractObject() != null)
					unit.put("ContractObject", String.valueOf(r.getContractObject()));
				else
					unit.put("ContractObject", "");
				unit.put("Quantity", String.valueOf(r.getQuantity()));
				unit.put("TotalPrice", String.valueOf(r.getTotalPrice()));
				if (r.getDeadlineForPerformance() != null)
					unit.put("DeadlineForPerformance", String.valueOf(r.getDeadlineForPerformance()));
				else
					unit.put("DeadlineForPerformance", "");
				if (r.getLocationForPerformance() != null)
					unit.put("LocationForPerformance", String.valueOf(r.getLocationForPerformance()));
				else
					unit.put("LocationForPerformance", "");
				if (r.getMethodForPerformance() != null)
					unit.put("MethodForPerformance", String.valueOf(r.getMethodForPerformance()));
				else
					unit.put("MethodForPerformance", "");
				dataBillOfLading.add(unit);
			}
			
			allObjectTables.get("BillOfLading").setItems(dataBillOfLading);
	}
	public void updateDeliveryNotificationTable() {
			ObservableList<Map<String, String>> dataDeliveryNotification = FXCollections.observableArrayList();
			List<DeliveryNotification> rsDeliveryNotification = EntityManager.getAllInstancesOf("DeliveryNotification");
			for (DeliveryNotification r : rsDeliveryNotification) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getEffectiveDate() != null)
					unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
				else
					unit.put("EffectiveDate", "");
				if (r.getDetails() != null)
					unit.put("Details", String.valueOf(r.getDetails()));
				else
					unit.put("Details", "");
				dataDeliveryNotification.add(unit);
			}
			
			allObjectTables.get("DeliveryNotification").setItems(dataDeliveryNotification);
	}
	public void updateExchangeNotificationTable() {
			ObservableList<Map<String, String>> dataExchangeNotification = FXCollections.observableArrayList();
			List<ExchangeNotification> rsExchangeNotification = EntityManager.getAllInstancesOf("ExchangeNotification");
			for (ExchangeNotification r : rsExchangeNotification) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getEffectiveDate() != null)
					unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
				else
					unit.put("EffectiveDate", "");
				if (r.getDetails() != null)
					unit.put("Details", String.valueOf(r.getDetails()));
				else
					unit.put("Details", "");
				dataExchangeNotification.add(unit);
			}
			
			allObjectTables.get("ExchangeNotification").setItems(dataExchangeNotification);
	}
	public void updateOrderTermTable() {
			ObservableList<Map<String, String>> dataOrderTerm = FXCollections.observableArrayList();
			List<OrderTerm> rsOrderTerm = EntityManager.getAllInstancesOf("OrderTerm");
			for (OrderTerm r : rsOrderTerm) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataOrderTerm.add(unit);
			}
			
			allObjectTables.get("OrderTerm").setItems(dataOrderTerm);
	}
	public void updateClientGroupTable() {
			ObservableList<Map<String, String>> dataClientGroup = FXCollections.observableArrayList();
			List<ClientGroup> rsClientGroup = EntityManager.getAllInstancesOf("ClientGroup");
			for (ClientGroup r : rsClientGroup) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataClientGroup.add(unit);
			}
			
			allObjectTables.get("ClientGroup").setItems(dataClientGroup);
	}
	public void updateDeliveryTermTable() {
			ObservableList<Map<String, String>> dataDeliveryTerm = FXCollections.observableArrayList();
			List<DeliveryTerm> rsDeliveryTerm = EntityManager.getAllInstancesOf("DeliveryTerm");
			for (DeliveryTerm r : rsDeliveryTerm) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataDeliveryTerm.add(unit);
			}
			
			allObjectTables.get("DeliveryTerm").setItems(dataDeliveryTerm);
	}
	public void updateProductTable() {
			ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
			List<Product> rsProduct = EntityManager.getAllInstancesOf("Product");
			for (Product r : rsProduct) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Price", String.valueOf(r.getPrice()));
				dataProduct.add(unit);
			}
			
			allObjectTables.get("Product").setItems(dataProduct);
	}
	public void updateOrderLineProductTable() {
			ObservableList<Map<String, String>> dataOrderLineProduct = FXCollections.observableArrayList();
			List<OrderLineProduct> rsOrderLineProduct = EntityManager.getAllInstancesOf("OrderLineProduct");
			for (OrderLineProduct r : rsOrderLineProduct) {
				Map<String, String> unit = new HashMap<String, String>();


				unit.put("Id", String.valueOf(r.getId()));
				unit.put("Quantity", String.valueOf(r.getQuantity()));
				unit.put("SubAmount", String.valueOf(r.getSubAmount()));
				dataOrderLineProduct.add(unit);
			}
			
			allObjectTables.get("OrderLineProduct").setItems(dataOrderLineProduct);
	}
	
	public void classStatisicBingding() {
	
		 classInfodata = FXCollections.observableArrayList();
	 	 contracts = new ClassInfo("Contracts", EntityManager.getAllInstancesOf("Contracts").size());
	 	 classInfodata.add(contracts);
	 	 client = new ClassInfo("Client", EntityManager.getAllInstancesOf("Client").size());
	 	 classInfodata.add(client);
	 	 order = new ClassInfo("Order", EntityManager.getAllInstancesOf("Order").size());
	 	 classInfodata.add(order);
	 	 invoice = new ClassInfo("Invoice", EntityManager.getAllInstancesOf("Invoice").size());
	 	 classInfodata.add(invoice);
	 	 billoflading = new ClassInfo("BillOfLading", EntityManager.getAllInstancesOf("BillOfLading").size());
	 	 classInfodata.add(billoflading);
	 	 deliverynotification = new ClassInfo("DeliveryNotification", EntityManager.getAllInstancesOf("DeliveryNotification").size());
	 	 classInfodata.add(deliverynotification);
	 	 exchangenotification = new ClassInfo("ExchangeNotification", EntityManager.getAllInstancesOf("ExchangeNotification").size());
	 	 classInfodata.add(exchangenotification);
	 	 orderterm = new ClassInfo("OrderTerm", EntityManager.getAllInstancesOf("OrderTerm").size());
	 	 classInfodata.add(orderterm);
	 	 clientgroup = new ClassInfo("ClientGroup", EntityManager.getAllInstancesOf("ClientGroup").size());
	 	 classInfodata.add(clientgroup);
	 	 deliveryterm = new ClassInfo("DeliveryTerm", EntityManager.getAllInstancesOf("DeliveryTerm").size());
	 	 classInfodata.add(deliveryterm);
	 	 product = new ClassInfo("Product", EntityManager.getAllInstancesOf("Product").size());
	 	 classInfodata.add(product);
	 	 orderlineproduct = new ClassInfo("OrderLineProduct", EntityManager.getAllInstancesOf("OrderLineProduct").size());
	 	 classInfodata.add(orderlineproduct);
	 	 
		 class_statisic.setItems(classInfodata);
		 
		 //Class Statisic Binding
		 class_statisic.getSelectionModel().selectedItemProperty().addListener(
				 (observable, oldValue, newValue) ->  { 
				 										 //no selected object in table
				 										 objectindex = -1;
				 										 
				 										 //get lastest data, reflect updateTableData method
				 										 try {
												 			 Method updateob = this.getClass().getMethod("update" + newValue.getName() + "Table", null);
												 			 updateob.invoke(this);			 
												 		 } catch (Exception e) {
												 		 	 e.printStackTrace();
												 		 }		 										 
				 	
				 										 //show object table
				 			 				 			 TableView obs = allObjectTables.get(newValue.getName());
				 			 				 			 if (obs != null) {
				 			 				 				object_statics.setContent(obs);
				 			 				 				object_statics.setText("All Objects " + newValue.getName() + ":");
				 			 				 			 }
				 			 				 			 
				 			 				 			 //update association information
							 			 				 updateAssociation(newValue.getName());
				 			 				 			 
				 			 				 			 //show association information
				 			 				 			 ObservableList<AssociationInfo> asso = allassociationData.get(newValue.getName());
				 			 				 			 if (asso != null) {
				 			 				 			 	association_statisic.setItems(asso);
				 			 				 			 }
				 			 				 		  });
	}
	
	public void classStatisticUpdate() {
	 	 contracts.setNumber(EntityManager.getAllInstancesOf("Contracts").size());
	 	 client.setNumber(EntityManager.getAllInstancesOf("Client").size());
	 	 order.setNumber(EntityManager.getAllInstancesOf("Order").size());
	 	 invoice.setNumber(EntityManager.getAllInstancesOf("Invoice").size());
	 	 billoflading.setNumber(EntityManager.getAllInstancesOf("BillOfLading").size());
	 	 deliverynotification.setNumber(EntityManager.getAllInstancesOf("DeliveryNotification").size());
	 	 exchangenotification.setNumber(EntityManager.getAllInstancesOf("ExchangeNotification").size());
	 	 orderterm.setNumber(EntityManager.getAllInstancesOf("OrderTerm").size());
	 	 clientgroup.setNumber(EntityManager.getAllInstancesOf("ClientGroup").size());
	 	 deliveryterm.setNumber(EntityManager.getAllInstancesOf("DeliveryTerm").size());
	 	 product.setNumber(EntityManager.getAllInstancesOf("Product").size());
	 	 orderlineproduct.setNumber(EntityManager.getAllInstancesOf("OrderLineProduct").size());
		
	}
	
	/**
	 * association binding
	 */
	public void associationStatisicBingding() {
		
		allassociationData = new HashMap<String, ObservableList<AssociationInfo>>();
		
		ObservableList<AssociationInfo> Contracts_association_data = FXCollections.observableArrayList();
		AssociationInfo Contracts_associatition_ContractstoClient = new AssociationInfo("Contracts", "Client", "ContractstoClient", false);
		Contracts_association_data.add(Contracts_associatition_ContractstoClient);
		AssociationInfo Contracts_associatition_BelongedOrder = new AssociationInfo("Contracts", "Order", "BelongedOrder", false);
		Contracts_association_data.add(Contracts_associatition_BelongedOrder);
		AssociationInfo Contracts_associatition_InvoiceOfContract = new AssociationInfo("Contracts", "Invoice", "InvoiceOfContract", false);
		Contracts_association_data.add(Contracts_associatition_InvoiceOfContract);
		
		allassociationData.put("Contracts", Contracts_association_data);
		
		ObservableList<AssociationInfo> Client_association_data = FXCollections.observableArrayList();
		AssociationInfo Client_associatition_CG = new AssociationInfo("Client", "ClientGroup", "CG", false);
		Client_association_data.add(Client_associatition_CG);
		AssociationInfo Client_associatition_ContainedOrders = new AssociationInfo("Client", "Order", "ContainedOrders", true);
		Client_association_data.add(Client_associatition_ContainedOrders);
		
		allassociationData.put("Client", Client_association_data);
		
		ObservableList<AssociationInfo> Order_association_data = FXCollections.observableArrayList();
		AssociationInfo Order_associatition_BillOfLadingOfOrder = new AssociationInfo("Order", "BillOfLading", "BillOfLadingOfOrder", false);
		Order_association_data.add(Order_associatition_BillOfLadingOfOrder);
		AssociationInfo Order_associatition_DN = new AssociationInfo("Order", "DeliveryNotification", "DN", false);
		Order_association_data.add(Order_associatition_DN);
		AssociationInfo Order_associatition_EN = new AssociationInfo("Order", "ExchangeNotification", "EN", true);
		Order_association_data.add(Order_associatition_EN);
		AssociationInfo Order_associatition_OT = new AssociationInfo("Order", "OrderTerm", "OT", false);
		Order_association_data.add(Order_associatition_OT);
		AssociationInfo Order_associatition_Buyer = new AssociationInfo("Order", "Client", "Buyer", false);
		Order_association_data.add(Order_associatition_Buyer);
		AssociationInfo Order_associatition_ContainedOrderLine = new AssociationInfo("Order", "OrderLineProduct", "ContainedOrderLine", true);
		Order_association_data.add(Order_associatition_ContainedOrderLine);
		AssociationInfo Order_associatition_ContractOfOrder = new AssociationInfo("Order", "Contracts", "ContractOfOrder", false);
		Order_association_data.add(Order_associatition_ContractOfOrder);
		
		allassociationData.put("Order", Order_association_data);
		
		ObservableList<AssociationInfo> Invoice_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Invoice", Invoice_association_data);
		
		ObservableList<AssociationInfo> BillOfLading_association_data = FXCollections.observableArrayList();
		AssociationInfo BillOfLading_associatition_DT = new AssociationInfo("BillOfLading", "DeliveryTerm", "DT", false);
		BillOfLading_association_data.add(BillOfLading_associatition_DT);
		AssociationInfo BillOfLading_associatition_BelongedOrder = new AssociationInfo("BillOfLading", "Order", "BelongedOrder", false);
		BillOfLading_association_data.add(BillOfLading_associatition_BelongedOrder);
		
		allassociationData.put("BillOfLading", BillOfLading_association_data);
		
		ObservableList<AssociationInfo> DeliveryNotification_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("DeliveryNotification", DeliveryNotification_association_data);
		
		ObservableList<AssociationInfo> ExchangeNotification_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("ExchangeNotification", ExchangeNotification_association_data);
		
		ObservableList<AssociationInfo> OrderTerm_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("OrderTerm", OrderTerm_association_data);
		
		ObservableList<AssociationInfo> ClientGroup_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("ClientGroup", ClientGroup_association_data);
		
		ObservableList<AssociationInfo> DeliveryTerm_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("DeliveryTerm", DeliveryTerm_association_data);
		
		ObservableList<AssociationInfo> Product_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Product", Product_association_data);
		
		ObservableList<AssociationInfo> OrderLineProduct_association_data = FXCollections.observableArrayList();
		AssociationInfo OrderLineProduct_associatition_BelongedProduct = new AssociationInfo("OrderLineProduct", "Product", "BelongedProduct", false);
		OrderLineProduct_association_data.add(OrderLineProduct_associatition_BelongedProduct);
		AssociationInfo OrderLineProduct_associatition_BelongedOrder = new AssociationInfo("OrderLineProduct", "Order", "BelongedOrder", false);
		OrderLineProduct_association_data.add(OrderLineProduct_associatition_BelongedOrder);
		
		allassociationData.put("OrderLineProduct", OrderLineProduct_association_data);
		
		
		association_statisic.getSelectionModel().selectedItemProperty().addListener(
			    (observable, oldValue, newValue) ->  { 
	
							 		if (newValue != null) {
							 			 try {
							 			 	 if (newValue.getNumber() != 0) {
								 				 //choose object or not
								 				 if (objectindex != -1) {
									 				 Class[] cArg = new Class[1];
									 				 cArg[0] = List.class;
									 				 //reflect updateTableData method
										 			 Method updateob = this.getClass().getMethod("update" + newValue.getTargetClass() + "Table", cArg);
										 			 //find choosen object
										 			 Object selectedob = EntityManager.getAllInstancesOf(newValue.getSourceClass()).get(objectindex);
										 			 //reflect find association method
										 			 Method getAssociatedObject = selectedob.getClass().getMethod("get" + newValue.getAssociationName());
										 			 List r = new LinkedList();
										 			 //one or mulity?
										 			 if (newValue.getIsMultiple() == true) {
											 			 
											 			r = (List) getAssociatedObject.invoke(selectedob);
										 			 }
										 			 else {
										 				r.add(getAssociatedObject.invoke(selectedob));
										 			 }
										 			 //invoke update method
										 			 updateob.invoke(this, r);
										 			  
										 			 
								 				 }
												 //bind updated data to GUI
					 				 			 TableView obs = allObjectTables.get(newValue.getTargetClass());
					 				 			 if (obs != null) {
					 				 				object_statics.setContent(obs);
					 				 				object_statics.setText("Targets Objects " + newValue.getTargetClass() + ":");
					 				 			 }
					 				 		 }
							 			 }
							 			 catch (Exception e) {
							 				 e.printStackTrace();
							 			 }
							 		}
					 		  });
		
	}	
	
	

    //prepare data for contract
	public void prepareData() {
		
		//definition map
		definitions_map = new HashMap<String, String>();
		definitions_map.put("makeNewOrder", "buyer:Client = Client.allInstance()->any(bu:Client | bu.Id = buyId)\r\r\n");
		definitions_map.put("addProduct", "product:Product = Product.allInstance()->any(pr:Product | pr.Id = id)\r\r\n");
		definitions_map.put("createContracts", "contracts:Contracts = Contracts.allInstance()->any(con:Contracts | con.Id = id)\r\r\n");
		definitions_map.put("queryContracts", "contracts:Contracts = Contracts.allInstance()->any(con:Contracts | con.Id = id)\r\r\n");
		definitions_map.put("modifyContracts", "contracts:Contracts = Contracts.allInstance()->any(con:Contracts | con.Id = id)\r\r\n");
		definitions_map.put("deleteContracts", "contracts:Contracts = Contracts.allInstance()->any(con:Contracts | con.Id = id)\r\r\n");
		definitions_map.put("createInvoice", "invoice:Invoice = Invoice.allInstance()->any(invo:Invoice | invo.Id = id)\r\r\n");
		definitions_map.put("queryInvoice", "invoice:Invoice = Invoice.allInstance()->any(invo:Invoice | invo.Id = id)\r\r\n");
		definitions_map.put("modifyInvoice", "invoice:Invoice = Invoice.allInstance()->any(invo:Invoice | invo.Id = id)\r\r\n");
		definitions_map.put("deleteInvoice", "invoice:Invoice = Invoice.allInstance()->any(invo:Invoice | invo.Id = id)\r\r\n");
		definitions_map.put("createBillOfLading", "billoflading:BillOfLading = BillOfLading.allInstance()->any(bil:BillOfLading | bil.Id = id)\r\r\n");
		definitions_map.put("queryBillOfLading", "billoflading:BillOfLading = BillOfLading.allInstance()->any(bil:BillOfLading | bil.Id = id)\r\r\n");
		definitions_map.put("modifyBillOfLading", "billoflading:BillOfLading = BillOfLading.allInstance()->any(bil:BillOfLading | bil.Id = id)\r\r\n");
		definitions_map.put("deleteBillOfLading", "billoflading:BillOfLading = BillOfLading.allInstance()->any(bil:BillOfLading | bil.Id = id)\r\r\n");
		definitions_map.put("createClient", "client:Client = Client.allInstance()->any(cli:Client | cli.Id = id)\r\r\n");
		definitions_map.put("queryClient", "client:Client = Client.allInstance()->any(cli:Client | cli.Id = id)\r\r\n");
		definitions_map.put("modifyClient", "client:Client = Client.allInstance()->any(cli:Client | cli.Id = id)\r\r\n");
		definitions_map.put("deleteClient", "client:Client = Client.allInstance()->any(cli:Client | cli.Id = id)\r\r\n");
		definitions_map.put("createOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.Id = id)\r\r\n");
		definitions_map.put("queryOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.Id = id)\r\r\n");
		definitions_map.put("modifyOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.Id = id)\r\r\n");
		definitions_map.put("deleteOrder", "order:Order = Order.allInstance()->any(ord:Order | ord.Id = id)\r\r\n");
		definitions_map.put("createProduct", "product:Product = Product.allInstance()->any(pro:Product | pro.Id = id)\r\r\n");
		definitions_map.put("queryProduct", "product:Product = Product.allInstance()->any(pro:Product | pro.Id = id)\r\r\n");
		definitions_map.put("modifyProduct", "product:Product = Product.allInstance()->any(pro:Product | pro.Id = id)\r\r\n");
		definitions_map.put("deleteProduct", "product:Product = Product.allInstance()->any(pro:Product | pro.Id = id)\r\r\n");
		
		//precondition map
		preconditions_map = new HashMap<String, String>();
		preconditions_map.put("makeNewOrder", "buyer.oclIsUndefined() = false and\nClient.allInstance()->includes(buyer) and\n(currentOrder.oclIsUndefined() = true or\n(currentOrder.oclIsUndefined() = false and\ncurrentOrder.IsCompleted = true\n)\n)\n");
		preconditions_map.put("addProduct", "currentOrder.oclIsUndefined() = false and\ncurrentOrder.IsCompleted = false and\nproduct.oclIsUndefined() = false\n");
		preconditions_map.put("generateContract", "currentOrder.oclIsUndefined() = false and\ncurrentOrder.IsComplete = false\n");
		preconditions_map.put("generateOrder", "true");
		preconditions_map.put("salesPlanManagement", "true");
		preconditions_map.put("deliveryNotification", "true");
		preconditions_map.put("exchangeNotification", "true");
		preconditions_map.put("itemsPricesMaintenance", "true");
		preconditions_map.put("postingOfAccount", "true");
		preconditions_map.put("invoiceManagement", "true");
		preconditions_map.put("contractTerminationAndSettlement", "true");
		preconditions_map.put("orderTerminationAndSettlement", "true");
		preconditions_map.put("salesCommissionManagement", "true");
		preconditions_map.put("authorization", "authorizationProcessing()");
		preconditions_map.put("authorizationProcessing", "true");
		preconditions_map.put("createContracts", "contracts.oclIsUndefined() = true");
		preconditions_map.put("queryContracts", "contracts.oclIsUndefined() = false");
		preconditions_map.put("modifyContracts", "contracts.oclIsUndefined() = false");
		preconditions_map.put("deleteContracts", "contracts.oclIsUndefined() = false and\nContracts.allInstance()->includes(contracts)\n");
		preconditions_map.put("createInvoice", "invoice.oclIsUndefined() = true");
		preconditions_map.put("queryInvoice", "invoice.oclIsUndefined() = false");
		preconditions_map.put("modifyInvoice", "invoice.oclIsUndefined() = false");
		preconditions_map.put("deleteInvoice", "invoice.oclIsUndefined() = false and\nInvoice.allInstance()->includes(invoice)\n");
		preconditions_map.put("createBillOfLading", "billoflading.oclIsUndefined() = true");
		preconditions_map.put("queryBillOfLading", "billoflading.oclIsUndefined() = false");
		preconditions_map.put("modifyBillOfLading", "billoflading.oclIsUndefined() = false");
		preconditions_map.put("deleteBillOfLading", "billoflading.oclIsUndefined() = false and\nBillOfLading.allInstance()->includes(billoflading)\n");
		preconditions_map.put("createClient", "client.oclIsUndefined() = true");
		preconditions_map.put("queryClient", "client.oclIsUndefined() = false");
		preconditions_map.put("modifyClient", "client.oclIsUndefined() = false");
		preconditions_map.put("deleteClient", "client.oclIsUndefined() = false and\nClient.allInstance()->includes(client)\n");
		preconditions_map.put("createOrder", "order.oclIsUndefined() = true");
		preconditions_map.put("queryOrder", "order.oclIsUndefined() = false");
		preconditions_map.put("modifyOrder", "order.oclIsUndefined() = false");
		preconditions_map.put("deleteOrder", "order.oclIsUndefined() = false and\nOrder.allInstance()->includes(order)\n");
		preconditions_map.put("createProduct", "product.oclIsUndefined() = true");
		preconditions_map.put("queryProduct", "product.oclIsUndefined() = false");
		preconditions_map.put("modifyProduct", "product.oclIsUndefined() = false");
		preconditions_map.put("deleteProduct", "product.oclIsUndefined() = false and\nProduct.allInstance()->includes(product)\n");
		preconditions_map.put("manageItemsPrices", "true");
		
		//postcondition map
		postconditions_map = new HashMap<String, String>();
		postconditions_map.put("makeNewOrder", "let o:Order ino.oclIsNew() and\no.Buyer = buyer and\nbuyer.ContainedOrders->includes(o) and\no.IsCompleted = false and\nOrder.allInstance()->includes(o) and\nself.currentOrder = o and\nresult = true\n");
		postconditions_map.put("addProduct", "let olp:OrderLineProduct inolp.oclIsNew() and\ncurrentOrderLine = olp and\nolp.BelongedOrder = currentOrder and\ncurrentOrder.ContainedOrderLine->includes(olp) and\nolp.Quantity = quantity and\nolp.BelongedProduct = product and\nolp.SubAmount = product.Price * quantity and\nOrderLineProduct.allInstance()->includes(olp) and\nresult = true\n");
		postconditions_map.put("generateContract", "let con:Contracts incon.oclIsNew() and\ncon.Id = currentOrder.Id and\ncon.BelongedOrder = currentOrder and\ncon.Packing = packing and\ncon.DateOfShipment = dateOfShipment and\ncon.PortOfShipment = portOfShipment and\ncon.PortOfDestination = portOfDestination and\ncon.Insurance = insurance and\ncon.EffectiveDate = effectiveDate and\nContracts.allInstance()->includes(con) and\nresult = true\n");
		postconditions_map.put("generateOrder", "true");
		postconditions_map.put("salesPlanManagement", "true");
		postconditions_map.put("deliveryNotification", "true");
		postconditions_map.put("exchangeNotification", "true");
		postconditions_map.put("itemsPricesMaintenance", "true");
		postconditions_map.put("postingOfAccount", "true");
		postconditions_map.put("invoiceManagement", "true");
		postconditions_map.put("contractTerminationAndSettlement", "true");
		postconditions_map.put("orderTerminationAndSettlement", "true");
		postconditions_map.put("salesCommissionManagement", "true");
		postconditions_map.put("authorization", "true");
		postconditions_map.put("authorizationProcessing", "true");
		postconditions_map.put("createContracts", "let con:Contracts incon.oclIsNew() and\ncon.Id = id and\ncon.Buyer = buyer and\ncon.Packing = packing and\ncon.DateOfShipment = dateofshipment and\ncon.PortOfShipment = portofshipment and\ncon.PortOfDestination = portofdestination and\ncon.Insurance = insurance and\ncon.EffectiveDate = effectivedate and\nContracts.allInstance()->includes(con) and\nresult = true\n");
		postconditions_map.put("queryContracts", "result = contracts");
		postconditions_map.put("modifyContracts", "contracts.Id = id and\ncontracts.Buyer = buyer and\ncontracts.Packing = packing and\ncontracts.DateOfShipment = dateofshipment and\ncontracts.PortOfShipment = portofshipment and\ncontracts.PortOfDestination = portofdestination and\ncontracts.Insurance = insurance and\ncontracts.EffectiveDate = effectivedate and\nresult = true\n");
		postconditions_map.put("deleteContracts", "Contracts.allInstance()->excludes(contracts) and\nresult = true\n");
		postconditions_map.put("createInvoice", "let invo:Invoice ininvo.oclIsNew() and\ninvo.Id = id and\ninvo.Title = title and\ninvo.EffecitveDate = effecitvedate and\ninvo.Amount = amount and\nInvoice.allInstance()->includes(invo) and\nresult = true\n");
		postconditions_map.put("queryInvoice", "result = invoice");
		postconditions_map.put("modifyInvoice", "invoice.Id = id and\ninvoice.Title = title and\ninvoice.EffecitveDate = effecitvedate and\ninvoice.Amount = amount and\nresult = true\n");
		postconditions_map.put("deleteInvoice", "Invoice.allInstance()->excludes(invoice) and\nresult = true\n");
		postconditions_map.put("createBillOfLading", "let bil:BillOfLading inbil.oclIsNew() and\nbil.Id = id and\nbil.Consignee = consignee and\nbil.CommodityList = commoditylist and\nbil.ContractObject = contractobject and\nbil.Quantity = quantity and\nbil.TotalPrice = totalprice and\nbil.DeadlineForPerformance = deadlineforperformance and\nbil.LocationForPerformance = locationforperformance and\nbil.MethodForPerformance = methodforperformance and\nBillOfLading.allInstance()->includes(bil) and\nresult = true\n");
		postconditions_map.put("queryBillOfLading", "result = billoflading");
		postconditions_map.put("modifyBillOfLading", "billoflading.Id = id and\nbilloflading.Consignee = consignee and\nbilloflading.CommodityList = commoditylist and\nbilloflading.ContractObject = contractobject and\nbilloflading.Quantity = quantity and\nbilloflading.TotalPrice = totalprice and\nbilloflading.DeadlineForPerformance = deadlineforperformance and\nbilloflading.LocationForPerformance = locationforperformance and\nbilloflading.MethodForPerformance = methodforperformance and\nresult = true\n");
		postconditions_map.put("deleteBillOfLading", "BillOfLading.allInstance()->excludes(billoflading) and\nresult = true\n");
		postconditions_map.put("createClient", "let cli:Client incli.oclIsNew() and\ncli.Id = id and\ncli.Name = name and\ncli.Address = address and\ncli.Contact = contact and\ncli.PhoneNumber = phonenumber and\nClient.allInstance()->includes(cli) and\nresult = true\n");
		postconditions_map.put("queryClient", "result = client");
		postconditions_map.put("modifyClient", "client.Id = id and\nclient.Name = name and\nclient.Address = address and\nclient.Contact = contact and\nclient.PhoneNumber = phonenumber and\nresult = true\n");
		postconditions_map.put("deleteClient", "Client.allInstance()->excludes(client) and\nresult = true\n");
		postconditions_map.put("createOrder", "let ord:Order inord.oclIsNew() and\nord.Id = id and\nord.IsCompleted = iscompleted and\nord.PaymentInformation = paymentinformation and\nord.Amount = amount and\nOrder.allInstance()->includes(ord) and\nresult = true\n");
		postconditions_map.put("queryOrder", "result = order");
		postconditions_map.put("modifyOrder", "order.Id = id and\norder.IsCompleted = iscompleted and\norder.PaymentInformation = paymentinformation and\norder.Amount = amount and\nresult = true\n");
		postconditions_map.put("deleteOrder", "Order.allInstance()->excludes(order) and\nresult = true\n");
		postconditions_map.put("createProduct", "let pro:Product inpro.oclIsNew() and\npro.Id = id and\npro.Name = name and\npro.Price = price and\nProduct.allInstance()->includes(pro) and\nresult = true\n");
		postconditions_map.put("queryProduct", "result = product");
		postconditions_map.put("modifyProduct", "product.Id = id and\nproduct.Name = name and\nproduct.Price = price and\nresult = true\n");
		postconditions_map.put("deleteProduct", "Product.allInstance()->excludes(product) and\nresult = true\n");
		postconditions_map.put("manageItemsPrices", "true");
		
		//service invariants map
		service_invariants_map = new LinkedHashMap<String, String>();
		
		//entity invariants map
		entity_invariants_map = new LinkedHashMap<String, String>();
		
	}
	
	public void generatOperationPane() {
		
		 operationPanels = new LinkedHashMap<String, GridPane>();
		
		 // ==================== GridPane_makeNewOrder ====================
		 GridPane makeNewOrder = new GridPane();
		 makeNewOrder.setHgap(4);
		 makeNewOrder.setVgap(6);
		 makeNewOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> makeNewOrder_content = makeNewOrder.getChildren();
		 Label makeNewOrder_buyId_label = new Label("buyId:");
		 makeNewOrder_buyId_label.setMinWidth(Region.USE_PREF_SIZE);
		 makeNewOrder_content.add(makeNewOrder_buyId_label);
		 GridPane.setConstraints(makeNewOrder_buyId_label, 0, 0);
		 
		 makeNewOrder_buyId_t = new TextField();
		 makeNewOrder_content.add(makeNewOrder_buyId_t);
		 makeNewOrder_buyId_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(makeNewOrder_buyId_t, 1, 0);
		 operationPanels.put("makeNewOrder", makeNewOrder);
		 
		 // ==================== GridPane_addProduct ====================
		 GridPane addProduct = new GridPane();
		 addProduct.setHgap(4);
		 addProduct.setVgap(6);
		 addProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> addProduct_content = addProduct.getChildren();
		 Label addProduct_id_label = new Label("id:");
		 addProduct_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 addProduct_content.add(addProduct_id_label);
		 GridPane.setConstraints(addProduct_id_label, 0, 0);
		 
		 addProduct_id_t = new TextField();
		 addProduct_content.add(addProduct_id_t);
		 addProduct_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addProduct_id_t, 1, 0);
		 Label addProduct_quantity_label = new Label("quantity:");
		 addProduct_quantity_label.setMinWidth(Region.USE_PREF_SIZE);
		 addProduct_content.add(addProduct_quantity_label);
		 GridPane.setConstraints(addProduct_quantity_label, 0, 1);
		 
		 addProduct_quantity_t = new TextField();
		 addProduct_content.add(addProduct_quantity_t);
		 addProduct_quantity_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addProduct_quantity_t, 1, 1);
		 operationPanels.put("addProduct", addProduct);
		 
		 // ==================== GridPane_generateContract ====================
		 GridPane generateContract = new GridPane();
		 generateContract.setHgap(4);
		 generateContract.setVgap(6);
		 generateContract.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> generateContract_content = generateContract.getChildren();
		 Label generateContract_packing_label = new Label("packing:");
		 generateContract_packing_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateContract_content.add(generateContract_packing_label);
		 GridPane.setConstraints(generateContract_packing_label, 0, 0);
		 
		 generateContract_packing_t = new TextField();
		 generateContract_content.add(generateContract_packing_t);
		 generateContract_packing_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(generateContract_packing_t, 1, 0);
		 Label generateContract_dateOfShipment_label = new Label("dateOfShipment (yyyy-MM-dd):");
		 generateContract_dateOfShipment_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateContract_content.add(generateContract_dateOfShipment_label);
		 GridPane.setConstraints(generateContract_dateOfShipment_label, 0, 1);
		 
		 generateContract_dateOfShipment_t = new TextField();
		 generateContract_content.add(generateContract_dateOfShipment_t);
		 generateContract_dateOfShipment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(generateContract_dateOfShipment_t, 1, 1);
		 Label generateContract_portOfShipment_label = new Label("portOfShipment:");
		 generateContract_portOfShipment_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateContract_content.add(generateContract_portOfShipment_label);
		 GridPane.setConstraints(generateContract_portOfShipment_label, 0, 2);
		 
		 generateContract_portOfShipment_t = new TextField();
		 generateContract_content.add(generateContract_portOfShipment_t);
		 generateContract_portOfShipment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(generateContract_portOfShipment_t, 1, 2);
		 Label generateContract_portOfDestination_label = new Label("portOfDestination:");
		 generateContract_portOfDestination_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateContract_content.add(generateContract_portOfDestination_label);
		 GridPane.setConstraints(generateContract_portOfDestination_label, 0, 3);
		 
		 generateContract_portOfDestination_t = new TextField();
		 generateContract_content.add(generateContract_portOfDestination_t);
		 generateContract_portOfDestination_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(generateContract_portOfDestination_t, 1, 3);
		 Label generateContract_insurance_label = new Label("insurance:");
		 generateContract_insurance_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateContract_content.add(generateContract_insurance_label);
		 GridPane.setConstraints(generateContract_insurance_label, 0, 4);
		 
		 generateContract_insurance_t = new TextField();
		 generateContract_content.add(generateContract_insurance_t);
		 generateContract_insurance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(generateContract_insurance_t, 1, 4);
		 Label generateContract_effectiveDate_label = new Label("effectiveDate (yyyy-MM-dd):");
		 generateContract_effectiveDate_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateContract_content.add(generateContract_effectiveDate_label);
		 GridPane.setConstraints(generateContract_effectiveDate_label, 0, 5);
		 
		 generateContract_effectiveDate_t = new TextField();
		 generateContract_content.add(generateContract_effectiveDate_t);
		 generateContract_effectiveDate_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(generateContract_effectiveDate_t, 1, 5);
		 operationPanels.put("generateContract", generateContract);
		 
		 // ==================== GridPane_generateOrder ====================
		 GridPane generateOrder = new GridPane();
		 generateOrder.setHgap(4);
		 generateOrder.setVgap(6);
		 generateOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> generateOrder_content = generateOrder.getChildren();
		 Label generateOrder_label = new Label("This operation is no intput parameters..");
		 generateOrder_label.setMinWidth(Region.USE_PREF_SIZE);
		 generateOrder_content.add(generateOrder_label);
		 GridPane.setConstraints(generateOrder_label, 0, 0);
		 operationPanels.put("generateOrder", generateOrder);
		 
		 // ==================== GridPane_salesPlanManagement ====================
		 GridPane salesPlanManagement = new GridPane();
		 salesPlanManagement.setHgap(4);
		 salesPlanManagement.setVgap(6);
		 salesPlanManagement.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> salesPlanManagement_content = salesPlanManagement.getChildren();
		 Label salesPlanManagement_label = new Label("This operation is no intput parameters..");
		 salesPlanManagement_label.setMinWidth(Region.USE_PREF_SIZE);
		 salesPlanManagement_content.add(salesPlanManagement_label);
		 GridPane.setConstraints(salesPlanManagement_label, 0, 0);
		 operationPanels.put("salesPlanManagement", salesPlanManagement);
		 
		 // ==================== GridPane_deliveryNotification ====================
		 GridPane deliveryNotification = new GridPane();
		 deliveryNotification.setHgap(4);
		 deliveryNotification.setVgap(6);
		 deliveryNotification.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deliveryNotification_content = deliveryNotification.getChildren();
		 Label deliveryNotification_label = new Label("This operation is no intput parameters..");
		 deliveryNotification_label.setMinWidth(Region.USE_PREF_SIZE);
		 deliveryNotification_content.add(deliveryNotification_label);
		 GridPane.setConstraints(deliveryNotification_label, 0, 0);
		 operationPanels.put("deliveryNotification", deliveryNotification);
		 
		 // ==================== GridPane_exchangeNotification ====================
		 GridPane exchangeNotification = new GridPane();
		 exchangeNotification.setHgap(4);
		 exchangeNotification.setVgap(6);
		 exchangeNotification.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> exchangeNotification_content = exchangeNotification.getChildren();
		 Label exchangeNotification_label = new Label("This operation is no intput parameters..");
		 exchangeNotification_label.setMinWidth(Region.USE_PREF_SIZE);
		 exchangeNotification_content.add(exchangeNotification_label);
		 GridPane.setConstraints(exchangeNotification_label, 0, 0);
		 operationPanels.put("exchangeNotification", exchangeNotification);
		 
		 // ==================== GridPane_itemsPricesMaintenance ====================
		 GridPane itemsPricesMaintenance = new GridPane();
		 itemsPricesMaintenance.setHgap(4);
		 itemsPricesMaintenance.setVgap(6);
		 itemsPricesMaintenance.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> itemsPricesMaintenance_content = itemsPricesMaintenance.getChildren();
		 Label itemsPricesMaintenance_label = new Label("This operation is no intput parameters..");
		 itemsPricesMaintenance_label.setMinWidth(Region.USE_PREF_SIZE);
		 itemsPricesMaintenance_content.add(itemsPricesMaintenance_label);
		 GridPane.setConstraints(itemsPricesMaintenance_label, 0, 0);
		 operationPanels.put("itemsPricesMaintenance", itemsPricesMaintenance);
		 
		 // ==================== GridPane_postingOfAccount ====================
		 GridPane postingOfAccount = new GridPane();
		 postingOfAccount.setHgap(4);
		 postingOfAccount.setVgap(6);
		 postingOfAccount.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> postingOfAccount_content = postingOfAccount.getChildren();
		 Label postingOfAccount_label = new Label("This operation is no intput parameters..");
		 postingOfAccount_label.setMinWidth(Region.USE_PREF_SIZE);
		 postingOfAccount_content.add(postingOfAccount_label);
		 GridPane.setConstraints(postingOfAccount_label, 0, 0);
		 operationPanels.put("postingOfAccount", postingOfAccount);
		 
		 // ==================== GridPane_invoiceManagement ====================
		 GridPane invoiceManagement = new GridPane();
		 invoiceManagement.setHgap(4);
		 invoiceManagement.setVgap(6);
		 invoiceManagement.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> invoiceManagement_content = invoiceManagement.getChildren();
		 Label invoiceManagement_label = new Label("This operation is no intput parameters..");
		 invoiceManagement_label.setMinWidth(Region.USE_PREF_SIZE);
		 invoiceManagement_content.add(invoiceManagement_label);
		 GridPane.setConstraints(invoiceManagement_label, 0, 0);
		 operationPanels.put("invoiceManagement", invoiceManagement);
		 
		 // ==================== GridPane_contractTerminationAndSettlement ====================
		 GridPane contractTerminationAndSettlement = new GridPane();
		 contractTerminationAndSettlement.setHgap(4);
		 contractTerminationAndSettlement.setVgap(6);
		 contractTerminationAndSettlement.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> contractTerminationAndSettlement_content = contractTerminationAndSettlement.getChildren();
		 Label contractTerminationAndSettlement_label = new Label("This operation is no intput parameters..");
		 contractTerminationAndSettlement_label.setMinWidth(Region.USE_PREF_SIZE);
		 contractTerminationAndSettlement_content.add(contractTerminationAndSettlement_label);
		 GridPane.setConstraints(contractTerminationAndSettlement_label, 0, 0);
		 operationPanels.put("contractTerminationAndSettlement", contractTerminationAndSettlement);
		 
		 // ==================== GridPane_orderTerminationAndSettlement ====================
		 GridPane orderTerminationAndSettlement = new GridPane();
		 orderTerminationAndSettlement.setHgap(4);
		 orderTerminationAndSettlement.setVgap(6);
		 orderTerminationAndSettlement.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> orderTerminationAndSettlement_content = orderTerminationAndSettlement.getChildren();
		 Label orderTerminationAndSettlement_label = new Label("This operation is no intput parameters..");
		 orderTerminationAndSettlement_label.setMinWidth(Region.USE_PREF_SIZE);
		 orderTerminationAndSettlement_content.add(orderTerminationAndSettlement_label);
		 GridPane.setConstraints(orderTerminationAndSettlement_label, 0, 0);
		 operationPanels.put("orderTerminationAndSettlement", orderTerminationAndSettlement);
		 
		 // ==================== GridPane_salesCommissionManagement ====================
		 GridPane salesCommissionManagement = new GridPane();
		 salesCommissionManagement.setHgap(4);
		 salesCommissionManagement.setVgap(6);
		 salesCommissionManagement.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> salesCommissionManagement_content = salesCommissionManagement.getChildren();
		 Label salesCommissionManagement_label = new Label("This operation is no intput parameters..");
		 salesCommissionManagement_label.setMinWidth(Region.USE_PREF_SIZE);
		 salesCommissionManagement_content.add(salesCommissionManagement_label);
		 GridPane.setConstraints(salesCommissionManagement_label, 0, 0);
		 operationPanels.put("salesCommissionManagement", salesCommissionManagement);
		 
		 // ==================== GridPane_authorization ====================
		 GridPane authorization = new GridPane();
		 authorization.setHgap(4);
		 authorization.setVgap(6);
		 authorization.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> authorization_content = authorization.getChildren();
		 Label authorization_label = new Label("This operation is no intput parameters..");
		 authorization_label.setMinWidth(Region.USE_PREF_SIZE);
		 authorization_content.add(authorization_label);
		 GridPane.setConstraints(authorization_label, 0, 0);
		 operationPanels.put("authorization", authorization);
		 
		 // ==================== GridPane_authorizationProcessing ====================
		 GridPane authorizationProcessing = new GridPane();
		 authorizationProcessing.setHgap(4);
		 authorizationProcessing.setVgap(6);
		 authorizationProcessing.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> authorizationProcessing_content = authorizationProcessing.getChildren();
		 Label authorizationProcessing_label = new Label("This operation is no intput parameters..");
		 authorizationProcessing_label.setMinWidth(Region.USE_PREF_SIZE);
		 authorizationProcessing_content.add(authorizationProcessing_label);
		 GridPane.setConstraints(authorizationProcessing_label, 0, 0);
		 operationPanels.put("authorizationProcessing", authorizationProcessing);
		 
		 // ==================== GridPane_createContracts ====================
		 GridPane createContracts = new GridPane();
		 createContracts.setHgap(4);
		 createContracts.setVgap(6);
		 createContracts.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createContracts_content = createContracts.getChildren();
		 Label createContracts_id_label = new Label("id:");
		 createContracts_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_id_label);
		 GridPane.setConstraints(createContracts_id_label, 0, 0);
		 
		 createContracts_id_t = new TextField();
		 createContracts_content.add(createContracts_id_t);
		 createContracts_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_id_t, 1, 0);
		 Label createContracts_buyer_label = new Label("buyer:");
		 createContracts_buyer_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_buyer_label);
		 GridPane.setConstraints(createContracts_buyer_label, 0, 1);
		 
		 createContracts_buyer_t = new TextField();
		 createContracts_content.add(createContracts_buyer_t);
		 createContracts_buyer_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_buyer_t, 1, 1);
		 Label createContracts_packing_label = new Label("packing:");
		 createContracts_packing_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_packing_label);
		 GridPane.setConstraints(createContracts_packing_label, 0, 2);
		 
		 createContracts_packing_t = new TextField();
		 createContracts_content.add(createContracts_packing_t);
		 createContracts_packing_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_packing_t, 1, 2);
		 Label createContracts_dateofshipment_label = new Label("dateofshipment (yyyy-MM-dd):");
		 createContracts_dateofshipment_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_dateofshipment_label);
		 GridPane.setConstraints(createContracts_dateofshipment_label, 0, 3);
		 
		 createContracts_dateofshipment_t = new TextField();
		 createContracts_content.add(createContracts_dateofshipment_t);
		 createContracts_dateofshipment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_dateofshipment_t, 1, 3);
		 Label createContracts_portofshipment_label = new Label("portofshipment:");
		 createContracts_portofshipment_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_portofshipment_label);
		 GridPane.setConstraints(createContracts_portofshipment_label, 0, 4);
		 
		 createContracts_portofshipment_t = new TextField();
		 createContracts_content.add(createContracts_portofshipment_t);
		 createContracts_portofshipment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_portofshipment_t, 1, 4);
		 Label createContracts_portofdestination_label = new Label("portofdestination:");
		 createContracts_portofdestination_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_portofdestination_label);
		 GridPane.setConstraints(createContracts_portofdestination_label, 0, 5);
		 
		 createContracts_portofdestination_t = new TextField();
		 createContracts_content.add(createContracts_portofdestination_t);
		 createContracts_portofdestination_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_portofdestination_t, 1, 5);
		 Label createContracts_insurance_label = new Label("insurance:");
		 createContracts_insurance_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_insurance_label);
		 GridPane.setConstraints(createContracts_insurance_label, 0, 6);
		 
		 createContracts_insurance_t = new TextField();
		 createContracts_content.add(createContracts_insurance_t);
		 createContracts_insurance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_insurance_t, 1, 6);
		 Label createContracts_effectivedate_label = new Label("effectivedate (yyyy-MM-dd):");
		 createContracts_effectivedate_label.setMinWidth(Region.USE_PREF_SIZE);
		 createContracts_content.add(createContracts_effectivedate_label);
		 GridPane.setConstraints(createContracts_effectivedate_label, 0, 7);
		 
		 createContracts_effectivedate_t = new TextField();
		 createContracts_content.add(createContracts_effectivedate_t);
		 createContracts_effectivedate_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createContracts_effectivedate_t, 1, 7);
		 operationPanels.put("createContracts", createContracts);
		 
		 // ==================== GridPane_queryContracts ====================
		 GridPane queryContracts = new GridPane();
		 queryContracts.setHgap(4);
		 queryContracts.setVgap(6);
		 queryContracts.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryContracts_content = queryContracts.getChildren();
		 Label queryContracts_id_label = new Label("id:");
		 queryContracts_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryContracts_content.add(queryContracts_id_label);
		 GridPane.setConstraints(queryContracts_id_label, 0, 0);
		 
		 queryContracts_id_t = new TextField();
		 queryContracts_content.add(queryContracts_id_t);
		 queryContracts_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryContracts_id_t, 1, 0);
		 operationPanels.put("queryContracts", queryContracts);
		 
		 // ==================== GridPane_modifyContracts ====================
		 GridPane modifyContracts = new GridPane();
		 modifyContracts.setHgap(4);
		 modifyContracts.setVgap(6);
		 modifyContracts.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyContracts_content = modifyContracts.getChildren();
		 Label modifyContracts_id_label = new Label("id:");
		 modifyContracts_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_id_label);
		 GridPane.setConstraints(modifyContracts_id_label, 0, 0);
		 
		 modifyContracts_id_t = new TextField();
		 modifyContracts_content.add(modifyContracts_id_t);
		 modifyContracts_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_id_t, 1, 0);
		 Label modifyContracts_buyer_label = new Label("buyer:");
		 modifyContracts_buyer_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_buyer_label);
		 GridPane.setConstraints(modifyContracts_buyer_label, 0, 1);
		 
		 modifyContracts_buyer_t = new TextField();
		 modifyContracts_content.add(modifyContracts_buyer_t);
		 modifyContracts_buyer_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_buyer_t, 1, 1);
		 Label modifyContracts_packing_label = new Label("packing:");
		 modifyContracts_packing_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_packing_label);
		 GridPane.setConstraints(modifyContracts_packing_label, 0, 2);
		 
		 modifyContracts_packing_t = new TextField();
		 modifyContracts_content.add(modifyContracts_packing_t);
		 modifyContracts_packing_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_packing_t, 1, 2);
		 Label modifyContracts_dateofshipment_label = new Label("dateofshipment (yyyy-MM-dd):");
		 modifyContracts_dateofshipment_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_dateofshipment_label);
		 GridPane.setConstraints(modifyContracts_dateofshipment_label, 0, 3);
		 
		 modifyContracts_dateofshipment_t = new TextField();
		 modifyContracts_content.add(modifyContracts_dateofshipment_t);
		 modifyContracts_dateofshipment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_dateofshipment_t, 1, 3);
		 Label modifyContracts_portofshipment_label = new Label("portofshipment:");
		 modifyContracts_portofshipment_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_portofshipment_label);
		 GridPane.setConstraints(modifyContracts_portofshipment_label, 0, 4);
		 
		 modifyContracts_portofshipment_t = new TextField();
		 modifyContracts_content.add(modifyContracts_portofshipment_t);
		 modifyContracts_portofshipment_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_portofshipment_t, 1, 4);
		 Label modifyContracts_portofdestination_label = new Label("portofdestination:");
		 modifyContracts_portofdestination_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_portofdestination_label);
		 GridPane.setConstraints(modifyContracts_portofdestination_label, 0, 5);
		 
		 modifyContracts_portofdestination_t = new TextField();
		 modifyContracts_content.add(modifyContracts_portofdestination_t);
		 modifyContracts_portofdestination_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_portofdestination_t, 1, 5);
		 Label modifyContracts_insurance_label = new Label("insurance:");
		 modifyContracts_insurance_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_insurance_label);
		 GridPane.setConstraints(modifyContracts_insurance_label, 0, 6);
		 
		 modifyContracts_insurance_t = new TextField();
		 modifyContracts_content.add(modifyContracts_insurance_t);
		 modifyContracts_insurance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_insurance_t, 1, 6);
		 Label modifyContracts_effectivedate_label = new Label("effectivedate (yyyy-MM-dd):");
		 modifyContracts_effectivedate_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyContracts_content.add(modifyContracts_effectivedate_label);
		 GridPane.setConstraints(modifyContracts_effectivedate_label, 0, 7);
		 
		 modifyContracts_effectivedate_t = new TextField();
		 modifyContracts_content.add(modifyContracts_effectivedate_t);
		 modifyContracts_effectivedate_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyContracts_effectivedate_t, 1, 7);
		 operationPanels.put("modifyContracts", modifyContracts);
		 
		 // ==================== GridPane_deleteContracts ====================
		 GridPane deleteContracts = new GridPane();
		 deleteContracts.setHgap(4);
		 deleteContracts.setVgap(6);
		 deleteContracts.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteContracts_content = deleteContracts.getChildren();
		 Label deleteContracts_id_label = new Label("id:");
		 deleteContracts_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteContracts_content.add(deleteContracts_id_label);
		 GridPane.setConstraints(deleteContracts_id_label, 0, 0);
		 
		 deleteContracts_id_t = new TextField();
		 deleteContracts_content.add(deleteContracts_id_t);
		 deleteContracts_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteContracts_id_t, 1, 0);
		 operationPanels.put("deleteContracts", deleteContracts);
		 
		 // ==================== GridPane_createInvoice ====================
		 GridPane createInvoice = new GridPane();
		 createInvoice.setHgap(4);
		 createInvoice.setVgap(6);
		 createInvoice.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createInvoice_content = createInvoice.getChildren();
		 Label createInvoice_id_label = new Label("id:");
		 createInvoice_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 createInvoice_content.add(createInvoice_id_label);
		 GridPane.setConstraints(createInvoice_id_label, 0, 0);
		 
		 createInvoice_id_t = new TextField();
		 createInvoice_content.add(createInvoice_id_t);
		 createInvoice_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createInvoice_id_t, 1, 0);
		 Label createInvoice_title_label = new Label("title:");
		 createInvoice_title_label.setMinWidth(Region.USE_PREF_SIZE);
		 createInvoice_content.add(createInvoice_title_label);
		 GridPane.setConstraints(createInvoice_title_label, 0, 1);
		 
		 createInvoice_title_t = new TextField();
		 createInvoice_content.add(createInvoice_title_t);
		 createInvoice_title_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createInvoice_title_t, 1, 1);
		 Label createInvoice_effecitvedate_label = new Label("effecitvedate (yyyy-MM-dd):");
		 createInvoice_effecitvedate_label.setMinWidth(Region.USE_PREF_SIZE);
		 createInvoice_content.add(createInvoice_effecitvedate_label);
		 GridPane.setConstraints(createInvoice_effecitvedate_label, 0, 2);
		 
		 createInvoice_effecitvedate_t = new TextField();
		 createInvoice_content.add(createInvoice_effecitvedate_t);
		 createInvoice_effecitvedate_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createInvoice_effecitvedate_t, 1, 2);
		 Label createInvoice_amount_label = new Label("amount:");
		 createInvoice_amount_label.setMinWidth(Region.USE_PREF_SIZE);
		 createInvoice_content.add(createInvoice_amount_label);
		 GridPane.setConstraints(createInvoice_amount_label, 0, 3);
		 
		 createInvoice_amount_t = new TextField();
		 createInvoice_content.add(createInvoice_amount_t);
		 createInvoice_amount_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createInvoice_amount_t, 1, 3);
		 operationPanels.put("createInvoice", createInvoice);
		 
		 // ==================== GridPane_queryInvoice ====================
		 GridPane queryInvoice = new GridPane();
		 queryInvoice.setHgap(4);
		 queryInvoice.setVgap(6);
		 queryInvoice.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryInvoice_content = queryInvoice.getChildren();
		 Label queryInvoice_id_label = new Label("id:");
		 queryInvoice_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryInvoice_content.add(queryInvoice_id_label);
		 GridPane.setConstraints(queryInvoice_id_label, 0, 0);
		 
		 queryInvoice_id_t = new TextField();
		 queryInvoice_content.add(queryInvoice_id_t);
		 queryInvoice_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryInvoice_id_t, 1, 0);
		 operationPanels.put("queryInvoice", queryInvoice);
		 
		 // ==================== GridPane_modifyInvoice ====================
		 GridPane modifyInvoice = new GridPane();
		 modifyInvoice.setHgap(4);
		 modifyInvoice.setVgap(6);
		 modifyInvoice.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyInvoice_content = modifyInvoice.getChildren();
		 Label modifyInvoice_id_label = new Label("id:");
		 modifyInvoice_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyInvoice_content.add(modifyInvoice_id_label);
		 GridPane.setConstraints(modifyInvoice_id_label, 0, 0);
		 
		 modifyInvoice_id_t = new TextField();
		 modifyInvoice_content.add(modifyInvoice_id_t);
		 modifyInvoice_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyInvoice_id_t, 1, 0);
		 Label modifyInvoice_title_label = new Label("title:");
		 modifyInvoice_title_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyInvoice_content.add(modifyInvoice_title_label);
		 GridPane.setConstraints(modifyInvoice_title_label, 0, 1);
		 
		 modifyInvoice_title_t = new TextField();
		 modifyInvoice_content.add(modifyInvoice_title_t);
		 modifyInvoice_title_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyInvoice_title_t, 1, 1);
		 Label modifyInvoice_effecitvedate_label = new Label("effecitvedate (yyyy-MM-dd):");
		 modifyInvoice_effecitvedate_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyInvoice_content.add(modifyInvoice_effecitvedate_label);
		 GridPane.setConstraints(modifyInvoice_effecitvedate_label, 0, 2);
		 
		 modifyInvoice_effecitvedate_t = new TextField();
		 modifyInvoice_content.add(modifyInvoice_effecitvedate_t);
		 modifyInvoice_effecitvedate_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyInvoice_effecitvedate_t, 1, 2);
		 Label modifyInvoice_amount_label = new Label("amount:");
		 modifyInvoice_amount_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyInvoice_content.add(modifyInvoice_amount_label);
		 GridPane.setConstraints(modifyInvoice_amount_label, 0, 3);
		 
		 modifyInvoice_amount_t = new TextField();
		 modifyInvoice_content.add(modifyInvoice_amount_t);
		 modifyInvoice_amount_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyInvoice_amount_t, 1, 3);
		 operationPanels.put("modifyInvoice", modifyInvoice);
		 
		 // ==================== GridPane_deleteInvoice ====================
		 GridPane deleteInvoice = new GridPane();
		 deleteInvoice.setHgap(4);
		 deleteInvoice.setVgap(6);
		 deleteInvoice.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteInvoice_content = deleteInvoice.getChildren();
		 Label deleteInvoice_id_label = new Label("id:");
		 deleteInvoice_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteInvoice_content.add(deleteInvoice_id_label);
		 GridPane.setConstraints(deleteInvoice_id_label, 0, 0);
		 
		 deleteInvoice_id_t = new TextField();
		 deleteInvoice_content.add(deleteInvoice_id_t);
		 deleteInvoice_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteInvoice_id_t, 1, 0);
		 operationPanels.put("deleteInvoice", deleteInvoice);
		 
		 // ==================== GridPane_createBillOfLading ====================
		 GridPane createBillOfLading = new GridPane();
		 createBillOfLading.setHgap(4);
		 createBillOfLading.setVgap(6);
		 createBillOfLading.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createBillOfLading_content = createBillOfLading.getChildren();
		 Label createBillOfLading_id_label = new Label("id:");
		 createBillOfLading_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_id_label);
		 GridPane.setConstraints(createBillOfLading_id_label, 0, 0);
		 
		 createBillOfLading_id_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_id_t);
		 createBillOfLading_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_id_t, 1, 0);
		 Label createBillOfLading_consignee_label = new Label("consignee:");
		 createBillOfLading_consignee_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_consignee_label);
		 GridPane.setConstraints(createBillOfLading_consignee_label, 0, 1);
		 
		 createBillOfLading_consignee_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_consignee_t);
		 createBillOfLading_consignee_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_consignee_t, 1, 1);
		 Label createBillOfLading_commoditylist_label = new Label("commoditylist:");
		 createBillOfLading_commoditylist_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_commoditylist_label);
		 GridPane.setConstraints(createBillOfLading_commoditylist_label, 0, 2);
		 
		 createBillOfLading_commoditylist_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_commoditylist_t);
		 createBillOfLading_commoditylist_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_commoditylist_t, 1, 2);
		 Label createBillOfLading_contractobject_label = new Label("contractobject:");
		 createBillOfLading_contractobject_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_contractobject_label);
		 GridPane.setConstraints(createBillOfLading_contractobject_label, 0, 3);
		 
		 createBillOfLading_contractobject_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_contractobject_t);
		 createBillOfLading_contractobject_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_contractobject_t, 1, 3);
		 Label createBillOfLading_quantity_label = new Label("quantity:");
		 createBillOfLading_quantity_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_quantity_label);
		 GridPane.setConstraints(createBillOfLading_quantity_label, 0, 4);
		 
		 createBillOfLading_quantity_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_quantity_t);
		 createBillOfLading_quantity_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_quantity_t, 1, 4);
		 Label createBillOfLading_totalprice_label = new Label("totalprice:");
		 createBillOfLading_totalprice_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_totalprice_label);
		 GridPane.setConstraints(createBillOfLading_totalprice_label, 0, 5);
		 
		 createBillOfLading_totalprice_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_totalprice_t);
		 createBillOfLading_totalprice_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_totalprice_t, 1, 5);
		 Label createBillOfLading_deadlineforperformance_label = new Label("deadlineforperformance:");
		 createBillOfLading_deadlineforperformance_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_deadlineforperformance_label);
		 GridPane.setConstraints(createBillOfLading_deadlineforperformance_label, 0, 6);
		 
		 createBillOfLading_deadlineforperformance_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_deadlineforperformance_t);
		 createBillOfLading_deadlineforperformance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_deadlineforperformance_t, 1, 6);
		 Label createBillOfLading_locationforperformance_label = new Label("locationforperformance:");
		 createBillOfLading_locationforperformance_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_locationforperformance_label);
		 GridPane.setConstraints(createBillOfLading_locationforperformance_label, 0, 7);
		 
		 createBillOfLading_locationforperformance_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_locationforperformance_t);
		 createBillOfLading_locationforperformance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_locationforperformance_t, 1, 7);
		 Label createBillOfLading_methodforperformance_label = new Label("methodforperformance:");
		 createBillOfLading_methodforperformance_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBillOfLading_content.add(createBillOfLading_methodforperformance_label);
		 GridPane.setConstraints(createBillOfLading_methodforperformance_label, 0, 8);
		 
		 createBillOfLading_methodforperformance_t = new TextField();
		 createBillOfLading_content.add(createBillOfLading_methodforperformance_t);
		 createBillOfLading_methodforperformance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBillOfLading_methodforperformance_t, 1, 8);
		 operationPanels.put("createBillOfLading", createBillOfLading);
		 
		 // ==================== GridPane_queryBillOfLading ====================
		 GridPane queryBillOfLading = new GridPane();
		 queryBillOfLading.setHgap(4);
		 queryBillOfLading.setVgap(6);
		 queryBillOfLading.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryBillOfLading_content = queryBillOfLading.getChildren();
		 Label queryBillOfLading_id_label = new Label("id:");
		 queryBillOfLading_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryBillOfLading_content.add(queryBillOfLading_id_label);
		 GridPane.setConstraints(queryBillOfLading_id_label, 0, 0);
		 
		 queryBillOfLading_id_t = new TextField();
		 queryBillOfLading_content.add(queryBillOfLading_id_t);
		 queryBillOfLading_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryBillOfLading_id_t, 1, 0);
		 operationPanels.put("queryBillOfLading", queryBillOfLading);
		 
		 // ==================== GridPane_modifyBillOfLading ====================
		 GridPane modifyBillOfLading = new GridPane();
		 modifyBillOfLading.setHgap(4);
		 modifyBillOfLading.setVgap(6);
		 modifyBillOfLading.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyBillOfLading_content = modifyBillOfLading.getChildren();
		 Label modifyBillOfLading_id_label = new Label("id:");
		 modifyBillOfLading_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_id_label);
		 GridPane.setConstraints(modifyBillOfLading_id_label, 0, 0);
		 
		 modifyBillOfLading_id_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_id_t);
		 modifyBillOfLading_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_id_t, 1, 0);
		 Label modifyBillOfLading_consignee_label = new Label("consignee:");
		 modifyBillOfLading_consignee_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_consignee_label);
		 GridPane.setConstraints(modifyBillOfLading_consignee_label, 0, 1);
		 
		 modifyBillOfLading_consignee_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_consignee_t);
		 modifyBillOfLading_consignee_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_consignee_t, 1, 1);
		 Label modifyBillOfLading_commoditylist_label = new Label("commoditylist:");
		 modifyBillOfLading_commoditylist_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_commoditylist_label);
		 GridPane.setConstraints(modifyBillOfLading_commoditylist_label, 0, 2);
		 
		 modifyBillOfLading_commoditylist_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_commoditylist_t);
		 modifyBillOfLading_commoditylist_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_commoditylist_t, 1, 2);
		 Label modifyBillOfLading_contractobject_label = new Label("contractobject:");
		 modifyBillOfLading_contractobject_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_contractobject_label);
		 GridPane.setConstraints(modifyBillOfLading_contractobject_label, 0, 3);
		 
		 modifyBillOfLading_contractobject_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_contractobject_t);
		 modifyBillOfLading_contractobject_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_contractobject_t, 1, 3);
		 Label modifyBillOfLading_quantity_label = new Label("quantity:");
		 modifyBillOfLading_quantity_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_quantity_label);
		 GridPane.setConstraints(modifyBillOfLading_quantity_label, 0, 4);
		 
		 modifyBillOfLading_quantity_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_quantity_t);
		 modifyBillOfLading_quantity_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_quantity_t, 1, 4);
		 Label modifyBillOfLading_totalprice_label = new Label("totalprice:");
		 modifyBillOfLading_totalprice_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_totalprice_label);
		 GridPane.setConstraints(modifyBillOfLading_totalprice_label, 0, 5);
		 
		 modifyBillOfLading_totalprice_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_totalprice_t);
		 modifyBillOfLading_totalprice_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_totalprice_t, 1, 5);
		 Label modifyBillOfLading_deadlineforperformance_label = new Label("deadlineforperformance:");
		 modifyBillOfLading_deadlineforperformance_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_deadlineforperformance_label);
		 GridPane.setConstraints(modifyBillOfLading_deadlineforperformance_label, 0, 6);
		 
		 modifyBillOfLading_deadlineforperformance_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_deadlineforperformance_t);
		 modifyBillOfLading_deadlineforperformance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_deadlineforperformance_t, 1, 6);
		 Label modifyBillOfLading_locationforperformance_label = new Label("locationforperformance:");
		 modifyBillOfLading_locationforperformance_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_locationforperformance_label);
		 GridPane.setConstraints(modifyBillOfLading_locationforperformance_label, 0, 7);
		 
		 modifyBillOfLading_locationforperformance_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_locationforperformance_t);
		 modifyBillOfLading_locationforperformance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_locationforperformance_t, 1, 7);
		 Label modifyBillOfLading_methodforperformance_label = new Label("methodforperformance:");
		 modifyBillOfLading_methodforperformance_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBillOfLading_content.add(modifyBillOfLading_methodforperformance_label);
		 GridPane.setConstraints(modifyBillOfLading_methodforperformance_label, 0, 8);
		 
		 modifyBillOfLading_methodforperformance_t = new TextField();
		 modifyBillOfLading_content.add(modifyBillOfLading_methodforperformance_t);
		 modifyBillOfLading_methodforperformance_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBillOfLading_methodforperformance_t, 1, 8);
		 operationPanels.put("modifyBillOfLading", modifyBillOfLading);
		 
		 // ==================== GridPane_deleteBillOfLading ====================
		 GridPane deleteBillOfLading = new GridPane();
		 deleteBillOfLading.setHgap(4);
		 deleteBillOfLading.setVgap(6);
		 deleteBillOfLading.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteBillOfLading_content = deleteBillOfLading.getChildren();
		 Label deleteBillOfLading_id_label = new Label("id:");
		 deleteBillOfLading_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteBillOfLading_content.add(deleteBillOfLading_id_label);
		 GridPane.setConstraints(deleteBillOfLading_id_label, 0, 0);
		 
		 deleteBillOfLading_id_t = new TextField();
		 deleteBillOfLading_content.add(deleteBillOfLading_id_t);
		 deleteBillOfLading_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteBillOfLading_id_t, 1, 0);
		 operationPanels.put("deleteBillOfLading", deleteBillOfLading);
		 
		 // ==================== GridPane_createClient ====================
		 GridPane createClient = new GridPane();
		 createClient.setHgap(4);
		 createClient.setVgap(6);
		 createClient.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createClient_content = createClient.getChildren();
		 Label createClient_id_label = new Label("id:");
		 createClient_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 createClient_content.add(createClient_id_label);
		 GridPane.setConstraints(createClient_id_label, 0, 0);
		 
		 createClient_id_t = new TextField();
		 createClient_content.add(createClient_id_t);
		 createClient_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createClient_id_t, 1, 0);
		 Label createClient_name_label = new Label("name:");
		 createClient_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createClient_content.add(createClient_name_label);
		 GridPane.setConstraints(createClient_name_label, 0, 1);
		 
		 createClient_name_t = new TextField();
		 createClient_content.add(createClient_name_t);
		 createClient_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createClient_name_t, 1, 1);
		 Label createClient_address_label = new Label("address:");
		 createClient_address_label.setMinWidth(Region.USE_PREF_SIZE);
		 createClient_content.add(createClient_address_label);
		 GridPane.setConstraints(createClient_address_label, 0, 2);
		 
		 createClient_address_t = new TextField();
		 createClient_content.add(createClient_address_t);
		 createClient_address_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createClient_address_t, 1, 2);
		 Label createClient_contact_label = new Label("contact:");
		 createClient_contact_label.setMinWidth(Region.USE_PREF_SIZE);
		 createClient_content.add(createClient_contact_label);
		 GridPane.setConstraints(createClient_contact_label, 0, 3);
		 
		 createClient_contact_t = new TextField();
		 createClient_content.add(createClient_contact_t);
		 createClient_contact_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createClient_contact_t, 1, 3);
		 Label createClient_phonenumber_label = new Label("phonenumber:");
		 createClient_phonenumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 createClient_content.add(createClient_phonenumber_label);
		 GridPane.setConstraints(createClient_phonenumber_label, 0, 4);
		 
		 createClient_phonenumber_t = new TextField();
		 createClient_content.add(createClient_phonenumber_t);
		 createClient_phonenumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createClient_phonenumber_t, 1, 4);
		 operationPanels.put("createClient", createClient);
		 
		 // ==================== GridPane_queryClient ====================
		 GridPane queryClient = new GridPane();
		 queryClient.setHgap(4);
		 queryClient.setVgap(6);
		 queryClient.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryClient_content = queryClient.getChildren();
		 Label queryClient_id_label = new Label("id:");
		 queryClient_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryClient_content.add(queryClient_id_label);
		 GridPane.setConstraints(queryClient_id_label, 0, 0);
		 
		 queryClient_id_t = new TextField();
		 queryClient_content.add(queryClient_id_t);
		 queryClient_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryClient_id_t, 1, 0);
		 operationPanels.put("queryClient", queryClient);
		 
		 // ==================== GridPane_modifyClient ====================
		 GridPane modifyClient = new GridPane();
		 modifyClient.setHgap(4);
		 modifyClient.setVgap(6);
		 modifyClient.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyClient_content = modifyClient.getChildren();
		 Label modifyClient_id_label = new Label("id:");
		 modifyClient_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyClient_content.add(modifyClient_id_label);
		 GridPane.setConstraints(modifyClient_id_label, 0, 0);
		 
		 modifyClient_id_t = new TextField();
		 modifyClient_content.add(modifyClient_id_t);
		 modifyClient_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyClient_id_t, 1, 0);
		 Label modifyClient_name_label = new Label("name:");
		 modifyClient_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyClient_content.add(modifyClient_name_label);
		 GridPane.setConstraints(modifyClient_name_label, 0, 1);
		 
		 modifyClient_name_t = new TextField();
		 modifyClient_content.add(modifyClient_name_t);
		 modifyClient_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyClient_name_t, 1, 1);
		 Label modifyClient_address_label = new Label("address:");
		 modifyClient_address_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyClient_content.add(modifyClient_address_label);
		 GridPane.setConstraints(modifyClient_address_label, 0, 2);
		 
		 modifyClient_address_t = new TextField();
		 modifyClient_content.add(modifyClient_address_t);
		 modifyClient_address_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyClient_address_t, 1, 2);
		 Label modifyClient_contact_label = new Label("contact:");
		 modifyClient_contact_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyClient_content.add(modifyClient_contact_label);
		 GridPane.setConstraints(modifyClient_contact_label, 0, 3);
		 
		 modifyClient_contact_t = new TextField();
		 modifyClient_content.add(modifyClient_contact_t);
		 modifyClient_contact_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyClient_contact_t, 1, 3);
		 Label modifyClient_phonenumber_label = new Label("phonenumber:");
		 modifyClient_phonenumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyClient_content.add(modifyClient_phonenumber_label);
		 GridPane.setConstraints(modifyClient_phonenumber_label, 0, 4);
		 
		 modifyClient_phonenumber_t = new TextField();
		 modifyClient_content.add(modifyClient_phonenumber_t);
		 modifyClient_phonenumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyClient_phonenumber_t, 1, 4);
		 operationPanels.put("modifyClient", modifyClient);
		 
		 // ==================== GridPane_deleteClient ====================
		 GridPane deleteClient = new GridPane();
		 deleteClient.setHgap(4);
		 deleteClient.setVgap(6);
		 deleteClient.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteClient_content = deleteClient.getChildren();
		 Label deleteClient_id_label = new Label("id:");
		 deleteClient_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteClient_content.add(deleteClient_id_label);
		 GridPane.setConstraints(deleteClient_id_label, 0, 0);
		 
		 deleteClient_id_t = new TextField();
		 deleteClient_content.add(deleteClient_id_t);
		 deleteClient_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteClient_id_t, 1, 0);
		 operationPanels.put("deleteClient", deleteClient);
		 
		 // ==================== GridPane_createOrder ====================
		 GridPane createOrder = new GridPane();
		 createOrder.setHgap(4);
		 createOrder.setVgap(6);
		 createOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createOrder_content = createOrder.getChildren();
		 Label createOrder_id_label = new Label("id:");
		 createOrder_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_id_label);
		 GridPane.setConstraints(createOrder_id_label, 0, 0);
		 
		 createOrder_id_t = new TextField();
		 createOrder_content.add(createOrder_id_t);
		 createOrder_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_id_t, 1, 0);
		 Label createOrder_iscompleted_label = new Label("iscompleted:");
		 createOrder_iscompleted_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_iscompleted_label);
		 GridPane.setConstraints(createOrder_iscompleted_label, 0, 1);
		 
		 createOrder_iscompleted_t = new TextField();
		 createOrder_content.add(createOrder_iscompleted_t);
		 createOrder_iscompleted_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_iscompleted_t, 1, 1);
		 Label createOrder_paymentinformation_label = new Label("paymentinformation:");
		 createOrder_paymentinformation_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_paymentinformation_label);
		 GridPane.setConstraints(createOrder_paymentinformation_label, 0, 2);
		 
		 createOrder_paymentinformation_t = new TextField();
		 createOrder_content.add(createOrder_paymentinformation_t);
		 createOrder_paymentinformation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_paymentinformation_t, 1, 2);
		 Label createOrder_amount_label = new Label("amount:");
		 createOrder_amount_label.setMinWidth(Region.USE_PREF_SIZE);
		 createOrder_content.add(createOrder_amount_label);
		 GridPane.setConstraints(createOrder_amount_label, 0, 3);
		 
		 createOrder_amount_t = new TextField();
		 createOrder_content.add(createOrder_amount_t);
		 createOrder_amount_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createOrder_amount_t, 1, 3);
		 operationPanels.put("createOrder", createOrder);
		 
		 // ==================== GridPane_queryOrder ====================
		 GridPane queryOrder = new GridPane();
		 queryOrder.setHgap(4);
		 queryOrder.setVgap(6);
		 queryOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryOrder_content = queryOrder.getChildren();
		 Label queryOrder_id_label = new Label("id:");
		 queryOrder_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryOrder_content.add(queryOrder_id_label);
		 GridPane.setConstraints(queryOrder_id_label, 0, 0);
		 
		 queryOrder_id_t = new TextField();
		 queryOrder_content.add(queryOrder_id_t);
		 queryOrder_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryOrder_id_t, 1, 0);
		 operationPanels.put("queryOrder", queryOrder);
		 
		 // ==================== GridPane_modifyOrder ====================
		 GridPane modifyOrder = new GridPane();
		 modifyOrder.setHgap(4);
		 modifyOrder.setVgap(6);
		 modifyOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyOrder_content = modifyOrder.getChildren();
		 Label modifyOrder_id_label = new Label("id:");
		 modifyOrder_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_id_label);
		 GridPane.setConstraints(modifyOrder_id_label, 0, 0);
		 
		 modifyOrder_id_t = new TextField();
		 modifyOrder_content.add(modifyOrder_id_t);
		 modifyOrder_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_id_t, 1, 0);
		 Label modifyOrder_iscompleted_label = new Label("iscompleted:");
		 modifyOrder_iscompleted_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_iscompleted_label);
		 GridPane.setConstraints(modifyOrder_iscompleted_label, 0, 1);
		 
		 modifyOrder_iscompleted_t = new TextField();
		 modifyOrder_content.add(modifyOrder_iscompleted_t);
		 modifyOrder_iscompleted_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_iscompleted_t, 1, 1);
		 Label modifyOrder_paymentinformation_label = new Label("paymentinformation:");
		 modifyOrder_paymentinformation_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_paymentinformation_label);
		 GridPane.setConstraints(modifyOrder_paymentinformation_label, 0, 2);
		 
		 modifyOrder_paymentinformation_t = new TextField();
		 modifyOrder_content.add(modifyOrder_paymentinformation_t);
		 modifyOrder_paymentinformation_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_paymentinformation_t, 1, 2);
		 Label modifyOrder_amount_label = new Label("amount:");
		 modifyOrder_amount_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyOrder_content.add(modifyOrder_amount_label);
		 GridPane.setConstraints(modifyOrder_amount_label, 0, 3);
		 
		 modifyOrder_amount_t = new TextField();
		 modifyOrder_content.add(modifyOrder_amount_t);
		 modifyOrder_amount_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyOrder_amount_t, 1, 3);
		 operationPanels.put("modifyOrder", modifyOrder);
		 
		 // ==================== GridPane_deleteOrder ====================
		 GridPane deleteOrder = new GridPane();
		 deleteOrder.setHgap(4);
		 deleteOrder.setVgap(6);
		 deleteOrder.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteOrder_content = deleteOrder.getChildren();
		 Label deleteOrder_id_label = new Label("id:");
		 deleteOrder_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteOrder_content.add(deleteOrder_id_label);
		 GridPane.setConstraints(deleteOrder_id_label, 0, 0);
		 
		 deleteOrder_id_t = new TextField();
		 deleteOrder_content.add(deleteOrder_id_t);
		 deleteOrder_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteOrder_id_t, 1, 0);
		 operationPanels.put("deleteOrder", deleteOrder);
		 
		 // ==================== GridPane_createProduct ====================
		 GridPane createProduct = new GridPane();
		 createProduct.setHgap(4);
		 createProduct.setVgap(6);
		 createProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createProduct_content = createProduct.getChildren();
		 Label createProduct_id_label = new Label("id:");
		 createProduct_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 createProduct_content.add(createProduct_id_label);
		 GridPane.setConstraints(createProduct_id_label, 0, 0);
		 
		 createProduct_id_t = new TextField();
		 createProduct_content.add(createProduct_id_t);
		 createProduct_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createProduct_id_t, 1, 0);
		 Label createProduct_name_label = new Label("name:");
		 createProduct_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createProduct_content.add(createProduct_name_label);
		 GridPane.setConstraints(createProduct_name_label, 0, 1);
		 
		 createProduct_name_t = new TextField();
		 createProduct_content.add(createProduct_name_t);
		 createProduct_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createProduct_name_t, 1, 1);
		 Label createProduct_price_label = new Label("price:");
		 createProduct_price_label.setMinWidth(Region.USE_PREF_SIZE);
		 createProduct_content.add(createProduct_price_label);
		 GridPane.setConstraints(createProduct_price_label, 0, 2);
		 
		 createProduct_price_t = new TextField();
		 createProduct_content.add(createProduct_price_t);
		 createProduct_price_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createProduct_price_t, 1, 2);
		 operationPanels.put("createProduct", createProduct);
		 
		 // ==================== GridPane_queryProduct ====================
		 GridPane queryProduct = new GridPane();
		 queryProduct.setHgap(4);
		 queryProduct.setVgap(6);
		 queryProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryProduct_content = queryProduct.getChildren();
		 Label queryProduct_id_label = new Label("id:");
		 queryProduct_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryProduct_content.add(queryProduct_id_label);
		 GridPane.setConstraints(queryProduct_id_label, 0, 0);
		 
		 queryProduct_id_t = new TextField();
		 queryProduct_content.add(queryProduct_id_t);
		 queryProduct_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryProduct_id_t, 1, 0);
		 operationPanels.put("queryProduct", queryProduct);
		 
		 // ==================== GridPane_modifyProduct ====================
		 GridPane modifyProduct = new GridPane();
		 modifyProduct.setHgap(4);
		 modifyProduct.setVgap(6);
		 modifyProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyProduct_content = modifyProduct.getChildren();
		 Label modifyProduct_id_label = new Label("id:");
		 modifyProduct_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyProduct_content.add(modifyProduct_id_label);
		 GridPane.setConstraints(modifyProduct_id_label, 0, 0);
		 
		 modifyProduct_id_t = new TextField();
		 modifyProduct_content.add(modifyProduct_id_t);
		 modifyProduct_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyProduct_id_t, 1, 0);
		 Label modifyProduct_name_label = new Label("name:");
		 modifyProduct_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyProduct_content.add(modifyProduct_name_label);
		 GridPane.setConstraints(modifyProduct_name_label, 0, 1);
		 
		 modifyProduct_name_t = new TextField();
		 modifyProduct_content.add(modifyProduct_name_t);
		 modifyProduct_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyProduct_name_t, 1, 1);
		 Label modifyProduct_price_label = new Label("price:");
		 modifyProduct_price_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyProduct_content.add(modifyProduct_price_label);
		 GridPane.setConstraints(modifyProduct_price_label, 0, 2);
		 
		 modifyProduct_price_t = new TextField();
		 modifyProduct_content.add(modifyProduct_price_t);
		 modifyProduct_price_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyProduct_price_t, 1, 2);
		 operationPanels.put("modifyProduct", modifyProduct);
		 
		 // ==================== GridPane_deleteProduct ====================
		 GridPane deleteProduct = new GridPane();
		 deleteProduct.setHgap(4);
		 deleteProduct.setVgap(6);
		 deleteProduct.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteProduct_content = deleteProduct.getChildren();
		 Label deleteProduct_id_label = new Label("id:");
		 deleteProduct_id_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteProduct_content.add(deleteProduct_id_label);
		 GridPane.setConstraints(deleteProduct_id_label, 0, 0);
		 
		 deleteProduct_id_t = new TextField();
		 deleteProduct_content.add(deleteProduct_id_t);
		 deleteProduct_id_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteProduct_id_t, 1, 0);
		 operationPanels.put("deleteProduct", deleteProduct);
		 
		 // ==================== GridPane_manageItemsPrices ====================
		 GridPane manageItemsPrices = new GridPane();
		 manageItemsPrices.setHgap(4);
		 manageItemsPrices.setVgap(6);
		 manageItemsPrices.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> manageItemsPrices_content = manageItemsPrices.getChildren();
		 Label manageItemsPrices_label = new Label("This operation is no intput parameters..");
		 manageItemsPrices_label.setMinWidth(Region.USE_PREF_SIZE);
		 manageItemsPrices_content.add(manageItemsPrices_label);
		 GridPane.setConstraints(manageItemsPrices_label, 0, 0);
		 operationPanels.put("manageItemsPrices", manageItemsPrices);
		 
	}	

	public void actorTreeViewBinding() {
		
		TreeItem<String> treeRootsalesstaff = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_salesProcessing = new TreeItem<String>("salesProcessing");
			subTreeRoot_salesProcessing.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("makeNewOrder"),
					 	new TreeItem<String>("addProduct"),
					 	new TreeItem<String>("generateContract"),
					 	new TreeItem<String>("authorization"),
					 	new TreeItem<String>("generateOrder")
				 		));	
		
		treeRootsalesstaff.getChildren().addAll(Arrays.asList(
			subTreeRoot_salesProcessing,
			new TreeItem<String>("deliveryNotification"),
			new TreeItem<String>("exchangeNotification")
			));
		
		treeRootsalesstaff.setExpanded(true);

		actor_treeview_salesstaff.setShowRoot(false);
		actor_treeview_salesstaff.setRoot(treeRootsalesstaff);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_salesstaff.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootfinancialstaff = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_manageInvoice = new TreeItem<String>("manageInvoice");
			subTreeRoot_manageInvoice.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createInvoice"),
				 	new TreeItem<String>("queryInvoice"),
				 	new TreeItem<String>("modifyInvoice"),
				 	new TreeItem<String>("deleteInvoice")
				 	));
				
		
		treeRootfinancialstaff.getChildren().addAll(Arrays.asList(
			new TreeItem<String>("manageItemsPrices"),
			subTreeRoot_manageInvoice,
			new TreeItem<String>("postingOfAccount"),
			new TreeItem<String>("orderTerminationAndSettlement"),
			new TreeItem<String>("contractTerminationAndSettlement")
			));
		
		treeRootfinancialstaff.setExpanded(true);

		actor_treeview_financialstaff.setShowRoot(false);
		actor_treeview_financialstaff.setRoot(treeRootfinancialstaff);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_financialstaff.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootdepartmentmanager = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_manageClient = new TreeItem<String>("manageClient");
			subTreeRoot_manageClient.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createClient"),
				 	new TreeItem<String>("queryClient"),
				 	new TreeItem<String>("modifyClient"),
				 	new TreeItem<String>("deleteClient")
				 	));
				
		
		treeRootdepartmentmanager.getChildren().addAll(Arrays.asList(
			subTreeRoot_manageClient,
			new TreeItem<String>("salesCommissionManagement"),
			new TreeItem<String>("salesPlanManagement")
			));
		
		treeRootdepartmentmanager.setExpanded(true);

		actor_treeview_departmentmanager.setShowRoot(false);
		actor_treeview_departmentmanager.setRoot(treeRootdepartmentmanager);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_departmentmanager.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootadministrator = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_manageOrder = new TreeItem<String>("manageOrder");
			subTreeRoot_manageOrder.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createOrder"),
				 	new TreeItem<String>("queryOrder"),
				 	new TreeItem<String>("modifyOrder"),
				 	new TreeItem<String>("deleteOrder")
				 	));
				
			TreeItem<String> subTreeRoot_manageContract = new TreeItem<String>("manageContract");
			subTreeRoot_manageContract.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createContracts"),
				 	new TreeItem<String>("queryContracts"),
				 	new TreeItem<String>("modifyContracts"),
				 	new TreeItem<String>("deleteContracts")
				 	));
				
			TreeItem<String> subTreeRoot_manageProduct = new TreeItem<String>("manageProduct");
			subTreeRoot_manageProduct.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createProduct"),
				 	new TreeItem<String>("queryProduct"),
				 	new TreeItem<String>("modifyProduct"),
				 	new TreeItem<String>("deleteProduct")
				 	));
				
		
		treeRootadministrator.getChildren().addAll(Arrays.asList(
			subTreeRoot_manageOrder,
			subTreeRoot_manageContract,
			subTreeRoot_manageProduct
			));
		
		treeRootadministrator.setExpanded(true);

		actor_treeview_administrator.setShowRoot(false);
		actor_treeview_administrator.setRoot(treeRootadministrator);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_administrator.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
	}

	/**
	*    Execute Operation
	*/
	@FXML
	public void execute(ActionEvent event) {
		
		switch (clickedOp) {
		case "makeNewOrder" : makeNewOrder(); break;
		case "addProduct" : addProduct(); break;
		case "generateContract" : generateContract(); break;
		case "generateOrder" : generateOrder(); break;
		case "salesPlanManagement" : salesPlanManagement(); break;
		case "deliveryNotification" : deliveryNotification(); break;
		case "exchangeNotification" : exchangeNotification(); break;
		case "itemsPricesMaintenance" : itemsPricesMaintenance(); break;
		case "postingOfAccount" : postingOfAccount(); break;
		case "invoiceManagement" : invoiceManagement(); break;
		case "contractTerminationAndSettlement" : contractTerminationAndSettlement(); break;
		case "orderTerminationAndSettlement" : orderTerminationAndSettlement(); break;
		case "salesCommissionManagement" : salesCommissionManagement(); break;
		case "authorization" : authorization(); break;
		case "authorizationProcessing" : authorizationProcessing(); break;
		case "createContracts" : createContracts(); break;
		case "queryContracts" : queryContracts(); break;
		case "modifyContracts" : modifyContracts(); break;
		case "deleteContracts" : deleteContracts(); break;
		case "createInvoice" : createInvoice(); break;
		case "queryInvoice" : queryInvoice(); break;
		case "modifyInvoice" : modifyInvoice(); break;
		case "deleteInvoice" : deleteInvoice(); break;
		case "createBillOfLading" : createBillOfLading(); break;
		case "queryBillOfLading" : queryBillOfLading(); break;
		case "modifyBillOfLading" : modifyBillOfLading(); break;
		case "deleteBillOfLading" : deleteBillOfLading(); break;
		case "createClient" : createClient(); break;
		case "queryClient" : queryClient(); break;
		case "modifyClient" : modifyClient(); break;
		case "deleteClient" : deleteClient(); break;
		case "createOrder" : createOrder(); break;
		case "queryOrder" : queryOrder(); break;
		case "modifyOrder" : modifyOrder(); break;
		case "deleteOrder" : deleteOrder(); break;
		case "createProduct" : createProduct(); break;
		case "queryProduct" : queryProduct(); break;
		case "modifyProduct" : modifyProduct(); break;
		case "deleteProduct" : deleteProduct(); break;
		case "manageItemsPrices" : manageItemsPrices(); break;
		
		}
		
		System.out.println("execute buttion clicked");
		
		//checking relevant invariants
		opInvairantPanelUpdate();
	}

	/**
	*    Refresh All
	*/		
	@FXML
	public void refresh(ActionEvent event) {
		
		refreshAll();
		System.out.println("refresh all");
	}		
	
	/**
	*    Save All
	*/			
	@FXML
	public void save(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save State to File");
		fileChooser.setInitialFileName("*.state");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			System.out.println("save state to file " + file.getAbsolutePath());				
			EntityManager.save(file);
		}
	}
	
	/**
	*    Load All
	*/			
	@FXML
	public void load(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open State File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			System.out.println("choose file" + file.getAbsolutePath());
			EntityManager.load(file); 
		}
		
		//refresh GUI after load data
		refreshAll();
	}
	
	
	//precondition unsat dialog
	public void preconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Precondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}
	
	//postcondition unsat dialog
	public void postconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Postcondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}

	public void thirdpartyServiceUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("third party service is exception");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}		
	
	
	public void makeNewOrder() {
		
		System.out.println("execute makeNewOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: makeNewOrder in service: SalesProcessingService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesprocessingservice_service.makeNewOrder(
			Integer.valueOf(makeNewOrder_buyId_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void addProduct() {
		
		System.out.println("execute addProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: addProduct in service: SalesProcessingService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesprocessingservice_service.addProduct(
			Integer.valueOf(addProduct_id_t.getText()),
			Integer.valueOf(addProduct_quantity_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void generateContract() {
		
		System.out.println("execute generateContract");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: generateContract in service: SalesProcessingService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesprocessingservice_service.generateContract(
			generateContract_packing_t.getText(),
			LocalDate.parse(generateContract_dateOfShipment_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			generateContract_portOfShipment_t.getText(),
			generateContract_portOfDestination_t.getText(),
			generateContract_insurance_t.getText(),
			LocalDate.parse(generateContract_effectiveDate_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void generateOrder() {
		
		System.out.println("execute generateOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: generateOrder in service: SalesProcessingService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesprocessingservice_service.generateOrder(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void salesPlanManagement() {
		
		System.out.println("execute salesPlanManagement");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: salesPlanManagement in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.salesPlanManagement(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deliveryNotification() {
		
		System.out.println("execute deliveryNotification");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deliveryNotification in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.deliveryNotification(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void exchangeNotification() {
		
		System.out.println("execute exchangeNotification");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: exchangeNotification in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.exchangeNotification(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void itemsPricesMaintenance() {
		
		System.out.println("execute itemsPricesMaintenance");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: itemsPricesMaintenance in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.itemsPricesMaintenance(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void postingOfAccount() {
		
		System.out.println("execute postingOfAccount");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: postingOfAccount in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.postingOfAccount(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void invoiceManagement() {
		
		System.out.println("execute invoiceManagement");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: invoiceManagement in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.invoiceManagement(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void contractTerminationAndSettlement() {
		
		System.out.println("execute contractTerminationAndSettlement");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: contractTerminationAndSettlement in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.contractTerminationAndSettlement(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void orderTerminationAndSettlement() {
		
		System.out.println("execute orderTerminationAndSettlement");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: orderTerminationAndSettlement in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.orderTerminationAndSettlement(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void salesCommissionManagement() {
		
		System.out.println("execute salesCommissionManagement");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: salesCommissionManagement in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.salesCommissionManagement(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void authorization() {
		
		System.out.println("execute authorization");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: authorization in service: SalesProcessingService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesprocessingservice_service.authorization(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void authorizationProcessing() {
		
		System.out.println("execute authorizationProcessing");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: authorizationProcessing in service: ThirdPartyServices ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(thirdpartyservices_service.authorizationProcessing(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createContracts() {
		
		System.out.println("execute createContracts");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createContracts in service: ManageContractsCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managecontractscrudservice_service.createContracts(
			Integer.valueOf(createContracts_id_t.getText()),
			createContracts_buyer_t.getText(),
			createContracts_packing_t.getText(),
			LocalDate.parse(createContracts_dateofshipment_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			createContracts_portofshipment_t.getText(),
			createContracts_portofdestination_t.getText(),
			createContracts_insurance_t.getText(),
			LocalDate.parse(createContracts_effectivedate_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryContracts() {
		
		System.out.println("execute queryContracts");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryContracts in service: ManageContractsCRUDService ");
		
		try {
			//invoke op with parameters
				Contracts r = managecontractscrudservice_service.queryContracts(
				Integer.valueOf(queryContracts_id_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableContracts = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableContracts_Id = new TableColumn<Map<String, String>, String>("Id");
				tableContracts_Id.setMinWidth("Id".length()*10);
				tableContracts_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_Id);
				TableColumn<Map<String, String>, String> tableContracts_Buyer = new TableColumn<Map<String, String>, String>("Buyer");
				tableContracts_Buyer.setMinWidth("Buyer".length()*10);
				tableContracts_Buyer.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Buyer"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_Buyer);
				TableColumn<Map<String, String>, String> tableContracts_Packing = new TableColumn<Map<String, String>, String>("Packing");
				tableContracts_Packing.setMinWidth("Packing".length()*10);
				tableContracts_Packing.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Packing"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_Packing);
				TableColumn<Map<String, String>, String> tableContracts_DateOfShipment = new TableColumn<Map<String, String>, String>("DateOfShipment");
				tableContracts_DateOfShipment.setMinWidth("DateOfShipment".length()*10);
				tableContracts_DateOfShipment.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("DateOfShipment"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_DateOfShipment);
				TableColumn<Map<String, String>, String> tableContracts_PortOfShipment = new TableColumn<Map<String, String>, String>("PortOfShipment");
				tableContracts_PortOfShipment.setMinWidth("PortOfShipment".length()*10);
				tableContracts_PortOfShipment.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("PortOfShipment"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_PortOfShipment);
				TableColumn<Map<String, String>, String> tableContracts_PortOfDestination = new TableColumn<Map<String, String>, String>("PortOfDestination");
				tableContracts_PortOfDestination.setMinWidth("PortOfDestination".length()*10);
				tableContracts_PortOfDestination.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("PortOfDestination"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_PortOfDestination);
				TableColumn<Map<String, String>, String> tableContracts_Insurance = new TableColumn<Map<String, String>, String>("Insurance");
				tableContracts_Insurance.setMinWidth("Insurance".length()*10);
				tableContracts_Insurance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Insurance"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_Insurance);
				TableColumn<Map<String, String>, String> tableContracts_EffectiveDate = new TableColumn<Map<String, String>, String>("EffectiveDate");
				tableContracts_EffectiveDate.setMinWidth("EffectiveDate".length()*10);
				tableContracts_EffectiveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("EffectiveDate"));
				    }
				});	
				tableContracts.getColumns().add(tableContracts_EffectiveDate);
				
				ObservableList<Map<String, String>> dataContracts = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					if (r.getBuyer() != null)
						unit.put("Buyer", String.valueOf(r.getBuyer()));
					else
						unit.put("Buyer", "");
					if (r.getPacking() != null)
						unit.put("Packing", String.valueOf(r.getPacking()));
					else
						unit.put("Packing", "");
					if (r.getDateOfShipment() != null)
						unit.put("DateOfShipment", r.getDateOfShipment().format(dateformatter));
					else
						unit.put("DateOfShipment", "");
					if (r.getPortOfShipment() != null)
						unit.put("PortOfShipment", String.valueOf(r.getPortOfShipment()));
					else
						unit.put("PortOfShipment", "");
					if (r.getPortOfDestination() != null)
						unit.put("PortOfDestination", String.valueOf(r.getPortOfDestination()));
					else
						unit.put("PortOfDestination", "");
					if (r.getInsurance() != null)
						unit.put("Insurance", String.valueOf(r.getInsurance()));
					else
						unit.put("Insurance", "");
					if (r.getEffectiveDate() != null)
						unit.put("EffectiveDate", r.getEffectiveDate().format(dateformatter));
					else
						unit.put("EffectiveDate", "");
					dataContracts.add(unit);
				
				
				tableContracts.setItems(dataContracts);
				operation_return_pane.setContent(tableContracts);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyContracts() {
		
		System.out.println("execute modifyContracts");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyContracts in service: ManageContractsCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managecontractscrudservice_service.modifyContracts(
			Integer.valueOf(modifyContracts_id_t.getText()),
			modifyContracts_buyer_t.getText(),
			modifyContracts_packing_t.getText(),
			LocalDate.parse(modifyContracts_dateofshipment_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			modifyContracts_portofshipment_t.getText(),
			modifyContracts_portofdestination_t.getText(),
			modifyContracts_insurance_t.getText(),
			LocalDate.parse(modifyContracts_effectivedate_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteContracts() {
		
		System.out.println("execute deleteContracts");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteContracts in service: ManageContractsCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managecontractscrudservice_service.deleteContracts(
			Integer.valueOf(deleteContracts_id_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createInvoice() {
		
		System.out.println("execute createInvoice");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createInvoice in service: ManageInvoiceCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageinvoicecrudservice_service.createInvoice(
			Integer.valueOf(createInvoice_id_t.getText()),
			createInvoice_title_t.getText(),
			LocalDate.parse(createInvoice_effecitvedate_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			Float.valueOf(createInvoice_amount_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryInvoice() {
		
		System.out.println("execute queryInvoice");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryInvoice in service: ManageInvoiceCRUDService ");
		
		try {
			//invoke op with parameters
				Invoice r = manageinvoicecrudservice_service.queryInvoice(
				Integer.valueOf(queryInvoice_id_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableInvoice = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableInvoice_Id = new TableColumn<Map<String, String>, String>("Id");
				tableInvoice_Id.setMinWidth("Id".length()*10);
				tableInvoice_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableInvoice.getColumns().add(tableInvoice_Id);
				TableColumn<Map<String, String>, String> tableInvoice_Title = new TableColumn<Map<String, String>, String>("Title");
				tableInvoice_Title.setMinWidth("Title".length()*10);
				tableInvoice_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
				    }
				});	
				tableInvoice.getColumns().add(tableInvoice_Title);
				TableColumn<Map<String, String>, String> tableInvoice_EffecitveDate = new TableColumn<Map<String, String>, String>("EffecitveDate");
				tableInvoice_EffecitveDate.setMinWidth("EffecitveDate".length()*10);
				tableInvoice_EffecitveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("EffecitveDate"));
				    }
				});	
				tableInvoice.getColumns().add(tableInvoice_EffecitveDate);
				TableColumn<Map<String, String>, String> tableInvoice_Amount = new TableColumn<Map<String, String>, String>("Amount");
				tableInvoice_Amount.setMinWidth("Amount".length()*10);
				tableInvoice_Amount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Amount"));
				    }
				});	
				tableInvoice.getColumns().add(tableInvoice_Amount);
				
				ObservableList<Map<String, String>> dataInvoice = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					if (r.getTitle() != null)
						unit.put("Title", String.valueOf(r.getTitle()));
					else
						unit.put("Title", "");
					if (r.getEffecitveDate() != null)
						unit.put("EffecitveDate", r.getEffecitveDate().format(dateformatter));
					else
						unit.put("EffecitveDate", "");
					unit.put("Amount", String.valueOf(r.getAmount()));
					dataInvoice.add(unit);
				
				
				tableInvoice.setItems(dataInvoice);
				operation_return_pane.setContent(tableInvoice);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyInvoice() {
		
		System.out.println("execute modifyInvoice");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyInvoice in service: ManageInvoiceCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageinvoicecrudservice_service.modifyInvoice(
			Integer.valueOf(modifyInvoice_id_t.getText()),
			modifyInvoice_title_t.getText(),
			LocalDate.parse(modifyInvoice_effecitvedate_t.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))						,
			Float.valueOf(modifyInvoice_amount_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteInvoice() {
		
		System.out.println("execute deleteInvoice");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteInvoice in service: ManageInvoiceCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageinvoicecrudservice_service.deleteInvoice(
			Integer.valueOf(deleteInvoice_id_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createBillOfLading() {
		
		System.out.println("execute createBillOfLading");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createBillOfLading in service: ManageBillOfLadingCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebillofladingcrudservice_service.createBillOfLading(
			Integer.valueOf(createBillOfLading_id_t.getText()),
			createBillOfLading_consignee_t.getText(),
			createBillOfLading_commoditylist_t.getText(),
			createBillOfLading_contractobject_t.getText(),
			Float.valueOf(createBillOfLading_quantity_t.getText()),
			Float.valueOf(createBillOfLading_totalprice_t.getText()),
			createBillOfLading_deadlineforperformance_t.getText(),
			createBillOfLading_locationforperformance_t.getText(),
			createBillOfLading_methodforperformance_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryBillOfLading() {
		
		System.out.println("execute queryBillOfLading");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryBillOfLading in service: ManageBillOfLadingCRUDService ");
		
		try {
			//invoke op with parameters
				BillOfLading r = managebillofladingcrudservice_service.queryBillOfLading(
				Integer.valueOf(queryBillOfLading_id_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableBillOfLading = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableBillOfLading_Id = new TableColumn<Map<String, String>, String>("Id");
				tableBillOfLading_Id.setMinWidth("Id".length()*10);
				tableBillOfLading_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_Id);
				TableColumn<Map<String, String>, String> tableBillOfLading_Consignee = new TableColumn<Map<String, String>, String>("Consignee");
				tableBillOfLading_Consignee.setMinWidth("Consignee".length()*10);
				tableBillOfLading_Consignee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Consignee"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_Consignee);
				TableColumn<Map<String, String>, String> tableBillOfLading_CommodityList = new TableColumn<Map<String, String>, String>("CommodityList");
				tableBillOfLading_CommodityList.setMinWidth("CommodityList".length()*10);
				tableBillOfLading_CommodityList.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("CommodityList"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_CommodityList);
				TableColumn<Map<String, String>, String> tableBillOfLading_ContractObject = new TableColumn<Map<String, String>, String>("ContractObject");
				tableBillOfLading_ContractObject.setMinWidth("ContractObject".length()*10);
				tableBillOfLading_ContractObject.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("ContractObject"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_ContractObject);
				TableColumn<Map<String, String>, String> tableBillOfLading_Quantity = new TableColumn<Map<String, String>, String>("Quantity");
				tableBillOfLading_Quantity.setMinWidth("Quantity".length()*10);
				tableBillOfLading_Quantity.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Quantity"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_Quantity);
				TableColumn<Map<String, String>, String> tableBillOfLading_TotalPrice = new TableColumn<Map<String, String>, String>("TotalPrice");
				tableBillOfLading_TotalPrice.setMinWidth("TotalPrice".length()*10);
				tableBillOfLading_TotalPrice.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("TotalPrice"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_TotalPrice);
				TableColumn<Map<String, String>, String> tableBillOfLading_DeadlineForPerformance = new TableColumn<Map<String, String>, String>("DeadlineForPerformance");
				tableBillOfLading_DeadlineForPerformance.setMinWidth("DeadlineForPerformance".length()*10);
				tableBillOfLading_DeadlineForPerformance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("DeadlineForPerformance"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_DeadlineForPerformance);
				TableColumn<Map<String, String>, String> tableBillOfLading_LocationForPerformance = new TableColumn<Map<String, String>, String>("LocationForPerformance");
				tableBillOfLading_LocationForPerformance.setMinWidth("LocationForPerformance".length()*10);
				tableBillOfLading_LocationForPerformance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("LocationForPerformance"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_LocationForPerformance);
				TableColumn<Map<String, String>, String> tableBillOfLading_MethodForPerformance = new TableColumn<Map<String, String>, String>("MethodForPerformance");
				tableBillOfLading_MethodForPerformance.setMinWidth("MethodForPerformance".length()*10);
				tableBillOfLading_MethodForPerformance.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("MethodForPerformance"));
				    }
				});	
				tableBillOfLading.getColumns().add(tableBillOfLading_MethodForPerformance);
				
				ObservableList<Map<String, String>> dataBillOfLading = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					if (r.getConsignee() != null)
						unit.put("Consignee", String.valueOf(r.getConsignee()));
					else
						unit.put("Consignee", "");
					if (r.getCommodityList() != null)
						unit.put("CommodityList", String.valueOf(r.getCommodityList()));
					else
						unit.put("CommodityList", "");
					if (r.getContractObject() != null)
						unit.put("ContractObject", String.valueOf(r.getContractObject()));
					else
						unit.put("ContractObject", "");
					unit.put("Quantity", String.valueOf(r.getQuantity()));
					unit.put("TotalPrice", String.valueOf(r.getTotalPrice()));
					if (r.getDeadlineForPerformance() != null)
						unit.put("DeadlineForPerformance", String.valueOf(r.getDeadlineForPerformance()));
					else
						unit.put("DeadlineForPerformance", "");
					if (r.getLocationForPerformance() != null)
						unit.put("LocationForPerformance", String.valueOf(r.getLocationForPerformance()));
					else
						unit.put("LocationForPerformance", "");
					if (r.getMethodForPerformance() != null)
						unit.put("MethodForPerformance", String.valueOf(r.getMethodForPerformance()));
					else
						unit.put("MethodForPerformance", "");
					dataBillOfLading.add(unit);
				
				
				tableBillOfLading.setItems(dataBillOfLading);
				operation_return_pane.setContent(tableBillOfLading);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyBillOfLading() {
		
		System.out.println("execute modifyBillOfLading");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyBillOfLading in service: ManageBillOfLadingCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebillofladingcrudservice_service.modifyBillOfLading(
			Integer.valueOf(modifyBillOfLading_id_t.getText()),
			modifyBillOfLading_consignee_t.getText(),
			modifyBillOfLading_commoditylist_t.getText(),
			modifyBillOfLading_contractobject_t.getText(),
			Float.valueOf(modifyBillOfLading_quantity_t.getText()),
			Float.valueOf(modifyBillOfLading_totalprice_t.getText()),
			modifyBillOfLading_deadlineforperformance_t.getText(),
			modifyBillOfLading_locationforperformance_t.getText(),
			modifyBillOfLading_methodforperformance_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteBillOfLading() {
		
		System.out.println("execute deleteBillOfLading");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteBillOfLading in service: ManageBillOfLadingCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebillofladingcrudservice_service.deleteBillOfLading(
			Integer.valueOf(deleteBillOfLading_id_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createClient() {
		
		System.out.println("execute createClient");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createClient in service: ManageClientCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageclientcrudservice_service.createClient(
			Integer.valueOf(createClient_id_t.getText()),
			createClient_name_t.getText(),
			createClient_address_t.getText(),
			createClient_contact_t.getText(),
			createClient_phonenumber_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryClient() {
		
		System.out.println("execute queryClient");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryClient in service: ManageClientCRUDService ");
		
		try {
			//invoke op with parameters
				Client r = manageclientcrudservice_service.queryClient(
				Integer.valueOf(queryClient_id_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableClient = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableClient_Id = new TableColumn<Map<String, String>, String>("Id");
				tableClient_Id.setMinWidth("Id".length()*10);
				tableClient_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableClient.getColumns().add(tableClient_Id);
				TableColumn<Map<String, String>, String> tableClient_Name = new TableColumn<Map<String, String>, String>("Name");
				tableClient_Name.setMinWidth("Name".length()*10);
				tableClient_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableClient.getColumns().add(tableClient_Name);
				TableColumn<Map<String, String>, String> tableClient_Address = new TableColumn<Map<String, String>, String>("Address");
				tableClient_Address.setMinWidth("Address".length()*10);
				tableClient_Address.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Address"));
				    }
				});	
				tableClient.getColumns().add(tableClient_Address);
				TableColumn<Map<String, String>, String> tableClient_Contact = new TableColumn<Map<String, String>, String>("Contact");
				tableClient_Contact.setMinWidth("Contact".length()*10);
				tableClient_Contact.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Contact"));
				    }
				});	
				tableClient.getColumns().add(tableClient_Contact);
				TableColumn<Map<String, String>, String> tableClient_PhoneNumber = new TableColumn<Map<String, String>, String>("PhoneNumber");
				tableClient_PhoneNumber.setMinWidth("PhoneNumber".length()*10);
				tableClient_PhoneNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("PhoneNumber"));
				    }
				});	
				tableClient.getColumns().add(tableClient_PhoneNumber);
				
				ObservableList<Map<String, String>> dataClient = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					if (r.getAddress() != null)
						unit.put("Address", String.valueOf(r.getAddress()));
					else
						unit.put("Address", "");
					if (r.getContact() != null)
						unit.put("Contact", String.valueOf(r.getContact()));
					else
						unit.put("Contact", "");
					if (r.getPhoneNumber() != null)
						unit.put("PhoneNumber", String.valueOf(r.getPhoneNumber()));
					else
						unit.put("PhoneNumber", "");
					dataClient.add(unit);
				
				
				tableClient.setItems(dataClient);
				operation_return_pane.setContent(tableClient);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyClient() {
		
		System.out.println("execute modifyClient");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyClient in service: ManageClientCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageclientcrudservice_service.modifyClient(
			Integer.valueOf(modifyClient_id_t.getText()),
			modifyClient_name_t.getText(),
			modifyClient_address_t.getText(),
			modifyClient_contact_t.getText(),
			modifyClient_phonenumber_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteClient() {
		
		System.out.println("execute deleteClient");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteClient in service: ManageClientCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageclientcrudservice_service.deleteClient(
			Integer.valueOf(deleteClient_id_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createOrder() {
		
		System.out.println("execute createOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageordercrudservice_service.createOrder(
			Integer.valueOf(createOrder_id_t.getText()),
			Boolean.valueOf(createOrder_iscompleted_t.getText()),
			createOrder_paymentinformation_t.getText(),
			Float.valueOf(createOrder_amount_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryOrder() {
		
		System.out.println("execute queryOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
				Order r = manageordercrudservice_service.queryOrder(
				Integer.valueOf(queryOrder_id_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableOrder = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableOrder_Id = new TableColumn<Map<String, String>, String>("Id");
				tableOrder_Id.setMinWidth("Id".length()*10);
				tableOrder_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_Id);
				TableColumn<Map<String, String>, String> tableOrder_IsCompleted = new TableColumn<Map<String, String>, String>("IsCompleted");
				tableOrder_IsCompleted.setMinWidth("IsCompleted".length()*10);
				tableOrder_IsCompleted.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("IsCompleted"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_IsCompleted);
				TableColumn<Map<String, String>, String> tableOrder_PaymentInformation = new TableColumn<Map<String, String>, String>("PaymentInformation");
				tableOrder_PaymentInformation.setMinWidth("PaymentInformation".length()*10);
				tableOrder_PaymentInformation.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("PaymentInformation"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_PaymentInformation);
				TableColumn<Map<String, String>, String> tableOrder_Amount = new TableColumn<Map<String, String>, String>("Amount");
				tableOrder_Amount.setMinWidth("Amount".length()*10);
				tableOrder_Amount.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Amount"));
				    }
				});	
				tableOrder.getColumns().add(tableOrder_Amount);
				
				ObservableList<Map<String, String>> dataOrder = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					unit.put("IsCompleted", String.valueOf(r.getIsCompleted()));
					if (r.getPaymentInformation() != null)
						unit.put("PaymentInformation", String.valueOf(r.getPaymentInformation()));
					else
						unit.put("PaymentInformation", "");
					unit.put("Amount", String.valueOf(r.getAmount()));
					dataOrder.add(unit);
				
				
				tableOrder.setItems(dataOrder);
				operation_return_pane.setContent(tableOrder);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyOrder() {
		
		System.out.println("execute modifyOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageordercrudservice_service.modifyOrder(
			Integer.valueOf(modifyOrder_id_t.getText()),
			Boolean.valueOf(modifyOrder_iscompleted_t.getText()),
			modifyOrder_paymentinformation_t.getText(),
			Float.valueOf(modifyOrder_amount_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteOrder() {
		
		System.out.println("execute deleteOrder");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteOrder in service: ManageOrderCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageordercrudservice_service.deleteOrder(
			Integer.valueOf(deleteOrder_id_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createProduct() {
		
		System.out.println("execute createProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createProduct in service: ManageProductCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageproductcrudservice_service.createProduct(
			Integer.valueOf(createProduct_id_t.getText()),
			createProduct_name_t.getText(),
			Float.valueOf(createProduct_price_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryProduct() {
		
		System.out.println("execute queryProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryProduct in service: ManageProductCRUDService ");
		
		try {
			//invoke op with parameters
				Product r = manageproductcrudservice_service.queryProduct(
				Integer.valueOf(queryProduct_id_t.getText())
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableProduct = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableProduct_Id = new TableColumn<Map<String, String>, String>("Id");
				tableProduct_Id.setMinWidth("Id".length()*10);
				tableProduct_Id.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Id"));
				    }
				});	
				tableProduct.getColumns().add(tableProduct_Id);
				TableColumn<Map<String, String>, String> tableProduct_Name = new TableColumn<Map<String, String>, String>("Name");
				tableProduct_Name.setMinWidth("Name".length()*10);
				tableProduct_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableProduct.getColumns().add(tableProduct_Name);
				TableColumn<Map<String, String>, String> tableProduct_Price = new TableColumn<Map<String, String>, String>("Price");
				tableProduct_Price.setMinWidth("Price".length()*10);
				tableProduct_Price.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Price"));
				    }
				});	
				tableProduct.getColumns().add(tableProduct_Price);
				
				ObservableList<Map<String, String>> dataProduct = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					unit.put("Id", String.valueOf(r.getId()));
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					unit.put("Price", String.valueOf(r.getPrice()));
					dataProduct.add(unit);
				
				
				tableProduct.setItems(dataProduct);
				operation_return_pane.setContent(tableProduct);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyProduct() {
		
		System.out.println("execute modifyProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyProduct in service: ManageProductCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageproductcrudservice_service.modifyProduct(
			Integer.valueOf(modifyProduct_id_t.getText()),
			modifyProduct_name_t.getText(),
			Float.valueOf(modifyProduct_price_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteProduct() {
		
		System.out.println("execute deleteProduct");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteProduct in service: ManageProductCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageproductcrudservice_service.deleteProduct(
			Integer.valueOf(deleteProduct_id_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void manageItemsPrices() {
		
		System.out.println("execute manageItemsPrices");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: manageItemsPrices in service: SalesManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(salesmanagementsystemsystem_service.manageItemsPrices(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}




	//select object index
	int objectindex;
	
	@FXML
	TabPane mainPane;

	@FXML
	TextArea log;
	
	@FXML
	TreeView<String> actor_treeview_salesstaff;
	@FXML
	TreeView<String> actor_treeview_financialstaff;
	@FXML
	TreeView<String> actor_treeview_departmentmanager;
	@FXML
	TreeView<String> actor_treeview_administrator;

	@FXML
	TextArea definition;
	@FXML
	TextArea precondition;
	@FXML
	TextArea postcondition;
	@FXML
	TextArea invariants;
	
	@FXML
	TitledPane precondition_pane;
	@FXML
	TitledPane postcondition_pane;
	
	//chosen operation textfields
	List<TextField> choosenOperation;
	String clickedOp;
		
	@FXML
	TableView<ClassInfo> class_statisic;
	@FXML
	TableView<AssociationInfo> association_statisic;
	
	Map<String, ObservableList<AssociationInfo>> allassociationData;
	ObservableList<ClassInfo> classInfodata;
	
	SalesManagementSystemSystem salesmanagementsystemsystem_service;
	ThirdPartyServices thirdpartyservices_service;
	ManageContractsCRUDService managecontractscrudservice_service;
	ManageClientCRUDService manageclientcrudservice_service;
	ManageOrderCRUDService manageordercrudservice_service;
	ManageInvoiceCRUDService manageinvoicecrudservice_service;
	ManageBillOfLadingCRUDService managebillofladingcrudservice_service;
	ManageProductCRUDService manageproductcrudservice_service;
	SalesProcessingService salesprocessingservice_service;
	
	ClassInfo contracts;
	ClassInfo client;
	ClassInfo order;
	ClassInfo invoice;
	ClassInfo billoflading;
	ClassInfo deliverynotification;
	ClassInfo exchangenotification;
	ClassInfo orderterm;
	ClassInfo clientgroup;
	ClassInfo deliveryterm;
	ClassInfo product;
	ClassInfo orderlineproduct;
		
	@FXML
	TitledPane object_statics;
	Map<String, TableView> allObjectTables;
	
	@FXML
	TitledPane operation_paras;
	
	@FXML
	TitledPane operation_return_pane;
	
	@FXML
	TitledPane all_invariant_pane;
	
	@FXML
	TitledPane invariants_panes;
	
	Map<String, GridPane> operationPanels;
	Map<String, VBox> opInvariantPanel;
	
	//all textfiled or eumntity
	TextField makeNewOrder_buyId_t;
	TextField addProduct_id_t;
	TextField addProduct_quantity_t;
	TextField generateContract_packing_t;
	TextField generateContract_dateOfShipment_t;
	TextField generateContract_portOfShipment_t;
	TextField generateContract_portOfDestination_t;
	TextField generateContract_insurance_t;
	TextField generateContract_effectiveDate_t;
	TextField createContracts_id_t;
	TextField createContracts_buyer_t;
	TextField createContracts_packing_t;
	TextField createContracts_dateofshipment_t;
	TextField createContracts_portofshipment_t;
	TextField createContracts_portofdestination_t;
	TextField createContracts_insurance_t;
	TextField createContracts_effectivedate_t;
	TextField queryContracts_id_t;
	TextField modifyContracts_id_t;
	TextField modifyContracts_buyer_t;
	TextField modifyContracts_packing_t;
	TextField modifyContracts_dateofshipment_t;
	TextField modifyContracts_portofshipment_t;
	TextField modifyContracts_portofdestination_t;
	TextField modifyContracts_insurance_t;
	TextField modifyContracts_effectivedate_t;
	TextField deleteContracts_id_t;
	TextField createInvoice_id_t;
	TextField createInvoice_title_t;
	TextField createInvoice_effecitvedate_t;
	TextField createInvoice_amount_t;
	TextField queryInvoice_id_t;
	TextField modifyInvoice_id_t;
	TextField modifyInvoice_title_t;
	TextField modifyInvoice_effecitvedate_t;
	TextField modifyInvoice_amount_t;
	TextField deleteInvoice_id_t;
	TextField createBillOfLading_id_t;
	TextField createBillOfLading_consignee_t;
	TextField createBillOfLading_commoditylist_t;
	TextField createBillOfLading_contractobject_t;
	TextField createBillOfLading_quantity_t;
	TextField createBillOfLading_totalprice_t;
	TextField createBillOfLading_deadlineforperformance_t;
	TextField createBillOfLading_locationforperformance_t;
	TextField createBillOfLading_methodforperformance_t;
	TextField queryBillOfLading_id_t;
	TextField modifyBillOfLading_id_t;
	TextField modifyBillOfLading_consignee_t;
	TextField modifyBillOfLading_commoditylist_t;
	TextField modifyBillOfLading_contractobject_t;
	TextField modifyBillOfLading_quantity_t;
	TextField modifyBillOfLading_totalprice_t;
	TextField modifyBillOfLading_deadlineforperformance_t;
	TextField modifyBillOfLading_locationforperformance_t;
	TextField modifyBillOfLading_methodforperformance_t;
	TextField deleteBillOfLading_id_t;
	TextField createClient_id_t;
	TextField createClient_name_t;
	TextField createClient_address_t;
	TextField createClient_contact_t;
	TextField createClient_phonenumber_t;
	TextField queryClient_id_t;
	TextField modifyClient_id_t;
	TextField modifyClient_name_t;
	TextField modifyClient_address_t;
	TextField modifyClient_contact_t;
	TextField modifyClient_phonenumber_t;
	TextField deleteClient_id_t;
	TextField createOrder_id_t;
	TextField createOrder_iscompleted_t;
	TextField createOrder_paymentinformation_t;
	TextField createOrder_amount_t;
	TextField queryOrder_id_t;
	TextField modifyOrder_id_t;
	TextField modifyOrder_iscompleted_t;
	TextField modifyOrder_paymentinformation_t;
	TextField modifyOrder_amount_t;
	TextField deleteOrder_id_t;
	TextField createProduct_id_t;
	TextField createProduct_name_t;
	TextField createProduct_price_t;
	TextField queryProduct_id_t;
	TextField modifyProduct_id_t;
	TextField modifyProduct_name_t;
	TextField modifyProduct_price_t;
	TextField deleteProduct_id_t;
	
	HashMap<String, String> definitions_map;
	HashMap<String, String> preconditions_map;
	HashMap<String, String> postconditions_map;
	HashMap<String, String> invariants_map;
	LinkedHashMap<String, String> service_invariants_map;
	LinkedHashMap<String, String> entity_invariants_map;
	LinkedHashMap<String, Label> service_invariants_label_map;
	LinkedHashMap<String, Label> entity_invariants_label_map;
	LinkedHashMap<String, Label> op_entity_invariants_label_map;
	LinkedHashMap<String, Label> op_service_invariants_label_map;
	

	
}
