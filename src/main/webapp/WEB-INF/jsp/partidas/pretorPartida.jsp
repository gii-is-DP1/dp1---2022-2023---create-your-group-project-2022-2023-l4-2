<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:lo2 pageName="partidas">

    <div style = "font-family: 'Dalek Pinpoint', sans-serif; font-size: 20px;text-align: center; height: 200;">

        <div style="font-size: 35px">
            <c:out value="${principal.name}"/>
        </div>

        <table class="table table-striped">
            <tr>
                <td style="background: #e2861e">Votos a favor del cesar:  <c:out value="${partida.votosFavorCesar}"/></td>
                <td style="background: #cecbc8 ">Votos contra el cesar:  <c:out value="${partida.votosContraCesar}"/></td>
                <td style="background: #e2861e">Limite de votos:  <c:out value="${partida.limite}"/></td>
            </tr>
        </table>

        <h2 style = "font-family: 'Dalek Pinpoint', sans-serif; font-size: 20px;">Ronda:  <c:out value="${partida.ronda}"/></h2>
        <h2 style = "font-family: 'Dalek Pinpoint', sans-serif; font-size: 20px;">Turno:  <c:out value="${partida.turno}"/></h2>

        <div style="text-align:left">
            Jugadores de la partida:
            <div>
                <c:forEach items="${partida.jugadores}" var="jugador">
                    <tr style = "text-align: left; ";>
                        <td>
                            <div>
                                <c:out value="${jugador.user.username}"/> -  <c:out value="${jugador.rol}"/>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <div style="height:100px; width: 100px;">

                </div>
                <c:if test ="${faccionApoyada == null}">
                <div style="text-align:left">
                    Tus opciones: 
                    <div>
                        <c:forEach items="${elegir}" var="opcion">
                            <tr style = "text-align: left; ";>
                                <td>
                                    <div>
                                        <c:out value="${opcion.getName()}"/> 
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <div style="height:100px; width: 100px;">
        
                </div>
                
                <c:if test ="${faccionApoyada != null}">
                    <div style="text-align:left">
                        Tu faccion Apoyada
                        <div>
                                <tr style = "text-align: left; ";>
                                    <td>
                                        <div>
                                            <c:out value="${faccionApoyada.getName()}"/> 
                                        </div>
                                    </td>
                                </tr>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <span> ¿El voto de quien quieres cambiar?</span>
        <c:forEach items="${votos}" var="voto">
                    <tr style = "text-align: left; ";>
                        <td>
                            <div>
                                <c:out value="${voto.jugador.user.username}"/>

                                <c:if test= "${partida.getRonda() == 1}">
                                    <a href="/partidas/jugar/pretor/edit/${partida.id}/${voto.id}"> 
                                        <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                    </a>
                                </c:if>
                                <c:if test = "${partida.getRonda() == 2}">
                                    <a href="/partidas/jugar/pretor/edit2/${partida.id}/${voto.id}"> 
                                        <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                    </a>
                                </c:if>

                            </div>
                        </td>
                    </tr>
                </c:forEach>
    </div>
   
</petclinic:lo2>