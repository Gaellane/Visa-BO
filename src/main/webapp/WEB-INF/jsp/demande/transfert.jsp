<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Ajouter passeport">
<h1>Transfert sans donnees anterieures</h1>
    <h3><p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom}</p></h3>
    <div class="card" style="max-width: 760px;">
        <h1>Ajouter passeport</h1>
        

        <form method="post" action="${pageContext.request.contextPath}/demandes/newTransfert">
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
                    <label for="expirationVisaTransf">Date d'expiration</label>
                    <input id="expirationVisaTransf" name="expirationVisaTransf" type="date" required>
                </div>
            </div>

    </div>
    <div class="card" style="max-width: 760px;">
        <h1>Ajouter visa transformable</h1>
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

    </div>
    <div class="card" style="max-width: 900px;">
        <h1>enregistrer demande</h1>
            <input type="hidden" name="demandeurId" value="${demandeur.id}">

            <div class="grid">
                <div class="field">
                    <label for="dateDemande">Date demande</label>
                    <input id="dateDemande" name="dateDemande" type="date" required>
                </div>

                <div class="field">
                    <label for="visaTypeId">Type visa</label>
                    <select id="visaTypeId" name="visaTypeId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${visaTypes}" var="visaType">
                            <option value="${visaType.id}">${visaType.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Enregistrer</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Annuler</a>
            </div>
        </form>
    </div>
</layout:page>