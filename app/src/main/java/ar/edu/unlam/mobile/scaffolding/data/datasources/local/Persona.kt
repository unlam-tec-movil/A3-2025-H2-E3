package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

class OficiosConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String? = list?.joinToString(separator = ",")

    @TypeConverter
    fun toList(data: String?): List<String>? = data?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }
}

@Entity(tableName = "persona")
data class PersonaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val nombre: String,
    val dni: Long,
    val profesional: Boolean,
    val ubicacion: String?,
    var ciudad: String,
    var oficios: List<String> = emptyList<String>(),
)
