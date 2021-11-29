package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class DesicionSimple(var expresionLogica: ExpresionLogica?, var listaSencia:ArrayList<Sentencia>, var listaSenciaSino:ArrayList<Sentencia>?):Sentencia() {


    override fun toString(): String {
        return "DesicionSimple(expresionLogica=$expresionLogica, listaSencia=$listaSencia, listaSenciaSino=$listaSenciaSino)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Decicion Simple")
        raiz.children.add(expresionLogica!!.getArbolVisual())
        var raizSentenciasV = TreeItem("Lista sentencias (V)")
        for (p in listaSencia){
            raizSentenciasV.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizSentenciasV)

        var listaF = listaSenciaSino
        if (listaF != null) {
            var raizSentenciasF = TreeItem("Lista sentencias (F)")
            for (p in listaF) {
                raizSentenciasF.children.add(p.getArbolVisual())
            }
            raiz.children.add(raizSentenciasF)
        }
        return raiz

    }

}