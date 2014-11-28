package com.appmunki.survival.util;

/**
 * Created by radzell on 10/7/14.
 */
public class EntityType {
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_WAYPOINTS = 0x002;
    public static final short CATEGORY_DROID = 0x0004;
    public static final short CATEGORY_SCENERY = 0x008;

    public static final short MASK_PLAYER = CATEGORY_DROID | CATEGORY_SCENERY;
    public static final short MASK_DROID = CATEGORY_PLAYER | CATEGORY_SCENERY;
    public static final short MASK_WAYPOINTS = 0;
    public static final short MASK_SCENERY = -1;
}
