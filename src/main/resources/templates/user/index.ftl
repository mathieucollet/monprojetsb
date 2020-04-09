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

                    <a href="create" class="btn btn-primary btn-xs ml-auto">Create new</a>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <table class="table table-hover">
                            <thead>
                            <tr class="bg-light">

                                <th>Firstname</th>

                                <th>Lastname</th>

                                <th class="text-center min">Products</th>

                                <th class="text-center">Actions</th>

                            </tr>
                            </thead>

                            <tbody>
                            <#list items as item>

                                <tr>

                                    <td class="align-middle">${item.firstname}</td>

                                    <td class="align-middle">${item.lastname}</td>

                                    <td class="align-middle text-center min">${item.products?size}</td>

                                    <td class="text-right align-middle min">
                                        <div class="d-flex align-items-center">
                                            <a href="show/${item["id"]}" class="mr-2">
                                                <button class="btn btn-primary btn-sm">
                                                    <i class="material-icons">visibility</i>
                                                </button>
                                            </a>


                                            <form action="delete" method="POST" class="mb-0">

                                                <input type="hidden" name="id" value="${item["id"]}">

                                                <button type="submit" class="btn btn-danger btn-sm" value="delete">
                                                    <i class="material-icons">delete_outline</i>
                                                </button>

                                            </form>
                                        </div>

                                    </td>

                                </tr>

                            </#list>
                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>