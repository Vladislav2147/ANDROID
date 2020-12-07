package by.bstu.vs.stpms.lr12.chess

import by.bstu.vs.stpms.lr12.chess.pieces.Piece
import java.lang.Exception
import java.util.ArrayList

class Board {
    companion object {
        private var instance: Board? = null
        fun getInstance(): Board {
            if (instance == null) {
                instance = Board()
            }
            return instance!!
        }
    }
    var pieces: ArrayList<Piece> = ArrayList<Piece>()

    fun getPieceBySpace(space: Space?): Piece? = pieces.firstOrNull { it.space != null && it.space == space }
    fun addPiece(piece: Piece) {
        if (getPieceBySpace(piece.space) == null)
            pieces.add(piece)
        else
            throw Exception("Space is occupied by another figure!")
    }
    fun dropPiece(space: Space) {
        getPieceBySpace(space)?.space = null
    }

}