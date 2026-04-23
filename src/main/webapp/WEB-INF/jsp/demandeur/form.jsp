<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<c:set var="editMode" value="${not empty demandeur.id}" />
<c:set var="formAction" value="${editMode ? pageContext.request.contextPath.concat('/demandeurs/').concat(demandeur.id).concat('/edit') : pageContext.request.contextPath.concat('/demandeurs')}" />

<layout:page title="${editMode ? 'Modifier demandeur' : 'Ajouter un demandeur'}">
    <div class="card" style="max-width: 860px;">
        <h1>${editMode ? 'Modifier demandeur' : 'Ajouter un demandeur'}</h1>
        <form method="post" action="${formAction}">
            <div class="grid">
                <div class="field">
                    <label for="nom">Nom</label>
                    <input id="nom" name="nom" type="text" value="${demandeur.nom}" required>
                </div>
                <div class="field">
                    <label for="prenom">Prenom</label>
                    <input id="prenom" name="prenom" type="text" value="${demandeur.prenom}">
                </div>
                <div class="field">
                    <label for="nomJeuneFille">Nom de jeune fille</label>
                    <input id="nomJeuneFille" name="nomJeuneFille" type="text" value="${demandeur.nomJeuneFille}">
                </div>
                <div class="field">
                    <label for="dateNaissance">Date de naissance</label>
                    <input id="dateNaissance" name="dateNaissance" type="date" value="${demandeur.dateNaissance}" required>
                </div>
                <div class="field full">
                    <label for="adresse">Adresse</label>
                    <input id="adresse" name="adresse" type="text" value="${demandeur.adresse}" required>
                </div>
                <div class="field">
                    <label for="mail">Email</label>
                    <input id="mail" name="mail" type="email" value="${demandeur.mail}">
                </div>
                <div class="field">
                    <label for="tel">Telephone</label>
                    <input id="tel" name="tel" type="text" value="${demandeur.tel}" required>
                </div>
                <div class="field">
                    <label for="genreId">Genre</label>
                    <select id="genreId" name="genreId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${genres}" var="genre">
                            <option value="${genre.id}" <c:if test="${not empty demandeur.genre and demandeur.genre.id == genre.id}">selected="selected"</c:if>>${genre.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="field">
                    <label for="statusMaritalId">Statut marital</label>
                    <select id="statusMaritalId" name="statusMaritalId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${statusMaritals}" var="statusMarital">
                            <option value="${statusMarital.id}" <c:if test="${not empty demandeur.statusMarital and demandeur.statusMarital.id == statusMarital.id}">selected="selected"</c:if>>${statusMarital.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="field full">
                    <label for="nationaliteId">Nationalite</label>
                    <select id="nationaliteId" name="nationaliteId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${nationalites}" var="nationalite">
                            <option value="${nationalite.id}" <c:if test="${not empty demandeur.nationalite and demandeur.nationalite.id == nationalite.id}">selected="selected"</c:if>>${nationalite.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">${editMode ? 'Mettre a jour' : 'Enregistrer'}</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs">Retour</a>
            </div>
        </form>
    </div>
</layout:page>