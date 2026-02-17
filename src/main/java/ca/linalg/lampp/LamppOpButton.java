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
 **  @file   LamppOpButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief button to display number of operations executed and for selecting a specific operation from the history
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * button to display number of operations executed and
 * for selecting a specific operation from the history 
 * from which to begin execution
 * It is the last operated upon (if not being displayed) or displayed matrix that is saved
 */
public class LamppOpButton extends Group implements LamppGlobal{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private final DropShadow ds;
    public boolean viewOnly;
    public boolean wasPressed=false;
    private final Text buttonText;
    private final Rectangle rect;
    private double th,tw;
    private double xx,yy;
    public static int opExecuted=0, latestOp=0;
    private String buttonName;
    public final static String eventName="setOp";
    private int selected=0;
    private int quadrant=1;

    
    /**
     * @param xb the x coordinate of the bottom left of the button
     * @param yb the y coordinate of the bottom left of button
     * @param width the preferred width
     * @param height the preferred height
     * @param fontSize font size of button name
     */    
    public LamppOpButton (double xb,double yb, double width, double height,
            final double fontSize){
        super();

        buttonName = String.format("%1$02d",opExecuted);
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
        xb=(width-tw)/2;
        yb=height/2+th/4;
        buttonText.setTranslateX(xb);
        buttonText.setTranslateY(yb);
        rect = new Rectangle(0,0,width,height);
        ds = new DropShadow(); 
        rect.setFill(Color.TRANSPARENT);       
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(0.8);    
        viewOnly=false;
        ds.setOffsetY(3.0f);        
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!viewOnly&&!event.isSynthesized()&&!wasPressed){
                    //left button = touched = fire event to operation box
                    if (event.isPrimaryButtonDown()){
                        quadrant=quad(event.getX(),event.getY());
                        switch(quadrant){
                            case 1:{setLeastOp();break;}
                            case 2:{decOp();break;}
                            case 3:{setMaxOp();break;}
                            case 4:{incOp();break;}
                        }
                        setEnteredView();
                    selected=0;                    
                    }
                }
                event.consume();
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isSynthesized()&&!viewOnly){
                    if(!viewOnly){

                    }
                    setExitedView();
                }
                wasPressed=false;
                event.consume();
            }
        });
        
        setOnSwipeRight(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly)setMaxOp();
                event.consume();
            }
        });
        setOnSwipeLeft(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly)setLeastOp();
                event.consume();
            }
        });
        setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly)incOp();
                event.consume();
            }
        });        
        setOnSwipeUp(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(!viewOnly)decOp();
                event.consume();
            }
        });        
        
        
        
/*        
        setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly&&!wasPressed){
                    //  element is touched - do same as mouse entered. 
                    setEnteredView();
                    quadrant=quad(event.getTouchPoint().getX(),event.getTouchPoint().getY());
                        switch(quadrant){
                            case 1:{setLeastOp();break;}
                            case 2:{decOp();break;}
                            case 3:{setMaxOp();break;}
                            case 4:{incOp();break;}
                        }                            
                }
                event.consume();
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(!viewOnly){
                    setExitedView();
                }
                wasPressed=false;
                event.consume();
            }
        });   
*/        
        
        this.getChildren().addAll(rect,buttonText);        
    }
    
    private int quad(double x, double y){
        if(x<width/2){
            if(y<height/2)return 2;
            else return 3;
        }
        else{
            if(y<height/2)return 1;
            else return 4;
        }
    }
    private void setOp(){
        buttonName = String.format("%1$02d",opExecuted);
        buttonText.setText(buttonName);
        th=buttonText.getLayoutBounds().getHeight();
        tw=buttonText.getLayoutBounds().getWidth();
        xx=(width-tw)/2;
        yy=height/2+th/4;
        buttonText.setTranslateX(xx);
        buttonText.setTranslateY(yy);
    }
    private void incOp(){
        if(opExecuted>=latestOp)return;
        opExecuted++;
        setOp();
        changes.firePropertyChange(eventName,buttonName,null);  
    }
    private void decOp(){
        if(opExecuted<1)return;
        opExecuted--;
        setOp();
        changes.firePropertyChange(eventName,buttonName,null);  
    }
    private void setMaxOp(){
        opExecuted=latestOp;
        setOp();
        changes.firePropertyChange(eventName,buttonName,null);  
    }
    private void setLeastOp(){
        opExecuted=0;
        setOp();
        changes.firePropertyChange(eventName,buttonName,null);  
    }
    public void reset(){
        opExecuted=0;
        latestOp=opExecuted;
        selected=0;
        quadrant=0;
        setOp(); 
    }
    public void opAdded(){
        opExecuted++;
        latestOp=opExecuted;
        setOp();
    }
    public boolean canAdd(){
        return latestOp<MAX_OPS-1||opExecuted<latestOp;
    }
    public int getSelected(){return selected;}
    public int getExecuted(){return opExecuted;}
    private void setEnteredView(){
        wasPressed=true;
        buttonText.setFill(Color.WHITE);
        LinearGradient linearGrad = new LinearGradient(
            0, // start X
            0, // start Y
            1, // end X  
            1, // end Y
            true, // proportional
            CycleMethod.NO_CYCLE, // cycle colors
            // stops
            new Stop(0.1f, Color.rgb(255, 200, 0, .784)),
            new Stop(1.0f, Color.rgb(0, 0, 139, .784))); 
        switch(quadrant){
            case 1:{linearGrad = new LinearGradient(
                0, // start X
                1, // start Y
                1, // end X  
                0, // end Y
                true, // proportional
                CycleMethod.NO_CYCLE, // cycle colors
                // stops
                new Stop(0.1f, Color.rgb(127, 255, 212, .784)),
                new Stop(1.0f, Color.rgb(0, 0, 0, .784)));break;}
            case 2:{linearGrad = new LinearGradient(
                1, // start X
                1, // start Y
                0, // end X  
                0, // end Y
                true, // proportional
                CycleMethod.NO_CYCLE, // cycle colors
                // stops
                new Stop(0.1f, Color.rgb(255, 200, 0, .784)),
                new Stop(1.0f, Color.rgb(0, 0, 139, .784)));break;}
            case 3:{linearGrad = new LinearGradient(
                1, // start X
                0, // start Y
                0, // end X  
                1, // end Y
                true, // proportional
                CycleMethod.NO_CYCLE, // cycle colors
                // stops
                new Stop(0.1f, Color.rgb(127, 255, 212, .784)),
                new Stop(1.0f, Color.rgb(69, 139, 116, .784)));break;}
        }
        rect.setFill(linearGrad);
        buttonText.setEffect(ds);
    }
    public void setExitedView(){
        wasPressed=false;
        buttonText.setEffect(null);
        buttonText.setFill(Color.BLACK);
        rect.setFill(Color.TRANSPARENT);
    }

//there should be no need to overwrite the following methods    
    
    public boolean wasPressed(){
        return wasPressed;
    }
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public void fire(String s){changes.firePropertyChange(s,s,null);}
    public void fire(){changes.firePropertyChange(eventName,buttonName,null);}
    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
    }    
}
