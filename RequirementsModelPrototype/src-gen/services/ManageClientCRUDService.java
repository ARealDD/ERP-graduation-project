package services;

import entities.*;
import java.util.List;
import java.time.LocalDate;


public interface ManageClientCRUDService {

	/* all system operations of the use case*/
	boolean createClient(int id, String name, String address, String contact, String phonenumber, int groupId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Client queryClient(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyClient(int id, String name, String address, String contact, String phonenumber, int groupId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteClient(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean createClientGroup() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean queryClientGroup() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyClientGroup() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteClientGroup() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
