package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class Funcion(
    var nombreFuncion: Token,
    var listaParametros: ArrayList<Parametro>,
    var tipoRetorno: Token,
    var listaSentencias: ArrayList<Sentencia>
) {

    override fun toString(): String {
        return "Funcion(nombre='$nombreFuncion', parametros=$listaParametros, tipo='$tipoRetorno', sentencias=$listaSentencias)"
    }

    fun getArbolVisual(): TreeItem<String> {

        val raiz = TreeItem("Función")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))
        raiz.children.add(TreeItem("Tipo de retorno: ${tipoRetorno.lexema}"))

        var raizParametros = TreeItem("Parámetros")
        for (parametro in listaParametros) {
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

    fun obtenerTiposParametros(): ArrayList<String> {
        var lista = ArrayList<String>()
        for (p in listaParametros) {
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito : String){
        tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, tipoRetorno.lexema, obtenerTiposParametros(), ambito, nombreFuncion.fila, nombreFuncion.columna)
        for (p in listaParametros) {
            tablaSimbolos.guardarSimboloValor(p.identificador.lexema, p.tipoDato.lexema, true, "${nombreFuncion.lexema}${obtenerTiposParametros()}", p.identificador.fila, p.identificador.columna)
        }
        for (s in listaSentencias) {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>) {
        for (s in listaSentencias) {
            s.analizarSemantica(tablaSimbolos, listaErrores, nombreFuncion.lexema)
        }
    }

    fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String, listaErrores: ArrayList<Error>): String {
        return tipoRetorno.lexema
    }

}





