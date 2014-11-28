package com.appmunki.survival;

/**
 * Created by radzell on 8/9/14.
 */
public interface GameHelper {
    public boolean isSignedIn();

    public boolean hasSignInError();

    public void signOut();

    public void signIn();

    public String getResourceString(String name);

    public void createRoom();

    public void inviteFriends();
    public void showInvitations();

    public void sendMessage(String message);

    public void sendUnreliableMessage(String message);

}
