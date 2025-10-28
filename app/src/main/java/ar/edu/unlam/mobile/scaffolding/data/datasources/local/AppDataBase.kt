package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PersonaEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao
}
