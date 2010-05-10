
<%@ page import="grudge.Diagram" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'diagram.label', default: 'Diagram')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'diagram.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'diagram.title.label', default: 'Title')}" />
                        
                            <g:sortableColumn property="logicalSpecificationHash" title="${message(code: 'diagram.logicalSpecificationHash.label', default: 'Logical Specification Hash')}" />
                        
                            <g:sortableColumn property="logicalSpecification" title="${message(code: 'diagram.logicalSpecification.label', default: 'Logical Specification')}" />
                        
                            <g:sortableColumn property="layoutSpecification" title="${message(code: 'diagram.layoutSpecification.label', default: 'Layout Specification')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${diagramInstanceList}" status="i" var="diagramInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${diagramInstance.id}">${fieldValue(bean: diagramInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: diagramInstance, field: "title")}</td>
                        
                            <td>${fieldValue(bean: diagramInstance, field: "logicalSpecificationHash")}</td>
                        
                            <td>${fieldValue(bean: diagramInstance, field: "logicalSpecification")}</td>
                        
                            <td>${fieldValue(bean: diagramInstance, field: "layoutSpecification")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${diagramInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
