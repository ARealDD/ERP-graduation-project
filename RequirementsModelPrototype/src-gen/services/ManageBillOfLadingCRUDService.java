package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageBillOfLadingCRUDService {

	/* all system operations of the use case*/
	boolean createBillOfLading(int id, String consignee, String commoditylist, String contractobject, float quantity, float totalprice, String deadlineforperformance, String locationforperformance, String methodforperformance) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	BillOfLading queryBillOfLading(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyBillOfLading(int id, String consignee, String commoditylist, String contractobject, float quantity, float totalprice, String deadlineforperformance, String locationforperformance, String methodforperformance) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteBillOfLading(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
