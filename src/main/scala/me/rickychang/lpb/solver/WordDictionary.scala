package me.rickychang.lpb.solver

import scala.io.Source
import scala.io.BufferedSource

import scala.collection.immutable.Seq

import me.rickychang.lpb.solver.SolverUtil._

class WordDictionary(inputWordFile: String = DefaultWordDict) {
  
  val wordHistograms: Seq[(String, LetterHistogram)] = {
    Source.fromURL(getClass.getResource(inputWordFile)).getLines.map(w => (w, wordHistogram(w))).toStream
  }
}
