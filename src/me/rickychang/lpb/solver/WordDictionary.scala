package me.rickychang.lpb.solver

import scala.io.Source

import me.rickychang.lpb.solver.SolverUtil._

class WordDictionary(inputWordFile: String) {
  
  val wordsWithOccurrences: List[(String, Occurrences)] = {
    Source.fromFile(inputWordFile).getLines.toTraversable.map(w => (w, wordOccurrences(w))).toList
  }
}
