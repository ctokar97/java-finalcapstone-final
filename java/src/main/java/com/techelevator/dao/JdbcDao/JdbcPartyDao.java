package com.techelevator.dao.JdbcDao;

import com.techelevator.dao.DaoInterface.PartyDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
import com.techelevator.services.MappingServices.PartyMapper;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;


public class JdbcPartyDao implements PartyDao {

	private final JdbcTemplate jdbcTemplate;
	private final PartyMapper partyMapper;


	public JdbcPartyDao(JdbcTemplate jdbcTemplate, PartyMapper partyMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.partyMapper = partyMapper;
	}



	/**
	 * Retrieves all parties from the database.
	 *
	 * @return A list of Party objects representing all parties in the database.
	 * @throws DaoException If there is an error connecting to the database.
	 */
	@Override
	public List<Party> getAllParties() {
		List<Party> parties = new ArrayList<>();
		Party party = new Party();
		String sql = "SELECT party_id, party_name FROM party";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				party = partyMapper.mapRowToParty(results);
				parties.add(party);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return parties;
	}

	/**
	 * Retrieves a Party object from the database based on the given party name.
	 *
	 * @param partyName the name of the party
	 * @return a Party object representing the party with the given name, or an empty Party object if no party is found
	 * @throws DaoException if there is an issue with the database connection
	 */
	@Override
	public Party getPartyByName(String partyName) {
		Party party = new Party();
		String sql = "SELECT party_id, party_name FROM party WHERE party_name = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyName);
			if (results.next()) {
				party = partyMapper.mapRowToParty(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return party;
	}

	/**
	 * Creates a new party in the database.
	 *
	 * @param party the party object containing the party name to be created
	 * @return the newly created party object
	 * @throws DaoException if unable to connect to the database
	 */
	@Override
	public Party createParty(Party party) {
		Party newParty = new Party();
		newParty.setParty_name(party.getParty_name());

		String sql = "INSERT INTO party (party_name) VALUES (?)";
		try {
			jdbcTemplate.update(sql, newParty.getParty_name());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return getPartyByName(newParty.getParty_name());
	}

	/**
	 * Updates a party in the database with the specified party object.
	 *
	 * @param party the party object to update
	 * @return the updated party object
	 * @throws DaoException if there is an error connecting to the database
	 */
	@Override
	public Party updateParty(Party party) {
		Party newParty = new Party();
		newParty.setParty_name(party.getParty_name());
		newParty.setId(party.getId());
		String sql = "UPDATE party SET party_name = ? WHERE party_id = ?";
		try {
			jdbcTemplate.update(sql, newParty.getParty_name(), newParty.getId());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return newParty;
	}
}
