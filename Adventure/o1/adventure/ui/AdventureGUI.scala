package o1.adventure.ui

import scala.swing._
import scala.swing.event._
import javax.swing.UIManager
import o1.adventure.Adventure
import o1.adventure._





object AdventureGUI extends SimpleSwingApplication {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  def top = new MainFrame {

    // Access to the application’s internal logic:

    val game = new Adventure
    val player = game.player

    // Components:

    val upperInfoBox = new TextArea(7, 80) {
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
    
    val availableCommands = new TextArea(1, 60) {
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
      layout += new Label("Location:") ->                         new Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Command:")  ->                         new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Available commands:")   ->             new Constraints(0, 2, 1, 0, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)            
      layout += new Label("Events:")   ->                         new Constraints(0, 3, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += upperInfoBox           ->                         new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += input                  ->                         new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += availableCommands      ->                         new Constraints(1, 2, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += output                 ->                         new Constraints(1, 3, 1, 2, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += turnCounter            ->                         new Constraints(0, 5, 2, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
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
    upperInfoBox.text = "You are sitting in your livingroom, what you wanna do?" + player.location.fullDescription
    location = new Point(50, 50)
    minimumSize = new Dimension(200, 300)
    pack()
    input.requestFocusInWindow()
    this.turnCounter.text = "turnCOunterTExt"

    def playTurn(command: String) = {
      var ret = command.toLowerCase()
      ret match {
        case "maths" => goToMath(0)
        case "2" => goToMath(2)
        case _ => {              
          val turnReport = this.game.playTurn(ret)
          this.upperInfoBox.text = player.location.fullDescription
        }
      }
    }
    
    private def goToMath(i: Int) = {
      if ( i == 0 ) {
        0 //corner prepared
      }
      refreshMaths()
      upperInfoBox.text = ("Solve the equation 1 + 1")
      if ( i == 2 ) {
        output.text = "CORRECT WELL DONE YOU GET 1 POINT"
      } else {
        output.text = "WRONG... I GOTTA GIVE YOU -1 POINT"
      }
    }
    
    private def refreshMaths() = {
      title = "The study coom of maths"
      output.text = "You are about to get rekt"
      upperInfoBox.text = "You are in the study room of maths" + "\n" + player.location.fullDescription
    }
    


  }

}

