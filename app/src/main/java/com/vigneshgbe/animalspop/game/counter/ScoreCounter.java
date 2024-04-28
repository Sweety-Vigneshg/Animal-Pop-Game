package com.vigneshgbe.animalspop.game.counter;

import android.widget.TextView;

import com.vigneshgbe.animalspop.R;
import com.vigneshgbe.animalspop.game.MyGameEvent;
import com.vigneshgbe.animalspop.level.MyLevel;
import com.nativegame.nattyengine.Game;
import com.nativegame.nattyengine.event.GameEvent;
import com.nativegame.nattyengine.entity.UIGameObject;

/**
 * Created by Oscar Liang on 2022/09/18
 */

public class ScoreCounter extends UIGameObject {

    private static final int POINTS_GAINED_PER_BUBBLE = 10;

    private final MyLevel mLevel;
    private final TextView mText;
    private int mPoints;

    public ScoreCounter(Game game) {
        super(game);
        mLevel = (MyLevel) game.getLevel();
        mText = (TextView) game.getGameActivity().findViewById(R.id.txt_score);
    }

    @Override
    public void onStart() {
        mPoints = 0;
        drawUI();
    }

    @Override
    public void onUpdate(long elapsedMillis) {
    }

    @Override
    protected void onDrawUI() {
        mText.setText(String.valueOf(mPoints));
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == MyGameEvent.BUBBLE_POP) {
            mPoints += POINTS_GAINED_PER_BUBBLE;
            mLevel.mScore = mPoints;
            drawUI();
        }
    }

}
