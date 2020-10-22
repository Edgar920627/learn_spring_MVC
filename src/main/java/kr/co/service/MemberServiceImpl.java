package kr.co.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.dao.MemberDAO;
import kr.co.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDAO dao;

	// 회원가입
	@Override
	public void register(MemberVO vo) throws Exception {
		System.out.println("  [  1. service. register. 회원가입. 접속. ] ");
		dao.register(vo);
		System.out.println("  [  2. service. register. 회원가입. vo. = ] " + vo);

	}

	// 로그인
	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		System.out.println("  [ service login  로그인 ]");
		return dao.login(vo);
	}
	

	// 회원정보 수정
	// Controller에서 보내는 파라미터들을 memberUpdate(MemberVO vo)로 받고
	@Override
	public void memberUpdate(MemberVO vo) throws Exception {
		// 받은 vo를 DAO로 보내줍니다.
		System.out.println("  [ service memberUpdate  회원정보 수정 ]");
		dao.memberUpdate(vo);

	}

	// 회원탈퇴
	// 업데이트에서 처리한 내용과 같습니다.
	@Override
	public void memberDelete(MemberVO vo) throws Exception {
		System.out.println("  [ service memberDelete  회원 탈퇴 ]");
		dao.memberDelete(vo);
	}
	
	
	// 패스워드 체크
	@Override
	public int passChk(MemberVO vo) throws Exception {
		int result = dao.passChk(vo);
		System.out.println("  [ service passChk  패스워드 체크  ]  = " + result);
		return result;
	}
	
	
	// 아이디 중복 체크
	@Override
	public int idChk(MemberVO vo) throws Exception {
		System.out.println("  [  1. service. idChk. ID중복체크. 접속 ] ");
		int result = dao.idChk(vo);
		System.out.println("  [  2. service. idChk. ID중복체크. result = ] " + result);
		return result;
	}
	
	
	
}