package me.rickychang.lpb.imageparser

import java.awt.image.BufferedImage
import me.rickychang.lpb.board.TileState
import java.awt.Color
import me.rickychang.lpb.board.TileColors._
import me.rickychang.lpb.board._


trait TileStateParser {

  val colorToStateMap: Map[Color, TileState] = {
    def genPair(s: TileState) = (c: Color) => (c, s)
    val colorStatePairs = FreeColors.map(genPair(new Free)) ++
      PlayerOccupiedColors.map(genPair(new PlayerOccupied)) ++
      PlayerDefendedColors.map(genPair(new PlayerDefended)) ++
      OpponentOccupiedColors.map(genPair(new OpponentOccupied)) ++
      OpponentDefendedColors.map(genPair(new OpponentDefended))
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