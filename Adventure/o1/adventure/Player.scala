package o1.adventure

import scala.collection.mutable.Map


/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  
  private val inv = Map[String, Item]()


  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven


  /** Returns the current location of the player. */
  def location = this.currentLocation

  def has(item: String) = inv.contains(item)
  
  def examine(item: String): String = {
    var ret = "If you want to examine something, you need to pick it up first."
    if (has(item)) {
      val x = inv.get(item)
      ret = "You look closely at the " + item + ".\n" + x.get.description
    }
    ret
  }
  
  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }
  
  def get(item: String): String = {
    var returnText = "There is no " + item + " here to pick up."
    if (this.location.contains(item)) {
      var a = this.location.removeItem(item)
      if (a.isDefined) {
        var addMe = (a.get)
        inv += (item -> addMe)
      }
      returnText = "You pick up the " + item + "."
    }
    returnText
  }
  
  def drop(item: String): String = {
    var ret = "You don't have that!"
    if (inv.contains(item)) {
      val dropped = inv.remove(item)
      location.addItem(dropped.get)
      ret = "You drop the " + item + "."
    }
    ret
  }
  
  // Returns list of inventory if non-empty
  def inventory: String = {
    var ret = "You are empty-handed."
    if (!inv.isEmpty) {
      val list = inv.keys.mkString("\n")
      ret= "You are carrying:\n" + list
    }
    ret 
  }


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    "You rest for a while. Better get a move on, though."
  }


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }


  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name


}


