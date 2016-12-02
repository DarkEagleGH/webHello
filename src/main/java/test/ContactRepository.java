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
        return jdbcTemplate.query("select * from contacts LIMIT 100", new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public List<Contact> findWithLimit(long limit) {
        return jdbcTemplate.query("select * from contacts LIMIT 100", new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public List<Contact> findWithFilter(ContactFilter filter) {
        return jdbcTemplate.query("select * from contacts", new UserRowMapper());
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