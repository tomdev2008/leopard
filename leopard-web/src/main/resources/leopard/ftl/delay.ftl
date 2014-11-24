<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>页面随机延迟 - Leopard管理系统</title>
    
</head>
<body>
    <script type="text/javascript">
    	function config(on) {
    		document.location.href = "delayConfig.do?on="+on;
    	}
    </script>
    页面随机延迟0-5秒，当前状态:
    <#if close>
            关闭 <input type="button" value="开启" onclick="config(true)"/>
    <#else>
            开启  <input type="button" value="关闭" onclick="config(false)"/>
    </#if>
    
    <br/><br/>
    <i>页面随机延迟功能，可以用来模拟测试客户端、前端，在遇到糟糕的网络情况下的用户体验。</i><br/>
    <i>如，是否有数据加载中的提示。</i><br/>
    
</body>
</html>
