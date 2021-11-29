package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional():Expresion() {

    var expresionAritmetica1:ExpresionAritmetica?=null
    var expresionAritmetica2:ExpresionAritmetica?=null
    var operador: Token?=null

    constructor(espresionAritmetica1:ExpresionAritmetica?, operador: Token?, espresionAritmetica2:ExpresionAritmetica?):this(){
        this.expresionAritmetica1=espresionAritmetica1
        this.expresionAritmetica2=espresionAritmetica2
        this.operador=operador
    }

    override fun toString(): String {
        return "ExpresionRelacional(espresionAritmetica1=$expresionAritmetica1 operador=$operador espresionAritmetica2=$expresionAritmetica2)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresión Relacional")
        raiz.children.add(expresionAritmetica1!!.getArbolVisual())
        raiz.children.add(TreeItem("Operador relacional: ${operador!!.lexema}"))
        raiz.children.add(expresionAritmetica2!!.getArbolVisual())
        return raiz
    }

}
