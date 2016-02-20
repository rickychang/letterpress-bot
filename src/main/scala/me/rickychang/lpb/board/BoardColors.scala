package me.rickychang.lpb.board

import java.awt.Color

import scala.math._

object TileColors {

  val LightFreeColors = 
    Set(new Color(232,231,228),
        new Color(230,229,226),
        new Color(247,217,229),
        new Color(244,215,227),
        new Color(217,223,209),
        new Color(215,220,207),
        new Color(243,228,191),
        new Color(241,226,189))
                            
  val DarkFreeColors = 
    Set(new Color(54,54,54),
        new Color(55,55,55),
        new Color(55,55,55))

  val FreeColors = LightFreeColors ++ DarkFreeColors

  val LightPlayerOccupiedColors = 
    Set(new Color(120,200,245),
        new Color(195,136,226),
        new Color(239,193,108),
        new Color(232,108,231))
                                      
  val DarkPlayerOccupiedColors = 
    Set(new Color(46,117,152),
        new Color(87,152,23),
        new Color(73,152,119))
  
  val PlayerOccupiedColors = LightPlayerOccupiedColors ++ DarkPlayerOccupiedColors

  val LightPlayerDefendedColors = 
    Set(new Color(56,162,251),
        new Color(144,82,253),
        new Color(242,155,51),
        new Color(230,89,227))

  val DarkPlayerDefendedColors = 
    Set(new Color(76,187,251),
        new Color(127,242,5),
        new Color(123,246,191))
  
  val PlayerDefendedColors = LightPlayerDefendedColors ++ DarkPlayerDefendedColors
  
  val LightOpponentOccupiedColors =
    Set(new Color(240,152,140),
        new Color(242,165,133),
        new Color(122,164,137),
        new Color(170,140,142))

  val DarkOpponentOccupiedColors =
    Set(new Color(152,58,48),
        new Color(153,50,123),
        new Color(142,39,77))

  val OpponentOccupiedColors = LightOpponentOccupiedColors ++ DarkOpponentOccupiedColors
  
  val LightOpponentDefendedColors = 
    Set(new Color(233,63,51),
        new Color(235,97,70),
        new Color(47,99,60),
        new Color(87,56,47))

  val DarkOpponentDefendedColors =
    Set(new Color(233,63,51),
        new Color(231,82,197),
    	new Color(233,67,104))
  
  val OpponentDefendedColors = LightOpponentDefendedColors ++ DarkOpponentDefendedColors

  val LightColors = LightFreeColors ++
    LightPlayerOccupiedColors ++
    LightPlayerDefendedColors ++
    LightOpponentOccupiedColors ++
    LightOpponentDefendedColors

  val DarkColors = DarkFreeColors ++
    DarkPlayerOccupiedColors ++
    DarkPlayerDefendedColors ++
    DarkOpponentOccupiedColors ++
    DarkOpponentDefendedColors
  
  val AllColors = LightColors ++ DarkColors 
}

object BoardBackgroundColors { 
  
  val LightColors = Set(new Color(240,239,236),
                        new Color(251,236,197),
                        new Color(224,230,216))
                        
  val DarkColors = Set(new Color(49,49,49))
  
  val AllColors =   LightColors ++ DarkColors
}

object BoardThemeType extends Enumeration {
  type BoardThemeType = Value
  val Light, Dark = Value
}