package o1.adventure

import scala.swing.TextArea

class LivingRoom(name: String, desc: String) extends Area(name: String, desc: String) {
  
  
  def handleLivingRoom(command: String, outcomeReport: String, studyRoom: StudyRoom, mainFrame: TextArea, output: TextArea, availableExits: TextArea, player: Player) = {
      studyRoom.setStudyRoomBooleansToDefault()
      availableExits.text = getAvailableExits(player)
      mainFrame.text = player.location.getText()
      output.text = outcomeReport
    } 

    private def getAvailableExits(player: Player): String = {
      return player.location.getNeighbors()
    }
}