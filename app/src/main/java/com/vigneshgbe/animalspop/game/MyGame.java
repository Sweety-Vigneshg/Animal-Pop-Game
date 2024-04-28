package com.vigneshgbe.animalspop.game;

import android.widget.ImageView;

import com.vigneshgbe.animalspop.MainActivity;
import com.vigneshgbe.animalspop.R;
import com.vigneshgbe.animalspop.game.animal.Animal;
import com.vigneshgbe.animalspop.game.counter.MoveCounter;
import com.vigneshgbe.animalspop.game.counter.ProgressBarCounter;
import com.vigneshgbe.animalspop.game.counter.ScoreCounter;
import com.vigneshgbe.animalspop.game.counter.combo.ComboCounter;
import com.vigneshgbe.animalspop.game.counter.target.CollectTargetCounter;
import com.vigneshgbe.animalspop.game.counter.target.PopTargetCounter;
import com.vigneshgbe.animalspop.game.input.InputController;
import com.vigneshgbe.animalspop.level.LevelType;
import com.vigneshgbe.animalspop.level.MyLevel;
import com.vigneshgbe.animalspop.ui.dialog.PauseDialog;
import com.nativegame.nattyengine.Game;
import com.nativegame.nattyengine.GameView;
import com.nativegame.nattyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/12/10
 */

public class MyGame extends Game {

    public MyGame(GameActivity activity, GameView gameView, int level) {
        super(activity, gameView);
        setPixelFactor(3300);
        setTouchController(new InputController(this));
        setSoundManager(getGameActivity().getSoundManager());
        setLevel(getGameActivity().getLevelManager().getLevel(level));
        // setDebugMode(true);

        // Init all the object and add to game
        new GameController(this).addToGame();
        new MoveCounter(this).addToGame();
        new ComboCounter(this).addToGame();
        new ScoreCounter(this).addToGame();
        new ProgressBarCounter(this).addToGame();
        setTargetCounter(((MyLevel) getLevel()).mLevelType);
        new Animal(this).addToGame();
    }

    private void setTargetCounter(LevelType levelType) {
        // Init target image
        ImageView imageTarget = (ImageView) getGameActivity().findViewById(R.id.image_target);
        imageTarget.setImageResource(levelType.getTargetDrawableId());
        switch (levelType) {
            case POP_BUBBLE:
                new PopTargetCounter(this).addToGame();
                break;
            case COLLECT_ITEM:
                new CollectTargetCounter(this).addToGame();
                break;
        }
    }

    @Override
    protected void onStart() {
        // Start bgm
        getGameActivity().getSoundManager().loadMusic(R.raw.village);
    }

    @Override
    protected void onPause() {
        showPauseDialog();
    }

    @Override
    protected void onStop() {
        // Stop the game bgm
        getGameActivity().getSoundManager().unloadMusic();
        // Start the menu bgm
        getGameActivity().getSoundManager().loadMusic(R.raw.happy_and_joyful_children);
    }

    private void showPauseDialog() {
        PauseDialog dialog = new PauseDialog(getGameActivity(), getLevel().mLevel) {
            @Override
            public void resumeGame() {
                resume();
            }

            @Override
            public void quitGame() {
                // Reduce one live
                ((MainActivity) getGameActivity()).getLivesTimer().reduceLive();
                // Navigate back to map
                getGameActivity().navigateBack();
                // The GameEngine will stop automatically when fragment destroy
            }
        };
        getGameActivity().showDialog(dialog);
    }

}
