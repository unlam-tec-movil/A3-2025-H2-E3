package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val personaDao: PersonaDao,
    ) : ViewModel() {
        val personas: StateFlow<List<PersonaEntity>> =
            personaDao.selectasteriscoPersona().stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList(),
            )

        fun insertarAll() {
            viewModelScope.launch {
                // valores hardcodeados para probar la lista de la screen
                personaDao.insertarPersonas(
                    PersonaEntity(nombre = "Belen Sosa", dni = 28987654L, profesional = false, ubicacion = null, ciudad = "Ramos Mejía"),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(nombre = "Carlos Perez", dni = 39001202L, profesional = true, ubicacion = null, ciudad = "Laferrere"),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Daniela Gomez",
                        dni = 41556778L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Isidro Casanova",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Emiliano Lopez",
                        dni = 30912345L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Gregorio de Laferrère",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Florencia Diaz",
                        dni = 36234567L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "González Catán",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Gustavo Martinez",
                        dni = 27789012L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Virrey del Pino",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Hilda Ramirez",
                        dni = 42345678L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Ciudad Evita",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Ignacio Fernández",
                        dni = 25098765L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "San Justo",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Julieta Castro",
                        dni = 38456789L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Ramos Mejía",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Kevin Sanchez",
                        dni = 32123456L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Isidro Casanova",
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(nombre = "Laura Varela", dni = 34987654L, profesional = true, ubicacion = null, ciudad = "Laferrere"),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Marcos Torres",
                        dni = 40001202L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "González Catán",
                    ),
                )
            }
        }

        fun deleteAll() {
            viewModelScope.launch {
                personaDao.eliminarTodasLasPersonas()
            }
        }
    }
