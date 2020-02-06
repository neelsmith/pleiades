package edu.holycross.shot.pleiades


import scala.scalajs.js.annotation._



@JSExportTopLevel("GeoPoint")
case class GeoPoint(x: BigDecimal, y: BigDecimal) {

  override def toString = {
    x.toString + "," + y.toString
  }
}
