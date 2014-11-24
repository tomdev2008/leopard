<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>性能监控 - Leopard监控系统</title>
    
    <#include "inc/js.inc.ftl">
    
</head>
<body>
    <div class="wrapper"> 
            
        <#include "inc/menu.inc.ftl">

        <div class="content">

			<#include "inc/nav.inc.ftl">
            
            <div class="workplace">
				<!-- 
                <div class="row-fluid">
                    <div class="span12">

                        <div class="widgetButtons">                        
                            <div class="bb"><a href="index.html#" class="tipb" title="Add new"><span class="ibw-plus"></span></a></div>
                            <div class="bb"><a href="index.html#" class="tipb" title="Edit"><span class="ibw-edit"></span></a></div>
                            <div class="bb">
                                <a href="index.html#" class="tipb" title="Upload"><span class="ibw-folder"></span></a>
                                <div class="caption red">31</div>
                            </div>
                            
                            <div class="bb"><a href="index.html#" class="tipb" title="Add to favorite"><span class="ibw-favorite"></span></a></div>
                            <div class="bb">
                                <a href="index.html#" class="tipb" title="Send mail"><span class="ibw-mail"></span></a>
                                <div class="caption green">31</div>
                            </div>
                            <div class="bb"><a href="index.html#" class="tipb" title="Settings"><span class="ibw-settings"></span></a></div>
                        </div>

                    </div>
                </div>
                 

                         

                <div class="dr"><span></span></div> 
                -->


                <div class="row-fluid">

                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>监控数据 >> ${typeName}</h1>      
                            <ul class="buttons">
                            	<li>
                                    <a href="index.html#" class="isw-tab" style="width:132px;text-align:center" title="按监控入口查看统计数据">按入口</a>
                                    <ul class="dd-list" style="right: 153px;">
                                    	<li><a href="performance.do?entryName="><span class="isw-edit"></span> 全部</a></li>
                                    	<#list entryNameList as entryName>
							            <li><a href="performance.do?entryName=${entryName!}"><span class="isw-edit"></span> ${entryName}</a></li>
							            </#list>
                                    </ul>
                                </li>
                                
                                <li>
                                    <a href="index.html#" class="isw-tab" style="width:132px;text-align:center" title="查看历史记录">历史数据</a>
                                    <ul class="dd-list" style="right: 0px;">
                                    	<#list postdateList as postdate>
							            <li><a href="performance.do?entryName=${entryName!}&postdate=${postdate!}"><span class="isw-edit"></span> ${postdate}</a></li>
							            </#list>
                                    </ul>
                                </li>

                            </ul>       
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                	<tr>
                                        <th nowrap="nowrap" colspan="4" style="text-align: center;">耗时</th>
                                        <th nowrap="nowrap" colspan="2" style="text-align: center;">次数</th>
                                        <th nowrap="nowrap" colspan="3" style="text-align: center;">比率</th>                                        
                                        <th nowrap="nowrap" colspan="2" style="text-align: center;">接口</th>
                                    </tr>
                                    <tr>
                                    	<th nowrap="nowrap" style="text-align: center;" width="32">状态</th>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=totalTime')}">总毫秒</a></th>
                                    	<th nowrap="nowrap" style="text-align: center;">总秒</th>
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=avgTime')}">单次(ms)</a></th>                                
                                    	
                                    	<th nowrap="nowrap" style="text-align: center;"><a href="${replaceParam('order=totalCount')}">总次数</a></th>
                                    	<th nowrap="nowrap" style="text-align: center;">次数/秒</th>     	

                                    	<th nowrap="nowrap" style="text-align: center;" alt="(当前方法耗时/该接口所有方法耗时)*100%">接口</th>
	                                    <th nowrap="nowrap" style="text-align: center;" alt="(当前方法耗时/当前模块的总耗时)*100%">模块</th>
	                                    <th nowrap="nowrap" style="text-align: center;" alt="(当前方法耗时/所有Controller入口耗时)*100%">全部</th>                                        		
                                        
                                    	<th nowrap="nowrap" style="text-align: center;">分类</th>
                                    	<th nowrap="nowrap" style="text-align: center;">方法名称</th>
                                    </tr>
                                    
                                </thead>
                                
                                <tbody>
                                	<#if performanceVOList??>
                                	<#list performanceVOList as performanceVO>
                                    <tr>
                                        <td style="text-align: center;"><img src="http://leopard.game.yy.com/resources/img/icons/monitor/ic_ok.png" /></td>
                                        <td>${performanceVO.totalMilliSeconds}</td>
                                        <td>${performanceVO.totalSeconds}</td>
                                        <td <#if anomalyDetector.checkAvgTime(performanceVO)>style="color:red;"</#if>>${performanceVO.avgTime}ms</td>
                                        
                                        <td>${performanceVO.count}</td>
                                        <td>${performanceVO.avgCount}</td>
                                        
                                        
                                        <td><a title="${performanceVO.interfaceName}">${performanceVO.interfaceRatio}%</a></td>
                                        <td><a title="${performanceVO.moduleName}">${performanceVO.moduleRatio}%</a></td>
                                        <td>${performanceVO.timeRatio}%</td>
                                        
                                        <td>${performanceVO.categoryName!}</td>
                                        <td>${performanceVO.simpleMethodName}</td>
                                    </tr>
                                    </#list>
                                    </#if>


                                </tbody>
                            </table>
                            <script type="text/javascript">
                            	//Jtest.checkState('feedback');
                            	//Jtest.checkState('news');
                            </script>
                        </div>
                    </div>                                
                </div>


                <div class="dr"><span></span></div>

            </div>

        </div>   
    </div>
    
    
    
    


</body>
</html>
