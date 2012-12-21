package me.rickychang.lpb.solver

object SolverUtil {
  
  type Occurrences = Map[Char, Int]

  def wordOccurrences(w: String): Occurrences = {
    val groupedLetters = w.toUpperCase.toList.groupBy((c: Char) => c)
    val letterCounts = groupedLetters map { case (x, y) => (x, y.length) }
    letterCounts.toMap
  }
  
//  def canPlayWord(board: Occurrences, )

}