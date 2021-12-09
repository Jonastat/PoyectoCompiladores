package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class ExpresionAritmetica() : Expresion() {

    var expresionAritmetica1: ExpresionAritmetica? = null
    var expresionAritmetica2: ExpresionAritmetica? = null
    var operador: Token? = null
    var valorNumerico: ValorNumerico? = null

    constructor(
        espresionAritmetica1: ExpresionAritmetica,
        operador: Token,
        espresionAritmetica2: ExpresionAritmetica
    ) : this() {
        this.expresionAritmetica1 = espresionAritmetica1
        this.expresionAritmetica2 = espresionAritmetica2
        this.operador = operador
    }

    constructor(espresionAritmetica1: ExpresionAritmetica) : this() {
        this.expresionAritmetica1 = espresionAritmetica1

    }

    constructor(valorNumerico: ValorNumerico, operador: Token, espresionAritmetica2: ExpresionAritmetica) : this() {
        this.valorNumerico = valorNumerico
        this.expresionAritmetica2 = espresionAritmetica2
        this.operador = operador
    }

    constructor(valorNumerico: ValorNumerico) : this() {
        this.valorNumerico = valorNumerico

    }



    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Expresion Aritmetica")

        if (valorNumerico != null) {
            raiz.children.add(valorNumerico!!.getArbolVisual())
        } else if (expresionAritmetica1 != null && operador != null && expresionAritmetica2 != null) {

            raiz.children.add(expresionAritmetica1!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresionAritmetica2!!.getArbolVisual())
        } else {
            if (expresionAritmetica1 != null) {
                raiz.children.add(expresionAritmetica1!!.getArbolVisual())
            } else {
                if (valorNumerico != null && operador != null && expresionAritmetica2 != null) {
                    raiz.children.add(valorNumerico!!.getArbolVisual())
                    raiz.children.add(TreeItem("${operador!!.lexema}"))
                    raiz.children.add(expresionAritmetica2!!.getArbolVisual())
                }
            }
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String, listaErrores: ArrayList<Error>): String {

        if (expresionAritmetica1 != null && expresionAritmetica2 != null) {
            var tipo1 = expresionAritmetica1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
            var tipo2 = expresionAritmetica2!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)

            if (tipo1 == "Real" || tipo2 == "Real") {
                return "Real"
            } else {
                return "Entero"
            }
        } else if (expresionAritmetica1 != null) {
            return expresionAritmetica1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
        } else if (valorNumerico != null && expresionAritmetica1 != null) {
            var tipo1 = obtenerTipoCampo(valorNumerico, ambito, tablaSimbolos, listaErrores)
            var tipo2 = expresionAritmetica1!!.obtenerTipo(tablaSimbolos, ambito, listaErrores)
            if (tipo1 == "Real" || tipo2 == "Real") {
                return "Real"
            } else {
                return "Entero"
            }
        } else if (valorNumerico != null) {
            return obtenerTipoCampo(valorNumerico, ambito, tablaSimbolos, listaErrores)
        }
        return ""
    }

    fun obtenerTipoCampo (valorNumerico: ValorNumerico?, ambito: String, tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>) : String {
        if (valorNumerico!!.numero!!.categoria == Categoria.ENTERO) {
            return "Entero"
        } else if (valorNumerico!!.numero!!.categoria == Categoria.REAL) {
            return "Real"
        } else {
            var simbolo = tablaSimbolos.buscarSimboloValor(valorNumerico!!.numero!!.lexema, ambito)
            if (simbolo != null) {
                return simbolo.tipo
            } else {
                listaErrores.add(Error("El campo (${valorNumerico!!.numero!!.lexema}) no existe dentro del ámbito ($ambito).", valorNumerico!!.numero!!.fila, valorNumerico!!.numero!!.columna))
            }
        }
        return ""
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        if (valorNumerico != null) {
            if (valorNumerico!!.numero!!.categoria == Categoria.IDENTIFICADOR || valorNumerico!!.numero!!.categoria == Categoria.CARACTER) {
                var simbolo = tablaSimbolos.buscarSimboloValor(valorNumerico!!.numero!!.lexema, ambito)
                if (simbolo == null) {
                    listaErrores.add(Error("El campo (${valorNumerico!!.numero!!.lexema}) no existe dentro del ámbito ($ambito).", valorNumerico!!.numero!!.fila, valorNumerico!!.numero!!.columna))
                } else {
                    var tipoSimbolo = simbolo.tipo
                    var tipoValorNumerico = valorNumerico!!.obtenerTipo(valorNumerico!!.numero!!)
                    if (tipoValorNumerico != tipoSimbolo) {
                        listaErrores.add(Error("El tipo ($tipoValorNumerico) del valor numérico (${valorNumerico!!.numero!!.lexema}) no es un tipo de dato válido para una expresión aritmética.", valorNumerico!!.numero!!.fila, valorNumerico!!.numero!!.columna))
                    }
                }
            }
        }
        if (expresionAritmetica1 != null) {
            expresionAritmetica1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        if (expresionAritmetica2 != null) {
            expresionAritmetica2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun toString(): String {
        return "ExpresionAritmetica(valorNumerico=$valorNumerico)"
    }
}