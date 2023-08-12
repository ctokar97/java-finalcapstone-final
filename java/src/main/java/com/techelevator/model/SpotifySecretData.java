package com.techelevator.model;

public enum SpotifySecretData {

	INSTANCE;

	private final String clientId = "de60d1063e5d40fcb739f8ecffd5c245";
	private final String clientSecret = "6b6eb0e23eba49f49d54a5e4dc49e000";

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}
