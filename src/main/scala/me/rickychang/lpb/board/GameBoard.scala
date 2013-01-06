package me.rickychang.lpb.board

import me.rickychang.lpb.board._
import me.rickychang.lpb.imageparser.ParserUtil.TilesPerRowColumn
import me.rickychang.lpb.solver.SolverUtil

class GameBoard(val tiles: List[BoardTile]) {
  
  private val letterToTiles: Map[Char, List[BoardTile]] = tiles.groupBy((c => c._1)).mapValues(_.sortWith((e1, e2) => e1._2.playerPotential > e2._2.playerPotential))
  
  val playerOccupiedTiles = tiles collect { case t @ (_,PlayerOccupied,_) => t }
  val playerDefendedTiles = tiles collect { case t @ (_,PlayerDefended,_) => t }
  val freeTiles = tiles collect { case t @ (_,Free,_) => t }
  val opponentOccupiedTiles = tiles collect { case t @ (_,OpponentOccupied,_) => t }
  val opponentDefendedTiles = tiles collect { case t @ (_,OpponentDefended,_) => t }
  
  val playerTiles = (playerOccupiedTiles ++ playerDefendedTiles).sortBy(_._3)
  val opponentTiles = (opponentOccupiedTiles ++ opponentDefendedTiles).sortBy(_._3)
  
  val playerScore = playerTiles.map(_._2.currrentVal).sum
  val opponentScore = -opponentTiles.map(_._2.currrentVal).sum
  
  def letterTiles(letter: Char) = letterToTiles.getOrElse(letter, List.empty)

  override def toString = {
    val buffer = new StringBuilder
    for (i <- 0 until tiles.length) {
      val t = tiles(i)
      buffer.append("%s%s ".format(t._1, t._2))
      if ((i + 1) % TilesPerRowColumn == 0) buffer.replace(buffer.length - 1, buffer.length, "\n")
    }
    buffer.stripLineEnd
  }
}