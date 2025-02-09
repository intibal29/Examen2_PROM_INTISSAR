package es.icjardin.examenprom2.repositorio


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import es.icjardin.examenprom2.modelos.Grupo

class GrupoRepositorio(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "examen_interactivo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_GRUPOS = "grupo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_GRUPOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GRUPOS")
        onCreate(db)
    }

    /**
     * Método para agregar un nuevo grupo
     */
    fun agregarGrupo(grupo: Grupo): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, grupo.nombre)
        }
        val resultado = db.insert(TABLE_GRUPOS, null, values)
        db.close()
        return resultado != -1L
    }

    /**
     * Método para obtener todos los grupos
     */
    fun obtenerGrupos(): List<Grupo> {
        val listaGrupos = mutableListOf<Grupo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_GRUPOS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val grupo = Grupo(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                )
                listaGrupos.add(grupo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaGrupos
    }

    /**
     * Método para actualizar un grupo
     */
    fun actualizarGrupo(grupo: Grupo): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, grupo.nombre)
        }
        val resultado = db.update(TABLE_GRUPOS, values, "$COLUMN_ID = ?", arrayOf(grupo.id.toString()))
        db.close()
        return resultado > 0
    }

    /**
     * Método para eliminar un grupo
     */
    fun eliminarGrupo(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete(TABLE_GRUPOS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }
}
