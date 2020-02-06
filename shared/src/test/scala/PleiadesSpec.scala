package edu.holycross.shot.pleiades

import org.scalatest.FlatSpec

class PleiadesSpec extends FlatSpec {

  val namesCex = """authors#bbox#created#creators#currentVersion#description#extent#id#locationPrecision#maxDate#minDate#modified#nameAttested#nameLanguage#nameTransliterated#path#pid#reprLat#reprLatLong#reprLong#tags#timePeriods#timePeriodsKeys#timePeriodsRange#title#uid
  Spann, P., R. Warner, R. Talbert, T. Elliott, S. Gillies#-3.606772, 39.460299, -3.606772, 39.460299#2010-09-24T19:02:22Z#P.O. Spann###{"type": "Point", "coordinates": [-3.606772, 39.460299]}#consabura#precise#640#-330#2011-09-05T20:57:22Z###Consabura#/places/265876/consabura#/places/265876#39.460299#39.460299,-3.606772#-3.606772##HRL#hellenistic-republican,roman,late-antique#-330.0,640.0#Consabura#fc0ee157ff11ce6d2e72cd7c5df06fee
  Spann, P., R. Warner, R. Talbert, T. Elliott, S. Gillies#-3.606772, 39.460299, -3.606772, 39.460299#2010-09-24T19:02:22Z#P.O. Spann###{"type": "Point", "coordinates": [-3.606772, 39.460299]}#consabrum#precise#640#-330#2011-09-05T20:57:22Z###Consabrum#/places/265876/consabrum#/places/265876#39.460299#39.460299,-3.606772#-3.606772##HRL#hellenistic-republican,roman,late-antique#-330.0,640.0#Consabrum#e2b8756302fb62ff0e301710c265a3e6""".split("\n").toVector

  val placesCex = """authors#bbox#connectsWith#created#creators#currentVersion#description#extent#featureTypes#geoContext#hasConnectionsWith#id#locationPrecision#maxDate#minDate#modified#path#reprLat#reprLatLong#reprLong#tags#timePeriods#timePeriodsKeys#timePeriodsRange#title#uid
  Becker, J., T. Elliott#13.4119837, 42.082885, 13.4119837, 42.082885#413005#2016-11-04T16:36:09Z#jbecker, thomase#1#The post-Roman settlement at Alba Fucens became an administrative center ca. A.D. 870 and remained significant as a center in Marsica until ca. 1143. The area was later subsumed by the Duchy of Spoleto.#{"type": "Point", "coordinates": [13.4119837, 42.082885]}#settlement###48210385#precise#1453#640#2016-11-08T21:58:28Z#/places/48210385#42.082885#42.082885,13.4119837#13.4119837##M#mediaeval-byzantine#640.0,1453.0#Borgo Medievale#ece5760c4c6d42c1a331aad543c4ecc4
  Becker, J., T. Elliott#11.6285463, 42.4193742, 11.6285463, 42.4193742#413393#2016-11-04T16:39:09Z#jbecker, thomase#2#A major urban sanctuary at Vulci with a long period of use, stretching from the archaic period into the Roman period.#{"type": "Point", "coordinates": [11.6285463, 42.4193742]}#temple-2###48210386#precise#300#-750#2016-12-05T11:47:10Z#/places/48210386#42.4193742#42.4193742,11.6285463#11.6285463#sanctuary, extant remains, temple#ACHR#archaic,classical,hellenistic-republican,roman#-750.0,300.0#Tempio Grande at Vulci#4e06898f2de74dbc9f3a3bdba6d74ba2
  Spann, P., DARMC, R. Talbert, S. Gillies, R. Warner, J. Becker, T. Elliott#-3.606772, 39.460299, -3.606772, 39.460299##2010-09-24T19:02:22Z#P.O. Spann#14#An ancient settlement, likely of Celtic origin, located near Consuegra south of Toledo.#{"type": "Point", "coordinates": [-3.606772, 39.460299]}#settlement#Consuegra##265876#precise#640#-330#2016-03-11T16:57:09Z#/places/265876#39.460299#39.460299,-3.606772#-3.606772#dare:ancient=1, dare:major=1, dare:feature=major settlement#HRL#hellenistic-republican,roman,late-antique#-330.0,640.0#Consabura/Consabrum#3fb26862377912da0f866fc310bcaf0c""".split("\n").toVector

  val places = PleiadesPlaces.parsePlacesCex(placesCex)
  val names = PleiadesNames.parseNamesCex(namesCex)


  "A Pleiades collection" should "be constructable" in {
    val pleiades = Pleiades(places, names )
    pleiades match {
      case pl : Pleiades => assert(true)
      case _ => fail("Did not consturct a Pleiades collection")
    }
  }
  it should "lookup an Option for records by ID" in {
    val pleiades = Pleiades(places, names)
    val id = BigDecimal("48210385")
    val placeOption = pleiades.lookup(id)
    placeOption match {
      case None => fail("Did not find matching place.")
      case place: Some[PleiadesPlace] => {
        assert(place.get.pleiadesId == id)
      }
    }

  }

  it should "look up a Vector of places from a Vector of ids" in {
    val pleiades = Pleiades(places, names)
    val ids = Vector(BigDecimal("48210385"), BigDecimal("265876"))
    val matches = pleiades.lookup(ids)
    val expected = 2
    assert(matches.size  == expected)
  }


  it should "lookup a Vector of records by name" in  {
    val pleiades = Pleiades(places, names)
    val name = "Consabura"
    val results = pleiades.lookupName(name)
    assert(results.size == 1)
    val expectedId = BigDecimal("265876")
    assert(results(0).pleiadesId == expectedId)
  }
}
