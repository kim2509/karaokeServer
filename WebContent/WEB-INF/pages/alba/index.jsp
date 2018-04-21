<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
                    document.location.href = 'android://openURL?url=' + encodeURIComponent('/karaoke/alba/room.do');
                });

                $('#btnCheckCashBalance').click(function(){
                    document.location.href = 'android://openURL?url=' + encodeURIComponent('/karaoke/alba/cash.do');
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

            $('#divBeforeLogin').hide();
            $('#afterLogin').show();

        }

        function logout(){

            if ( confirm('정말 종료하시겠습니까?') ){
                $('#divBeforeLogin').show();
                $('#afterLogin').hide();
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
                <option value="hyun">강수현</option>
                <option value="chan">우성찬</option>
                <option value="boss">사장</option>
            </select>
      		<div id="btnShowRemained" onclick="login();" class="btnFull">근무시작</div>
        </div>
        <!-- 로그인 하기 전 -->


        <!-- 로그인 후 -->
        <div id="afterLogin" style="display:none;">
            <div id="loginInfo">
                근무자 : <span id="workerName">강수현</span><br/>
                근무일자 : <span id="workerName">2018-04-19</span><br/>
                근무 seq : <span id="workSeq">1</span>
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