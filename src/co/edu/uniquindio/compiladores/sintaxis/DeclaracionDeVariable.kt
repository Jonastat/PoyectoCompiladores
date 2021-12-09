package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class DeclaracionDeVariable(var tipoDato:Token, var tipoVariable:Token,var  identificadorVariable: Token) :Sentencia(){

    override fun toString(): String {
        return "DeclaracionDeVariable(tipoDato='$tipoDato', tipoVariable='$tipoVariable', identificadorVariable='$identificadorVariable')"
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Declaración de Variable")
        if (tipoVariable.lexema == "Var") {
            raiz.children.add(TreeItem("Mutable:\n${tipoVariable.lexema} ${tipoDato.lexema} ${identificadorVariable.lexema}"))
        } else {
            raiz.children.add(TreeItem("Inmutable:\n${tipoVariable.lexema} ${tipoDato.lexema} ${identificadorVariable.lexema}"))
        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(identificadorVariable.lexema, tipoDato.lexema, true, ambito, identificadorVariable.fila, identificadorVariable.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
        var simbolo = tablaSimbolos.buscarSimboloValor(identificadorVariable.lexema, ambito)
        if (simbolo!!.nombre == identificadorVariable.lexema && simbolo!!.tipo == tipoDato.lexema) {
            listaErrores.add(Error("El campo (${identificadorVariable.lexema}) ya existe dentro del ámbito ($ambito).", identificadorVariable.fila, identificadorVariable.columna))
        }
    }

}