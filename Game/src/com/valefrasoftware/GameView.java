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
import com.valefrasoftware.control.Printer;
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
       private int contadorArriba=0;
       private int contadorLeft=0;
       private int contadorRigth=0;
       private int xBoton=10;
       private int yBoton=10;
       private int xFondo=-20;
       private int yFondo=-20;
       private Bitmap arbolito;
       boolean caminando;
       boolean caminandoArriba;
       boolean rigth;
       boolean left;
       boolean stop;
       Printer pin;
      
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
             fondo = BitmapFactory.decodeResource(getResources(), R.drawable.tierra);
             arbolito = BitmapFactory.decodeResource(getResources(), R.drawable.arbolillo);
             
             
              
       }

       
       
 
       @Override
       protected void onDraw(Canvas canvas) {
           System.out.println(System.getenv());
           int movimiento;
             
             
             // el if para la imagen inicial
        
      if(!stop){
          
          bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
           if(caminando){
            
            contador++;
            
            if (contador==1){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_left);
            }

            if (contador==2){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_right);
                contador=0;
            }
               
        }
           
            
        
        //caminando arriba
        
        if(caminandoArriba){
            
            contadorArriba++;
            
            if (contadorArriba==1){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.up_left);
            }
            if (contadorArriba==2){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.up_right);
                contadorArriba=0;
            }
         }
           
        
        
        //caminando derecha
        if(rigth){
               contadorRigth++;
            if (contadorRigth==1){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_left);
            }
            if (contadorRigth==2){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_left_2);
            contadorRigth=0;
            }
        }
           
        
        
        
        //caminando izquierda
        if(left){
            contadorLeft++;
            if (contadorLeft==1){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_rigth);
            }
            if (contadorLeft==2){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_right_2);
            contadorLeft=0;
            }
           }
        }
           
             intro++;
           
             y=y+ySpeed;
             x = x + xSpeed;
             movimiento=xFondo-x;
             canvas.drawColor(Color.GREEN);
             canvas.drawBitmap(fondo, movimiento , yFondo, null);
             canvas.drawBitmap(arbolito, movimiento+40 , 20, null);
             canvas.drawBitmap(analogo, getWidth() - analogo.getWidth() , getHeight()- analogo.getHeight(), null);
             
             
             
             canvas.drawBitmap(bmp, x , y, null);
             
             
             canvas.drawBitmap(botonA, (getWidth()-getWidth()+100) - botonA.getWidth() , getHeight()- botonA.getHeight(), null);
             canvas.drawBitmap(botonB, botonB.getWidth()+10 , getHeight()- botonB.getHeight(), null);
             
             xSpeed = 0;
             ySpeed=0;
             //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
             analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_1);
             botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a);

             
      
             
             
             
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

            
            
            
        }break;
        default:{
            stop=true;
            
        }break;
        
        }
        return true;
    } catch (Exception e) {
    
        return false;
   
    }
    
}

private void processMovement(float x1, float y1, float x2, float y2) {
    caminando=false;
    rigth=false;
    left=false;
    stop=false;
    int contador=0;
    caminandoArriba = false;
    
   // if(analogo.getWidth()/2 == x2 && analogo.getHeight()/2 == y2 && analogo.getWidth()/2 == x1 && analogo.getHeight()/2 == y1){
        
     //  bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
      //  caminandoArriba=false;
       // caminando=false;
       // rigth=false;
     //   left=false;
    //}else{
    if((x2>getWidth() - analogo.getWidth() && x2<getWidth()) && (y2>getHeight()- analogo.getHeight()) && y2<getHeight()){
    ///
     if (x2 < x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
        //Log.i("touch", "left");
        // move Left
        //setSpriteState(1);
        //x =0;
            //xFondo=+15;
            caminandoArriba=false;
            caminando=false;
            rigth=false;
            left=true;
            xSpeed = -10;  
            //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_left);
            
            
    } else if (x2 > x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
        //Log.i("touch", "right");
        // move right
        caminandoArriba=false;
        caminando=false;
        rigth=true;
        left=false;
        
        xSpeed = 10;
        //xFondo=-15;
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_rigth);
        //setSpriteState(2);
    } else if (y2 > y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "down");
        // move down
        //setSpriteState(4);
        caminandoArriba=false;
        caminando=true;
        rigth=false;
        left=false;
        
        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_down);
        ySpeed=+10;
        //yFondo=-5;
    } else if (y2 < y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "up");
        caminandoArriba=true;//
        caminando=false;
        rigth=false;
        left=false;
        // move up
        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_up);
        ySpeed=-10;
        //yFondo=+5;
        //setSpriteState(3);
    }else if (x1==x2 && y1==y2){
        //fire();
   }else{
        
        caminandoArriba=false;
        caminando=false;
        rigth=false;
        left=false;
        stop = true;
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
        xSpeed = 0; 
   // }
    }
  // }
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
                // move up
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_up_1);
//        ySpeed=-5;
        //setSpriteState(3);
    } else if (x1==x2 && y1==y2){
        //fire();
    }
    
    
}





}