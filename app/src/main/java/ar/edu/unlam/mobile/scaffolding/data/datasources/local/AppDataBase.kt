package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PersonaEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(OficiosConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao
}
