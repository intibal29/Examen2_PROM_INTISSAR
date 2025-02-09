package es.icjardin.examenprom2.repositorio

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import es.icjardin.examenprom2.modelos.Alumno

class AlumnoRepositorio(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "examen_interactivo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_ALUMNOS = "alumno"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_USUARIO = "usuario"
        private const val COLUMN_CONTRASENA = "contrasena"
        private const val COLUMN_ID_GRUPO = "idGrupo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_ALUMNOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_USUARIO TEXT UNIQUE NOT NULL,
                $COLUMN_CONTRASENA TEXT NOT NULL,
                $COLUMN_ID_GRUPO INTEGER,
                FOREIGN KEY ($COLUMN_ID_GRUPO) REFERENCES grupo(id)
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        onCreate(db)
    }

    /**
     * Método para agregar un nuevo alumno
     */
    fun agregarAlumno(alumno: Alumno): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, alumno.nombre)
            put(COLUMN_USUARIO, alumno.usuario)
            put(COLUMN_CONTRASENA, alumno.contrasena)
            put(COLUMN_ID_GRUPO, alumno.idGrupo)
        }
        val resultado = db.insert(TABLE_ALUMNOS, null, values)
        db.close()
        return resultado != -1L
    }

    /**
     * Método para obtener todos los alumnos
     */
    fun obtenerAlumnos(): List<Alumno> {
        val listaAlumnos = mutableListOf<Alumno>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_ALUMNOS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val alumno = Alumno(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    usuario = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USUARIO)),
                    contrasena = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTRASENA)),
                    idGrupo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_GRUPO))
                )
                listaAlumnos.add(alumno)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaAlumnos
    }

    /**
     * Método para actualizar un alumno
     */
    fun actualizarAlumno(alumno: Alumno): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, alumno.nombre)
            put(COLUMN_USUARIO, alumno.usuario)
            put(COLUMN_CONTRASENA, alumno.contrasena)
            put(COLUMN_ID_GRUPO, alumno.idGrupo)
        }
        val resultado = db.update(TABLE_ALUMNOS, values, "$COLUMN_ID = ?", arrayOf(alumno.id.toString()))
        db.close()
        return resultado > 0
    }

    /**
     * Método para eliminar un alumno
     */
    fun eliminarAlumno(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete(TABLE_ALUMNOS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }
}
