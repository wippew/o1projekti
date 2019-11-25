package o1.adventure
import scala.swing.TextArea

class WritingTasks(mainFrame: TextArea, output: TextArea) {
  
  def goToWritingProblem1(str: String, firstTime: Boolean) = {
    mainFrame.text = ("Write the following word: AbCd^")
    output.text = "";
    if (firstTime) {
      0
    } else {
      if ( str == "AbCd^" ) {
        output.text = "CORRECT WELL DONE YOU GET 1 POINT!" + "\n" + "TRY THE OTHER PROBLEM BY PRESSING 2\nOr try maths by typing: maths"          
      }
    }
  }
  
  def goToWritingProblem2(str: String, firstTime: Boolean) = {
    mainFrame.text = ("Write the following word: A€]@)#*^`")
    output.text = "";
    if (firstTime) {
      0
    } else {
      println(str)
      if ( str == "A€]@)#*^`" ) {
        output.text = "CORRECT WELL DONE YOU GET 1 POINT!" + "\n" + "TRY THE OTHER PROBLEM BY PRESSING 1\nOr try maths by typing: maths"          
      }
    }
  }
  
  def goToLosePoint() = {
    output.text = "Wrong, -1 point...\nTry the same task again or switch to maths by typing: maths"
  }
  
  
  
}