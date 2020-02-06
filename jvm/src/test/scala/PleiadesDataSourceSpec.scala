package edu.holycross.shot.pleiades
import org.scalatest.FlatSpec



class PleiadesDataSourceSpec extends FlatSpec {

  val pleiadesPlacesCex = "jvm/src/test/resources/pleiades-places-20200129.cex"

  val pleiadesNamesCex = "jvm/src/test/resources/pleiades-names-20200205.cex"

  "The PleiadesPlaces object"  should "parse Pleiades places dump" in {
    val parsed = PleiadesDataSource.parsePlacesFile(pleiadesPlacesCex)
  }

  it should "parse a map of names to pleiades IDs" in {
    val namesMap = PleiadesDataSource.parseNamesFile(pleiadesNamesCex)
  }


}
