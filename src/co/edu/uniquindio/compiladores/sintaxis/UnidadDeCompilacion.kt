package co.edu.uniquindio.compiladores.sintaxis
import javafx.scene.control.TreeItem

class UnidadDeCompilacion (var listaFunciones:ArrayList<Funcion>){
    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Unidad de Compilacion")

        for (p in listaFunciones){
            raiz.children.add(p.getArbolVisual())
        }
        return raiz
    }

}