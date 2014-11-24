<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>数据源连接数量 - Leopard监控系统</title>
    
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
                            <h1>数据源连接数量</h1>
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                    <tr>
                                    	<th nowrap="nowrap" style="width:60px">类型</th>
                                    	<th nowrap="nowrap" style="width:26px">当前</th>
                                    	<th nowrap="nowrap" style="width:26px">峰值</th>
                                    	<th nowrap="nowrap" style="width:45px">poolSize</th>
                                    	<th nowrap="nowrap" style="width:50px"><a href="?order=connectionCount">次数</a></th>
                                    	<th nowrap="nowrap" style="width:26px">出错</th>
                                    	<th nowrap="nowrap" style="width:40px"><a href="?order=totalTime">总耗时</a></th>
                                    	<th nowrap="nowrap" style="width:55px"><a href="?order=avgTime">平均耗时</a></th>
                                    	<th nowrap="nowrap" style="width:55px"><a href="?order=connectionTime">连接耗时</a></th>
                                    	<th nowrap="nowrap" style="width:55px"><a href="?order=avgConnectionTime">平均连接</a></th>
                                    	<th nowrap="nowrap" style="width:250px">Host</th>
                                    	<th nowrap="nowrap" style="width:40px">端口</th>
                                    	<th nowrap="nowrap" style="">备注</th>
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                	
                                	<#if connectionInfoList??>
                                	<#list connectionInfoList as connectionInfo>
                                	<tr>
                                		<td>${connectionInfo.type}</td>
                                		<td>${connectionInfo.currentSize}</td>
                                		<td><a title="${connectionInfo.peakTime?string('yyyy-MM-dd hh:mm:ss')}">${connectionInfo.peakSize}</a></td>
                                		<td>${connectionInfo.maxPoolSize}</td>
                                		<td>${connectionInfo.connectionCount}</td>
                                		<td>${connectionInfo.brokenCount}</td>
                                		<td>${connectionInfo.totalTimeStr}</td>
                                		<td>${connectionInfo.avgTime}ms</td>
                                		<td>${connectionInfo.connectionTimeStr}</td>
                                		<td>${connectionInfo.avgConnectionTime}ms</td>
                                		<td>${connectionInfo.host}</td>
                                		<td>${connectionInfo.port?c}</td>
                                		<td>${connectionInfo.content}</td>
                                	</tr>
                                	</#list>
                                    </#if>
                                	
                                </tbody>
                            </table>
                        </div>
                    </div>                                
                </div>
                <div class="dr"><span></span></div>
                
                <!-- 
                <div class="row-fluid">

                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>调用者</h1>
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                    <tr>
                                    	<th nowrap="nowrap" style="width:26px">序号</th>
                                    	<th nowrap="nowrap" style="width:120px">开始时间</th>
                                    	<th nowrap="nowrap" style="width:60px">耗时(ms)</th>
                                    	<th nowrap="nowrap" style="">调用者</th>
                                    </tr>
                                    
                                </thead>
                                <tbody>
                                	<tr>
                                		<td>1</td>
                                		<td>2013-01-01 00:00:00</td>
                                		<td>10</td>
                                		
                                		<td>com.duowan.news.dao.mysql.UserDaoMysqlImpl.get</td>
                                	</tr>
                                	<tr>
                                		<td>2</td>
                                		<td>2013-01-01 00:00:00</td>
                                		<td>10</td>
                                		
                                		<td>com.duowan.news.dao.mysql.UserDaoMysqlImpl.get</td>
                                	</tr>
                                	
                                	<tr>
                                		<td>3</td>
                                		<td>2013-01-01 00:00:00</td>
                                		<td>10</td>
                                		
                                		<td>com.duowan.news.dao.mysql.UserDaoMysqlImpl.get</td>
                                	</tr>
                                </tbody>
                            </table>
                        </div>
                    </div>                                
                </div>
                <div class="dr"><span></span></div>
                 -->

            </div>

        </div>   
    </div>
    
</body>
</html>
