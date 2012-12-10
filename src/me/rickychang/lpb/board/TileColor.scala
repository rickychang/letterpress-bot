package me.rickychang.lpb.board

/**
 * Representation of Tile Colors (States).  Used for determining current player and opponent scores and
 * evaluating potential words to be played.
 */
sealed abstract class TileColor(name: String, currrentVal: Short, playerPotential: Short, opponentPotential: Short)

case object Free extends TileColor("Free", 0, 1, 0)
case object PlayerOccupied extends TileColor("Player Occupied", 1, 0, 0)
case object PlayerDefended extends TileColor("Player Defended", 1, 0, 0)
case object OpponentOccupied extends TileColor("OpponentOccupied", -1, 1, -1)
case object OpponentDefended extends TileColor("OpponentDefended", -1, 0, 0)
