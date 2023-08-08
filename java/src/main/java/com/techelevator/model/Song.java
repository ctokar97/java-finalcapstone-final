package com.techelevator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Song {

	private int song_id;
	private String song_name;
	private String artist;
	private String genre;
	private String user_genre;
}
