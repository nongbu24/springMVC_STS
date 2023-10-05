package com.office.library.book.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.office.library.book.BookVO;
import com.office.library.book.RentalBookVO;
import com.office.library.user.member.UserMemberVO;

@Controller
@RequestMapping("/book/user")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVO bookVO, Model model) {
		String nextPage = "user/book/search_book";
		
		List<BookVO> bookVOs = bookService.searchBookConfirm(bookVO);
		
		model.addAttribute("bookVOs", bookVOs);
		
		return nextPage;
	}
	
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		String nextPage = "user/book/book_detail";
		
		BookVO bookVO = bookService.bookDetail(b_no);
		
		model.addAttribute("bookVO", bookVO);
		
		return nextPage;
	}
	
	@GetMapping("/rentalBookConfirm")
	public String rentalBookConfirm(@RequestParam("b_no") int b_no, HttpSession session) {
		String nextPage = "user/book/rental_book_ok";
		
		UserMemberVO loginedUserMemberVO =
				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		// 로그인에 대한 인터셉터가 모두 구현되었으므로 더이상 로그인 상태를 확인할 필요가 없다.
//		if (loginedUserMemberVO == null)
//			return "redirect:/user/member/loginForm";
		
		int result = bookService.rentalBookConfirm(b_no, loginedUserMemberVO.getU_m_no());
		
		if (result <= 0)
			nextPage = "user/book/rental_book_ng";
		
		return nextPage;
	}
	
	@GetMapping("/enterBookshelf")
	public String enterBookshelf(HttpSession session, Model model) {
		String nextPage = "user/book/bookshelf";
		
		UserMemberVO loginedUserMemberVO =
				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		List<RentalBookVO> rentalBookVOs =
				bookService.enterBookshelf(loginedUserMemberVO.getU_m_no());
		
		model.addAttribute("rentalBookVOs", rentalBookVOs);
		
		return nextPage;
	}

	@GetMapping("/listupRentalBookHistory")
	public String listupRentalBookHistory(HttpSession session, Model model) {
		String nextPage = "user/book/rental_book_history";
		
		UserMemberVO loginedUserMemberVO = (UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		List<RentalBookVO> rentalBookVOs =
				bookService.listupRentalBookHistory(loginedUserMemberVO.getU_m_no());
		
		model.addAttribute("rentalBookVOs", rentalBookVOs);
		
		return nextPage;
	}
}