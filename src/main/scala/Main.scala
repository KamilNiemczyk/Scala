package main
import akka.actor._
import actor.CalculateScore
import actor.Organiser
import actor.Saver
import java.io.File
import scala.concurrent.Await
import scala.concurrent.duration._
import com.typesafe.config.ConfigFactory

@main  //main
def main() = {
  val system = ActorSystem("System")    //creating actor system
  val config = ConfigFactory.load()
  val dirPath = config.getString("in")     //load config
  val dir = new File(dirPath)
  val files = dir.listFiles().toList.filter(file => file.getName.endsWith(".jpg") || file.getName.endsWith(".png") || file.getName.endsWith(".jpeg")) //select correct files
  val organiser = system.actorOf(Props[Organiser](new Organiser(files.size, system))) //create organiser
  println("Give your 'cut off' point (0-100 recomended 73-86): ")
  val cutoff = scala.io.StdIn.readInt()              //get input
  val saver = system.actorOf(Props[Saver](new Saver(organiser, cutoff)))   //create saver
  files.foreach(file => {
    val path = file.getPath
    val actor = system.actorOf(Props[CalculateScore](new CalculateScore(saver)))       //creating actor
    actor ! CalculateScore.ReadFile(path)                     //sending each file to actor
  })
  Await.result(system.whenTerminated, Duration.Inf)    //waiting untill all actors are done
}