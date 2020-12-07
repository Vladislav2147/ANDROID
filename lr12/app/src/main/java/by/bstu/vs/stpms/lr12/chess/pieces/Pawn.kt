package by.bstu.vs.stpms.lr12.chess.pieces

import by.bstu.vs.stpms.lr12.chess.Board
import by.bstu.vs.stpms.lr12.chess.Color
import by.bstu.vs.stpms.lr12.chess.Space
import kotlin.math.abs

class Pawn(
    override val color: Color,
    space: Space?,
) : Piece {

    override var space: Space? = null
    set(value) {
        field = value
        history.add(value)
    }

    override val name: String
        get() = "Pawn"
    override var history: ArrayList<Space?> = ArrayList()

    init {
        this.space = space
    }


    override fun moveTo(newSpace: Space) {
        if (space != null) {
            val step = if (color == Color.BLACK) -1 else 1
            if (newSpace.y == space!!.y + step) {
                if (newSpace.x == space!!.x) {
                    if (spaceIsEmpty(newSpace)) {
                        space = newSpace
                    }
                    else {
                        println("Unable to move for $name from $space to $newSpace")
                    }
                }
                if (abs(newSpace.x - space!!.x) == 1) {
                    val piece = Board.getInstance().getPieceBySpace(newSpace)
                    if (piece != null && piece.color != color ) {
                        Board.getInstance().dropPiece(newSpace)
                        space = newSpace
                    } else {
                        println("Unable to move for $name from $space to $newSpace")
                    }
                } else {
                    println("Unable to move for $name from $space to $newSpace")
                }
            }
            else {
                println("Unable to move for $name from $space to $newSpace")
            }
        }
    }

}