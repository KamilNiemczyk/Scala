package main.actor

import akka.actor._
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.Raster


object CalculateScore {
  case class ReadFile(path: String)
  case class SaveFile(path: String, score: Int)
}

class CalculateScore(saver : ActorRef) extends Actor {
  import CalculateScore._

    def receive : Receive = {
      case ReadFile(path) => {
        val img = ImageIO.read(new File(path))                //reading image
        val raster = img.getRaster  
        val h = img.getHeight()         //getting height
        val w = img.getWidth()          //getting width 
        val matrix = Array.ofDim[Int](h, w)   //creating matrix

        for (i <- 0 until h) {
          for (j <- 0 until w) {
            val color = raster.getPixel(j, i, new Array[Int](3))   //getting pixel
            val red = color(0)       //getting red
            val green = color(1)        //getting green
            val blue = color(2)     //getting blue
            val gray = (red + green + blue) / 3     //converting to gray
            matrix(i)(j) = gray             //saving gray to matrix
          }
        }

        val avg = matrix.flatten.sum / (h * w)         //calculating average gray calue of matrix
        val percent = (avg * 100) / 256                  //calculating percent of grayness
        val reversepercent = 100 - percent           //calculating reverse percent of grayness
        saver ! Saver.SaveFile(path, reversepercent.toInt)         //sending result to saver
      }
    }
}