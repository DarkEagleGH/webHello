package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class ContactRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<Contact> findAll() {
        return jdbcTemplate.query("SELECT * FROM contacts ORDER BY id", new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public List<Contact> findLast() {
        return jdbcTemplate.query("select * from contacts order by id desc limit 1",
                new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public List<Contact> findInRange(long from, long limit) {
        return jdbcTemplate.query("SELECT * FROM contacts WHERE id > ? ORDER BY id LIMIT ?",
                new Object[]{from,limit},  new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public long getCount() {
        return jdbcTemplate.queryForObject("SELECT count (*) FROM contacts", Long.class);
    }
}

class UserRowMapper implements RowMapper<Contact>
{
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setName(rs.getString("name"));
        return contact;
    }
}