<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des demandes</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 24px; }
        h1 { margin-bottom: 16px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f5f5f5; }
        tr:nth-child(even) { background: #fafafa; }
    </style>
</head>
<body>
    <h1>Liste des demandes</h1>

    <table>
        <thead>
            <tr>
                <th>dateDemande</th>
                <th>demandeur</th>
                <th>type</th>
                <th>typeVisa</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${demandes}" var="demande">
                <tr>
                    <td>${demande.dateDemande}</td>
                    <td>
                        <c:choose>
                            <c:when test="${demande.demandeur != null}">
                                ${demande.demandeur.id} - ${demande.demandeur.nom} ${demande.demandeur.prenom}
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${demande.type != null}">
                                ${demande.type.id} - ${demande.type.valeur}
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${demande.typeVisa != null}">
                                ${demande.typeVisa.id} - ${demande.typeVisa.valeur}
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>