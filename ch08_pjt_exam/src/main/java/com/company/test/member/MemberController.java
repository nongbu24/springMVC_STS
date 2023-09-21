package com.company.test.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService = new MemberService();
	
	@RequestMapping("/signUp")
	public String signUp() {
		return "sign_up";
	}
	
	@RequestMapping("/signIn")
	public String signIn() {
		return "sign_in";
	}
	
	@RequestMapping("/signUpConfirm")
	public String signUpConfirm(MemberVO memberVO) {
		memberService.signUpConfirm(memberVO);
		return "sign_up_ok";
	}
	
	@RequestMapping("/signInConfirm")
	public String signInConfirm(MemberVO memberVO) {
		MemberVO signedInMember = memberService.signInConfirm(memberVO);
		
		if(signedInMember != null) {
			return "sign_in_ok";
		} else {
			return "sign_in_ng";
		}

	}
	
	@RequestMapping("/logOutConfirm")
	public String logOutConfirm() {
		return "log_out";
	}

}