package es.icjardin.examenprom2.modelos


import java.util.Date

data class Puntuacion(
    val id: Int,
    val idAlumno: Int,
    val idJuego: Int,
    val puntaje: Int,
    val fecha: Date
)
