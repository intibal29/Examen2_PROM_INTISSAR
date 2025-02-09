package es.icjardin.examenprom2.modelos

data class Alumno(
    val id: Int,
    val nombre: String,
    val usuario: String,
    val contrasena: String,
    val idGrupo: Int? = null  // Puede ser nulo si el alumno no está asignado a un grupo
)