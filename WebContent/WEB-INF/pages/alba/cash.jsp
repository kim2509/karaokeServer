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

        #workInfo{
            text-align: center;
            width:100%;
            margin:20px 0px;
            font-weight: bold;
            font-size: 20px;
        }

        #cashInfo{
            text-align: center;
            width:100%;
            margin:20px 0px;
        }

        #cashInfo input{
            width:60px;
            height:30px;
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

        #sum{
            text-align: center;
            margin:10px;
            font-weight: bold;
            color:blue;
            font-size: 20px;
        }

    </style>

    <script language="javascript">
    	
    	jQuery(document).ready(function(){
			try {


			} catch ( ex ) {
			alert( ex.message );
			}
    	});

        function calc(){

            try {

                var w50000 = $('input[name=w50000]').val();
                if ( w50000 == '' ) w50000 = 0;
                w50000 = parseInt(w50000);

                var w10000 = $('input[name=w10000]').val();
                if ( w10000 == '' ) w10000 = 0;
                w10000 = parseInt(w10000);

                var w5000 = $('input[name=w5000]').val();
                if ( w5000 == '' ) w5000 = 0;
                w5000 = parseInt(w5000);

                var w1000 = $('input[name=w1000]').val();
                if ( w1000 == '' ) w1000 = 0;
                w1000 = parseInt(w1000);

                var w500 = $('input[name=w500]').val();
                if ( w500 == '' ) w500 = 0;
                w500 = parseInt(w500);

                var w100 = $('input[name=w100]').val();
                if ( w100 == '' ) w100 = 0;
                w100 = parseInt(w100);

                var sum = w50000 * 50000 + w10000 * 10000 + w5000 * 5000 + w1000 * 1000 + w500 * 500 + w100 * 100;
                $('#sum').html( '합계: ' + addComma(sum) + '원' );

            } catch ( ex ) {
                alert( ex.message );
            }
        }

        function addComma(num) {
          var regexp = /\B(?=(\d{3})+(?!\d))/g;
           return num.toString().replace(regexp, ',');
        }

        function save(){
            if ( confirm('금액을 저장하시겠습니까?') ) {
                alert('저장되었습니다.');
            }
        }
        
        function closeWindow() {
    		document.location.href = 'android://finishActivity';
    	}
        
    </script>
    
  </head>
  <body>
  	<div id="wrapper">
  		
        <div id="workInfo">
            근무자 : <span>강수현</span><br/>
            근무 seq : <span>1</span>
        </div>

        <table id="cashInfo">
            <tr>
                <th>5만원권</th>
                <td><input type="number" name="w50000" /> 장</td>
            </tr>
            <tr>
                <th>1만원권</th>
                <td><input type="number" name="w10000"/> 장</td>
            </tr>
            <tr>
                <th>5천원권</th>
                <td><input type="number" name="w5000" /> 장</td>
            </tr>
            <tr>
                <th>1천원권</th>
                <td><input type="number" name="w1000" /> 장</td>
            </tr>
            <tr>
                <th>500원짜리</th>
                <td><input type="number" name="w500" /> 개</td>
            </tr>
            <tr>
                <th>100원짜리</th>
                <td><input type="number" name="w100" /> 개</td>
            </tr>
        </table>
        
        <div id="sum"></div>

        <div class="btnFull" onclick="calc();">계산하기</div>
        <div class="btnFull" onclick="save();">저장</div>
        <div id="btnClose" onclick="closeWindow();" class="btnFullRed">닫기</div>

  	</div>

  </body>
</html>