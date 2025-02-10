package es.icjardin.examenprom2.interfaz.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.interfaz.alumno.MenuAlumnoActividad
import es.icjardin.examenprom2.util.SesionManager

class LoginActividad : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var sesionManager: SesionManager

    private val loginViewModel: LoginVistaModelo by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ExamenProm2)  // Aplicamos el tema antes del setContentView
        setContentView(R.layout.actividad_login)

        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        sesionManager = SesionManager(this)

        // Si ya hay una sesión iniciada, entrar directamente al menú de alumnos
        if (sesionManager.sesionIniciada()) {
            entrarAlMenuAlumno()
        }

        btnIniciarSesion.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                loginViewModel.verificarCredenciales(usuario, contrasena) { idAlumno, esValido, esProfesor ->
                    runOnUiThread {
                        if (esValido && !esProfesor) {
                            // Solo permite alumnos, ignora si es profesor
                            sesionManager.guardarSesion(idAlumno, usuario, false)
                            entrarAlMenuAlumno()
                        } else {
                            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Redirige solo al menú de alumnos.
     */
    private fun entrarAlMenuAlumno() {
        startActivity(Intent(this, MenuAlumnoActividad::class.java))
        finish() // Cierra la pantalla de login
    }
}
