package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageDeliveryMethodCRUDService {

	/* all system operations of the use case*/
	boolean createDeliveryMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	DeliveryMethod queryDeliveryMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyDeliveryMethod(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteDeliveryMethod(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
