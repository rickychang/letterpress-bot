letterpress-bot
==============

Letterpress solver bot written in Scala.

## Try Me

Send tweets containing game screenshots to [@letterpressbot](https://twitter.com/letterpressbot).

## Features

* Parses game board images and infers tile characters and states
* Analyzes board to find playable words
* Ranks playable words by player and opponent score deltas
* Supports multiple iOS device resolutions (iPhone4, iPhone5, iPad)
* Supports multiple color themes

## Limitations
* No support for previously played words
* Current word ranking function is very simple
  * For every playable word, only consider one set of tiles to play that word
  * Selected set of tiles is greedily choosen to maximize free tiles claimed
