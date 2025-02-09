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
import es.icjardin.examenprom2.interfaz.profesor.MenuProfesorActividad
import es.icjardin.examenprom2.util.SesionManager

class LoginActividad : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var sesionManager: SesionManager

    private val loginViewModel: LoginVistaModelo by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_login)

        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        sesionManager = SesionManager(this)

        // Si ya hay una sesión iniciada, entrar directamente al menú
        if (sesionManager.sesionIniciada()) {
            entrarAlMenu()
        }

        btnIniciarSesion.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                loginViewModel.verificarCredenciales(usuario, contrasena) { idAlumno, esValido, esProfesor ->
                    runOnUiThread {
                        when {
                            esValido && !esProfesor -> {
                                // Guardamos la sesión con el ID del alumno y el usuario
                                sesionManager.guardarSesion(idAlumno, usuario)
                                startActivity(Intent(this, MenuAlumnoActividad::class.java))
                            }
                            esValido && esProfesor -> {
                                startActivity(Intent(this, MenuProfesorActividad::class.java))
                            }
                            else -> {
                                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                            }
                        }
                        finish() // Cierra la pantalla de login después de iniciar sesión correctamente
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Redirige al menú del alumno o profesor según el usuario logueado.
     */
    private fun entrarAlMenu() {
        val idAlumno = sesionManager.obtenerIdAlumno()

        val intent = if (idAlumno > 0) {
            Intent(this, MenuAlumnoActividad::class.java)
        } else {
            Intent(this, MenuProfesorActividad::class.java)
        }
        startActivity(intent)
        finish() // Cierra la pantalla de login
    }
}
