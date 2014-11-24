<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>命令行程序执行 - Leopard管理系统</title>
    
</head>
<body>
    <script type="text/javascript">
    	function run(command) {
    		document.location.href = "/leopard/commandExecute.do?command="+command;
    	}
    </script>
     可执行的命令:
     </br>
	 <#list commandList as command>
		${command} <input type="button" value="执行" onclick="run('${command}')"/>
	 </#list>
  
    
    <br/><br/>
   
    
</body>
</html>
