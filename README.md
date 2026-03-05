# codebite-app
Plataforma de micro-learning para devs: píldoras de conocimiento y desafíos de 2 minutos para aprovechar cada tiempo muerto.


CodeBite es una plataforma educativa móvil diseñada para desarrolladores y estudiantes de tecnología que desean mantener sus conocimientos frescos sin dedicar horas a un curso extenso. A través de píldoras de conocimiento y desafíos rápidos de 2 minutos, la app transforma el tiempo muerto (como esperar el autobús o hacer una fila) en una sesión productiva 
de estudio.


Stack Tecnológico
CodeBite está construido con tecnologías modernas de desarrollo nativo para garantizar un rendimiento óptimo y una experiencia de usuario fluida en dispositivos Android.
Lenguaje de Programación: Kotlin, el lenguaje moderno y preferido por Google para el desarrollo de Android.
Entorno de Desarrollo (IDE): Android Studio, el IDE oficial para la plataforma Android.
Interfaz de Usuario: Jetpack Compose (recomendado) para una UI declarativa y moderna que facilita la creación de las "píldoras" de conocimiento.
Arquitectura: MVVM (Model-View-ViewModel) para asegurar un código mantenible, escalable y fácil de testear.
Gestión de Dependencias: Gradle (Kotlin DSL).

Estructura del Proyecto
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

🔍 Detalle de Componentes
* **Data Layer**: Maneja la persistencia local con Room para asegurar el aprendizaje offline.
* **UI Layer**: Implementada 100% en Jetpack Compose, siguiendo los principios de Material Design 3.
* **ViewModel**: Gestiona el estado de la UI y la lógica de los desafíos de 2 minutos.

🚀 Estado del Desarrollo
Puedes seguir el progreso en tiempo real de las funcionalidades de **CodeBite** en nuestro [Tablero de Proyecto](github.com/users/EDWIN0vg/projects/1).

🎨 Diseño UI/UX
La identidad visual de CodeBite está diseñada para la eficiencia.
* Puedes consultar la [Guía de Estilo en nuestra Wiki](https://github.com/EDWIN0vg/codebite-app/wiki/Guía-de-Estilo).
* Los assets y wireframes están disponibles en la carpeta [/design](./design).

⚖️ Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
