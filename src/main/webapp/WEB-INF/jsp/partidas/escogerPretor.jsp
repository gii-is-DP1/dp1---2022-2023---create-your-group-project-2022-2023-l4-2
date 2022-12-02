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



        <span>Quien quieres que sea el Pretor?</span><br>
        <form:form modelAttribute="jugador"
                   class="form-horizontal">
            <div class="form-group has-feedback">                
                <c:forEach items="${jugFilt}" var="jug">
                    <td>${jug.user.username}<td>
                    <input type="radio" name="id" value="${jug.id}" checked/>
                 </c:forEach>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10"></div>
                     <button class="btn btn-default" type="submit">Seleccionar Pretor</button>
                </div>
            </div>
        </form:form> 
    </div>
   
</petclinic:lo2>