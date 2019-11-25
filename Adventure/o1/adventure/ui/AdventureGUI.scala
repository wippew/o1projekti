package o1.adventure.ui

import scala.swing._
import scala.swing.event._
import javax.swing.UIManager
import o1.adventure.Adventure
import o1.adventure._
import o1.adventure.MathTasks




object AdventureGUI extends SimpleSwingApplication {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  def top = new MainFrame {

    // Access to the application’s internal logic:

    val game = new Adventure
    val player = game.player

    // Components:

    val mainFrame = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }
    val output = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }
    
    val input = new TextField(40) {
      minimumSize = preferredSize
    }
    
    val availableExits = new TextArea(1, 60) {
      editable = false
      wordWrap = true
      lineWrap = true
    }

    
    this.listenTo(input.keys)
    
    val turnCounter = new Label

    // Events:

    this.reactions += {
      case keyEvent: KeyPressed =>
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver) {
          val command = this.input.text.trim
          if (command.nonEmpty) {
            this.input.text = ""
            this.playTurn(command)
          }
        }
    }

    // Layout:

    this.contents = new GridBagPanel {
      import scala.swing.GridBagPanel.Anchor._
      import scala.swing.GridBagPanel.Fill
      layout += new Label("Main frame:") ->                        new Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Command:")  ->                          new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Available doors:")   ->                 new Constraints(0, 2, 1, 0, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)            
      layout += new Label("Output:")   ->                          new Constraints(0, 3, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += mainFrame           ->                             new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += input                  ->                          new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += availableExits      ->                          new Constraints(1, 2, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += output                 ->                          new Constraints(1, 3, 1, 2, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += turnCounter            ->                          new Constraints(0, 5, 2, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
    }

    // Menu:

    this.menuBar = new MenuBar {
      contents += new Menu("Program") {
        val quitAction = Action("Quit") { dispose() }
        contents += new MenuItem(quitAction)
      }
    }

    // Set up the GUI’s initial state:

    title = "GAME STARTS"
    mainFrame.text = player.location.description
    availableExits.text = getAvailableExits
    location = new Point(50, 50)
    minimumSize = new Dimension(200, 300)
    pack()
    input.requestFocusInWindow()
    this.turnCounter.text = "turnCOunterTExt"

    def playTurn(command: String) = {
      val str = command.toLowerCase().trim().replaceAll("\\s", "")
      val outcomeReport = game.playTurn(command)
      if (player.location.name == "Home") {
        handleHome(command, outcomeReport)
      } else if (player.location.name == "Study room") {
        handleStudyRoom(str, command, outcomeReport)
      }
    }
    
    private def handleHome(command: String, outcomeReport: String) = {
      setStudyRoomBooleansToDefault()
      availableExits.text = getAvailableExits()
      mainFrame.text = player.location.getText()
      output.text = outcomeReport
    }
    
    //study room booleans to control the right messages
    var studyingMaths = false
    var mathTaskChosen = false
    var studyingWriting = false
    var writingTaskChosen = false
    private def handleStudyRoom(str: String, command: String, outcomeReport: String) = { 
      if ( str == "m" || studyingMaths) {
        if(str == "write") {
          switchToWriting()
        } else if (!studyingMaths) {
          refreshMaths()
          output.text = ""          
        } else if (!mathTaskChosen){          
          chooseMathTask(str)          
        } else {
          testMathAnswer(str)            
        }
      } else if ( str == "w" || studyingWriting) {
        if (str == "maths") {
          switchToMaths()
        } else if (!studyingWriting) {
          refreshWriting()
          
          output.text = ""
          studyingWriting = true
        } else if (!writingTaskChosen) {
          chooseWritingTask(str)
        } else {
          testWritingAnswer(command)          
        }
      } else {
        this.mainFrame.text = "For studying writing type w, for studying maths type m"        
        this.output.text = outcomeReport
        this.availableExits.text = getAvailableExits
      }
    }
    
    private def switchToWriting() = {
      output.text = "You switched to writing."
      setStudyRoomBooleansToDefault()
      refreshWriting()
    }
    
    private def switchToMaths() = {
      output.text = "You switched to maths."
      setStudyRoomBooleansToDefault()
      refreshMaths()
    }
    
    private def setStudyRoomBooleansToDefault() = {
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
    
    private def chooseWritingTask(str: String) = {
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
    
    private def testWritingAnswer(str: String) = {
      val writingTask = new WritingTasks(mainFrame, output)
      str match {
          case "AbCd^" => writingTask.goToWritingProblem1(str, false)
          case "A€]@)#*^`" => writingTask.goToWritingProblem2(str, false)
          case _ => writingTask.goToLosePoint()
        }
      writingTaskChosen = false
      refreshWriting()
    }
    
    private def chooseMathTask(str: String) = {
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
    
    private def testMathAnswer(str: String) = {
     val mathTask = new MathTasks(mainFrame, output)
     str match {
          case "10" => mathTask.goToMathProblem1(10)
          case "100000" => mathTask.goToMathProblem2(100000)
          case "-15" => mathTask.goToMathProblem3(-15)
          case _ => mathTask.goToLosePoint()
     }
     mathTaskChosen = false
     refreshMaths()
    }
    
    
    private def refreshMaths() = {
      title = "The study room of maths"
      availableExits.text = getAvailableExits()
      mainFrame.text = "You are in the study room of maths.\nTo solve problem 1 type A, to solve problem 2 type B and for problem 3 type C"
      setWritingBooleansToDefault()
      studyingMaths = true
    }
    
    private def refreshWriting() = {
      title = "The study room of writing"
      availableExits.text = getAvailableExits()
      mainFrame.text = "You are in the study room of writing.\nTo solve problem 1 type A, to solve problem 2 type B"
      setMathBooleansToDefault()
      studyingWriting = true
    }
    
    
    private def getAvailableExits(): String = {
      return player.location.getNeighbors()
    }
    


  }

}

