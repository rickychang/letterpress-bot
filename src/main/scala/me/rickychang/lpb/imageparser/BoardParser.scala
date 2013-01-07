package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage

import me.rickychang.lpb.board.BoardThemeType.BoardThemeType
import me.rickychang.lpb.board.GameBoard

trait BoardParser {

  val charParser: TileCharParser

  def extractBoardImage(screenshot: BufferedImage): BufferedImage

  def getThemeType(screenshot: BufferedImage): BoardThemeType

  def extractTileImages(screenshot: BufferedImage): List[BufferedImage]

  def parseGameBoard(screenshot: BufferedImage): GameBoard

}
