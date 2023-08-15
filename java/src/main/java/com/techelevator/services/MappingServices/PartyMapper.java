package com.techelevator.services.MappingServices;

import com.techelevator.model.Party;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class PartyMapper {

	/**
	 * Maps a row from a SQL result set to a Party object.
	 *
	 * @param rowset the result set containing the row data
	 * @return the Party object mapped from the row
	 */
	public Party mapRowToParty(SqlRowSet rowset) {
		Party party = new Party();
		party.setId(rowset.getInt("party_id"));
		party.setParty_name(rowset.getString("party_name"));
		party.setTheme(rowset.getString("theme"));
		party.setParty_owner(rowset.getInt("party_owner"));
		return party;
	}
}
