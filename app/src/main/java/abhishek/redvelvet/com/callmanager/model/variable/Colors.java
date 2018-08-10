package abhishek.redvelvet.com.callmanager.model.variable;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by abhishek on 7/8/18.
 */

public class Colors {

    public Colors(){

    }

    public int getColor(){

        Random rand = new Random();

        int color = Color.argb(255,rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        return color;
    }
}
