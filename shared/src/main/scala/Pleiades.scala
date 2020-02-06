package edu.holycross.shot.pleiades

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.scalajs.js.annotation._


/** A downloadable release of the Pleiades names data set.
*
* @param places A Vector of [[PleiadesPlace]] records.
*/
@JSExportTopLevel("Pleiades") case class Pleiades(
  places: PleiadesPlaces,
  names: PleiadesNames
) extends LogSupport {

  def lookup(id: BigDecimal): Option[PleiadesPlace] = {
    places.lookup(id)
  }

  def lookup(ids: Vector[BigDecimal]): Vector[PleiadesPlace] = {
    val matches = for (id <- ids) yield { places.lookup(id) }
    matches.flatten
  }



  def lookupName(name: String) : Vector[PleiadesPlace] = {
    val ids = names.idsForName(name)
    lookup(ids)
  }
}
