package com.techelevator.dao.DaoInterface;

import com.techelevator.model.Party;
import com.techelevator.model.Playlist;
import com.techelevator.model.User;

import java.util.List;

public interface PartyDao {

	List<Party> getAllParties();

	Party getPartyByName(String partyName);

	List<User> getUsersInParty(int partyId);

	Playlist getPlaylistInParty(int partyId);

	Party createParty(Party party);

	Party updateParty(Party party);

	Party assignPartyToUser(int partyId, int userId);

	Party getPartyById(int partyId);

	Party addUserToParty(int partyId, int userId);
}
