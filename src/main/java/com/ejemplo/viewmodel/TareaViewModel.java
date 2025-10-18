package com.ejemplo.viewmodel;

import com.ejemplo.model.Tarea;
import com.ejemplo.service.TareaService;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import java.util.List;

public class TareaViewModel {

    private TareaService tareaService;
    private ListModelList<Tarea> tareas;
    private Tarea tareaSeleccionada;
    private Tarea nuevaTarea;
    private String filtroEstado = "TODAS"; // TODAS, PENDIENTES, COMPLETADAS

    @Init
    public void init() {
        tareaService = TareaService.getInstance();
        cargarTareas();
        nuevaTarea = new Tarea();
    }

    private void cargarTareas() {
        List<Tarea> listaTareas;

        switch (filtroEstado) {
            case "PENDIENTES":
                listaTareas = tareaService.obtenerTareasPendientes();
                break;
            case "COMPLETADAS":
                listaTareas = tareaService.obtenerTareasCompletadas();
                break;
            default:
                listaTareas = tareaService.obtenerTodasLasTareas();
        }

        tareas = new ListModelList<>(listaTareas);
    }

    // Getters
    public ListModelList<Tarea> getTareas() {
        return tareas;
    }

    public Tarea getTareaSeleccionada() {
        return tareaSeleccionada;
    }

    public void setTareaSeleccionada(Tarea tareaSeleccionada) {
        this.tareaSeleccionada = tareaSeleccionada;
    }

    public Tarea getNuevaTarea() {
        return nuevaTarea;
    }

    public String getFiltroEstado() {
        return filtroEstado;
    }

    public void setFiltroEstado(String filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

    public int getTotalTareas() {
        return tareaService.obtenerTodasLasTareas().size();
    }

    public int getTareasPendientes() {
        return tareaService.contarTareasPendientes();
    }

    public int getTareasCompletadas() {
        return tareaService.contarTareasCompletadas();
    }

    // Comandos
    @Command
    @NotifyChange({"tareas", "totalTareas", "tareasPendientes", "tareasCompletadas"})
    public void agregarTarea() {
        if (nuevaTarea.getTitulo() == null || nuevaTarea.getTitulo().trim().isEmpty()) {
            Messagebox.show("El título es obligatorio", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        tareaService.crearTarea(
                nuevaTarea.getTitulo(),
                nuevaTarea.getDescripcion(),
                nuevaTarea.getPrioridad()
        );

        nuevaTarea = new Tarea();
        cargarTareas();
        Messagebox.show("Tarea creada exitosamente", "Éxito", Messagebox.OK, Messagebox.INFORMATION);
    }

    @Command
    @NotifyChange({"tareas", "tareaSeleccionada", "totalTareas", "tareasPendientes", "tareasCompletadas"})
    public void eliminarTarea(@BindingParam("tarea") Tarea tarea) {
        Messagebox.show("¿Estás seguro de eliminar esta tarea?", "Confirmar",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if (Messagebox.ON_YES.equals(event.getName())) {
                        tareaService.eliminarTarea(tarea.getId());
                        cargarTareas();
                        tareaSeleccionada = null;
                        Messagebox.show("Tarea eliminada", "Éxito", Messagebox.OK, Messagebox.INFORMATION);
                    }
                }
        );
    }

    @Command
    @NotifyChange({"tareas", "tareasPendientes", "tareasCompletadas"})
    public void cambiarEstado(@BindingParam("tarea") Tarea tarea) {
        if (tarea.isCompletada()) {
            tareaService.marcarComoCompletada(tarea.getId());  // ← CAMBIO AQUÍ
        } else {
            tareaService.marcarComoPendiente(tarea.getId());   // ← Y AQUÍ
        }
        cargarTareas();
    }

    @Command
    @NotifyChange({"tareas", "totalTareas", "tareasPendientes", "tareasCompletadas"})
    public void actualizarTarea() {
        if (tareaSeleccionada == null) {
            Messagebox.show("Selecciona una tarea primero", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        tareaService.actualizarTarea(tareaSeleccionada);
        cargarTareas();
        Messagebox.show("Tarea actualizada exitosamente", "Éxito", Messagebox.OK, Messagebox.INFORMATION);
    }

    @Command
    @NotifyChange("tareas")
    public void aplicarFiltro() {
        cargarTareas();
    }

    @Command
    @NotifyChange("nuevaTarea")
    public void limpiarFormulario() {
        nuevaTarea = new Tarea();
    }
}
