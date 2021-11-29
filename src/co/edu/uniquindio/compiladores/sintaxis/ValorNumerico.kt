package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem
import sun.reflect.generics.tree.Tree


class ValorNumerico( var signo: Token?,  var numero: Token?):Expresion(){
    override fun toString(): String {
        return "ValorNumerico(signo=${signo!!.lexema}, numero=($numero),)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Valor numérico:")
        if (signo != null) {
            raiz.children.add(TreeItem("${signo!!.lexema}${numero!!.lexema}"))
        } else {
            raiz.children.add(TreeItem("${numero!!.lexema}"))
        }
        return raiz
    }

}