package edu.holycross.shot.pleiades

import org.scalatest.FlatSpec

class PleiadesNamesSpec extends FlatSpec {

  val cex = """authors#bbox#created#creators#currentVersion#description#extent#id#locationPrecision#maxDate#minDate#modified#nameAttested#nameLanguage#nameTransliterated#path#pid#reprLat#reprLatLong#reprLong#tags#timePeriods#timePeriodsKeys#timePeriodsRange#title#uid
  Spann, P., R. Warner, R. Talbert, T. Elliott, S. Gillies#-3.606772, 39.460299, -3.606772, 39.460299#2010-09-24T19:02:22Z#P.O. Spann###{"type": "Point", "coordinates": [-3.606772, 39.460299]}#consabura#precise#640#-330#2011-09-05T20:57:22Z###Consabura#/places/265876/consabura#/places/265876#39.460299#39.460299,-3.606772#-3.606772##HRL#hellenistic-republican,roman,late-antique#-330.0,640.0#Consabura#fc0ee157ff11ce6d2e72cd7c5df06fee
  Spann, P., R. Warner, R. Talbert, T. Elliott, S. Gillies#-3.606772, 39.460299, -3.606772, 39.460299#2010-09-24T19:02:22Z#P.O. Spann###{"type": "Point", "coordinates": [-3.606772, 39.460299]}#consabrum#precise#640#-330#2011-09-05T20:57:22Z###Consabrum#/places/265876/consabrum#/places/265876#39.460299#39.460299,-3.606772#-3.606772##HRL#hellenistic-republican,roman,late-antique#-330.0,640.0#Consabrum#e2b8756302fb62ff0e301710c265a3e6""".split("\n").toVector


  "A PleiadesNames collection" should "be constructable from a delimited text record" in  {
    val names = PleiadesNames.parseNamesCex(cex)
    names match {
      case pn: PleiadesNames => assert(true)
      case _ => fail("Did not create a PleiadesName from cex.")
    }
  }

  it should "lookup records by ID value" in {
    val names = PleiadesNames.parseNamesCex(cex)
    val id = BigDecimal("265876")
    val expectedMatches = 2
    assert(names.lookup(id).size == expectedMatches)
  }

  it should "lookup records by name String"  in {
    val names = PleiadesNames.parseNamesCex(cex)
    val expected =  BigDecimal("265876")
    val ids = names.idsForName("Consabura")
    assert(ids.size == 1 && ids(0) == expected)
  }
}
