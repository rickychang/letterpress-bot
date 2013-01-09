package me.rickychang.lpb.board

import scala.Array.canBuildFrom

import me.rickychang.lpb.imageparser.ParserUtil.TilesPerBoard
import me.rickychang.lpb.imageparser.ParserUtil.TilesPerRowColumn
import me.rickychang.lpb.imageparser.TileStateParser

class GameBoard(val tiles: List[BoardTile]) {

  require(tiles.length == TilesPerBoard,
		  "Expected %d tiles, found %d".format(TilesPerBoard, tiles.length))

  private val letterToTiles: Map[Char, List[BoardTile]] =
    tiles.groupBy((c => c._1)).mapValues {
      _.sortWith((e1, e2) => e1._2.playerPotential > e2._2.playerPotential)
    }

  val playerOccupiedTiles = tiles collect { case t @ (_, PlayerOccupied, _) => t }
  val playerDefendedTiles = tiles collect { case t @ (_, PlayerDefended, _) => t }
  val freeTiles = tiles collect { case t @ (_, Free, _) => t }
  val opponentOccupiedTiles = tiles collect { case t @ (_, OpponentOccupied, _) => t }
  val opponentDefendedTiles = tiles collect { case t @ (_, OpponentDefended, _) => t }

  val playerTiles = (playerOccupiedTiles ++ playerDefendedTiles).sortBy(_._3)
  val opponentTiles = (opponentOccupiedTiles ++ opponentDefendedTiles).sortBy(_._3)

  val playerScore = playerTiles.map(_._2.currrentVal).sum
  val opponentScore = -opponentTiles.map(_._2.currrentVal).sum

  def letterTiles(letter: Char) = letterToTiles.getOrElse(letter, List.empty)

  def toFormattedString = {
    val rows =
      for (r <- tiles.grouped(TilesPerRowColumn))
        yield r map (t => "%s%s".format(t._1, t._2)) mkString ("", " ", "\n")
    rows.mkString
  }

  override def hashCode = tiles.hashCode

  override def equals(other: Any) = other match {
    case that: GameBoard => this.tiles == that.tiles
    case _ => false
  }

  override def toString = {
    tiles map (t => "%s%s".format(t._1, t._2)) mkString (" ")
  }
}

object GameBoard {

  def apply(tiles: List[BoardTile]) = new GameBoard(tiles)

  /**
   * Generate GameBoard from string representation.  Expected format is
   * space-delimited tiles each consisting of a capital letter and
   * a TileState short name, e.g.
   * Aw Br CR Db EB Fw Gr ...
   */
  def apply(tileString: String) = {
    val tileList = tileString.split("\\s+")
    if (tileList.length != TilesPerBoard)
      throw new InvalidTilesException("Expected %d tiles, found %d".format(TilesPerBoard, tileList.length))
    val tiles = tileList.zipWithIndex map { e =>
      getBoardTile(e) match {
        case Some(t) => t
        case None => throw new InvalidTilesException("Invalid tile string: %s".format(e._1))
      }
    }
    new GameBoard(tiles.toList)
  }

  private def getBoardTile(tileWithIndex: (String, Int)): Option[BoardTile] = {
    val (t, i) = tileWithIndex
    if (t.length != 2) None
    val c = t.charAt(0)
    if (!c.isLetter || !c.isUpper) None
    TileStateParser.getState(t.charAt(1)) match {
      case Some(s) => Some(c, s, i)
      case None => None
    }
  }

}
