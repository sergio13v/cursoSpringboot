//Esta función manda a ejecutar todo el código que hay en su interior una vez se ejecute la página
$(document).ready(function() {
	alert("Registro");
});

//Buscamos un ejemplo de fetch en stackoverflow para crear una función que nos recoja los usuarios
//Cuando usamos await hay que indicarle a la función que es asincrónica
async function registrarUsuario() {
  let datosRegistro = {};
  
  //Recogemos los id de cada input del html. Dejo comentada otra manera de recogerlos
  //datosRegistro.nombre = document.querySelector('#nombre').value;
  datosRegistro.nombre = document.getElementById('nombre').value;
  datosRegistro.apellido = document.getElementById('apellido').value;
  datosRegistro.email = document.getElementById('email').value;
  datosRegistro.password = document.getElementById('password').value;
	
  let repitePassword = document.getElementById('repitePassword').value;
  
  if (datosRegistro.password != repitePassword) {
	  alert('Las contraseñas no coinciden');
	  return;
  }
	
  const request = await fetch('api/usuarios', {
    //Utilizamos el método POST
    method: 'POST',
    //Indicamos que usaremos JSON
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datosRegistro)
  });
  
  alert('Usuario creado correctamente');
  window.location.href = 'login.html';
  
  //Aquí convertimos el resultado en JSON
  const usuarios = await request.json();

  //Esto nos imprime que está ocurriendo por consola
  console.log(usuarios);
}