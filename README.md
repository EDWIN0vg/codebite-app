# codebite-app

**Propósito del Proyecto**

Plataforma de micro-learning para devs: píldoras de conocimiento y desafíos de 2 minutos para aprovechar cada tiempo muerto.

CodeBite nace para transformar la forma en que los desarrolladores aprovechan su tiempo libre. En un mundo donde el aprendizaje continuo es vital pero el tiempo es escaso, muchos estudiantes pierden momentos valiosos (transporte, esperas, descansos) por no tener herramientas adaptadas a sesiones cortas.

Esta aplicación resuelve ese problema ofreciendo:

* Micro-aprendizaje: Píldoras de conocimiento técnico que se consumen en segundos.
* Aprendizaje Activo: Desafíos prácticos de 2 minutos que refuerzan la retención de conceptos de Kotlin.
* Hábito Gamificado: Un sistema de rachas que motiva al usuario a aprender algo nuevo cada día, convirtiendo el tiempo muerto en crecimiento profesional.
  

**Funcionalidades Principales**

CodeBite es una plataforma de micro-aprendizaje diseñada para dispositivos Android que permite a los desarrolladores dominar Kotlin a través de tres pilares fundamentales:

1. Píldoras de Conocimiento (Knowledge Bites)
* Contenido curado: Lecciones teóricas breves diseñadas para ser leídas en menos de 60 segundos.
* Sintaxis real: Bloques de código con formato legible para facilitar la comprensión de conceptos complejos de Kotlin.

2. Desafíos de 2 Minutos (Daily Challenges)
* Aprendizaje bajo presión: Un motor de retos con temporizador de 120 segundos para poner a prueba la agilidad mental del usuario.
* Feedback instantáneo: Validación inmediata de respuestas para corregir errores en el momento.

3. Sistema de Progresión y Gamificación
* Contador de Rachas (Streaks): Registro visual de los días consecutivos de aprendizaje para fomentar la disciplina.
* Gestión de Perfil: Personalización de los temas de interés según el nivel del desarrollador (Junior, Mid, Senior).


Stack Tecnológico

CodeBite está construido con tecnologías modernas de desarrollo nativo para garantizar un rendimiento óptimo y una experiencia de usuario fluida en dispositivos Android.
Lenguaje de Programación: Kotlin, el lenguaje moderno y preferido por Google para el desarrollo de Android.
Entorno de Desarrollo (IDE): Android Studio, el IDE oficial para la plataforma Android.
Interfaz de Usuario: Jetpack Compose (recomendado) para una UI declarativa y moderna que facilita la creación de las "píldoras" de conocimiento.
Arquitectura: MVVM (Model-View-ViewModel) para asegurar un código mantenible, escalable y fácil de testear.
Gestión de Dependencias: Gradle (Kotlin DSL).

### Estructura del Proyecto

```text
app/src/main/java/com/tuusuario/codebite/
├── data/                 # Repositorios y Fuentes de Datos
│   ├── local/            # Base de datos Room (para Streaks)
│   └── remote/           # API o servicios externos (para Píldoras)
├── ui/                   # Capa de Interfaz (Jetpack Compose)
│   ├── components/       # Componentes reutilizables (BiteCard, Timer)
│   ├── screens/          # Pantallas principales (Home, Challenge, Profile)
│   └── theme/            # Colores, Tipografía y Formas
├── viewmodel/            # Lógica de UI y gestión de estado
├── model/                # Clases de datos (Pill, Challenge, User)
└── utils/                # Funciones de ayuda y extensiones de Kotlin
```
Detalle de Componentes
* **Data Layer**: Maneja la persistencia local con Room para asegurar el aprendizaje offline.
* **UI Layer**: Implementada 100% en Jetpack Compose, siguiendo los principios de Material Design 3.
* **ViewModel**: Gestiona el estado de la UI y la lógica de los desafíos de 2 minutos.

Estado del Desarrollo
Puedes seguir el progreso en tiempo real de las funcionalidades de **CodeBite** en nuestro

Diseño UI/UX
La identidad visual de CodeBite está diseñada para la eficiencia.

Autor(es): Edwin Hernan Vergara Gonzalez

## Referencias

- [Ideas iniciales de proyecto](docs/ideas.md)
- [Funcionalidades de la aplicación](docs/funcionalidades.md)
- [Diseño de la interfaz de usuario](docs/ui.md)

p1.0.1
