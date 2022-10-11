package hrd.vo;

import java.sql.Date;

/**
 * @author myste
 *
 */
public class MemberVO {
	private int custno;
	private String custname;
	private String phone;
	private String address;
	private Date joindate;
	private String sjoindate;	
	// 문제에서 가입일자를 구분기호 없이 연도 4자리, 월 2자리, 일 2자리 형식(20220805 형식)으로 입력한다.
	// 해당 형식으로 입력받은 가입일자를 처리하기 위해 sjoindate 변수를 선언 및 활용한다.
	// 이때 입력받은 20220805 문자열을 데이터에는 2022-08-05로 입력되어야 하는데, 이는
	// insert 메소드인 registMember()에서 to_date(문자열,'yyyyMMdd')로 처리해준다.
	private String grade;
	private String city;
	
	public MemberVO() {
		
	}
	
	public MemberVO(int custno, String custname, String phone, String address, 
					Date joindate, String sjoindate, String grade, String city) {
		this.custno = custno;
		this.custname = custname;
		this.phone = phone;
		this.address = address;
		this.joindate = joindate;
		this.sjoindate = sjoindate;
		this.grade = grade;
		this.city = city;
	}

	// getter만 만들었다. setter는 만들어도 되고 만들지 않아도 되고~
	public int getCustno() {
		return custno;
	}

	public String getCustname() {
		return custname;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public Date getJoindate() {
		return joindate;
	}

	public String getGrade() {
		return grade;
	}

	public String getCity() {
		return city;
	}
	
	public String getSjoindate() {
		return sjoindate;
	}

	@Override
	public String toString() {
		return "MemberVO [custno = " + custno + ", custname = " + custname + ", phone = " + phone + ", address = " + address
				+ ", joindate = " + joindate + ", sjoindate = " + sjoindate + ", grade = " + grade + ", city = " + city + "]";
	}
	
	
	
}
