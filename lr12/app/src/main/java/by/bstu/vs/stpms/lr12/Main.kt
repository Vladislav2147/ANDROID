package by.bstu.vs.stpms.lr12

import by.bstu.vs.stpms.lr12.chess.Color
import by.bstu.vs.stpms.lr12.chess.Space
import by.bstu.vs.stpms.lr12.chess.pieces.Pawn
import by.bstu.vs.stpms.lr12.chess.pieces.Piece

fun main(args: Array<String>) {
//
//    var pawn = Pawn(Color.WHITE, Space.B4)
//    pawn.moveTo(Space.B5)
//    println(pawn.space)
//    println(pawn.history)
    var a = 10
    var b = 1
    println(1 in a..b || 5 in a downTo b)
    println()
}
