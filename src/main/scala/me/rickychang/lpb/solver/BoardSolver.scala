package me.rickychang.lpb.solver

import scala.Option.option2Iterable

import me.rickychang.lpb.board.BoardTile
import me.rickychang.lpb.board.GameBoard
import me.rickychang.lpb.board.TileState

class BoardSolver(val wordDict: WordDictionary) {

  def scoreDeltas(wordTiles: List[BoardTile]): (Int, Int) = {
    wordTiles.map(t => Pair(t._2.playerPotential, t._2.opponentPotential)).foldLeft((0, 0)) { (acc, v) => (acc._1 + v._1, acc._2 + v._2) }
  }
  
  def isWinningWord(board: GameBoard, wordTiles: List[BoardTile]): Boolean = {
    val deltas = scoreDeltas(wordTiles)
    val newPlayerScore = board.playerScore + deltas._1
    val newOpponentScore = board.opponentScore + deltas._2
    (newPlayerScore + newOpponentScore == 25) && (newPlayerScore > newOpponentScore)
  }
  
  /**
   * Ranking function used for ordering valid words.
   */
  // TODO: Generalize this to allow different ranking functions to be used.
  private def wordRanking(board: GameBoard, wordTiles: List[BoardTile]): Int = {
    // if word is a winning word, rank higher than all non-winning words
    if (isWinningWord(board, wordTiles)) Int.MaxValue
    else {
      val deltas = scoreDeltas(wordTiles)
      deltas._1 - deltas._2
    }
  }

  def findWords(board: GameBoard, maxWords: Int): List[(String, List[BoardTile])] = {
    val validWords = wordDict.wordsWithOccurrences.flatMap(w => canPlay(board, w._1, w._2))
    validWords.sortWith((e1, e2) => {
      wordRanking(board, e1._2) > wordRanking(board, e2._2)
    }).take(maxWords).toList
  }

  private def canPlay(board: GameBoard, candidate: String, candOccurrences: Map[Char, Int]): Option[(String, List[BoardTile])] = {
    def helper(acc: Set[BoardTile], occList: List[(Char, Int)]): Option[(String, List[BoardTile])] = {
      if (occList.isEmpty) Some((candidate, acc.toList))
      else {
        val candidateLetter = occList.head
        val letterTiles = board.letterTiles(candidateLetter._1)
        val matchingTile = letterTiles.filter { !acc.contains(_) }.headOption
        matchingTile match {
          case None => None
          case Some(t) => {
            if (candidateLetter._2 == 1) helper(acc + t, occList.tail)
            else helper(acc + t, (candidateLetter._1, candidateLetter._2 - 1) :: occList.tail)
          }
        }
      }
    }
    helper(Set.empty, candOccurrences.toList)
  }
}