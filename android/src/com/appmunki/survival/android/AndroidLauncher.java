package com.appmunki.survival.android;

import android.content.Intent;
import android.os.Bundle;

import com.appmunki.survival.MyGdxGame;
import com.appmunki.survival.util.Util;
import com.appmunki.survival.android.basegameutils.AndroidGameHelper;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication {
    private AndroidGameHelper mAndroidGameHelper;


    public MyGdxGame gdxGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useImmersiveMode = true;
        mAndroidGameHelper = new AndroidGameHelper(this, AndroidGameHelper.CLIENT_GAMES);
//        mAndroidGameHelper.setConnectOnStart(true);
        mAndroidGameHelper.enableDebugLog(true, "GameHelper");
        AndroidGameHelper.GameHelperListener mGameListener = new AndroidGameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
                mAndroidGameHelper.makeSimpleDialog("Signed In failed");
                if (gdxGame != null)
                    gdxGame.onRoomEvent(Util.EVENT.SIGNIN_FAIL, "");
            }

            @Override
            public void onSignInSucceeded() {
                mAndroidGameHelper.makeSimpleDialog("Signed In game");
                if (gdxGame != null)
                    gdxGame.onRoomEvent(Util.EVENT.SIGNIN_SUCCESS, "");

            }
        };
        mAndroidGameHelper.setup(mGameListener);
        mAndroidGameHelper.setAndroidLauncher(this);

        gdxGame = new MyGdxGame(mAndroidGameHelper);
        initialize(gdxGame, config);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAndroidGameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAndroidGameHelper.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAndroidGameHelper.onActivityResult(requestCode, resultCode, data);
    }

}
