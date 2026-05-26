# codebite-app

### **Propósito del Proyecto**

Plataforma de micro-learning para devs: píldoras de conocimiento y desafíos de 2 minutos para aprovechar cada tiempo muerto.

CodeBite nace para transformar la forma en que los desarrolladores aprovechan su tiempo libre. En un mundo donde el aprendizaje continuo es vital pero el tiempo es escaso, muchos estudiantes pierden momentos valiosos (transporte, esperas, descansos) por no tener herramientas adaptadas a sesiones cortas.

Esta aplicación resuelve ese problema ofreciendo:

* Micro-aprendizaje: Píldoras de conocimiento técnico que se consumen en segundos.
* Aprendizaje Activo: Desafíos prácticos de 2 minutos que refuerzan la retención de conceptos de C++, Java, Python.
* Hábito Gamificado: Un sistema de rachas que motiva al usuario a aprender algo nuevo cada día, convirtiendo el tiempo muerto en crecimiento profesional.
  
### Capturas de Pantalla 
### Inicio
<img width="238" height="508" alt="image" src="https://github.com/user-attachments/assets/08510b9a-f959-4f25-af56-9823555a16a1" />

### Lección
<img width="238" height="510" alt="image" src="https://github.com/user-attachments/assets/9ea84133-90cd-44bc-ac8c-bccc1c0707c1" />

### Quiz
<img width="239" height="504" alt="image" src="https://github.com/user-attachments/assets/d6d53793-0c36-4d77-941b-3eb5cc52a984" />

### Resumen
<img width="237" height="507" alt="image" src="https://github.com/user-attachments/assets/05d9a9dd-739c-49ea-a812-122da0330ad7" />

## Funcionalidades Principales y Estado de Implementación

| Módulo / Funcionalidad | Descripción Técnica | Estado |
| :--- | :--- | :---: |
| **Autenticación Nativa** | Pantalla de Login con validación rigurosa de credenciales en cliente, control de excepciones de red y persistencia del estado de sesión a través del ciclo de vida de la app. | `✅ IMPLEMENTADO` |
| **Especialización por Nivel** | Selector dinámico interactivo de perfiles (**Junior → Mid → Senior**). Adapta de manera automática el árbol de complejidad del contenido y los parámetros del temporizador según el perfil técnico del usuario. | `✅ IMPLEMENTADO` |
| **Visor de Código IDE** | Componente visual personalizado con arquitectura de renderizado rápido y tema *Darcula*. Incorpora coloreado sintáctico semántico real para fragmentos de código en **Java, Python y C++**. | `✅ IMPLEMENTADO` |
| **XP Honesto + Feedback** | Sistema de gamificación que otorga únicamente `+50 XP` tras validación exitosa del reto. Al finalizar el quiz, despliega un cuadro de diálogo con una explicación técnica profunda del porqué del acierto o fallo. | `✅ IMPLEMENTADO` |
| **Sistema de Rachas (Streaks)** | Algoritmo de verificación diaria conectado a la capa de persistencia local para medir la consistencia del aprendizaje del usuario. | `⏳ EN DESARROLLO` |

---

## Stack Tecnológico

CodeBite está construido bajo los estándares más exigentes de la ingeniería de software móvil moderna, priorizando el rendimiento nativo, el diseño reactivo y la estabilidad del sistema:

* **Lenguaje de Programación:** [Kotlin](https://kotlinlang.org/), aprovechando sus características avanzadas de *Null-safety*, expresiones funcionales y corrutinas para tareas asíncronas.
* **Interfaz de Usuario:** [Jetpack Compose](https://developer.android.com/jetpack/compose) implementando componentes nativos y directrices visuales de **Material Design 3**.
* **Arquitectura de Software:** **MVVM (Model-View-ViewModel)** acoplado con arquitectura limpia (*Clean Architecture* de forma interna) para desacoplar completamente la lógica de negocio de la renderización de la UI.
* **Garantía de Calidad (Testing):** [JUnit 4](https://junit.org/junit4/) para la ejecución de pruebas unitarias automatizadas que validan la lógica de asignación de XP, el control de temporizadores y los estados de la sesión.
* **Integración Continua (CI/CD):** [GitHub Actions](https://github.com/features/actions) configurando un pipeline automatizado (`android.yml`) que compila la aplicación y ejecuta la suite de tests en cada *Commit* o *Pull Request* hacia la rama principal (**Always Green Assurance**).
* **Gestión de Dependencias:** Gradle (Kotlin DSL).

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
### **Detalle de Componentes**
* **Data Layer**: Maneja la persistencia local con Room para asegurar el aprendizaje offline.
* **UI Layer**: Implementada 100% en Jetpack Compose, siguiendo los principios de Material Design 3.
* **ViewModel**: Gestiona el estado de la UI y la lógica de los desafíos de 2 minutos.

### **Estado del Desarrollo**
Puedes seguir el progreso en tiempo real de las funcionalidades de **CodeBite** en nuestro github

### Diseño UI/UX e Identidad Visual
La interfaz de CodeBite está diseñada bajo un enfoque de eficiencia cognitiva en movilidad:

Tema Oscuro por Defecto: Reduce la fatiga visual en entornos de transporte público y optimiza el consumo de batería en pantallas OLED.

Paleta Técnica: Uso del color negro absoluto (#000000) de fondo para contraste máximo, combinado con un acento naranja quemado (#E67E22) que guía el foco de atención del desarrollador hacia los elementos interactivos críticos y el visor de código.

### **Autor(es):** Edwin Hernan Vergara Gonzalez

## Referencias
- [poster codebite](docs/PosterCodeBite.png)
- [Ideas iniciales de proyecto](docs/ideas.md)
- [Funcionalidades de la aplicación](docs/funcionalidades.md)
- [Diseño de la interfaz de usuario](docs/ui.md)

