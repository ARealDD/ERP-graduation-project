package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface SalesProcessingService {

	/* all system operations of the use case*/
	boolean makeNewOrder(int buyId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean addProduct(int id, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean generateContract(String packing, LocalDate dateOfShipment, String portOfShipment, String portOfDestination, String insurance, LocalDate effectiveDate) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean authorization() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean generateOrder() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	OrderLineProduct getCurrentOrderLine();
	void setCurrentOrderLine(OrderLineProduct currentorderline);
	Order getCurrentOrder();
	void setCurrentOrder(Order currentorder);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
