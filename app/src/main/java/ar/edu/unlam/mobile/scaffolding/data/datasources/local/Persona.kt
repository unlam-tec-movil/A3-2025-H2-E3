package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffolding.data.repositories.CategoriaOficio

@Entity(tableName = "persona")
data class PersonaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val nombre: String,
    val dni: Long,
    val profesional: Boolean,
    val ubicacion: String?,
    var ciudad: String,
    // var oficio : List<CategoriaOficio>?
) {
/*
    fun checkOficio(oficio : String) : Boolean{
    var status : Boolean = false
        if(this.oficio!=null && this.oficio.contains(oficio)){
            status = true
            return status
        }else{
            status =false
            return status
        }
    }
*/
}
