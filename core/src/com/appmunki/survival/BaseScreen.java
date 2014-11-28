package com.appmunki.survival;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.Screen;

/**
 * Created by diegoamezquita on 8/14/14.
 */
public interface BaseScreen extends Screen {

    public void onPlayServiceEvent(Util.EVENT event,String data);

}
