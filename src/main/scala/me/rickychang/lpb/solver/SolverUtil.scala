package me.rickychang.lpb.solver

object SolverUtil {
  
  val DefaultWordDict = "/dicts/lpWords.txt"
  
  type Occurrences = Map[Char, Int]

  def wordOccurrences(w: String): Occurrences = {
    val groupedLetters = w.toUpperCase.toList.groupBy((c: Char) => c)
    groupedLetters map { case (x, y) => (x, y.length) }
  }
  
//  def canPlayWord(board: Occurrences, )

}