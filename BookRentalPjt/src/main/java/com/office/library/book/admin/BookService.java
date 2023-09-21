package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVO;

@Service
public class BookService {

	final static public int BOOK_ISBN_ALREADY_EXIST = 0;
	final static public int BOOK_REGISTER_SUCCESS = 1;
	final static public int BOOK_REGISTER_FAIL = -1;
	
	@Autowired
	BookDAO bookDAO;
	
	public int registerBookConfirm(BookVO bookVO) {
		boolean isISBN = bookDAO.isISBN(bookVO.getB_isbn());
		
		if (!isISBN) {	
			int result = bookDAO.insertBook(bookVO);
			
			if (result > 0)
				return BOOK_REGISTER_SUCCESS;
			else
				return BOOK_REGISTER_FAIL;
			
		} else {
			return BOOK_ISBN_ALREADY_EXIST;
		}

	}

	public List<BookVO> searchBookConfirm(BookVO bookVO) {
		return bookDAO.selectBooksBySearch(bookVO);
	}
	
	public BookVO bookDetail(int b_no) {
		return bookDAO.selectBook(b_no);
	}
	
	public BookVO modifyBookForm(int b_no) {
		return bookDAO.selectBook(b_no);
	}
	
	public int modifyBookConfirm(BookVO bookVO) {
		return bookDAO.updateBook(bookVO);
	}
	
	public int deleteBookConfirm(int b_no) {
		return bookDAO.deleteBook(b_no);
	}
	
}