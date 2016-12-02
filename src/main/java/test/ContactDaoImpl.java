package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class ContactDaoImpl implements ContactDao {
        private DataSource dataSource;
        private JdbcTemplate jdbcTemplate;
        private NamedParameterJdbcTemplate namedTemplate;

        public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        }

        private RowMapper<Contact> rowMapper = new RowMapper<Contact>() {
            @Override
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
                Contact contact = new Contact();
                contact.setId(rs.getLong("id"));
                contact.setName(rs.getString("name"));
                return contact;
            }
        };

        @Override
        public List<Contact> getAll() {
            return jdbcTemplate.query("SELECT * FROM contacts", rowMapper);
        }

        @Override
        public List<Contact> findWithFilter(ContactFilter filter) {
//            if (filter.isEmpty()) {
//                return getAll();
//            }
            String sql = "SELECT * FROM contacts LIMIT 1000";
            if (filter.getName() != null) {
            }
            BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(filter);
            return namedTemplate.query(sql, namedParameters, rowMapper);
        }

        private PreparedStatementSetter getPreparedStatementSetter(final Contact contact) {
            return new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 0;
                    ps.setLong(++i, contact.getId());
                    ps.setString(++i, contact.getName());
                }
            };
        }

    }
