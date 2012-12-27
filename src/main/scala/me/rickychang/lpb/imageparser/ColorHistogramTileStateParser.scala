package me.rickychang.lpb.imageparser

import me.rickychang.lpb.board.TileState
import java.awt.image.BufferedImage

// TODO: Fold trait code into this object,
// we don't need multiple TileStateParser implementations
object ColorHistogramTileStateParser extends TileStateParser {

  def extractColor(tileImage: BufferedImage): TileState = {
    val domColor = new ColorHistogram(tileImage).dominantColor
    colorToStateMap(normalizeColor(domColor))
  }

}