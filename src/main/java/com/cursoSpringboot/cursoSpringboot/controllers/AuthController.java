package com.cursoSpringboot.cursoSpringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cursoSpringboot.cursoSpringboot.dao.UsuariosDao;
import com.cursoSpringboot.cursoSpringboot.models.Usuarios;
import com.cursoSpringboot.cursoSpringboot.utils.JwtUtil;

@RestController
public class AuthController {

	//Con @Autowired creamos un objeto de UsuariosDaoImp y lo guardamos en una variable
	//Esto se llama inyección de dependencias, ya tenemos ese objeto guardado en memoria y listo para usarse
	@Autowired
	private UsuariosDao usuariosDao;

	@Autowired
	private JwtUtil jwtUtil;

	//@RequestMapping(value = "api/login", method = RequestMethod.POST)
	@PostMapping(value = "api/login")
	public String loginUsuario(@RequestBody Usuarios usuario) {
		String respuesta;

		Usuarios usuarioLogueado = usuariosDao.loginUsuario(usuario);

		if (usuarioLogueado != null) {
			//Creamos el JWT para la sesión
			String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()),
					usuarioLogueado.getEmail());
			respuesta = tokenJwt;
		} else {
			respuesta = "usuarioNoOk";
		}
		return respuesta;
	}

}