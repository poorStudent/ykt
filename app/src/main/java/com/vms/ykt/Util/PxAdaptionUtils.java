package com.vms.ykt.Util;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public class PxAdaptionUtils {
    //基准屏幕宽
    private final int standardWith = 1080;
    //基准屏幕高
    private final int standradHeight = 2400;
    //屏幕分辨率
    private int screenWidth;
    private int screenHeight;
    private final float multipleWith;
    private final float multipleHeight;
    private static PxAdaptionUtils mPxAdaptionUtils;

    public PxAdaptionUtils(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        multipleWith = (float) standardWith / (float) screenWidth;
        multipleHeight = (float) standradHeight / (float) screenHeight;
    }
    public static PxAdaptionUtils initPxAdaptionUtils(Context context) {
        if (mPxAdaptionUtils==null)
            mPxAdaptionUtils =new PxAdaptionUtils(context);
        return mPxAdaptionUtils;
    }
    public int[] getAdaptWH(int width, int height) {
        //适配完的宽度
        float sWidth = 0;
        //适配完的高度
        float sHeight = 0;
        if (standardWith > screenWidth && standradHeight > screenHeight) {
            sWidth = (float) width / multipleWith;
            sHeight = (float) height / multipleHeight;
        } else if (standardWith < screenWidth && standradHeight < screenHeight) {
            sWidth = (float) width * (2.0f - multipleWith);
            sHeight = (float) height * (2.0f - multipleHeight);
        } else if (standardWith < screenWidth && standradHeight > screenHeight) {
            sWidth = (float) width * (2.0f - multipleWith);
            sHeight = (float) height / multipleHeight;
        } else if (standardWith > screenWidth && standradHeight > screenHeight) {
            sWidth = (float) width / multipleWith;
            sHeight = (float) height * (2.0f - multipleHeight);
        }
        //standradWH[0] 是宽  standradWH[1] 是高
        int[] standradWH = {(int) sWidth, (int) sHeight};
        return standradWH;
    }

    public List<Float> getAdaptWH(List<Float> listWh) {
        //集合从第一个元素开始，每两个元素是一对，索引为偶数的是宽
        List<Float> adaptWHList = new ArrayList<>();
        //看你传来的集合是否成对，如果成对，最后一个值，舍弃
        boolean isPair = true;
        if (listWh.size() % 2 == 0) {
            isPair = true;
        } else {
            isPair = false;
        }

        for (int i = 0; i < listWh.size(); i++) {
            float element = listWh.get(i);
            if (isPair) {
                if (i % 2 == 0) {
                    float adaptWidth = getAdaptWidth(element);
                    adaptWHList.add(adaptWidth);
                } else {
                    float adaptHeight = getAdaptHeight(element);
                    adaptWHList.add(adaptHeight);
                }
            } else {
                if (i == (listWh.size() - 1)) {
                    break;
                }
                if (i % 2 == 0) {
                    float adaptWidth = getAdaptWidth(element);
                    adaptWHList.add(adaptWidth);
                } else {
                    float adaptHeight = getAdaptHeight(element);
                    adaptWHList.add(adaptHeight);
                }
            }
        }
        return adaptWHList;
    }

    public List<Float> getAdaptWH(int[] arrWh) {
        //集合从第一个元素开始，每两个元素是一对，索引为偶数的是宽
        List<Float> adaptWHList = new ArrayList<>();
        //看你传来的集合是否成对，如果成对，最后一个值，舍弃
        boolean isPair = true;
        if (arrWh.length % 2 == 0) {
            isPair = true;
        } else {
            isPair = false;
        }

        for (int i = 0; i < arrWh.length; i++) {
            int element = arrWh[i];
            if (isPair) {
                if (i % 2 == 0) {
                    float adaptWidth = getAdaptWidth(element);
                    adaptWHList.add(adaptWidth);
                } else {
                    float adaptHeight = getAdaptHeight(element);
                    adaptWHList.add(adaptHeight);
                }
            } else {
                if (i == (arrWh.length - 1)) {
                    break;
                }
                if (i % 2 == 0) {
                    float adaptWidth = getAdaptWidth(element);
                    adaptWHList.add(adaptWidth);
                } else {
                    float adaptHeight = getAdaptHeight(element);
                    adaptWHList.add(adaptHeight);
                }
            }
        }
        return adaptWHList;
    }

    public float getAdaptHeight(int height) {
        //适配玩的高度
        float sHeight = 0;
        if (standardWith > screenWidth && standradHeight > screenHeight) {
            sHeight = (float) height / multipleHeight;
        } else if (standardWith < screenWidth && standradHeight < screenHeight) {
            sHeight = (float) height * (2.0f - multipleHeight);
        } else if (standardWith < screenWidth && standradHeight > screenHeight) {
            sHeight = (float) height / multipleHeight;
        } else if (standardWith > screenWidth && standradHeight > screenHeight) {
            sHeight = (float) height * (2.0f - multipleHeight);
        }
        return sHeight;
    }

    public float getAdaptWidth(int width) {
        //适配完的宽度
        float sWidth = 0;
        if (standardWith > screenWidth && standradHeight > screenHeight) {
            sWidth = (float) width / multipleWith;
        } else if (standardWith < screenWidth && standradHeight < screenHeight) {
            sWidth = (float) width * (2.0f - multipleWith);
        } else if (standardWith < screenWidth && standradHeight > screenHeight) {
            sWidth = (float) width * (2.0f - multipleWith);
        } else if (standardWith > screenWidth && standradHeight > screenHeight) {
            sWidth = (float) width / multipleWith;
        }
        return sWidth;
    }

    public float getAdaptHeight(float height) {
        //适配玩的高度
        float sHeight = 0;
        if (standardWith > screenWidth && standradHeight > screenHeight) {
            sHeight = height / multipleHeight;
        } else if (standardWith < screenWidth && standradHeight < screenHeight) {
            sHeight = height * (2.0f - multipleHeight);
        } else if (standardWith < screenWidth && standradHeight > screenHeight) {
            sHeight = height / multipleHeight;
        } else if (standardWith > screenWidth && standradHeight > screenHeight) {
            sHeight = height * (2.0f - multipleHeight);
        }
        return sHeight;
    }

    public float getAdaptWidth(float width) {
        //适配完的宽度
        float sWidth = 0;
        if (standardWith > screenWidth && standradHeight > screenHeight) {
            sWidth = width / multipleWith;
        } else if (standardWith < screenWidth && standradHeight < screenHeight) {
            sWidth = width * (2.0f - multipleWith);
        } else if (standardWith < screenWidth && standradHeight > screenHeight) {
            sWidth = width * (2.0f - multipleWith);
        } else if (standardWith > screenWidth && standradHeight > screenHeight) {
            sWidth = width / multipleWith;
        }
        return sWidth;
    }


}

