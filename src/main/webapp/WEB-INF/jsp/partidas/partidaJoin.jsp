<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:lo2 pageName="partidaJoin">

    <button>
        <a class="btn btn-default" href="/partidas/join">< Volver</a></th>
    </button>

    <h2 style = "font-family: 'Dalek Pinpoint', sans-serif;";>Partida de ${partida.anfitrion} </h2>

    <table id="partidaJoin" class="table table-striped">
        <thead>
        <tr>
            <th>Jugadores en la sala</th>
            <th>&#191;Est&aacute; listo?</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${jugadores}" var="jugador">
                <tr style = "text-align: left; ";>
                    <td>
                        <c:out value="${jugador.user.username}"/>
                    </td>
                    <td>
                        <c:if test="true">
                            LISTO
                        </c:if>
                    </td>
                </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:lo2>
