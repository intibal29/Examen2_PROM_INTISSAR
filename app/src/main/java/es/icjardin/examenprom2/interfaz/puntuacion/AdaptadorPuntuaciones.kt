package es.icjardin.examenprom2.interfaz.puntuacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.icjardin.examenprom2.R
import es.icjardin.examenprom2.modelos.Puntuacion

class AdaptadorPuntuaciones(private var listaPuntuaciones: List<Puntuacion>) :
    RecyclerView.Adapter<AdaptadorPuntuaciones.PuntuacionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuntuacionViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_puntuacion, parent, false)
        return PuntuacionViewHolder(vista)
    }

    override fun onBindViewHolder(holder: PuntuacionViewHolder, position: Int) {
        val puntuacion = listaPuntuaciones[position]

        holder.tvNombreAlumno.text = "Alumno: ${puntuacion.nombreAlumno}"
        holder.tvPuntuacion.text = "Puntos: ${puntuacion.puntaje}"
        holder.tvJuego.visibility = View.GONE  // No necesitamos mostrar el juego
        holder.tvFecha.visibility = View.GONE  // No mostramos la fecha en el total acumulado
    }

    override fun getItemCount(): Int = listaPuntuaciones.size

    fun actualizarLista(nuevaLista: List<Puntuacion>) {
        listaPuntuaciones = nuevaLista
        notifyDataSetChanged()
    }

    inner class PuntuacionViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val tvNombreAlumno: TextView = vista.findViewById(R.id.tvNombreAlumno)
        val tvJuego: TextView = vista.findViewById(R.id.tvJuego)
        val tvPuntuacion: TextView = vista.findViewById(R.id.tvPuntuacion)
        val tvFecha: TextView = vista.findViewById(R.id.tvFecha)
    }
}
