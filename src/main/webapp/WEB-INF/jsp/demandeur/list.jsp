<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Demandeurs">
    <div class="card">
        <div class="toolbar">
            <h1>Liste des demandeurs</h1>
            <a class="btn-add" href="${pageContext.request.contextPath}/demandeurs/new">Ajouter</a>
        </div>
        <c:choose>
            <c:when test="${not empty demandeurs}">
                <table>
                    <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Prenom</th>
                            <th>Telephone</th>
                            <th>Email</th>
                            <th>Nationalite</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${demandeurs}" var="demandeur">
                            <tr>
                                <td>${demandeur.nom}</td>
                                <td>${demandeur.prenom}</td>
                                <td>${demandeur.tel}</td>
                                <td>${demandeur.mail}</td>
                                <td>${demandeur.nationalite.valeur}</td>
                                <td>
                                    <a class="btn-action" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Details</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty">Aucun demandeur enregistre pour le moment.</div>
            </c:otherwise>
        </c:choose>
    </div>
</layout:page>