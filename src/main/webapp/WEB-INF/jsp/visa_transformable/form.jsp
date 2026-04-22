<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Ajouter visa transformable">
    <div class="card" style="max-width: 760px;">
        <h1>Ajouter visa transformable</h1>
        <p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom} | Passeport: ${passeport.numero}</p>

        <form method="post" action="${pageContext.request.contextPath}/visa_transformables">
            <input type="hidden" name="passeportId" value="${passeport.id}">
            <div class="grid">
                <div class="field">
                    <label for="reference">Reference</label>
                    <input id="reference" name="reference" type="text" placeholder="Ex:VT0001" required>
                </div>
            </div>
            <div class="grid">
                <div class="field">
                    <label for="dateEntree">Date d'entree</label>
                    <input id="dateEntree" name="dateEntree" type="date" required>
                </div>
                <div class="field">
                    <label for="lieu">Lieu</label>
                    <input id="lieu" name="lieu" type="text" placeholder="Ex : Antananarivo" required>
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
