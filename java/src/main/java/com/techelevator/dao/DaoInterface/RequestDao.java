package com.techelevator.dao.DaoInterface;

import com.techelevator.model.Request;
import com.techelevator.model.Song;

import java.util.List;

public interface RequestDao {

	public List<Request> getAllRequests();
	/**
	 * Retrieves all requests from the system for a given party ID.
	 *
	 * @return A list of Request objects representing all requests in the system for the specified party ID.
	 */
	public List<Request> getRequestsByPartyId(int partyId);

	public Request getRequestBySongId(int songId);

	public Song getSongByRequestId(int requestId);

	public Request createRequest(Request request);

	/**
	 * Deletes a request with the given ID.
	 *
	 * @param id the ID of the request to be deleted
	 */
	public void deleteRequest(int id);

}
