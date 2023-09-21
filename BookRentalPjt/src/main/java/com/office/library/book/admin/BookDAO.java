package com.office.library.book.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.library.book.BookVO;

@Component
public class BookDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isISBN(String b_isbn) {
		String sql = "SELECT COUNT(*) FROM tbl_book WHERE b_isbn = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, b_isbn);
		
		return result > 0 ? true : false;
	}
	
	public int insertBook(BookVO bookVO) {	
		String sql = "INSERT INTO tbl_book(b_thumbnail, "
				+ "b_name, b_author, b_publisher, "
				+ "b_publish_year, b_isbn, b_call_number, "
				+ "b_rental_able, b_reg_date, b_mod_date) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql,
					bookVO.getB_thumbnail(), bookVO.getB_name(),
					bookVO.getB_author(), bookVO.getB_publisher(),
					bookVO.getB_publish_year(), bookVO.getB_isbn(),
					bookVO.getB_call_number(), bookVO.getB_rental_able());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;	
	}
	
	public List<BookVO> selectBooksBySearch(BookVO bookVO) {
		String sql = "SELECT * FROM tbl_book WHERE b_name LIKE ? ORDER BY b_no DESC";
		
		List<BookVO> bookVOs = null;
		
		try {
			bookVOs = jdbcTemplate.query(sql, new RowMapper<BookVO>() {
				
				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVO bookVO = new BookVO();
					
					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_author(rs.getString("b_author"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rental_able(rs.getInt("b_rental_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));
					
					return bookVO;
				}
			}, "%" + bookVO.getB_name() + "%");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bookVOs.size() > 0 ? bookVOs : null;
	}
	
	public BookVO selectBook(int b_no) {
		String sql = "SELECT * FROM tbl_book WHERE b_no = ?";
		
		List<BookVO> bookVOs = null;
		
		try {
			bookVOs = jdbcTemplate.query(sql, new RowMapper<BookVO>() {
				
				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVO bookVO = new BookVO();
					
					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_author(rs.getString("b_author"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rental_able(rs.getInt("b_rental_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));

					return bookVO;
				}
				
			}, b_no);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bookVOs.size() > 0 ? bookVOs.get(0) : null;
	}
	
	public int updateBook(BookVO bookVO) {
		List<String> args = new ArrayList<String>();
		
		String sql = "UPDATE tbl_book SET ";
		
		if (bookVO.getB_thumbnail() != null) {
			sql += "b_thumbnail = ?, ";
			args.add(bookVO.getB_thumbnail());
		}
		
		sql += "b_name = ?, ";
		args.add(bookVO.getB_name());
		
		sql += "b_author = ?, ";
		args.add(bookVO.getB_author());
		
		sql += "b_publisher = ?, ";
		args.add(bookVO.getB_publisher());
		
		sql += "b_publish_year = ?, ";
		args.add(bookVO.getB_publish_year());
		
		sql += "b_isbn = ?, ";
		args.add(bookVO.getB_isbn());
		
		sql += "b_rental_able = ?, ";
		args.add(Integer.toString(bookVO.getB_rental_able()));
		
		sql += "b_mod_date = NOW() ";
		
		sql += "WHERE b_no = ?";
		args.add(Integer.toString(bookVO.getB_no()));
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteBook(int b_no) {
		String sql = "DELETE FROM tbl_book "
				+ "WHERE b_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}