# Memorama

## Inicio de sesión 

Para realizar el inicio de sesión, se hace uso de Firebase, a traves de la FirebaseAuth y es intanciada en su declaración para poder optimizar el código, disminuir lineas de código y disminuir el uso del procesador del teéfono móvil 

```
FirebaseAuth mAuth = FirebaseAuth.getInstance();
```

Una vez instanciada podemos hacer uso de los métodos de inicio de sesión que nos proporciona Firebase, en ese sentido para poder realizar el inicio de sesión hacemos uso de la propiedad ```signInWithEmailAndPassword ``` permitienedonos realizar el inicio de sesión con un e-mail y contraseña proporcionadas por el usuario, como dato adicional se crea la funcion ``` onStart ``` propia del IDE de desarrollo de Android Studio. Esa función detecta si al abrir la aplicación una sesión se encuentra activa, esto provoca que el usuario no tenga que pasar nuevamente por la pantalla de inicio de sesión, gracias a esto, se otorga  profesionalismo a la aplicación móvil.

![image](https://user-images.githubusercontent.com/58042215/155858770-844687a5-32cf-49c0-9ddd-abfea792bec2.png)

![Screenshot_1645908992](https://user-images.githubusercontent.com/58042215/155858817-a37fe0a0-2733-4abf-b0d0-567cc43e1050.png)

Como se puede visualizar en la imágen anterior, se presenta la pantalla de inicio de sesión con botones adicionales tales como; ``` Registrarse ``` y  ```Recuperar Contraseña``` . Estos botones nos permitirán realizar la transición entre actividades para realizar cada uno de los procesos mencionados en dichos botones.




