package com.android;

import android.graphics.Canvas;

public class GameLoopThread extends Thread 
{
	/**Скорость передвижения обїектов = 10*/
	static final long FPS = 10;
	
	/**Объект класса*/
    private GameView view;
    
    /**Переменная запускающая поток рисования*/
    private boolean running = false;
   
    /**Конструктор класса*/
    public GameLoopThread(GameView view) 
    {
          this.view = view;
    }

    /**Задание состояния потока*/
    public void setRunning(boolean run) 
    {
          running = run;
    }

    /** Действия, выполняемые в потоке */
    public void run() 
    {
          long ticksPS = 1000 / FPS;
          
          /**Начало движения*/
          long startTime;
          
          /**Конец движения*/
          long sleepTime;
          
          
          while (running) 
          {
                 Canvas c = null;
                 startTime = System.currentTimeMillis();
                 try 
                 {
                        c = view.getHolder().lockCanvas();
                        synchronized (view.getHolder()) 
                        {
                               view.onDraw(c);
                        }
                 } 
                 finally 
                 {
                        if (c != null) 
                        {
                               view.getHolder().unlockCanvasAndPost(c);
                        }
                 }
                 sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                 try 
                 {
                        if (sleepTime > 0)
                               sleep(sleepTime);
                        else
                               sleep(10);
                 } catch (Exception e) {}
          }
    }
}  