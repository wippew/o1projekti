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
      var str = command.toLowerCase().trim().replaceAll("\\s", "")
      game.playTurn(command)
      if (player.location.name == "Home") {
        handleHome(command)
      } else if (player.location.name == "Study room") {
        handleStudyRoom(str, command)
      }
    }
    
    private def handleHome(command: String) = {
      game.playTurn(command)
      availableExits.text = getAvailableExits()
      mainFrame.text = player.location.getText()
      println(player.location.name)
    }
    
    private def handleStudyRoom(str: String, command: String) = {
      val mathTask = new MathTasks(mainFrame, output)
      str match {
          case "m" => refreshMaths()
          case "1" => mathTask.goToMathProblem1(0)
          case "2" => mathTask.goToMathProblem2(0)
          case "3" => mathTask.goToMathProblem3(0)
          case "10" => mathTask.goToMathProblem1(10)
          case "100000" => mathTask.goToMathProblem2(100000)
          case "-15" => mathTask.goToMathProblem3(-15)
          case _ => {
            this.mainFrame.text = "For studying writing type w, for studying maths type m"
            this.output.text = "turnRepo"
            this.availableExits.text = getAvailableExits
      }
          println(player.location.name)
    }
    }
    
    
    
    private def refreshMaths() = {
      title = "The study room of maths"
      availableExits.text = getAvailableExits()
      output.text = "You are about to get rekt"
      mainFrame.text = "You are in the study room of maths, to solve problem 1 type 1, to solve problem 2 type 2 and for problem 3 type 3"
    }
    
    
    private def getAvailableExits(): String = {
      return player.location.getNeighbors()
    }
    


  }

}

