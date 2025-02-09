package es.icjardin.examenprom2.interfaz.profesor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.modelos.Alumno

class CrearAlumnoActividad : AppCompatActivity() {

    private lateinit var etNombreAlumno: EditText
    private lateinit var etUsuarioAlumno: EditText
    private lateinit var etContrasenaAlumno: EditText
    private lateinit var btnGuardarAlumno: Button
    private lateinit var btnVolver: Button

    private val crearAlumnoViewModel: CrearAlumnoVistaModelo by viewModels() // Conexión con ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_crear_alumno)

        // Referencias a los elementos del XML
        etNombreAlumno = findViewById(R.id.etNombreAlumno)
        etUsuarioAlumno = findViewById(R.id.etUsuarioAlumno)
        etContrasenaAlumno = findViewById(R.id.etContrasenaAlumno)
        btnGuardarAlumno = findViewById(R.id.btnGuardarAlumno)
        btnVolver = findViewById(R.id.btnVolver)

        // Acción del botón "Guardar Alumno"
        btnGuardarAlumno.setOnClickListener {
            val nombre = etNombreAlumno.text.toString().trim()
            val usuario = etUsuarioAlumno.text.toString().trim()
            val contrasena = etContrasenaAlumno.text.toString().trim()

            if (nombre.isNotEmpty() && usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                val nuevoAlumno = Alumno(id = 0, nombre = nombre, usuario = usuario, contrasena = contrasena, idGrupo = 0)
                crearAlumnoViewModel.agregarAlumno(nuevoAlumno) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Alumno creado exitosamente", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                    } else {
                        Toast.makeText(this, "Error al crear el alumno", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción del botón "Volver"
        btnVolver.setOnClickListener {
            finish() // Cierra la actividad y vuelve al menú del profesor
        }
    }

    /**
     * Limpia los campos después de agregar un alumno correctamente
     */
    private fun limpiarCampos() {
        etNombreAlumno.text.clear()
        etUsuarioAlumno.text.clear()
        etContrasenaAlumno.text.clear()
    }
}
