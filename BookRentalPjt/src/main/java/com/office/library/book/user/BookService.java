package com.office.library.book.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVO;
import com.office.library.book.RentalBookVO;

@Service
public class BookService {

	@Autowired
	BookDAO bookDAO;
	
	public List<BookVO> searchBookConfirm(BookVO bookVO) {
		return bookDAO.selectBooksBySearch(bookVO);
	}
	
	public BookVO bookDetail(int b_no) {
		return bookDAO.selectBook(b_no);
	}
	
	public int rentalBookConfirm(int b_no, int u_m_no) {
		int result = bookDAO.insertRentalBook(b_no, u_m_no);
		
		if (result >= 0)
			bookDAO.updateRentalBookAble(b_no);
		
		return result;
	}
	
	public List<RentalBookVO> enterBookshelf(int u_m_no) {
		return bookDAO.selectRentalBooks(u_m_no);
	}
	
	public List<RentalBookVO> listupRentalBookHistory(int u_m_no) {
		return bookDAO.selectRentalBookHistory(u_m_no);
	}
	
}