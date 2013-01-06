package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage

trait TileCharParser {
  def extractChar(tileImage: BufferedImage): Char
}
