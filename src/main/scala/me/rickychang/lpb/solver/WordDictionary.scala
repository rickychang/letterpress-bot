package me.rickychang.lpb.solver

import scala.io.Source
import scala.io.BufferedSource

import me.rickychang.lpb.solver.SolverUtil._

class WordDictionary(inputWordFile: String = DefaultWordDict) {
  
  val wordsWithOccurrences: List[(String, Occurrences)] = {
    Source.fromURL(getClass.getResource(inputWordFile)).getLines.map(w => (w, wordOccurrences(w))).toList
  }
}
