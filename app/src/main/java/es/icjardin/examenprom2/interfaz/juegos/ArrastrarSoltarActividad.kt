package es.icjardin.examenprom2.interfaz.juegos


import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.icjardin.examenprom2.R

class ArrastrarSoltarActividad : AppCompatActivity() {

    private lateinit var btnVerificar: Button
    private lateinit var btnVolver: Button
    private lateinit var btnAyuda: ImageButton
    private lateinit var btnIdioma: ImageButton
    private lateinit var imgOpciones: List<ImageView>
    private lateinit var txtDestinos: List<TextView>

    // Diccionario de respuestas correctas
    private val respuestasCorrectas = mutableMapOf<Int, Int>()

    // Diccionario de asignaciones hechas por el usuario
    private val respuestasUsuario = mutableMapOf<Int, Int>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_juego_arrastrar)

        // Referencias a los elementos del XML
        btnVerificar = findViewById(R.id.btnVerificar)
        btnVolver = findViewById(R.id.btnVolver)
        btnAyuda = findViewById(R.id.btnAyuda)
        btnIdioma = findViewById(R.id.btnIdioma)

        imgOpciones = listOf(
            findViewById(R.id.imagen1),
            findViewById(R.id.imagen2),
            findViewById(R.id.imagen3),
            findViewById(R.id.imagen4)
        )

        txtDestinos = listOf(
            findViewById(R.id.texto1),
            findViewById(R.id.texto2),
            findViewById(R.id.texto3),
            findViewById(R.id.texto4)
        )

        // Configurar respuestas correctas (asociaciones de imagen con texto)
        respuestasCorrectas[R.id.imagen1] = R.id.texto1
        respuestasCorrectas[R.id.imagen2] = R.id.texto2
        respuestasCorrectas[R.id.imagen3] = R.id.texto3
        respuestasCorrectas[R.id.imagen4] = R.id.texto4

        // Habilitar arrastre en imágenes
        for (img in imgOpciones) {
            img.setOnLongClickListener { view ->
                val clipData = ClipData.newPlainText("", "")
                val shadow = View.DragShadowBuilder(view)
                view.startDragAndDrop(clipData, shadow, view, 0)
                true
            }
        }

        // Habilitar zonas de destino para soltar imágenes
        for (txt in txtDestinos) {
            txt.setOnDragListener { view, event ->
                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> true
                    DragEvent.ACTION_DROP -> {
                        val draggedView = event.localState as View
                        respuestasUsuario[draggedView.id] = view.id
                        (view as TextView).text = (draggedView as ImageView).contentDescription
                        draggedView.visibility = View.INVISIBLE
                        true
                    }
                    DragEvent.ACTION_DRAG_ENDED -> true
                    else -> false
                }
            }
        }

        // Botón para verificar respuestas
        btnVerificar.setOnClickListener {
            verificarRespuestas()
        }

        // Botón para volver al menú
        btnVolver.setOnClickListener {
            finish()
        }

        // Botón de ayuda
        btnAyuda.setOnClickListener {
            mostrarAyuda()
        }

        // Botón de cambio de idioma
        btnIdioma.setOnClickListener {
            cambiarIdioma()
        }
    }

    /**
     * Verifica si las respuestas del usuario son correctas
     */
    private fun verificarRespuestas() {
        var correctas = 0

        for ((imgId, txtId) in respuestasUsuario) {
            if (respuestasCorrectas[imgId] == txtId) {
                correctas++
            }
        }

        if (correctas == respuestasCorrectas.size) {
            Toast.makeText(this, "¡Correcto! Todas las respuestas son correctas.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Algunas respuestas son incorrectas. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Muestra un mensaje de ayuda al usuario
     */
    private fun mostrarAyuda() {
        Toast.makeText(this, "Arrastra las imágenes y suéltalas sobre la frase correcta.", Toast.LENGTH_LONG).show()
    }

    /**
     * Lógica para cambiar el idioma (puede implementarse más adelante)
     */
    private fun cambiarIdioma() {
        Toast.makeText(this, "Cambio de idioma no implementado aún.", Toast.LENGTH_SHORT).show()
    }
}
