package es.icjardin.examenprom2.interfaz.profesor


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.icjardin.examenprom2.modelos.Alumno
import es.icjardin.examenprom2.repositorio.AlumnoRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrearAlumnoVistaModelo(application: Application) : AndroidViewModel(application) {

    private val alumnoRepositorio = AlumnoRepositorio(application)

    /**
     * Método para agregar un nuevo alumno a la base de datos.
     * Llama a un callback con `true` si la inserción fue exitosa, `false` si falló.
     */
    fun agregarAlumno(alumno: Alumno, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            // Verificar si el usuario ya existe
            val listaAlumnos = alumnoRepositorio.obtenerAlumnos()
            val usuarioExistente = listaAlumnos.any { it.usuario == alumno.usuario }

            if (usuarioExistente) {
                // Usuario ya existe, devolvemos `false`
                viewModelScope.launch(Dispatchers.Main) {
                    callback(false)
                }
            } else {
                // Insertar el nuevo alumno
                val exito = alumnoRepositorio.agregarAlumno(alumno)
                viewModelScope.launch(Dispatchers.Main) {
                    callback(exito)
                }
            }
        }
    }
}
