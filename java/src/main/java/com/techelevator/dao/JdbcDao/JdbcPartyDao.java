package com.techelevator.dao.JdbcDao;

import com.techelevator.dao.DaoInterface.PartyDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
import com.techelevator.model.Playlist;
import com.techelevator.model.User;
import com.techelevator.services.MappingServices.PartyMapper;
import com.techelevator.services.MappingServices.PlaylistMapper;
import com.techelevator.services.MappingServices.UserMapper;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPartyDao implements PartyDao {

	private final JdbcTemplate jdbcTemplate;
	private final PartyMapper partyMapper;
	private final UserMapper userMapper;
	private final PlaylistMapper playlistMapper;

	public JdbcPartyDao(JdbcTemplate jdbcTemplate, PartyMapper partyMapper , UserMapper userMapper, PlaylistMapper playlistMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.partyMapper = partyMapper;
		this.userMapper = userMapper;
		this.playlistMapper = playlistMapper;
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
		String sql = "SELECT party_id, party_name, party_owner, theme, emoji FROM party";
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

	@Override
	public Party getPartyById(int partyId) {
		Party party = new Party();
		String sql = "SELECT party_id, party_name, party_owner, theme, emoji FROM party WHERE party_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
			if (results.next()) {
				party = partyMapper.mapRowToParty(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return party;
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
		String sql = "SELECT party_id, party_name, party_owner, theme, emoji FROM party WHERE party_name = ?";
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
	 * Retrieves a list of User objects who belong to a specific party.
	 *
	 * @param partyId the ID of the party
	 * @return a list of User objects representing the users in the party
	 * @throws DaoException if there is an issue with the database connection
	 */
	@Override
	public List<User> getUsersInParty(int partyId) {
		List<User> users = new ArrayList<>();
		User user = new User();
		String sql = "SELECT users.user_id, users.username, users.password_hash, users.role FROM users INNER JOIN user_party ON users.user_id = user_party.user_id WHERE user_party.party_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
			while (results.next()) {
				user = userMapper.mapRowToUser(results);
				users.add(user);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database");
		}
		return users;
	}

	/**
	 * Retrieves the playlist associated with a given party ID.
	 *
	 * @param partyId the ID of the party
	 * @return the playlist associated with the given party ID
	 * @throws DaoException if unable to connect to the server or database
	 */
	@Override
	public Playlist getPlaylistInParty(int partyId) {
		Playlist playlist = new Playlist();
		String sql = "SELECT playlist_id, playlist_name, party_id FROM playlist WHERE party_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
			if (results.next()) {
				playlist = playlistMapper.mapRowToPlaylist(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return playlist;
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
		newParty.setParty_owner(party.getParty_owner());
		newParty.setTheme(party.getTheme());

		String sql = "INSERT INTO party (party_name, party_owner, theme) VALUES (? , ? , ?)";
		try {
			jdbcTemplate.update(sql, newParty.getParty_name(), newParty.getParty_owner(), newParty.getTheme());
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
		newParty.setEmoji(party.getEmoji());
		String sql = "UPDATE party SET party_name = ?, emoji = ? WHERE party_id = ?";
		try {
			jdbcTemplate.update(sql, newParty.getParty_name(), newParty.getEmoji(), newParty.getId());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return newParty;
	}

	@Override
	public Party assignPartyToUser(int partyId, int userId) {
		String sql = "UPDATE party SET party_owner = ? WHERE party_id = ?";
		int updatedRows = 0;
		try {
			updatedRows = jdbcTemplate.update(sql, userId, partyId);
			if ( updatedRows == 0 ) {
				throw new DaoException("Update failed: No such party id found");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return getPartyById(partyId);
	}
}
