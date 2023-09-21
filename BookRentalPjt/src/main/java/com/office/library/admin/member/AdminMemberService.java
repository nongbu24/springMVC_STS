package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDAO adminMemberDAO;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	public int createAccountConfirm(AdminMemberVO adminMemberVO) {
		boolean isMember = adminMemberDAO.isAdminMember(adminMemberVO.getA_m_id());
		
		if (!isMember) {
			int result = adminMemberDAO.insertAdminAccount(adminMemberVO);
			
			if(result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS;
			else
				return ADMIN_ACCOUNT_CREATE_FAIL;
			
		} else {
			return ADMIN_ACCOUNT_ALREADY_EXIST;
		}
		
	}
	
	public AdminMemberVO loginConfirm(AdminMemberVO adminMemberVO) {
		AdminMemberVO loginedAdminMemberVO =
				adminMemberDAO.selectAdmin(adminMemberVO);
		
		return loginedAdminMemberVO;
	}
	
	public List<AdminMemberVO> listupAdmin() {
		return adminMemberDAO.selectAdmins();
	}
	
	public void setAdminApproval(int a_m_no) {
		int result = adminMemberDAO.updateAdminAccount(a_m_no);
	}
	
	public int modifyAccountConfirm(AdminMemberVO adminMemberVO) {
		return adminMemberDAO.updateAdminAccount(adminMemberVO);
	}
	
	public AdminMemberVO getLoginedAdminMemberVO (int a_m_no) {
		return adminMemberDAO.selectAdmin(a_m_no);
	}
	
	public int findPasswordConfirm(AdminMemberVO adminMemberVO) {
		AdminMemberVO selectedAdminMemberVO =
				adminMemberDAO.selectAdmin(adminMemberVO.getA_m_id(),
						adminMemberVO.getA_m_name(), adminMemberVO.getA_m_mail());
		
		int result = 0;
		
		if (selectedAdminMemberVO != null) {
			String newPassword = createNewPassword();
			result = adminMemberDAO.updatePassword(adminMemberVO.getA_m_id(), newPassword);
			
			if (result > 0)
				sendNewPasswordByMail(adminMemberVO.getA_m_mail(), newPassword);
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
				mimeMessageHelper.setSubject("[한국도서관] 새 비밀번호 안내입니다.");
				mimeMessageHelper.setText("새 비밀번호 : " + newPassword, true);
			}
		};
		
		javaMailSenderImpl.send(mimeMessagePreparator);
	}
}