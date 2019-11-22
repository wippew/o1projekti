
package o1.adventure


/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure {

  val home = new Area("Home", "Chilling at home. What do you want to do?")
  val studyRoom = new StudyRoom("Study room", "Do you want to study writing or maths?")
  
  home.setNeighbors(Vector("north" -> studyRoom))
  studyRoom.setNeighbors(Vector("south" -> home))



  /** The character that the player controls in the game. */
  val player = new Player(home)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40000


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = false

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete)
      "Home at last... and phew, just in time! Well done!"
    else if (this.turnCount == this.timeLimit)
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    else  // game over due to player quitting
      "Quitter!"
  }


  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}

