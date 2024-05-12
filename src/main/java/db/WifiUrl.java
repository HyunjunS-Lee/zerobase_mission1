package db;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class WifiUrl {
	public static void main(String[] args) {
		String baseUrl = "http://openapi.seoul.go.kr:8088/";
		String KEY = "6d6a7741526c796836376649547a78";
		String type =  "/json/";
		String serviceName = "TbPublicWifiInfo";
		int startRow = 1;
		int endRow = 1000;
		
		
		String url = baseUrl + KEY + type + serviceName + "/" + startRow + "/" + endRow + "/";
				
		//Json형식으로 받아와서 리스트 받아오고,
		//1, 1000이후에도 데이터 없을때까지 반복 필요
		
		String jsonResult = httpTestByMethod(url, "GET");

		JSONParser jsonParser = new JSONParser();

		JSONObject resultObject = new JSONObject();

		try {
			resultObject = (JSONObject) jsonParser.parse(jsonResult);
			
			resultObject.get("code");
			
			do{
			
			
				JSONObject object = new JSONObject();
				JSONArray array = new JSONArray();
			
				object = (JSONObject) jsonParser.parse(jsonResult);
				
				JSONObject info = (JSONObject) object.get("TbPublicWifiInfo");
				
				array = (JSONArray) info.get("row");
				
				String dburl = "jdbc:mariadb://3.35.2.232:3306/mission1";
				String dbUserId = "mission1_user";
				String dbPassword = "mission1";
				
				for(int i = 0; i < array.size(); i++) {
					JSONObject row = (JSONObject) array.get(i);
					
					String mgrNo = (String) row.get("X_SWIFI_MGR_NO");
					//여기에서 한줄씩 반복하면서 출력한 부분입니다.
					//이 값을 DB에 저장하면 됩니다.
					try {
						Class.forName("org.mariadb.jdbc.Driver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					Connection connection = null;
					PreparedStatement preparedStatement = null;
					//ResultSet rs = null;
					//WifiMember wifimember  = new WifiMember();
					try {
						connection = DriverManager.getConnection(dburl, dbUserId, dbPassword);
									
						//쿼리실행
						String sql = "insert into WIFI(DISTANCE, X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, "
								+ "X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, "
								+ "X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, "
								+ "X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)  " 
					          	+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";
						
						
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, (String) row.get("DISTANCE")); //거리					
						preparedStatement.setString(2, (String) row.get("X_SWIFI_MGR_NO")); //관리번호
						preparedStatement.setString(3, (String) row.get("X_SWIFI_WRDOFC")); //자치구
						preparedStatement.setString(4, (String) row.get("X_SWIFI_MAIN_NM")); //와이파이명
						preparedStatement.setString(5, (String) row.get("X_SWIFI_ADRES1")); //도로명주소
						preparedStatement.setString(6, (String) row.get("X_SWIFI_ADRES2")); //상세주소
						preparedStatement.setString(7, (String) row.get("X_SWIFI_INSTL_FLOOR")); //설치위치
						preparedStatement.setString(8, (String) row.get("X_SWIFI_INSTL_TY")); //설치유형
						preparedStatement.setString(9, (String) row.get("X_SWIFI_INSTL_MBY")); //설치기관
						preparedStatement.setString(10, (String) row.get("X_SWIFI_SVC_SE")); //서비스 구분
						preparedStatement.setString(11, (String) row.get("X_SWIFI_CMCWR")); //망종류
						preparedStatement.setString(12, (String) row.get("X_SWIFI_CNSTC_YEAR")); //설치년도
						preparedStatement.setString(13, (String) row.get("X_SWIFI_INOUT_DOOR")); //실내외 구분
						preparedStatement.setString(14, (String) row.get("X_SWIFI_REMARS3")); //설치환경
						preparedStatement.setString(15, (String) row.get("LAT")); //x좌표
						preparedStatement.setString(16, (String) row.get("LNT")); //y좌표
						preparedStatement.setString(17, (String) row.get("WORK_DTTM")); //작업일자
						
						int affected = preparedStatement.executeUpdate();
						if (affected > 0) {
							System.out.println(" 저장 성공 ");
						} else {
							System.out.println(" 저장 실패 ");
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
					
					System.out.println(row);
					System.out.println(mgrNo);
				}
				
				startRow = startRow + 1000;
				endRow = endRow + 1000;

				url = baseUrl + KEY + type + serviceName + "/" + startRow + "/" + endRow + "/";
						
				//Json형식으로 받아와서 리스트 받아오고,
				//1, 1000이후에도 데이터 없을때까지 반복 필요
				
				jsonResult = httpTestByMethod(url, "GET");
			}while(!"INFO-200".equals(resultObject.get("code")));

		}catch(Exception e) {
			
		}
		//httpTestByMethod(url, "POST");
		
		//httpTestByMethod(url, "DELETE");
	}
	
	public static String httpTestByMethod(String url, String method) {
		
		HttpUtils htppUtils = new HttpUtils();
		String result = "";
		HttpURLConnection conn = htppUtils.getHttpURLConnection(url, method);;

		if("GET".equalsIgnoreCase(method)){
			
			// conn.setDoInput(true); //URL 연결에서 데이터를 읽을지에 대한 설정 ( defualt true )
			result = htppUtils.getHttpRespons(conn);
		}else if("POST".equalsIgnoreCase(method)) {
			
			conn.setDoOutput(true); //URL 연결시 데이터를 사용할지에 대한 설정 ( defualt false )
			try (DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());){
				
				String str = "{\"user\" : \"kimchy\",    "
						+ "\"post_date\" : \"2009-11-15T14:12:12\",    "
						+ "\"message\" : \"trying out Elasticsearch\"}";
				
				dataOutputStream.writeBytes(str);
				dataOutputStream.flush();
				
				result = htppUtils.getHttpRespons(conn);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("DELETE".equalsIgnoreCase(method)) {			
			result = htppUtils.getHttpRespons(conn);
		}
		
		return result;
	}
}


class HttpUtils {
	
	@SuppressWarnings("deprecation")
	public HttpURLConnection getHttpURLConnection(String strUrl, String method) {
		URL url;
		HttpURLConnection conn = null;
		try {
			url = new URL(strUrl);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method); //Method 방식 설정. GET/POST/DELETE/PUT/HEAD/OPTIONS/TRACE
			conn.setConnectTimeout(5000); //연결제한 시간 설정. 5초 간 연결시도
			conn.setRequestProperty("Content-Type", "application/json");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	public String getHttpRespons(HttpURLConnection conn) {
		StringBuilder sb = null;

		try {
			if(conn.getResponseCode() == 200) {
				//정상적으로 데이터를 받았을경우
				sb = readResopnseData(conn.getInputStream());
			}else{
				//정상적으로 데이터를 받지 못한 경우 
				System.out.println(conn.getResponseCode());
				System.out.println(conn.getResponseMessage());
				
				sb = readResopnseData(conn.getErrorStream());
				
				System.out.println("error : " + sb.toString());
				
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.disconnect(); //연결 해제
		};
		
		if(sb == null) return null;
		
		return sb.toString();
	}
	
	public StringBuilder readResopnseData(InputStream in) {
		
		if(in == null ) return null;

		StringBuilder sb = new StringBuilder();

		String line = "";
		
		try (InputStreamReader ir = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(ir)){
			while( (line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb;
	}
}