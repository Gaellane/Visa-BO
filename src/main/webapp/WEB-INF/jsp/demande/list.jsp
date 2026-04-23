<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Demandes">
    <div class="card">
        <h1>Liste des demandes</h1>

        <form method="get" action="${pageContext.request.contextPath}/demandes" style="margin-bottom: 18px;">
            <div class="grid">
                <div class="field">
                    <label for="dateMin">Date min</label>
                    <input id="dateMin" type="date" name="dateMin" value="${dateMin}" />
                </div>

                <div class="field">
                    <label for="dateMax">Date max</label>
                    <input id="dateMax" type="date" name="dateMax" value="${dateMax}" />
                </div>

                <div class="field">
                    <label for="typeId">Type</label>
                    <select id="typeId" name="typeId">
                        <option value="">Tous</option>
                        <c:forEach items="${demandeTypes}" var="type">
                            <c:choose>
                                <c:when test="${type.id == typeId}">
                                    <option value="${type.id}" selected="selected">${type.valeur}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${type.id}">${type.valeur}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

                <div class="field">
                    <label for="visaTypeId">Type de visa</label>
                    <select id="visaTypeId" name="visaTypeId">
                        <option value="">Tous</option>
                        <c:forEach items="${visaTypes}" var="visaType">
                            <c:choose>
                                <c:when test="${visaType.id == visaTypeId}">
                                    <option value="${visaType.id}" selected="selected">${visaType.valeur}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${visaType.id}">${visaType.valeur}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Rechercher</button>
            </div>
        </form>

        <c:choose>
            <c:when test="${not empty demandes}">
                <table>
                    <thead>
                        <tr>
                            <th>Date demande</th>
                            <th>Demandeur</th>
                            <th>Type</th>
                            <th>Type visa</th>
                            <th>Action</th>
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
                                <td>
                                    <a class="btn-action" href="${pageContext.request.contextPath}/demandes/details?id=${demande.id}">Voir</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty">Aucune demande trouvée pour ces critères.</div>
            </c:otherwise>
        </c:choose>
    </div>
</layout:page>