<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>日志管理 - Leopard监控系统</title>
    
    <#include "inc/js.inc.ftl">
    
</head>
<body>
    <div class="wrapper"> 
            
        <#include "inc/menu.inc.ftl">

        <div class="content">

			<#include "inc/nav.inc.ftl">
            
            <div class="workplace">
				
                <div class="row-fluid">

                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>日志管理</h1>
                        </div>
                        <form action="">
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                    <tr>
                                    	<th nowrap="nowrap" style="width:26px">文件</th>
                                    	<th nowrap="nowrap" style="width:63px">级别</th>
                                    	<th nowrap="nowrap" style="width:80%">&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<#if logInfoList??>
                                	<#list logInfoList as logInfo>
                                	<tr>
                                		<td>${logInfo.filename}</td>
                                		<td><select name="${logInfo.filename}">
                                			<#if levelList??>
                                			<#list levelList as level>
                                			<option value="${level}"  <#if logInfo.level==level>selected="selected"</#if>>${level}</option>
                                			</#list>
                                    		</#if>
                                		</select></td>
                                		<td> </td>
                                	</tr>
                                	</#list>
                                    </#if>
                                	
                                </tbody>
                            </table>
                            
                            
                        </div>
                        
                        <div class="widget">

			                <div class="input-append">
			                    <button class="btn" type="submit">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                    <button class="btn" type="reset">重置</button>
			                </div>            
			
			            </div>
			            
			            </form>
                    </div>                                
                </div>
                <div class="dr"><span></span></div>

            </div>

        </div>   
    </div>
    
</body>
</html>
