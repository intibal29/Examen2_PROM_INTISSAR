package es.icjardin.examenprom2.interfaz.configuracion

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.interfaz.login.LoginActividad
import es.icjardin.examenprom2.util.LocaleHelper

class ConfiguracionActividad : AppCompatActivity() {

    private lateinit var spinnerIdioma: Spinner
    private lateinit var btnGuardarIdioma: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnVolver: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplica el idioma antes de llamar a super.onCreate()
        LocaleHelper.aplicarIdioma(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_configuracion)

        // Referencias al XML
        spinnerIdioma = findViewById(R.id.spinnerIdioma)
        btnGuardarIdioma = findViewById(R.id.btnGuardarIdioma)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnVolver = findViewById(R.id.btnVolver)

        // Configurar Spinner con opciones de idioma (Solo Español e Inglés)
        val opcionesIdiomas = arrayOf("Español", "English")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesIdiomas)
        spinnerIdioma.adapter = adapter

        // Seleccionar el idioma actual en el Spinner
        val idiomaActual = LocaleHelper.obtenerIdioma(this)
        spinnerIdioma.setSelection(if (idiomaActual == "es") 0 else 1)

        // Guardar idioma seleccionado
        btnGuardarIdioma.setOnClickListener {
            val nuevoIdioma = if (spinnerIdioma.selectedItem.toString() == "Español") "es" else "en"
            LocaleHelper.guardarIdioma(this, nuevoIdioma)

            Toast.makeText(this, getString(R.string.bot_n_para_cambiar_idioma), Toast.LENGTH_SHORT).show()
            reiniciarAplicacion()
        }

        // Acción para cerrar sesión
        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }

        // Acción para volver
        btnVolver.setOnClickListener {
            finish()
        }
    }

    /**
     * Reinicia la aplicación para aplicar el cambio de idioma.
     */
    private fun reiniciarAplicacion() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        finishAffinity() // Cierra todas las actividades
        startActivity(intent)
    }

    /**
     * Cierra sesión y redirige a la pantalla de inicio.
     */
    private fun cerrarSesion() {
        startActivity(Intent(this, LoginActividad::class.java))
        finishAffinity()
    }
}
