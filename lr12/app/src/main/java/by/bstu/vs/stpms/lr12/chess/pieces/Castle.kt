package by.bstu.vs.stpms.lr12.chess.pieces

import by.bstu.vs.stpms.lr12.chess.Board
import by.bstu.vs.stpms.lr12.chess.Color
import by.bstu.vs.stpms.lr12.chess.Space
import by.bstu.vs.stpms.lr12.exceptions.UnableToMoveToSpaceException

class Castle (
        override val color: Color,
        space: Space?,
) : Piece {

    override val state: Piece.State = Piece.State(canMove = false, isOnField = false)
    override var space: Space? = null
        set(value) {
            field = value
            history.add(value)
            state.canMove = value != null
            state.isOnField = value != null
        }

    override val name: String = "Castle"
    override var history: ArrayList<Space?> = ArrayList()

    init {
        this.space = space
    }

    constructor(color: Color): this(color, null)

    override fun moveTo(newSpace: Space) {
        try {
            if (space != null) {
                //Vertical
                if (space!!.x == newSpace.x) {
                    //If trajectory is clear
                    if (Board.pieces.none { it.space?.x == space!!.x &&
                                    (it.space!!.y > space!!.y && it.space!!.y < newSpace.y ||
                                            it.space!!.y < space!!.y && it.space!!.y > newSpace.y) }) {
                        val pieceOnNewSpace = Board.getPieceBySpace(newSpace)
                        if (pieceOnNewSpace != null) {
                            if (pieceOnNewSpace.color != this.color) {
                                Board.dropPiece(newSpace)
                                this.space = newSpace
                            } else {
                                throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace: cant drop same color piece")
                            }
                        } else {
                            this.space = newSpace
                        }

                    } else {
                        throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace: piece between spaces")
                    }
                    //Horizontal
                } else if (space!!.y == newSpace.y) {
                    //If trajectory is clear
                    if (Board.pieces.none { it.space?.y == space!!.y &&
                                    (it.space!!.x > space!!.x && it.space!!.x < newSpace.x ||
                                            it.space!!.x < space!!.x && it.space!!.x > newSpace.x) }) {

                        val pieceOnNewSpace = Board.getPieceBySpace(newSpace)
                        if (pieceOnNewSpace != null) {
                            if (pieceOnNewSpace.color != this.color) {
                                Board.dropPiece(newSpace)
                                this.space = newSpace
                            } else {
                                throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace: cant drop same color piece")
                            }
                        } else {
                            this.space = newSpace
                        }

                    } else {
                        throw UnableToMoveToSpaceException("Unable to move for $name from $space to $newSpace: figure between spaces")
                    }
                } else {
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
        return "\nCastle(color=$color, state=$state, space=$space, name='$name', history=$history)"
    }
}