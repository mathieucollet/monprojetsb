<#import "/spring.ftl" as spring/>

<head>
    <#include "../includable/bootstrap.ftl">
    <style>
        <#include "../includable/custom.css">
    </style>
</head>

<body>
<#include "../includable/nav.ftl">
<div class="container">
    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <#if page??>
                        ${page}
                    </#if>
                    <a href="/product/index" class="btn btn-info btn-xs ml-auto">Go Back</a>
                </div>
                <div class="card-body">
                    <div class="card-text">


                        <form action="<@spring.url '/user/create'/>" method="POST">

                            <div class="form-group">
                                <label for="">Firstname :</label>
                                <input type="text" name="firstname" value="" class="form-control">
                            </div>

                            <div class="form-group">
                                <label for="">Lastname :</label>
                                <input type="text" name="lastname" value="" class="form-control">
                            </div>

                            <input class="btn btn-primary float-right" type="submit" value="Submit">

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>