package com.techelevator.services.MappingServices;

import com.techelevator.model.Request;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class RequestListMapper {

	public Request mapRowToRequestList(SqlRowSet rowset) {
		Request request = new Request();
		request.setId(rowset.getInt("id"));
		request.setSong_id(rowset.getInt("song_id"));
		request.setParty_id(rowset.getInt("party_id"));
		return request;
	}
}
