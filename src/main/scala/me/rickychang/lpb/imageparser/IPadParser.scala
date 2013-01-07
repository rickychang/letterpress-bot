package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import scala.collection.immutable.List
import me.rickychang.lpb.board.GameBoard
import ParserUtil.getResizedDimension
import ParserUtil.getTileWidthHeight
import ParserUtil.resizeImage
import me.rickychang.lpb.board.TileState

class IPadParser(cParser: TileCharParser) extends IOSDeviceParser {

  val screenshotAspectRatio = 0.75f

  val charParser = cParser

  val boardXStartPercent = 0.12890625f

  val boardXEndPercent = 0.87109375f

  val boardYStartPercent = 0.345703125f

  val boardYEndPercent = 0.90234375f

}
