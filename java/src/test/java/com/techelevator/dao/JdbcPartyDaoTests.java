package com.techelevator.dao;

import com.techelevator.dao.JdbcDao.JdbcPartyDao;
import com.techelevator.model.Party;
import com.techelevator.services.MappingServices.PartyMapper;
import com.techelevator.services.MappingServices.PlaylistMapper;
import com.techelevator.services.MappingServices.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;


import java.time.LocalDate;
import java.util.List;
public class JdbcPartyDaoTests extends BaseDaoTests {

    public static final Party PARTY_1 = new Party(1,"party1");
    public static final Party PARTY_2 = new Party(2,"party2");
    public static final Party PARTY_3 = new Party(3,"party3");
    public static final Party PARTY_4 = new Party(4,"party4");
    public static final Party PARTY_5 = new Party(5,"party5");

    private JdbcPartyDao sut;

    @Before
    public  void setup() {
        PartyMapper partyMapper = new PartyMapper();
        UserMapper userMapper = new UserMapper();
        PlaylistMapper playlistMapper = new PlaylistMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcPartyDao(jdbcTemplate, partyMapper, userMapper, playlistMapper);
    }

    @Test
    public void getParty_byName_returns_given_PartyName () {
        Party actualParty1 = sut.getPartyByName(PARTY_1.getParty_name());
        Assert.assertEquals(PARTY_1.getParty_name(),actualParty1.getParty_name());

        Party actualParty2 = sut.getPartyByName(PARTY_2.getParty_name());
        Assert.assertEquals(PARTY_2.getParty_name(),actualParty2.getParty_name());

        Party actualParty5 = sut.getPartyByName(PARTY_5.getParty_name());
        Assert.assertEquals(PARTY_5.getParty_name(),actualParty5.getParty_name());
    }
    @Test
    public void getPartyName_given_null_throw_exception() {
        sut.getPartyByName(null);
    }

    @Test
    public void getAllParties() {
        List<Party> parties = sut.getAllParties();

        Assert.assertEquals(5, parties.size());

        assertPartyMatch(PARTY_1, parties.get(0));
        assertPartyMatch(PARTY_2, parties.get(1));
        assertPartyMatch(PARTY_3, parties.get(2));
        assertPartyMatch(PARTY_4, parties.get(3));
        assertPartyMatch(PARTY_5, parties.get(4));
    }

    @Test
    public void createParty_returns_party_with_id_and_expected_values() {
        Party newParty = new Party(6, "party6");

        Party createdParty = sut.createParty(newParty);

        Assert.assertTrue(createdParty.getId() > 0);

        Party retrievedParty = sut.getPartyByName(createdParty.getParty_name());

        assertPartyMatch(retrievedParty, createdParty);

        newParty.setParty_name(createdParty.getParty_name());
        assertPartyMatch(newParty, createdParty);
    }

    @Test
    public void updated_party_has_expected_values_when_retrieved() {
        Party partyToUpdate = sut.getPartyByName("party2");

        partyToUpdate.setParty_name(partyToUpdate.getParty_name() + " Jam");

        Party updatedParty = sut.updateParty(partyToUpdate);

        assertPartyMatch(partyToUpdate, updatedParty);
    }
    private void assertPartyMatch(Party expected, Party actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getParty_name(), actual.getParty_name());
    }

}
