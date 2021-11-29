package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionDeVariable(var tipoDato:Token, var tipoVariable:Token,var  identificadorVariable: Token) :Sentencia(){

    override fun toString(): String {
        return "DeclaracionDeVariable(tipoDato='$tipoDato', tipoVariable='$tipoVariable', identificadorVariable='$identificadorVariable')"
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Declaración de Variable")
        if (tipoVariable.lexema == "Var") {
            raiz.children.add(TreeItem("Mutable:\n${tipoVariable.lexema} ${tipoDato.lexema} ${identificadorVariable.lexema}"))
        } else {
            raiz.children.add(TreeItem("Inmutable:\n${tipoVariable.lexema} ${tipoDato.lexema} ${identificadorVariable.lexema}"))
        }
        return raiz
    }

}