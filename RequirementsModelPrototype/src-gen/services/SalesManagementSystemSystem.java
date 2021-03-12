package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface SalesManagementSystemSystem {

	/* all system operations of the use case*/
	boolean salesPlanManagement() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean manageItemsPrices() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean postingOfAccount() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean salesCommissionManagement() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	
	/* invariant checking function */
}
