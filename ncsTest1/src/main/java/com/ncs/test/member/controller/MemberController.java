package com.ncs.test.member.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncs.test.member.model.service.MemberService;
import com.ncs.test.member.model.vo.Member;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/")
	public String toMainPage() {
		return "index";
	}
	
	@RequestMapping("login")
	public String memberLogin(Member memberId, Model model, HttpRequest request) {
		Member member = memberService.loginMember(memberId);
		HttpSession session = request.getSession();
		
		if(member == null) {
			model.addAttribute("msg", "Wrong answer");
			return "errorPage";
		} else {
			session.setAttribute("memberId", member);
		}
		
	}
	
}