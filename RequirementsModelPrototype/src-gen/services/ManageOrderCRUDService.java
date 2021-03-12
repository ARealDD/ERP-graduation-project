package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageOrderCRUDService {

	/* all system operations of the use case*/
	boolean createOrder(int id, boolean iscompleted, String paymentinformation, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Order queryOrder(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyOrder(int id, boolean iscompleted, String paymentinformation, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteOrder(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
