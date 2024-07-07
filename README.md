## Documentation

### How it works

It’s a simple application which describes if the image is bright or dark based on provided ‘cut off’ parameter. At application.properties file you can describe from which directory you are reading the images. All of the resources are in ‘resources’ directory. After running the application will ask you for setting the ‘cut off’ parameter (the recommend number is between 73-86). Based on this it will set the line between bright and dark images (images which will get higher or ecqual score than this parameter will be described as dark and other images will be described as bright). Then the application will create as many actors as how many files there are.  Each actor will calculate the avarage score and then will reverse this percent because this task required 100% to be the darkest image. Then this actor will send this result to other actor which will save the image with changed name which will contain score and type of image (bright/dark based on previous ‘cut off’ parameter). After saving the file it will send message to actor Organiser which will terminate the system if all of the files will be processed. All of the specifications are in build.sbt file.

### How to run

After cloning repository or downloading the zip you need to have sbt installed on your computer and then enter the directory at terminal and write a command „sbt run” which will start the program. 

