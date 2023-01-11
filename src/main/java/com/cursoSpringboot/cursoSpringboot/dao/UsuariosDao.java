package com.cursoSpringboot.cursoSpringboot.dao;

import java.util.List;

import com.cursoSpringboot.cursoSpringboot.models.Usuarios;

public interface UsuariosDao {

	List<Usuarios> getUsuarios();

	void eliminarUsuario(Integer id);

	void registrarUsuario(Usuarios usuario);

	Usuarios loginUsuario(Usuarios usuario);

}
