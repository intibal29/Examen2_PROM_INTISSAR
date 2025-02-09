package es.icjardin.examenprom2.interfaz.alumno

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.interfaz.configuracion.ConfiguracionActividad
import es.icjardin.examenprom2.interfaz.juegos.ArrastrarSoltarActividad
import es.icjardin.examenprom2.interfaz.juegos.CompletarCancionActividad
import es.icjardin.examenprom2.interfaz.puntuacion.PuntuacionActividad
import es.icjardin.examenprom2.interfaz.login.LoginActividad
import es.icjardin.examenprom2.util.SesionManager

class MenuAlumnoActividad : AppCompatActivity() {
    private lateinit var sesionManager: SesionManager

    private lateinit var tvNombreAlumno: TextView
    private lateinit var btnDragAndDrop: Button
    private lateinit var btnRellenarHuecos: Button
    private lateinit var btnPuntuacion: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnAyuda: ImageButton
    private lateinit var btnIdioma: ImageButton
    private lateinit var btnConfiguracion: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_menu_alumno)

        sesionManager = SesionManager(this)

        // Inicializar elementos del XML
        tvNombreAlumno = findViewById(R.id.tvNombreAlumno)
        btnDragAndDrop = findViewById(R.id.btnDragAndDrop)
        btnRellenarHuecos = findViewById(R.id.btnRellenarHuecos)
        btnPuntuacion = findViewById(R.id.btnPuntuacion)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnAyuda = findViewById(R.id.btnAyuda)
        btnIdioma = findViewById(R.id.btnIdioma)
        btnConfiguracion = findViewById(R.id.btnConfiguracion) // Se mueve aquí arriba

        // Cargar el nombre del alumno desde SesionManager (corregido)
        tvNombreAlumno.text = "Bienvenido, ${sesionManager.obtenerNombreAlumno()}"

        // Configurar botones
        btnConfiguracion.setOnClickListener {
            startActivity(Intent(this, ConfiguracionActividad::class.java))
        }

        btnDragAndDrop.setOnClickListener {
            startActivity(Intent(this, ArrastrarSoltarActividad::class.java))
        }

        btnRellenarHuecos.setOnClickListener {
            startActivity(Intent(this, CompletarCancionActividad::class.java))
        }

        btnPuntuacion.setOnClickListener {
            startActivity(Intent(this, PuntuacionActividad::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            sesionManager.cerrarSesion()
            startActivity(Intent(this, LoginActividad::class.java))
            finish()
        }

        btnAyuda.setOnClickListener {
            mostrarMensajeAyuda()
        }

        btnIdioma.setOnClickListener {
            cambiarIdioma()
        }
    }

    /**
     * Muestra un mensaje de ayuda
     */
    private fun mostrarMensajeAyuda() {
        Toast.makeText(this, "Escoge un juego para empezar a jugar.", Toast.LENGTH_LONG).show()
    }

    /**
     * Lógica para cambiar el idioma de la aplicación
     */
    private fun cambiarIdioma() {
        Toast.makeText(this, "Función de cambio de idioma en desarrollo.", Toast.LENGTH_SHORT).show()
    }
}
