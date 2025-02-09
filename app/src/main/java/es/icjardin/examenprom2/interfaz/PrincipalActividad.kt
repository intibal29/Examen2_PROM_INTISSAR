package es.icjardin.examenprom2.interfaz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.interfaz.login.LoginActividad

class PrincipalActividad : AppCompatActivity() {

    private lateinit var btnAlumno: Button
    private lateinit var btnProfesor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_principal)

        // Referencias a los botones
        btnAlumno = findViewById(R.id.btnAlumno)
        btnProfesor = findViewById(R.id.btnProfesor)

        // Acción para alumnos (los lleva al Login)
        btnAlumno.setOnClickListener {
            val intent = Intent(this, LoginActividad::class.java)
            startActivity(intent)
        }

        // Acción para profesores (los lleva al Login también)
        btnProfesor.setOnClickListener {
            val intent = Intent(this, LoginActividad::class.java)
            startActivity(intent)
        }
    }
}
