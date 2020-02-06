package edu.holycross.shot.pleiades



import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

import scala.scalajs.js.annotation._

@JSExportTopLevel("PleiadesPlace")
case class PleiadesPlace(
  pleiadesId: BigDecimal,
  description: String,
  pointOption: Option[GeoPoint]) extends LogSupport{
}

object PleiadesPlace extends LogSupport {

  /** Create a PleiadesPlace object from delimited-text source.
  *
  * @param record Delimited-text record.
  */
  def apply(record: String): PleiadesPlace = {
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
}
