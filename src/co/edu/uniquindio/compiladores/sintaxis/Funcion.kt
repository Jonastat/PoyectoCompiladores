package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class Funcion(
    var nombre: Token,
    var parametros: ArrayList<Parametro>,
    var tipoRetorno: Token,
    var listaSentencias: ArrayList<Sentencia>
) {

    override fun toString(): String {
        return "Funcion(nombre='$nombre', parametros=$parametros, tipo='$tipoRetorno', sentencias=$listaSentencias)"
    }

    fun getArbolVisual(): TreeItem<String> {

        val raiz = TreeItem("Función")

        raiz.children.add(TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add(TreeItem("Tipo de retorno: ${tipoRetorno.lexema}"))

        var raizParametros = TreeItem("Parámetros")
        for (parametro in parametros) {
            raizParametros.children.add(parametro.getArbolVisual())
        }
        raiz.children.add(raizParametros)

        var raizSentencias = TreeItem("Sentencias")
        for (sentencia in listaSentencias) {
            raizSentencias.children.add(sentencia.getArbolVisual())
        }
        raiz.children.add(raizSentencias)

        return raiz
    }

}





