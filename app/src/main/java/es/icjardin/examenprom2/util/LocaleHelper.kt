package es.icjardin.examenprom2.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {

    private const val PREFS_NAME = "configuracion_idioma"
    private const val KEY_IDIOMA = "idioma"

    /**
     * Aplica el idioma seleccionado al contexto.
     */
    fun aplicarIdioma(context: Context): Context {
        val idioma = obtenerIdioma(context)
        val locale = Locale(idioma)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    /**
     * Guarda el idioma en las preferencias.
     */
    fun guardarIdioma(context: Context, idioma: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_IDIOMA, idioma).apply()
    }

    /**
     * Obtiene el idioma guardado o devuelve "es" por defecto.
     */
    fun obtenerIdioma(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_IDIOMA, "es") ?: "es"
    }
}
