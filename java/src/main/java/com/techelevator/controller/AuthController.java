package com.techelevator.controller;

import com.techelevator.model.SpotifySecretData;
import org.apache.hc.core5.http.ParseException;
import org.openqa.selenium.devtools.v85.io.IO;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:9000/api/get-user-code");
	private String code = "";

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			.setClientId(SpotifySecretData.INSTANCE.getClientId())
			.setClientSecret(SpotifySecretData.INSTANCE.getClientSecret())
			.setRedirectUri(redirectUri)
			.build();


	@GetMapping("login")
	@ResponseBody
	public String spotifyLogin() {
		AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
				.scope("user-read-private, user-read-email, user-top-read")
				.show_dialog(true)
				.build();

		final URI uri = authorizationCodeUriRequest.execute();
		return uri.toString();
	}

	@GetMapping(value = "get-user-code")
	public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
		code = userCode;
		AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
				.build();

		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		response.sendRedirect("http://localhost:8080/");
		return spotifyApi.getAccessToken();
	}

	@GetMapping("/user-top-artists")
	public Artist[] getUserTopArtist() {

		final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
				.time_range("medium_term")
				.limit(10)
				.offset(5)
				.build();

		try {
			final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

			// Return top artists as JSON
			return artistPaging.getItems();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return new Artist[0];
	}

	@GetMapping("/search")
	public Track searchTracks(@RequestParam("q") String query) {
		SearchTracksRequest request = spotifyApi.searchTracks(query)
				.limit(1)  // Limit to top result only
				.offset(0) // Start from the first result
				.build();

		try {
			Paging<Track> trackPaging = request.execute();

			if (trackPaging.getTotal() > 0) {
				Track topTrack = trackPaging.getItems()[0];
				System.out.println("Returning top track: " + topTrack.getName());
				return topTrack;  // Return the top result if results are found
			}
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			e.printStackTrace();
		}

		return null;  // Return null if no tracks found
	}
}
