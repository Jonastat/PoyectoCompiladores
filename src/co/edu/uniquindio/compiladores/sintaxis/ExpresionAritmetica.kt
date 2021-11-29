package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class ExpresionAritmetica() : Expresion() {

    var expresionAritmetica1: ExpresionAritmetica? = null
    var expresionAritmetica2: ExpresionAritmetica? = null
    var operador: Token? = null
    var valorNumerico: ValorNumerico? = null

    constructor(
        espresionAritmetica1: ExpresionAritmetica?,
        operador: Token?,
        espresionAritmetica2: ExpresionAritmetica?
    ) : this() {
        this.expresionAritmetica1 = espresionAritmetica1
        this.expresionAritmetica2 = espresionAritmetica2
        this.operador = operador
    }

    constructor(espresionAritmetica1: ExpresionAritmetica?) : this() {
        this.expresionAritmetica1 = espresionAritmetica1

    }

    constructor(valorNumerico: ValorNumerico?, operador: Token?, espresionAritmetica2: ExpresionAritmetica?) : this() {
        this.valorNumerico = valorNumerico
        this.expresionAritmetica2 = espresionAritmetica2
        this.operador = operador
    }

    constructor(valorNumerico: ValorNumerico?) : this() {
        this.valorNumerico = valorNumerico

    }

    override fun toString(): String {
        return "ExpresionAritmetica(espresionAritmetica1=$expresionAritmetica1, espresionAritmetica2=$expresionAritmetica2, operador=$operador, valorNumerico=$valorNumerico)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Expresion Aritmetica")

        if (expresionAritmetica1 != null && operador != null && expresionAritmetica2 != null) {

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
                } else {
                    if (valorNumerico != null) {
                        raiz.children.add(valorNumerico!!.getArbolVisual())
                    }
                }
            }
        }

        return raiz
    }

}