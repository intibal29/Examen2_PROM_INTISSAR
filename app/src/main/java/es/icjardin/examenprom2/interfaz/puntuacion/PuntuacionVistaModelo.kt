package es.icjardin.examenprom2.interfaz.puntuacion


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.icjardin.examenprom2.modelos.Puntuacion
import es.icjardin.examenprom2.repositorio.AlumnoRepositorio
import es.icjardin.examenprom2.repositorio.JuegoRepositorio
import es.icjardin.examenprom2.repositorio.PuntuacionRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class PuntuacionVistaModelo(application: Application) : AndroidViewModel(application) {

    private val puntuacionRepositorio = PuntuacionRepositorio(application)

    /**
     * Método para obtener todas las puntuaciones con los nombres de los alumnos y juegos.
     */
    fun obtenerPuntuaciones(callback: (List<Puntuacion>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val listaPuntuaciones = puntuacionRepositorio.obtenerPuntuaciones()

            viewModelScope.launch(Dispatchers.Main) {
                callback(listaPuntuaciones)
            }
        }
    }
}
