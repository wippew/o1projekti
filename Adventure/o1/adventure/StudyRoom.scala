package o1.adventure

class StudyRoom(name: String, desc: String) extends Area(name: String, desc: String){
  
  
  def chooseTask(str: String): String = {
    var temp = str.toLowerCase();
    if ( temp == "maths") {
      return str
    } else if (temp == "writing") {
      return str
    }
    temp
  }
  
  
}