package es.icjardin.examenprom2.util

import android.content.Context
import android.content.SharedPreferences

class SesionManager(context: Context) {

    companion object {
        private const val PREFERENCIAS = "sesion_prefs"
        private const val KEY_ID_ALUMNO = "id_alumno"
        private const val KEY_NOMBRE_ALUMNO = "nombre_alumno"
        private const val KEY_USUARIO = "usuario"
        private const val KEY_SESION_INICIADA = "sesion_iniciada"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE)

    /**
     * Guarda la sesión del usuario después de iniciar sesión.
     */
    fun guardarSesion(idAlumno: Int, nombreAlumno: String) {
        sharedPreferences.edit().apply {
            putInt(KEY_ID_ALUMNO, idAlumno)
            putString(KEY_NOMBRE_ALUMNO, nombreAlumno)
            putBoolean(KEY_SESION_INICIADA, true)
            apply()
        }
    }

    /**
     * Devuelve el ID del alumno logueado.
     */
    fun obtenerIdAlumno(): Int {
        return sharedPreferences.getInt(KEY_ID_ALUMNO, -1) // -1 si no hay usuario guardado
    }

    /**
     * Devuelve el nombre del alumno logueado.
     */
    fun obtenerNombreAlumno(): String {
        return sharedPreferences.getString(KEY_NOMBRE_ALUMNO, "") ?: ""
    }

    /**
     * Devuelve el nombre de usuario logueado.
     */
    fun obtenerUsuario(): String? {
        return sharedPreferences.getString(KEY_USUARIO, null)
    }

    /**
     * Verifica si hay una sesión activa.
     */
    fun sesionIniciada(): Boolean {
        return sharedPreferences.getBoolean(KEY_SESION_INICIADA, false)
    }

    /**
     * Cierra la sesión del usuario y borra los datos guardados.
     */
    fun cerrarSesion() {
        sharedPreferences.edit().clear().apply()
    }
}
