/***************************************************************************
 *                                                                         *
 *   Copyright (C) 2003, 2026 Thomas Czyczko                               *
 *                                                                         *
 *   https://github.com/ca/linalg/lampp                                    *
 *                                                                         *
 ***************************************************************************
 **  This file is part of Lampp.
 **  Lampp is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the 
 **  Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 **  
 **  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 **  See the GNU Affero General Public License for more details.
 **  
 **  You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>. 
 ** 
 **************************************************************************
 **************************************************************************
 **
 **  @file   LamppMatrixButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief button to send matrix request events
 **  @copyright 2003, 2026 Thomas Czyczko
 **  This source code is part of the Lampp project. It includes a Linear Algebra Matrix aPP 
 **  program, to accompany the book Linear Algebra for Mouse, Pen and Pad.
 **  Copyright (C) 2003, 2023 Thomas Czyczko
 **  <https://github.com/ca/linalg/lampp> All Rights Reserved.
 **
 **  Lampp is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the 
 **  Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 **  
 **  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 **  See the GNU Affero General Public License for more details.
 **  
 **  You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>. 
 **
 *************************************************************************/

package ca.linalg.lampp;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * button to send matrix request events 
 */
public class LamppMatrixButton extends Group{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private final DropShadow ds;
    public boolean viewOnly;
    public boolean wasPressed=false;
    private final boolean borderRect;
    private final Text buttonText;
    private final Rectangle rect;
    private double th,tw;
    private int selected;
    private Integer buttonID;
    public static String eventName[]={"Matrix","MatrixFile","MatrixEdit"};
    private static long t0,t1;
    
    /**
     * @param xb the x coordinate of the bottom left of the button
     * @param yb the y coordinate of the bottom left of button
     * @param width the preferred width
     * @param height the preferred height
     * @param buttonName string name of button, fired
     * @param fontSize font size of button name
     * @param borderRect true - border with curved edges drawn around button
     * @param  buttonNum ID of button, fired
     */    
    public LamppMatrixButton (double xb,double yb, double width, double height,
            final String buttonName, final double fontSize, final int buttonNum,
            boolean borderRect){
        super();
        double xspace=5;
        buttonID=buttonNum;
        this.borderRect=borderRect;
        buttonText=new Text(buttonName);
        buttonText.setFont( new Font(buttonText.getFont().getName(), fontSize ));
        th=buttonText.getLayoutBounds().getHeight();
        height=Math.max(height,th);
        this.height=height;
        tw=buttonText.getLayoutBounds().getWidth();
        width=Math.max(width,tw);
        this.width=width;
        setTranslateX(xb);
        setTranslateY(yb);
        if(borderRect)xb=(width-tw)/2;
        else xb=2*xspace;
        yb=height/2+th/2-fontSize/4;
        if (borderRect)yb=height/2+th/4;
        buttonText.setTranslateX(xb);
        buttonText.setTranslateY(yb);
        rect = new Rectangle(0,0,width,height);
        ds = new DropShadow(); 
        rect.setFill(Color.TRANSPARENT);
        rect.setArcWidth(Math.min(fontSize,12));
        rect.setArcHeight(Math.min(fontSize,12));        
        if(borderRect){rect.setStroke(Color.BLACK);}
        rect.setStrokeWidth(0.8);    
        viewOnly=false;
        ds.setOffsetY(3.0f);        
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
        ae -> setExitedView()));
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!viewOnly&&!event.isSynthesized()){
                switch (selected){
                    case 1:
//                            toggleLeftSelected();
                        changes.firePropertyChange(eventName[0],buttonName,buttonID);
                        break;
                    case 2:
                        //middle button = swipe down = fire event to operation box
                        changes.firePropertyChange(eventName[1],buttonName,buttonID);
                        break;
                    case 3:
                    //right button = swipe right = fire event to edit box
//                            toggleRightSelected();
                        changes.firePropertyChange(eventName[2],buttonName,buttonID);
                }
                selected=0;
                }
                event.consume();
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isSynthesized()&&!viewOnly){
                   setExitedView();
                }
                wasPressed=false;
                event.consume();
            }
        });
        setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly&&!wasPressed){
                    //  element is touched - do same as mouse entered and left button. 
                    timeline.play();
                    t0 = System.nanoTime();
                    setEnteredView();
                    changes.firePropertyChange(eventName[0],buttonName,buttonID);          
                }
                event.consume();
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                    //  element is touched 
                    timeline.stop();
    // do same as mouse exited before the following release options
                    t1 = System.nanoTime()-t0;
                    if (t1<1000000000) {
    //  do something here when touched less than 1 second - addition to primary mouse button

                        changes.firePropertyChange(eventName[0],buttonName,buttonID);
                    }
    //  do something here when pressed more than 1.5 seconds
                    else {

                        changes.firePropertyChange(eventName[2],buttonName,buttonID);
                    }   
                wasPressed=false;
                event.consume();
            }
        });   
        this.getChildren().addAll(rect,buttonText);        
    }
        
    private void setEnteredView(){
        wasPressed=true;
        if(borderRect){buttonText.setFill(Color.WHITE);rect.setFill(Color.BLUE);}
        buttonText.setEffect(ds);
    }
    public void setExitedView(){
        wasPressed=false;
        buttonText.setEffect(null);
        if(borderRect){buttonText.setFill(Color.BLACK);rect.setFill(Color.TRANSPARENT);}
    }

//there should be no need to overwrite the following methods    
    
    public boolean wasPressed(){
        return wasPressed;
    }
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public void fire(String s){changes.firePropertyChange(s,s,null);}
    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
    }    
}
