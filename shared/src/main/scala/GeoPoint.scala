package edu.holycross.shot.pleiades


import scala.scalajs.js.annotation._


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


@JSExportTopLevel("GeoPoint")
case class GeoPoint(x: BigDecimal, y: BigDecimal) extends LogSupport {

  override def toString = {
    x.toString + "," + y.toString
  }
}
