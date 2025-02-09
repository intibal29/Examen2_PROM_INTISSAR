package es.icjardin.examenprom2.interfaz.puntuacion

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.modelos.Puntuacion
import es.icjardin.examenprom2.repositorio.PuntuacionRepositorio
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PuntuacionActividad : AppCompatActivity() {

    private lateinit var rvPuntuaciones: RecyclerView
    private lateinit var btnVolver: Button
    private lateinit var btnAyuda: ImageButton
    private lateinit var btnIdioma: ImageButton
    private lateinit var tvRanking: TextView
    private lateinit var adaptadorPuntuaciones: AdaptadorPuntuaciones

    private val puntuacionRepositorio by lazy { PuntuacionRepositorio(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_puntuaciones)

        // Referencias a los elementos del XML
        rvPuntuaciones = findViewById(R.id.rvPuntuaciones)
        btnVolver = findViewById(R.id.btnVolver)
        btnAyuda = findViewById(R.id.btnAyuda)
        btnIdioma = findViewById(R.id.btnIdioma)
        tvRanking = findViewById(R.id.tvRanking)

        // Configurar RecyclerView
        rvPuntuaciones.layoutManager = LinearLayoutManager(this)
        adaptadorPuntuaciones = AdaptadorPuntuaciones(emptyList())
        rvPuntuaciones.adapter = adaptadorPuntuaciones

        // Cargar ranking de puntuaciones
        cargarRanking()

        // Acción del botón "Volver"
        btnVolver.setOnClickListener {
            finish()
        }

        // Acción del botón "Ayuda"
        btnAyuda.setOnClickListener {
            mostrarMensajeAyuda()
        }

        // Acción del botón "Cambio de Idioma"
        btnIdioma.setOnClickListener {
            cambiarIdioma()
        }
    }

    /**
     * Obtiene las puntuaciones acumuladas ordenadas y muestra el ranking.
     */
    private fun cargarRanking() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listaPuntuaciones = puntuacionRepositorio.obtenerPuntuacionesAcumuladas()

            // Ordenar por puntaje en orden descendente
            val ranking = listaPuntuaciones.sortedByDescending { it.puntaje }

            runOnUiThread {
                adaptadorPuntuaciones.actualizarLista(ranking)
                mostrarTop3(ranking)
            }
        }
    }

    /**
     * Muestra el TOP 3 del ranking en un TextView.
     */
    private fun mostrarTop3(lista: List<Puntuacion>) {
        if (lista.isNotEmpty()) {
            val top3 = lista.take(3)
            val textoRanking = StringBuilder("🏆 Ranking TOP 3 🏆\n\n")
            for ((index, puntuacion) in top3.withIndex()) {
                textoRanking.append("${index + 1}. ${puntuacion.nombreAlumno} - ${puntuacion.puntaje} pts\n")
            }
            tvRanking.text = textoRanking.toString()
        } else {
            tvRanking.text = "Aún no hay puntuaciones registradas."
        }
    }

    /**
     * Muestra un mensaje de ayuda
     */
    private fun mostrarMensajeAyuda() {
        tvRanking.text = "Aquí puedes ver el ranking de los alumnos con más puntos acumulados."
    }

    /**
     * Lógica para cambiar el idioma de la aplicación
     */
    private fun cambiarIdioma() {
        tvRanking.text = "Cambio de idioma no implementado aún."
    }
}
