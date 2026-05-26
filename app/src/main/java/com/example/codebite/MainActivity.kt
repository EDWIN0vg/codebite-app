package com.example.codebite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeBiteApp()
        }
    }
}

// --- COLORES GLOBALES ---
val ColorFondoApp = Color.Black
val ColorGrisBarra = Color(0xFF1E1E1E)
val ColorTextoDestacadoCard = Color.Black
val ColorCardHeader = Color.White
val ColorBotonCircular = Color.White
val ColorAcentoNaranja = Color(0xFFE67E22)

val ColorBarraFondo = Color(0xFF333333)
val ColorPython = Color(0xFF4B8BBE)
val ColorJava = Color(0xFFF89820)
val ColorCpp = Color(0xFF00599C)

// --- ESTRUCTURAS DE DATOS ---
data class DatosLeccion(val num: String, val titulo: String, val desc: String, val cod1: String, val cod2: String, val cod3: String)
data class DatosQuiz(val pregunta: String, val cod1: String, val cod2: String, val opciones: List<Pair<String, String>>, val correcta: String, val explicacion: String)

// --- SINTAXIS IDE ---
@Composable
fun TextoCodigoIDE(texto: String, modifier: Modifier = Modifier) {
    val palabraClaveColor = Color(0xFFCC7832)
    val textoColor = Color(0xFF6A8759)
    val numeroColor = Color(0xFF6897BB)
    val palabrasClave = listOf("int", "String", "for", "in", "int*", "numeros", "dobles")

    val textoFormateado = buildAnnotatedString {
        if (texto.startsWith("//") || texto.startsWith("#")) {
            withStyle(style = SpanStyle(color = Color.Gray, fontStyle = FontStyle.Italic)) { append(texto) }
        } else {
            val palabras = texto.split(" ")
            palabras.forEachIndexed { index, palabra ->
                val palabraLimpia = palabra.replace(";", "").replace("=", "").replace("[", "").replace("]", "").trim()
                val estilo = when {
                    palabrasClave.contains(palabraLimpia) -> SpanStyle(color = palabraClaveColor, fontWeight = FontWeight.Bold)
                    palabra.contains("\"") || palabra.contains("'") -> SpanStyle(color = textoColor)
                    palabra.any { it.isDigit() } -> SpanStyle(color = numeroColor)
                    else -> SpanStyle(color = Color.White)
                }
                withStyle(style = estilo) { append(palabra) }
                if (index < palabras.size - 1) append(" ")
            }
        }
    }
    Text(text = textoFormateado, fontFamily = FontFamily.Monospace, fontSize = 16.sp, modifier = modifier)
}

// --- EL CEREBRO DE LA APP ---
@Composable
fun CodeBiteApp() {
    var sesionIniciada by remember { mutableStateOf(false) }
    var xpUsuario by remember { mutableIntStateOf(0) }
    var xpGanadaUltimoQuiz by remember { mutableIntStateOf(0) }
    var nivelSeleccionado by remember { mutableStateOf("Junior") }

    var pantallaActual by remember { mutableStateOf("Inicio") }
    var lenguajeSeleccionado by remember { mutableStateOf("Python") }

    BackHandler(enabled = sesionIniciada && pantallaActual != "Inicio") { pantallaActual = "Inicio" }

    if (!sesionIniciada) {
        PantallaLogin(alIniciarSesion = {
            sesionIniciada = true
            xpUsuario = 0
            pantallaActual = "Inicio"
        })
    } else {
        Scaffold(
            bottomBar = {
                if (pantallaActual == "Inicio" || pantallaActual == "Perfil" || pantallaActual == "Desafios") {
                    BarraNavegacionInferior(pantallaSeleccionada = pantallaActual, alNavegar = { destino -> pantallaActual = destino })
                }
            }
        ) { paddingValues ->
            Surface(modifier = Modifier.fillMaxSize().padding(paddingValues), color = ColorFondoApp) {
                when (pantallaActual) {
                    "Inicio" -> PantallaInicio(
                        nivelActual = nivelSeleccionado,
                        alCambiarNivel = { nivelSeleccionado = it },
                        alNavegar = { destino ->
                            if (destino in listOf("Java", "Python", "C++")) {
                                lenguajeSeleccionado = destino
                                pantallaActual = "Leccion"
                            } else { pantallaActual = destino }
                        }
                    )
                    "Perfil" -> PantallaPerfil(xpTotal = xpUsuario, alCerrarSesion = { sesionIniciada = false }, alVolver = { pantallaActual = "Inicio" })
                    "Desafios" -> PantallaDesafios(alVolver = { pantallaActual = "Inicio" })
                    "Leccion" -> {
                        val nivelTexto = " [$nivelSeleccionado]"
                        val datos = when(lenguajeSeleccionado) {
                            "Java" -> DatosLeccion("01", "JAVA: VARIABLES$nivelTexto", "Aprende a declarar variables.", "// Ejemplo:", "int edad = 20;", "String nombre = \"Edwin\";")
                            "C++" -> DatosLeccion("01", "C++: POINTERS$nivelTexto", "Los punteros almacenan memoria.", "// Ejemplo:", "int var = 20;", "int* ptr = &var;")
                            else -> DatosLeccion("02", "PYTHON: LISTAS$nivelTexto", "Recorre listas rápido.", "# Ejemplo:", "numeros = [1, 2]", "dobles = [x*2 for x in numeros]")
                        }
                        PantallaDetalleLenguaje(datos, alVolver = { pantallaActual = "Inicio" }, alSiguiente = { pantallaActual = "Quiz" })
                    }
                    "Quiz" -> {
                        val quiz = when(lenguajeSeleccionado) {
                            "Java" -> DatosQuiz("¿Qué tipo de dato es 'edad'?", "int edad = 20;", "", listOf("A" to "A) String", "B" to "B) Float", "C" to "C) int", "D" to "D) Boolean"), "C", "¡Correcto! En Java, 'int' es entero.")
                            "C++" -> DatosQuiz("¿Qué guarda un puntero?", "int* ptr = &var;", "", listOf("A" to "A) String", "B" to "B) Dirección de memoria", "C" to "C) int", "D" to "D) Nada"), "B", "¡Correcto! El operador '&' obtiene la dirección.")
                            else -> DatosQuiz("¿Cuál es el resultado de 'dobles'?", "numeros = [1, 2]", "dobles = [x*10 for x in numeros]", listOf("A" to "A) [1, 2, 10]", "B" to "B) [10, 20]", "C" to "C) [11, 12]", "D" to "D) Error"), "B", "¡Correcto! Genera la lista [10, 20].")
                        }
                        PantallaQuizLenguaje(datos = quiz, alVolver = { pantallaActual = "Leccion" }, alSiguiente = { fueCorrecta ->
                            if (fueCorrecta) { xpGanadaUltimoQuiz = 50; xpUsuario += 50 } else { xpGanadaUltimoQuiz = 0 }
                            pantallaActual = "Resumen"
                        }
                        )
                    }
                    "Resumen" -> PantallaResumen(xpGanada = xpGanadaUltimoQuiz, alFinalizar = { pantallaActual = "Inicio" })
                }
            }
        }
    }
}

// --- PANTALLAS ---
@Composable
fun PantallaLogin(alIniciarSesion: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("CodeBite", color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Micro-learning para Devs", color = Color.LightGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = alIniciarSesion, colors = ButtonDefaults.buttonColors(containerColor = ColorAcentoNaranja), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(0.7f).height(56.dp)) {
            Text("Iniciar Sesión", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PantallaInicio(nivelActual: String, alCambiarNivel: (String) -> Unit, alNavegar: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("CodeBite", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Especialización:", color = Color.LightGray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            listOf("Junior", "Mid", "Senior").forEach { nivel ->
                Button(
                    onClick = { alCambiarNivel(nivel) },
                    colors = ButtonDefaults.buttonColors(containerColor = if (nivelActual == nivel) ColorAcentoNaranja else Color.DarkGray),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(horizontal = 4.dp).height(36.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                ) { Text(nivel, color = if (nivelActual == nivel) Color.Black else Color.White, fontWeight = FontWeight.Bold) }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("¿Qué vamos a morder hoy?", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(24.dp))

        // RESTAURADO: Usando imágenes reales de nuevo
        BotonLenguajeImagen(idImagen = R.drawable.logo_java, nombre = "Java", alHacerClic = { alNavegar("Java") })
        Spacer(modifier = Modifier.height(16.dp))
        BotonLenguajeImagen(idImagen = R.drawable.logo_python, nombre = "Python", alHacerClic = { alNavegar("Python") })
        Spacer(modifier = Modifier.height(16.dp))
        BotonLenguajeImagen(idImagen = R.drawable.logo_cpp, nombre = "C++", alHacerClic = { alNavegar("C++") })
    }
}

// RESTAURADO: Componente original que usa recursos Drawable
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
fun PantallaDetalleLenguaje(datos: DatosLeccion, alVolver: () -> Unit, alSiguiente: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(ColorFondoApp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            IconButton(onClick = alVolver, modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                Text("←", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
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
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF0F0F0F)).padding(24.dp)) {
                TextoCodigoIDE(texto = datos.cod1)
                Spacer(modifier = Modifier.height(4.dp))
                TextoCodigoIDE(texto = datos.cod2)
                Spacer(modifier = Modifier.height(4.dp))
                TextoCodigoIDE(texto = datos.cod3)
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.BottomEnd) {
            Button(onClick = alSiguiente, modifier = Modifier.size(72.dp), shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = ColorBotonCircular), elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp), contentPadding = PaddingValues(0.dp)) {
                Text("→", color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PantallaQuizLenguaje(datos: DatosQuiz, alVolver: () -> Unit, alSiguiente: (Boolean) -> Unit) {
    val verdeQuiz = Color(0xFF2ECC71); val rojoQuiz = Color(0xFFE74C3C); val grisQuiz = Color(0xFF2C2C2C); val naranjaAcento = Color(0xFFE67E22); val fondoExplicacion = Color(0xFF1E1E1E)
    var opcionSeleccionada by remember { mutableStateOf<String?>(null) }
    var tiempoRestante by remember { mutableIntStateOf(120) }
    LaunchedEffect(key1 = tiempoRestante, key2 = opcionSeleccionada) {
        if (tiempoRestante > 0 && opcionSeleccionada == null) { delay(1000L); tiempoRestante-- }
    }
    val min = (tiempoRestante / 60).toString().padStart(2, '0'); val sec = (tiempoRestante % 60).toString().padStart(2, '0'); val tiempoTexto = "$min:$sec"
    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = alVolver, modifier = Modifier.offset(x = (-16).dp)) { Text("←", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(text = tiempoTexto, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold); Spacer(modifier = Modifier.height(4.dp)); Box(modifier = Modifier.width(80.dp).height(2.dp).background(naranjaAcento)) }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = datos.pregunta, color = Color.White, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF0F0F0F)).padding(16.dp)) {
            if (datos.cod1.isNotEmpty()) TextoCodigoIDE(texto = datos.cod1)
            if (datos.cod2.isNotEmpty()) TextoCodigoIDE(texto = datos.cod2)
        }
        Spacer(modifier = Modifier.height(24.dp))
        datos.opciones.forEach { (id, texto) ->
            val colorBoton = if (opcionSeleccionada != null) { when (id) { datos.correcta -> verdeQuiz; opcionSeleccionada -> rojoQuiz; else -> grisQuiz } } else grisQuiz
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                Button(onClick = { if (opcionSeleccionada == null && tiempoRestante > 0) opcionSeleccionada = id }, colors = ButtonDefaults.buttonColors(containerColor = colorBoton), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().height(50.dp), contentPadding = PaddingValues(horizontal = 16.dp)) {
                    Text(text = texto, color = Color.White, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                }
            }
        }
        if (opcionSeleccionada != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = fondoExplicacion), shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, if (opcionSeleccionada == datos.correcta) verdeQuiz else rojoQuiz)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = if (opcionSeleccionada == datos.correcta) "¡RESPUESTA CORRECTA!" else "RESPUESTA INCORRECTA", color = if (opcionSeleccionada == datos.correcta) verdeQuiz else rojoQuiz, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = datos.explicacion, color = Color.LightGray, fontSize = 14.sp, lineHeight = 18.sp)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { alSiguiente(opcionSeleccionada == datos.correcta) }, colors = ButtonDefaults.buttonColors(containerColor = naranjaAcento), modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) { Text("Continuar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
fun PantallaResumen(xpGanada: Int, alFinalizar: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(48.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(140.dp).border(6.dp, ColorAcentoNaranja, CircleShape)) {
            Icon(imageVector = Icons.Filled.Star, contentDescription = "Racha de días", tint = ColorAcentoNaranja, modifier = Modifier.size(80.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "7", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "¡Racha de 7 días!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(24.dp))
        val textoXp = if (xpGanada > 0) "+$xpGanada XP ganados hoy" else "0 XP ganados"
        val colorXp = if (xpGanada > 0) Color(0xFF2ECC71) else Color.LightGray
        Text(text = textoXp, color = colorXp, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(48.dp))
        BarraProgresoVisual("🐍", "Python", 0.8f, ColorPython)
        BarraProgresoVisual("☕", "Java", 0.3f, ColorJava)
        BarraProgresoVisual("🟦", "C++", 0.1f, ColorCpp)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = alFinalizar, colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().height(56.dp)) { Text("FINALIZAR POR HOY", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
    }
}

@Composable
fun BarraProgresoVisual(emoji: String, lenguaje: String, progreso: Float, colorLlenado: Color) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
        Text(text = "$emoji $lenguaje", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(24.dp).background(ColorBarraFondo, RoundedCornerShape(12.dp))) {
            Box(modifier = Modifier.fillMaxWidth(progreso).fillMaxHeight().background(colorLlenado, RoundedCornerShape(12.dp)))
            Text(text = "${(progreso * 100).toInt()}%", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterEnd).padding(end = 12.dp))
        }
    }
}

@Composable
fun BarraNavegacionInferior(pantallaSeleccionada: String, alNavegar: (String) -> Unit) {
    NavigationBar(containerColor = ColorGrisBarra, contentColor = Color.White) {
        NavigationBarItem(icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }, label = { Text("Inicio") }, selected = pantallaSeleccionada == "Inicio", onClick = { alNavegar("Inicio") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.White, unselectedIconColor = Color.Gray, indicatorColor = Color.DarkGray))
        NavigationBarItem(icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }, label = { Text("Perfil") }, selected = pantallaSeleccionada == "Perfil", onClick = { alNavegar("Perfil") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.White, unselectedIconColor = Color.Gray, indicatorColor = Color.DarkGray))
    }
}

@Composable
fun PantallaPerfil(xpTotal: Int, alCerrarSesion: () -> Unit, alVolver: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(100.dp))
        Text("Edwin", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Experiencia Total: $xpTotal XP", color = Color(0xFFE67E22), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = alCerrarSesion, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) { Text("Cerrar Sesión", color = Color.White) }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = alVolver, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) { Text("Volver", color = Color.Gray) }
    }
}

@Composable
fun PantallaDesafios(alVolver: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Desafíos Semanales", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Próximamente...", color = Color.LightGray, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = alVolver, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF313D4F))) { Text("Volver al Inicio", color = Color.White) }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaCodeBite() { CodeBiteApp() }