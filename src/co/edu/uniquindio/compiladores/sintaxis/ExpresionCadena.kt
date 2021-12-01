package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem


class ExpresionCadena(var cadena: String) : Expresion() {

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Expresión Cadena:")
        raiz.children.add(TreeItem(cadena))
        return raiz
    }

    override fun toString(): String {
        return "Expresion Cadena ($cadena)"
    }

}
