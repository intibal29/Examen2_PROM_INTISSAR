package es.icjardin.examenprom2.interfaz.juegos

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.modelos.Puntuacion
import es.icjardin.examenprom2.repositorio.PuntuacionRepositorio
import es.icjardin.examenprom2.util.SesionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class CompletarCancionActividad : AppCompatActivity() {

    private lateinit var btnReproducir: Button
    private lateinit var btnVerificar: Button
    private lateinit var btnVolver: Button
    private lateinit var btnAyuda: ImageButton
    private lateinit var btnIdioma: ImageButton

    private lateinit var etRespuestas: List<EditText>
    private lateinit var tvLetraCancion: TextView
    private var mediaPlayer: MediaPlayer? = null

    private val puntuacionRepositorio by lazy { PuntuacionRepositorio(this) }
    private lateinit var sesionManager: SesionManager

    // ID del juego (según la base de datos)
    private val ID_JUEGO = 2  // ID del juego "Rellenar Huecos"

    // Respuestas correctas
    private val respuestasCorrectas = listOf("sol", "parar", "canción", "plata")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_juego_completar)

        // Referencias a los elementos del XML
        btnReproducir = findViewById(R.id.btnReproducirCancion)
        btnVerificar = findViewById(R.id.btnVerificarRespuestas)
        btnVolver = findViewById(R.id.btnVolver)
        btnAyuda = findViewById(R.id.btnAyuda)
        btnIdioma = findViewById(R.id.btnIdioma)

        etRespuestas = listOf(
            findViewById(R.id.etRespuesta1),
            findViewById(R.id.etRespuesta2),
            findViewById(R.id.etRespuesta3),
            findViewById(R.id.etRespuesta4)
        )

        tvLetraCancion = findViewById(R.id.tvLetraCancion)

        sesionManager = SesionManager(this)

        // Configurar reproductor de música
        mediaPlayer = MediaPlayer.create(this, R.raw.cancion)

        btnReproducir.setOnClickListener {
            reproducirCancion()
        }

        btnVerificar.setOnClickListener {
            verificarRespuestas()
        }

        btnVolver.setOnClickListener {
            mediaPlayer?.stop()
            finish()
        }

        btnAyuda.setOnClickListener {
            mostrarAyuda()
        }

        btnIdioma.setOnClickListener {
            cambiarIdioma()
        }
    }

    /**
     * Reproduce la canción si no está ya sonando.
     */
    private fun reproducirCancion() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
            }
        }
    }

    /**
     * Verifica si las respuestas ingresadas son correctas y guarda la puntuación
     */
    private fun verificarRespuestas() {
        val idAlumno = sesionManager.obtenerIdAlumno()
        val nombreAlumno = sesionManager.obtenerNombreAlumno()

        if (idAlumno == -1 || nombreAlumno.isEmpty()) {
            Toast.makeText(this, "Error: No se pudo obtener la sesión del usuario.", Toast.LENGTH_SHORT).show()
            return
        }

        var correctas = 0

        for (i in respuestasCorrectas.indices) {
            if (etRespuestas[i].text.toString().trim().equals(respuestasCorrectas[i], ignoreCase = true)) {
                correctas++
            }
        }

        val puntaje = correctas * 10  // Cada respuesta correcta vale 10 puntos

        if (correctas == respuestasCorrectas.size) {
            Toast.makeText(this, "¡Correcto! Todas las palabras son correctas. +$puntaje puntos", Toast.LENGTH_SHORT).show()
            guardarPuntuacion(idAlumno, nombreAlumno, puntaje)
        } else {
            Toast.makeText(this, "Algunas palabras son incorrectas. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Guarda la puntuación en la base de datos
     */
    private fun guardarPuntuacion(idAlumno: Int, nombreAlumno: String, puntaje: Int) {
        val nuevaPuntuacion = Puntuacion(
            id = 0,  // ID autogenerado
            idAlumno = idAlumno,
            idJuego = ID_JUEGO,
            puntaje = puntaje,
            fecha = Date(),
            nombreAlumno = nombreAlumno,
            nombreJuego = "Rellenar Huecos"
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val exito = puntuacionRepositorio.agregarPuntuacion(nuevaPuntuacion)
            runOnUiThread {
                if (exito) {
                    Toast.makeText(this@CompletarCancionActividad, "Puntuación guardada correctamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CompletarCancionActividad, "Error al guardar la puntuación.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Muestra un mensaje de ayuda al usuario
     */
    private fun mostrarAyuda() {
        Toast.makeText(this, "Escucha la canción y completa las palabras que faltan.", Toast.LENGTH_LONG).show()
    }

    /**
     * Lógica para cambiar el idioma (puede implementarse más adelante)
     */
    private fun cambiarIdioma() {
        Toast.makeText(this, "Cambio de idioma no implementado aún.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
