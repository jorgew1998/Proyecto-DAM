# Memorama

## Inicio de sesión 

Para realizar el inicio de sesión, se hace uso de Firebase, a traves de la FirebaseAuth y es intanciada en su declaración para poder optimizar el código, disminuir lineas de código y disminuir el uso del procesador del teéfono móvil 

```
FirebaseAuth mAuth = FirebaseAuth.getInstance();
```

Una vez instaciada podemos hacer uso de los métodos de inicio de sesión que nos proporciona Firebase, en ese sentido para poder realizar el inicio de sesión hacemos uso de la propiedad ```signInWithEmailAndPassword ``` 

