<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Ajouter un demandeur">
    <div class="card" style="max-width: 860px;">
        <h1>Ajouter un demandeur</h1>
        <form method="post" action="${pageContext.request.contextPath}/demandeurs">
            <div class="grid">
                <div class="field">
                    <label for="nom">Nom</label>
                    <input id="nom" name="nom" type="text" required>
                </div>
                <div class="field">
                    <label for="prenom">Prenom</label>
                    <input id="prenom" name="prenom" type="text">
                </div>
                <div class="field">
                    <label for="nomJeuneFille">Nom de jeune fille</label>
                    <input id="nomJeuneFille" name="nomJeuneFille" type="text">
                </div>
                <div class="field">
                    <label for="dateNaissance">Date de naissance</label>
                    <input id="dateNaissance" name="dateNaissance" type="date" required>
                </div>
                <div class="field full">
                    <label for="adresse">Adresse</label>
                    <input id="adresse" name="adresse" type="text" required>
                </div>
                <div class="field">
                    <label for="mail">Email</label>
                    <input id="mail" name="mail" type="email">
                </div>
                <div class="field">
                    <label for="tel">Telephone</label>
                    <input id="tel" name="tel" type="text" required>
                </div>
                <div class="field">
                    <label for="genreId">Genre</label>
                    <select id="genreId" name="genreId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${genres}" var="genre">
                            <option value="${genre.id}">${genre.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="field">
                    <label for="statusMaritalId">Statut marital</label>
                    <select id="statusMaritalId" name="statusMaritalId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${statusMaritals}" var="statusMarital">
                            <option value="${statusMarital.id}">${statusMarital.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="field full">
                    <label for="nationaliteId">Nationalite</label>
                    <select id="nationaliteId" name="nationaliteId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${nationalites}" var="nationalite">
                            <option value="${nationalite.id}">${nationalite.valeur}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Enregistrer</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs">Retour</a>
            </div>
        </form>
    </div>
</layout:page>