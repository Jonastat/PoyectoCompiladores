package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.lexico.Token

class Retorno () : Sentencia() {

    var expresion : Expresion? = null
    var invocacionFuncion : InvocacionFuncion? = null
    var nulo : Token? = null

    constructor(expresion: Expresion) : this() {
        this.expresion = expresion
    }

    constructor(invocacionFuncion: InvocacionFuncion) : this() {
        this.invocacionFuncion = invocacionFuncion
    }

    constructor(nulo: Token) : this() {
        this.nulo = nulo
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem<String>("Retorno:")

        if (expresion != null) {
            raiz.children.add(expresion!!.getArbolVisual())
        } else {
            if (invocacionFuncion != null) {
                raiz.children.add(invocacionFuncion!!.getArbolVisual())
            } else {
                if (nulo!!.categoria == Categoria.PALABRA_RESERVADA && nulo!!.lexema == "Nulo") {
                    raiz.children.add(TreeItem(nulo!!.lexema))
                }
            }
        }

        return raiz
    }

}
