<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Scan des pieces justificatives">
    <div class="card" style="max-width: 900px;">
        <h1>Scan des pieces justificatives</h1>
        <p class="sub">Demandeur: ${demande.demandeur.nom} ${demande.demandeur.prenom}</p>

        <div class="info-grid">
            <div class="info-item">
                <div class="label">Demandeur</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${demande.demandeur != null}">
                            ${demande.demandeur.id} - ${demande.demandeur.nom} ${demande.demandeur.prenom}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="info-item">
                <div class="label">Type de demande</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${demande.type != null}">
                            ${demande.type.valeur}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="info-item">
                <div class="label">Type de visa</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${demande.typeVisa != null}">
                            ${demande.typeVisa.valeur}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="info-item">
                <div class="label">Date de demande</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${demande.dateDemande != null}">
                            ${demande.dateDemande}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/demandes/${demande.id}/scan" enctype="multipart/form-data">
            <h2 style="margin-top: 24px;">Pieces justificatives</h2>
            <c:choose>
                <c:when test="${not empty pieces}">
                    <div class="table-wrap" style="margin-top: 10px;">
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Piece</th>
                                    <th>Fichier</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${pieces}" var="piece" varStatus="st">
                                    <tr>
                                        <td>${st.count}</td>
                                        <td>${piece.piece.nomPiece}</td>
                                        <c:if test="${empty piece.cheminPiece}">
                                            <td>
                                                <input type="file" name="${piece.piece.id}" >
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <p style="margin-top: 8px; color: #6b7280;">Aucune piece justificative enregistree pour cette demande.</p>
                </c:otherwise>
            </c:choose>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Terminer le scan</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandes/${demande.id}/edit">Retour</a>
            </div>
        </form>
    </div>
</layout:page>