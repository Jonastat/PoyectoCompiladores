package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Impresion(var listaExpresionCadena: ArrayList<ExpresionCadena>):Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem<String>("Imprimir:")
        var raizExpresionCadena = TreeItem("Expresiones Cadena:")
        for (expresionCadena in listaExpresionCadena) {
            raizExpresionCadena.children.add(expresionCadena.getArbolVisual())
        }
        raiz.children.add(raizExpresionCadena)
        return raiz
    }

}