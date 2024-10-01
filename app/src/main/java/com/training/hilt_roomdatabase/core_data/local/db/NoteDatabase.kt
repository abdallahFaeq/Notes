package com.training.hilt_roomdatabase.core_data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_data.local.dao.NoteDao
import com.training.hilt_roomdatabase.utils.Constants

@Database(entities = [NoteEntity::class], version = 4)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object{
        val MIGRATION2_3 = object :Migration(3,4){
            override fun migrate(db: SupportSQLiteDatabase) {
                // create a new table
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS note_table_new(
                    ${Constants.NOTE_ID_COLUMN} INTEGER PRIMARY KEY AUTOINCREMENT,
                    ${Constants.TITLE_COLUMN} TEXT NOT NULL,
                    ${Constants.SUBTITLE_COLUMN} TEXT NOT NULL,
                    ${Constants.DESCRIPTION_COLUMN} TEXT NOT NULL,
                    ${Constants.DATE_TIME_COLUMN} TEXT NOT NULL,
                    ${Constants.COLOR_COLUMN} INTEGER,
                    ${Constants.IMAGE_PATH_COLUMN} TEXT NOT NULL,
                    ${Constants.WEB_LINK_COLUMN} TEXT NOT NULL
                    )
                    """.trimIndent())

                // Copy the data from the old table to the new table
                db.execSQL("""
            INSERT INTO note_table_new (${Constants.NOTE_ID_COLUMN},
             ${Constants.TITLE_COLUMN},
              ${Constants.DESCRIPTION_COLUMN},
            SELECT note_id, title_row, desc_row FROM ${Constants.NOTE_TABLE}
        """)

                // drop old table
                db.execSQL("DROP TABLE ${Constants.NOTE_TABLE}")

                // rename new table name to old table name
                db.execSQL("ALTER TABLE note_table_new RENAME TO ${Constants.NOTE_TABLE}")
            }

        }
    }
}