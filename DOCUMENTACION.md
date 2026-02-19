# Documentación del Proyecto: Viajes App

## 1. Introducción
**Viajes** es una aplicación móvil nativa desarrollada para Android que permite a los usuarios buscar hoteles en diferentes ciudades, visualizar su ubicación exacta en un mapa interactivo y gestionar reservas.

---

## 2. Tecnologías y Librerías Utilizadas
El proyecto sigue las mejores prácticas de desarrollo moderno en Android utilizando **Java** como lenguaje principal.

*   **Arquitectura:** Basada en Componentes de Arquitectura de Android (ViewBinding, Adapters, Room).
*   **Networking (APIs):** [Retrofit 2](https://square.github.io/retrofit/) + [Gson](https://github.com/google/gson) para el consumo de servicios web.
*   **Base de Datos Local:** [Room Persistence Library](https://developer.android.com/training/data-storage/room) para el almacenamiento de reservas.
*   **Mapas:** [OSMDroid](https://github.com/osmdroid/osmdroid) (OpenStreetMap) para visualización geográfica sin dependencia de claves de pago.
*   **Carga de Imágenes:** [Glide](https://github.com/bumptech/glide) para la gestión eficiente y caché de imágenes desde URLs.
*   **Interfaz de Usuario:** Material Design, ChipNavigationBar y ViewPager2.

---

## 3. Funcionamiento de los Módulos

### A. Búsqueda de Hoteles (Integración de API)
La aplicación utiliza una arquitectura de red basada en **Retrofit**. 
-   **Consumo de Datos:** Se conecta a un servicio API de hoteles externo (proporcionado en `ApiService.java`) para obtener datos en tiempo real de estancias, precios y disponibilidad según la ciudad introducida por el usuario.
-   **Fallback de Datos:** El sistema incluye una lógica de "Hoteles Personalizados" que garantiza que ciudades clave como **Donostia, Barcelona, Bilbao y Madrid** siempre muestren resultados relevantes y ubicaciones precisas, incluso si la API externa presenta latencia.

### B. Explorador y Mapa Interactivo
El módulo de mapas es una de las piezas centrales:
-   **Sin Claves de Pago:** Se utiliza la tecnología de OpenStreetMap a través de la librería **OSMDroid**, eliminando la necesidad de Google Maps API Keys.
-   **Geolocalización:** Cada hotel cuenta con coordenadas reales (Latitud/Longitud). Al entrar en el mapa, se generan marcadores dinámicos por cada hotel encontrado.
-   **Interactividad:** Al pulsar un marcador, se despliega una tarjeta con la información del hotel, permitiendo al usuario navegar directamente a los detalles del mismo.

### C. Sistema de Reservas y Persistencia (Room DB)
Para la gestión de reservas, la app no depende de una conexión constante:
-   **Almacenamiento Local:** Se utiliza **Room**, una capa de abstracción sobre SQLite.
-   **Entidades:** La clase `ReservationEntity` define la estructura de los datos (Nombre del hotel, fechas, precio total e imagen).
-   **Operaciones (DAO):** El sistema permite realizar inserciones de nuevas reservas y eliminaciones (cancelaciones) de forma asíncrona mediante un `ExecutorService`, garantizando que la interfaz no se bloquee.

### D. Gestión de Perfil
El apartado "Nire Erreserbak" (Mis Reservas) permite al usuario:
-   Listar todas sus reservas actuales.
-   Visualizar las fechas de Check-in y Check-out.
-   **Cancelación en tiempo real:** Eliminar reservas directamente desde la lista, actualizando la base de datos local al instante.

---

## 4. Estructura de Datos (Modelos)
1.  **ItemModel:** Modelo principal para hoteles (Nombre, Dirección, Precio, Coordenadas, Wifi, etc.).
2.  **ReservationModel:** Modelo optimizado para la visualización de reservas.
3.  **StayResponse:** Clase de mapeo para las respuestas JSON de la API.

