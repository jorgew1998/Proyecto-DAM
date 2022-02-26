# Memorama

## Inicio de sesión 

Para realizar el inicio de sesión, se hace uso de Firebase, a traves de la FirebaseAuth y es intanciada en su declaración para poder optimizar el código, disminuir lineas de código y disminuir el uso del procesador del teéfono móvil 

```
FirebaseAuth mAuth = FirebaseAuth.getInstance();
```

Una vez instanciada podemos hacer uso de los métodos de inicio de sesión que nos proporciona Firebase, en ese sentido para poder realizar el inicio de sesión hacemos uso de la propiedad ```signInWithEmailAndPassword ``` permitienedonos realizar el inicio de sesión con un e-mail y contraseña proporcionadas por el usuario, como dato adicional se crea la funcion ``` onStart ``` propia del IDE de desarrollo de Android Studio. Esa función detecta si al abrir la aplicación una sesión se encuentra activa, esto provoca que el usuario no tenga que pasar nuevamente por la pantalla de inicio de sesión, gracias a esto, se otorga  profesionalismo a la aplicación móvil.



