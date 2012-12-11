package me.rickychang.lpb.board

import java.awt.Color

import scala.math._

object TileColors {

  val LightFreeColors = Set(new Color(232,231,228), new Color(230,229,226))
  val FreeColors = LightFreeColors

  val LightPlayerOccupiedColors = Set(new Color(120,200,245))
  val PlayerOccupiedColors = LightPlayerOccupiedColors    

  val LightPlayerDefendedColors = Set(new Color(56,162,251))
  val PlayerDefendedColors = LightPlayerDefendedColors
  
  val LightOpponentOccupiedColors = Set(new Color(240,152,140))  
  val OpponentOccupiedColors = LightOpponentOccupiedColors
  
  val LightOpponentDefendedColors = Set(new Color(233,63,51))
  val OpponentDefendedColors = LightOpponentDefendedColors

  val LightColors = LightFreeColors ++
    LightPlayerOccupiedColors ++
    LightPlayerDefendedColors ++
    LightOpponentOccupiedColors ++
    LightOpponentDefendedColors
  
  val AllColors = LightColors

  /**
   * Compute Euclidean distance between to Color objects
   * using RGB values
   */
  def colorDiff(c1: Color, c2: Color): Double = {
    val rDiff = c1.getRed - c2.getRed
    val gDiff = c1.getGreen - c2.getGreen
    val bDiff = c1.getBlue - c2.getBlue
    sqrt(pow(rDiff,2) + pow(gDiff,2) + pow(bDiff,2))
  }
}