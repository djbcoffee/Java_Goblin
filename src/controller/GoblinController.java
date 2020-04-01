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

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import model.GoblinModel;
import view.GoblinView;

/**
 * The controller in the game MVC architecture.  This class is responsible for
 * managing the operation of both the view and the model.  This class contains
 * the listeners from the view and the timer that controls the operation speed of
 * the model.  When the timer triggers the model is told to update its state and
 * when it is completed the controller looks at the new state of the model and
 * determines what action(s) need to taken in both the view and model.  The
 * controller also determines what action(s) to take based on the events passed
 * from the view listeners.
 * 
 * @author Donald J Bartley
 * @version 1.0
 */
public class GoblinController {

    /** The value in milliseconds for a one second delay. */
    private final int ONE_SECOND_DELAY = 1000;
    /** The value in milliseconds for a three second delay. */
    private final int THREE_SECOND_DELAY = 3000;
    /** The value in milliseconds used between goblin movements in level 1. */
    private final int LEVEL_ONE_DELAY = 400;
    /** The value in milliseconds used between goblin movements in level 2. */
    private final int LEVEL_TWO_DELAY = 330;
    /** The value in milliseconds used between goblin movements in level 3. */
    private final int LEVEL_THREE_DELAY = 250;
    /** The value in milliseconds used between goblin movements in level 4. */
    private final int LEVEL_FOUR_DELAY = 170;
    /** the value in milliseconds used between goblin movements in levels 5 and
     * up.
     */
    private final int LEVEL_FIVE_AND_UP_DELAY = 80;
    /**
     * Holds the boolean value that determines whether the timer should increment
     * its counter.
     */
    private boolean timerEnabled;
    /** Holds the current timer count in milliseconds. */
    private int eventTimerCount;
    /** Holds the value that will trigger a timed event. */
    private int triggerValue;
    /** Instance of the goblin model object. */
    private GoblinModel model;
    /** Instance of the goblin view object. */
    private GoblinView view;
    /** Instance of the timer object. */
    private Timer eventTimer = new Timer(true);

    //Constructor.
    /**
     * Sole constructor.
     */
    public GoblinController() {
        //Create an instance of the goblin model.
        model = new GoblinModel();

        //Create an instance of the view model and send a reference of the goblin
        //model to its constructor.
        view = new GoblinView(model);

        //Add the listeners instances for the keyboard, window, and pull-down
        //menus.
        view.addKeyboardListener(new KeyboardListener());
        view.addGameWindowListener(new GameWindowListener());
        view.addGameMenuListener(new GameMenuListener());
        view.addOptionsMenuListener(new OptionsMenuListener());
    }

    /**
     * This method is used to start the game when it is first run.  This must be
     * called first before any other method once the controller class is
     * instantiated.
     */
    public void startGame() {
        //Instruct the model to build the initial game grid.
        model.buildGameGrid();

        //Setup the timer to trigger at a fixed rate of 10 milliseconds.
        eventTimer.scheduleAtFixedRate(new EventTimer(), 10, 10);

        //Tell the view that the model is ready and repaint the entire screen.
        view.setIsReady(true);
        view.repaint();
    }

    /**
     * Method used to check the current state of the model, tell the model to
     * perform an action based on that state, check the state of the model after
     * the task is complete, and then determine which action(s) the view and
     * model should performed based on that state.  This method is called solely
     * from the timer event.
     */
    public void doModelStateCheck() {
        //Declarations.
        int levelDelay;

        //Based on the current game level get the delay between each goblin
        //movement and store it in the level delay variable.
        switch (model.getLevel()) {
            case 1:
                levelDelay = LEVEL_ONE_DELAY;
                break;
            case 2:
                levelDelay = LEVEL_TWO_DELAY;
                break;
            case 3:
                levelDelay = LEVEL_THREE_DELAY;
                break;
            case 4:
                levelDelay = LEVEL_FOUR_DELAY;
                break;
            default:
                levelDelay = LEVEL_FIVE_AND_UP_DELAY;
                break;
        }

        //Check the current state of the model before making any changes.
        switch (model.getCurrentState()) {
            case GoblinModel.STATE_BUILD_LEVEL:
                //The model is in a state where it is ready to build a new level.
                //Execute the level builder.
                model.buildLevel();
                break;
            case GoblinModel.STATE_BEGIN_LEVEL:
            case GoblinModel.STATE_LEVEL_RUNNING:
            case GoblinModel.STATE_GOBLIN_GOT_FACE:
                //The model is in a state where the goblin can be moved.
                //Move the goblin.
                model.moveGoblin();
                break;
            case GoblinModel.STATE_GOBLIN_DESTROYED:
                //The model is in a state where the last move resulted in the
                //goblin getting destroyed.  Clear the explosion off of the
                //screen.
                model.clearExplosion();
                break;
            case GoblinModel.STATE_LEVEL_CLEARED:
                //The model is in a state where the goblin got the last face
                //during the last move.  Tell the model to change it's state to
                //build level.
                model.changeStateToBuildLevel();
                break;
        }

        //Now check the state of the model after the changes.
        switch (model.getCurrentState()) {
            case GoblinModel.STATE_BEGIN_LEVEL:
                //When a level begins the level number is updated.  Since the
                //level number in the model changed tell the view to update the
                //window title where the level is shown.
                view.updateTitle();

                //Tell the view to repaint the entire screen since it is a new
                //level and everything on the screen changed.
                view.repaint();

                //Load a three second delay to allow the player time to prepare
                //before the first move.
                triggerValue = THREE_SECOND_DELAY;
                break;
            case GoblinModel.STATE_LEVEL_RUNNING:
                //A goblin move occured.  Tell the view to redraw only the
                //affected area of the screen.
                view.redrawArea();

                //Load the level delay into the timer delay.
                triggerValue = levelDelay;
                break;
            case GoblinModel.STATE_GOBLIN_GOT_FACE:
                //Tell the view to change the title since the score changed.
                view.updateTitle();

                //Tell the view to redraw only the affected area of the screen.
                view.redrawArea();

                //Load the level delay into the timer delay.
                triggerValue = levelDelay;
                break;
            case GoblinModel.STATE_GOBLIN_DESTROYED:
                //The goblin was detsroyed so all the shocked faces turned into
                //happy faces.  Tell the view to redraw the whole screen.
                view.repaint();

                //Load a one second delay into the timer delay to allow the
                //explosion to stay on the screen long enough for the player to
                //see it.
                triggerValue = ONE_SECOND_DELAY;
                break;
            case GoblinModel.STATE_LEVEL_CLEARED:
                //The goblin got the last face.  Tell the view to change the
                //title since the score would have changed.
                view.updateTitle();

                //Tell the view to redraw only the affected area of the screen.
                view.redrawArea();
                break;
            case GoblinModel.STATE_GAME_OVER:
                //Tell the view to change the title to display that the game is
                //over.
                view.updateTitle();

                //The explosion was cleared off the screen so have the view
                //redraw the affected area.
                view.redrawArea();

                //Stop the timer from counting since the game is over.  Either
                //the key listener or the pull-down event listener will start a
                //new game and re-trigger the timer.
                timerEnabled = false;
                break;
        }
    }

    /**
     * Inner class that handles the timer event.  It is setup to trigger an event
     * every ten milliseconds in this game.  When the trigger value is reached it
     * called the doModelStateCheck() method and resets itself.
     * 
     * @author Donald J Bartley
     * @version 1.0
     */
    private class EventTimer extends TimerTask {
        /**
         * Overrides the run() method of TimerTask.  Contains the code for
         * incrementing the count by 10, checking if the counter matches the
         * trigger value, and then executing the doModelStateCheck() method if it
         * does.
         */
        @Override
        public void run() {
            //If the timer is no enabled do nothing.
            if (timerEnabled) {
                //The timer triggered, increment the counter by ten (10) since
                //this timer fires every ten (10) milliseconds.
                eventTimerCount += 10;

                //If the count equals the trigger value then run the model state
                //checker and reset the counter.
                if (eventTimerCount == triggerValue) {
                    doModelStateCheck();

                    //Reset the counter.
                    eventTimerCount = 0;
                }
            }
        }
    }

    /**
     * Inner class responsible for capturing keyboard events from the view.  When
     * a keyboard event is captured the key press is compared with the ones used
     * by this game.  If it is either <A> or <L> and a game level is running then
     * they are added to the FIFO buffer of the model.  If it is the <Enter> key
     * and the game is over a new game is started.
     * 
     * @author Donald J Bartley
     * @version 1.0
     */
    private class KeyboardListener extends KeyAdapter {
        /**
         * Overrides the keyPressed() method of KeyAdapter.  Contains the code
         * for determining if the key press if one of the keys used by the game
         * and what action to take based on it and the state of the model.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            //There are only three keys that are needed for the game.  During
            //normal game play they are the 'A' and the 'L' keys.  When the game
            //is over the only key used is the enter key to start a new game.
            //Based of the state of the model check if the keypress is valid.
            if (model.getCurrentState() == GoblinModel.STATE_GAME_OVER) {
                //The game is over, if the key pressed was enter then start a new
                //game.
                if (e.getKeyCode() == GoblinModel.NEW_GAME) {
                    //Enter was pressed which means the user wants to play a new
                    //game.  Tell the model to reset itself.
                    model.reset();

                    //Call the model state checker so the game can be restarted
                    //and the first level drawn.
                    doModelStateCheck();

                    //Enable the timer that will be used to keep time for the
                    //game.
                    timerEnabled = true;
                }
            } else {
                //The game is running, if it was either the A or the L key then
                //send the keypress to the model.  'A' is for move left and 'L'
                //is for move right.
                if (e.getKeyCode() == GoblinModel.MOVE_LEFT
                        || e.getKeyCode() == GoblinModel.MOVE_RIGHT) {
                    model.setKeyPress(e.getKeyCode());
                }
            }
        }
    }

    /**
     * Inner class responsible for capturing window events from the view.  If the
     * window is closed the game is exited.
     * 
     * @author Donald J Bartley
     * @version 1.0
     */    
    private class GameWindowListener extends WindowAdapter {
        /**
         * Overrides the windowClosing() method of WindowAdapter.  Contains the
         * code for exiting the game if the window is closed.
         */
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    /**
     * Inner class responsible for capturing events from the Game pull-down menu.
     * 
     * @author Donald J Bartley
     * @version 1.0
     */ 
    private class GameMenuListener implements ActionListener {
        /**
         * Overrides the actionPerformed() method of ActionListener.  Contains
         * the code for handling when Start or Exit if selected from the Game
         * menu.
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            //If the Start menu item is selected and the model is in a state of
            //game over then start a new game.
            if (ae.getActionCommand().equals(GoblinView.START_GAME_MENU_ITEM)
                    && model.getCurrentState() == GoblinModel.STATE_GAME_OVER) {
                //Tell the model to reset itself.
                model.reset();

                //Clear the timer counter, load in a trigger value that will
                //cause it to trigger quickly, and then enabled the timer.
                eventTimerCount = 0;
                triggerValue = 100;
                timerEnabled = true;
            } else if (ae.getActionCommand().equals(GoblinView.EXIT_GAME_MENU_ITEM)) {
                //The Exit menu item was selected, exit the game.
                System.exit(0);
            }
        }
    }

    /**
     * Inner class responsible for capturing events from the Options pull-down
     * menu.
     * 
     * @author Donald J Bartley
     * @version 1.0
     */ 
    private class OptionsMenuListener implements ItemListener {
        /**
         * Overrides the itemStateChanged() method of ItemListener.  Contains the
         * code for handling when one of the option items if selected from the
         * Options menu.
         */
        @Override
        public void itemStateChanged(ItemEvent ie) {
            //If the model is in a state of game over then accept and change in
            //options.
            if (model.getCurrentState() == GoblinModel.STATE_GAME_OVER) {
                //The model is in the proper state.  Determine which option is
                //was that was selected and do the appropriate task.
                if (ie.getItem().toString().equals(GoblinView.SMALL_TILES_MENU_ITEM)) {
                    //The user set the tile size to small.  Change the model and
                    //tell the view to update the check marks in the Options
                    //menu.
                    model.setTileSize(GoblinModel.SMALL_TILE_SIZE);
                    view.setOptionsMenuTileSizeItem();
                } else if (ie.getItem().toString().equals(GoblinView.MEDIUM_TILES_MENU_ITEM)) {
                    //The user set the tile size to medium.  Change the model and
                    //tell the view to update the check marks in the Options
                    //menu.
                    model.setTileSize(GoblinModel.MEDIUM_TILE_SIZE);
                    view.setOptionsMenuTileSizeItem();
                } else if (ie.getItem().toString().equals(GoblinView.LARGE_TILES_MENU_ITEM)) {
                    //The user set the tile size to large.  Change the model and
                    //tell the view to update the check marks in the Options
                    //menu.
                    model.setTileSize(GoblinModel.LARGE_TILE_SIZE);
                    view.setOptionsMenuTileSizeItem();
                } else if (ie.getItem().toString().equals(GoblinView.SMALL_GRID_MENU_ITEM)) {
                    //The user set the tile grid to small.  Change the model and
                    //tell the view to update the check marks in the Options
                    //menu.
                    model.setGameGridSize(GoblinModel.SMALL_GRID_SIZE);
                    view.setOptionsMenuGridSizeItem();
                } else if (ie.getItem().toString().equals(GoblinView.MEDIUM_GRID_MENU_ITEM)) {
                    //The user set the grid size to medium.  Change the model and
                    //tell the view to update the check marks in the Options
                    //menu.
                    model.setGameGridSize(GoblinModel.MEDIUM_GRID_SIZE);
                    view.setOptionsMenuGridSizeItem();
                } else {
                    //The user set the grid size to large.  Change the model and
                    //tell the view to update the check marks in the Options
                    //menu.
                    model.setGameGridSize(GoblinModel.LARGE_GRID_SIZE);
                    view.setOptionsMenuGridSizeItem();
                }

                //Since the user changed either the grid size or the tile size
                //tell the model to build a whole new game grid, tell the view
                //the window size will need to be reset by changing the size set
                //value to false, and then tell the view to repaint the whole
                //screen.
                model.buildGameGrid();
                view.setSizeSet(false);
                view.repaint();
            } else {
                //The model is not in the right state, make sure the option check
                //marks do not change.  Call the methods in the view that set the
                //check marks to the state of the current model.
                view.setOptionsMenuTileSizeItem();
                view.setOptionsMenuGridSizeItem();
            }
        }
    }
}