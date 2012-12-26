package me.rickychang.lpb.solver

import javax.imageio.ImageIO
import java.io.File
import me.rickychang.lpb.imageparser.IPhone5BoardParser
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser
import scala.collection.mutable.MutableList

/**
 * Utility object for testing solver against local images
 */
object ImageSolverUtil {
  
  private val boardSolver = new BoardSolver(new WordDictionary)
  private val tileParser = new JavaOCRCharParser
  
  def findWords(imageFilePath: String, maxWords: Int): List[String] = {
    val boardImage = ImageIO.read(new File(imageFilePath))
    val imageParser = new IPhone5BoardParser(boardImage, tileParser, ColorHistogramTileStateParser)
    val wordsToPlay = boardSolver.findWords(imageParser.gameBoard, maxWords).map { 
      case (w, t) => val (p, o) = boardSolver.wordScore(t); "%s : +%d,%d".format(w, p, o) }
    wordsToPlay
  }

}