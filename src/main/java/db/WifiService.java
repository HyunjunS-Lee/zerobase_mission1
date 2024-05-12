package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiService {
	
	public WifiMember detail(String X_SWIFI_MGR_NO, String X_SWIFI_WRDOFC) {

		WifiMember wifimember = new WifiMember();

		String url = "jdbc:mariadb://3.35.2.232:3306/mission1";
		String dbUserId = "mission1_user";
		String dbPassword = "mission1";

		// 1. 드라이버 로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			// 커넥션 객체 생성
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);

			String sql = " " 
					+ " SELECT DISTANCE, X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, "
					+ " X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, "
					+ " X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, "
					+ " X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM"
					+ " from WIFI "
					+ " WHERE X_SWIFI_MGR_NO = ? AND X_SWIFI_WRDOFC = ? "
					;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, X_SWIFI_MGR_NO);
			preparedStatement.setString(2, X_SWIFI_WRDOFC);

			rs = preparedStatement.executeQuery();

			// 결과 수행 가져오기
			if (rs.next()) {
                wifimember.setDISTANCE(rs.getDouble("DISTANCE"));
				wifimember.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				wifimember.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
				wifimember.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				wifimember.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
				wifimember.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
				wifimember.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
				wifimember.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
				wifimember.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
				wifimember.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
				wifimember.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
				wifimember.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
				wifimember.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
				wifimember.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
				wifimember.setLAT(rs.getString("LAT"));
				wifimember.setLNT(rs.getString("LNT"));
				wifimember.setWORK_DTTM(rs.getString("WORK_DTTM"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return wifimember;
	}

	
	public List<WifiMember> list() {

		List<WifiMember> memberList = new ArrayList<>();

		String url = "jdbc:mariadb://3.35.2.232:3306/mission1";
		String dbUserId = "mission1_user";
		String dbPassword = "mission1";

		// 1. 드라이버 로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		ResultSet rs = null;

		//String wifimemberTypeValue = "X_SWIFI_MGR_NO";

		try {
			// 커넥션 객체 생성
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);

			// 스테이트먼트 객체 생성
			statement = connection.createStatement();

			// 쿼리 실행
			String sql =  "SELECT " + " DISTANCE, X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
					  " X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, " +
					  " X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, " +
					  " X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM" + " from WIFI ";

					 
					
					/*
					 * "SELECT *, " +
					 * "ROUND(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(LAT))*COS(RADIANS(LNT)" +
					 * "-RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(LAT))), 4) " + "AS DISTANCE " +
					 * "FROM WIFI " + "ORDER BY DISTANCE ";
					 */
			  					
			preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setString(1, wifimemberTypeValue);

			rs = preparedStatement.executeQuery();

			// 결과 수행
			while (rs.next()) {
				double DISTANCE = rs.getDouble("DISTANCE");
				String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
				String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
				String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
				String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
				String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
				String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
				String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
				String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
				String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
				String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
				String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
				String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
				String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
				String LAT = rs.getString("LAT");
				String LNT = rs.getString("LNT");
				String WORK_DTTM = rs.getString("WORK_DTTM");

				WifiMember wifimember = new WifiMember();
				wifimember.setDISTANCE(DISTANCE);
				wifimember.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
				wifimember.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
				wifimember.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
				wifimember.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
				wifimember.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);	
				wifimember.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
				wifimember.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
				wifimember.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
				wifimember.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
				wifimember.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
				wifimember.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
				wifimember.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
				wifimember.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
				wifimember.setLAT(LAT);
				wifimember.setLNT(LNT);
				wifimember.setWORK_DTTM(WORK_DTTM);
				memberList.add(wifimember);
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null && preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return memberList;
	}

	// 회원가입 함수
	public void register(WifiMember wifimember) {
		// 데이터베이스에 접속해서 퀴리를 실행한 후 결과를 받아온 부분

		String url = "jdbc:mariadb://3.35.2.232:3306/mission1";
		String dbUserId = "mission1_user";
		String dbPassword = "mission1";

		// 1. 드라이버 로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			// 커넥션 객체 생성
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);

			 //쿼리 실행
			String sql = "INSERT INTO WIFI(DISTANCE, X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, "
					+ "X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, "
					+ "X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, "
					+ "X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)  " 
		          	+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
	

			preparedStatement.setDouble(1, wifimember.getDISTANCE()); //거리
            preparedStatement.setString(2, wifimember.getX_SWIFI_MGR_NO()); //관리번호
			preparedStatement.setString(3, wifimember.getX_SWIFI_WRDOFC()); //자치구
			preparedStatement.setString(4, wifimember.getX_SWIFI_MAIN_NM()); //와이파이명
			preparedStatement.setString(5, wifimember.getX_SWIFI_ADRES1()); //도로명주소
			preparedStatement.setString(6, wifimember.getX_SWIFI_ADRES2()); //상세주소
			preparedStatement.setString(7, wifimember.getX_SWIFI_INSTL_FLOOR()); //설치위치
			preparedStatement.setString(8, wifimember.getX_SWIFI_INSTL_TY()); //설치유형
			preparedStatement.setString(9, wifimember.getX_SWIFI_INSTL_MBY()); //설치기관
			preparedStatement.setString(10, wifimember.getX_SWIFI_SVC_SE()); //서비스 구분
			preparedStatement.setString(11, wifimember.getX_SWIFI_CMCWR()); //망종류
			preparedStatement.setString(12, wifimember.getX_SWIFI_CNSTC_YEAR()); //설치년도
			preparedStatement.setString(13, wifimember.getX_SWIFI_INOUT_DOOR()); //실내외 구분
			preparedStatement.setString(14, wifimember.getX_SWIFI_REMARS3()); //설치환경
			preparedStatement.setString(15, wifimember.getLAT()); //x좌표
			preparedStatement.setString(16, wifimember.getLNT()); //y좌표
			preparedStatement.setString(17, wifimember.getWORK_DTTM()); //작업일자

			int affected = preparedStatement.executeUpdate();

			if (affected > 0) {
				System.out.println(" 저장 성공 ");
			} else {
				System.out.println(" 저장 실패 ");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 무조건 실행되어야해서 finally
			// jdbc 객체들 연결해제, isclosed도 예외를 발생시켜서 trycatch 해줘야함
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void dbUpdate() { //이부분은 아직 사용할 일이 없어서 수정X
		// 데이터베이스에 접속해서 퀴리를 실행한 후 결과를 받아온 부분
		String url = "jdbc:mariadb://3.35.2.232:3306/mission1";
		String dbUserId = "mission1_user";
		String dbPassword = "mission1";

		// 1. 드라이버 로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		ResultSet rs = null;

		String memberTypeValue = "email";
		String userIdValue = "zerobase@naver.com";
		String passwordValue = "9999";

		try {
			// 커넥션 객체 생성
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);

			// 스테이트먼트 객체 생성
			statement = connection.createStatement();

			// 쿼리 실행
			String sql = "update member set " + " password = ? " + " where member_type = ? and user_id = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, passwordValue);
			preparedStatement.setString(2, memberTypeValue);
			preparedStatement.setString(3, userIdValue);
			

			int affected = preparedStatement.executeUpdate();

			if (affected > 0) {
				System.out.println(" 수정 성공 ");
			} else {
				System.out.println(" 수정 실패 ");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void withdraw(WifiMember wifimember) {
		// 데이터베이스에 접속해서 퀴리를 실행한 후 결과를 받아온 부분

		String url = "jdbc:mariadb://3.35.2.232:3306/mission1";
		String dbUserId = "mission1_user";
		String dbPassword = "mission1";

		// 1. 드라이버 로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			// 커넥션 객체 생성
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);

			// 스테이트먼트 객체 생성
			statement = connection.createStatement();

			// 쿼리 실행
			String sql = " delete from WIFI " + "where getX_SWIFI_MGR_NO = ? and getX_SWIFI_WRDOFC = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, wifimember.getX_SWIFI_MGR_NO()); //관리번호
			preparedStatement.setString(2, wifimember.getX_SWIFI_WRDOFC()); //자치구

			int affected = preparedStatement.executeUpdate();

			if (affected > 0) {
				System.out.println(" 삭제 성공 ");
			} else {
				System.out.println(" 삭제 실패 ");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 무조건 실행되어야해서 finally
			// jdbc 객체들 연결해제, isclosed도 예외를 발생시켜서 trycatch 해줘야함
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
