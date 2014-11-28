package com.appmunki.survival.Game;

import com.appmunki.survival.util.Util;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MapInputProcessor implements InputProcessor {

    Camera camera;
    Vector2 touchStart;
    boolean dragged;

    float divisor;

    GameScreen gameManager;
    float minCameraX;
    float maxCameraX;

    float amountDragged;

    public MapInputProcessor(GameScreen gameManager, Camera camera) {
        this.camera = camera;
        this.gameManager = gameManager;
        this.minCameraX = camera.viewportWidth / 2;
        divisor = Util.SCREEN_HEIGHT / 32;
        dragged = false;

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        if (!gameManager.gameHUD.pauseMenu.isVisible()) {
            touchStart = new Vector2(screenX, Util.SCREEN_HEIGHT - screenY);
//            gameManager.isTouchingScreen = true;
//        }
//
//        float positionYRound = Math.round((Util.SCREEN_HEIGHT - screenY) / divisor);
//        if (positionYRound >= 3 && positionYRound <= 8) {
//            gameManager.positionUnitCreation = positionYRound;
////            gameManager.ghostSprite.setNewPosition(5f, positionYRound);
//        }
//        if (gameManager.ghostSprite == gameManager.ghostObstacle) {
//            if (positionYRound >= 3 && positionYRound <= 8) {
//                gameManager.positionUnitCreation = positionYRound;
//                screenX += (int) ((camera.position.x - camera.viewportWidth / 2) * divisor);
////                gameManager.ghostSprite.setNewPosition(screenX / divisor, positionYRound);
//                dragged = false;
//            }
//        }
//
//        if(gameManager.healthPowerSelected){
//            screenX += (int) ((camera.position.x - camera.viewportWidth / 2) * divisor);
//            float positionY = (Util.SCREEN_HEIGHT - screenY) / divisor;
//            System.out.println(positionY);
//
//            gameManager.positionUnitCreation = positionY;
//            gameManager.healthArea.setCenterPosition(screenX / divisor, positionY);
//            dragged = false;
//        }
//
//
//        if(gameManager.healthPowerSelected){
//            gameManager.healthArea.setVisible(true);
//        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        if (!gameManager.gameHUD.pauseMenu.isVisible()) {
//            if (!dragged) {
                touchStart.x += (int) ((camera.position.x - camera.viewportWidth / 2) * divisor);
//                gameManager.touchUp(touchStart.x / divisor, touchStart.y / divisor);
//            }
//            dragged = false;
//            amountDragged = 0;
//            gameManager.isTouchingScreen = false;
//        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        Vector3 cameraPosition = camera.position;
        Vector2 touchDrag = new Vector2(screenX, screenY);

        //Recalculate the position on the screen to use multiple resolution
        float newScreenX = (touchDrag.x - touchStart.x) / divisor;
        camera.position.set(cameraPosition.x - newScreenX, cameraPosition.y, cameraPosition.z);


//        if (!gameManager.gameHUD.pauseMenu.isVisible()) {
//            Vector3 cameraPosition = camera.position;
//            Vector2 touchDrag = new Vector2(screenX, screenY);
//
//            //Recalculate the position on the screen to use multiple resolution
//            float newScreenX = (touchDrag.x - touchStart.x) / divisor;
//
//            amountDragged += Math.abs(cameraPosition.x - (cameraPosition.x - newScreenX));
//
//            if (gameManager.ghostSprite != gameManager.ghostObstacle&&!gameManager.healthPowerSelected) {
//
//                if ((cameraPosition.x - newScreenX) >= minCameraX && (cameraPosition.x - newScreenX) <= maxCameraX) {
//                    camera.position.set(cameraPosition.x - newScreenX, cameraPosition.y, cameraPosition.z);
//                }
//            }
//            touchStart = touchDrag;
//            if (amountDragged > 1.0f) {
//                dragged = true;
//            }
//
//            float positionYRound = Math.round((Util.SCREEN_HEIGHT - screenY) / divisor);
//            if (positionYRound >= 3 && positionYRound <= 8) {
//                gameManager.positionUnitCreation = positionYRound;
////                gameManager.ghostSprite.setNewPosition(5f, positionYRound);
//            }
//
//            if (gameManager.ghostSprite == gameManager.ghostObstacle) {
//                if (positionYRound >= 3 && positionYRound <= 8) {
//                    gameManager.positionUnitCreation = positionYRound;
//                    screenX += (int) ((camera.position.x - camera.viewportWidth / 2) * divisor);
////                    gameManager.ghostSprite.setNewPosition(screenX / divisor, positionYRound);
//                    dragged = false;
//                }
//            }
//
//            if(gameManager.healthPowerSelected){
//                screenX += (int) ((camera.position.x - camera.viewportWidth / 2) * divisor);
//                float positionY = (Util.SCREEN_HEIGHT - screenY) / divisor;
//
//                gameManager.positionUnitCreation = positionY;
//                gameManager.healthArea.setCenterPosition(screenX / divisor, positionY);
//                dragged = false;
//            }
//
//        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}