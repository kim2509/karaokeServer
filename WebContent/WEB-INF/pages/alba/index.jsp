<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%
	List<HashMap> workers = (List<HashMap>) request.getAttribute("workers");
%>
<!DOCTYPE html>
<html>
  <head>
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
	<script type="text/javascript" src="//www.hereby.co.kr/nearhere/js//jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://script.gmarket.co.kr/js/mobile/main/common/handlebars-v1.1.2.js"></script>

    <style type="text/css">
    	
    	* { margin:0;padding:0; }

    	#wrapper{
    		margin:10px;
    	}

        #workerList{
            width:100%;
            height:40px;
            font-weight: bold;
        }

        #loginInfo{

            font-weight: bold;
            margin:10px;
        }

    	.btnFull{
    		margin:5px 0px;
    		padding:10px;
    		text-align: center;
    		border:1px solid blue;
    		background:#eeeeff;
    		color:blue;
    	}

    	.btnFullRed{
    		margin:5px 0px;
    		padding:10px;
    		text-align: center;
    		border:1px solid red;
    		background:#ffbbbb;
    		color:red;
    		font-weight:bold;
    	}



    </style>

    <script language="javascript">
    	
    	jQuery(document).ready(function(){
			try {

				$('#btnStartCleaning').click(function(){
                    document.location.href = 'android://openURL?url=' 
                    		+ encodeURIComponent('/karaoke/alba/room.do?workSeq=' + $('#workSeq').html() );
                });

                $('#btnCheckCashBalance').click(function(){
                    document.location.href = 'android://openURL?url=' 
                    		+ encodeURIComponent('/karaoke/alba/cash.do?workSeq=' + $('#workSeq').html() );
                });

			} catch ( ex ) {
			alert( ex.message );
			}
    	});

        function login(){

            if ( $('#workerList').val() == '' ) {
                alert('근무자를 선택해 주세요.');
                return;
            }
            
            var param = {"worker_id": $('#workerList').val(), "worker_name": $('#workerList :checked').text() };

            jQuery.ajax({
    			type : "POST",
    			url : "/karaoke/alba/getWorkSeq.do",
    			data : JSON.stringify( param ),
    			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    			contentType : "application/json; charset=UTF-8",
    			success : function(result) {
    				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
    				// TODO
    				try {

    					$('#workerName').html( result.data.worker_name);
    					$('#workDate').html( result.data.workDate);
    					$('#workSeq').html( result.data.workSeq);
    					
    		            $('#divBeforeLogin').hide();
    		            $('#afterLogin').show();
    					
    				} catch (ex) {
    					alert(ex.message);
    				}
    			},
    			complete : function(data) {
    				// 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
    				// TODO
    				bLoading = false;
    			},
    			error : function(xhr, status, error) {
    				alert("에러발생(getCityList)" + error );
    			}
    		});
            

        }

        function logout(){

            if ( confirm('정말 종료하시겠습니까?\n창고키, 사무실키 주머니에 없는거 확실한가요?') ){
            	
            	var param = {"workSeq": $('#workSeq').html()};
        		
        		
    			jQuery.ajax({
        			type : "POST",
        			url : "/karaoke/alba/finishWork.do",
        			data : JSON.stringify( param ),
        			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
        			contentType : "application/json; charset=UTF-8",
        			success : function(result) {
        				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
        				// TODO
        				try {
        					
    						if ( result.resCode == '0000' ) {
    							$('#divBeforeLogin').show();
    			                $('#afterLogin').hide();
        					}
        					else {
        					
        						alert( result.resMsg );
        						
        					}
        					
        				} catch (ex) {
        					alert(ex.message);
        				}
        			},
        			error : function(xhr, status, error) {
        				alert("에러발생(getCityList)" + error );
        			}
        		});
    			
                
            }
        }
    </script>

  </head>
  <body>
  	<div id="wrapper">
  		
        <!-- 로그인 하기 전 -->
        <div id="divBeforeLogin">
            <select id="workerList">
                <option value="">본인의 이름을 선택해 주세요.</option>
<%                
			for ( int i = 0; i < workers.size(); i++ ) {
				HashMap worker = (HashMap) workers.get(i);
				String id = worker.get("id").toString();
				String name = worker.get("name").toString();
%>
				<option value="<%= id %>"><%= name %></option>
<%				
			}
 %>
            </select>
      		<div id="btnShowRemained" onclick="login();" class="btnFull">근무시작</div>
        </div>
        <!-- 로그인 하기 전 -->


        <!-- 로그인 후 -->
        <div id="afterLogin" style="display:none;">
            <div id="loginInfo">
                근무자 : <span id="workerName"></span><br/>
                근무일자 : <span id="workDate"></span><br/>
                근무 seq : <span id="workSeq"></span>
            </div>

            <div id="btnStartCleaning" class="btnFull">청소하기</div>
            <div id="btnCheckCashBalance" class="btnFull">현금잔액체크</div>
            <div id="btnFinishWork" onclick="logout();" class="btnFullRed" style="margin-top:30px">근무종료</div>
            근무를 종료하기 위해선 현금잔액체크가 완료돼야 됩니다.
        </div>
        <!-- 로그인 후 -->
  	</div>

  </body>
</html>