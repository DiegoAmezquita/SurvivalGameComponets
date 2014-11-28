package com.appmunki.survival.util;

import com.appmunki.survival.GameHelper;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by diegoamezquita on 8/6/14.
 */
public class Util {

    public static boolean debugEnable = false;



    public enum EVENT {
        SIGNIN_SUCCESS,
        SIGNIN_FAIL,
        ROOM_CREATED_SUCCESS,
        ROOM_JOINED,
        USER_JOIN,
        INVITATION_RECEIVED,
        MESSAGE_RECEIVED,
        PEER_LEFT
    }

    public enum UNIT_TYPE{
        MELEE,
        MELEE_WOMEN,
        RANGE,
        EXPLORER,
        CONSTRUCTOR,
        OBSTACLE
    }


    public enum POWER{
        HEALTH
    }
    public enum TEAM {
        LEFT,
        RIGHT,
    }

    public static String getPath() {
        String pathFiles = "";
        if (System.getProperty("os.name").contains("Windows") && Gdx.app.getType() == Application.ApplicationType.Desktop) {
            pathFiles = "C:/Users/Seidan/Dropbox/GameWar/SurvivalGame/android/assets/";
//            pathFiles = "";
        } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            pathFiles = "/Users/diegoamezquita/Dropbox/GameWar/SurvivalGame/android/assets/";
        }

        System.out.println("Path: "+pathFiles);

        return pathFiles;
    }
    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static final float factor = (Util.SCREEN_WIDTH/960f);


    public static String getString(GameHelper gameHelper, String name) {
        if (gameHelper == null) {
            return name;
        }
        String value = gameHelper.getResourceString(name);
        return value != null ? value : name;
    }
}
