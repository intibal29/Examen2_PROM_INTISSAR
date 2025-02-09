package es.icjardin.examenprom2.repositorio


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import es.icjardin.examenprom2.modelos.Juego

class JuegoRepositorio(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "examen_interactivo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_JUEGOS = "juego"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_JUEGOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_DESCRIPCION TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_JUEGOS")
        onCreate(db)
    }

    /**
     * Método para agregar un nuevo juego
     */
    fun agregarJuego(juego: Juego): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, juego.nombre)
            put(COLUMN_DESCRIPCION, juego.descripcion)
        }
        val resultado = db.insert(TABLE_JUEGOS, null, values)
        db.close()
        return resultado != -1L
    }

    /**
     * Método para obtener todos los juegos
     */
    fun obtenerJuegos(): List<Juego> {
        val listaJuegos = mutableListOf<Juego>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_JUEGOS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val juego = Juego(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                )
                listaJuegos.add(juego)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listaJuegos
    }

    /**
     * Método para actualizar un juego
     */
    fun actualizarJuego(juego: Juego): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, juego.nombre)
            put(COLUMN_DESCRIPCION, juego.descripcion)
        }
        val resultado = db.update(TABLE_JUEGOS, values, "$COLUMN_ID = ?", arrayOf(juego.id.toString()))
        db.close()
        return resultado > 0
    }

    /**
     * Método para eliminar un juego
     */
    fun eliminarJuego(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete(TABLE_JUEGOS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }
}
