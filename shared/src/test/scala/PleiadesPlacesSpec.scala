package edu.holycross.shot.pleiades

import org.scalatest.FlatSpec

class PleiadesPlacesSpec extends FlatSpec {

  val cex = """authors#bbox#connectsWith#created#creators#currentVersion#description#extent#featureTypes#geoContext#hasConnectionsWith#id#locationPrecision#maxDate#minDate#modified#path#reprLat#reprLatLong#reprLong#tags#timePeriods#timePeriodsKeys#timePeriodsRange#title#uid
  Becker, J., T. Elliott#13.4119837, 42.082885, 13.4119837, 42.082885#413005#2016-11-04T16:36:09Z#jbecker, thomase#1#The post-Roman settlement at Alba Fucens became an administrative center ca. A.D. 870 and remained significant as a center in Marsica until ca. 1143. The area was later subsumed by the Duchy of Spoleto.#{"type": "Point", "coordinates": [13.4119837, 42.082885]}#settlement###48210385#precise#1453#640#2016-11-08T21:58:28Z#/places/48210385#42.082885#42.082885,13.4119837#13.4119837##M#mediaeval-byzantine#640.0,1453.0#Borgo Medievale#ece5760c4c6d42c1a331aad543c4ecc4
  Becker, J., T. Elliott#11.6285463, 42.4193742, 11.6285463, 42.4193742#413393#2016-11-04T16:39:09Z#jbecker, thomase#2#A major urban sanctuary at Vulci with a long period of use, stretching from the archaic period into the Roman period.#{"type": "Point", "coordinates": [11.6285463, 42.4193742]}#temple-2###48210386#precise#300#-750#2016-12-05T11:47:10Z#/places/48210386#42.4193742#42.4193742,11.6285463#11.6285463#sanctuary, extant remains, temple#ACHR#archaic,classical,hellenistic-republican,roman#-750.0,300.0#Tempio Grande at Vulci#4e06898f2de74dbc9f3a3bdba6d74ba2""".split("\n").toVector


  "A PleiadesPlaces collection" should "be constructable from a delimited text record" in  {
    val places = PleiadesPlaces.parsePlacesCex(cex)
    places match {
      case pn: PleiadesPlaces => assert(true)
      case _ => fail("Did not create a PleiadesPlaces from cex.")
    }
  }

  it should "lookup records by ID value" in {
    val places = PleiadesPlaces.parsePlacesCex(cex)
    val id = BigDecimal("48210385")
    val placeOption = places.lookup(id)
    placeOption match {
      case None => fail("Did not find a match.")
      case p : Some[PleiadesPlace] => assert(p.get.pleiadesId == id)
    }
  }


}
