package com.techelevator.dao.DaoInterface;

import com.techelevator.model.Song;

import java.util.List;

public interface SongDao {

	List<Song> getAllSongs();

	Song getSongByName(String songName);

	Song createSong(Song song);

	Song updateGenre(Song song);
}
