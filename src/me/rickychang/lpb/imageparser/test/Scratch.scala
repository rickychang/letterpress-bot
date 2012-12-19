package me.rickychang.lpb.imageparser.test

import java.awt.image.BufferedImage
import scala.collection.mutable.StringBuilder
import java.io.File
import java.io.IOException
import scala.collection.mutable.ListBuffer
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import me.rickychang.lpb.imageparser.ParserUtil.getResizedDimension
import me.rickychang.lpb.imageparser.ParserUtil.getTileWidthHeight
import me.rickychang.lpb.imageparser.ParserUtil._
import me.rickychang.lpb.imageparser.IPhone5BoardParser
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader
import net.sourceforge.javaocr.scanner.PixelImage
import java.util.HashMap
import java.util.ArrayList
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage
import java.awt.Image
import java.awt.Frame
import me.rickychang.lpb.imageparser.ParserUtil
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import java.awt.image.DataBufferInt
import java.awt.Color
import java.awt.Rectangle
import me.rickychang.lpb.imageparser.ColorHistogram
import me.rickychang.lpb.board.TileColors
import me.rickychang.lpb.imageparser.TileStateParser
import me.rickychang.lpb.board.Free
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser
import me.rickychang.lpb.solver.WordDictionary
import me.rickychang.lpb.solver.SolverUtil
import me.rickychang.lpb.board.GameBoard
import me.rickychang.lpb.solver.WordSolver
import util.control.Breaks._

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
  
  test("Scratch") {
    val tileParser: JavaOCRCharParser = new JavaOCRCharParser("images/training/light")
    val img: BufferedImage = ImageIO.read(new File("images/test/iphone5-twitter-board2.jpg"))
    val imageParser = new IPhone5BoardParser(img, tileParser, ColorHistogramTileStateParser)
    val board = new GameBoard(imageParser.boardTiles)
    val solver = new WordSolver(board)
    val tiles = board.tiles
    println(imageParser.toString)
    val tileCombos = (2 to 18).map(tiles.combinations(_)).fold(Iterator.empty) { _ ++ _ }
    val dict = new WordDictionary("resources/lpWords.txt")
    println("dictionary loaded.")
    val s = System.currentTimeMillis
    var count = 0
    println(dict.wordsWithOccurrences.size)
    
    for (w <- dict.wordsWithOccurrences) {
      count += 1
      if (count % 1000 == 0) println(count)
    }
    println(count)
    println("iterating over words: %d".format(System.currentTimeMillis - s))
  }

}