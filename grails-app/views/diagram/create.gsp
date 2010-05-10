
<%@ page import="grudge.Diagram" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'diagram.label', default: 'Diagram')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${diagramInstance}">
            <div class="errors">
                <g:renderErrors bean="${diagramInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="diagram.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: diagramInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" maxlength="80" value="${diagramInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="logicalSpecificationHash"><g:message code="diagram.logicalSpecificationHash.label" default="Logical Specification Hash" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: diagramInstance, field: 'logicalSpecificationHash', 'errors')}">
                                    <g:textField name="logicalSpecificationHash" value="${diagramInstance?.logicalSpecificationHash}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="logicalSpecification"><g:message code="diagram.logicalSpecification.label" default="Logical Specification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: diagramInstance, field: 'logicalSpecification', 'errors')}">
                                    <g:textArea name="logicalSpecification" cols="40" rows="5" value="${diagramInstance?.logicalSpecification}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="layoutSpecification"><g:message code="diagram.layoutSpecification.label" default="Layout Specification" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: diagramInstance, field: 'layoutSpecification', 'errors')}">
                                    <g:textArea name="layoutSpecification" cols="40" rows="5" value="${diagramInstance?.layoutSpecification}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
