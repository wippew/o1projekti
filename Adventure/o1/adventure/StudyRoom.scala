package o1.adventure
import scala.swing.TextArea
class StudyRoom(name: String, desc: String) extends Area(name: String, desc: String){
  
    private var studyingMaths = false
    private var mathTaskChosen = false
    private var studyingWriting = false
    private var writingTaskChosen = false
    
    def handleStudyRoom(str: String, command: String, outcomeReport: String, mainFrame: TextArea, output: TextArea, availableExits: TextArea, player: Player) = { 
      if ( str == "m" || studyingMaths) {
        if(str == "write") {
          switchToWriting(mainFrame, output, availableExits, player)
        } else if (!studyingMaths) {
          refreshMaths(mainFrame, availableExits, player)
          output.text = ""          
        } else if (!mathTaskChosen){          
          chooseMathTask(str, mainFrame, output)          
        } else {
          testMathAnswer(str, mainFrame, output, availableExits, player)            
        }
      } else if ( str == "w" || studyingWriting) {
        if (str == "maths") {
          switchToMaths(mainFrame, output, availableExits, player)
        } else if (!studyingWriting) {
          refreshWriting(mainFrame, availableExits, player)          
          output.text = ""
          studyingWriting = true
        } else if (!writingTaskChosen) {
          chooseWritingTask(str, mainFrame, output)
        } else {
          testWritingAnswer(command, mainFrame, output, availableExits, player)          
        }
      } else {
        mainFrame.text = "For studying writing type w, for studying maths type m"        
        output.text = outcomeReport
        availableExits.text = getAvailableExits(player)
      }
    }
    
    private def switchToWriting(mainFrame: TextArea, output: TextArea, availableExits: TextArea, player: Player) = {
      output.text = "You switched to writing."
      setStudyRoomBooleansToDefault()
      refreshWriting(mainFrame, availableExits, player)
    }
    
    private def switchToMaths(mainFrame: TextArea, output: TextArea, availableExits: TextArea, player: Player) = {
      output.text = "You switched to maths."
      setStudyRoomBooleansToDefault()
      refreshMaths(mainFrame, availableExits, player)
    }
    
    def setStudyRoomBooleansToDefault() = {
      setMathBooleansToDefault()
      setWritingBooleansToDefault()      
    }
    
    private def setMathBooleansToDefault() = {
      studyingMaths = false
      mathTaskChosen = false
    }
    
    private def setWritingBooleansToDefault() = {
      studyingWriting = false
      writingTaskChosen = false
    }
    
    private def chooseWritingTask(str: String, mainFrame: TextArea, output: TextArea) = {
      val writingTask = new WritingTasks(mainFrame, output)
      writingTaskChosen = true
      str match {
          case "a" => writingTask.goToWritingProblem1(str, true)
          case "b" => writingTask.goToWritingProblem2(str, true)
          case _ => {
          output.text = "The options are A,B and C..."
          writingTaskChosen = false
          }
        }      
    }
    
    private def testWritingAnswer(str: String, mainFrame: TextArea, output: TextArea,  availableExits: TextArea, player: Player) = {
      val writingTask = new WritingTasks(mainFrame, output)
      str match {
          case "AbCd^" => writingTask.goToWritingProblem1(str, false)
          case "A€]@)#*^`" => writingTask.goToWritingProblem2(str, false)
          case _ => writingTask.goToLosePoint()
        }
      writingTaskChosen = false
      refreshWriting(mainFrame, availableExits, player)
    }
    
    private def chooseMathTask(str: String, mainFrame: TextArea, output: TextArea) = {
      val mathTask = new MathTasks(mainFrame, output)
      mathTaskChosen = true
      str match {
        case "a" => mathTask.goToMathProblem1(0)
        case "b" => mathTask.goToMathProblem2(0)
        case "c" => mathTask.goToMathProblem3(0)
        case _ => {
          output.text = "The options are A,B and C..."
          mathTaskChosen = false
        }
      }      
    }
    
    private def testMathAnswer(str: String, mainFrame: TextArea, output: TextArea, availableExits: TextArea, player: Player) = {
     val mathTask = new MathTasks(mainFrame, output)
     str match {
          case "10" => mathTask.goToMathProblem1(10)
          case "100000" => mathTask.goToMathProblem2(100000)
          case "-15" => mathTask.goToMathProblem3(-15)
          case _ => mathTask.goToLosePoint()
     }
     mathTaskChosen = false
     refreshMaths(mainFrame, availableExits, player)
    }
    
    
    private def refreshMaths(mainFrame: TextArea, availableExits: TextArea, player: Player) = {
      availableExits.text = getAvailableExits(player)
      mainFrame.text = "You are in the study room of maths.\nTo solve problem 1 type A, to solve problem 2 type B and for problem 3 type C"
      setWritingBooleansToDefault()
      studyingMaths = true
    }
    
    private def refreshWriting(mainFrame: TextArea, availableExits: TextArea, player: Player) = {
      availableExits.text = getAvailableExits(player)
      mainFrame.text = "You are in the study room of writing.\nTo solve problem 1 type A, to solve problem 2 type B"
      setMathBooleansToDefault()
      studyingWriting = true
    }
    
    private def getAvailableExits(player: Player): String = {
      return player.location.getNeighbors()
    }
  
  
  override def getText(): String = {
    return "For studying writing type w, for studying maths type m"
  }
  
}