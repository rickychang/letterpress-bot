package me.rickychang.lpb.scratch

import java.awt.image.BufferedImage
import java.io.File
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import me.rickychang.lpb.board.GameBoard
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser
import me.rickychang.lpb.imageparser.IPhone5BoardParser
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.solver.WordDictionary
import me.rickychang.lpb.solver.BoardSolver
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScratchSuite extends FunSuite {
  
//  test("Scratch") {
//	  println("creating dict" )
//	  var s = System.currentTimeMillis
//	  val dict = new WordDictionary("resources/lpWords.txt")
//	  println("done creating dict: %d".format(System.currentTimeMillis - s))
//	  val testWord = "cat"
//	  val testOccurrences = dict.wordOccurrences(testWord)
//	  println("sleeping 10")
//	  Thread.sleep(10000)
//	  println(testOccurrences)
//	  s = System.currentTimeMillis
//	  val matchingWords = dict.getWords(testOccurrences)
//	  println("done lookup 1 dict: %d".format(System.currentTimeMillis - s))
//
//	  println(matchingWords)
//	  s = System.currentTimeMillis
//	  println(dict.getWords(dict.wordOccurrences("ABOLITIONISM")))
//	  println("done lookup 2 dict: %d".format(System.currentTimeMillis - s))
//
//  }

//  test("Scratch") {
//    def combinations(occurrences:  List[(Char, Int)]): List[ List[(Char, Int)]] = {
//      def validFrequencies(freq: (Char, Int)) = {
//        val count = freq._2
//        for (i <- 1 to count) yield (freq._1, i)
//      }
//      def comboHelper(o:  List[(Char, Int)]): List[ List[(Char, Int)]] = {
//        if (o.isEmpty) List(List()) else {
//          val tailSubsets = comboHelper(o.tail)
//          (for {
//            subset <- tailSubsets
//            o <- validFrequencies(o.head)
//          } yield o :: subset) ++ tailSubsets
//        }
//      }
//      comboHelper(occurrences)
//    }
//    val dict = new WordDictionary("resources/lpWords.txt")
//    val testOccurrences = SolverUtil.wordOccurrences("ABCDEFG")
//    println(combinations(testOccurrences))
//  }
  //TODO: Turn this into a unit test for end-to-end board solving
  test("Scratch") {
    val tileParser: JavaOCRCharParser = new JavaOCRCharParser
    val img: BufferedImage = ImageIO.read(getClass.getResource("/images/test/georges.png"))
    val imageParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
    val board = new GameBoard(imageParser.boardTiles)
    println(imageParser.toString)
    val dict = new WordDictionary
    println("dictionary loaded.")
    val solver = new BoardSolver(dict)
    val moves = solver.findWords(board, 10)
    for (m <- moves) {
      val (word, tiles) = m
      println("%s : %s".format(word, solver.wordScore(tiles)))
    }
  }

}