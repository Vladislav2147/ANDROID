package by.bstu.vs.stpms.lr12

import by.bstu.vs.stpms.lr12.chess.Board
import by.bstu.vs.stpms.lr12.chess.Color
import by.bstu.vs.stpms.lr12.chess.Player
import by.bstu.vs.stpms.lr12.chess.Space
import by.bstu.vs.stpms.lr12.chess.pieces.Castle
import by.bstu.vs.stpms.lr12.chess.pieces.Pawn

fun main() {

    val player = Player("Vladislav", 100)
    player.wins = 1000

    val (name, level) = player
    println("Player $name, $level lvl")
    println("-------------------------------------------------------")

    val whitePawn = Pawn(Color.WHITE)
    val blackPawn = Pawn(Color.BLACK)
    val whiteCastle = Castle(Color.WHITE)
    val blackCastle = Castle(Color.BLACK)

    Board.addPiece(whitePawn)
    Board.addPiece(blackPawn)
    Board.addPiece(whiteCastle)
    Board.addPiece(blackCastle)

    println(Board.pieces)
    println("-------------------------------------------------------")

    whiteCastle.space = Space.A1
    whitePawn.space = Space.A2
    blackCastle.space = Space.H8
    blackPawn.space = Space.H7

    println("On Board: ${Board.pieces}")
    println("-------------------------------------------------------")

    println("Invalid move:")
    blackCastle.moveTo(Space.H6)
    blackCastle.moveTo(Space.B3)
    println("-------------------------------------------------------")

    //Valid move
    blackCastle.moveTo(Space.B8)
    blackCastle.moveTo(Space.B3)

    println("Valid move result ${Board.pieces}")
    println("-------------------------------------------------------")

    whitePawn.moveTo(Space.B3)
    println("white pawn drops black castle: ${Board.pieces}")


}
