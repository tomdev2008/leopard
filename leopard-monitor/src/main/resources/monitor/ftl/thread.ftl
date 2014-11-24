<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>线程数量 - Leopard监控系统</title>
    
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
                            <h1>线程数量</h1>
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                    <tr>
                                    	<th nowrap="nowrap" style="width:26px">序号</th>
                                    	<th nowrap="nowrap" style="width:63px">分类</th>
                                    	<th nowrap="nowrap" style="width:30px">数量</th>
                                    	<th nowrap="nowrap" style="">线程名称</th>
                                    	<th nowrap="nowrap" style="width:100px">备注</th>
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                	<#if threadInfoList??>
                                	<#list threadInfoList as threadInfo>
                                	<tr>
                                		<td>${threadInfo_index+1}</td>
                                		<td>${threadInfo.category!}</td>
                                		<td>${threadInfo.currentSize}</td>
                                		<td>${threadInfo.threadName}</td>
                                		<td>${threadInfo.content!}</td>
                                	</tr>
                                	</#list>
                                    </#if>
                                	
                                </tbody>
                            </table>
                        </div>
                    </div>                                
                </div>
                <div class="dr"><span></span></div>

            </div>

        </div>   
    </div>
    
</body>
</html>
