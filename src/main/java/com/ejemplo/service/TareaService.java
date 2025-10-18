package com.ejemplo.service;

import com.ejemplo.model.Tarea;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TareaService {

    private static TareaService instance;
    private List<Tarea> tareas;
    private AtomicLong idGenerator;

    private TareaService() {
        tareas = new ArrayList<>();
        idGenerator = new AtomicLong(1);
        inicializarDatosEjemplo();
    }

    public static TareaService getInstance() {
        if (instance == null) {
            instance = new TareaService();
        }
        return instance;
    }

    private void inicializarDatosEjemplo() {
        crearTarea("Aprender ZK Framework", "Estudiar la documentación y crear ejemplos", "ALTA");
        crearTarea("Configurar proyecto Maven", "Configurar dependencias y estructura", "MEDIA");
        crearTarea("Implementar CRUD", "Crear operaciones básicas de base de datos", "ALTA");
        crearTarea("Diseñar interfaz", "Crear mockups y prototipos", "BAJA");
    }

    public List<Tarea> obtenerTodasLasTareas() {
        return new ArrayList<>(tareas);
    }

    public List<Tarea> obtenerTareasPendientes() {
        return tareas.stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
    }

    public List<Tarea> obtenerTareasCompletadas() {
        return tareas.stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }

    public Tarea obtenerTareaPorId(Long id) {
        return tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Tarea crearTarea(String titulo, String descripcion, String prioridad) {
        Tarea tarea = new Tarea();
        tarea.setId(idGenerator.getAndIncrement());
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setPrioridad(prioridad);
        tareas.add(tarea);
        return tarea;
    }

    public void actualizarTarea(Tarea tarea) {
        Tarea tareaExistente = obtenerTareaPorId(tarea.getId());
        if (tareaExistente != null) {
            tareaExistente.setTitulo(tarea.getTitulo());
            tareaExistente.setDescripcion(tarea.getDescripcion());
            tareaExistente.setPrioridad(tarea.getPrioridad());
            tareaExistente.setFechaVencimiento(tarea.getFechaVencimiento());
            tareaExistente.setCompletada(tarea.isCompletada());
        }
    }

    public void eliminarTarea(Long id) {
        tareas.removeIf(t -> t.getId().equals(id));
    }

    public void marcarComoCompletada(Long id) {
        Tarea tarea = obtenerTareaPorId(id);
        if (tarea != null) {
            tarea.setCompletada(true);
        }
    }

    public void marcarComoPendiente(Long id) {
        Tarea tarea = obtenerTareaPorId(id);
        if (tarea != null) {
            tarea.setCompletada(false);
        }
    }

    public int contarTareasPendientes() {
        return (int) tareas.stream().filter(t -> !t.isCompletada()).count();
    }

    public int contarTareasCompletadas() {
        return (int) tareas.stream().filter(Tarea::isCompletada).count();
    }
}