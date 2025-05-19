package kr.or.iei.user.controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.user.model.service.UserService;
import kr.or.iei.user.model.vo.User;


/**
 * Servlet implementation class ForgotPwServlet
 */
@WebServlet("/user/forgotPw")
public class ForgotPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPwServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        
        UserService service = new UserService();
        User chkUser = service.userChk(userId, userName);

        System.out.println("입력받은 ID: " + userId);
        System.out.println("입력받은 이름: " + userName);
		
        
        if (chkUser != null) {
            // 임시 비밀번호 생성
            SecureRandom random = new SecureRandom();
            String randomPw = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int index = random.nextInt(randomPw.length());
                sb.append(randomPw.charAt(index));
            }
           //생성된 임시 비밀번호 tempPw
            String tempPw = sb.toString();
        
            //1. 환경 설정 정보
            Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.naver.com"); //사용 SMTP 서버명
			prop.put("mail.smtp.port", 465);			  //사용 SMTP 포트
			prop.put("mail.smtp.auth", "true");			  //인증 수행
			prop.put("mail.smtp.ssl.enable", "true");	  //보안 설정 적용
			prop.put("mail.smtp.ssl.trust", "smtp.naver.com");
			
			//2. 세션 설정 및 인증 정보 설정
			Session session = Session.getDefaultInstance(prop, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("seoght0702@naver.com", "tjdydwns3658!");
					}
			});
			
			//3. 이메일 관련 정보 세팅
			MimeMessage msg = new MimeMessage(session);
			
			try {
				msg.setSentDate(new Date());
				
				msg.setFrom(new InternetAddress("seoght0702@naver.com", "KH_OMS"));
				
				InternetAddress to = new InternetAddress(userId); //수신자 이메일 주소
				msg.setRecipient(Message.RecipientType.TO, to);
				
				msg.setSubject("임시 비밀번호 안내"); //제목
				
				msg.setContent("회원님의 임시 비밀번호는 [<span style='color:red;'>" + tempPw + "</span>]", "text/html; charset=utf-8"); //본문 내용 및 형식 
				
				Transport.send(msg);//이메일 전송
            
				//DB에 임시 비밀번호 저장
				int result = service.updateTempPw(userId, tempPw);
				
				if(result > 0) {
					//개인정보수정 페이지 고치면 그쪽으로 이동
					response.getWriter().print(true);
					return;
				}
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.getWriter().print(false);
			
			}else { //조회된 이메일 없을 때
				response.getWriter().print(false);
			}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}