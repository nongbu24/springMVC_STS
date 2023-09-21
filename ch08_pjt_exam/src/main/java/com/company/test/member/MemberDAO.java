package com.company.test.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MemberDAO {
	private Map<String, MemberVO> memberDB = new HashMap<String, MemberVO>();
	
	public void insertMember(MemberVO memberVO) {
		memberDB.put(memberVO.getM_id(), memberVO);
	}
	
	public MemberVO selectMember(MemberVO memberVO) {
		MemberVO signedInMember = memberDB.get(memberVO.getM_id());
		
		if(signedInMember != null && memberVO.getM_pw().equals(signedInMember.getM_pw())) {
			return signedInMember;
		} else {
			return null;
		}

	}

}