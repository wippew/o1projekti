package o1.adventure

class StudyRoom(name: String, desc: String) extends Area(name: String, desc: String){
  
  
  override def getText(): String = {
    return "For studying writing type w, for studying maths type m"
  }
  
}