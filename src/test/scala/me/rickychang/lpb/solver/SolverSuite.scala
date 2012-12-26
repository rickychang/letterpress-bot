package me.rickychang.lpb.solver

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import java.io.File
import me.rickychang.lpb.imageparser.IPhone5BoardParser
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.imageparser.ColorHistogramTileStateParser

@RunWith(classOf[JUnitRunner])
class SolverSuite extends FunSuite {
  
  private val boardSolver = new BoardSolver(new WordDictionary("/dicts/testDict.txt"))

  test("iPhone 5 Solver test board 1") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board1.png"))
    val gameBoard = new IPhone5BoardParser(boardImage, new JavaOCRCharParser, ColorHistogramTileStateParser).gameBoard
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map { 
      case (w, t) => (w, boardSolver.wordScore(t)) }
    assert(wordsToPlay == List(("ATTACHMENT",(5,-4)),
    						   ("ATTACHMENTS",(5,-4)),
    						   ("PENCHANT",(5,-4)),
    						   ("PENCHANTS",(5,-4)),
    						   ("PHANTAST",(5,-4))))
  }  
  
  test("iPhone 5 Solver test board 2") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board2.png"))
    val gameBoard = new IPhone5BoardParser(boardImage, new JavaOCRCharParser, ColorHistogramTileStateParser).gameBoard
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map { 
      case (w, t) => (w, boardSolver.wordScore(t)) }
    assert(wordsToPlay == List(("UNSTEADYING",(11,-5)),
    						   ("MAGNITUDES",(10,-5)),
    						   ("NUTMEGGING",(10,-5)),
    						   ("UNDEAFING",(9,-6)),
    						   ("ANGUISHED",(9,-5))))
  }
  
  test("iPhone 5 Solver test board 3") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board3.png"))
    val gameBoard = new IPhone5BoardParser(boardImage, new JavaOCRCharParser, ColorHistogramTileStateParser).gameBoard
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map { 
      case (w, t) => (w, boardSolver.wordScore(t)) }
    assert(wordsToPlay == List(("BETTER",(6,0)),
                               ("TRIO",(4,0)),
                               ("COPE",(4,0)),
                               ("BEE",(3,0)),
                               ("POT",(3,0))))
  }  
  
    test("iPhone 5 Solver twitter board 1") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val gameBoard = new IPhone5BoardParser(boardImage, new JavaOCRCharParser, ColorHistogramTileStateParser).gameBoard
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map { 
      case (w, t) => (w, boardSolver.wordScore(t)) }
    assert(wordsToPlay == List(("WALLYDRAGS",(9,-8)),
    						   ("GLASSWORK",(8,-8)),
    						   ("KARYOPLASMS",(8,-8)),
    						   ("VOLKSRAADS",(9,-7)),
    						   ("GALLOWGLASS",(8,-7))))
  }

}