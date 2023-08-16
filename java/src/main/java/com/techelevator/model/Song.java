package com.techelevator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Song {

	private int song_id;
	private String song_name;
	private String artist;
	private String genre;
	private String user_genre;
	private String spotify_id;
	private int votes;
}
