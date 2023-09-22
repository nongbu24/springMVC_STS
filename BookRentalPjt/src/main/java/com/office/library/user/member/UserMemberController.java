package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
public class UserMemberController {
	
	@Autowired
	UserMemberService userMemberService;
	
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		String nextPage = "user/member/create_account_form";
		
		return nextPage;
	}
	
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(UserMemberVO userMemberVO) {
		String nextPage = "user/member/create_account_ok";
		
		int result = userMemberService.createAccountConfirm(userMemberVO);
		
		if (result <= 0)
			nextPage = "user/member/create_account_ng";

		
		return nextPage;
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "user/member/login_form";
		
		return nextPage;
	}
	
	@PostMapping("loginConfirm")
	public String loginConfirm(UserMemberVO userMemberVO, HttpSession session) {
		String nextPage = "user/member/login_ok";
		
		UserMemberVO loginedUserMemberVO =
				userMemberService.loginConfirm(userMemberVO);
		
		if (loginedUserMemberVO == null) {
			nextPage = "user/member/login_ng";
		} else {
			session.setAttribute("loginedUserMemberVO", loginedUserMemberVO);
			session.setMaxInactiveInterval(60 * 30);
		}
		
		return nextPage;
	}
	
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		String nextPage = "user/member/modify_account_form";
		
		UserMemberVO loginedUserMemberVO =
				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		if (loginedUserMemberVO == null)
			nextPage = "redirect:/user/member/loginForm";
		
		return nextPage;
	}
	
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVO userMemberVO, HttpSession session) {
		String nextPage = "user/member/modify_account_ok";
		
		int result = userMemberService.modifyAccountConfirm(userMemberVO);
		
		if (result > 0) {
			UserMemberVO loginedUserMemberVO =
					userMemberService.getLoginedUserMemberVO(userMemberVO.getU_m_no());
			
			session.setAttribute("loginedUserMemberVO", loginedUserMemberVO);
			session.setMaxInactiveInterval(60 *30);
		} else {
			nextPage = "user/member/modify_account_ng";
		}
		
		return nextPage;
	}
	
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		String nextPage = "redirect:/";
		session.invalidate();
		
		return nextPage;
	}
	
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		String nextPage = "user/member/find_password_form";
		
		return nextPage;
	}
		
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberVO userMemberVO) {
		String nextPage = "user/member/find_password_ok";
		
		int result = userMemberService.findPasswordConfirm(userMemberVO);
		
		if (result <= 0)
			nextPage = "user/member/find_password_ng";
		
		return nextPage;
	}
	
}