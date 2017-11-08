package com.snail.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import com.snail.dao.UserDao;
import com.snail.model.User;

/**
 *
 * @author snail
 * @version 1.0.0
 *
 */

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<User> findAll() {
		return jdbcTemplate.execute("select * from user", new PreparedStatementCallback<List<User>>() {
			@Override
			public List<User> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				List<User> results = new ArrayList<User>();
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getLong("id"));
					user.setAge(rs.getInt("age"));
					user.setName(rs.getString("name"));
					results.add(user);
				}
				return results;
			}
		});
	}

	@Override
	public User findOne(Long id) {
		return jdbcTemplate.execute("select * from user where id = ?", new PreparedStatementCallback<User>() {
			@Override
			public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getLong("id"));
					user.setAge(rs.getInt("age"));
					user.setName(rs.getString("name"));
					return user;
				}
				return null;
			}
		});
	}

	@Override
	public User findByName(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		return jdbcTemplate.execute("delete from user where id = ?", new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				try {
					ps.setLong(1, id);
					ps.execute();
					return true;
				} catch (SQLException e) {
					return false;
				}

			}

		});
	}

}
