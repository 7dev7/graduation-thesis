<#macro page_head>
<title>Regression</title>
<link rel="stylesheet" href="/css/bootstrap.css">
</#macro>

<#macro page_body>
<h1>Basic Page</h1>
<p>This is the body of the page!</p>
</#macro>

<#macro display_page>
<!doctype html>
<html>
<head>
    <@page_head/>
</head>
<body>
    <@page_body/>
<script src="/js/jquery-3.2.0.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
</#macro>