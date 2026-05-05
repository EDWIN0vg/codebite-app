package com.example.codebite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

// --- COLORES ---
val ColorFondoApp = Color.Black
val ColorGrisBarra = Color(0xFF1E1E1E)
val ColorTextoDestacadoCard = Color.Black
val ColorCardHeader = Color.White
val ColorCodigoComentario = Color.Gray
val ColorBotonCircular = Color.White

val ColorBotonQuizGris = Color(0xFF5A5A5A)
val ColorBotonQuizVerde = Color(0xFF2ECC71)
val ColorBotonQuizRojo = Color(0xFFC0392B)
val ColorAcentoNaranja = Color(0xFFE67E22)

// Colores Barras de Progreso
val ColorBarraFondo = Color(0xFF333333)
val ColorPython = Color(0xFF4B8BBE)
val ColorJava = Color(0xFFF89820)
val ColorCpp = Color(0xFF00599C)

// --- ESTRUCTURAS DE DATOS PARA LAS PANTALLAS GENÉRICAS ---
data class DatosLeccion(val num: String, val titulo: String, val desc: String, val cod1: String, val cod2: String, val cod3: String)
data class DatosQuiz(val pregunta: String, val cod1: String, val cod2: String, val opciones: List<Pair<String, String>>, val correcta: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CodeBiteApp() }
    }
}

// 1. EL CEREBRO DE LA APP (NAVEGACIÓN ACTUALIZADA)
@Composable
fun CodeBiteApp() {
    var pantallaActual by remember { mutableStateOf("Inicio") }
    var lenguajeSeleccionado by remember { mutableStateOf("Python") } // Nueva variable para saber qué se presionó

    BackHandler(enabled = pantallaActual != "Inicio") { pantallaActual = "Inicio" }

    Scaffold(
        bottomBar = {
            if (pantallaActual == "Inicio" || pantallaActual == "Perfil" || pantallaActual == "Desafios") {
                BarraNavegacionInferior(
                    pantallaSeleccionada = pantallaActual,
                    alNavegar = { destino -> pantallaActual = destino }
                )
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize().padding(paddingValues), color = ColorFondoApp) {
            when (pantallaActual) {
                "Inicio" -> PantallaInicio(alNavegar = { destino ->
                    // Si el destino es un lenguaje, lo guardamos y vamos a la lección
                    if (destino in listOf("Java", "Python", "C++")) {
                        lenguajeSeleccionado = destino
                        pantallaActual = "Leccion"
                    } else {
                        pantallaActual = destino
                    }
                })
                "Perfil" -> PantallaPerfil(alVolver = { pantallaActual = "Inicio" })
                "Desafios" -> PantallaDesafios(alVolver = { pantallaActual = "Inicio" })

                "Leccion" -> {
                    // Aquí llenamos los datos dependiendo del lenguaje que eligió
                    val datos = when(lenguajeSeleccionado) {
                        "Java" -> DatosLeccion("01", "JAVA: VARIABLES", "Aprende a declarar variables estrictas.", "// Ejemplo:", "int edad = 20;", "String nombre = \"Edwin\";")
                        "C++" -> DatosLeccion("01", "C++: POINTERS", "Los punteros almacenan memoria.", "// Ejemplo:", "int var = 20;", "int* ptr = &var;")
                        else -> DatosLeccion("02", "PYTHON: LIST COMPREHENSIONS", "Recorre listas rápido.", "# Ejemplo:", "numeros = [1, 2]", "dobles = [x*2 for x in numeros]")
                    }
                    PantallaDetalleLenguaje(datos, alVolver = { pantallaActual = "Inicio" }, alSiguiente = { pantallaActual = "Quiz" })
                }

                "Quiz" -> {
                    // Preguntas que cambian según el lenguaje
                    val quiz = when(lenguajeSeleccionado) {
                        "Java" -> DatosQuiz("¿Qué tipo de dato es 'edad'?", "int edad = 20;", "", listOf("A" to "A) String", "B" to "B) Float", "C" to "C) int", "D" to "D) Boolean"), "C")
                        "C++" -> DatosQuiz("¿Qué guarda un puntero?", "int* ptr = &var;", "", listOf("A" to "A) String", "B" to "B) Dirección de memoria", "C" to "C) int", "D" to "D) Nada"), "B")
                        else -> DatosQuiz("¿Cuál es el resultado de 'dobles'?", "numeros = [1, 2]", "dobles = [x*10 for x in numeros]", listOf("A" to "A) [1, 2, 10]", "B" to "B) [10, 20]", "C" to "C) [11, 12]", "D" to "D) Error"), "B")
                    }
                    PantallaQuizLenguaje(quiz, alVolver = { pantallaActual = "Leccion" }, alSiguiente = { pantallaActual = "Resumen" })
                }

                "Resumen" -> PantallaResumen(alFinalizar = { pantallaActual = "Inicio" })
            }
        }
    }
}

// =========================================================================
// 2. PANTALLAS ANTERIORES (INICIO, LECCIÓN Y QUIZ ACTUALIZADO)
// =========================================================================

@Composable
fun PantallaInicio(alNavegar: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text("Aprovecha cada tiempo muerto.", color = Color.LightGray, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("CodeBite", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Text("¿Qué vamos a morder hoy?", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 32.dp))

        BotonLenguajeImagen(idImagen = R.drawable.logo_java, nombre = "Java", alHacerClic = { alNavegar("Java") })
        Spacer(modifier = Modifier.height(20.dp))
        BotonLenguajeImagen(idImagen = R.drawable.logo_python, nombre = "Python", alHacerClic = { alNavegar("Python") })
        Spacer(modifier = Modifier.height(20.dp))
        BotonLenguajeImagen(idImagen = R.drawable.logo_cpp, nombre = "C++", alHacerClic = { alNavegar("C++") })
    }
}

@Composable
fun PantallaDetalleLenguaje(datos: DatosLeccion, alVolver: () -> Unit, alSiguiente: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(ColorFondoApp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            IconButton(onClick = alVolver, modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp), colors = CardDefaults.cardColors(containerColor = ColorCardHeader), shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = datos.num, color = ColorTextoDestacadoCard, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = datos.titulo, color = ColorTextoDestacadoCard, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = datos.desc, color = ColorTextoDestacadoCard, fontSize = 16.sp)
                }
            }
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp)) {
                val codeTextStyle = TextStyle(fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 16.sp)
                Text(text = datos.cod1, style = codeTextStyle, color = ColorCodigoComentario)
                Text(text = datos.cod2, style = codeTextStyle)
                Text(text = datos.cod3, style = codeTextStyle, color = if (datos.cod3.startsWith("#") || datos.cod3.startsWith("//")) ColorCodigoComentario else Color.White)
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.BottomEnd) {
            Button(onClick = alSiguiente, modifier = Modifier.size(72.dp), shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = ColorBotonCircular), elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp), contentPadding = PaddingValues(0.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente", tint = Color.Black, modifier = Modifier.size(36.dp))
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PantallaQuizLenguaje(datos: DatosQuiz, alVolver: () -> Unit, alSiguiente: () -> Unit) {
    var opcionSeleccionada by remember { mutableStateOf<String?>(null) }
    var tiempoRestante by remember { mutableIntStateOf(120) }

    LaunchedEffect(key1 = tiempoRestante, key2 = opcionSeleccionada) {
        if (tiempoRestante > 0 && opcionSeleccionada == null) { delay(1000L); tiempoRestante-- }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = alVolver, modifier = Modifier.offset(x = (-16).dp)) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = String.format("%02d:%02d", tiempoRestante / 60, tiempoRestante % 60), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.width(80.dp).height(2.dp).background(ColorAcentoNaranja))
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = datos.pregunta, color = Color.White, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        val codeStyle = TextStyle(fontFamily = FontFamily.Monospace, color = Color.LightGray, fontSize = 18.sp)
        Text(text = datos.cod1, style = codeStyle)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = datos.cod2, style = codeStyle)
        Spacer(modifier = Modifier.height(48.dp))

        datos.opciones.forEach { (id, texto) ->
            val colorBoton = if (opcionSeleccionada != null) {
                when (id) {
                    datos.correcta -> ColorBotonQuizVerde
                    opcionSeleccionada -> ColorBotonQuizRojo
                    else -> ColorBotonQuizGris
                }
            } else ColorBotonQuizGris

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                Button(onClick = { if (opcionSeleccionada == null && tiempoRestante > 0) opcionSeleccionada = id }, colors = ButtonDefaults.buttonColors(containerColor = colorBoton), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(0.65f).height(56.dp), contentPadding = PaddingValues(horizontal = 16.dp)) {
                    Text(text = texto, color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                }
                if (opcionSeleccionada != null && id == datos.correcta) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = "Correcto", tint = Color.White, modifier = Modifier.size(36.dp))
                }
            }
        }
        if (opcionSeleccionada != null) {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = alSiguiente, colors = ButtonDefaults.buttonColors(containerColor = ColorAcentoNaranja), modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Continuar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


// =========================================================================
// 3. PANTALLA DE RESUMEN Y RACHA (MOCKUP FINAL)
// =========================================================================

@Composable
fun PantallaResumen(alFinalizar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // --- Círculo de Fuego (Racha) ---
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(140.dp)
                .border(6.dp, ColorAcentoNaranja, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Racha de días",
                tint = ColorAcentoNaranja,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "7", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "¡Racha de 7 días!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "+50 XP ganados hoy", color = Color.LightGray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(48.dp))

        // --- Barras de Progreso Reales ---
        BarraProgresoVisual("🐍", "Python", 0.8f, ColorPython)
        BarraProgresoVisual("☕", "Java", 0.3f, ColorJava)
        BarraProgresoVisual("🟦", "C++", 0.1f, ColorCpp)

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al fondo

        // --- Botón Finalizar ---
        Button(
            onClick = alFinalizar,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("FINALIZAR POR HOY", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Componente especial para que las barras de progreso se vean increíbles
@Composable
fun BarraProgresoVisual(emoji: String, lenguaje: String, progreso: Float, colorLlenado: Color) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
        Text(text = "$emoji $lenguaje", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        // Contenedor de la barra (Gris Oscuro)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(ColorBarraFondo, RoundedCornerShape(12.dp))
        ) {
            // Relleno de la barra (Color del lenguaje)
            Box(
                modifier = Modifier
                    .fillMaxWidth(progreso)
                    .fillMaxHeight()
                    .background(colorLlenado, RoundedCornerShape(12.dp))
            )
            // Porcentaje en texto
            Text(
                text = "${(progreso * 100).toInt()}%",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 12.dp)
            )
        }
    }
}

// =========================================================================
// 4. COMPONENTES RECICLADOS (Iguales)
// =========================================================================

@Composable
fun BotonLenguajeImagen(idImagen: Int, nombre: String, alHacerClic: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = alHacerClic, modifier = Modifier.size(90.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White), contentPadding = PaddingValues(16.dp), elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Image(painter = painterResource(id = idImagen), contentDescription = "Logo de $nombre", modifier = Modifier.fillMaxSize())
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = nombre, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BarraNavegacionInferior(pantallaSeleccionada: String, alNavegar: (String) -> Unit) {
    NavigationBar(containerColor = ColorGrisBarra, contentColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = pantallaSeleccionada == "Inicio",
            onClick = { alNavegar("Inicio") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.White, unselectedIconColor = Color.Gray, indicatorColor = Color.DarkGray)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = pantallaSeleccionada == "Perfil",
            onClick = { alNavegar("Perfil") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.White, unselectedIconColor = Color.Gray, indicatorColor = Color.DarkGray)
        )
    }
}

@Composable
fun PantallaPerfil(alVolver: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(100.dp))
        Text("Edwin", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Racha de 7 días", color = Color(0xFFE67E22), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = alVolver, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun PantallaDesafios(alVolver: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Desafíos Semanales", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Próximamente...", color = Color.LightGray, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = alVolver, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF313D4F))) {
            Text("Volver al Inicio", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaCodeBite() {
    CodeBiteApp()
}