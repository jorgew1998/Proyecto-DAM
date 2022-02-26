# Memorama

## Inicio de sesión 

Para realizar el inicio de sesión, se hace uso de Firebase, a traves de ``` FirebaseAuth ```y es intanciada en su declaración para poder optimizar el código, disminuir lineas de código y disminuir el uso del procesador del teéfono móvil 

```
FirebaseAuth mAuth = FirebaseAuth.getInstance();
```

Una vez instanciada podemos hacer uso de los métodos de inicio de sesión que nos proporciona Firebase, en ese sentido para poder realizar el inicio de sesión hacemos uso de la propiedad ```signInWithEmailAndPassword ``` permitienedonos realizar el inicio de sesión con un e-mail y contraseña proporcionadas por el usuario.

```
mAuth.signInWithEmailAndPassword(emailLo, passwordLo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "No se pudo iniciar sesión con los datos proporcionados. Por favor, revíselos e intente de nuevo.", Toast.LENGTH_LONG).show();
                }
            }
        });
```

Como dato adicional se puede visualizar en el código de la función el método ``` onStart ``` propio del IDE de desarrollo de Android Studio. Esa función detecta si al abrir la aplicación una sesión se encuentra activa, esto provoca que el usuario no tenga que pasar nuevamente por la pantalla de inicio de sesión, gracias a esto, se otorga  profesionalismo a la aplicación móvil.

![image](https://user-images.githubusercontent.com/58042215/155859117-9d5e802a-0bf1-4b2b-b742-101d832c933f.png)

Como se puede visualizar en la imágen anterior, se presenta la pantalla de inicio de sesión con botones adicionales tales como; ``` Registrarse ``` y  ```Recuperar Contraseña```. Estos botones nos permitirán realizar la transición entre actividades para realizar cada uno de los procesos mencionados en dichos botones.

## Registro de sesión 

Para realizar el registro de sesión llamamos a ```FirebaseAuth``` y ```FirebaseFirestore``` la primera nos ayuda a registrar los usuarios en el servicio de autenticación de Firebase y la segunda propiedad nos ayuda con la creación de una colección que permite almacenar el email del usuario registrado y un sobrenombre el cual usaremos para el registro de puntajes. 

![image](https://user-images.githubusercontent.com/58042215/155859324-cfa786b1-1268-4765-8e16-7401878aa615.png)

```
mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nickname", nickname);
                    map.put("email", email);

                    mFirestore.collection("Usuarios").document(mAuth.getCurrentUser().getUid()).set(map);

                    Intent main = new Intent(Registro.this, MainActivity.class);
                    startActivity(main);
                    finish();

                } else {
                    Toast.makeText(Registro.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
```

Como se puede denotar en el código de la función para realizar el registro de sesión se usa el método ```createUserWithEmailAndPassword``` otorgado por ```FirebaseAuth``` y se realiza la creación del usuario en la base de datos con un e-mail y contraseña otrogados por el mismo.

## Recuperar contraseña

Para realizar la recuperación de contraseña se hace uso de ```FirebaseAuth``` permitiéndonos traer el método de ```sendPasswordResetEmail``` a través del e-mail otorgado por el usuario de la aplicación móvil.
 
![image](https://user-images.githubusercontent.com/58042215/155859399-88f6ac03-2e14-4a40-a5e1-825f5647013e.png)

```
mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Recuperar.this, "Se ha enviado un correo para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Recuperar.this, "No se pudo enviar el correo elctrónico.", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();

                Intent intent = new Intent(Recuperar.this, Login.class);
                startActivity(intent);
            }
        });
```

Como dato adicional se puede visualizar en la imágen del código de la función que se hace uso del método ```setLanguageCode``` este método permite enviar un e-mail para resetear la contraseña con el lenguaje en español facilitando la comprensión del usuario al momento de recibir el correo electrónico.









