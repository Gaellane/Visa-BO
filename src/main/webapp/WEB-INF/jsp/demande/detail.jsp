<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Fiche demande">
    <div class="card" style="max-width: 900px;">
        <h1>Fiche demande</h1>

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
                <div class="label">Passeport</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${passeport != null}">
                            ${passeport.numero}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="info-item">
                <div class="label">Delivrance</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${passeport != null}">
                            ${passeport.delivrance}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="info-item">
                <div class="label">Expiration</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${passeport != null}">
                            ${passeport.expiration}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <h2 style="margin-top: 24px;">Pieces justificatives</h2>
        <c:choose>
            <c:when test="${not empty pieces}">
                <div class="table-wrap" style="margin-top: 10px;">
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Piece</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${pieces}" var="piece" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td>${piece.piece.nomPiece}</td>
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
    </div>
</layout:page>