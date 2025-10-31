# 📅 Sistema de Agendamiento de Eventos - ZK Framework

Sistema web de gestión y agendamiento de eventos desarrollado con ZK Framework, implementando el patrón MVVM y una interfaz moderna e intuitiva.

## 📋 Descripción del Proyecto

Este proyecto es una aplicación web completa para la gestión de eventos y calendario, desarrollada utilizando ZK Framework con Java. Permite a los usuarios crear, visualizar, editar y eliminar eventos de manera intuitiva con una interfaz similar a Google Calendar.

### Problema que resuelve

- **Gestión desorganizada de eventos**: Sin una vista clara del calendario
- **Dificultad para programar reuniones**: No hay visualización de disponibilidad
- **Falta de notificaciones**: Los usuarios olvidan sus eventos
- **Sin categorización**: Todos los eventos se ven igual
- **Búsqueda ineficiente**: Difícil encontrar eventos pasados

## 🚀 Características Principales

- ✅ **Vista de Calendario**: Mensual, semanal y diaria
- ✅ **CRUD de Eventos**: Crear, leer, actualizar y eliminar eventos
- ✅ **Categorías de Eventos**: Personal, trabajo, reunión, cumpleaños, etc.
- ✅ **Códigos de Color**: Diferentes colores por tipo de evento
- ✅ **Búsqueda y Filtros**: Por fecha, categoría o palabras clave
- ✅ **Eventos Recurrentes**: Diarios, semanales, mensuales
- ✅ **Recordatorios**: Notificaciones antes del evento
- ✅ **Vista de Lista**: Todos los eventos en formato tabla
- ✅ **Exportar Calendario**: A formato PDF o iCal
- ✅ **Responsive**: Adaptable a dispositivos móviles

## 🛠️ Stack Tecnológico

- **Frontend**: ZK Framework 9.6.0
- **Lenguaje**: Java 11+
- **Patrón de Arquitectura**: MVVM (Model-View-ViewModel)
- **Build Tool**: Maven
- **Base de Datos**: H2 (desarrollo) / PostgreSQL (producción)
- **Servidor**: Apache Tomcat embebido
- **IDE Recomendado**: IntelliJ IDEA / Eclipse

## 📁 Estructura del Proyecto

```
proyectoZK/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/brayanmantilla/eventos/
│   │   │       ├── model/
│   │   │       │   ├── Evento.java
│   │   │       │   ├── Categoria.java
│   │   │       │   └── Usuario.java
│   │   │       ├── service/
│   │   │       │   ├── EventoService.java
│   │   │       │   └── CategoriaService.java
│   │   │       ├── viewmodel/
│   │   │       │   ├── CalendarioViewModel.java
│   │   │       │   ├── EventoViewModel.java
│   │   │       │   └── ListaEventosViewModel.java
│   │   │       └── utils/
│   │   │           └── DateUtils.java
│   │   ├── webapp/
│   │   │   ├── WEB-INF/
│   │   │   │   ├── web.xml
│   │   │   │   └── zk.xml
│   │   │   ├── calendario.zul
│   │   │   ├── eventos.zul
│   │   │   ├── index.zul
│   │   │   └── css/
│   │   │       └── calendario.css
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## 📦 Componentes ZK Utilizados

```xml
<!-- Componentes principales del proyecto -->
- <calendar>     <!-- Vista de calendario -->
- <window>       <!-- Ventanas modales -->
- <listbox>      <!-- Listas de eventos -->
- <datebox>      <!-- Selectores de fecha -->
- <timebox>      <!-- Selectores de hora -->
- <combobox>     <!-- Selección de categorías -->
- <textbox>      <!-- Campos de texto -->
- <button>       <!-- Acciones -->
- <borderlayout> <!-- Layout principal -->
- <popup>        <!-- Tooltips de eventos -->
```

## 🔧 Instalación y Configuración

### Requisitos Previos

- JDK 11 o superior
- Maven 3.6+
- Git

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/Brayanmantilla/proyectoZK.git
cd proyectoZK
```

2. **Compilar el proyecto**
```bash
mvn clean install
```

3. **Ejecutar la aplicación**
```bash
mvn jetty:run
```

4. **Acceder a la aplicación**
```
http://localhost:8080
```

## 📖 Guía de Uso

### Vista Principal - Calendario

1. **Navegación**: Use las flechas para cambiar de mes/semana
2. **Crear evento**: Doble click en el día deseado
3. **Ver detalles**: Click sobre un evento existente
4. **Cambiar vista**: Botones para vista mensual/semanal/diaria

### Crear Nuevo Evento

1. Doble click en la fecha deseada
2. Completar el formulario:
   - Título del evento
   - Fecha y hora de inicio/fin
   - Categoría
   - Descripción
   - Recordatorio
3. Click en "Guardar"

### Gestión de Eventos

- **Editar**: Click derecho → "Editar"
- **Eliminar**: Click derecho → "Eliminar"
- **Duplicar**: Click derecho → "Duplicar"

## 💻 Ejemplos de Código

### Modelo de Evento
```java
public class Evento {
    private Long id;
    private String titulo;
    private Date fechaInicio;
    private Date fechaFin;
    private Categoria categoria;
    private String descripcion;
    private boolean todoElDia;
    // getters y setters...
}
```

### ViewModel Principal
```java
@VariableResolver(org.zkoss.bind.impl.BindingVariableResolver.class)
public class CalendarioViewModel {
    
    @Wire
    private Calendar calendario;
    
    private EventoService eventoService;
    private List<Evento> eventos;
    
    @Init
    public void init() {
        eventoService = new EventoService();
        cargarEventos();
    }
    
    @Command
    @NotifyChange({"eventos"})
    public void crearEvento() {
        // Lógica para crear evento
    }
}
```

### Vista ZUL
```xml
<zk>
    <window title="Calendario de Eventos" 
            viewModel="@id('vm') @init('com.brayanmantilla.eventos.viewmodel.CalendarioViewModel')">
        
        <borderlayout hflex="1" vflex="1">
            <center>
                <calendar id="calendario" 
                          model="@bind(vm.calendarModel)"
                          onEventCreate="@command('crearEvento')"
                          onEventEdit="@command('editarEvento')"/>
            </center>
        </borderlayout>
        
    </window>
</zk>
```

## 🎨 Capturas de Pantalla

### Vista de Calendario Mensual
```
┌─────────────────────────────────────────────┐
│  < Octubre 2024 >                    [M][S] │
├─────┬─────┬─────┬─────┬─────┬─────┬─────────┤
│ Dom │ Lun │ Mar │ Mié │ Jue │ Vie │ Sáb │
├─────┼─────┼─────┼─────┼─────┼─────┼─────────┤
│     │  1  │  2  │  3  │  4  │  5  │  6  │
│     │[...]│[▪▪▪]│     │[...]│     │     │
├─────┼─────┼─────┼─────┼─────┼─────┼─────────┤
```

## 🔌 API de Eventos

### EventoService
```java
public interface EventoService {
    List<Evento> obtenerEventosPorMes(int mes, int año);
    Evento crearEvento(Evento evento);
    Evento actualizarEvento(Evento evento);
    void eliminarEvento(Long id);
    List<Evento> buscarEventos(String criterio);
}
```

## 🧪 Testing

Ejecutar las pruebas unitarias:
```bash
mvn test
```

## 🚀 Despliegue

### Desarrollo
```bash
mvn jetty:run
```

### Producción
```bash
mvn clean package
# El archivo WAR estará en target/proyectoZK.war
```

## 📈 Próximas Mejoras

- [ ] Integración con Google Calendar
- [ ] Notificaciones por email
- [ ] Vista de agenda compartida
- [ ] Sincronización con dispositivos móviles
- [ ] Modo oscuro
- [ ] Multi-idioma

**Brayan Mantilla**
- GitHub: [@Brayanmantilla](https://github.com/Brayanmantilla)
- LinkedIn: [Brayan Mantilla](https://www.linkedin.com/in/brayan-mantilla/)
