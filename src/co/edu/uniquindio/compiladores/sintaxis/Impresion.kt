package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Impresion(var expresionCadena: ExpresionCadena?):Sentencia() {

    override fun toString(): String {
        return "Impresion(lista=$expresionCadena)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem<String>("Imprimir:")
        raiz.children.add(expresionCadena?.getArbolVisual())
        return raiz
    }

}