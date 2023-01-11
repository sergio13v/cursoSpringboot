// Call the dataTables jQuery plugin
//Esta función manda a ejecutar todo el código que hay en su interior una vez se ejecute la página
$(document).ready(function() {
	alert("Usuarios");
	getUsuarios();
	//Esta función es la responsable de la paginación de la tabla entre otras cosas
	$('#usuarios ').DataTable();
	
	//Si el email está vacío (no te has logueado), acaba el método y no hace la siguiente función
	if(!localStorage.email){
		return;
	}
	//Esta función muestra el nombre del usuario logueado
	mostrarEmailUsuario();	
	
});

//Buscamos un ejemplo de fetch en stackoverflow para crear una función que nos recoja los usuarios
//Cuando usamos await hay que indicarle a la función que es asincrónica
async function getUsuarios() {
  const request = await fetch('api/usuarios', {
    //Utilizamos el método GET
    method: 'GET',
    //Indicamos que usaremos JSON
    headers: getHeaders()
  });
  //Aquí convertimos el resultado en JSON
  const usuarios = await request.json();

  //Esto nos imprime que está ocurriendo por consola
  console.log(usuarios);

  let listaHtml = '';

  //For each para conseguir todos los usuarios del HTML y guardarlos en una lista
  for (let usuario of usuarios) {
	//Usamos comillas simples porque hay comillas dobles dentro del String
	let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id 
	+ ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
  
    let usuarioHtml = '<tr><td>' + usuario.id + '</td><td>' + usuario.nombre + ' ' 
    + usuario.apellido + '</td><td>' + usuario.email + '</td><td>' 
    + usuario.numTelefono + '</td><td>' + botonEliminar + '</td></tr>';
  
    listaHtml += usuarioHtml;
  }

  //Enviamos la lista al tbody del html
  document.querySelector('#usuarios tbody').outerHTML = listaHtml;
}

async function eliminarUsuario(id) {
	//confirm mostrará un cartel de confirmación
	//Al ponerle la exclamación indicamos que si se pulsa en no se termina la función
	if (!confirm("¿Desea eliminar este usuario?")) {
		return;
	}
	
	const request = await fetch('api/usuarios/' + id, {
	  //Utilizamos el método DELETE
	  method: 'DELETE',
	  //Indicamos que usaremos JSON
	  headers: getHeaders()
	});
	
	//Este actualiza toda la página, es mejorable con AJAX
	location.reload();

	//Esto nos imprime que está ocurriendo por consola
	console.log(id);
}

function mostrarEmailUsuario(){
	document.getElementById('email-usuario').outerHTML = localStorage.email;
}

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization':localStorage.token
	}
}