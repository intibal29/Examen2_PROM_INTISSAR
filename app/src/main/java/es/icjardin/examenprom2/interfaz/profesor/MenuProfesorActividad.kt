package es.icjardin.examenprom2.interfaz.profesor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.interfaz.configuracion.ConfiguracionActividad
import es.icjardin.examenprom2.interfaz.login.LoginActividad
import es.icjardin.examenprom2.interfaz.puntuacion.PuntuacionActividad
import es.icjardin.examenprom2.util.SesionManager

class MenuProfesorActividad : AppCompatActivity() {
    private lateinit var sesionManager: SesionManager

    private lateinit var tvTituloProfesor: TextView
    private lateinit var btnCrearAlumno: Button
    private lateinit var btnCrearGrupo: Button
    private lateinit var btnConsultarPuntuaciones: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnAyuda: ImageButton
    private lateinit var btnIdioma: ImageButton
    private lateinit var btnConfiguracion: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_menu_profesor)

        sesionManager = SesionManager(this)

        // Inicializar elementos del XML
        tvTituloProfesor = findViewById(R.id.tvTituloProfesor)
        btnCrearAlumno = findViewById(R.id.btnCrearAlumno)
        btnCrearGrupo = findViewById(R.id.btnCrearGrupo)
        btnConsultarPuntuaciones = findViewById(R.id.btnConsultarPuntuaciones)
       // btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnAyuda = findViewById(R.id.btnAyuda)
        btnIdioma = findViewById(R.id.btnIdioma)
        btnConfiguracion = findViewById(R.id.btnConfiguracion) // Mover aquí arriba

        // Acción del botón "Configuración"
        btnConfiguracion.setOnClickListener {
            startActivity(Intent(this, ConfiguracionActividad::class.java))
        }

        // Acción del botón "Crear Alumno"
        btnCrearAlumno.setOnClickListener {
            startActivity(Intent(this, CrearAlumnoActividad::class.java))
        }

        // Acción del botón "Crear Grupo"
        btnCrearGrupo.setOnClickListener {
            startActivity(Intent(this, CrearGrupoActividad::class.java))
        }

        // Acción del botón "Consultar Puntuaciones"
        btnConsultarPuntuaciones.setOnClickListener {
            startActivity(Intent(this, PuntuacionActividad::class.java))
        }
/*
        // Acción del botón "Cerrar Sesión"
        btnCerrarSesion.setOnClickListener {
            sesionManager.cerrarSesion()
            startActivity(Intent(this, LoginActividad::class.java))
            finish()
        }
*/
        // Acción del botón "Ayuda"
        btnAyuda.setOnClickListener {
            mostrarMensajeAyuda()
        }

        // Acción del botón "Cambio de Idioma"
        btnIdioma.setOnClickListener {
            cambiarIdioma()
        }
    }

    private fun mostrarMensajeAyuda() {
        Toast.makeText(this, "Administra alumnos, grupos y puntuaciones.", Toast.LENGTH_LONG).show()
    }

    private fun cambiarIdioma() {
        Toast.makeText(this, "Función de cambio de idioma en desarrollo.", Toast.LENGTH_SHORT).show()
    }
}
