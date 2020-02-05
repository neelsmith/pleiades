package edu.holycross.shot.pleiades

import scala.io._

import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter

case class PleiadesData(pleiadesNum : BigDecimal) extends LogSupport {

  /** Base URL for Pleiades REST interface.
  val url: String = "https://pleiades.stoa.org/places"
*/

  /** Pleiades JSON data for a Pleiades ID.
  def json : String = {
    val url = "https://pleiades.stoa.org/places/" + pleiadesNum + "/json"
    Source.fromURL(url).getLines.toVector.mkString("\n")
  }
*/

  /** Optional Point object from Pleiades data.  Will be Some(Point)
  * if Pleiades has a parseable "reprPoint" property, or None otherwise.
  def pointOption: Option[Point] = {
    try {
      val res = json
      if (res.contains("reprP")) {
        val filt = res.split("reprP")
        val pt2 = filt(1).split("\n").toVector
        val s = pt2(1) + pt2(2)
        val v = s.replaceAll("[ ]+","").split(",").toVector
        Some(Point(v(0).toDouble,v(1).toDouble, pleiadesNum))

      } else {
        println("No reprPoint found for " + pleiadesNum)
        None
      }
    } catch {
      case e: Throwable => None
    }
  } */
}

object PleiadesData extends LogSupport {
  Logger.setDefaultLogLevel(LogLevel.INFO)
  /**
  def apply(pleiadesId: String): PleiadesData = {
    try {
      val n = pleiadesId.toInt
      PleiadesData(n)

    } catch {
      case e: Throwable => {
        throw new Exception("Unable to parse PleiadesData for ID " + pleiadesId)
      }
    }
  }
*/
  def parsePlacesFile(fName: String) : Vector[PleiadesPlace]= {
    val lines = Source.fromFile(fName).getLines.toVector
    val header = lines.head


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
    ok
  }
}
