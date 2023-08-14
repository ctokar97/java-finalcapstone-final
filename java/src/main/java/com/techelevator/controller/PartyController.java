package com.techelevator.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.techelevator.dao.DaoInterface.PartyDao;
import com.techelevator.model.Party;
import com.techelevator.model.Playlist;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/party")
public class PartyController {

	private final PartyDao partyDao;
	private static final Logger log = LoggerFactory.getLogger(PartyController.class);

	public PartyController(PartyDao partyDao) {
		this.partyDao = partyDao;
	}

	/**
	 * Retrieves all parties from the partyDao.
	 *
	 * @return A ResponseEntity object containing a List of Party objects retrieved from the partyDao.
	 *         The ResponseEntity will have a status code of 200 (OK) if the parties were successfully retrieved.
	 */
	@GetMapping
	public ResponseEntity<List<Party>> getAllParties() {
		List<Party> parties = partyDao.getAllParties();
		return ResponseEntity.ok(parties);
	}

	/**
	 * Retrieves a party by name
	 *
	 * @param partyName the name of the party to retrieve
	 * @return a ResponseEntity with the retrieved party, or ResponseEntity.notFound() if the party is not found,
	 *         or ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) if an exception occurs
	 */
	@GetMapping("/{partyName}")
	public ResponseEntity<Party> getPartyByName(@PathVariable String partyName) {
		try {
			Party party = partyDao.getPartyByName(partyName);
			if (party == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(party);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Retrieves the users in a party
	 *
	 * @param partyId the ID of the party to retrieve the users from
	 * @return a ResponseEntity with the retrieved users in the party,
	 *         or ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) if an exception occurs
	 */
	@GetMapping("/{partyId}/users")
	public ResponseEntity<List<User>> getUsersInParty(@PathVariable Integer partyId) {
		List<User> users;
		try {
			users = partyDao.getUsersInParty(partyId);
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Retrieves the playlist for a given party.
	 *
	 * @param partyId the ID of the party
	 * @return the playlist for the party
	 * @throws Exception if an error occurs while retrieving the playlist
	 */
	@GetMapping("/{partyId}/playlist")
	public ResponseEntity<Playlist> getPlaylistInParty(@PathVariable Integer partyId) {
		try {
			Playlist playlist = partyDao.getPlaylistInParty(partyId);
			return ResponseEntity.ok(playlist);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Creates a new party.
	 *
	 * @param party The party object to be created.
	 * @param uriBuilder A UriComponentsBuilder for building the URI of the created party.
	 * @return ResponseEntity<Party> The ResponseEntity with status code and the created party object.
	 *         If the party creation is successful, the status code is 201 (Created) with the URI of the created party in the "Location" header.
	 *         If the party creation fails, the status code is 500 (Internal Server Error).
	 */
	@PostMapping
	public ResponseEntity<Party> createParty(@RequestBody Party party, UriComponentsBuilder uriBuilder) {
		log.debug("Creating party: {}", party);
		Party createdParty;
		try {
			createdParty = partyDao.createParty(party);
			log.debug("Party created: {}", createdParty);
			if (createdParty == null) {
				log.error("Created party is null");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			URI uri = uriBuilder.path("/party/{id}").buildAndExpand(createdParty.getId()).toUri();
			return ResponseEntity.created(uri).body(createdParty);
		} catch (Exception e) {
			log.error("Error creating party", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Updates a party with the given partyId.
	 *
	 * @param partyId The ID of the party to update.
	 * @param party   The updated party object.
	 * @return The updated party object if the update was successful.
	 *         Otherwise, returns an HTTP 500 Internal Server Error response.
	 */
	@PutMapping("/{partyId}")
	public ResponseEntity<Party> updateParty(@PathVariable Integer partyId, @RequestBody Party party) {
		Party updatedParty;
		try {
			if(partyId != party.getId()) {
				return ResponseEntity.badRequest().body(null);
			}
			updatedParty = partyDao.updateParty(party);
			return ResponseEntity.ok(updatedParty);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{partyId}/owner/{userId}")
	public ResponseEntity<Party> assignPartyToUser(@PathVariable Integer partyId, @PathVariable Integer userId) {
		Party party;
		try {
			party = partyDao.assignPartyToUser(partyId, userId);
			return ResponseEntity.ok(party);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
