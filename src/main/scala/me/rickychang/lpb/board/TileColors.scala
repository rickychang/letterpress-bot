package me.rickychang.lpb.board

import java.awt.Color

import scala.math._

object TileColors {

  val LightFreeColors = Set(new Color(232,231,228), new Color(230,229,226))
  val FreeColors = LightFreeColors

  val LightPlayerOccupiedColors = Set(new Color(120,200,245))
  val DarkPlayerOccupiedColors = Set(new Color(232,108,231))
  val PlayerOccupiedColors = LightPlayerOccupiedColors ++ DarkPlayerOccupiedColors

  val LightPlayerDefendedColors = Set(new Color(56,162,251))
  val DarkPlayerDefendedColors = Set(new Color(230,89,226))
  val PlayerDefendedColors = LightPlayerDefendedColors ++ DarkPlayerDefendedColors
  
  val LightOpponentOccupiedColors = Set(new Color(240,152,140))
  val DarkOpponentOccupiedColors = Set(new Color(170,140,142))
  val OpponentOccupiedColors = LightOpponentOccupiedColors ++ DarkOpponentOccupiedColors
  
  val LightOpponentDefendedColors = Set(new Color(233,63,51))
  val DarkDefendedColors = Set(new Color(87,56,47))
  val OpponentDefendedColors = LightOpponentDefendedColors ++ DarkDefendedColors

  val LightColors = LightFreeColors ++
    LightPlayerOccupiedColors ++
    LightPlayerDefendedColors ++
    LightOpponentOccupiedColors ++
    LightOpponentDefendedColors

  val DarkColors = DarkPlayerOccupiedColors ++
    DarkPlayerDefendedColors ++
    DarkOpponentOccupiedColors ++
    DarkDefendedColors
  
  val AllColors = LightColors ++ DarkColors

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