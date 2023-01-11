package com.cursoSpringboot.cursoSpringboot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursoSpringboot.cursoSpringboot.models.Usuarios;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//Transactional nos da la funcionalidad de poder hacer las consultas de SQL a la DB
//Con Repository tendremos la funcionalidad de poder acceder al repositorio de la DB
@Repository
@Transactional
public class UsuariosDaoImp implements UsuariosDao {

	//EntityManager nos servirá para hacer la conexión con la DB
	@PersistenceContext
	EntityManager entityManager;

	//Override sirve para sobrescribir los métodos
	@Override
	public List<Usuarios> getUsuarios() {
		//Aquí no ponemos el nombre de la tabla sino el de la clase
		String query = "FROM Usuarios";
		//EntityManager ejecutará la query y luego la convertimos en un ResultList
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public void eliminarUsuario(Integer id) {
		Usuarios usuario = entityManager.find(Usuarios.class, id);
		entityManager.remove(usuario);
	}

	@Override
	public void registrarUsuario(Usuarios usuario) {
		entityManager.merge(usuario);
	}

	@Override
	public Usuarios loginUsuario(Usuarios usuario) {
		//PELIGRO, Inyección de SQL. Pueden iniciar sesion sin el password si hacemos lo siguiente:
		//String query = "FROM Usuarios WHERE email='" + usuario.getEmail()
		//+ "' AND password='" + usuario.getPassword()  + "'";
		String query = "FROM Usuarios WHERE email = :email";
		List<Usuarios> listaUsuarios = entityManager.createQuery(query)
				.setParameter("email", usuario.getEmail())
				.getResultList();

		//Verificamos que la lista no esté vacía
		if (listaUsuarios.isEmpty()) {
			return null;
		}

		//Traemos la contraseña hasheada de ls DB
		String passwordHash = listaUsuarios.get(0).getPassword();

		//Verificamos con la contraseña de la DB y la que le estamos pasando
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		//Esto devolverá un boolean true en caso de que coincidan o false en caso de que no
		if (argon2.verify(passwordHash, usuario.getPassword())) {
			//Esto devolverá el usuario
			return listaUsuarios.get(0);
		}
		//Si las credenciales no son correctas
		return null;
	}

}
