package edu.holycross.shot.pleiades

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter




import scala.scalajs.js.annotation._


/** A downloadable release of the Pleiades names data set.
*
* @param places A Vector of [[PleiadesPlace]] records.
*/
@JSExportTopLevel("PleiadesPlaces") case class PleiadesPlaces(places: Vector[PleiadesPlace]) extends LogSupport {

  /** Retrieve a single PleiadesPlace record by Id, or None
  * if not found.
  *
  * @param pleiadesId Pleiades ID number for record to look up.
  */
  def lookup(pleiadesId: BigDecimal) : Option[PleiadesPlace] = {
    val matches = places.filter(_.pleiadesId == pleiadesId)
    matches.size match {
      case 1 => Some(matches(0))
      case 0 => {
        val msg = "No matches for id value " + pleiadesId
        warn(msg)
        None
      }
      case n: Int => {
        val msg = s"${n} matches for id value " + pleiadesId
        warn(msg)
        None
      }
    }
  }
}

object PleiadesPlaces extends LogSupport {
  Logger.setDefaultLogLevel(LogLevel.INFO)

  /** Parse records in a CEX-formatted dump of Pleiades places into
  * a [[PleiadesPlaces]] construction.
  *
  * @param fName Name of file with Pleiades CSV data.
  *
  */
  def parsePlacesCex(lines: Vector[String]) : PleiadesPlaces= {
    val data = lines.tail.filterNot(_.contains("errata"))
    info("Parsing " + data.size + " pleiades place records.")

    val places = for (record <- data) yield {
      PleiadesPlace(record)
    }
    val ok = places.filterNot(_.pleiadesId == -1)
    val failures = places.filter(_.pleiadesId == -1)
    for (f <- failures) {
      warn("Failed on " + f.description)
    }
    if (failures.nonEmpty){
      info("Failed on " + failures.size + " records.")
    }
    info("Successfully parsed " + ok.size + " places records.")
    PleiadesPlaces(ok)
  }

}
