package com.techelevator.dao.JdbcDao;

import com.techelevator.dao.DaoInterface.RequestDao;
import com.techelevator.model.Request;
import com.techelevator.model.Song;
import com.techelevator.services.MappingServices.RequestListMapper;
import com.techelevator.services.MappingServices.SongMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRequestDao implements RequestDao {

	private final JdbcTemplate jdbcTemplate;
	private final RequestListMapper requestListMapper;
	private final SongMapper songMapper;

	public JdbcRequestDao(JdbcTemplate jdbcTemplate, RequestListMapper requestListMapper, SongMapper songMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.requestListMapper = requestListMapper;
		this.songMapper = songMapper;
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

	@Override
	public Song getSongByRequestId(int requestId) {
		Song song = new Song();
		String sql = "SELECT song.song_id, song.song_name, song.artist, song.genre, song.user_genre, song.spotify_id, song.votes FROM song " +
				"JOIN request_list ON song.song_id = request_list.song_id " +
				"WHERE request_list.id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, requestId);
			while (results.next()) {
				song = songMapper.mapRowToSong(results);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return song;
	}

	@Override
	public Request getRequestBySongId(int songId) {
		Request request = new Request();
		String sql = "SELECT id, party_id, song_id FROM request_list WHERE song_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, songId);
			if (results.next()) {
				request = requestListMapper.mapRowToRequestList(results);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return request;
	}

	/**
	 * Creates a new request with the given request list.
	 *
	 * @param request The request object representing the details of the request.
	 * @return The created request object.
	 */
	@Override
	public Request createRequest(Request request) {
		Request createdRequest;
		String sql = "INSERT INTO request_list (party_id, song_id) VALUES (?, ?)";
		try {
			jdbcTemplate.update(sql, request.getParty_id(), request.getSong_id());
			createdRequest = getRequestBySongId(request.getSong_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return createdRequest;
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
