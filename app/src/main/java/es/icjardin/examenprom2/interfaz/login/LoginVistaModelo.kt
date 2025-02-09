package es.icjardin.examenprom2.interfaz.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.icjardin.examenprom2.modelos.Alumno
import es.icjardin.examenprom2.repositorio.AlumnoRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginVistaModelo(application: Application) : AndroidViewModel(application) {

    private val alumnoRepositorio = AlumnoRepositorio(application)

    /**
     * Método para verificar credenciales de usuario en la base de datos.
     * Llama a un callback con `idAlumno`, `esValido` (true/false) y `esProfesor` (true si es profesor).
     */
    fun verificarCredenciales(usuario: String, contrasena: String, callback: (Int, Boolean, Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listaAlumnos = alumnoRepositorio.obtenerAlumnos()
                val alumno = listaAlumnos.find { it.usuario == usuario && it.contrasena == contrasena }

                withContext(Dispatchers.Main) {
                    when {
                        alumno != null -> {
                            callback(alumno.id, true, false) // Es un alumno válido
                        }
                        usuario == "admin" && contrasena == "admin123" -> {
                            callback(-1, true, true) // Es el profesor
                        }
                        else -> {
                            callback(-1, false, false) // Credenciales incorrectas
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginVistaModelo", "Error en la autenticación", e)
                withContext(Dispatchers.Main) {
                    callback(-1, false, false)
                }
            }
        }
    }
}
