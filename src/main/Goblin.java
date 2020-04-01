/*
 ********************************************************************************
 ** Copyright (C) 2012 Donald J. Bartley <djbcoffee@gmail.com>
 **
 ** This source file may be used and distributed without restriction provided
 ** that this copyright statement is not removed from the file and that any
 ** derivative work contains the original copyright notice and the associated
 ** disclaimer.
 **
 ** This source file is free software; you can redistribute it and/or modify it
 ** under the terms of the GNU General Public License as published by the Free
 ** Software Foundation; either version 2 of the License, or (at your option) any
 ** later version.
 **
 ** This source file is distributed in the hope that it will be useful, but
 ** WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 ** FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 ** more details.
 **
 ** You should have received a copy of the GNU General Public License along with
 ** this source file.  If not, see <http://www.gnu.org/licenses/> or write to the
 ** Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 ** 02110-1301, USA.
 ********************************************************************************
 ** DJB 03/17/2012 Created.
 ********************************************************************************
 */

package main;

import controller.GoblinController;

/**
 * Goblin is a fun, challenging, and entertaining maze game.  The object is to
 * capture the shocked faces your goblin while avoiding the many brick wall
 * obstacles that lie in your path.  When you select Start from the Game menu the
 * playing field is drawn.  After the brick wall obstacles and shocked faces have
 * been randomly positioned, your goblin appears at the bottom of the screen in a
 * random position.  After the goblin is drawn you have 3 seconds before the game
 * starts.  As the game progresses, the goblin moves continually upward.  You
 * control your Goblin's horizontal movement with the 'A' and 'L' keys.  The 'A'
 * key is for left movement and the 'L' key for right movement.  All movement is
 * made diagonally when moving left or right.  Using the character keyboard
 * buffer built into the game you can cue up moves ahead of time.  When the
 * Goblin reaches the top of the screen it starts again at the bottom.  <b>Beware
 * of brick wall obstacles at the bottom of the screen!</b>  As each shocked face
 * is captured by the Goblin the score is updated in the window title bar.  If
 * the Goblin successfully clears the playing field of all the shocked faces an
 * entirely new field will be drawn and the level increased.  With each level
 * there will be more brick wall obstacles drawn and the speed of the Goblin
 * increases.  The game ends when the Goblin crashes into one of the brick wall
 * obstacles and explodes.  With the Goblin destroyed the remaining faces smile.
 * "GAME OVER" will appear in the window title bar along with the final score and
 * level.
 * 
 * The menu bar contains game control and game option pull-down menu.  In the
 * Game menu there is Start and Exit.  Start will start a new game and Exit will
 * close the game window.  In the Options menu there are ways to customize the
 * game field.  There are three options for tile sizes and three options for
 * playing field size.  As these are selected the game window is redrawn.
 * 
 * @author Donald J Bartley
 * @version 1.0
 */
public class Goblin {

    /**
     * Main method used to start the game.
     * 
     * @param args Arguments passed from the command line (these are not used).
     */
    public static void main(String[] args) {
        //Instantiate the controller.
        GoblinController controller = new GoblinController();

        //Start the game.
        controller.startGame();
    }
}