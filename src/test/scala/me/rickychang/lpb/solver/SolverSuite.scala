package me.rickychang.lpb.solver

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import java.io.File
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.imageparser.MultiDeviceParser
import me.rickychang.lpb.board.GameBoard
import me.rickychang.lpb.board.OpponentDefended
import me.rickychang.lpb.board.PlayerDefended
import me.rickychang.lpb.board.OpponentOccupied
import me.rickychang.lpb.board.PlayerOccupied
import me.rickychang.lpb.board.Free
import me.rickychang.lpb.board.GreedyStrategyOrdering
import me.rickychang.lpb.replay.ReplayUtils

@RunWith(classOf[JUnitRunner])
class SolverSuite extends FunSuite {

  private val boardSolver = new BoardSolver(new WordDictionary("/dicts/testDict.txt"))
  private val parser = new MultiDeviceParser(new JavaOCRCharParser)

  test("iPhone 5 Solver test board 1") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board1.png"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findMoves(gameBoard, Set.empty[String], 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("ATTACHMENT", (5, -3)),
      ("ATTACHMENTS", (5, -3)),
      ("PENCHANT", (5, -2)),
      ("PENCHANTS", (5, -2)),
      ("PHANTAST", (5, -2))))
  }

  test("iPhone 5 Solver test board 2") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board2.png"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findMoves(gameBoard, Set.empty[String], 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("UNSTEADYING", (11, -4)),
      ("NUTMEGGING", (10, -5)),
      ("UNDEAFING", (9, -5)),
      ("MAGNITUDES", (10, -3)),
      ("ANGUISHED", (9, -3))))
  }

  test("iPhone 5 Solver test board 3") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board3.png"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findMoves(gameBoard, Set.empty[String], 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("BETTER", (6, 0)),
      ("TRIO", (4, 0)),
      ("COPE", (4, 0)),
      ("BEE", (3, 0)),
      ("POT", (3, 0))))
  }

  test("iPhone 5 Solver twitter board 1") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findMoves(gameBoard, Set.empty[String], 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("WALLYDRAGS", (9, -7)),
      ("KARYOPLASMS", (8, -8)),
      ("VOLKSRAADS", (9, -7)),
      ("GLASSWORK", (8, -7)),
      ("GALLOWGLASS", (8, -7))))
  }

  test("Winning word rankings") {
    val b = GameBoard("AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB Aw Er Br Er Tr Tr Rr")
    val wordsAndTiles = boardSolver.findMoves(b, Set.empty[String], 5)
    val wordsToPlay = wordsAndTiles.map {
      case (w, t) => {
        val (p, o) = boardSolver.scoreDeltas(t)
        val a = if (boardSolver.isWinningMove(b, t)) "*" else ""
        "%s%s (+%d,%d)".format(w, a, p, o)
      }
    }.mkString(", ")
    assert(wordsToPlay == "BAT* (+3,-2), BETTER (+6,-6), BEE (+3,-3)")
  }

  test("TileState ordering") {
    val stateList = List(OpponentDefended, Free, OpponentOccupied, PlayerOccupied, PlayerDefended)
    assert(stateList.sortWith(GreedyStrategyOrdering.gt(_, _)) ==
      List(Free, OpponentOccupied, PlayerOccupied, PlayerDefended, OpponentDefended))
    assert(List(OpponentDefended, Free, OpponentOccupied, PlayerOccupied, Free).sortWith(GreedyStrategyOrdering.gt(_, _)) ==
      List(Free, Free, OpponentOccupied, PlayerOccupied, OpponentDefended))
  }

  test("Played words direct input") {
    // based on iPhone 5 Solver twitter board 1
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findMoves(gameBoard, Set("GLASSWORK", "WALLYDRAGS"), 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(
      ("KARYOPLASMS", (8, -8)),
      ("VOLKSRAADS", (9, -7)),
      ("GALLOWGLASS", (8, -7))))
  }

  test("Played words parsed input") {
    // based on iPhone 5 Solver twitter board 1
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val replayJson = """
      {"players" : [{"username" : "Opponent"},
                    {"username" : "Player"}],
       "letters" : "ABCDEFGHIJKLMNOPQRSTUVWXY",
       "perspective" : 1.0,
       "columns" : 5.0,
       "turns" : [{"w" : "GLASSWORK", "t" : 0.0},
                  {"w" : "WALLYDRAGS", "t" : 1.0}]}
    """
    val wordsToPlay = boardSolver.findMoves(gameBoard, ReplayUtils.playedWords(replayJson), 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    print(replayJson)
    print(ReplayUtils.playedWords(replayJson))
    assert(wordsToPlay == List(
      ("KARYOPLASMS", (8, -8)),
      ("VOLKSRAADS", (9, -7)),
      ("GALLOWGLASS", (8, -7))))
  }
}
