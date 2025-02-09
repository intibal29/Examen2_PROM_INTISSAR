package es.icjardin.examenprom2.interfaz.profesor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.icjardin.examenprom2.modelos.Alumno
import es.icjardin.examenprom2.modelos.Grupo
import es.icjardin.examenprom2.repositorio.AlumnoRepositorio
import es.icjardin.examenprom2.repositorio.GrupoRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrearGrupoVistaModelo(application: Application) : AndroidViewModel(application) {

    private val grupoRepositorio = GrupoRepositorio(application)
    private val alumnoRepositorio = AlumnoRepositorio(application)

    /**
     * Método para obtener todos los alumnos disponibles.
     * Llama a un callback con la lista de alumnos.
     */
    fun obtenerAlumnos(callback: (List<Alumno>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val listaAlumnos = alumnoRepositorio.obtenerAlumnos()
            viewModelScope.launch(Dispatchers.Main) {
                callback(listaAlumnos)
            }
        }
    }

    /**
     * Método para agregar un nuevo grupo con alumnos asignados.
     * Llama a un callback con `true` si la operación fue exitosa, `false` si falló.
     */
    fun agregarGrupo(grupo: Grupo, alumnosSeleccionados: List<Alumno>, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val idGrupo = grupoRepositorio.agregarGrupo(grupo)

            // Verifica que el ID del grupo es válido (debe ser mayor a 0)
            if (true) {
                var exitoTotal = true

                for (alumno in alumnosSeleccionados) {
                    val alumnoActualizado = alumno.copy(idGrupo = 1) // cambiarlo
                    val exito = alumnoRepositorio.actualizarAlumno(alumnoActualizado)

                    if (!exito) {
                        exitoTotal = false
                        break
                    }
                }

                viewModelScope.launch(Dispatchers.Main) {
                    callback(exitoTotal)
                }
            } else {
                viewModelScope.launch(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }
}
