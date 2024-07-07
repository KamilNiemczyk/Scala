package main.actor

import akka.actor._
import javax.imageio.ImageIO
import java.io.File
import com.typesafe.config.ConfigFactory

object Saver {
  case class SaveFile(path: String, score: Int)
  case object End
}

class Saver(organiser : ActorRef, cutoff: Int) extends Actor {
    import Saver._

    def receive : Receive = {
        case SaveFile(path, score) => {
            val img = ImageIO.read(new File(path))         //reading image
            val format = path.split('.').last             //getting format
            val config = ConfigFactory.load() 
            val outPath = config.getString("out")       //getting out dir from config
            val out = new File(outPath)
            if (score < cutoff) {         //checking if score is lower or higher than cutoff
                val file = new File(s"$out/${path.split("\\\\").last.split('.').head}_bright_$score.$format")
                ImageIO.write(img, format, file)      //saving image as bright with score
            } else {
                val file = new File(s"$out/${path.split("\\\\").last.split('.').head}_dark_$score.$format")
                ImageIO.write(img, format, file)          //saving image as dark with score
            }
            organiser ! End       //sending message to organiser
        }
    }
}