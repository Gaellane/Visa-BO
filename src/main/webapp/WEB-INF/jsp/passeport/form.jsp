<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<c:set var="editMode" value="${not empty passeport.id}" />
<c:set var="formAction" value="${editMode ? pageContext.request.contextPath.concat('/passeports/').concat(passeport.id).concat('/edit') : pageContext.request.contextPath.concat('/passeports')}" />

<layout:page title="${editMode ? 'Modifier passeport' : 'Ajouter passeport'}">
    <div class="card" style="max-width: 760px;">
        <h1>${editMode ? 'Modifier passeport' : 'Ajouter passeport'}</h1>
        <p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom}</p>

        <form method="post" action="${formAction}">
            <input type="hidden" name="demandeurId" value="${demandeur.id}">

            <div class="grid">
                <div class="field">
                    <label for="numero">Numero</label>
                    <input id="numero" name="numero" type="text" value="${passeport.numero}" placeholder="Ex : PASS0001" required>
                </div>
                <div class="field">
                    <label for="delivrance">Date de delivrance</label>
                    <input id="delivrance" name="delivrance" type="date" value="${passeport.delivrance}" required>
                </div>
                <div class="field">
                    <label for="expiration">Date d'expiration</label>
                    <input id="expiration" name="expiration" type="date" value="${passeport.expiration}" required>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">${editMode ? 'Mettre a jour' : 'Enregistrer'}</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Annuler</a>
            </div>
        </form>
    </div>
</layout:page>