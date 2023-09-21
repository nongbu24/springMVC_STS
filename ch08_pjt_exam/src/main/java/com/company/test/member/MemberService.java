package com.company.test.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	@Autowired
	MemberDAO memberDAO;
	
	public int signUpConfirm(MemberVO memberVO) {
		memberDAO.insertMember(memberVO);
		return 0;
	}

	public MemberVO signInConfirm(MemberVO memberVO) {
		MemberVO signedInMember = memberDAO.selectMember(memberVO);
		return signedInMember;
	}

}