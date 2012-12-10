package me.rickychang.lpb.board

/**
 * Representation of Tile Colors (States).  Used for determining current player and opponent scores and
 * evaluating potential words to be played.
 */
sealed abstract class TileColor(name: String, shortName: String, currrentVal: Short, playerPotential: Short, opponentPotential: Short) {
  override def toString: String = shortName
}

case object Free extends TileColor("Free", "w", 0, 1, 0)
case object PlayerOccupied extends TileColor("Player Occupied", "b", 1, 0, 0)
case object PlayerDefended extends TileColor("Player Defended", "B", 1, 0, 0)
case object OpponentOccupied extends TileColor("OpponentOccupied", "r", -1, 1, -1)
case object OpponentDefended extends TileColor("OpponentDefended", "R", -1, 0, 0)
