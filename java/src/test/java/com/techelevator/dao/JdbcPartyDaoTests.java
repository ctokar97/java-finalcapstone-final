package com.techelevator.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.dao.JdbcDao.JdbcPartyDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Party;
import com.techelevator.services.MappingServices.PartyMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    sut = new JdbcPartyDao(jdbcTemplate, partyMapper);
}

@Test
public void getParty_byName_returns_given_PartyName () {
    Party actualParty = sut.getPartyByName(PARTY_1.getParty_name());
    Assert.assertEquals(PARTY_1.getParty_name(),actualParty.getParty_name());
}
@Test
public void getPartyName_given_null_throw_exception() {
    sut.getPartyByName(null);
}

    @Test
    public void getAllParties() {
        List<Party> parties = sut.getAllParties();


    }

}
