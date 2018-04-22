<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String workSeq = request.getParameter("workSeq");
%>
<!DOCTYPE html>
<html>
  <head>
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
	<script type="text/javascript" src="//www.hereby.co.kr/nearhere/js//jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://script.gmarket.co.kr/js/mobile/main/common/handlebars-v1.1.2.js"></script>

    <style type="text/css">
    	
    	* { margin:0;padding:0; }

    	/* 모달 dialog */

		.modal {
		    display: none; /* Hidden by default */
		    position: fixed; /* Stay in place */
		    z-index: 1; /* Sit on top */
		    padding-top: 100px; /* Location of the box */
		    left: 0;
		    top: 0;
		    width: 100%; /* Full width */
		    height: 100%; /* Full height */
		    overflow: auto; /* Enable scroll if needed */
		    background-color: rgb(0,0,0); /* Fallback color */
		    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
		}

		/* Modal Content */
		.modal-content {
		    background-color: #fefefe;
		    margin: auto;
		    padding: 20px;
		    border: 1px solid #888;
		    width: 80%;
		}

		/* The Close Button */
		.close {
		    color: #aaaaaa;
		    float: right;
		    font-size: 28px;
		    font-weight: bold;
		}

		.close:hover,
		.close:focus {
		    color: #000;
		    text-decoration: none;
		    cursor: pointer;
		}

    	/* 모달 dialog */


    	#wrapper{
    		margin:10px;
    	}

    	#workseq{ float:right; }

    	ul{
    		list-style: none;
    		margin-top:10px;
    	}

    	li{
    		padding:1px;
    		border-top: 1px solid black;
    		border-left: 1px solid black;
    		border-right: 1px solid black;
    		border-bottom: 1px solid black;
    		margin-top: 5px;
    		margin-bottom: 5px;
    		overflow: auto;
    	}

    	.room{
    		padding:5px;
    	}

    	.btns{
    		width:100%;
    	}

    	.btnItem{
    		float:left;
    		width:45%;
    		margin:5px 5px;
    		padding:5px 0px;
    		line-height:30px;
    		text-align:center;
    	}

		.selected{
    		background:black;
    		color:white;
    	}
    	
		.etc{
			margin:10px 0px;
            border:1px solid black;
            padding:10px;
            overflow:auto;
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
    	
    	var workSeq = '<%= workSeq %>';
        var data = {};
        var roomStatus = {};

    	var modal = null;

    	jQuery(document).ready(function(){
			try {

				Handlebars.registerHelper("inc", function(value, options)
				{
				    return parseInt(value) + 1;
				});

                Handlebars.registerHelper("checkStatus", function(value, options)
                {
                    if ( value == 'Y' )
                        return 'selected';
                    return '';
                });
                
                loadRoomList();


                /*
				// Get the modal
				modal = document.getElementById('myModal');

				// Get the <span> element that closes the modal
				var span = document.getElementsByClassName("close")[0];

				// When the user clicks on <span> (x), close the modal
				span.onclick = function() {
				    modal.style.display = "none";
				}

				// When the user clicks anywhere outside of the modal, close it
				window.onclick = function(event) {
				    if (event.target == modal) {
				        modal.style.display = "none";
				    }
				}
				*/


			} catch ( ex ) {
			alert( ex.message );
			}
    	});

    	function loadRoomList(){
    		var param = {};
    		
    		jQuery.ajax({
    			type : "POST",
    			url : "/karaoke/alba/getRoomList.do",
    			data : JSON.stringify( param ),
    			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    			contentType : "application/json; charset=UTF-8",
    			success : function(result) {
    				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
    				// TODO
    				try {

    					data['roomList'] = null;
    					data.roomList = result.data.data;

						loadRoomStatus();
    					
    				} catch (ex) {
    					alert(ex.message);
    				}
    			},
    			error : function(xhr, status, error) {
    				alert("에러발생(getCityList)" + error );
    			}
    		});
    	}
    	
    	function loadRoomStatus(){
    		var param = {};
    		
    		jQuery.ajax({
    			type : "POST",
    			url : "/karaoke/alba/getRoomStatus.do",
    			data : JSON.stringify( param ),
    			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    			contentType : "application/json; charset=UTF-8",
    			success : function(result) {
    				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
    				// TODO
    				try {

    					roomStatus['jobList'] = result.data.data;
    					roomStatus['details'] = null;
    					
    					for ( var i = 0; i < data.roomList.length; i++ ) {
    	                    var room = data.roomList[i];

    	                    for ( var j = 0; j < roomStatus.jobList.length; j++ ) {
    	                        var job = roomStatus.jobList[j];

    	                        if ( room.roomNo == job.roomNo ){
    	                            if ( room.details == null ) room.details = [];

    	                            room.details.push( job );
    	                        }
    	                    }
    	                }

    					var source = $('#room-template').html();
    					var template = Handlebars.compile(source);
    					var html    = template(data);
    					$('#roomList').html(html);

    					$('#roomList').show();
    					
    					loadHallStatus();
    					
    				} catch (ex) {
    					alert(ex.message);
    				}
    			},
    			error : function(xhr, status, error) {
    				alert("에러발생(getCityList)" + error );
    			}
    		});
    	}
    	
    	function loadHallStatus(){
    		var param = {};
    		
    		jQuery.ajax({
    			type : "POST",
    			url : "/karaoke/alba/getEtcStatus.do",
    			data : JSON.stringify( param ),
    			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    			contentType : "application/json; charset=UTF-8",
    			success : function(result) {
    				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
    				// TODO
    				try {
    					
    					source = $('#etc-template').html();
    	                template = Handlebars.compile(source);
    	                html    = template(result.data);
    	                $('#etcItems').html(html);
    					
    				} catch (ex) {
    					alert(ex.message);
    				}
    			},
    			error : function(xhr, status, error) {
    				alert("에러발생(getCityList)" + error );
    			}
    		});
    	}
    	
    	function showRoom( roomNo ) {
    		$(modal).show();
    	}
    	
    	function showRemained(){
    		$('.selected').hide();
    		$('#btnShowAll').show();
    	}

    	function showAll(){
    		$('.selected').show();
    		$('#btnShowAll').hide();
    	}

    	function updateRoomStatus( div ){
    		var param = {"roomNo": $(div).attr("roomNo")
    				, "jobNo" : $(div).attr("jobNo")
    				, "workSeq": workSeq
    				, "cleanYN" : $(div).hasClass('selected') ? 'N':'Y'
    		}
    		
    		if ( $(div).hasClass('selected') )
    			$(div).removeClass('selected');
    		else
    			$(div).addClass('selected');
    		
			jQuery.ajax({
    			type : "POST",
    			url : "/karaoke/alba/updateRoomStatus.do",
    			data : JSON.stringify( param ),
    			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    			contentType : "application/json; charset=UTF-8",
    			success : function(result) {
    				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
    				// TODO
    				try {
    					
						if ( result.resCode == '0000' ) {
    						
    					}
    					else {
    					
    						alert( result.resMsg );
    						
    						if ( $(div).hasClass('selected') )
        		    			$(div).removeClass('selected');
        		    		else
        		    			$(div).addClass('selected');
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
    	
    	function updateEtcStatus( div ){
    		var param = {"jobNo" : $(div).attr("jobNo")
    				, "workSeq": workSeq
    				, "cleanYN" : $(div).hasClass('selected') ? 'N':'Y'
    		}
    		
    		if ( $(div).hasClass('selected') )
    			$(div).removeClass('selected');
    		else
    			$(div).addClass('selected');
    			
    		jQuery.ajax({
    			type : "POST",
    			url : "/karaoke/alba/updateEtcStatus.do",
    			data : JSON.stringify( param ),
    			dataType : "JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
    			contentType : "application/json; charset=UTF-8",
    			success : function(result) {
    				// 통신이 성공적으로 이루어졌을 때 이 함수를 타게 된다.
    				// TODO
    				try {
    					
    					if ( result.resCode == '0000' ) {
    						
    					}
    					else {
    					
    						alert( result.resMsg );
    						
    						if ( $(div).hasClass('selected') )
        		    			$(div).removeClass('selected');
        		    		else
        		    			$(div).addClass('selected');
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
    	
    	function closeWindow() {
    		document.location.href = 'android://finishActivity';
    	}

    </script>

    <script id="room-template" type="text/x-handlebars-template">
      <ul>
      {{#each roomList}}
        <li>
			<div class="room">

				<div>룸{{roomNo}}</div>

				<div class="btns">
					{{#each details}}
					<div class="btnItem {{checkStatus cleanYN}}" roomNo="{{roomNo}}" jobNo="{{jobNo}}" onclick="updateRoomStatus(this);">
						{{jobName}}
                    </div>
					{{/each}}
					<!--div class="btnItem"><input type="button" onclick="showRoom({{roomNo}});" value="상세" /></div-->
				</div>

				
			</div>
		</li>
      {{/each}}
      </ul>
    </script>

    <script id="etc-template" type="text/x-handlebars-template">
      {{#each data}}
        <div>
            <div class="btnItem {{checkStatus cleanYN}}" jobNo="{{jobNo}}" onclick="updateEtcStatus(this);">
				{{jobName}}
            </div>
        </div>
      {{/each}}
    </script>
    
  </head>
  <body>
  	<div id="wrapper">
  		
  		<div id="workseq">근무 seq : <%= workSeq %></div>
		룸 청소현황
  		<div id="roomList" style="display:none;width:100%">
  		</div>

		기타 청소현황
  		<div id="etcItems" class="etc">
  		</div>

		<div id="btnShowRemained" onclick="showRemained();" class="btnFull">완료된 항목 숨기기</div>
		<div id="btnShowAll" onclick="showAll();" class="btnFull" style="display:none;">전체 보기</div>
		<div id="btnClose" onclick="closeWindow();" class="btnFullRed">닫기</div>

  	</div>

  	<!-- The Modal -->
	<div id="myModal" class="modal">

	  <!-- Modal content -->
	  <div class="modal-content">
	    <span class="close">&times;</span>
	    <p>Some text in the Modal..</p>
	  </div>

	</div>

  </body>
</html>