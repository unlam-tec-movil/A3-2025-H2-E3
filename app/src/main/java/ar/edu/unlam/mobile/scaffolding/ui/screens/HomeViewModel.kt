package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
                    PersonaEntity(
                        nombre = "Alejandro Ruiz",
                        dni = 35123456L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "San Justo",
                        oficios = listOf("Plomería", "Electricidad", "Peluquería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Belen Sosa",
                        dni = 28987654L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Ramos Mejía",
                        oficios = listOf("Albañilería", "Carpintería", "Pintura"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Carlos Perez",
                        dni = 39001202L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Laferrere",
                        oficios = listOf("Mecánica Automotriz", "Jardinería", "Reparación Informática"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Daniela Gomez",
                        dni = 41556778L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Isidro Casanova",
                        oficios = listOf("Cerrajería", "Plomería", "Albañilería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Emiliano Lopez",
                        dni = 30912345L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "González Catán",
                        oficios = listOf("Electricidad", "Carpintería", "Jardinería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Florencia Diaz",
                        dni = 36234567L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Virrey del Pino",
                        oficios = listOf("Peluquería", "Pintura", "Reparación Informática"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Gustavo Martinez",
                        dni = 27789012L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Ciudad Evita",
                        oficios = listOf("Plomería", "Pintura", "Cerrajería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Hilda Ramirez",
                        dni = 42345678L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Tapiales",
                        oficios = listOf("Albañilería", "Mecánica Automotriz", "Electricidad"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Ignacio Fernández",
                        dni = 25098765L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Lomas del Mirador",
                        oficios = listOf("Carpintería", "Jardinería", "Peluquería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Julieta Castro",
                        dni = 38456789L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Aldo Bonzi",
                        oficios = listOf("Reparación Informática", "Electricidad", "Carpintería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Kevin Sanchez",
                        dni = 32123456L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "San Justo",
                        oficios = listOf("Pintura", "Cerrajería", "Peluquería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Laura Varela",
                        dni = 34987654L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Ramos Mejía",
                        oficios = listOf("Mecánica Automotriz", "Plomería", "Albañilería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Marcos Torres",
                        dni = 40001202L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Laferrere",
                        oficios = listOf("Jardinería", "Electricidad", "Pintura"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Natalia Ortiz",
                        dni = 29556778L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Isidro Casanova",
                        oficios = listOf("Reparación Informática", "Peluquería", "Mecánica Automotriz"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Omar Herrera",
                        dni = 37912345L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "González Catán",
                        oficios = listOf("Cerrajería", "Albañilería", "Plomería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Paula Garcia",
                        dni = 31000100L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Virrey del Pino",
                        oficios = listOf("Electricidad", "Carpintería", "Cerrajería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Raúl Blanco",
                        dni = 26500900L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Ciudad Evita",
                        oficios = listOf("Peluquería", "Pintura", "Jardinería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Sofía Ruiz Díaz",
                        dni = 43101202L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Tapiales",
                        oficios = listOf("Albañilería", "Mecánica Automotriz", "Plomería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Tomás Vidal",
                        dni = 34500000L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Lomas del Mirador",
                        oficios = listOf("Carpintería", "Jardinería", "Electricidad"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Úrsula Moreno",
                        dni = 30000500L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Aldo Bonzi",
                        oficios = listOf("Pintura", "Reparación Informática", "Peluquería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Víctor Luna",
                        dni = 40500100L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "González Catán",
                        oficios = listOf("Mecánica Automotriz", "Cerrajería", "Albañilería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Wendy Caceres",
                        dni = 29000000L,
                        profesional = false,
                        ubicacion = null,
                        ciudad = "Virrey del Pino",
                        oficios = listOf("Jardinería", "Plomería", "Carpintería"),
                    ),
                )
                personaDao.insertarPersonas(
                    PersonaEntity(
                        nombre = "Yago Nuñez",
                        dni = 37100000L,
                        profesional = true,
                        ubicacion = null,
                        ciudad = "Laferrere",
                        oficios = listOf("Reparación Informática", "Electricidad", "Pintura"),
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
