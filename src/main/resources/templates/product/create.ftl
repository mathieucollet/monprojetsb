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


                        <form action="<@spring.url '/product/create'/>" method="POST">

                            <div class="form-group">
                                <label for="">Name :</label>
                                <input type="text" name="name" value="" class="form-control">
                            </div>

                            <div class="form-group">
                                <label for="">Price :</label>
                                <input type="text" name="price" value="" class="form-control">
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