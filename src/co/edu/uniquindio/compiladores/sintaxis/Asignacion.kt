package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion():Sentencia() {
    var identificadoVariable: Token?=null
    var operadorAsignacion:Token?=null
    var expresion:Expresion?=null
    var invocacion:Sentencia?=null

    constructor(identificadoVariable: Token?,operadorAsignacion:Token?,expresion:Expresion?):this(){
        this.identificadoVariable=identificadoVariable
        this.operadorAsignacion=operadorAsignacion
        this.expresion=expresion
    }
    constructor(identificadoVariable: Token?,operadorAsignacion:Token?, invovacion:Sentencia?):this(){
        this.identificadoVariable=identificadoVariable
        this.operadorAsignacion=operadorAsignacion
        this.invocacion =invovacion

    }

    override fun toString(): String {
        return "Asignacion(identificadoVariable=$identificadoVariable, operadorAsignacion=$operadorAsignacion, expresion=$expresion, invovacion=$invocacion)"
    }

    override fun getArbolVisual(): TreeItem<String> {

            var raiz = TreeItem("Asignacion")

        if (expresion != null) {
            raiz.children.add(TreeItem("${identificadoVariable} ${operadorAsignacion} ${expresion}"))
        } else {
            raiz.children.add(TreeItem("${identificadoVariable} ${operadorAsignacion} ${invocacion}"))
        }
            return raiz
        }
}
