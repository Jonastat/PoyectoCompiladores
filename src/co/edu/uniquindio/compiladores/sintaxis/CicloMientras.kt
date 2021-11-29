package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class CicloMientras (var exp:ExpresionLogica?, var lista:ArrayList<Sentencia>):Sentencia(){

    override fun toString(): String {
        return "CicloMientras(exp=$exp, lista=$lista)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Ciclo Mientras")
        raiz.children.add(exp!!.getArbolVisual())
        var raizParametros = TreeItem("Lista Sentencia")
        for (p in lista){
            raizParametros.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizParametros)
        return raiz

    }

}