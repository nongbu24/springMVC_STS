package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

	@Autowired
	AdminMemberService adminMemberService;
	
	@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	public String createAccountForm() {		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
	}
	
	@RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	public String createAccountConfirm(AdminMemberVO adminMemberVO) {
		String nextPage = "admin/member/create_account_ok";
		int result = adminMemberService.createAccountConfirm(adminMemberVO);
		
		if (result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "admin/member/login_form";
		
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVO adminMemberVO, HttpSession session) {
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVO loginedAdminMemberVO =
				adminMemberService.loginConfirm(adminMemberVO);
		
		if (loginedAdminMemberVO == null) {
			nextPage = "admin/member/login_ng";
		} else {	
			session.setAttribute("loginedAdminMemberVO", loginedAdminMemberVO);
			session.setMaxInactiveInterval(60 * 30);
		}
		
		return nextPage;
	}
	
	@RequestMapping(value = "/logoutConfirm", method = RequestMethod.GET)
	public String logoutConfirm(HttpSession session) {
		String nextPage = "redirect:/admin";
		
		session.invalidate();
		
		return nextPage;
	}

	@RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
	public String listupAdmin(Model model) {
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVO> adminMemberVOs = adminMemberService.listupAdmin();
		
		model.addAttribute("adminMemberVOs", adminMemberVOs);
		
		return nextPage;
	}
	
	@RequestMapping(value = "/setAdminApproval", method = RequestMethod.GET)
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;	
	}
	
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		String nextPage = "admin/member/modify_account_form";
		
		AdminMemberVO loginedAdminMemberVO =
				(AdminMemberVO) session.getAttribute("loginedAdminMemberVO");
		
		if (loginedAdminMemberVO == null)
			nextPage = "redirect:/admin/member/loginForm";
		
		return nextPage;
	}
	
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(AdminMemberVO adminMemberVO, HttpSession
			session) {
		String nextPage = "admin/member/modify_account_ok";
		
		int result = adminMemberService.modifyAccountConfirm(adminMemberVO);
		
		if (result > 0) {	
			AdminMemberVO loginedAdminMemberVO =
					adminMemberService.getLoginedAdminMemberVO(adminMemberVO.getA_m_no());
			
			session.setAttribute("loginedAdminMemberVO", loginedAdminMemberVO);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			nextPage = "admin/member/modify_account_ng";
		}
		
		return nextPage;	
	}
	
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {	
		String nextPage = "admin/member/find_password_form";
		
		return nextPage;
	}
	
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(AdminMemberVO adminMemberVO) {
		String nextPage = "admin/member/find_password_ok";
		
		int result = adminMemberService.findPasswordConfirm(adminMemberVO);
		
		if (result <= 0)
			nextPage = "admin/member/find_password_ng";
		
		return nextPage;
	}
	
}