package kr.co.controller;


import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	// 회원가입 get
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void getRegister() throws Exception {
		System.out.println("  [  1. Controller. GET. 회원가입. 페이지이동 ]  ");
		logger.info("get register");
	}
	
	// 회원가입 post
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception {
		System.out.println("  [  1. Controller. post. 회원가입. 접속 ]  ");
		logger.info("post register");
		System.out.println("  [  2. Controller. post. 회원가입. 접속 ]  ");
		int result = service.idChk(vo);
		System.out.println("  [  3. Controller. post. 회원가입. ID중복체크. result = ]  " + result);
		try {
			if(result == 1) {
				return "/member/register";
			}else if(result == 0) {
				String inputPass = vo.getUserPass();
				String pwd = pwdEncoder.encode(inputPass);
				System.out.println("  [  4. Controller. post. 회원가입. 비밀번호. inputPass. = ] " + inputPass);
				System.out.println("  [  5. Controller. post. 회원가입. 암호화. pwd. = ] " + pwd);
				vo.setUserPass(pwd);
				
				System.out.println("  [  6. Controller. post. 회원가입. vo  = ]  " + vo);
				service.register(vo);
				System.out.println("  [  7. Controller. post. 회원가입. vo  = ]  " + vo);
			}
			// 요기에서~ 입력된 아이디가 존재한다면 -> 다시 회원가입 페이지로 돌아가기 
			// 존재하지 않는다면 -> register
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		System.out.println("  [  8. Controller. post. 회원가입. vo  = ]  " + vo);
		return "redirect:/";
	}
	
	
	
	// 로그인 get
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login() throws Exception {
		System.out.println("  [ Controller. loginView.  GET 이동화인.  ]");
		logger.info("get login");
	}
	
	// 로그인 post
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		logger.info("post login");
	
		session.getAttribute("member");
		MemberVO login = service.login(vo);
		boolean pwdMatch = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());

		if(login != null && pwdMatch == true) {
			session.setAttribute("member", login);
			System.out.println("  [ Controller. login. login.  ]  = " + login);

		} else {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg", false);
		}
		
		
		return "redirect:/";
	}
	
	// 로그아웃 post
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원정보 수정 get
	@RequestMapping(value="/memberUpdateView", method = RequestMethod.GET)
	public String registerUpdateView() throws Exception{
		return "member/memberUpdateView";
	}
	
	// 회원정보 수정  post
	@RequestMapping(value="/memberUpdate", method = RequestMethod.POST)
	public String registerUpdate(MemberVO vo, HttpSession session) throws Exception{
		
/*		MemberVO login = service.login(vo);
		
		boolean pwdMatch = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());
		if(pwdMatch) {
			service.memberUpdate(vo);
			session.invalidate();
		}else {
			return "member/memberUpdateView";
		}*/
		service.memberUpdate(vo);
		session.invalidate();
		return "redirect:/";
	}
	
	// 회원 탈퇴 get
	@RequestMapping(value="/memberDeleteView", method = RequestMethod.GET)
	public String memberDeleteView() throws Exception{
		return "member/memberDeleteView";
	}
	
	// 회원 탈퇴 post
	@RequestMapping(value="/memberDelete", method = RequestMethod.POST)
	public String memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		service.memberDelete(vo);
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 패스워드 체크
	@ResponseBody
	@RequestMapping(value="/passChk", method = RequestMethod.POST)
	public boolean passChk(MemberVO vo) throws Exception {

		MemberVO login = service.login(vo);
		boolean pwdChk = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());
		
		System.out.println("  [ Controller. passChk. vo.getUserPass().  ]  = " + vo.getUserPass());
		System.out.println("  [ Controller. passChk. login.getUserPass().  ]  = " + login.getUserPass());
		System.out.println("  [ Controller. passChk. login.  ]  = " + login);
		System.out.println("  [ Controller. passChk. pwdChk.  ]  = " + pwdChk);
		return pwdChk;
	}
	
	
	// 아이디 중복 체크
	@ResponseBody
	@RequestMapping(value="/idChk", method = RequestMethod.POST)
	public int idChk(MemberVO vo) throws Exception {
		int result = service.idChk(vo);
		System.out.println("  [ Controller. idChk. result.  ]  = " + result);
		return result;
	}
}
