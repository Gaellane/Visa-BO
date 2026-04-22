<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:page title="Ajouter demande">
    <div class="card" style="max-width: 900px;">
        <h1>Ajouter demande</h1>
        <p class="sub">Demandeur: ${demandeur.nom} ${demandeur.prenom}</p>

        <form method="post" action="${pageContext.request.contextPath}/demandes">
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

                <div class="field">
                    <label for="demandeTypeId">Type demande</label>
                    <select id="demandeTypeId" name="demandeTypeId" required>
                        <option value="">Choisir</option>
                        <c:forEach items="${demandeTypes}" var="demandeType">
                            <option value="${demandeType.id}">${demandeType.valeur}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="field full">
                    <label>Pieces justificatives</label>
                    <div class="pieces-box">
                        <div id="common-pieces-title" class="piece-title" style="display: none;">Pieces communes</div>
                        <div id="common-pieces" class="muted">Choisissez un type de visa et un type de demande pour charger les pieces.</div>

                        <div id="typed-pieces-title" class="piece-title" style="display: none;">Pieces selon type de visa</div>
                        <div id="typed-pieces" class="muted"></div>
                    </div>
                </div>
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Enregistrer</button>
                <a class="btn btn-back" href="${pageContext.request.contextPath}/demandeurs/${demandeur.id}">Annuler</a>
            </div>
        </form>
    </div>

    <script>
        const contextPath = '${pageContext.request.contextPath}';
        const visaTypeSelect = document.getElementById('visaTypeId');
        const demandeTypeSelect = document.getElementById('demandeTypeId');
        const commonPiecesTitle = document.getElementById('common-pieces-title');
        const commonPiecesContainer = document.getElementById('common-pieces');
        const typedPiecesTitle = document.getElementById('typed-pieces-title');
        const typedPiecesContainer = document.getElementById('typed-pieces');

        function renderPieceList(pieces) {
            return pieces.map((piece) => {
                const safeNom = String(piece.nomPiece)
                    .replaceAll('&', '&amp;')
                    .replaceAll('<', '&lt;')
                    .replaceAll('>', '&gt;')
                    .replaceAll('"', '&quot;');
                return '<label class="piece-line">'
                    + '<input type="checkbox" name="selectedPieces" value="' + piece.id + '">'
                    + '<span>' + safeNom + '</span>'
                    + '</label>';
            }).join('');
        }

        function renderPieces(payload) {
            const commonPieces = (payload && payload.piecesCommunes) || [];
            const typedPieces = (payload && payload.piecesPropres) || [];

            if (commonPieces.length === 0) {
                commonPiecesTitle.style.display = 'none';
                commonPiecesContainer.innerHTML = '<div class="muted">Aucune piece commune.</div>';
            } else {
                commonPiecesTitle.style.display = 'block';
                commonPiecesContainer.innerHTML = renderPieceList(commonPieces);
            }

            if (typedPieces.length === 0) {
                typedPiecesTitle.style.display = 'none';
                typedPiecesContainer.innerHTML = '<div class="muted">Aucune piece specifique pour ce type.</div>';
            } else {
                typedPiecesTitle.style.display = 'block';
                typedPiecesContainer.innerHTML = renderPieceList(typedPieces);
            }
        }

        function resetPieces(message) {
            commonPiecesTitle.style.display = 'none';
            typedPiecesTitle.style.display = 'none';
            commonPiecesContainer.innerHTML = '<div class="muted">' + message + '</div>';
            typedPiecesContainer.innerHTML = '';
        }

        async function loadTypedPieces() {
            const visaTypeId = visaTypeSelect.value;
            const typeDemandeId = demandeTypeSelect.value;
            if (!visaTypeId || !typeDemandeId) {
                resetPieces('Choisissez un type de visa et un type de demande pour charger les pieces.');
                return;
            }

            try {
                const url = contextPath + '/api/pieces-justificatives/'
                    + encodeURIComponent(visaTypeId)
                    + '?typeDemandeId=' + encodeURIComponent(typeDemandeId);
                const response = await fetch(url);
                if (!response.ok) {
                    throw new Error('Erreur API');
                }
                const data = await response.json();
                renderPieces(data);
            } catch (error) {
                resetPieces('Impossible de charger les pieces pour ce type.');
            }
        }

        visaTypeSelect.addEventListener('change', loadTypedPieces);
        demandeTypeSelect.addEventListener('change', loadTypedPieces);
        resetPieces('Choisissez un type de visa et un type de demande pour charger les pieces.');
    </script>
</layout:page>