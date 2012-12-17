package me.rickychang.lpb.solver

import java.io.File
import scala.io.Source
import me.rickychang.lpb.solver.SolverUtil._

class WordDictionary(inputWordFile: String) {
  
  private val wordsByOccurrences: Map[Occurrences, List[String]] = {
    val groupedOccurrences = Source.fromFile(inputWordFile).getLines.toTraversable.map(w => (w, wordOccurrences(w))).groupBy(p => p._2)
    groupedOccurrences.mapValues(l => l.map(e => e._1).toList)
  }
  
  def getWords(letters: Occurrences): Option[List[String]] = {
    wordsByOccurrences.get(letters)
  }
  

}