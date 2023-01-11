package com.cursoSpringboot.cursoSpringboot.controllers;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursoSpringboot.cursoSpringboot.dao.UsuariosDao;
import com.cursoSpringboot.cursoSpringboot.models.Usuarios;
import com.cursoSpringboot.cursoSpringboot.utils.JwtUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UsuariosController {

	//Con @Autowired creamos un objeto de UsuariosDaoImp y lo guardamos en una variable
	//Esto se llama inyección de dependencias, ya tenemos ese objeto guardado en memoria y listo para usarse
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private JwtUtil jwtUtil;

	//Hacemos el id dinámico (Se enviará a la entidad el que se recoja de la URI)
	@RequestMapping(value = "api/usuarios/{id}")
	public Usuarios getUsuario(@PathVariable Integer id) {
		Usuarios usuario1 = new Usuarios();
		usuario1.setId(id);
		usuario1.setNombre("Sergio");
		usuario1.setApellido("Vazquez");
		usuario1.setEmail("sergio13v@hotmail.com");
		usuario1.setNumTelefono("611140796");
		return usuario1;
	}

	//Podemos simplificar las requests
	//@RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
	@GetMapping(value = "api/usuarios")
	public List<Usuarios> getUsuarios(@RequestHeader(value="Authorization") String token) {
		
		//Si el método da error se acaba
		if (!validarToken(token)) {
			return null;
		}
		
		return usuariosDao.getUsuarios();
	}

	//@RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
	@DeleteMapping(value = "api/usuarios/{id}")
	public void eliminarUsuario(@RequestHeader(value="Authorization") String token, 
			@PathVariable Integer id) {
		
		//Si el método da error se acaba
		if (!validarToken(token)) {
			return;
		}
		
		usuariosDao.eliminarUsuario(id);
	}

	@PostMapping(value = "api/usuarios")
	public void registrarUsuario(@RequestBody Usuarios usuario) {

		//Encriptamos la contraseña
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		//Con 1 encriptará la contraseña una vez, cuanto mayor sea el número, será más seguro y más lento
		String passwordHash = argon2.hash(1, 1024, 1, usuario.getPassword());
		//Volvemos a enviarla una vez hasheada
		usuario.setPassword(passwordHash);

		usuariosDao.registrarUsuario(usuario);
	}
	
	private boolean validarToken(String token) {
		//Aquí podemos traer el Key (id del usuario) o el Value
		String idUsuario = jwtUtil.getKey(token);
		return idUsuario != null;
	}

	/*@RequestMapping(value = "usuario1")
	public Usuarios editarUsuario() {
		Usuarios usuario1 = new Usuarios();
		usuario1.setNombre("Sergio");
		usuario1.setApellido("Vazquez");
		usuario1.setEmail("sergio13v@hotmail.com");
		usuario1.setNumTelefono("611140796");
		return usuario1;
	}

	@RequestMapping(value = "usuario3")
	public Usuarios buscarUsuario() {
		Usuarios usuario1 = new Usuarios();
		usuario1.setNombre("Sergio");
		usuario1.setApellido("Vazquez");
		usuario1.setEmail("sergio13v@hotmail.com");
		usuario1.setNumTelefono("611140796");
		return usuario1;
	}

	@RequestMapping(value = "prueba")
	public List<String> prueba() {
		//En Java 9 podríamos usar el método List.of
		//return lList.of("manzana", "kiwi", "melon");
		return Arrays.asList("kiwi", "melon", "manzana");
	}*/

}