package me.rickychang.lpb.imageparser

import java.io.File
import javax.imageio.ImageIO

import me.rickychang.lpb.board.BoardThemeType._
import java.awt.image.BufferedImage
import me.rickychang.lpb.board.GameBoard
import java.awt.Color
import me.rickychang.lpb.board.BoardBackgroundColors
import me.rickychang.lpb.board.BoardThemeType
import ParserUtil.getResizedDimension
import ParserUtil.getTileWidthHeight
import ParserUtil.resizeImage
import me.rickychang.lpb.board.TileState

trait IOSDeviceParser extends BoardParser {

  val charParser: TileCharParser

  val screenshotAspectRatio: Float
  
  val boardXStartPercent: Float

  val boardXEndPercent: Float

  val boardYStartPercent: Float

  val boardYEndPercent: Float

  /**
   * Crop screenshot to include just board tiles
   */
  def extractBoardImage(screenshot: BufferedImage): BufferedImage = {
    val boardXStartOffset = (screenshot.getWidth * boardXStartPercent).toInt
    val boardXEndOffset = (screenshot.getWidth * boardXEndPercent).toInt
    val boardYStartOffset = (screenshot.getHeight * boardYStartPercent).toInt
    val boardYEndOffset = (screenshot.getHeight * boardYEndPercent).toInt
    val leftPadding = boardXStartOffset
    val rightPadding = screenshot.getWidth - boardXEndOffset
    val topPadding = boardYStartOffset
    val bottomPadding = screenshot.getHeight - boardYEndOffset
    val origBoardWidth = screenshot.getWidth - leftPadding - rightPadding
    val origBoardHeight = screenshot.getHeight - topPadding - bottomPadding
    val origBoard = screenshot.getSubimage(boardXStartOffset, boardYStartOffset, origBoardWidth, origBoardHeight)
    // resize image to make tile extraction math cleaner
    resizeImage(origBoard, getResizedDimension(origBoardWidth), getResizedDimension(origBoardHeight))
  }

  /**
   * Infer board theme type (Dark or Light)
   */
  def getThemeType(screenshot: BufferedImage): BoardThemeType = {
    val c = new Color(screenshot.getRGB(0, 0))
    val norm = ParserUtil.normalizeColor(BoardBackgroundColors.AllColors, c)
    if (BoardBackgroundColors.LightColors contains norm) BoardThemeType.Light
    else BoardThemeType.Dark
  }

  /**
   * Extract individual tiles as list of BufferedImage objects.  Tiles
   * are ordered by rows, left to right.
   */
  def extractTileImages(screenshot: BufferedImage): List[BufferedImage] = {
    val bImage = extractBoardImage(screenshot)
    val bWidth = bImage.getWidth
    val bHeight = bImage.getHeight
    val tWidth = getTileWidthHeight(bWidth)
    val tHeight = getTileWidthHeight(bHeight)
    val tiles = for {
      y <- 0 until bHeight by tHeight
      x <- 0 until bWidth by tWidth
    } yield ParserUtil.resizeImage(bImage.getSubimage(x, y, tWidth, tHeight), 128, 128)
    tiles.zipWithIndex.map { e =>
      ImageIO.write(e._1, "jpg", new File("/Users/ricky/Desktop/" + e._2 + ".jpg"))
    }
    tiles.toList
  }

  def parseGameBoard(screenshot: BufferedImage): GameBoard = {
    val tiles = extractTileImages(screenshot)
    val themeType = getThemeType(screenshot)
    val tileChars: List[Char] = tiles.map { charParser.extractChar(_) }
    val tileStates: List[TileState] = tiles.map { TileStateParser.getState(themeType, _) }
    GameBoard((tileChars, tileStates, tileChars.indices).zipped.toList)
  }

}