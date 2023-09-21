package com.office.library.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminMemberDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean isAdminMember(String a_m_id) {
		String sql = "SELECT COUNT(*) FROM tbl_admin_member "
				+ "WHERE a_m_id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);
		
		if (result > 0)
			return true;
		else
			return false;	
	}
	
	public int insertAdminAccount(AdminMemberVO adminMemberVO) {	
		List<String>args = new ArrayList<String>();
		
		String sql = "INSERT INTO tbl_admin_member(";
					
				if (adminMemberVO.getA_m_id().equals("super admin")) {
					sql += "a_m_approval, ";
					args.add("1");
				}
		
				sql += "a_m_id, ";
				args.add(adminMemberVO.getA_m_id());
				
				sql += "a_m_pw, ";
				args.add(passwordEncoder.encode(adminMemberVO.getA_m_pw()));

				sql += "a_m_name, ";
				args.add(adminMemberVO.getA_m_name());
				
				sql += "a_m_gender, ";
				args.add(adminMemberVO.getA_m_gender());
				
				sql += "a_m_part, ";
				args.add(adminMemberVO.getA_m_part());
				
				sql += "a_m_position, ";
				args.add(adminMemberVO.getA_m_position());

				sql += "a_m_mail, ";
				args.add(adminMemberVO.getA_m_mail());
				
				sql += "a_m_phone, ";
				args.add(adminMemberVO.getA_m_phone());

				sql += "a_m_reg_date, a_m_mod_date) ";

				if (adminMemberVO.getA_m_id().equals("super admin"))
					sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
				else
					sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
				
			int result = -1;
			
			try {
				return jdbcTemplate.update(sql, args.toArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
	}
	
	public AdminMemberVO selectAdmin(AdminMemberVO adminMemberVO) {
		
		String sql = "SELECT * FROM tbl_admin_member "
				+ "WHERE a_m_id = ? AND a_m_approval > 0";
		
		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();
		
		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
				
				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();
					
					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return adminMemberVO;
				}
				
			}, adminMemberVO.getA_m_id());
			
			if (!passwordEncoder.matches(adminMemberVO.getA_m_pw(),
					adminMemberVOs.get(0).getA_m_pw()))
				adminMemberVOs.clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adminMemberVOs.size() > 0 ? adminMemberVOs.get(0) : null;
	}
	
	public List<AdminMemberVO> selectAdmins() {
		String sql = "SELECT * FROM tbl_admin_member";
		
		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();
		
		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
				
				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();
					
					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return adminMemberVO;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adminMemberVOs;
	}
	
	public int updateAdminAccount(int a_m_no) {
		String sql = "UPDATE tbl_admin_member SET "
				+ "a_m_approval = 1 "
				+ "WHERE a_m_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, a_m_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int updateAdminAccount(AdminMemberVO adminMemberVO) {
		String sql = "UPDATE tbl_admin_member SET " + "a_m_name = ?, "
				+ "a_m_gender = ?, " + "a_m_part = ?, "
				+ "a_m_position = ?, " + "a_m_mail = ?, "
				+ "a_m_phone = ?, " + "a_m_mod_date = NOW() "
				+ "WHERE a_m_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, adminMemberVO.getA_m_name(),
					adminMemberVO.getA_m_gender(), adminMemberVO.getA_m_part(),
					adminMemberVO.getA_m_position(), adminMemberVO.getA_m_mail(),
					adminMemberVO.getA_m_phone(), adminMemberVO.getA_m_no());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public AdminMemberVO selectAdmin(int a_m_no) {
		String sql = "SELECT * FROM tbl_admin_member "
				+ "WHERE a_m_no = ?";
		
		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();
		
		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
				
				@Override
				public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();
					
					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return adminMemberVO;
				}
			}, a_m_no);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adminMemberVOs.size() > 0 ? adminMemberVOs.get(0) : null;
	}
	
	public AdminMemberVO selectAdmin(String a_m_id, String a_m_name, String a_m_mail) {
		String sql = "SELECT * FROM tbl_admin_member "
				+ "WHERE a_m_id = ? AND a_m_name = ? AND a_m_mail = ?";
		
		List<AdminMemberVO> adminMemberVOs = new ArrayList<AdminMemberVO>();
		
		try {
			adminMemberVOs = jdbcTemplate.query(sql, new RowMapper<AdminMemberVO>() {
					
					@Override
					public AdminMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVO adminMemberVO = new AdminMemberVO();
					
					adminMemberVO.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVO.setA_m_id(rs.getString("a_m_id"));
					adminMemberVO.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVO.setA_m_name(rs.getString("a_m_name"));
					adminMemberVO.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVO.setA_m_part(rs.getString("a_m_part"));
					adminMemberVO.setA_m_position(rs.getString("a_m_position"));
					adminMemberVO.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVO.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVO.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVO.setA_m_mod_date(rs.getString("a_m_mod_date"));
					
					return adminMemberVO;
					}
			}, a_m_id, a_m_name, a_m_mail);

	} catch (Exception e) {
		e.printStackTrace();
	}
		
		return adminMemberVOs.size() > 0 ? adminMemberVOs.get(0) : null;
	}
	
	public int updatePassword(String a_m_id, String newPassword) {
		String sql = "UPDATE tbl_admin_member SET "
				+ "a_m_pw = ?, a_m_mod_date = NOW() "
				+ "WHERE a_m_id = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, passwordEncoder.encode(newPassword), a_m_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}