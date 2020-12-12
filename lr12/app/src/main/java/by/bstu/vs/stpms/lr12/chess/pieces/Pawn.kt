package by.bstu.vs.stpms.lr12.chess.pieces

import by.bstu.vs.stpms.lr12.chess.Board
import by.bstu.vs.stpms.lr12.chess.Color
import by.bstu.vs.stpms.lr12.chess.Space
import by.bstu.vs.stpms.lr12.exceptions.UnableToMoveToSpaceException
import kotlin.math.abs

class Pawn(
    override val color: Color,
    space: Space?,
) : Piece {

    override val state: Piece.State = Piece.State()
    override var space: Space? = null
    set(value) {
        field = value
        history.add(value)
        state.canMove = value != null
        state.isOnField = value != null
    }

    override val name: String = "Pawn"
    override var history: ArrayList<Space?> = ArrayList()

    init {
        this.space = space
    }

    constructor(color: Color): this(color, null)

    override fun moveTo(newSpace: Space) {
        try {
            if (space != null) {
                val step = if (color == Color.BLACK) -1 else 1
                if (newSpace.y == space!!.y + step) {
                    if (newSpace.x == space!!.x) {
                        if (spaceIsEmpty(newSpace)) {
                            space = newSpace
                        }
                        else {
                            throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace")
                        }
                    }
                    if (abs(newSpace.x - space!!.x) == 1) {
                        val piece = Board.getPieceBySpace(newSpace)
                        if (piece != null && piece.color != color ) {
                            Board.dropPiece(newSpace)
                            space = newSpace
                        } else {
                            throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace")
                        }
                    } else {
                        throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace")
                    }
                }
                else {
                    throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace")
                }
            }
            else {
                throw UnableToMoveToSpaceException("Unable to move: Piece $name isn't on Board!")
            }
        } catch (e: UnableToMoveToSpaceException) {
            println(e.message)
        }
    }

    override fun toString(): String {
        return "\nPawn(color=$color, state=$state, space=$space, name='$name', history=$history)"
    }


}