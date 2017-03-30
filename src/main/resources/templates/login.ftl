<#include "base.ftl">
<#macro page_body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Вход:</h3>
                </div>
                <#if (error)??>
                    <div class="control-group error">
                        <p class="control-label">${error!""}</p>
                    </div>
                </#if>
                <div class="panel-body">
                    <form role="form" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Логин" name="username" type="text"
                                       autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Пароль" name="password" type="password"
                                       value="">
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="Запомнить">Запомнить
                                </label>
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary btn-block">Войти</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</#macro>
<@display_page/>

