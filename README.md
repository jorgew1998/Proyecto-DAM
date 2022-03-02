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

## Envio de los puntajes a Firebase

Para realizar el respectivo envio de los puntajes a Firebase cada vez que un usuario termina una pantalla entonces sera necesario un boton que cumpla con esta función, aunque unicamente este boton aparecerá al usuario una vez completado el juego, como se muestra en la siguiente imagen.


<img src="https://user-images.githubusercontent.com/58036212/155887597-f5779541-b758-4785-91c5-c583f451bad5.png" width="361">

Para esto es necesario implementar todas las configuraciones para añadir nuestro proyecto a firebase. Como primer paso tenemos el de modififcar el archivo Gradle poniendo las siguientes lineas en nuestro gradle a nivel de módulo:

```
dependencies {
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:29.0.0')

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-firestore'
}
```
Ahora en nuestro archivo de juego procedemos a importar las siguientes importaciones para que se reconozca las instrucciones en nuestra clase, las primera funciona para realizar la operación de obtener el nombre y las siguientes para trabajar con la base de datos de Firestore:

```
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
```

Lo siguiente es declarar el boton y darle la propiedad de que no se presente al iniciar el juego y la orden que solo al presionarlo realice la función:

```
        botonGuardarPuntaje = findViewById(R.id.botonGuardarP);
        this.botonGuardarPuntaje.setVisibility(View.GONE);
        ...
        ...
        botonGuardarPuntaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaPuntaje();
            }
        });
```

Por último, para darle las funcionalidades al boton, creamos la funcion guardaPuntaje, para que esta en primer orden obtenga el nombre del usuario con la sesión activa y posterior a ello guarde en la colección "scores" el nombre del usuario y el puntaje que obtuvo:

```
private void guardaPuntaje() {
        // Create a new user with a first and last name



        db.collection("Usuarios")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> score = new HashMap<>();
                                score.put("user", document.getData().get("nickname"));
                                score.put("score", puntuacion);
                                // Add a new document with a generated ID
                                db.collection("scores")
                                        .add(score)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        this.botonGuardarPuntaje.setVisibility(View.GONE);
    }
```

El resultado que fue guardado en la base de datos se encontrara en la coleccion scores en firestore como se muestra en la siguiente imagen.

![image](https://user-images.githubusercontent.com/58036212/155887495-651c09e7-43d2-4d82-a5e1-ffc252e709f8.png)

## Pantalla de Puntajes

Primero implementamos una actividad llamada ScoreView. Esta pantalla nos mostrará los puntajes de los jugadores que han jugado ordenados de forma descendente.

![image](https://user-images.githubusercontent.com/58042023/156452603-78428f43-9fe9-4215-95dc-a02fbe181899.png)


Para mostrar la pantalla de puntajes más altos como podemos ver en la imagen. Utilizaremos la base de datos Firestore 
![Screenshot_105](https://user-images.githubusercontent.com/58042023/156451607-a20a9dd5-1a3a-4b07-a0a9-4db2f8d61047.png) 

Para leer los documentos directamente desde Firestore utilizaremos las funciones proporcionadas por la propia documentacion de Firebase
Además, crearemos Filas para la tabla a medida que vamos recolectando cada dato de forma uniforme.

```
db.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(40)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());

                                TableRow tr = new TableRow(ScoresView.this);
                                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                                // textwiew username
                                TextView txtUsername = new TextView(ScoresView.this);
                                txtUsername.setText(document.getData().get("user").toString());
                                txtUsername.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT));
                                txtUsername.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                txtUsername.setPadding(25,0,0,0);
                                tr.addView(txtUsername);
                                // textview scores
                                TextView txtScores = new TextView(ScoresView.this);
                                txtScores.setText(document.getData().get("score").toString());
                                txtScores.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                                txtScores.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                txtScores.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                tr.addView(txtScores);
                                table.addView(tr);
                            }
//                            System.out.println("size1 "+scoresMap.size());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
```

## Descarga de aplicación en Aptoide

Como primer paso se debe descaragar la apliación de aptoide, y se dirige a la seccion de Tiendos o Store:

<img src="https://user-images.githubusercontent.com/66254573/156453667-fc2329d0-3df9-42a5-a1b3-a468da011ce0.png" width="361">

Se pulsa el botón de seguir tienda:

<img src="https://user-images.githubusercontent.com/66254573/156453805-de1dce33-0cf6-44f3-bbd4-9cecff655e47.png" width="361">

Se escribe el nombre jorchdam el cual corresponde al nombre de la tienda:

<img src="https://user-images.githubusercontent.com/66254573/156453918-adb4cc75-929f-45bf-9da4-2a7cf6d34d89.png" width="361">

Se selecciona la opción de explorar:

<img src="https://user-images.githubusercontent.com/66254573/156453988-0850e2d3-80e6-4b23-878c-a13a2a937939.png" width="361">

Se selcciona la aplicación llamada memorama:

<img src="https://user-images.githubusercontent.com/66254573/156454090-678485fa-a833-4050-b9ea-bcf0f63009a9.png" width="361">

Por último se selecciona la opcion de instalar y la aplicación estara disponible en el dospositivo:

<img src="https://user-images.githubusercontent.com/66254573/156454248-f2b98a79-e03f-4a89-b7c2-57756a1ad391.png" width="361">


# Links de los vídeos

* https://www.youtube.com/watch?v=5hBvm_tnrHY&feature=youtu.be

* 

