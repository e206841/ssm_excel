<%@ page contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
<meta charset="utf-8">  
<title>文件上传下载</title>  
</head>  
<body>  
    <form action="excel/upload"  method="post" enctype="multipart/form-data">  
    	上传明细文件：<input type="file" name="file1" />  明细文件工作表名称:<input type="text" name="file1SheetName" /><br>
    	上传快递单      ：<input type="file" name="file2" /> 快递单文件工作表名称:<input type="text" name="file2SheetName" /><br>
    	
        <input type="submit" value="上传文件"/>  
    </form>  
    <form action=""
        method="get">
        <input type="submit" value="下载">
    </form>
</body>  
</html>  