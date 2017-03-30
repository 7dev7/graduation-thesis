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

<nav class="navbar navbar-default  navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Главная</a></li>
                <li><a href="#">Контакты</a></li>
                <#if user??>
                    <li><a href="#">${user}</a></li>
                    <li><a href="/logout">Выйти</a></li>
                </#if>
            </ul>
        </div>
    </div>
</nav>

    <@page_body/>
<script src="/js/jquery-3.2.0.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
</#macro>