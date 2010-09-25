package com.googlecode.android4cs2.beetlegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/** The entry point for this game */
public class BeetleGameActivity extends Activity {
    
	/** A TextView which displays the current turn 
	 * @see TextView */
	private TextView turn;
	
	/** An ImageView which provides an image of a die
	 * @see Die 
	 * @see ImageView */
	private DieView dv;
	
	/** An array containing references to the BeetleView objects */
	private BeetleView[] surfaces = new BeetleView[2];
	
	/** An array containing references to the Beetle objects of the BeetleViews */
	private Beetle[] bugs = new Beetle[2];
	
	/** Controls who plays and what parts are displayed */
	private Die die;
	
	/** An integer (0 or 1) which keeps track of the current player */
	private int player = 0;
	
	/** An array containing resource IDs for the player labels */
	private int[] labels = { R.string.playerone, R.string.playertwo };
	
	/** An array containing resource IDs for the winner labels*/
	private int[] winners = { R.string.playerone_win, R.string.playertwo_win };
	
	/** Listens for clicks from the user on whatever View it is assigned */
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!bugs[player].isComplete()) {
				die.roll();
				// This flag lets us know when to change players
				boolean breakOut = false;
				
				dv.updateImages();
				
				// If the player rolls such that he cannot add the body part he rolled, break out of this loop and switch players
				switch (die.getTopFace()) {
				case 1:
					breakOut = !bugs[player].addBody();
					break;
				case 2:
					breakOut = !bugs[player].addHead();
					break;
				case 3:
					breakOut = !bugs[player].addLeg();
					break;
				case 4:
					breakOut = !bugs[player].addEye();
					break;
				case 5:
					breakOut = !bugs[player].addFeeler();
					break;
				case 6:
					breakOut = !bugs[player].addTail();
					break;
				default:
					break;
				}
				
				surfaces[player].updateImages(); // Update the current player's BeetleView image...
				
				// Otherwise, if the player can't add anything to his bug this turn, move on to the next player
				if (breakOut) {
					surfaces[player].setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
					player = 1 - player;
					surfaces[player].clearColorFilter();
					turn.setText(labels[player]);
					return;
				}
			}
			
			// If the player won, say so
			if (bugs[player].isComplete()) {
				turn.setText(winners[player]);
				return;
			}
		}
		
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Get references to all widgets that we must alter
        turn = (TextView) findViewById(R.id.turn);
        surfaces[0] = (BeetleView) findViewById(R.id.bug_one);
        surfaces[1] = (BeetleView) findViewById(R.id.bug_two);
        dv = (DieView) findViewById(R.id.die);
        
        // Create a new game
        newGame();
        
        // Register the OnClickListener
        dv.setOnClickListener(listener);
    }
    
    /**
     * Inflates the Options Menu.
     * @param menu The menu which is being inflated.
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * Called when an item from the Options Menu is selected.
     * @param item The item which was selected.
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	// If the user wants a new game, we'll give him one
    	case R.id.newGame:
    		newGame();
    		return true;
    	// Otherwise, if they need help, they can have that too
    	case R.id.help:
    		Intent i = new Intent(BeetleGameActivity.this, HelpActivity.class);
    		startActivity(i);
    		return true;
    	default:
    		return false;
    	}
    }
    
    /**
     * Starts a new game 
     */
    public void newGame() {
    	die = new Die();
    	die.roll();
    	dv.setDie(die);
    	dv.updateImages();
    	
    	for (int i = 0; i < 2; i++) {
    		bugs[i] = new Beetle();
    		surfaces[i].setBug(bugs[i]);
    		surfaces[i].updateImages();
    	}
    	
    	turn.setText(labels[0]);
    	player = 0;
    }
}