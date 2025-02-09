package es.icjardin.examenprom2.interfaz.alumno


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.icjardin.examenprom2.repositorio.AlumnoRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuAlumnoVistaModelo(application: Application) : AndroidViewModel(application) {

    private val alumnoRepositorio = AlumnoRepositorio(application)

    /**
     * Método para obtener el nombre del alumno desde la base de datos.
     * Llama a un callback con el nombre del alumno.
     */
    fun obtenerNombreAlumno(callback: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val listaAlumnos = alumnoRepositorio.obtenerAlumnos()

            // Simulación: Se toma el primer alumno como ejemplo
            val alumno = listaAlumnos.firstOrNull()
            val nombre = alumno?.nombre ?: "Alumno"

            // Se ejecuta el callback en el hilo principal
            viewModelScope.launch(Dispatchers.Main) {
                callback(nombre)
            }
        }
    }
}
