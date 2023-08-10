package com.techelevator.dao;

import com.techelevator.dao.DaoInterface.PlaylistDao;
import com.techelevator.dao.JdbcDao.JdbcPlaylistDao;
import com.techelevator.model.Playlist;
import com.techelevator.model.Song;
import com.techelevator.model.User;
import com.techelevator.services.MappingServices.PlaylistMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;

import java.util.List;

public class JbdcPlaylistDaoTests extends BaseDaoTests {

    public static final Playlist PLAYLIST_1 = new Playlist(1, 1, "playlist1");
    public static final Playlist PLAYLIST_2 = new Playlist(2, 2, "playlist2");
    public static final Playlist PLAYLIST_3 = new Playlist(3, 3, "playlist3");
    public static final Playlist PLAYLIST_4 = new Playlist(4, 1, "playlist4");
    public static final Playlist PLAYLIST_5 = new Playlist(5, 2, "playlist5");


    private JdbcPlaylistDao sut;

    @Before
    public  void setup() {
        PlaylistMapper playlistMapper = new PlaylistMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcPlaylistDao(jdbcTemplate, playlistMapper);
    }


    @Test
    public void getPlaylistByPlaylistName_given_invalid_PlaylistName_returns_null() {
        Assert.assertNull(sut.getPlaylistByName("invalid"));
    }

    @Test
    public void getPlaylistByPlaylistName_given_valid_playlist_returns_playlist() {
        Playlist actualPlaylist1 = sut.getPlaylistByName(PLAYLIST_1.getPlaylist_name());
        Playlist actualPlaylist3 = sut.getPlaylistByName(PLAYLIST_3.getPlaylist_name());


        Assert.assertEquals(PLAYLIST_1.getPlaylist_name(), actualPlaylist1.getPlaylist_name());
        Assert.assertEquals(PLAYLIST_3.getPlaylist_name(), actualPlaylist3.getPlaylist_name());
    }

    @Test
    public void create_playlist_returns_playlist_with_id_and_expected_values() {
        Playlist newPlaylist = new Playlist(6,1,"playlist6");

        Playlist createdPlaylist = sut.createPlaylist(newPlaylist);

        Assert.assertTrue(createdPlaylist.getPlaylist_id() > 0);

        Playlist retrievedPlaylist = sut.getPlaylistByName(createdPlaylist.getPlaylist_name());

        assertPlaylistMatch(retrievedPlaylist, createdPlaylist);

        newPlaylist.setPlaylist_name(createdPlaylist.getPlaylist_name());
        assertPlaylistMatch(newPlaylist, createdPlaylist);
    }


//    @Test
//    public void updated_playlist_has_expected_values_when_retrieved() {
//        Playlist playlistToUpdate = sut.updatePlaylist(1);
//        timeSheetToUpdate.setBillable(false);
//        timeSheetToUpdate.setDescription("Updated Sheet");
//        dao.updateTimesheet(timeSheetToUpdate);
//
//
//        Timesheet timesheetActual = dao.getTimesheetById(1);
//        assertTimesheetsMatch(timeSheetToUpdate, timesheetActual);
//
//    }
//

    private void assertPlaylistMatch(Playlist expected, Playlist actual) {
        Assert.assertEquals(expected.getPlaylist_id(), actual.getPlaylist_id());
        Assert.assertEquals(expected.getParty_id(), actual.getParty_id());
        Assert.assertEquals(expected.getPlaylist_name(), actual.getPlaylist_name());
    }


}
