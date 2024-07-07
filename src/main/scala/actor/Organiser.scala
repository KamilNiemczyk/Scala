package main.actor

import akka.actor._

object Organiser {
  case object End
}


class Organiser(maxFiles: Int, system: ActorSystem) extends Actor {
    import Organiser._

    var filesProcessed = 0               //counter how many files are processed

    def receive : Receive = {
        case Saver.End => {
            filesProcessed += 1            //adding 1 to counter
            if (filesProcessed == maxFiles) {         //if all file are processed, system terminates
                system.terminate()
            }
        }
    }
}