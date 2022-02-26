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

## Funcionalidad del juego

Para la funcionalidad del juego es necesario guardar las imagenes tanto para el tablero del memorama y el fondo en la carpeta drawable como se muestra en la siguiente imagen:

![image](https://user-images.githubusercontent.com/66254573/155860422-01dc9330-d244-4418-9a6e-0541f54761f3.png)

Se crea una archivo Juego.java el cual contiene específicamente el código del funcionamiento del mismo, se empieza con la declaración de las variables para los componentes de la vista, variables del juego e imagenes:

```
 ImageButton imb00, imb01, imb02, imb03, imb04, imb05, imb06, imb07, imb08, imb09, imb10, imb11, imb12, imb13, imb14, imb15;
    ImageButton[] tablero = new ImageButton[16];
    Button botonReiniciar, botonSalir, botonGuardarPuntaje;
    TextView textoPuntuacion;
    int puntuacion;
    int aciertos;

    int[] imagenes;
    int fondo;

    ArrayList<Integer> arrayDesordenado;
    ImageButton primero;
    int numeroPrimero, numeroSegundo;
    boolean bloqueo = false;
    final Handler handler = new Handler();
```

Una vez declarado lo necesario se genera una función la cual permitira cargar el tablero del juego por medio de la asignación a los componentes respectivos como se muestra a continuación:

```
private void cargarTablero(){
        imb00 = findViewById(R.id.boton00);
        imb01 = findViewById(R.id.boton01);
        imb02 = findViewById(R.id.boton02);
        imb03 = findViewById(R.id.boton03);
        imb04 = findViewById(R.id.boton04);
        imb05 = findViewById(R.id.boton05);
        imb06 = findViewById(R.id.boton06);
        imb07 = findViewById(R.id.boton07);
        imb08 = findViewById(R.id.boton08);
        imb09 = findViewById(R.id.boton09);
        imb10 = findViewById(R.id.boton10);
        imb11 = findViewById(R.id.boton11);
        imb12 = findViewById(R.id.boton12);
        imb13 = findViewById(R.id.boton13);
        imb14 = findViewById(R.id.boton14);
        imb15 = findViewById(R.id.boton15);

        tablero[0] = imb00;
        tablero[1] = imb01;
        tablero[2] = imb02;
        tablero[3] = imb03;
        tablero[4] = imb04;
        tablero[5] = imb05;
        tablero[6] = imb06;
        tablero[7] = imb07;
        tablero[8] = imb08;
        tablero[9] = imb09;
        tablero[10] = imb10;
        tablero[11] = imb11;
        tablero[12] = imb12;
        tablero[13] = imb13;
        tablero[14] = imb14;
        tablero[15] = imb15;
    }
```

Se necesita una función que permita presentar los botones para reinicar el juego, salir o guardar los puntajes del mismo, para lo cual se hace uso de setOnClickListener con cada botón como se muestra en la siguiente sección de código:

```
private void cargarBotones(){
        botonReiniciar = findViewById(R.id.botonJuegoReiniciar);
        botonSalir = findViewById(R.id.botonJuegoSalir);
        botonGuardarPuntaje = findViewById(R.id.botonGuardarP);
        this.botonGuardarPuntaje.setVisibility(View.GONE);

        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botonGuardarPuntaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaPuntaje();
            }
        });
    }
```

Exitse una función denominada como cargarTexto la cual permite mostrar en el elemento textoPuntiacion la puntuacion que el usuario se encuentra generando al momento de jugar como se muestra a continuación:

```
private void cargarTexto(){
        textoPuntuacion = findViewById(R.id.texto_puntuacion);
        puntuacion = 0;
        aciertos = 0;
        textoPuntuacion.setText("Puntuacion: " + puntuacion);
    }
```

Con respecto a la carga de imagenes se realiza una funcion la cual hace referencia las imagenes de la carpeta drawable como se muestra a continuación: 

```
private void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.la0,
                R.drawable.la1,
                R.drawable.la2,
                R.drawable.la3,
                R.drawable.la4,
                R.drawable.la5,
                R.drawable.la6,
                R.drawable.la7
        };
        fondo = R.drawable.fondo;
    }
```

Una de las funciones mas importantes para el funcionamiento del juego es la denominada como comprobar ya que esta función determina durante el juego si las carats seleccionadas por el usuario son iguales o no, es decir, se realiza la comprobración si la variable primero es igual a null, caso contrario a la variable bloqueo se el asigna un valor de true. Mientras el usuario se encuentra jugando los aciertos y la puntuacion iran modificándose ya que si el usuario se equivoca los aciertos disminurán, una vez que el usuario encuentre todas las parejas se presentara la puntuación obtenida y ademas un mensaje de Has ganado.

```
private void comprobar(int i, final ImageButton imgb){
        if(primero == null){
            primero = imgb;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayDesordenado.get(i)]);
            primero.setEnabled(false);
            numeroPrimero = arrayDesordenado.get(i);
        } else {
            bloqueo = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imagenes[arrayDesordenado.get(i)]);
            imgb.setEnabled(false);
            numeroSegundo = arrayDesordenado.get(i);
            if(numeroPrimero == numeroSegundo){
                primero = null;
                bloqueo = false;
                aciertos++;
                puntuacion++;
                textoPuntuacion.setText("Puntuación: " + puntuacion);
                if(aciertos == imagenes.length){
                    Toast toast = Toast.makeText(getApplicationContext(), "Has ganado!!", Toast.LENGTH_LONG);
                    toast.show();
                    this.botonGuardarPuntaje.setVisibility(View.VISIBLE);
                }
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(fondo);
                        primero.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(fondo);
                        imgb.setEnabled(true);
                        bloqueo = false;
                        primero = null;
                        puntuacion--;
                        textoPuntuacion.setText("Puntuación: " + puntuacion);
                    }
                }, 1000);
            }
        }
    }
```

La pantalla correspondiente al juego se puede visualizar a coninuación la cual muestra a un usuario jugando mientras la puntuacion se modifica:

![WhatsApp Image 2022-02-26 at 16 58 08 (1)](https://user-images.githubusercontent.com/66254573/155860870-f6d374bf-4b45-4ec2-8801-e2c88f8cd2bd.jpeg)



