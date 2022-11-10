package org.springframework.samples.petclinic.jugador;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import org.springframework.samples.petclinic.user.User;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.partida.Participacion;
import org.springframework.samples.petclinic.partida.Partida;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Jugador extends Person {
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username",referencedColumnName = "username")
    private User user;

    @ManyToOne
    @JoinColumn
    private RolType rol;

    private boolean estaEnPartida;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "jugadores")
    public List<Partida> partidas;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Participacion> participaciones;

    
}