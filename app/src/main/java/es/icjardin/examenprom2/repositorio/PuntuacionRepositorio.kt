package es.icjardin.examenprom2.repositorio

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import es.icjardin.examenprom2.modelos.Puntuacion
import java.util.Date

class PuntuacionRepositorio(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "examen_interactivo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PUNTUACIONES = "puntuacion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // No se requiere definir la creación aquí, ya que MySQL maneja la estructura
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PUNTUACIONES")
        onCreate(db)
    }

    /**
     * Método para obtener todas las puntuaciones con los nombres de los alumnos y juegos.
     */
    fun obtenerPuntuaciones(): List<Puntuacion> {
        val listaPuntuaciones = mutableListOf<Puntuacion>()
        val db = readableDatabase

        val query = """
            SELECT p.id_puntuacion, p.id_alumno, a.nombre AS nombre_alumno, 
                   p.id_juego, j.nombre AS nombre_juego, p.puntaje, p.fecha 
            FROM puntuacion p
            INNER JOIN alumno a ON p.id_alumno = a.id_alumno
            INNER JOIN juego j ON p.id_juego = j.id_juego
            ORDER BY p.fecha DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val puntuacion = Puntuacion(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_puntuacion")),
                    idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow("id_alumno")),
                    nombreAlumno = cursor.getString(cursor.getColumnIndexOrThrow("nombre_alumno")),
                    idJuego = cursor.getInt(cursor.getColumnIndexOrThrow("id_juego")),
                    nombreJuego = cursor.getString(cursor.getColumnIndexOrThrow("nombre_juego")),
                    puntaje = cursor.getInt(cursor.getColumnIndexOrThrow("puntaje")),
                    fecha = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fecha")))
                )
                listaPuntuaciones.add(puntuacion)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaPuntuaciones
    }
    fun obtenerPuntuacionesAcumuladas(): List<Puntuacion> {
        val listaPuntuaciones = mutableListOf<Puntuacion>()
        val db = readableDatabase

        val query = """
        SELECT p.id_alumno, a.nombre AS nombre_alumno, SUM(p.puntaje) AS puntaje_total
        FROM puntuacion p
        INNER JOIN alumno a ON p.id_alumno = a.id_alumno
        GROUP BY p.id_alumno
        ORDER BY puntaje_total DESC
    """.trimIndent()

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val puntuacion = Puntuacion(
                    id = 0,  // No necesitamos un ID real aquí
                    idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow("id_alumno")),
                    nombreAlumno = cursor.getString(cursor.getColumnIndexOrThrow("nombre_alumno")),
                    idJuego = 0,  // No importa el ID del juego en esta vista
                    nombreJuego = "Total Acumulado",
                    puntaje = cursor.getInt(cursor.getColumnIndexOrThrow("puntaje_total")),
                    fecha = Date()
                )
                listaPuntuaciones.add(puntuacion)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaPuntuaciones
    }

    /**
     * Método para obtener las puntuaciones de un alumno.
     */
    fun obtenerPuntuacionesPorAlumno(idAlumno: Int): List<Puntuacion> {
        val listaPuntuaciones = mutableListOf<Puntuacion>()
        val db = readableDatabase

        val query = """
            SELECT p.id_puntuacion, p.id_alumno, a.nombre AS nombre_alumno, 
                   p.id_juego, j.nombre AS nombre_juego, p.puntaje, p.fecha 
            FROM puntuacion p
            INNER JOIN alumno a ON p.id_alumno = a.id_alumno
            INNER JOIN juego j ON p.id_juego = j.id_juego
            WHERE p.id_alumno = ?
            ORDER BY p.fecha DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(idAlumno.toString()))

        if (cursor.moveToFirst()) {
            do {
                val puntuacion = Puntuacion(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_puntuacion")),
                    idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow("id_alumno")),
                    nombreAlumno = cursor.getString(cursor.getColumnIndexOrThrow("nombre_alumno")),
                    idJuego = cursor.getInt(cursor.getColumnIndexOrThrow("id_juego")),
                    nombreJuego = cursor.getString(cursor.getColumnIndexOrThrow("nombre_juego")),
                    puntaje = cursor.getInt(cursor.getColumnIndexOrThrow("puntaje")),
                    fecha = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fecha")))
                )
                listaPuntuaciones.add(puntuacion)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaPuntuaciones
    }

    /**
     * Método para obtener las puntuaciones de un grupo.
     */
    fun obtenerPuntuacionesPorGrupo(idGrupo: Int): List<Puntuacion> {
        val listaPuntuaciones = mutableListOf<Puntuacion>()
        val db = readableDatabase

        val query = """
            SELECT p.id_puntuacion, p.id_alumno, a.nombre AS nombre_alumno, 
                   p.id_juego, j.nombre AS nombre_juego, p.puntaje, p.fecha 
            FROM puntuacion p
            INNER JOIN alumno a ON p.id_alumno = a.id_alumno
            INNER JOIN juego j ON p.id_juego = j.id_juego
            INNER JOIN alumno_grupo ag ON a.id_alumno = ag.id_alumno
            WHERE ag.id_grupo = ?
            ORDER BY p.fecha DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(idGrupo.toString()))

        if (cursor.moveToFirst()) {
            do {
                val puntuacion = Puntuacion(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id_puntuacion")),
                    idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow("id_alumno")),
                    nombreAlumno = cursor.getString(cursor.getColumnIndexOrThrow("nombre_alumno")),
                    idJuego = cursor.getInt(cursor.getColumnIndexOrThrow("id_juego")),
                    nombreJuego = cursor.getString(cursor.getColumnIndexOrThrow("nombre_juego")),
                    puntaje = cursor.getInt(cursor.getColumnIndexOrThrow("puntaje")),
                    fecha = Date(cursor.getLong(cursor.getColumnIndexOrThrow("fecha")))
                )
                listaPuntuaciones.add(puntuacion)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaPuntuaciones
    }

    /**
     * Método para agregar una nueva puntuación.
     */
    fun agregarPuntuacion(puntuacion: Puntuacion): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id_alumno", puntuacion.idAlumno)
            put("id_juego", puntuacion.idJuego)
            put("puntaje", puntuacion.puntaje)
            put("fecha", puntuacion.fecha.time)  // Convertir la fecha a timestamp
        }
        val resultado = db.insert(TABLE_PUNTUACIONES, null, values)
        db.close()
        return resultado != -1L
    }

    /**
     * Método para eliminar las puntuaciones de un alumno.
     */
    fun eliminarPuntuacionesPorAlumno(idAlumno: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete(TABLE_PUNTUACIONES, "id_alumno = ?", arrayOf(idAlumno.toString()))
        db.close()
        return resultado > 0
    }
}
