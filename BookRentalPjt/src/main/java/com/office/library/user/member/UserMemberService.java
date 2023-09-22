package com.office.library.user.member;

import java.security.SecureRandom;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {
	
	@Autowired
	UserMemberDAO userMemberDAO;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	public int createAccountConfirm(UserMemberVO userMemberVO) {
		boolean isMember = userMemberDAO.isUserMember(userMemberVO.getU_m_id());
		
		if (!isMember) {
			int result = userMemberDAO.insertUserAccount(userMemberVO);
			
			if (result > 0) {
				return USER_ACCOUNT_CREATE_SUCCESS;
			} else {
				return USER_ACCOUNT_CREATE_FAIL;
			}
			
		} else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
		
	}
	
	public UserMemberVO loginConfirm(UserMemberVO userMemberVO) {
		UserMemberVO loginedUserMemberVO = userMemberDAO.selectUser(userMemberVO);
		
		return loginedUserMemberVO;
	}
	
	public int modifyAccountConfirm(UserMemberVO userMemberVO) {
		return userMemberDAO.updateUserAccount(userMemberVO);
	}
	
	public UserMemberVO getLoginedUserMemberVO(int u_m_no) {
		return userMemberDAO.selectUser(u_m_no);
	}
	
	public int findPasswordConfirm(UserMemberVO userMemberVO) {
		UserMemberVO selectedUserMemberVO = userMemberDAO.selectUser(
																		userMemberVO.getU_m_id(), 
																		userMemberVO.getU_m_name(),
																		userMemberVO.getU_m_mail());
		
		int result = 0;
		
		if (selectedUserMemberVO != null) {
			String newPassword = createNewPassword();
			
			result = userMemberDAO.updatePassword(userMemberVO.getU_m_id(), newPassword);
			
			if (result > 0)
				sendNewPasswordByMail(userMemberVO.getU_m_mail(), newPassword);
		}
		
		return result;
	}
	
	private String createNewPassword() {
		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z' };
		
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
			
			if (index % 2 == 0)
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
		}
		
		return stringBuffer.toString();
	}
	
	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper
					= new MimeMessageHelper(mimeMessage, true, "UTF-8");
				
				mimeMessageHelper.setTo(toMailAddr);
				mimeMessageHelper.setSubject("[한국도서관] 새로운 비밀번호 안내입니다");
				mimeMessageHelper.setText("새 비밀번호: " + newPassword, true);
			}
		};
		
		javaMailSenderImpl.send(mimeMessagePreparator);
	}
	
}