package edu.holycross.shot.pleiades

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter




import scala.scalajs.js.annotation._


/** A downloadable release of the Pleiades names data set.
*
* @param places A Vector of [[PleiadesPlace]] records.
*/
@JSExportTopLevel("PleiadesData") case class PleiadesData(places: Vector[PleiadesPlace]) extends LogSupport {
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

object PleiadesData extends LogSupport {
  Logger.setDefaultLogLevel(LogLevel.INFO)

  /** Parse records in a CEX-formatted dump of Pleiades places into
  * a [[PleiadesData]] construction.
  *
  * @param fName Name of file with Pleiades CSV data.
  *
  */
  def parsePlacesCex(lines: Vector[String]) : PleiadesData= {
    val data = lines.tail.filterNot(_.contains("errata"))
    info("Parsing " + data.size + " pleiades records.")
    val places = for (record <- data) yield {
      val cols = record.replaceAll("##","# #").split("#").toVector
      val description = cols(6)

      if (cols.size != 26) {
        warn("ERROR IN INPUT LINE: " + cols.size + " columns")
        PleiadesPlace(-1, "ERROR ON INPUT: " + cols, None)

      } else {
        if (cols(16).contains("/places") == false) {
            warn("Ignoring bad ID value " + cols(16))
            PleiadesPlace(-1, "Bad ID value: " + cols(16), None)
        }
        val numeric =  cols(16).replaceAll("/places/", "")
        debug("Interpret ID "+ numeric)
        val id = BigDecimal(numeric)

        val geoPoint = try {
          if (cols(17).trim.isEmpty) {
            // silently ignore if no lon/lat
            None
          } else {
            val lat = BigDecimal(cols(17).trim)
            val lon = BigDecimal(cols(19).trim)
            Some(GeoPoint(lat, lon))
          }
        } catch {
          case t: Throwable => {
            warn("FAILED: src " + cols(17) + ":" + cols(19))
            warn(t.toString)
            None
          }
        }
        PleiadesPlace(id, description, geoPoint)
      }
    }
    val ok = places.filterNot(_.pleiadesId == -1)
    val failures = places.filter(_.pleiadesId == -1)
    for (f <- failures) {
      warn("Failed on " + f.description)
    }
    info("Failed on " + failures.size + " records.")
    info("Successfully read " + ok.size + " records.")
    PleiadesData(ok)
  }

}
