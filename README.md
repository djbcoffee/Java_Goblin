# Java Goblin
A Java remake of a classic game called Goblin from the July 1983 issue of [Compute! magazine](https://en.wikipedia.org/wiki/Compute!).

The complete project page can be found [here](https://sites.google.com/view/m-chips/goblin).

## Archive content

The following files are provided:
* build - Build directory
	- images - Images directory
		- Explosion_16x16.png - Image file
		- Explosion_24x24.png - Image file
		- Explosion_32x32.png - Image file
		- Goblin_16x16.png - Image file
		- Goblin_24x24.png - Image file
		- Goblin_32x32.png - Image file
		- Happy_Face_16x16.png - Image file
		- Happy_Face_24x24.png - Image file
		- Happy_Face_32x32.png - Image file
		- Shocked_Face_16x16.png - Image file
		- Shocked_Face_24x24.png - Image file
		- Shocked_Face_32x32.png - Image file
		- Shrub_16x16.png - Image file
		- Shrub_24x24.png - Image file
		- Shrub_32x32.png - Image file
		- Stone_16x16.png - Image file
		- Stone_24x24.png - Image file
		- Stone_32x32.png - Image file
		- Wall_16x16.png - Image file
		- Wall_24x24.png - Image file
		- Wall_32x32.png - Image file
* javadoc - Directory where the javadocs are stored when they are created
	- ignore - A place holder file to keep the directory from being empty.
* src - Source files directory
	- controller - Directory for controller source code
		- GoblinController.java - Source code file
	- main - Directory for main source code
		- Goblin.java - Source code file
	- model - Directory for model source code
		- GoblinModel.java - Source code file
		- Images.java - Source code file
		- Tile.java - Source code file
	- view - Directory for view source code
		- GoblinView.java - Source code file
* Goblin.jar - Executable JAR file with the Goblin game
* MANIFEST.MF - JAR File manifest used during build
* LICENSE - License text
* README.md - This file

## Prerequisites

To play the game Java SE JRE version 1.8.0_241, or higher, needs to be installed on your PC.

To build the source code Java SE JDK version 1.8.0_241, or higher, needs to be installed on your PC.

Both can be obtained from [here](https://www.oracle.com/java/technologies/javase-downloads.html).

## Installing

Place the directories and files into any convenient location on your PC.

The executable that was supplied, Goblin.jar, was built with Java SE JDK version 1.8.0_241.  This can be used as is with Java SE JRE version 1.8.0_241, or higher, installed on your PC.  If you wish to do a build continue with the following steps.

Open a command prompt and navigate to where you installed the directories and files.  Ensure that the path environment variable contains the path to the Java SE JDK binaries.

To compile the Java source code files execute the following on the command line:\
```javac -cp ./src -d ./build ./src/main/Goblin.java```\
Afterwards the build directory will have the compiled classes within their respective directories.

To build an executable JAR file execute the following on the command line (the period is suppose to follow ./build):\
```jar cvmf MANIFEST.MF Goblin.jar -C ./build .```\
Afterwards Goblin.jar will be created.

To create all the javadocs execute the following on the command line:\
```javadoc -d ./javadoc -author -version ./src/controller/*.java ./src/main/*.java ./src/model/*.java ./src/view/*.java```\
Afterwards the javadoc directory will have the HTML javadocs.  Open index.html in a browser to read through them.

## Operating instructions

Goblin is a fun, challenging, and entertaining maze game.  The object is to capture the shocked faces your goblin while avoiding the many brick wall obstacles that lie in your path.  When you select Start from the Game menu the playing field is drawn.  After the brick wall obstacles and shocked faces have been randomly positioned, your goblin appears at the bottom of the screen in a random position.  After the goblin is drawn you have 3 seconds before the game starts.  As the game progresses, the goblin moves continually upward.  You control your Goblin's horizontal movement with the 'A' and 'L' keys.  The 'A' key is for left movement and the 'L' key for right movement.  All movement is made diagonally when moving left or right.  Using the character keyboard buffer built into the game you can cue up moves ahead of time.  When the Goblin reaches the top of the screen it starts again at the bottom.  <b>Beware of brick wall obstacles at the bottom of the screen!</b>  As each shocked face is captured by the Goblin the score is updated in the window title bar.  If the Goblin successfully clears the playing field of all the shocked faces an entirely new field will be drawn and the level increased.  With each level there will be more brick wall obstacles drawn and the speed of the Goblin increases.  The game ends when the Goblin crashes into one of the brick wall obstacles and explodes.  With the Goblin destroyed the remaining faces smile.  "GAME OVER" will appear in the window title bar along with the final score and level.
 
The menu bar contains game control and game option pull-down menu.  In the Game menu there is Start and Exit.  Start will start a new game and Exit will close the game window.  In the Options menu there are ways to customize the game field.  There are three options for tile sizes and three options for playing field size.  As these are selected the game window is redrawn.

## Built With

* [Java SE JDK version 1.8.0_241](https://www.oracle.com/java/technologies/javase-downloads.html) - The toolchain used

## Version History

* v1.0.0 - 2012 
	- Initial release

## Authors

* **Donald J Bartley** - *Initial work* - [djbcoffee](https://github.com/djbcoffee)

## License

This project is licensed under the GNU Public License 2 - see the [LICENSE](LICENSE) file for details