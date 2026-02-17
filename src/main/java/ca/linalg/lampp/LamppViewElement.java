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
 **  @file   LamppViewElement.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief base class of matrix element and its display
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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * base class of matrix element and its display
 * fires matrix row and column coordinates to edit or use in a row/column operation
 * can be selected for input for a value from the editor
 * can be left or right selected for row/column operations via associated vector button
 * @author T. Czyczko
 */
public class LamppViewElement extends Group implements LamppGlobal{
// FLOOR and CEILING defined in LamppGlobal	
// Constructors and seven methods must be 
// overwritten in class extensions 
// direct extensions: LamppViewRational, LamppViewGaussian, LamppViewIntModP
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    public double height;
    public double width;
    public double x,y;
    public double xCenter, yCenter;
    public static double strokeWidth=5;
    public static DropShadow ds= new DropShadow();;
    public static InnerShadow is= new InnerShadow();;
    public Rectangle rect;
    public int selected;
    public int maxChar;
    private boolean viewOnly;
    private static long t0,t1;
    //event names fired: [0] middle button/down swipe [1] right button/right swipe
    static String eventName[]={"Editor","Operator","Input"};
    public boolean inputSelected;
    public boolean leftSelected;
    public boolean rightSelected;
    public boolean inside=false;
    int matrixPosition[];        
    boolean goodValue=true;
        
// Constructors  
    public LamppViewElement(){
        this(0, 0, 100, 100, 8, 0, 0);
    }
    /**
     * @param xx the x coordinate of the origin
     * @param yy the y coordinate of the origin
     * @param wi the preferred width
     * @param he the preferred height
     * @param maxC maximum characters to display in any text string
     * @param row matrix row number of element
     * @param column matrix column number of element
     */    
    public LamppViewElement(double xx, double yy,
            double wi, double he, int maxC,
            int row, int column){
        super();
        this.matrixPosition = new int[]{row, column};
        maxChar=maxC;
        x=xx;
        y=yy;
        width=wi;
        height=he;
        rect = new Rectangle(x,y,width,height);      
        xCenter=x + (width/2);
        yCenter=y + (height/2);
        common();
        this.getChildren().addAll(rect);
    }
    
// Methods
    
    // following methods should be overwritten for new classes
    public void setViewElement(Object element){
    }
    public Object getViewElement(){
        return null;
    }
    public void setFontSize(double fontSize){       
    }
    public void setEnteredView(){
    }
    public void setExitedView(){
    }
    public void setLeftSelected(){
        leftSelected=true;
        rightSelected=inputSelected=false;
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        rect.setEffect(null);          
        rect.setFill(Color.BLUE);        
        rect.setArcHeight(0);
        rect.setArcWidth(0);        
    }
    public void setRightSelected(){
        rightSelected=true;
        leftSelected=inputSelected=false;
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        rect.setFill(Color.GREY);
        rect.setEffect(null);   
        rect.setStrokeWidth(1);                  
        rect.setArcHeight(0);
        rect.setArcWidth(0);        
    }    
    public void setInputSelected(){
        inputSelected=true;
        rightSelected=leftSelected=false;
        rect.setArcHeight(height*0.3-2*strokeWidth);
        rect.setArcWidth(width*0.3-2*strokeWidth);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(strokeWidth);
        rect.setEffect(is);
        rect.setFill(Color.LIGHTGREEN);        
    }   
    public void setClearSelected(){
        inputSelected=rightSelected=leftSelected=false;
        rect.setEffect(null);             
        rect.setFill(Color.TRANSPARENT);
        rect.setStrokeWidth(0);            
        rect.setArcHeight(0);
        rect.setArcWidth(0);        
    }    

//there should be no need to overwrite the following methods    
    void common(){
        selected=0;
        viewOnly=false;
        is.setWidth(BUTTONWIDTH/2);
        is.setHeight(BUTTONHEIGHT/2);
        is.setOffsetX(BUTTONWIDTH/8);
        is.setOffsetY(BUTTONHEIGHT/8);
        is.setRadius(VIEW_SIZE/2);
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        inputSelected=rightSelected=leftSelected=false;
        rect.setFill(Color.TRANSPARENT);
        rect.setStrokeWidth(0);    
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               //mouse cursor enters node
               if(!inside&&!event.isSynthesized()){setEnteredView();inside=true;}
               event.consume();
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
        //mouse cursor leaves node
                if(inside&&!event.isSynthesized()){
                    setExitedView();
                inside=false;}
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
                            toggleInputSelected();
                            if(inputSelected)changes.firePropertyChange(eventName[2],matrixPosition,null);
                            break;
                        case 2:
                            //middle button = swipe down = fire event to operation box
                            changes.firePropertyChange(eventName[0],matrixPosition,null);
                            break;
                        case 3:
                        //right button = swipe right = fire event to edit box
                            changes.firePropertyChange(eventName[1],matrixPosition,null);
                    }
                }
                selected=0;
                }
                event.consume();
            }
        });
        setOnSwipeRight(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly){
                    //right button = swipe right = fire event to operation canvas
                   changes.firePropertyChange(eventName[1],matrixPosition,null);
                }
                selected=0;
                event.consume();
            }
        });
        setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly){
                    //middle button = swipe down = fire event to editor canvas
                   changes.firePropertyChange(eventName[0],matrixPosition,null);
                }
                selected=0;
                event.consume();
            }
        });
/*          
        setOnSwipeLeft(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly){
                    //middle button = swipe down = fire event to edit canvas
                    toggleLeftSelected();
                }
                selected=0;
                event.consume();
            }
        });
        setOnSwipeUp(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly){
                    //middle button = swipe down = fire event to edit canvas
                    toggleRightSelected();
                }
                selected=0;
                event.consume();
            }
        });
        
*/         
        setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly){
                    //  element is touched - do same as mouse entered. 
                    setEnteredView();          
                    //
                }
                t0 = System.nanoTime();
                selected=1;
                event.consume();
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                if(!viewOnly){
                    if (selected>0){
    // do same as mouse exited before the following release options
                        t1 = System.nanoTime()-t0;
                        if (t1<1500000000) {
    //  do something here when touched less than 1.5 seconds - addition to primary mouse button
                            toggleInputSelected();
                            if(inputSelected)changes.firePropertyChange(eventName[2],matrixPosition,null);
                        }
    //  do something here when pressed more than 1.5 seconds
    //                    else {
    //                        setViewElement("T+"+selected);
    //                    }
                    }
                }
                setExitedView();
                selected=0;
                event.consume();
            }
        });

    }
    public void setGoodValue(boolean x){
        goodValue=x;
        if(!x){
        rect.setFill(Color.RED);
        rect.setStrokeWidth(2);    
        }
    }
    public void toggleLeftSelected(){
        if(leftSelected){leftSelected=false; setClearSelected();}
        else {inputSelected=false; rightSelected=false;setLeftSelected();}
    }
    public void toggleRightSelected(){
        if(rightSelected){rightSelected=false; setClearSelected();}
        else {setRightSelected();inputSelected=false; leftSelected=false;}
    }
    public void toggleInputSelected(){
        if(inputSelected){inputSelected=false; setClearSelected();}
        else {setInputSelected();rightSelected=false;leftSelected=false;}
    }
    public static double bestFontSize(double fs){
        if(fs<=ALLOWEDFONTSIZES[0])return (ALLOWEDFONTSIZES[0]);
            int j=ALLOWEDFONTSIZES.length;
            for(int i=0;i<j-2;i++){
            if(fs>=ALLOWEDFONTSIZES[i]&&fs<ALLOWEDFONTSIZES[i+1])return (ALLOWEDFONTSIZES[i]);
        }
        return (ALLOWEDFONTSIZES[j-1]);
    }
    public void setViewOnly(boolean viewOnly){
        this.viewOnly=viewOnly;
    }
    public void setMatrixPosition(int matrixRow, int matrixColumn){
        this.matrixPosition[0]=matrixRow;
        this.matrixPosition[1]=matrixColumn;
    }
    public void setMatrixPosition(int matrixPosition[]){
        this.matrixPosition=matrixPosition;
    }
    public void setMatrixRow(int matrixRow){
        this.matrixPosition[0]=matrixRow;
    }
    public void setMatrixColumn(int matrixColumn){
        this.matrixPosition[1]=matrixColumn;
    }
    public int getMatrixRow(){
        return matrixPosition[0];
    }
    public int getMatrixColumn(){
        return matrixPosition[1];
    }
    public int[] getMatrixPosition(){
        return matrixPosition;
    }
    public boolean isInputSelected(){
        return inputSelected;
    }
    public boolean isVectorSelected(){
        return (leftSelected|rightSelected);
    }
    public boolean isLeftSelected(){
        return leftSelected;
    }
    public boolean isRightSelected(){
        return rightSelected;
    }
    public boolean isZero(){return false;}
    public boolean isGoodValue(){return goodValue;}
    public void additiveInverse(){}
    public void multiplicativeInverse(){}
    public void conjugate(){}

    public void op1(Object so){}
    public void op2(Object so){}
    public void op3(Object so,Object mo){}
    
    public double getX(){return x;}
    public double getY(){return y;}
    public double getXCenter(){return xCenter;}
    public double getYCenter(){return yCenter;}

    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
    }
}
