<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Ajouter passeport">
    <div class="card" style="max-width: 760px;">
        <h1>Ajouter passeport</h1>
        <p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom}</p>

        <form method="post" action="${pageContext.request.contextPath}/passeports">
            <input type="hidden" name="demandeurId" value="${demandeur.id}">

            <div class="grid">
                <div class="field">
                    <label for="numero">Numero</label>
                    <input id="numero" name="numero" type="text" placeholder="Ex : PASS0001" required>
                </div>
                <div class="field">
                    <label for="delivrance">Date de delivrance</label>
                    <input id="delivrance" name="delivrance" type="date" required>
                </div>
                <div class="field">
                    <label for="expiration">Date d'expiration</label>
                    <input id="expiration" name="expiration" type="date" required>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Enregistrer</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Annuler</a>
            </div>
        </form>
    </div>
</layout:page>