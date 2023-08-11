package com.techelevator.dao.JdbcDao;

import com.techelevator.dao.DaoInterface.RequestDao;
import com.techelevator.model.Request;
import com.techelevator.services.MappingServices.RequestListMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRequestDao implements RequestDao {

	private final JdbcTemplate jdbcTemplate;
	private final RequestListMapper requestListMapper;

	public JdbcRequestDao(JdbcTemplate jdbcTemplate, RequestListMapper requestListMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.requestListMapper = requestListMapper;
	}


	@Override
	public java.util.List<Request> getAllRequests() {
		List<Request> requests = new ArrayList<>();
		Request request;
		String sql = "SELECT id, party_id, song_id FROM request_list";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				request = requestListMapper.mapRowToRequestList(results);
				requests.add(request);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return requests;
	}
	/**
	 * Retrieves all requests from the system for a given party ID.
	 *
	 * @param partyId The ID of the party for which requests should be retrieved.
	 * @return A list of Request objects representing all requests in the system for the specified party ID.
	 */
	@Override
	public List<Request> getRequestsByPartyId(int partyId) {
		List<Request> requests = new ArrayList<>();
		Request request;
		String sql = "SELECT id, party_id, song_id FROM request_list WHERE party_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
			while (results.next()) {
				request = requestListMapper.mapRowToRequestList(results);
				requests.add(request);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return requests;
	}

	/**
	 * Creates a new request with the given request list.
	 *
	 * @param requestList The request list encapsulating the details of the request.
	 * @return The created request.
	 */
	@Override
	public List<Request> createRequest(Request requestList) {
		List<Request> requests = new ArrayList<>();
		String sql = "INSERT INTO request_list (party_id, song_id) VALUES (?, ?)";
		try {
			jdbcTemplate.update(sql, requestList.getParty_id(), requestList.getSong_id());
			requests = getRequestsByPartyId(requestList.getParty_id());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return requests;
	}
	/**
	 * Deletes a request with the given ID.
	 *
	 * @param id the ID of the request to be deleted
	 */
	@Override
	public void deleteRequest(int id) {
		String sql = "DELETE FROM request_list WHERE id = ?";
		try {
			jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
