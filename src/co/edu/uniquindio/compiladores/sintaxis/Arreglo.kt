package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem
import kotlin.collections.ArrayList


open class Arreglo(var nombre: Token, var tipoDato:Token, var listaExpresiones:ArrayList<Expresion>):Sentencia() {



    override fun getArbolVisual(): TreeItem<String> {

        val raiz = TreeItem<String>("Arreglo")


        raiz.children.add(TreeItem("${nombre.lexema} :${tipoDato.lexema}"))
        if (listaExpresiones.isNotEmpty()) {
            val raizExp = TreeItem("Argumentos")
            for (expresion in listaExpresiones) {
                raizExp.children.add(expresion.getArbolVisual())
            }
            raiz.children.add(raizExp)
        }
        return raiz


    }

    override fun toString(): String {
        return "Arreglo(nombre=$nombre, tipoDato=$tipoDato, listaExpresiones=$listaExpresiones)"
    }

}
