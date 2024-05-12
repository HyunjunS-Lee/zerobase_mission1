<%@page import="db.WifiMember"%>
<%@page import="db.WifiService"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {
	width: 100%;
	border-collapse: collapse; /* 셀 테두리가 겹치도록 설정 */
}

th, td {
	border: solid 1px #000;
	height: 40px;
	
}
.header-cell {
    background-color: #3cb371;
}
.buttonContainer {
    text-align: left; /* 버튼을 오른쪽으로 정렬 */
    margin-left: 10px;
}

</style>
<script>
function getMyLocation() {
    if ("geolocation" in navigator) {
        // 위치 정보 요청
        navigator.geolocation.getCurrentPosition(function(position) {
            var latitude = position.coords.latitude; // 위도
            var longitude = position.coords.longitude; // 경도

            // 가져온 위도와 경도 값을 입력 필드에 설정
            document.getElementById("lat").value = latitude;
            document.getElementById("lnt").value = longitude;

            console.log("Latitude: " + latitude + ", Longitude: " + longitude);

        }, function(error) {
            console.error("Error getting geolocation:", error);
        });
    } else {
        console.log("Geolocation is not supported by this browser.");
    }
}
</script>
</head>

<body>
	<%
/* 	String X_SWIFI_MGR_NO = request.getParameter("X_SWIFI_MGR_NO");
	String X_SWIFI_WRDOFC = request.getParameter("X_SWIFI_WRDOFC"); */
	
	WifiService wifiSevice = new WifiService();
	List<WifiMember> wifiList = wifiSevice.list();
	/* WifiMember wifiMember = wifiSevice.detail(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC);  */
	%>
   <h1>와이파이 정보 구하기</h1>
   	<div>
	   <a href="add.jsp">홈</a>|
	   <a href="add.jsp">위치 히스토리 목록</a>|
	   <a href="add.jsp">Open API 와이파이 정보 가져오기</a>
	</div>
	
	 <div class="buttonContainer">
	     <label>
        LAT: <input type="text" id="lat" name="lat" value="0.0">
    </label>
    <label>
        LNT: <input type="text" id="lnt" name="lnt" value="0.0">
    </label>
    <input type="button" onclick="getMyLocation()" value="내 위치 가져오기">
	<input type="button" value="근처 WIPI 정보 보기"> 
	</div>
	
	<div id="locationInfo">
    <p id="latitude"></p>
    <p id="longitude"></p>
</div>
	<table>
		<thead>
			<tr>
				<th class="header-cell">거리(Km)</th>
				<th class="header-cell">관리번호</th>
				<th class="header-cell">자치구</th>
				<th class="header-cell">와이파이명</th>
				<th class="header-cell">도로명주소</th>
				<th class="header-cell">상세주소</th>
				<th class="header-cell">설치위치(층)</th>
				<th class="header-cell">설치유형</th>
				<th class="header-cell">설치기관</th>
				<th class="header-cell">서비스구분</th>
				<th class="header-cell">망종류</th>
				<th class="header-cell">설치년도</th>
				<th class="header-cell">실내외구분</th>
				<th class="header-cell">WIFI접속환경</th>
				<th class="header-cell">X좌표</th>
				<th class="header-cell">Y좌표</th>
				<th class="header-cell">작업일자</th>
			</tr>
		</thead>
				<tbody>
				<tr>
				<%
				for (WifiMember wifimember : wifiList) {
				%>
				</tr>
			<tr>
				<td><%=wifimember.getDISTANCE() %></td>           
				<td><%=wifimember.getX_SWIFI_MGR_NO() %></td>
				<td><%=wifimember.getX_SWIFI_WRDOFC() %></td>
			    <td><%=wifimember.getX_SWIFI_MAIN_NM() %></td>
			    <td><%=wifimember.getX_SWIFI_ADRES1() %></td>
			    <td><%=wifimember.getX_SWIFI_ADRES2() %></td>
			    <td><%=wifimember.getX_SWIFI_INSTL_FLOOR() %></td>
			    <td><%=wifimember.getX_SWIFI_INSTL_TY() %></td> 
				<td><%=wifimember.getX_SWIFI_INSTL_MBY() %></td>
			    <td><%=wifimember.getX_SWIFI_SVC_SE() %></td>
			    <td><%=wifimember.getX_SWIFI_CMCWR() %></td>
			    <td><%=wifimember.getX_SWIFI_CNSTC_YEAR() %></td>
			    <td><%=wifimember.getX_SWIFI_INOUT_DOOR() %></td>
			    <td><%=wifimember.getX_SWIFI_REMARS3() %></td>
			    <td><%=wifimember.getLAT() %></td>
			    <td><%=wifimember.getLNT() %></td>
			    <td><%=wifimember.getWORK_DTTM() %></td>
		    </tr>
		 <tr>
			<%
			}
			%>
		</tr>
		</tbody>
	</table>
</body>
</html>