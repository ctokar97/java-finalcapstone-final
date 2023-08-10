package com.techelevator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Playlist {

	private int playlist_id;
	private int party_id;
	private String playlist_name;
}
