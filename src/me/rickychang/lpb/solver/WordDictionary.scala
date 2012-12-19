package me.rickychang.lpb.solver

import scala.io.Source

import me.rickychang.lpb.solver.SolverUtil._

class WordDictionary(inputWordFile: String) {
  
  val wordsWithOccurrences: List[(String, Occurrences)] = {
    Source.fromFile(inputWordFile).getLines.toTraversable.map(w => (w, wordOccurrences(w))).toList.sortWith((e1,e2) => e1._1.size > e2._2.size)
  }

  

}