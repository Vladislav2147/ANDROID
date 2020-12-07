package by.bstu.vs.stpms.lr12.chess.pieces

import by.bstu.vs.stpms.lr12.chess.Board
import by.bstu.vs.stpms.lr12.chess.Color
import by.bstu.vs.stpms.lr12.chess.Space


interface Piece {
    val color: Color
    val name: String
    var space: Space?
    var history: ArrayList<Space?>

    fun moveTo(newSpace: Space)
    fun spaceIsEmpty(space: Space): Boolean = Board.getInstance().getPieceBySpace(space) == null


}