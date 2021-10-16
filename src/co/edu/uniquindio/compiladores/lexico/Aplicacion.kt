package co.edu.uniquindio.compiladores.lexico

fun main (){
    val lexico =
        AnalizadorLexico("a b c d 1656 16546.1684 Entero Real Mientras Para entero real mientras para abcdefghijKlmnopqrstU = == ! != | & && || < > >= <= =< => ++ + -- - += -= *= /= % %= \"hola mundo\" #sadfasdf ")
    lexico.analizar()
    print(lexico.listaTokens)
}