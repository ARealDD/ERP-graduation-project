package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageInvoiceCRUDService {

	/* all system operations of the use case*/
	boolean createInvoice(int id, String title, LocalDate effecitvedate, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Invoice queryInvoice(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyInvoice(int id, String title, LocalDate effecitvedate, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteInvoice(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
