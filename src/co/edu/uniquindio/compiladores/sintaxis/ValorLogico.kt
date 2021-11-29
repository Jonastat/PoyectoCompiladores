package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token


class ValorLogico(var valor: Token):Expresion() {
    override fun toString(): String {
        return "ValorLogico(valor=$valor)"
    }

}