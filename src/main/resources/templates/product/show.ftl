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
                        <p class="card-text"><span class="text-muted">Name :</span> ${item.name}</p>
                        <p class="card-text"><span class="text-muted">Price :</span> ${item.price}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>