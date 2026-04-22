<%@ tag pageEncoding="UTF-8" body-content="scriptless" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f4f6f9; display: flex; min-height: 100vh; }

        .sidebar { width: 250px; background: #1f3c88; color: white; padding: 24px 0; }
        .sidebar-title { padding: 0 24px 18px; font-size: 18px; font-weight: 700; border-bottom: 1px solid rgba(255, 255, 255, 0.2); margin-bottom: 12px; }
        .sidebar a { display: block; padding: 15px 25px; color: white; text-decoration: none; }
        .sidebar a:hover { background-color: rgba(255, 255, 255, 0.12); }

        .main-content { flex: 1; padding: 32px; }
        .card { background: white; border: 1px solid #e5e7eb; border-radius: 6px; padding: 24px; }

        h1 { color: #1f3c88; margin-bottom: 18px; font-size: 24px; }
        .sub { color: #6b7280; margin-bottom: 16px; }

        .grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 14px; }
        .field { display: flex; flex-direction: column; gap: 6px; }
        .field.full { grid-column: 1 / -1; }
        .full { grid-column: 1 / -1; }

        label { font-size: 14px; color: #374151; font-weight: 700; }
        input, select { padding: 10px 12px; border: 1px solid #d1d5db; border-radius: 4px; font-size: 14px; }

        .actions { margin-top: 18px; display: flex; gap: 12px; flex-wrap: wrap; }
        .btn { display: inline-block; border: 0; border-radius: 4px; padding: 10px 16px; font-size: 14px; cursor: pointer; text-decoration: none; }
        .btn-primary { background: #1f3c88; color: white; }
        .btn-secondary { background: #2563eb; color: white; }
        .btn-back { background: #e5e7eb; color: #111827; }

        .toolbar { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
        .btn-add { display: inline-block; background: #1f3c88; color: white; text-decoration: none; padding: 10px 16px; border-radius: 4px; font-size: 14px; }
        .btn-action { display: inline-block; background: #2563eb; color: white; text-decoration: none; padding: 6px 10px; border-radius: 4px; font-size: 13px; }

        table { width: 100%; border-collapse: collapse; }
        th, td { text-align: left; padding: 12px 10px; border-bottom: 1px solid #e5e7eb; font-size: 14px; }
        th { color: #374151; background: #f9fafb; }
        .empty { color: #6b7280; padding: 12px 0; }

        .info-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 12px 24px; margin-bottom: 24px; }
        .info-item { border-bottom: 1px solid #e5e7eb; padding-bottom: 8px; }
        .label { color: #6b7280; font-size: 12px; text-transform: uppercase; letter-spacing: 0.4px; }
        .value { color: #111827; font-size: 15px; margin-top: 4px; }

        .pieces-box { border: 1px solid #e5e7eb; border-radius: 4px; padding: 12px; max-height: 240px; overflow: auto; background: #fafafa; }
        .piece-line { display: flex; align-items: center; gap: 8px; margin-bottom: 8px; font-size: 14px; color: #111827; }
        .piece-title { font-size: 13px; font-weight: 700; color: #374151; margin: 10px 0 6px; }
        .muted { color: #6b7280; font-size: 13px; }

        .header { background-color: white; padding: 22px 32px; border: 1px solid #e5e7eb; border-radius: 6px; margin-bottom: 20px; }
        .header h1 { color: #333; margin-bottom: 0; }
        .header p { color: #666; margin-top: 5px; font-size: 13px; }

        .welcome-section { background: white; padding: 28px; border-radius: 6px; max-width: 720px; border: 1px solid #e5e7eb; }
        .welcome-section h2 { color: #1f3c88; font-size: 28px; margin-bottom: 16px; }
        .welcome-section p { color: #555; line-height: 1.7; margin-bottom: 12px; font-size: 15px; }
        .welcome-section .highlight { color: #1f3c88; font-weight: bold; }
        .section-title { margin-top: 20px; font-size: 16px; color: #333; font-weight: 700; }

        .alert { padding: 14px 16px; border-radius: 4px; margin-bottom: 20px; font-size: 14px; }
        .alert-error { background: #fee2e2; border: 1px solid #fca5a5; color: #991b1b; }
        .alert-warning { background: #fef3c7; border: 1px solid #fbbf24; color: #92400e; }
        .alert-success { background: #dcfce7; border: 1px solid #86efac; color: #166534; }
        .alert-info { background: #e0f2fe; border: 1px solid #7dd3fc; color: #0c4a6e; }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/fragments/sidebar.jspf" %>
    <main class="main-content">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        <c:if test="${not empty warning}">
            <div class="alert alert-warning">${warning}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty info}">
            <div class="alert alert-info">${info}</div>
        </c:if>
        
        <jsp:doBody/>
    </main>
</body>
</html>
