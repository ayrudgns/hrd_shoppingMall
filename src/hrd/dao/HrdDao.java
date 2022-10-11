package hrd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrd.vo.MemberVO;
import hrd.vo.SaleVO;
import jdbc.util.OracleConnectionUtil;

public class HrdDao {
	private static HrdDao dao = new HrdDao();
	
	private HrdDao() {
		
	}
	
	public static HrdDao getInstance() {
		return dao;
	}					// 싱글턴 패턴 객체 생성할 수 있게!
	
	// 1)
	public int getNextSeq() {										// 다음 시퀀스 값을 회원 등록 화면에 표시해야 함 (회원번호 자동생성 기능)
//		String sql ="SELECT CUSTNO_SEQ01.NEXTVAL FROM DUAL";
		String sql2 ="SELECT (MAX(CUSTNO) + 1) FROM MEMBER_TBL_02";		// 메모장 설명 19번 참고
		int seq = 0;
		try (Connection conn = OracleConnectionUtil.connect();			// try-with-resources 사용
				PreparedStatement pstmt = conn.prepareStatement(sql2);
				ResultSet rs = pstmt.executeQuery())
		{
			if(rs.next())
				seq = rs.getInt(1);
			
		}catch(SQLException e) {
			System.out.println("Next SEQ에 문제가 있습니다. : " + e.getMessage());
		}
		return seq;
	}
	
	// 2)
	public int registMember(MemberVO vo) {			// 회원 등록
		
		String sql ="INSERT INTO MEMBER_TBL_02 VALUES "		// 시퀀스를 .getNextSeq()에서 미리 가져왔으므로, INSERT 쿼리에서 
				+ "	(?, ?, ?, ?, TO_DATE(?, 'YYYYMMDD'), ?, ?)";	// CUSTNO_SEQ01.NEXTVAL 값을 줄 필요가 없다.
								// 문제에서 가입 일자를 'yyyymmdd' 형식의 문자열로 입력받고, insert 할 때  
								// to_date(문자열,'yyyymmdd') 함수 사용
								// 회원 조회에는 yyyy-mm-dd로 출력되어야 하기 때문이다.
								// 따라서 입력받을 때는 MemberVO 클래스의 문자열 타입 날짜(sjoindate) 사용

		int cnt = 0;
		try (Connection conn = OracleConnectionUtil.connect();
			PreparedStatement pstmt=conn.prepareStatement(sql))
		{
			pstmt.setInt(1, vo.getCustno());
			pstmt.setString(2, vo.getCustname());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getAddress());
			pstmt.setString(5, vo.getSjoindate());
			pstmt.setString(6, vo.getGrade());
			pstmt.setString(7, vo.getCity());
			
			cnt = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("member regist 에 문제가 있습니다. : " + e.getMessage());
		}
		return cnt;

	}
	
	// 3)
	public MemberVO getMember(int custno) {				// 회원 정보 수정할 때 기본 정보가 미리 입력되어 있으려면 필요함!
		
		String sql = "SELECT * FROM MEMBER_TBL_02 WHERE CUSTNO = ?";
				
		MemberVO vo = null;
		try(Connection conn = OracleConnectionUtil.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			ResultSet rs = null;
			pstmt.setInt(1, custno);
			rs = pstmt.executeQuery();
			if (rs.next()) {		
				vo = new MemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getDate(5), null, rs.getString(6), rs.getString(7));
														// null은 sjoindate => 필요 없는 값
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("Member One 조회에 문제가 있습니다. : " + e.getMessage());
		} 
		return vo;		
	}
	
	// 4)
	public int updateMember(MemberVO vo) {				// 회원 정보 수정
		String sql ="UPDATE MEMBER_TBL_02 SET "
				+ "	PHONE = ?, ADDRESS = ?, GRADE = ?, CITY = ? WHERE CUSTNO = ?";
		int cnt = 0;
		try (Connection conn = OracleConnectionUtil.connect();
			PreparedStatement pstmt=conn.prepareStatement(sql))
		{
			pstmt.setString(1, vo.getPhone());
			pstmt.setString(2, vo.getAddress());
			pstmt.setString(3, vo.getGrade());
			pstmt.setString(4, vo.getCity());
			pstmt.setInt(5, vo.getCustno());
			
			cnt = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("member update 에 문제가 있습니다. : " + e.getMessage());
		}
		return cnt;
	}
	
	// 5)
	public List<MemberVO> getMembers() {				// 회원 목록 조회
		
		String sql = "SELECT CUSTNO, CUSTNAME, PHONE, ADDRESS, JOINDATE, "
				+ "DECODE(GRADE,'A','VIP','B','일반','C','직원') AS AGRADE,"
				+ "CITY FROM MEMBER_TBL_02 ORDER BY CUSTNO";
				
		MemberVO vo;
		List<MemberVO> list = new ArrayList<MemberVO>(); 
		
		try (Connection conn = OracleConnectionUtil.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {		
				vo = new MemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getDate(5), null, rs.getString(6), rs.getString(7));
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) {
			System.out.println("Member List 조회에 문제가 있습니다. : " + e.getMessage());
		}
		return list;		
		
	}
	
	// 6)
	public List<SaleVO> getSales() {						// 회원 매출 조회
		
		String sql = "SELECT mt.CUSTNO, " + 				// 원래 문제
				" mt.CUSTNAME, " + 
				" DECODE (mt.GRADE, 'A', 'VIP', 'B', '일반', 'C', '직원') AS agrade, " + 
				" SALE.ASUM TOTAL " + 
				" FROM MEMBER_TBL_02 mt, " + 
				" (SELECT CUSTNO, SUM(PRICE) AS ASUM FROM MONEY_TBL_02 " + 
				" GROUP BY CUSTNO " + 
				" ORDER BY ASUM DESC) SALE " + 			// ORDER BY 두번 해줘야 함!
				" WHERE mt.CUSTNO = SALE.CUSTNO"
				+ " ORDER BY TOTAL DESC"; 

		// 메모장 설명 17번 참고
		// 변형 문제 : 매출이 없으면 null이 아닌 0으로 출력하기
		// 외부 조인을 활용하여 매출이 없는 회원도 포함한다.
//		String sql2 = "SELECT met.CUSTNO, CUSTNAME, "		
//				+ "	DECODE(met.GRADE, 'A', 'VIP', 'B', '일반', 'C', '직원') AS GRADE, "
//				+ "	NVL(SALE.ASUM,0) TOTAL "		// SALE.ASUM의 값이 NULL일 때는 0으로 대체한다.
//				+ "	FROM MEMBER_TBL_02 met LEFT OUTER JOIN "
//				+ "	(SELECT CUSTNO, SUM(PRICE) ASUM FROM MONEY_TBL_02 mot "
//				+ "	GROUP BY CUSTNO "
//				+ "	ORDER BY ASUM DESC) SALE "
//				+ "	on met.CUSTNO = SALE.CUSTNO "
//				+ " ORDER BY TOTAL DESC, CUSTNO";		// 매출 금액이 같으면 CUSTNO 값이 정렬 기준이 된다.
		
		SaleVO vo;
		List<SaleVO> list = new ArrayList<SaleVO>();
		try (Connection conn = OracleConnectionUtil.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) 
		{
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new SaleVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				list.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("회원 매출 조회에  문제가 있습니다. : " + e.getMessage());
		}
		return list;	
	}
}
