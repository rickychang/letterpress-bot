package me.rickychang.lpb.solver

object SolverUtil {
  
  type Occurrences = List[(Char, Int)]

  def wordOccurrences(w: String): Occurrences = {
    val groupedLetters = w.toLowerCase().toList.groupBy((c: Char) => c).toList
    val letterCounts = groupedLetters map { case (x, y) => (x, y.length) }
    letterCounts.sorted
  }

}