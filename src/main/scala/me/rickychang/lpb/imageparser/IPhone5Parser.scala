package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import scala.collection.immutable.List
import me.rickychang.lpb.board.GameBoard
import ParserUtil.getResizedDimension
import ParserUtil.getTileWidthHeight
import ParserUtil.resizeImage
import me.rickychang.lpb.board.TileState

class IPhone5Parser(cParser: TileCharParser) extends IOSDeviceParser {

  val screenshotAspectRatio = 0.56338028169014f

  val charParser = cParser

  val boardXStartPercent = 0.0f

  val boardXEndPercent = 1.0f

  val boardYStartPercent = 0.43661971830986f

  val boardYEndPercent = 1.0f

}
