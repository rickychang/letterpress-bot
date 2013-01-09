package me.rickychang.lpb.solver

object SolverUtil {

  def wordHistogram(w: String): LetterHistogram = {
    val groupedLetters = w.toUpperCase.toList.groupBy((c: Char) => c)
    groupedLetters map { case (x, y) => (x, y.length) }
  }

}
