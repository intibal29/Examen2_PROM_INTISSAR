package es.icjardin.examenprom2.modelos


import java.util.Date

data class Puntuacion(
    val id: Int,
    val idAlumno: Int,
    val nombreAlumno: String,  // Nuevo campo
    val idJuego: Int,
    val nombreJuego: String,  // Nuevo campo
    val puntaje: Int,
    val fecha: Date
)