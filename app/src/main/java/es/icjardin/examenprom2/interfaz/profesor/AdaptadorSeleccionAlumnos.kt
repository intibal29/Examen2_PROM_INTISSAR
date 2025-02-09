package es.icjardin.examenprom2.interfaz.profesor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.modelos.Alumno

class AdaptadorSeleccionAlumnos(private var listaAlumnos: List<Alumno>) :
    RecyclerView.Adapter<AdaptadorSeleccionAlumnos.AlumnoViewHolder>() {

    private val alumnosSeleccionados = mutableSetOf<Alumno>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno_seleccion, parent, false)
        return AlumnoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = listaAlumnos[position]
        holder.tvNombreAlumno.text = alumno.nombre
        holder.checkBox.isChecked = alumnosSeleccionados.contains(alumno)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alumnosSeleccionados.add(alumno)
            } else {
                alumnosSeleccionados.remove(alumno)
            }
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size

    /**
     * Método para actualizar la lista de alumnos en el adaptador.
     */
    fun actualizarLista(nuevaLista: List<Alumno>) {
        listaAlumnos = nuevaLista
        notifyDataSetChanged()
    }

    /**
     * Devuelve la lista de alumnos seleccionados.
     */
    fun obtenerSeleccionados(): List<Alumno> {
        return alumnosSeleccionados.toList()
    }

    inner class AlumnoViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val tvNombreAlumno: TextView = vista.findViewById(R.id.tvNombreAlumno)
        val checkBox: CheckBox = vista.findViewById(R.id.cbSeleccionarAlumno)
    }
}
