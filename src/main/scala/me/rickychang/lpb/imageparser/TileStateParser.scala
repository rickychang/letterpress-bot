package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import me.rickychang.lpb.board.TileState
import java.awt.Color
import me.rickychang.lpb.board.TileColors._
import me.rickychang.lpb.board._

// TODO add support for light and dark board themes
trait TileStateParser {

  val colorToStateMap: Map[Color, TileState] = {
    def genPair(s: TileState) = (c: Color) => (c, s)
    val colorStatePairs = FreeColors.map(genPair(Free)) ++
      PlayerOccupiedColors.map(genPair(PlayerOccupied)) ++
      PlayerDefendedColors.map(genPair(PlayerDefended)) ++
      OpponentOccupiedColors.map(genPair(OpponentOccupied)) ++
      OpponentDefendedColors.map(genPair(OpponentDefended))
    colorStatePairs toMap
  }
  
  /**
   * Normalize color by returning most similar canonical tile background color.
   */
  def normalizeColor(c: Color): Color = {
    AllColors.map(tileColor => (tileColor, colorDiff(c, tileColor))).minBy(_._2)._1
  }
  
  def extractColor(tileImage: BufferedImage): TileState 

}