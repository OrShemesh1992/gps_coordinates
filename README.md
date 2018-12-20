# project gps coordinates
In this project we have developed a system that enables the collection of geographic
information and the presentation the information in graphical tools.

The goal of the game is to eat all the fruits in the shortest time possible,
each packman has a speed at which he can advance to the next fruit,
and an eating radius that defines the minimum required proximity to the fruit he want to eat.
The game configuration will be determined by the information given in a predetermined
CSV file or file that we have created. We have created a menu for the user that will
allow him to create his own game by placing the packman and fruits on the map,
and setting the radius and speed for the packman , and export the game to a csv file,
in addition the menu allows him to upload an existing file of the game and run the game.
In order to have all the fruits eaten in the shortest time, we have created an algorithm
that will calculate the most effective way for each packman to eat the fruit he has
reached in the shortest time, thus creating the path where he can eat the maximum
amount of fruit given his speed and radius.

## Authors
Or Shemesh & Yaara Goldenberg. 

## Tutorial
To start the program Import from your computer a csv file that containing the game data
or create your own game by selecting the fruit and packman and positioning them on the map.
Afterwards press the "start" button to start the game.
### Example of the game:
![20181220_151449](https://user-images.githubusercontent.com/44780654/50287115-355f6700-046a-11e9-9e9d-822963941fef.gif)
### KML file example:
![20181220_185317](https://user-images.githubusercontent.com/44780654/50298886-eaa11780-0488-11e9-9c27-dc77d5ea0494.gif)
## Description the packages and classes
### Package:
#### Coords:
- MyCoords- 
In this class we created a number of functions such as calculate distance, with which we can do several calculations between vectors like, convert a 3D vector to polar coordinates, convert a polar coordinate to 3D point, adds two vectors together, distance, calculate the vector in meters and calculate the distance, azimuth and elevation.
- Csv2Kml-
In this class we read a csv file from our computer and convert it to a kml file.
- MultiCSV-
In this class we are looking recursively for a csv file.
#### GIS:
- Data-
In this class we describe each layer and its time.
- Element-
This class represents the parameters: mac, ssid, authmode, time and GPS coordinates with which we can perform additional calculations on vectors.
- Layer-
In this class we have a collection of elements that we read from a csv file, with which we could create a kml file.
- Project-
In this class we use the class Layer to receive multiple layers so we could a kml file.
#### PackmanGame:
- Fruit-
This class represents target (the target cannot move), each target (fruit) has an ID number, weight, map location (in meters or pixel) and string type.
- Packman- 
This class represents packman (the packman capable of movement), each packman has an ID number, speed, radius, map location (in meters or pixel) and string type. 
- Game- 
In this class we have a list of packman and a list of fruits. This class has the ability to be built from the data it receives from the csv file, and also to create a csv file with the data it receives from a new game we created.
- Map- 
This class represent the map which we use in the game. It included the map image, the fruit image and the packman image.
The class calculates conversion of coordinates from global representation to pixel and vice versa, and also calculates the distance in meters between 2 pixel points.
- MyFrame-  
This class is a graphical class that displays the packman and fruits on the map, showing the algorithm operation we created in the ShortestPathAlgo class, and also performing a recovery of data from csv files or creating a game by selecting the fruit and packman and positioning them on the map.
- Path- 
This class include the paths that each packman does in the game.
- Path2KML-
 This class gets all the paths that the packman does in the game and saves them in a kml file, the path is saved so that we can run it on google earth and see the path that each packman does.
- ShortestPathAlgo- 
This class receives a list of fruit packman and a list of fruits and calculates the shortest path for each packman considering its speed and radius so that all of the fruits will eat as fast as possible.
