package me.rickychang.lpb.imageparser

import java.awt.Color
import java.awt.image.BufferedImage
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import me.rickychang.lpb.board.GameBoard

@RunWith(classOf[JUnitRunner])
class IPhone5ParsingSuite extends FunSuite {

  val tileParser = new JavaOCRCharParser
  val iPhone5Parser = new IPhone5Parser(tileParser)
  val iPadParser = new IPadParser(tileParser)
  val iPhone4Parser = new IPhone4Parser(tileParser)

  test("twitter board 1 parsing") {
    val img: BufferedImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val expected = GameBoard("FR LR HR Sr Ob Yr Kr Ar Mb Wr Fw Dw Rr Pb Vw Lb Lr Xr Xw Rb Ar Dw Gr Gw Sr")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("twitter board 2 parsing") {
    val img: BufferedImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board2.jpg"))
    val expected = GameBoard("Br Nb Eb Tr SR Sb Kb Pr Cb Nr SB Mb Tw Pw Hr Sb Nw Jw Tr Vw Fr Vw Pw Ab Ab")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("dark theme board 1") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board4-dark.png"))
    val expected = GameBoard("Ew Xw Jw Pw Vw Ow Cw Pw Zw Pw Rw Nw Xw Dw Mw Ww Zw Xw Ww Rw Yw Uw Ew Nw Rw")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("dark theme board 2") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board5-dark.png"))
    val expected = GameBoard("Xw Kw Xw Cw Uw Rw Lw Ww Zw Kw Ow Ow Gw Cw Mw Pw Ow Uw Yw Gw Nw Zr Or Or Jw")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("dark theme board 3") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board6-dark.png"))
    val expected = GameBoard("YB RB Ob Hr ER DB Bb Wr NR FR Xb Pb Hr Wr Ar Zr Nb Kb Lb Gb OR Nr Yb JB MB")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("dark theme board 4") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board7-dark.png"))
    val expected = GameBoard("YB RB Ob Hr ER DB Bb Wr NR FR Xb Pb Hr Wr Ar Zr Nb Kb Lb Gb OR Nr Yb JB MB")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("dark theme board 5") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board8-dark.png"))
    val expected = GameBoard("YB RB Ob Hr ER DB Bb Wr NR FR Xb Pb Hr Wr Ar Zr Nb Kb Lb Gb OR Nr Yb JB MB")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("alternative light theme board 1") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board9.png"))
    val expected = GameBoard("FR LR HR Sr Ob Yr KR Ar Mb Wr Fb Dr Rr Pb Vw Lb Lb Xr Xw Rb Ar Dw Gr Gb SB")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("alternative light theme board 2") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board10.png"))
    val expected = GameBoard("FR LR HR Sr Ob Yr KR Ar Mb Wr Fb Dr Rr Pb Vw Lb Lb Xr Xw Rb Ar Dw Gr Gb SB")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }

  test("alternative light theme board 3") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board11.png"))
    val expected = GameBoard("FR LR HR Sr Ob Yr KR Ar Mb Wr Fb Dr Rr Pb Vw Lb Lb Xr Xw Rb Ar Dw Gr Gb SB")
    assert(iPhone5Parser.getGameBoard(img) == expected)
  }
  
  test("iPad parser test 1") {

    val img = ImageIO.read(getClass.getResource("/images/test/ipad-test-board1.png"))    

    val expected = GameBoard("FR LR HR Sr Or YR KR Ar Mb Wb Fr Dr Rr Pb Vw Lb Lb Xr Xw Rr Ar Dw Gr Gr Sb")
    assert(iPadParser.getGameBoard(img) == expected)
  }

  test("iPhone 4 test 1") {
    val img = ImageIO.read(getClass.getResource("/images/test/iphone4-test-board1.png"))
    val expected = GameBoard("Ar IR Lr Pr Db Gw Or Fw Mr Yr Sr Tr Gw Zr Zr Db Xw Hr Dw Zw Xw Hr Ww Vw Rw")
    assert(iPhone4Parser.getGameBoard(img) == expected)
  }

  test("Non-screenshot test 1") {
    val img = ImageIO.read(getClass.getResource("/images/test/non-board-iphone5-1.jpg"))
    try {
      iPhone5Parser.getGameBoard(img)
      fail()
    } catch {
      case _: InvalidImageException =>
    }

  }

  test("Color diff") {
    val c1 = new Color(247, 153, 141)
    val c2 = new Color(247, 153, 141)
    val c3 = new Color(119, 200, 245)
    val c4 = new Color(120, 200, 245)
    assert(ParserUtil.colorDiff(c1, c2).toInt == 0)
    assert(ParserUtil.colorDiff(c1, c3).toInt == 171)
    assert(ParserUtil.colorDiff(c3, c4).toInt == 1)
  }

}