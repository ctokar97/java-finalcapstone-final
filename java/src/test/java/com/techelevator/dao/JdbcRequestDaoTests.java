package com.techelevator.dao;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.techelevator.dao.JdbcDao.JdbcPartyDao;
import com.techelevator.dao.JdbcDao.JdbcRequestDao;
import com.techelevator.model.Party;
import com.techelevator.model.Playlist;
import com.techelevator.model.Request;
import com.techelevator.model.User;
import com.techelevator.services.MappingServices.PartyMapper;
import com.techelevator.services.MappingServices.PlaylistMapper;
import com.techelevator.services.MappingServices.RequestListMapper;
import com.techelevator.services.MappingServices.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcRequestDaoTests extends BaseDaoTests {

        public static final Request REQUEST_1 = new Request(1, 1, 1);
        public static final Request REQUEST_2 = new Request(2, 2, 2);
        public static final Request REQUEST_3 = new Request(3, 3, 3);
        public static final Request REQUEST_4 = new Request(4, 4, 4);
        public static final Request REQUEST_5 = new Request(5, 5, 5);

        private JdbcRequestDao sut;

        @Before
        public  void setup() {
            RequestListMapper requestListMapper = new RequestListMapper();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            sut = new JdbcRequestDao(jdbcTemplate, requestListMapper);
        }

        @Test
        public void get_all_requests () {
            List<Request> requests = sut.getAllRequests();

            Assert.assertEquals(5, requests.size());

            assertRequestMatch(REQUEST_1, requests.get(0));
            assertRequestMatch(REQUEST_2, requests.get(1));
            assertRequestMatch(REQUEST_3, requests.get(2));
            assertRequestMatch(REQUEST_4, requests.get(3));
            assertRequestMatch(REQUEST_5, requests.get(4));
        }
        @Test
        public void get_requests_by_party_id () {
            List<Request> request1 = sut.getRequestsByPartyId(1);
            Assert.assertEquals(1, request1.size());
            assertRequestMatch(REQUEST_1, request1.get(0));
        }

        @Test
        public void create_request() {
            Request newRequest = new Request(6, 6, 6);

            List<Request> createdRequest = sut.createRequest(newRequest);

            List<Request> retrievedRequest = sut.getRequestsByPartyId(6);

            newRequest.setId(6);

          /*  Request newRequest = new Request(6, 6, 6);

            Request createdRequest = sut.createRequest(newRequest);

            Assert.assertTrue(createdParty.getId() > 0);

            Party retrievedParty = sut.getPartyByName(createdParty.getParty_name());

            assertPartyMatch(retrievedParty, createdParty);

            newParty.setParty_name(createdParty.getParty_name());
            assertPartyMatch(newParty, createdParty); */
        }

        /*@Test
        public void delete_request () {
            Party newParty = new Party(6, "party6");

            Party createdParty = sut.createParty(newParty);

            Assert.assertTrue(createdParty.getId() > 0);

            Party retrievedParty = sut.getPartyByName(createdParty.getParty_name());

            assertPartyMatch(retrievedParty, createdParty);

            newParty.setParty_name(createdParty.getParty_name());
            assertPartyMatch(newParty, createdParty);
        }*/



    private void assertRequestMatch(Request expected, Request actual) {
        Assert.assertEquals(expected.getId(),actual.getId());
        Assert.assertEquals(expected.getSong_id(), actual.getSong_id());
        Assert.assertEquals(expected.getParty_id(), actual.getParty_id());
    }

    }

