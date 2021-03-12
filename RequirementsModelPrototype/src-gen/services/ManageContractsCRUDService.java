package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageContractsCRUDService {

	/* all system operations of the use case*/
	boolean createContracts(int id, String buyer, String packing, LocalDate dateofshipment, String portofshipment, String portofdestination, String insurance, LocalDate effectivedate) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Contracts queryContracts(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyContracts(int id, String buyer, String packing, LocalDate dateofshipment, String portofshipment, String portofdestination, String insurance, LocalDate effectivedate) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteContracts(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
