package com.techelevator.dao;

import com.techelevator.dao.JdbcDao.JdbcSongDao;
import com.techelevator.model.Song;
import com.techelevator.services.MappingServices.SongMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;


public class JdbcSongDaoTests extends BaseDaoTests {

    public static final Song SONG_1 = new Song(1, "song1", "artist1", "genre1", "user_genre1");
    public static final Song SONG_2 = new Song(2, "song2", "artist2", "genre2", "user_genre2");
    public static final Song SONG_3 = new Song(3, "song3", "artist3", "genre3", "user_genre3");
    public static final Song SONG_4 = new Song(4, "song4", "artist4", "genre4", "user_genre4");
    public static final Song SONG_5 = new Song(5, "song5", "artist5", "genre5", "user_genre5");
    private JdbcSongDao sut;

    @Before
    public  void setup() {
        SongMapper songMapper = new SongMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcSongDao(jdbcTemplate, songMapper);
    }
    @Test
    public void get_all_songs() {
        List<Song> songs = sut.getAllSongs();

        Assert.assertEquals(5, songs.size());

        assertSongMatch(SONG_1, songs.get(0));
        assertSongMatch(SONG_2, songs.get(1));
        assertSongMatch(SONG_3, songs.get(2));
        assertSongMatch(SONG_4, songs.get(3));
        assertSongMatch(SONG_5, songs.get(4));
    }

    @Test
    public void get_song_by_name_returns_given_song_Name () {
        Song actualSong1 = sut.getSongByName(SONG_1.getSong_name());
        Assert.assertEquals(SONG_1.getSong_name(),actualSong1.getSong_name());

        Song actualParty2 = sut.getSongByName(SONG_2.getSong_name());
        Assert.assertEquals(SONG_2.getSong_name(),actualParty2.getSong_name());

        Song actualParty5 = sut.getSongByName(SONG_5.getSong_name());
        Assert.assertEquals(SONG_5.getSong_name(),actualParty5.getSong_name());
    }
    @Test
    public void get_song_name_given_null_throw_exception() {
        sut.getSongByName(null);
    }


    @Test
    public void create_song_returns_party_with_id_and_expected_values() {
        Song newSong = new Song(6, "song6", "artist6", "genre6", "user_genre6");

        Song createdSong = sut.createSong(newSong);

        Assert.assertTrue(createdSong.getSong_id() > 0);

        Song retrievedSong = sut.getSongByName(createdSong.getSong_name());

        assertSongMatch(retrievedSong, createdSong);

        newSong.setSong_name(createdSong.getSong_name());
        assertSongMatch(newSong, createdSong);
    }

    /* @Test
     public void updated_genre_has_expected_values_when_retrieved() {
         Song genreToUpdate = sut.getSongByName("party2");

         genreToUpdate.setGenre("Jam");

         Song updatedSong = sut.updateGenre(genreToUpdate);

         assertSongMatch(genreToUpdate, updatedSong);
     }*/
    private void assertSongMatch(Song expected, Song actual) {
        Assert.assertEquals(expected.getSong_id(), actual.getSong_id());
        Assert.assertEquals(expected.getSong_name(), actual.getSong_name());
        Assert.assertEquals(expected.getArtist(), actual.getArtist());
        Assert.assertEquals(expected.getGenre(), actual.getGenre());
        Assert.assertEquals(expected.getUser_genre(), actual.getUser_genre());
    }

}