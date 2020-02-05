package edu.holycross.shot.pleiades
import org.scalatest.FlatSpec



class PleiadesDataSpec extends FlatSpec {

  val pleiadesPlacesCex = "jvm/src/test/resources/pleiades-places-20200129.cex"

  val pleiadesNamesCex = "jvm/src/test/resources/pleiades-names-20200205.cex"

  "The PleiadesData object"  should "parse Pleiades places dump" in {
    val parsed = PleiadesData.parsePlacesFile(pleiadesPlacesCex)
  }

  it should "parse a map of names to pleiades IDs" in {
    val namesMap = PleiadesData.parseNamesCex(pleiadesNamesCex)
  }


}
