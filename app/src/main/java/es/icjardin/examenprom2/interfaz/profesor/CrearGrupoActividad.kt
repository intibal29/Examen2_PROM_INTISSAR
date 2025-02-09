package es.icjardin.examenprom2.interfaz.profesor

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.modelos.Alumno
import es.icjardin.examenprom2.modelos.Grupo

class CrearGrupoActividad : AppCompatActivity() {

    private lateinit var etNombreGrupo: EditText
    private lateinit var btnGuardarGrupo: Button
    private lateinit var btnVolver: Button
    private lateinit var rvListaAlumnos: RecyclerView
    private lateinit var adaptadorAlumnos: AdaptadorSeleccionAlumnos

    private val crearGrupoViewModel: CrearGrupoVistaModelo by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_crear_grupo)

        // Referencias a los elementos del XML
        etNombreGrupo = findViewById(R.id.etNombreGrupo)
        btnGuardarGrupo = findViewById(R.id.btnGuardarGrupo)
        btnVolver = findViewById(R.id.btnVolver)
        rvListaAlumnos = findViewById(R.id.rvListaAlumnos)

        // Configurar RecyclerView
        rvListaAlumnos.layoutManager = LinearLayoutManager(this)
        adaptadorAlumnos = AdaptadorSeleccionAlumnos(emptyList())
        rvListaAlumnos.adapter = adaptadorAlumnos

        // Cargar lista de alumnos
        crearGrupoViewModel.obtenerAlumnos { lista ->
            adaptadorAlumnos.actualizarLista(lista)
        }

        // Acción del botón "Guardar Grupo"
        btnGuardarGrupo.setOnClickListener {
            val nombreGrupo = etNombreGrupo.text.toString().trim()
            val alumnosSeleccionados = adaptadorAlumnos.obtenerSeleccionados()

            if (nombreGrupo.isNotEmpty() && alumnosSeleccionados.isNotEmpty()) {
                val nuevoGrupo = Grupo(id = 0, nombre = nombreGrupo)
                crearGrupoViewModel.agregarGrupo(nuevoGrupo, alumnosSeleccionados) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Grupo creado exitosamente", Toast.LENGTH_SHORT).show()
                        finish() // Cierra la actividad
                    } else {
                        Toast.makeText(this, "Error al crear el grupo", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Completa todos los campos y selecciona al menos un alumno", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción del botón "Volver"
        btnVolver.setOnClickListener {
            finish()
        }
    }
}
