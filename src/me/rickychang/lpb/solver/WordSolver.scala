package me.rickychang.lpb.solver

import me.rickychang.lpb.board.GameBoard
import me.rickychang.lpb.board.TileState

class WordSolver(board: GameBoard) {
  
	def wordScore(tiles: List[(Char, TileState, Int)]): (Int, Int) = {
	  tiles.map(t => Pair(t._2.playerPotential, t._2.opponentPotential)).foldLeft((0,0)) { (acc, v) => (acc._1 + v._1, acc._2 + v._2) }
	}
  
  
}