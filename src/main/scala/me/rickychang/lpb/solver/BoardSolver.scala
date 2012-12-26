package me.rickychang.lpb.solver

import me.rickychang.lpb.board.GameBoard
import me.rickychang.lpb.board.TileState

class BoardSolver(val wordDict: WordDictionary) {

  def wordScore(tiles: List[(Char, TileState, Int)]): (Int, Int) = {
    tiles.map(t => Pair(t._2.playerPotential, t._2.opponentPotential)).foldLeft((0, 0)) { (acc, v) => (acc._1 + v._1, acc._2 + v._2) }
  }

  //TODO: add type aliases to package object for tile tuples
  def findWords(board: GameBoard, maxWords: Int): List[(String, List[(Char, TileState, Int)])] = {
    val validWords = wordDict.wordsWithOccurrences.flatMap(w => canPlay(board, w._1, w._2))
    validWords.sortWith((e1, e2) => {
      val s1 = wordScore(e1._2)
      val s2 = wordScore(e2._2)
      s1._1 - s1._2 > s2._1 - s2._2
    }).take(maxWords)
  }
  
  private def canPlay(board: GameBoard, candidate: String, candOccurrences: Map[Char, Int]): Option[(String, List[(Char, TileState, Int)])] = {
    def helper(acc: Set[(Char, TileState, Int)], occList: List[(Char, Int)]): Option[(String, List[(Char, TileState, Int)])] = {
      if (occList.isEmpty) Some((candidate, acc.toList))
      else {
    	  val letter = occList.head
    	  val letterTiles = board.boardOccurrences.getOrElse(letter._1, List.empty)
          val matchingTile = letterTiles.filter { !acc.contains(_) } .headOption
          matchingTile match {
             case None => None
             case Some(t) => {
               if (letter._2 == 1) helper(acc + t, occList.tail)
               else helper(acc + t, (letter._1, letter._2 - 1) :: occList.tail)
             }
          }
      }
    }
    helper(Set.empty, candOccurrences.toList)
  }
  
}