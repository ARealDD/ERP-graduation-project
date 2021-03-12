package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageProductCRUDService {

	/* all system operations of the use case*/
	boolean createProduct(int id, String name, float price) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Product queryProduct(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyProduct(int id, String name, float price) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteProduct(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
