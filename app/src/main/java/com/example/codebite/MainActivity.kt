package com.example.codebite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
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

    Scaffold(
        bottomBar = {
            // La barra inferior solo se muestra en el Inicio
            if (pantallaActual == "Inicio") {
                BarraNavegacionInferior()
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            color = ColorFondoApp
        ) {
            when (pantallaActual) {
                "Inicio" -> PantallaInicio(alNavegar = { destino -> pantallaActual = destino })

                "Python" -> PantallaDetallePython(
                    alVolver = { pantallaActual = "Inicio" },
                    alSiguiente = { pantallaActual = "QuizPython" }
                )

                "QuizPython" -> PantallaQuizPython(
                    alVolver = { pantallaActual = "Python" },
                    alSiguiente = { pantallaActual = "Resumen" } // Flujo hacia el resumen
                )

                // ¡NUEVA! Pantalla de racha y resultados
                "Resumen" -> PantallaResumen(
                    alFinalizar = { pantallaActual = "Inicio" }
                )

                "Java" -> PantallaLenguajeSimple("JAVA", alVolver = { pantallaActual = "Inicio" })
                "C++" -> PantallaLenguajeSimple("C++", alVolver = { pantallaActual = "Inicio" })
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
fun PantallaDetallePython(alVolver: () -> Unit, alSiguiente: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(ColorFondoApp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            IconButton(onClick = alVolver, modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = ColorCardHeader), shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = "02", color = ColorTextoDestacadoCard, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "PYTHON: LIST COMPREHENSIONS", color = ColorTextoDestacadoCard, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Se usa para recorrer todos los elementos de una lista de forma sencilla sin usar contadores.", color = ColorTextoDestacadoCard, fontSize = 16.sp, fontWeight = FontWeight.Normal)
                }
            }
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp)) {
                val codeTextStyle = TextStyle(fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 16.sp)
                Text(text = "# Ejemplo rápido:", style = codeTextStyle, color = ColorCodigoComentario)
                Text(text = "numeros = [1, 2, 3]", style = codeTextStyle)
                Text(text = "dobles = [x * 2 for x in numeros]", style = codeTextStyle)
                Text(text = "# Resultado: [2, 4, 6]", style = codeTextStyle, color = ColorCodigoComentario)
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
fun PantallaQuizPython(alVolver: () -> Unit, alSiguiente: () -> Unit) {
    var opcionSeleccionada by remember { mutableStateOf<String?>(null) }
    val respuestaCorrecta = "B"

    var tiempoRestante by remember { mutableIntStateOf(120) }
    LaunchedEffect(key1 = tiempoRestante, key2 = opcionSeleccionada) {
        if (tiempoRestante > 0 && opcionSeleccionada == null) {
            delay(1000L)
            tiempoRestante--
        }
    }
    val minutos = tiempoRestante / 60
    val segundos = tiempoRestante % 60
    val tiempoTexto = String.format("%02d:%02d", minutos, segundos)

    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(24.dp)) {

        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = alVolver, modifier = Modifier.offset(x = (-16).dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = tiempoTexto, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.width(80.dp).height(2.dp).background(ColorAcentoNaranja))
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
        Text(text = "¿Cuál es el resultado de 'dobles'?", color = Color.White, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))

        val codeStyle = TextStyle(fontFamily = FontFamily.Monospace, color = Color.LightGray, fontSize = 18.sp)
        Text(text = "numeros = [1, 2]", style = codeStyle)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "dobles = [x * 10 for x in numeros]", style = codeStyle)
        Spacer(modifier = Modifier.height(48.dp))

        val opciones = listOf("A" to "A) [1, 2, 10, 20]", "B" to "B) [10, 20]", "C" to "C) [11, 12]]", "D" to "D) Error de Sintaxis")

        opciones.forEach { (id, texto) ->
            val colorBoton = if (opcionSeleccionada != null) {
                when (id) {
                    respuestaCorrecta -> ColorBotonQuizVerde
                    opcionSeleccionada -> ColorBotonQuizRojo
                    else -> ColorBotonQuizGris
                }
            } else ColorBotonQuizGris

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                Button(
                    onClick = { if (opcionSeleccionada == null && tiempoRestante > 0) opcionSeleccionada = id },
                    colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                    shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(0.65f).height(56.dp), contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text(text = texto, color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                }
                if (opcionSeleccionada != null && id == respuestaCorrecta) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = "Correcto", tint = Color.White, modifier = Modifier.size(36.dp))
                }
            }
        }

        // --- ¡NUEVO! Botón para avanzar después de responder ---
        if (opcionSeleccionada != null) {
            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo
            Button(
                onClick = alSiguiente,
                colors = ButtonDefaults.buttonColors(containerColor = ColorAcentoNaranja),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continuar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// =========================================================================
// !!! NUEVA !!! 3. PANTALLA DE RESUMEN Y RACHA (MOCKUP FINAL)
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
fun BarraNavegacionInferior() {
    NavigationBar(containerColor = ColorGrisBarra, contentColor = Color.White) {
        NavigationBarItem(icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }, label = { Text("Inicio") }, selected = true, onClick = { }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.White, unselectedIconColor = Color.Gray, selectedTextColor = Color.White, indicatorColor = Color.Transparent))
        NavigationBarItem(icon = { Icon(Icons.Default.Star, contentDescription = "Desafíos") }, label = { Text("Desafíos") }, selected = false, onClick = { }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
        NavigationBarItem(icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") }, label = { Text("Perfil") }, selected = false, onClick = { }, colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray))
    }
}

@Composable
fun PantallaLenguajeSimple(nombreLenguaje: String, alVolver: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(ColorFondoApp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Ruta de $nombreLenguaje (Placeholder)", color = Color.White, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = alVolver, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Text("Volver atrás", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaCodeBite() {
    CodeBiteApp()
}