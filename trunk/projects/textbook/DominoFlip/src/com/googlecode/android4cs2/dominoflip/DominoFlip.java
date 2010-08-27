package com.googlecode.android4cs2.dominoflip;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class DominoFlip extends Activity implements OnTouchListener {
	
	private Domino d;
	private Button random;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        d = new ArrayDomino(1,1);
        random = (Button) findViewById(R.id.random);
        random.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				randomize((int)(Math.random()*6)+1, (int)(Math.random()*6)+1);
			}
        	
        });
        randomize((int)(Math.random()*6)+1, (int)(Math.random()*6)+1);
    }
    
    public void randomize(int left, int right) {
    	d = new ArrayDomino(left, right);
    	draw((ImageView) findViewById(R.id.left), d.getLeft());
    	draw((ImageView) findViewById(R.id.right), d.getRight());
    }

    public void draw(ImageView view, int number) {
		switch (number) {
		case 1: view.setImageResource(R.drawable.die1);
		break;
		case 2: view.setImageResource(R.drawable.die2);
		break;
		case 3: view.setImageResource(R.drawable.die3);
		break;
		case 4: view.setImageResource(R.drawable.die4);
		break;
		case 5: view.setImageResource(R.drawable.die5);
		break;
		case 6: view.setImageResource(R.drawable.die6);
		break;
		default: view.setImageResource(R.drawable.die1);
		break;
		}

    }
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// d.flip();
		return false;
	}
}