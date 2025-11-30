<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Профиль пользователя</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .profile-info { background: #f5f5f5; padding: 20px; border-radius: 5px; }
        .logout-form { margin-top: 20px; }
        .error { color: red; }
    </style>
</head>
<body>
<h1>Профиль пользователя</h1>

<c:choose>
    <c:when test="${not empty sessionScope.username}">
        <div class="profile-info">
            <h2>Добро пожаловать, ${sessionScope.username}!</h2>

            <h3>Информация о сессии:</h3>
            <p><strong>ID сессии:</strong> ${pageContext.session.id}</p>
            <p><strong>Время создания:</strong>
                    ${pageContext.session.creationTime}</p>
            <p><strong>Последний доступ:</strong>
                    ${pageContext.session.lastAccessedTime}</p>
            <p><strong>Макс. время неактивности:</strong>
                    ${pageContext.session.maxInactiveInterval} секунд</p>
        </div>

        <div class="logout-form">
            <form action="logout" method="post">
                <input type="submit" value="Выйти из системы">
            </form>
        </div>

        <p><a href="welcome">Вернуться на главную</a></p>
    </c:when>

    <c:otherwise>
        <div class="error">
            <p>Доступ запрещен. Пожалуйста, <a href="login">войдите в систему</a>.</p>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>