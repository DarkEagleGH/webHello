package test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;

public class ContactRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findInRange() throws Exception {

    }

    @Test
    public void getCount() throws Exception {
        long cnt = jdbcTemplate.queryForObject("SELECT count (*) FROM contacts", Long.class);
        assertTrue(cnt>0);
    }

}