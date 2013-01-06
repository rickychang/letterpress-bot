package me.rickychang.lpb.board


/**
 * Representation of Tile Colors (States).  Used for determining current player and opponent scores and
 * evaluating potential words to be played.
 */
sealed abstract class TileState(val description: String, val shortName: Char, val currrentVal: Short, val playerPotential: Short, val opponentPotential: Short) {
  override def toString: String = shortName.toString
}

// The shortnames used are references to the default color theme, e.g. "b" for light blue, player occupied.
case object Free extends TileState("Free", 'w', 0, 1, 0)
case object PlayerOccupied extends TileState("Player Occupied", 'b', 1, 0, 0)
case object PlayerDefended extends TileState("Player Defended", 'B', 1, 0, 0)
case object OpponentOccupied extends TileState("Opponent Occupied", 'r', -1, 1, -1)
case object OpponentDefended extends TileState("Opponent Defended", 'R', -1, 0, 0)

