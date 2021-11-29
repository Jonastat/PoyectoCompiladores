package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error

class AnalizadorSintactico(var listaTokens: ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores = ArrayList<Error>()

    fun obtenerSiguienteToken() {
        posicionActual++;
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }

    fun hacerBT(posicion: Int) {
        posicionActual = posicion
        tokenActual = listaTokens[posicionActual]
    }

    /**
     * <UnidadCompilacion>::= <ListaFunciones>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if (listaFunciones.size > 0) {
            UnidadDeCompilacion(listaFunciones)
        } else null
    }

    /**
     * <ListaFunciones>::= <Funcion> [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()
        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion>::= Fun <TipoDeRetorno> nombre "(" [<ListaParametros>] ")" <BloqueSentencias>
     */
    fun esFuncion(): Funcion? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Fun") {
            obtenerSiguienteToken()

            var tipoRetorno = esTipoRetorno()
            if (tipoRetorno != null) {
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    val nombreFuncion = tokenActual
                    obtenerSiguienteToken()

                    if (tokenActual.categoria == Categoria.PARENTESIS_APERTURA) {
                        obtenerSiguienteToken()
                        val listaParametros: ArrayList<Parametro> = esListaParametros()

                        if (tokenActual.categoria == Categoria.PARENTESIS_CIERRE) {
                            obtenerSiguienteToken()
                            val bloqueSentencias = esBloqueSentencias()
                            if (bloqueSentencias != null) {
                                return Funcion(nombreFuncion, listaParametros, tipoRetorno, bloqueSentencias)

                            } else {
                                reportarError("El bloque de sentencias está vacío.")
                            }
                        } else {
                            print(tokenActual.lexema)
                            reportarError("Falta parentesis derecho.")
                        }
                    } else {
                        reportarError("Falta parentesis izquierdo.")
                    }
                } else {
                    reportarError("Falta el identificador de la función.")
                }
            } else {
                reportarError("Falta el tipo de retorno.")
            }
        }
        return null
    }

    /**
     * <TipoDeRetorno> ::= Entero | Real | Cadena | Caracter | Binario | Vacio
     */
    fun esTipoRetorno(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "Entero" || tokenActual.lexema == "Real" || tokenActual.lexema == "Cadena" || tokenActual.lexema == "Caracter" || tokenActual.lexema == "Binario" || tokenActual.lexema == "Vacio") {
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaParametros>::= <Parametro> ["," <ListaParametros>]
     */
    fun esListaParametros(): ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var p = esParametro()

        while (p != null) {
            listaParametros.add(p)

            if (tokenActual.categoria == Categoria.SEPARADOR) {
                obtenerSiguienteToken()
                p = esParametro()
            } else {
                if (tokenActual.categoria == Categoria.PARENTESIS_CIERRE) {
                    break
                } else {
                    break
                }
            }
        }
        return listaParametros
    }

    /**
     * <Parametro>::= Identificador ":" <TipoDeDato>
     */
    fun esParametro(): Parametro? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CARACTER) {
            val nombre = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                val tipoDeDato = esTipoDato()

                if (tipoDeDato != null) {
                    obtenerSiguienteToken()
                    return Parametro(tipoDeDato, nombre)
                } else {
                    reportarError("Falta el tipo de dato.")
                }
            } else {
                reportarError("Falta dos puntos.")
            }
        } else {
            reportarError("Falta el identificador.")
        }
        return null
    }

    /**
     * <TipoDato>::= Entero | Real | Cadena | Caracter
     */

    fun esTipoDato(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "Entero" || tokenActual.lexema == "Real" || tokenActual.lexema == "Cadena" || tokenActual.lexema == "Caracter" || tokenActual.lexema == "Binario") {
                return tokenActual;
            }
        }
        return null
    }

    /**
     * <BloqueSentencias>::= "{" [<ListaSentencias>] "}"
     */
    fun esBloqueSentencias(): ArrayList<Sentencia>? {
        if (tokenActual.categoria == Categoria.LLAVE_APERTURA) {
            obtenerSiguienteToken()
            var listaSentencias = esListaSentencias()
            if (tokenActual.categoria == Categoria.LLAVE_CIERRE) {
                obtenerSiguienteToken()
                return listaSentencias
            }
        }
        return null
    }

    /**
     * <ListaSentencias>::= [<Sentencia>]
     */
    fun esListaSentencias(): ArrayList<Sentencia>? {
        val listaSentencias = ArrayList<Sentencia>()
        var s = esSentencia()

        while (s != null) {
            listaSentencias.add(s)
            s = esSentencia()
        }
        return listaSentencias
    }

    /**
     * <Sentencia>::= <DecisiónSimple> | <DeclaracionVariable> | <Asignacion> | <Impresion> | <Ciclo> | <Retorno> | <LecturaDatos> | <InvocarFuncion> |
     *     <Incremento> | <Decremento> | <DeclaracionVariable> | <Arreglo>
     */
    fun esSentencia(): Sentencia? {
        var s: Sentencia? = esDecisionSimnple()
        if (s != null) {
            return s
        }
        s = esAsignacion()
        if (s != null) {
            return s
        }
        s = esImpresion()
        if (s != null) {
            return s
        }
        s = esCicloMientras()
        if (s != null) {
            return s
        }
        /*s = esRetorno()
        if (s != null){
            return s
        }*/
        /*s = esLecturaDatos()
        if (s != null){
        }*/
        s = esInvocacionFuncion()
        if (s != null) {
            return s
            s = esInvocacionFuncion()
        }
        /*s = esIncremento()
        if (s != null){
            return s
        }*/
        /*s = esDecremento()
        if (s != null){
            return s
        }*/
        s = esDeclaracionVariable()
        if (s != null) {
            return s
        }
        /*s = esArreglo()
        if (s != null){
            return s
        }*/
        return null
    }

    /**
     * <DecisionSimple>::= Si <ExpresionLogica> [<BloqueSentencias>] [Sino [<BloqueSentencias>]]
     */
    fun esDecisionSimnple(): Sentencia? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Si") {
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if (expresion != null) {
                hacerBT(posicionActual-1)
                val bloqueSentenciasV = esBloqueSentencias()
                if (bloqueSentenciasV != null) {
                    if (tokenActual.lexema == "Sino") {
                        obtenerSiguienteToken()
                        val bloqueSentenciasF = esBloqueSentencias()
                        if (bloqueSentenciasF != null) {
                            return DesicionSimple(expresion, bloqueSentenciasV, bloqueSentenciasF)
                        }
                    } else {
                        return DesicionSimple(expresion, bloqueSentenciasV, null)
                    }
                }
            } else {
                reportarError("Error en condición.")
            }
        }
        return null
    }

    /**
     * <DeclaracionVariable>::= <VariableMutable> | <VariableInmutable>
     */
    fun esDeclaracionVariable(): DeclaracionDeVariable? {
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Var") {
                return esVariableMutable()
            }
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Val") {
                return esVariableInmutable()
            }

        return null
    }

    /**
     * <VariableMutable>::= Var <tipoDato> Identificador
     */
    fun esVariableMutable(): DeclaracionDeVariable? {
        val mutable = tokenActual
        obtenerSiguienteToken()
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CARACTER) {
                val identificador = tokenActual
                obtenerSiguienteToken()
                return DeclaracionDeVariable(tipoDato, mutable, identificador)
            } else {
                reportarError("Falta identificador.")
                return null
            }

        } else {
            reportarError("Falta definir tipo de dato.")
            return null
        }

    }

    /**
     * <VariableInmutable>::= Val <tipoDato> Identificador
     */
    fun esVariableInmutable(): DeclaracionDeVariable? {
        val inMutable = tokenActual
        obtenerSiguienteToken()
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CARACTER) {
                val identificador = tokenActual
                obtenerSiguienteToken()
                return DeclaracionDeVariable(tipoDato, inMutable, identificador)
            } else {
                reportarError("Falta identificador.")
                return null
            }

        } else {
            reportarError("Falta definir tipo de dato.")
            return null
        }

    }

    /**
     * <Asignacion>::=<IdentificadorDeVariable> OpAsignacion <Expresion> ";" | <IdentificadorDeVariable> OpAsignacion <InvocacionFuncion> ";"
     */
    fun esAsignacion(): Asignacion? {

        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                val operadorAsignacion = tokenActual
                obtenerSiguienteToken()
                val invocacion = esInvocacionFuncion()
                if (invocacion != null) {
                    return Asignacion(identificador, operadorAsignacion, invocacion)
                } else {
                    val expresion = esExpresion()
                    if (expresion != null) {
                        if (tokenActual.categoria == Categoria.TERMINAL_FIN_SENTENCIA) {
                            obtenerSiguienteToken()
                            return Asignacion(identificador, operadorAsignacion, expresion)
                        } else {
                            reportarError("Falta terminal fin de sentencia ';'.")
                        }
                    } else {
                        reportarError("Falta expresión asignación.")
                    }
                }
            } else {
                reportarError("Falta un operador de asignación.")
            }
        }
        return null
    }

    /**
     * <CicloMientras>::= Mientras  <Expresionlogica> Haga <BloqueSentencias>
     */
    fun esCicloMientras(): CicloMientras? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Mientras") {
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if (expresion != null) {
                hacerBT(posicionActual-1)
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Haga") {
                    obtenerSiguienteToken()
                    val bloqueSentencias = esBloqueSentencias()
                    if (bloqueSentencias != null) {
                        return CicloMientras(expresion, bloqueSentencias)
                    }
                } else {
                    reportarError("Falta la palabra reservada 'Haga'.")
                }
            } else {
                reportarError("Falta expresión lógica.")
            }
        }
        return null
    }

    /**
     *<Expresion> ::= <ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionLogica> | <ExpresionCadena>
     */
    fun esExpresion(): Expresion? {

        var expresion = esExpresionLogica()
        if (expresion != null) {
            return expresion
        }
        var expresionAri = esExpresionAritmetica()
        if (expresionAri != null) {
            return expresionAri
        }
        var expresionRela = esExpresionRelacional()
        if (expresionRela != null) {
            return expresionRela
        }
        var expresionCadena = esExpresionCadena()
        if (expresionCadena != null) {
            return expresionCadena
        }
        return null
    }

    /**
     * <ExpAritmetica> ::= "("<ExpAritmetica>")" [operadorAritmetico <ExpAritmetica>] |<ValorNumerico> [operadorAritmetico <ExpAritmetica>]
     */
    fun esExpresionAritmetica(): ExpresionAritmetica? {
        if (tokenActual.categoria == Categoria.PARENTESIS_APERTURA) {
            obtenerSiguienteToken()
            val expA1 = esExpresionAritmetica()
            if (expA1 != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_CIERRE) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                        var oPa = tokenActual
                        obtenerSiguienteToken()
                        val exPA2 = esExpresionAritmetica()
                        if (exPA2 != null) {
                            return ExpresionAritmetica(expA1, oPa, exPA2)
                        }
                    } else {
                        return ExpresionAritmetica(expA1)
                    }
                } else {
                    reportarError("Falta paréntesis de cierre.")
                }
            } else {
                reportarError("Falta expresión aritmética.")
            }
        } else {
            var valorNum = esValorNumerico()
            if (valorNum != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                    var oPa = tokenActual
                    obtenerSiguienteToken()
                    val exPA = esExpresionAritmetica()
                    if (exPA != null) {
                        obtenerSiguienteToken()
                        return ExpresionAritmetica(valorNum, oPa, exPA)
                    }
                } else {
                    return ExpresionAritmetica(valorNum)
                }
            }
        }
        return null
    }

    /**
     * <valorNumerico>::= [<signo>]Entero | [<signo>]Doble | [<signo>]IdentificadorDeVariable
     */

    fun esValorNumerico(): ValorNumerico? {

        if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema == "-")) {
            var signo = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.REAL || tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CARACTER) {
                val termino = tokenActual
                return ValorNumerico(signo, termino)
            }
        } else {
            if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.REAL || tokenActual.categoria == Categoria.IDENTIFICADOR || tokenActual.categoria == Categoria.CARACTER) {
                val termino = tokenActual
                return ValorNumerico(null, termino)
            }
        }
        return null
    }

    /**
     *<ExpresionLogica>::=<ExpresionLogica> operadorLogicoBin <ExpresionLogica>|operadorNegacion<ExpresionLogica> | <valorLogico>
     * <ExpresionLogica>::= operadorNegacion <ExpresionLogica> [oLogicoBin<ExpresionLogica>] | <ExpresionRelacional>[operadorLogicoBin<ExpresionLogica>]
     */
    fun esExpresionLogica(): ExpresionLogica? {

        if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && (tokenActual.lexema == "!")) {
            val oLogico = tokenActual
            obtenerSiguienteToken()
            var expLogica1 = esExpresionLogica()
            if (expLogica1 != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_BINARIO && (tokenActual.lexema == "&&" || tokenActual.lexema == "||")) {
                    val oBinario = tokenActual
                    obtenerSiguienteToken()
                    val expLogica2 = esExpresionLogica()
                    if (expLogica2 != null) {
                        return ExpresionLogica(oLogico, expLogica1, oBinario, expLogica2)
                    } else {
                        reportarError("Falta la otra expresión lógica")
                    }

                } else {
                    return ExpresionLogica(oLogico, expLogica1)
                }
            } else {
                reportarError("Falta la expresión lógica ")
            }
        } else {

            val expRela = esExpresionRelacional()
            if (expRela != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_BINARIO && (tokenActual.lexema == "&&" || tokenActual.lexema == "||")) {
                    val oBinario2 = tokenActual
                    obtenerSiguienteToken()
                    val expLogica3 = esExpresionLogica()
                    if (expLogica3 != null) {
                        obtenerSiguienteToken()
                        return ExpresionLogica(expRela, oBinario2, expLogica3)
                    } else {
                        reportarError("Falta la otra expresión lógica")
                    }
                } else {
                    return ExpresionLogica(expRela)
                }
            } else {

                return null
            }

        }

        return null
    }


    /**
     * <ExpresionCadena>::= cadenaCaracteres["+"<ExpresionCadena>] | cadenaCaracteres
     */
    fun esExpresionCadena(): ExpresionCadena? {
        if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
            var cadena = tokenActual.lexema
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "+") {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.CADENA_CARACTERES) {
                    cadena += tokenActual.lexema
                    obtenerSiguienteToken()
                    return ExpresionCadena(cadena)
                } else {
                    reportarError("Falta concatenar un elemento válido (identificador o cadena de caracteres) a la expresión cadena.")
                }
            } else {
                if (tokenActual.categoria == Categoria.PARENTESIS_CIERRE) {
                    return ExpresionCadena(cadena)
                } else {
                    reportarError("Error en expresión cadena.")
                }
            }
        }
        return null
    }

    /**
     * <Impresion>::= Imprimir "(" [<ExpresionCadena>] ")"
     */
    fun esImpresion(): Impresion? {

        if (tokenActual.lexema == "Imprimir") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_APERTURA) {
                obtenerSiguienteToken()
                val expresionCadena = esExpresionCadena()
                if (tokenActual.categoria == Categoria.PARENTESIS_CIERRE) {
                    obtenerSiguienteToken()

                    return Impresion(expresionCadena)

                } else {
                    reportarError("No hay datos para imprimir")
                }

            } else {
                reportarError("Falta el agrupador inicial")
            }
        }
        return null
    }


    /**
     * <InvocacionFuncion>::= Invocar identificador"("<ListaArgumentos>")";"
     */
    fun esInvocacionFuncion(): Sentencia? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Invocar") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombreFuncion = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESIS_APERTURA) {
                    obtenerSiguienteToken()
                    val listaParametros: ArrayList<Parametro> = esListaParametros()
                    if (tokenActual.categoria == Categoria.PARENTESIS_CIERRE) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.TERMINAL_FIN_SENTENCIA) {
                            obtenerSiguienteToken()
                            return InvocacionFuncion(nombreFuncion, listaParametros)
                        } else {
                            reportarError("Falta fin de sentencia.")
                        }
                    } else {
                        reportarError("Falta paréntesis de cierre.")
                        return null
                    }
                } else {
                    reportarError("Falta paréntesis de apertura.")
                    return null
                }
            } else {
                reportarError("Falta identificador de función a invocar.")
            }
        }
        return null
    }

    /**
     *<ExpresionRelacional>::=":"<ExpresionAritmetica>OperadorRelacional<ExpresionAritmetica>
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        val expreAritmetica = esExpresionAritmetica()
        if (expreAritmetica != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                val operador = tokenActual
                obtenerSiguienteToken()
                val expreAritmetica2 = esExpresionAritmetica()
                if (expreAritmetica2 != null) {
                    return ExpresionRelacional(expreAritmetica, operador, expreAritmetica2)
                } else {
                    reportarError("Falta expresión aritmetica.")
                }
            } else {

                return null

            }
        }
        return null
    }

}