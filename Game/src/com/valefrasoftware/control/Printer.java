/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.valefrasoftware.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;
import com.valefrasoftware.R;

/**
 *
 * @author fmeneses
 */
public class Printer extends SurfaceView{
    
    
    private Bitmap bmp;

    public Printer(Context context) {
        super(context);
    }
    
    public void pintar (){
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
    }
    
}
