package ar.edu.unlam.mobile.scaffolding.data.repositories

enum class CategoriaOficio(
    val categoria: String,
) {
    PLOMERIA("Plomería"),
    ELECTRICIDAD("Electricidad"),
    ELECTRICIDAD_AUTO("Electricidad automotriz"),
    PELUQUERIA("Peluquería"),
    ALBANYILERIA("Albañilería"),
    CARPINTERIA("Carpintería"),
    PINTURA("Pintura"),
    MECANICA("Mecánica Automotriz"),
    JARDINERIA("Jardinería"),
    INFORMATICA("Reparación Informática"),
    CERRAJERIA("Cerrajería"),
    ;

    companion object {
        fun obtenerCategorias(): List<String> = entries.map { it.categoria }
    }
}
