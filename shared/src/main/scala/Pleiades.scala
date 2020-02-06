package edu.holycross.shot.pleiades

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.scalajs.js.annotation._


/** A downloadable release of the Pleiades names data set.
*
* @param places A Vector of [[PleiadesPlace]] records.
*/
@JSExportTopLevel("Pleiades") case class Pleiades(
  places: PleiadesData,
  names: PleiadesNames
) extends LogSupport {
}
