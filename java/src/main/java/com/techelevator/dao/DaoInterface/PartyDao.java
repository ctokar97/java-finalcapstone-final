package com.techelevator.dao.DaoInterface;

import com.techelevator.model.Party;

import java.util.List;

public interface PartyDao {

	List<Party> getAllParties();

	Party getPartyByName(String partyName);

	Party createParty(Party party);

	Party updateParty(Party party);
}
