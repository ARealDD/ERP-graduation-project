package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface TradingTerminationAndSettlementService {

	/* all system operations of the use case*/
	boolean contractTerminationAndSettlement() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean orderTerminationAndSettlement() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean payment() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean generateInvoice() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
