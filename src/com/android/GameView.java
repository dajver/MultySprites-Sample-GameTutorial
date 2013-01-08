package com.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
 
public class GameView extends SurfaceView 
{
	/**Объект класса GameLoopThread*/
	private GameLoopThread gameLoopThread;
	
	/**Список спрайтов*/
    private List<Sprite> sprites = new ArrayList<Sprite>();

    /**Конструктор класса*/
    public GameView(Context context) 
    {
          super(context);
          
          gameLoopThread = new GameLoopThread(this);
          
          /*Рисуем все наши объекты и все все все*/
          getHolder().addCallback(new SurfaceHolder.Callback() 
          {
        	  	 /*** Уничтожение области рисования */
                 public void surfaceDestroyed(SurfaceHolder holder) 
                 {
                        boolean retry = true;
                        gameLoopThread.setRunning(false);
                        while (retry) 
                        {
                               try 
                               {
                                     gameLoopThread.join();
                                     retry = false;
                               } catch (InterruptedException e) {}
                        }
                 }

                 /** Создание области рисования */
                 public void surfaceCreated(SurfaceHolder holder) 
                 {
                        createSprites();
                        gameLoopThread.setRunning(true);
                        gameLoopThread.start();
                 }

                 /** Изменение области рисования */
                 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
                 {
                 }
          });
    }

    /**Создание всех спрайтов*/
    private void createSprites() 
    {
          sprites.add(createSprite(R.drawable.bad1));
          sprites.add(createSprite(R.drawable.bad2));
          sprites.add(createSprite(R.drawable.bad3));
          sprites.add(createSprite(R.drawable.bad4));
          sprites.add(createSprite(R.drawable.bad5));
    }

    /**Создаем спрайт на сцене*/
    private Sprite createSprite(int resouce) 
    {
          Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
          return new Sprite(this, bmp);
    }
    
    /**Функция рисующая все спрайты и фон*/
    protected void onDraw(Canvas canvas) 
    {
          canvas.drawColor(Color.BLACK);
          
          for (Sprite sprite : sprites) 
          {
                 sprite.onDraw(canvas);
          }
    }
}
 