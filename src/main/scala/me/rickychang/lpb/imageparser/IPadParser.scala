package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import scala.collection.immutable.List
import me.rickychang.lpb.board.GameBoard
import ParserUtil.getResizedDimension
import ParserUtil.getTileWidthHeight
import ParserUtil.resizeImage
import me.rickychang.lpb.board.TileState

class IPadParser(cParser: TileCharParser) extends ScreenshotParser {

  val ScreenshotAspectRatio = 0.75f

  val charParser = cParser  

  val boardXOffsetRatio = 0.12890625f  
  
  val boardYStartOffsetRatio = 0.345703125f
  
  val boardYEndOffsetRatio = 0.90234375f

}