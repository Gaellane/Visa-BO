<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Ajouter passeport">
<h1>Transfert sans donnees anterieures</h1>
    <h3><p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom}</p></h3>
    <form action="${pageContext.request.contextPath}/demandes/empty" method="post">
    <div class="card" style="max-width: 900px;">
        <h1>enregistrer demande </h1>
            <input type="hidden" name="demandeurId" value="${demandeur.id}">

            <div class="grid">
                <div class="field">
                    <label for="dateDemande">Date demande</label>
                    <input id="dateDemande" name="dateDemande" type="date" required value="${today}">
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

                <div class="field">
                    <label for="passeportId">Passeport</label>
                    <select id="passeportId" name="passeportId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${passeports}" var="passeport">
                            <option value="${passeport.id}">${passeport.numero}</option>
                        </c:forEach>
                    </select>
                </div>


                 <div class="field">
                    <label for="typeDemande">Type de demande</label>
                    <select id="typeDemande" name="typeDemande" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${typeDemandes}" var="typeDemande">
                            <option value="${typeDemande.id}">${typeDemande.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="field">
                    <label for="dateObtention">Date Obtention</label>
                    <input id="dateObtention" name="dateObtention" type="date" required>
                </div>
                <div class="field">
                    <label for="dateExpiration">Date Expiration</label>
                    <input id="dateExpiration" name="dateExpiration" type="date" required>
                </div>

            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Enregistrer</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Annuler</a>
            </div>
        </div>
    </form>
</layout:page>