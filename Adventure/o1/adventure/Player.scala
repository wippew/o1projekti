package o1.adventure

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer


/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  
  private var inv = ArrayBuffer[(String, Item)]()
  
  //the main characters points
  var points = 0


  /** Returns the current location of the player. */
  def location = this.currentLocation
  
  def examine(item: String): String = {
    var ret = "If you want to examine your beer, you need to study to get some."
    if (!inv.isEmpty) {
      ret = "New unopened fresh beer"
    }
    ret
  }
  
  
  
  def setLocation(newLocation: Area) = currentLocation = newLocation
  
  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) {
      "You go " + direction + "."
    } else {
      "You can't go " + direction + "."
    }
  }
  
  def get(item: String): String = {
    var returnText = "There is no " + item + " here to pick up. Your only option is to study..."
    if (!this.location.allItems.isEmpty) {
      var a = this.location.removeItem(item)
      if (a.isDefined) {
        var addMe = a.get
        val addedItem = (item, addMe)
        inv += addedItem
      }
      returnText = "You grabbed a " + item + "."
    }
    returnText
  }
  
  def drinkBeer(player: Player, wife: Player): String = {
    var ret = ""
    if (wife.location.name == "Kitchen" && player.location.name == "Kitchen" && !inv.isEmpty) {
      ret = "Wife: NO YOU CANT DRINK BEER DURING THE DAY! YOU GET -4 POINTS"
      points -= 4
    } else if (!inv.isEmpty && player.location.name== "Kitchen") {
      ret = "You get a nice mood boost, and hence 5 points"
      points += 5
      drop()
    } else if (player.location.name == "Study room" && !inv.isEmpty) {
      ret = "Go to the kitchen if you want to drink. Wet notebooks are not nice..." 
    } else if (player.location.name == "Living room" && !inv.isEmpty) {
      ret = "Go to the kitchen if you want to drink. It is far to risky here in the middle of the house..." 
    } else {
      ret = "You are out of beer. Better study for more beer"
    }
    ret
  }
  
  def drop(): String = {
    var ret = "You don't have that!"
    if (!inv.isEmpty) {
      inv = inv.tail
    }
    ret
  }
  
  //returns how many beers you have
  def inventory: String = {
    var ret = "You are empty-handed."
    if (!inv.isEmpty) {
      println("invsize: " + inv.size)
      ret= "You are carrying:\n" + inv.size + " X BEERS"
    }
    ret 
  }



  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name


}


