<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<layout:page title="Fiche demandeur">
    <div class="card" style="max-width: 900px;">
        <h1>Fiche demandeur</h1>

        <div class="info-grid">
            <div class="info-item">
                <div class="label">Nom</div>
                <div class="value">${demandeur.nom}</div>
            </div>
            <div class="info-item">
                <div class="label">Prenom</div>
                <div class="value">${demandeur.prenom}</div>
            </div>
            <div class="info-item">
                <div class="label">Nom de jeune fille</div>
                <div class="value">${demandeur.nomJeuneFille}</div>
            </div>
            <div class="info-item">
                <div class="label">Date de naissance</div>
                <div class="value">${demandeur.dateNaissance}</div>
            </div>
            <div class="info-item">
                <div class="label">Adresse</div>
                <div class="value">${demandeur.adresse}</div>
            </div>
            <div class="info-item">
                <div class="label">Email</div>
                <div class="value">${demandeur.mail}</div>
            </div>
            <div class="info-item">
                <div class="label">Telephone</div>
                <div class="value">${demandeur.tel}</div>
            </div>
            <div class="info-item">
                <div class="label">Genre</div>
                <div class="value">${demandeur.genre.valeur}</div>
            </div>
            <div class="info-item">
                <div class="label">Status marital</div>
                <div class="value">${demandeur.statusMarital.valeur}</div>
            </div>
            <div class="info-item">
                <div class="label">Nationalite</div>
                <div class="value">${demandeur.nationalite.valeur}</div>
            </div>
        </div>

        <h2 style="margin-top: 24px;">Passeports</h2>
        <c:choose>
            <c:when test="${empty passeports}">
                <p style="margin-top: 8px; color: #6b7280;">Aucun passeport enregistre pour ce demandeur.</p>
            </c:when>
            <c:otherwise>
                <div class="table-wrap" style="margin-top: 10px;">
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Numero</th>
                                <th>Date de delivrance</th>
                                <th>Date d'expiration</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="passeport" items="${passeports}" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td>${passeport.numero}</td>
                                    <td>${passeport.delivrance}</td>
                                    <td>${passeport.expiration}</td>
                                    <td>
                                        <a class="btn-action" href="${pageContext.request.contextPath}/passeports/${passeport.id}/edit">Modifier passeport</a>
                                        <a class="btn-action" href="${pageContext.request.contextPath}/visa_transformables/new?passeportId=${passeport.id}">Ajouter visa transformable</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>

        <h2 style="margin-top: 24px;">Visa transformables</h2>
        <c:choose>
            <c:when test="${empty visaTransformables}">
                <p style="margin-top: 8px; color: #6b7280;">Aucun visa transformable enregistre pour ce demandeur.</p>
            </c:when>
            <c:otherwise>
                <div class="table-wrap" style="margin-top: 10px;">
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Reference</th>
                                <th>Date d'entree</th>
                                <th>Lieu</th>
                                <th>Expiration</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="visa" items="${visaTransformables}" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td>${visa.reference}</td>
                                    <td>${visa.dateEntree}</td>
                                    <td>${visa.lieu}</td>
                                    <td>${visa.expiration}</td>
                                    <td>
                                        <a class="btn-action" href="${pageContext.request.contextPath}/visa_transformables/${visa.id}/edit">Modifier visa transformable</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="actions">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}/edit">Modifier demandeur</a>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/passeports/new?demandeurId=${demandeur.id}">Ajouter passeport</a>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/demandes/transfert?demandeurId=${demandeur.id}">Transfert</a>
            
            <c:if test="${canDemande}">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/demandes/new?demandeurId=${demandeur.id}">Ajouter demande</a>
            </c:if>
            <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs">Retour liste</a>
        </div>
    </div>
</layout:page>
