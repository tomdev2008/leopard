<div class="header">
            <a class="logo" href="performance.do" style="color:#fff;font-size:20px;">Leopard监控系统 - 快速定位业务系统性能问题</a> 
            <script type="text/javascript">
              	var Cookie = {
              		"addCookie" : function(name, value) {
              			var str = name + "=" + escape(value);
              			str += ";path=/";
              			document.cookie = str;
              		},
              		"end":""
              	}
              	function selectResinServer(resinIp) {
              		Cookie.addCookie("resinIp", resinIp);
              		document.location.href = document.location.href; 
              	}
            </script>
            <ul class="header_menu">
                <li class="list_icon"><a href="index.do#">&nbsp;</a></li>
                
                
                <li class="xxx">
                	<#if showResinIpSelect>
                	<span style="color:#fff;font-size:16px">指定服务器:</span><select id="resinIp" onchange="selectResinServer(this.value)">
	                   	<option value="">随机</option>
	                   	<#list resinIpList as resinIp>
	                   	<option value="${resinIp}" <#if resinIp==serverIp>selected="selected"</#if>>${resinIp}</option>
	                   	</#list>
	                 </select>
	                 </#if>

                </li>
            </ul>    
        </div>