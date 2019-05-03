package org.owl.book.chap11.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 인터페이스 MemberDao의 구현체.
 * 
 * @author Jacob
 */
public class MemberDaoImpl implements MemberDao {

	static final String INSERT = "INSERT member(email, password, name) VALUES(?, sha2(?,256), ?)";

	static final String SELECT_ALL = "SELECT memberId, email, name, left(cdate,19) cdate FROM member ORDER BY memberId desc LIMIT ?,?";

	static final String COUNT_ALL = "SELECT count(memberId) count FROM member";

	JdbcTemplate jdbcTemplate;

	public MemberDaoImpl() throws NamingException {
		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cafe");
		jdbcTemplate = new JdbcTemplate(ds);
	}

	/**
	 * 회원 가입 구현
	 */
	@Override
	public void insert(Member member) {
		jdbcTemplate.update(INSERT, member.getEmail(), member.getPassword(),
				member.getName());
	}

	@Override
	public Member selectByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
	}

	/**
	 * 회원 목록 구현
	 */
	@Override
	public List<Member> selectAll(int offset, int count) {
		return jdbcTemplate.queryForList(SELECT_ALL,
				new Object[] { offset, count }, new RowMapper<>() {
					@Override
					public Member mapRow(ResultSet rs) throws SQLException {
						Member member = new Member();
						member.setMemberId(rs.getString("memberId"));
						member.setEmail(rs.getString("email"));
						member.setName(rs.getString("name"));
						member.setCdate(rs.getString("cdate"));
						return member;
					}
				});
	}

	@Override
	public int countAll() {
		return jdbcTemplate.queryForObject(COUNT_ALL, null, new RowMapper<>() {
			@Override
			public Integer mapRow(ResultSet rs) throws SQLException {
				return rs.getInt("count");
			}
		});
	}
}