package me.rickychang.lpb.imageparser

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

trait ScreenshotParser {
  
  val charParser: TileCharParser
  
  val boardXOffsetRatio: Float
  
  val boardYStartOffsetRatio: Float
  
  val boardYEndOffsetRatio: Float
  
  /**
   * Crop screenshot to include just board tiles
   */
  def extractBoardImage(screenshot: BufferedImage): BufferedImage = {
    val boardXOffset = (screenshot.getWidth * boardXOffsetRatio).toInt
    val boardYStartOffset = (screenshot.getHeight * boardYStartOffsetRatio).toInt
    val boardYEndOffset = (screenshot.getHeight * boardYEndOffsetRatio).toInt
    val origBoardWidth = screenshot.getWidth - 2*boardXOffset
    val topPadding = boardYStartOffset
    val bottomPadding = screenshot.getHeight - boardYEndOffset
    val origBoardHeight = screenshot.getHeight - topPadding - bottomPadding
    val finalBoardWidth = getResizedDimension(origBoardWidth)
    val finalBoardHeight = getResizedDimension(origBoardHeight)
    val origBoard = screenshot.getSubimage(boardXOffset, boardYStartOffset, origBoardWidth, origBoardHeight)
    resizeImage(origBoard, finalBoardWidth, finalBoardHeight)
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
  
  def getTileImages(screenshot: BufferedImage): List[BufferedImage] = {
    val boardImage = extractBoardImage(screenshot)
    val boardWidth = boardImage.getWidth
    val boardHeight = boardImage.getHeight
    val tileWidth = getTileWidthHeight(boardWidth)
    val tileHeight = getTileWidthHeight(boardHeight)
    val tiles = for {
      y <- 0 until boardHeight by tileHeight
      x <- 0 until boardWidth by tileWidth
    } yield boardImage.getSubimage(x, y, tileWidth, tileHeight) 
    tiles.toList
  }
  
  def getGameBoard(screenshot: BufferedImage): GameBoard = {
    val tiles = getTileImages(screenshot)
    val themeType = getThemeType(screenshot)
    val tileChars: List[Char] = tiles.map{ charParser.extractChar(_) }
    val tileStates: List[TileState] = tiles.map{ TileStateParser.extractState(themeType, _) }
    //TODO: add factory method to companion object
    new GameBoard((tileChars, tileStates, tileChars.indices).zipped.toList)
  }
  
}