package es.icjardin.examenprom2.repositorio

import android.content.Context
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

class BaseDatosHelper(context: Context) {
    private var conexion: Connection? = null

    init {
        cargarConfiguracion(context)
    }

    /**
     * Cargar las credenciales de conexión desde config.properties
     */
    private lateinit var dbUrl: String
    private lateinit var dbUser: String
    private lateinit var dbPassword: String

    private fun cargarConfiguracion(context: Context) {
        try {
            val properties = Properties()
            val inputStream = context.assets.open("config.properties")
            properties.load(inputStream)

            dbUrl = properties.getProperty("db.url")
            dbUser = properties.getProperty("db.user")
            dbPassword = properties.getProperty("db.password")
        } catch (e: Exception) {
            Log.e("BaseDatosHelper", "Error al cargar la configuración de la base de datos", e)
        }
    }

    /**
     * Método para establecer la conexión con MySQL
     */
    fun conectar(): Connection? {
        return try {
            Class.forName("com.mysql.jdbc.Driver") // Para versiones más antiguas
            conexion = DriverManager.getConnection(dbUrl, dbUser, dbPassword)
            Log.d("BaseDatosHelper", "Conexión a la base de datos exitosa")
            conexion
        } catch (e: Exception) {
            Log.e("BaseDatosHelper", "Error al conectar con la base de datos", e)
            null
        }
    }

    /**
     * Método para cerrar la conexión con la base de datos
     */
    fun cerrarConexion() {
        try {
            conexion?.close()
            Log.d("BaseDatosHelper", "Conexión cerrada correctamente")
        } catch (e: Exception) {
            Log.e("BaseDatosHelper", "Error al cerrar la conexión con la base de datos", e)
        }
    }
}
