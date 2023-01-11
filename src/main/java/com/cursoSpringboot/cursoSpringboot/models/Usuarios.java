package com.cursoSpringboot.cursoSpringboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@ToString @EqualsAndHashCode
public class Usuarios {

	//Con @Id indicamos que esta será la PK
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter @Column(name = "id_usuarios")
	private Integer id;

	@Getter @Setter @Column(name = "nombre_usuarios")
	private String nombre;

	@Getter @Setter @Column(name = "apellido_usuarios")
	private String apellido;

	@Getter @Setter @Column(name = "email_usuarios")
	private String email;

	@Getter @Setter @Column(name = "num_telefono_usuarios")
	private String numTelefono;

	@Getter @Setter @Column(name = "password_usuarios")
	private String password;

}