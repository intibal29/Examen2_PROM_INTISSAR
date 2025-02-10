package es.icjardin.examenprom2.interfaz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.interfaz.login.LoginActividad
import es.icjardin.examenprom2.interfaz.profesor.MenuProfesorActividad

class PrincipalActividad : Activity() {

    private lateinit var btnAlumno: Button
    private lateinit var btnProfesor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
       setTheme(R.style.Theme_ExamenProm2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_principal)

        btnAlumno = findViewById(R.id.btnAlumno)
        btnProfesor = findViewById(R.id.btnProfesor)

        btnAlumno.setOnClickListener {
            startActivity(Intent(this, LoginActividad::class.java))
        }

        btnProfesor.setOnClickListener {
            startActivity(Intent(this, MenuProfesorActividad::class.java))
        }
    }
}
