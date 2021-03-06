package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ExchangeProcessingService {

	/* all system operations of the use case*/
	boolean typeChoice() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean cancelOrder() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean regenerateBillOfLading() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean regenerateNotification() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
