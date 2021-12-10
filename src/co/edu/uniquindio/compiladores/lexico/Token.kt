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

    fun getJavaCode(): String {
        if (categoria == Categoria.PALABRA_RESERVADA) {
            when (lexema) {
                "Entero" -> {
                    return "int"
                }
                "Real" -> {
                    return "Double"
                }
                "Cadena" -> {
                    return "String"
                }
                "Caracter" -> {
                    return "char"
                }
                "Si" -> {
                    return "if"
                }
                "Sino" -> {
                    return "else"
                }
                "Arreglo" -> {
                    return "ArrayList"
                }
                "Para" -> {
                    return "for"
                }
                "Mientras" -> {
                    return "while"
                }
                "Haga" -> {
                    return ""
                }
                "Retorna" -> {
                    return "return"
                }
                "Break" -> {
                    return "break"
                }
                "Fun" -> {
                    return ""
                }
                "Var" -> {
                    return ""
                }
                "Val" -> {
                    return "final"
                }
                "Imprimir" -> {
                    return "JOptionPane.showMessageDialog( null, "
                }
                "Invocar" -> {
                    return ""
                }
                "Binario" -> {
                    return "boolean"
                }
                "Vacio" -> {
                    return "void"
                }
                "Nulo" -> {
                    return "null"
                }
            }
        } else if (categoria == Categoria.COMENTARIO_DE_LINEA) {
            return lexema.replace("#","//")
        } else if (categoria == Categoria.COMENTARIO_DE_BLOQUE) {
            lexema.replaceFirst("@","/*")
            lexema.replace("@","*/")
            return lexema
        }
        return lexema
    }

}