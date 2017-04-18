<#include "base.ftl">
<#macro page_body>
    <#import "/spring.ftl" as spring />
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Добавить врача:</h3>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Логин" name="login" type="text"
                                       autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Пароль" name="password" type="password"
                                       value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Подтверждение пароля" name="passwordConfirm"
                                       type="password"
                                       value="">
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary btn-block">Создать</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</#macro>
<@display_page/>

