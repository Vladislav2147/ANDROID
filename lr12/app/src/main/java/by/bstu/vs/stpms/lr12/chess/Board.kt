package by.bstu.vs.stpms.lr12.chess

import by.bstu.vs.stpms.lr12.chess.pieces.Piece
import java.lang.Exception
import java.util.ArrayList

object Board {
    var pieces: ArrayList<Piece> = ArrayList<Piece>()

    fun getPieceBySpace(space: Space?): Piece? = pieces.firstOrNull { it.space != null && it.space == space }
    fun addPiece(piece: Piece) {
        if (getPieceBySpace(piece.space) == null)

            pieces.add(piece)
        else
            throw Exception("Space is occupied by another figure!")
    }
    fun dropPiece(space: Space) {
        val piece = getPieceBySpace(space)
        piece?.space = null
        piece?.let { println("piece ${piece.name} was dropped from ${piece.space}") }
    }

}