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
 **  @file   LamppVectorButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief button used to select row or column vector for row or column operation
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
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.*;
import javafx.util.Duration;
/**
 * button used to select row or column vector for row or column operation
 */
public class LamppVectorButton extends Group{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private boolean viewOnly;
    private boolean wasPressed=false;
    private final boolean borderRect;
    private final Text buttonText;
    private final Rectangle rect;
    private final double th,tw;
    private boolean leftSelected;
    private boolean rightSelected;
    private int selected;
    private boolean inside=false;
    private int matrixPosition;
    public static String eventName[]={"Left","Middle","Right"};
    private static long t0,t1;

    /**
     * @param xb the x coordinate of the origin
     * @param yb the y coordinate of the origin
     * @param width the preferred width
     * @param height the preferred height
     * @param buttonName string representation of integer row or column number (1 to 10) associated with the button
     * @param fontSize font size of button number
     * @param borderRect true - border with curved edges drawn around button
     */    
    public LamppVectorButton (double xb,double yb, double width, double height,
            final String buttonName, final double fontSize,
            boolean borderRect){
        super();
        double xspace=5;
//        double yspace=5;
        this.borderRect=borderRect;
        matrixPosition=Integer.valueOf(buttonName)-1;
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
        rightSelected=leftSelected=false;
        rect.setFill(Color.TRANSPARENT);
        rect.setArcWidth(fontSize);
        rect.setArcHeight(fontSize);        
        if(borderRect){rect.setStroke(Color.BLACK);}
        rect.setStrokeWidth(0.9);    
        viewOnly=false;
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
        ae -> setRightView()));
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               //mouse cursor enters node
               if(!inside&&!event.isSynthesized()){
                   setEnteredView();
                   inside=true;
                }
                event.consume();
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
        //mouse cursor leaves node
                if(inside&&!event.isSynthesized()){
                    setExitedView();
                    inside=false;
                }
                event.consume();
            }
        });
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!viewOnly&&!event.isSynthesized()){
                    if (event.isPrimaryButtonDown()){selected=1;}
                    //middle button = swipe down = fire event to operation box
                    else if (event.isMiddleButtonDown()){selected=2;}
                    //right button = swipe right = fire event to edit box
                    else if (event.isSecondaryButtonDown()){selected=3;}
                }
                event.consume();
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (inside&&!event.isSynthesized()){
                if(!viewOnly){
                    switch (selected){
                        case 1:
                            toggleLeftSelected();
                            changes.firePropertyChange(eventName[0],matrixPosition,null);
                            break;
                        case 2:
                            //middle button = swipe down = fire event to operation box
                            changes.firePropertyChange(eventName[1],matrixPosition,null);
                            break;
                        case 3:
                        //right button = swipe right = fire event to edit box
                            toggleRightSelected();
                            changes.firePropertyChange(eventName[2],matrixPosition,null);
                    }
                }
                selected=0;
                }
                event.consume();
            }
        });
        setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly){
                    timeline.play();
                    t0 = System.nanoTime();
                    setLefttView();
                    event.consume();
                }
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly){
                    //  element is touched 
                    timeline.stop();
    // do same as mouse exited before the following release options
                    t1 = System.nanoTime()-t0;
                    if (t1<1000000000) {
    //  do something here when touched less than 1 second - addition to primary mouse button
                        toggleLeftSelected();
                        changes.firePropertyChange(eventName[0],matrixPosition,null);
                    }
    //  do something here when pressed more than 1.5 seconds
                    else {
                        toggleRightSelected();
                        changes.firePropertyChange(eventName[2],matrixPosition,null);
                    }                    
                }
                event.consume();
            }
        });

        
        
        this.getChildren().addAll(rect,buttonText);        
    }
    public void toggleLeftSelected(){
        if(leftSelected){setClearLeft();}
        else {setLeftSelected();}
    }
    public void toggleRightSelected(){
        if(rightSelected){setClearRight();}
        else {setRightSelected();}
    }

    private void setEnteredView(){
        if(viewOnly)return;
        wasPressed=true;
        if(borderRect){buttonText.setFill(Color.WHITE);buttonText.setStroke(Color.WHITE);rect.setFill(Color.LIGHTBLUE);}
    }
    private void setLefttView(){
        if(viewOnly)return;
        if(borderRect){buttonText.setFill(Color.WHITE);buttonText.setStroke(Color.WHITE);rect.setFill(Color.BLUE);}
    }
    private void setRightView(){
        if(viewOnly)return;
        if(borderRect){buttonText.setFill(Color.WHITE);buttonText.setStroke(Color.WHITE);rect.setFill(Color.GREY);}
    }
    public void setExitedView(){
        if(viewOnly)return;
        wasPressed=false;
        if(rightSelected){
            buttonText.setFill(Color.WHITE);
            buttonText.setStroke(Color.WHITE);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.GREY);
            rect.setEffect(null);   
        }
        else if(leftSelected){
            buttonText.setFill(Color.WHITE);
            buttonText.setStroke(Color.WHITE);
            rect.setStroke(Color.BLACK);
            rect.setEffect(null);          
            rect.setFill(Color.BLUE);        
        }
        else{
        buttonText.setEffect(null);
            if(borderRect){setClear();}
        }        
    }
    public void setClearSelected(){
        setClearLeft();             
        setClearRight();
    }    
    public void setClearLeft(){
        if(!leftSelected)return;
        leftSelected=false;
        setClear();             
    }    
    public void setClearRight(){
        if(!rightSelected)return;
        rightSelected=false;
        setClear();             
    }    
    private void setClear(){
        rect.setEffect(null);             
        rect.setFill(Color.TRANSPARENT);
        buttonText.setFill(Color.BLACK);
        buttonText.setStroke(null);
    }

    public void setLeftSelected(){
        setSelected(false);
    }
    public void setRightSelected(){
        setSelected(true);  
    }    
    private void setSelected(boolean right){
        buttonText.setFill(Color.WHITE);
        buttonText.setStroke(Color.WHITE);        
        rightSelected=right;
        leftSelected=!right;
        rect.setStroke(Color.BLACK);
        if(right)rect.setFill(Color.GREY);
        else rect.setFill(Color.BLUE);
        rect.setEffect(null);   
    }
    public int getMatrixPosition(){return matrixPosition;}

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
    public void setViewOnly(boolean x){viewOnly=x;}
    public boolean isLeftSelected(){return leftSelected;}
    public boolean isRightSelected(){return rightSelected;}
    
}
