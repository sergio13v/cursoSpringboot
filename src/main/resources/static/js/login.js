//Esta función manda a ejecutar todo el código que hay en su interior una vez se ejecute la página
$(document).ready(function() {
	alert("Login");
});

//Buscamos un ejemplo de fetch en stackoverflow para crear una función que nos recoja los usuarios
//Cuando usamos await hay que indicarle a la función que es asincrónica
async function loginUsuario() {
  let datosLogin = {};
  
  //Recogemos los id de cada input del html. Dejo comentada otra manera de recogerlos
  //datosRegistro.nombre = document.querySelector('#nombre').value;
  datosLogin.email = document.getElementById('email').value;
  datosLogin.password = document.getElementById('password').value;
	
  const request = await fetch('api/login', {
    //Utilizamos el método POST
    method: 'POST',
    //Indicamos que usaremos JSON
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datosLogin)
  });
  
  //Aquí convertimos el resultado en JSON
  //const usuarios = await request.json();
  
  //Con esto recogemos la respuesta que enviamos en el AuthController
  const respuesta = await request.text();
  if(respuesta != 'usuarioNoOk') {
	  //Guardamos el token en el lado del navegador del cliente
	  localStorage.token = respuesta;
	  //Podemos guardar más información como el email
	  localStorage.email = datosLogin.email;
	  window.location.href = 'usuarios.html';
  } else {
	  alert('Usuario o contraseña incorrecto');
  }

  //Esto nos imprime que está ocurriendo por consola
  console.log(respuesta);
}