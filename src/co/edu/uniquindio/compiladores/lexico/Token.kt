package co.edu.uniquindio.compiladores.lexico

class Token(var lexema: String, var categoria: Categoria, var fila: Int, var columna: Int) {

    override fun toString(): String {
        return ("\nToken [Lexema: " + lexema + ", categoria: " + categoria + ", fila: " + fila + ", columna: " + columna
                + "]")
    }

    init {
        this.categoria = categoria
        this.fila = fila
        this.columna = columna
    }
}