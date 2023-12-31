package com.techelevator.controller;

import com.techelevator.dao.DaoInterface.RequestDao;
import com.techelevator.model.Request;
import com.techelevator.model.Song;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RequestController {

	private final RequestDao requestDao;

	public RequestController(RequestDao requestDao) {
		this.requestDao = requestDao;
	}

	@GetMapping
	public ResponseEntity<List<Request>> getAllRequests() {
		List<Request> requests;
		try {
			requests = requestDao.getAllRequests();
			if ( requests.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(requests);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}


	@GetMapping("/{partyId}")
	public ResponseEntity<List<Request>> getRequestsByPartyId(@PathVariable int partyId) {
		List<Request> requests;
		try {
			requests = requestDao.getRequestsByPartyId(partyId);
			if ( requests.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(requests);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{requestId}/songs")
	public ResponseEntity<Song> getSongByRequestId(@PathVariable int requestId) {
		Song song = null;
		try {
			song = requestDao.getSongByRequestId(requestId);
			if ( song == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(song);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping
	public ResponseEntity<Request> createRequest(@RequestBody Request request) {
		Request createdRequest;
		try {
			createdRequest = requestDao.createRequest(request);
			return ResponseEntity.created(URI.create("/request/" + request.getId())).body(createdRequest);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
		try {
			requestDao.deleteRequest(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
