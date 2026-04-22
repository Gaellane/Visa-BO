<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fiche demande</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 24px; }
        h2 { margin-top: 24px; }
        .row { margin-bottom: 10px; }
        .label { font-weight: bold; display: inline-block; width: 160px; }
    </style>
</head>
<body>
    <h2>Info demandeur</h2>

    <div class="row">
        <span class="label">Identifiant:</span>
        <c:choose>
            <c:when test="${demande.demandeur != null}">
                ${demande.demandeur.id} - ${demande.demandeur.nom} ${demande.demandeur.prenom}
            </c:when>
            <c:otherwise>-</c:otherwise>
        </c:choose>
    </div>

    <div class="row">
        <span class="label">Type de demande:</span>
        <c:choose>
            <c:when test="${demande.type != null}">
                ${demande.type.valeur}
            </c:when>
            <c:otherwise>-</c:otherwise>
        </c:choose>
    </div>

    <div class="row">
        <span class="label">Type de visa:</span>
        <c:choose>
            <c:when test="${demande.typeVisa != null}">
                ${demande.typeVisa.valeur}
            </c:when>
            <c:otherwise>-</c:otherwise>
        </c:choose>
    </div>

    <h2>Passeport</h2>
    <c:choose>
        <c:when test="${passeport != null}">
            <div class="row">
                <span class="label">Numero:</span>
                ${passeport.numero}
            </div>
            <div class="row">
                <span class="label">Delivrance:</span>
                ${passeport.delivrance}
            </div>
            <div class="row">
                <span class="label">Expiration:</span>
                ${passeport.expiration}
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">-</div>
        </c:otherwise>
    </c:choose>

    <h3>Piece justificatives</h3>
    <c:choose>
        <c:when test="${not empty pieces}">
            <ul>
                <c:forEach items="${pieces}" var="piece">
                    <li>${piece.piece.nomPiece} - ${piece.piece.oligatoire}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <div class="row">-</div>
        </c:otherwise>
    </c:choose>
</body>
</html>