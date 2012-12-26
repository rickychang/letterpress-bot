package me.rickychang.lpb.board


/**
 * Representation of Tile Colors (States).  Used for determining current player and opponent scores and
 * evaluating potential words to be played.
 */
sealed abstract class TileState(val description: String, val shortName: String, val currrentVal: Short, val playerPotential: Short, val opponentPotential: Short) {
  override def toString: String = shortName
}

case class Free() extends TileState("Free", "w", 0, 1, 0)
case class PlayerOccupied() extends TileState("Player Occupied", "b", 1, 0, 0)
case class PlayerDefended() extends TileState("Player Defended", "B", 1, 0, 0)
case class OpponentOccupied() extends TileState("Opponent Occupied", "r", -1, 1, -1)
case class OpponentDefended() extends TileState("Opponent Defended", "R", -1, 0, 0)
