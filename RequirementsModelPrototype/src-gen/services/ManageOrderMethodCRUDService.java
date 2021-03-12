package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageOrderMethodCRUDService {

	/* all system operations of the use case*/
	boolean createOrderMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	OrderMethod queryOrderMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyOrderMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteOrderMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
