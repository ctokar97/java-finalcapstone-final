package com.techelevator.dao;

import com.techelevator.dao.JdbcDao.JdbcPlaylistDao;
import com.techelevator.model.Playlist;
import com.techelevator.services.MappingServices.PlaylistMapper;
import com.techelevator.services.MappingServices.SongMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcPlaylistDaoTests extends BaseDaoTests {
        public static final Playlist PLAYLIST_1 = new Playlist(1,1,"playlist1");
        public static final Playlist PLAYLIST_2 = new Playlist(2,2,"playlist2");
        public static final Playlist PLAYLIST_3 = new Playlist(3,3,"playlist3");
        public static final Playlist PLAYLIST_4 = new Playlist(4,4,"playlist4");
        public static final Playlist PLAYLIST_5 = new Playlist(5,5,"playlist5");

        private JdbcPlaylistDao sut;

        @Before
        public  void setup() {
            PlaylistMapper playlistMapper = new PlaylistMapper();
                SongMapper songMapper = new SongMapper();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            sut = new JdbcPlaylistDao(jdbcTemplate, playlistMapper, songMapper);
        }

        @Test
        public void get_All_Playlist() {
        List<Playlist> playlists = sut.getAllPlaylists();

        Assert.assertEquals(5, playlists.size());

        assertPlaylistMatch(PLAYLIST_1, playlists.get(0));
        assertPlaylistMatch(PLAYLIST_2, playlists.get(1));
        assertPlaylistMatch(PLAYLIST_3, playlists.get(2));
        assertPlaylistMatch(PLAYLIST_4, playlists.get(3));
        assertPlaylistMatch(PLAYLIST_5, playlists.get(4));
        }
        @Test
        public void getPlaylist_byName_returns_given_PartyName () {
                Playlist actualPlaylist1 = sut.getPlaylistByName(PLAYLIST_1.getPlaylist_name());
                Assert.assertEquals(PLAYLIST_1.getPlaylist_name(),actualPlaylist1.getPlaylist_name());

                Playlist actualPlaylist5 = sut.getPlaylistByName(PLAYLIST_5.getPlaylist_name());
                Assert.assertEquals(PLAYLIST_5.getPlaylist_name(),actualPlaylist5.getPlaylist_name());
        }

        @Test
        public void create_playlist () {
                Playlist newPlaylist = new Playlist(6, 5,"playlist6");

                Playlist createdPlaylist = sut.createPlaylist(newPlaylist);

                Assert.assertTrue(createdPlaylist.getPlaylist_id() > 0);

                Playlist retrievedPlaylist = sut.getPlaylistByName(createdPlaylist.getPlaylist_name());

                assertPlaylistMatch(retrievedPlaylist, createdPlaylist);

                newPlaylist.setPlaylist_name(createdPlaylist.getPlaylist_name());
                assertPlaylistMatch(newPlaylist, createdPlaylist);
        }

        @Test
        public void update_playlist() {
                Playlist playlistToUpdate = sut.getPlaylistByName("playlist2");

                playlistToUpdate.setPlaylist_name(playlistToUpdate.getPlaylist_name() + " Jam");

                Playlist updatedPlaylist = sut.updatePlaylist(playlistToUpdate);

                assertPlaylistMatch(playlistToUpdate, updatedPlaylist);
        }

        private void assertPlaylistMatch(Playlist expected, Playlist actual) {
        Assert.assertEquals(expected.getPlaylist_id(),actual.getPlaylist_id());
        Assert.assertEquals(expected.getParty_id(), actual.getParty_id());
        Assert.assertEquals(expected.getPlaylist_name(), actual.getPlaylist_name());
        }



}
