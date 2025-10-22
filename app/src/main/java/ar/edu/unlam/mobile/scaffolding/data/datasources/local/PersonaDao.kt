package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPersonas(persona: PersonaEntity)

    @Query("SELECT * FROM persona")
    fun selectasteriscoPersona() : Flow<List<PersonaEntity>>

    @Query("DELETE FROM persona")
    suspend fun eliminarTodasLasPersonas()
}