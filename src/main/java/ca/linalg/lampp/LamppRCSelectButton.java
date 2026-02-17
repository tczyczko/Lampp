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
 **  @file   LamppRCSelectButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief button to allow selection of row or column operations
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


/**
 * button to allow selection of row or column operations
 */
public class LamppRCSelectButton extends Group implements LamppGlobal{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private final Rectangle rect;
    private static boolean row=true;
    private int selected;
    private boolean inside=false;
    public static String eventName="RCToggle";
    private boolean viewOnly=false;
    private final double xr,yr,rw,rh,cw,ch,xc,yc;
     /**
     * @param xb the x coordinate of the origin
     * @param yb the y coordinate of the origin
     * @param width the preferred width
     * @param height the preferred height
     * @param fontSize size of arc width/height (same as fontSize for vector buttons)
     */    
    public LamppRCSelectButton (double xb,double yb, double width, double height,final double fontSize){
        super();
        this.height=height;
        this.width=width;
        rw=BUTTONWIDTH*1.25;
        cw=rw/2;
        ch=BUTTONHEIGHT*1.25;
        rh=ch/2;
        xc=(width-cw)/2;
        xr=(width-rw)/2;
        yr=(height-rh)/2;
        yc=(height-ch)/2;
        rect = new Rectangle(xr,yr,rw,rh);
        rect.setFill(Color.TRANSPARENT);
        rect.setArcWidth(fontSize*1.25);
        rect.setArcHeight(fontSize*1.25);        
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(0.9);    
        rect.setTranslateX(xr);
        rect.setTranslateY(yr);

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
                            toggleSelected();
                            changes.firePropertyChange(eventName,null,null);
                            break;
                        case 2:
                            //middle button = swipe down = fire event to operation box
                            toggleSelected();
                            changes.firePropertyChange(eventName,null,null);
                            break;
                        case 3:
                        //right button = swipe right = fire event to edit box
                            toggleSelected();
                            changes.firePropertyChange(eventName,null,null);
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
                    setEnteredView();
                    event.consume();
                }
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly){
                    //  element is touched 
                    toggleSelected();
                    changes.firePropertyChange(eventName,null,null);
                    setExitedView();
                }
                event.consume();
            }
        });
        this.getChildren().add(rect);        
        setTranslateX(xb);
        setTranslateY(yb);
    }

    private void toggleSelected(){
        row=!row;
        if(row){
            rect.setTranslateX(xr);
            rect.setTranslateY(yr);
            rect.setWidth(rw);
            rect.setHeight(rh);
        }
        else{
            rect.setTranslateX(xc);
            rect.setTranslateY(yc);
            rect.setWidth(cw);
            rect.setHeight(ch);
        }
    }
    private void setEnteredView(){
        if(viewOnly)return;
        rect.setFill(Color.LIGHTBLUE);
        
    }
    public void setExitedView(){
        if(viewOnly)return;
        rect.setFill(Color.TRANSPARENT);
                
    }


    
//there should be no need to overwrite the following methods    

    public void flipToggle(){
        toggleSelected();
        changes.firePropertyChange(eventName,null,null);
        setExitedView();
    }
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public void fire(String s){changes.firePropertyChange(s,null,null);}
    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
    }    
    public void setViewOnly(boolean x){viewOnly=x;}

    
}
