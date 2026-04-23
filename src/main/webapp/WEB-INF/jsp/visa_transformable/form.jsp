<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<c:set var="editMode" value="${not empty visaTransformable.id}" />
<c:set var="formAction" value="${editMode ? pageContext.request.contextPath.concat('/visa_transformables/').concat(visaTransformable.id).concat('/edit') : pageContext.request.contextPath.concat('/visa_transformables')}" />

<layout:page title="${editMode ? 'Modifier visa transformable' : 'Ajouter visa transformable'}">
    <div class="card" style="max-width: 760px;">
        <h1>${editMode ? 'Modifier visa transformable' : 'Ajouter visa transformable'}</h1>
        <p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom} | Passeport: ${passeport.numero}</p>

        <form method="post" action="${formAction}">
            <input type="hidden" name="passeportId" value="${passeport.id}">
            <div class="grid">
                <div class="field">
                    <label for="reference">Reference</label>
                    <input id="reference" name="reference" type="text" value="${visaTransformable.reference}" placeholder="Ex:VT0001" required>
                </div>
            </div>
            <div class="grid">
                <div class="field">
                    <label for="dateEntree">Date d'entree</label>
                    <input id="dateEntree" name="dateEntree" type="date" value="${visaTransformable.dateEntree}" required>
                </div>
                <div class="field">
                    <label for="lieu">Lieu</label>
                    <input id="lieu" name="lieu" type="text" value="${visaTransformable.lieu}" placeholder="Ex : Antananarivo" required>
                </div>
                <div class="field">
                    <label for="expiration">Date d'expiration</label>
                    <input id="expiration" name="expiration" type="date" value="${visaTransformable.expiration}" required>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">${editMode ? 'Mettre a jour' : 'Enregistrer'}</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Annuler</a>
            </div>
        </form>
    </div>
</layout:page>
