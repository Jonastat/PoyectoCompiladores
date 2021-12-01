package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Argumento () {

    var expresionAritmetica : ExpresionAritmetica? = null
    var exprecionCadena : ExpresionCadena? = null

    constructor(expresionAritmetica: ExpresionAritmetica) : this() {
        this.expresionAritmetica = expresionAritmetica
    }

    constructor(expresionCadena: ExpresionCadena) : this() {
        this.exprecionCadena = expresionCadena
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Argumento:")

        if (expresionAritmetica != null) {
            raiz.children.add(expresionAritmetica!!.getArbolVisual())
        } else {
            if (exprecionCadena != null) {
                raiz.children.add(exprecionCadena!!.getArbolVisual())
            }
        }

        return raiz
    }

}
