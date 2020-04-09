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
                    <a href="/user/index" class="btn btn-info btn-xs ml-auto">Go Back</a>
                </div>
                <div class="card-body">
                    <p class="card-text"><span class="text-muted">Firstname :</span> ${item.firstname}</p>
                    <p class="card-text"><span class="text-muted">Lastname :</span> ${item.lastname}</p>
                </div>
            </div>
            <div class="card mt-4">
                <div class="card-header">
                    Associated Products List
                </div>
                <div class="card-body">
                    <#if (item.products?size > 0)>
                        <table class="table table-hover">
                            <thead>
                            <tr class="bg-light">
                                <th>Name</th>
                                <th>Price</th>
                                <th class="text-center min">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list item.products as product>
                                <tr>
                                    <td class="align-middle">${product.name}</td>
                                    <td class="align-middle">${product.price}</td>
                                    <td class="text-right align-middle min">
                                        <div class="d-flex align-items-center justify-content-center">
                                            <a href="/product/show/${product["id"]}">
                                                <button class="btn btn-primary btn-sm">
                                                    <i class="material-icons">visibility</i>
                                                </button>
                                            </a>
                                        </div>

                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    <#else>
                        <p class="card-text text-muted">Pas de produits associées pour le moment</p>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>


</body>