/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.valefrasoftware;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

 
 
public class GameView extends SurfaceView {
       private Bitmap bmp;
       private Bitmap analogo;
       private Bitmap botonA;
       private Bitmap botonB;
       private Bitmap fondo;
       private int intro=0;
       Button button1;
       private SurfaceHolder holder;
       private GameLoopThread gameLoopThread;
       private int x = 100;
       private int y=200;
       private int xSpeed = 1;
       private int ySpeed = 1;
       private long lastClick;
       private int contador=0;
       private int xBoton=10;
       private int yBoton=10;
       private int xFondo=-20;
       private int yFondo=-20;
       private Bitmap arbolito;
       boolean caminando;
      
       public GameView(Context context) {
             super(context);
             gameLoopThread = new GameLoopThread(this);
             holder = getHolder();
             holder.addCallback(new SurfaceHolder.Callback() {
 
                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                           boolean retry = true;
                           gameLoopThread.setRunning(false);
                           while (retry) {
                                  try {
                                        gameLoopThread.join();
                                        retry = false;
                                  } catch (InterruptedException e) {
                                  }
                           }
                    }
 
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                           gameLoopThread.setRunning(true);
                           gameLoopThread.start();
                    }
 
                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format,
                                  int width, int height) {
                    }
                    
                    
                    
             });
             
             
             
           
             
             
             
             //dibujo inicial
             //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c1);}
             bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
             analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_1);
             botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a);
             botonB=BitmapFactory.decodeResource(getResources(), R.drawable.boton_b);
             fondo = BitmapFactory.decodeResource(getResources(), R.drawable.basefondo);
             arbolito = BitmapFactory.decodeResource(getResources(), R.drawable.arbolillo);
             
              
       }

       
       
 
       @Override
       protected void onDraw(Canvas canvas) {
           System.out.println(System.getenv());
//             if (x == getWidth() - bmp.getWidth()) {
//                    xSpeed = -1;
//             }
//             if (x == 0) {
//                    xSpeed = 5;
//             }
           
//           if(intro==1){
//               bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tran_1);      
//             }
//             if(intro==2){
//               bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tran2);   
//             }
//             if(intro==3){
//               bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tran_3);   
//             }
//             if(intro==4){
//              bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tran_4);    
//             }
//             if(intro==5){
//              bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tran_5);   
//             }
//             if(intro==6){
//              bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tran_6);   
//             }
           
           if(caminando){
               contador++;
           }
           
           if (contador==1){
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_left);
        }
        if (contador==2){
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_right);
        }
        
        
        
        if (contador==2){
            contador=0;
        }
           
           
             
             intro++;
           
             y=y+ySpeed;
             x = x + xSpeed;
             
             canvas.drawColor(Color.BLACK);
             canvas.drawBitmap(fondo, xFondo , yFondo, null);
             canvas.drawBitmap(arbolito, 20 , 20, null);
             canvas.drawBitmap(analogo, getWidth() - analogo.getWidth() , getHeight()- analogo.getHeight(), null);
             canvas.drawBitmap(bmp, x , y, null);
             canvas.drawBitmap(botonA, (getWidth()-getWidth()+100) - botonA.getWidth() , getHeight()- botonA.getHeight(), null);
             canvas.drawBitmap(botonB, botonB.getWidth()+10 , getHeight()- botonB.getHeight(), null);
             
             xSpeed = 0;
             ySpeed=0;
             //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
             analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_1);
             botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a);
//             if(intro>6){
//             bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c1);    
//             }
             
             bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
             // el if para la imagen inicial
             
             
             
       }

    
    
    
     
       
      
       
       
        @Override
       public boolean onTouchEvent(MotionEvent event) {
    try {
        
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
        case (MotionEvent.ACTION_MOVE): {
            float firstx = event.getHistoricalX(0);
            float firsty = event.getHistoricalY(0);
            float lastx = event.getX();
            float lasty = event.getY();

            processMovement(firstx, firsty, lastx, lasty);
            pressButon(firstx, firsty, lastx, lasty);

            break;
        }
        }
    } catch (Exception e) {
    }
    return true;
}

private void processMovement(float x1, float y1, float x2, float y2) {
    caminando=false;
    int contador=0;
    if((x2>getWidth() - analogo.getWidth() && x2<getWidth()) && (y2>getHeight()- analogo.getHeight()) && y2<getHeight()){
    ///
    if (x2 < x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
        //Log.i("touch", "left");
        // move Left
        //setSpriteState(1);
        //x =0;
            //xFondo=+15;
            xSpeed = -10;  
            //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_left);
            
            
    } else if (x2 > x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
        //Log.i("touch", "right");
        // move right
        xSpeed = 10;
        //xFondo=-15;
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_rigth);
        //setSpriteState(2);
    } else if (y2 > y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "down");
        // move down
        //setSpriteState(4);
//        if (contador==0){
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_left);
//        }
//        if (contador==1){
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_right);
//        }
//        
//        contador++;
//        
//        if (contador==1){
//            contador=0;
//        }
        caminando=true;
        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_down);
        ySpeed=+10;
        //yFondo=-5;
    } else if (y2 < y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "up");
        
        // move up
        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_up);
        ySpeed=-10;
        //yFondo=+5;
        //setSpriteState(3);
    } else if (x1==x2 && y1==y2){
        //fire();
    }
    }
    //
}
    
    
    public void pressButon(float x1, float y1, float x2, float y2) {
    if ((x2 > ((getWidth()-getWidth()+100) - botonA.getWidth())  &&  x2<(getWidth()-getWidth()+100)) 
            && (y2>getHeight()- botonA.getHeight()) && y2<getHeight()) {
        //Log.i("touch", "left");
        // move Left
        //setSpriteState(1);
        //x =0;
//            xSpeed = -10;  
//            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
//            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_left_1);
        botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a_2);
        bmp=BitmapFactory.decodeResource(getResources(), R.drawable.pu_2);
        //bmp.setPixel(70, 80, contador);    
    } else if (x2 > x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
//        //Log.i("touch", "right");
//        // move right
//        xSpeed = 10;
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_rigth_1);
        //setSpriteState(2);
    } else if (y2 > y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "down");
        // move down
        //setSpriteState(4);
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_down_1);
//        ySpeed=+5;
    } else if (y2 < y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "up");
        
//        // move up
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_up_1);
//        ySpeed=-5;
        //setSpriteState(3);
    } else if (x1==x2 && y1==y2){
        //fire();
    }
    
    
}





}