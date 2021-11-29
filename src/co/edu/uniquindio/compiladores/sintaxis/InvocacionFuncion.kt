package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem


class InvocacionFuncion(var nombreFuncion: Token, var listaArgumentos:ArrayList<Parametro>) : Sentencia() {
    override fun toString(): String {
        return "Invocacion(nombre='$nombreFuncion', listaArguementos=$listaArgumentos)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Invocar Función:")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))

        var raizParametros = TreeItem("Argumentos:")
        for (parametro in listaArgumentos) {
            raizParametros.children.add(parametro.getArbolVisual())
        }
        raiz.children.add(raizParametros)

        return raiz
    }
}