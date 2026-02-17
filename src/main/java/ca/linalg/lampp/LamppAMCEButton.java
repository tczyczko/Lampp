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
 **  @file   LamppAMCEButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Button for Additive inverse, Multiplicative inverse, complex Conjugate, Execute operation and send to selected matrix elements
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
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Button for Additive inverse, Multiplicative inverse, complex Conjugate, Execute operation and set (send to selected matrix elements)
 * used in Scalar Editor box
 * @version 1.0
 */
public class LamppAMCEButton extends Group{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private final DropShadow ds;
    public static InnerShadow is= new InnerShadow();;
    public boolean viewOnly;
    public boolean wasPressed=false;
    private final Text buttonText,inverseSign,overStrike;
    private final SVGPath entersvg;
    private final Rectangle rect;
    private double th,tw,ih,oh,ow;
    public static String eventName[]={"addInv","multInv","conj","execute","set"};
    private final int type;
    
    /**
     * @param xb the x coordinate of the bottom left of the button
     * @param yb the y coordinate of the bottom left of button
     * @param width the preferred width
     * @param height the preferred height
     * @param fontSize font size of button name
     * @param paneType predetermined parent pane - op,edit
     * @param type 0-3 - eventName[type] - type of button
     */    
    public LamppAMCEButton (double xb,double yb, double width, double height,
            final double fontSize, String paneType,
            final int type){
        super();
        buttonText=new Text("+");
        inverseSign=new Text("-1");
        overStrike=new Text("_");
        this.type=type;
        buttonText.setFont( new Font(buttonText.getFont().getName(), fontSize ));
        inverseSign.setFont( new Font(buttonText.getFont().getName(), fontSize/2 ));
        is.setWidth(width/2);
        is.setHeight(height/2);
        is.setOffsetX(width/8);
        is.setOffsetY(height/8);
        is.setRadius(width/2);
        switch (type){
            case 0:{
                break;
            }
            case 1:{
                buttonText.setText("*");
                break;
            }
            case 2:{
                buttonText.setText("C");
                overStrike.setFont( new Font(buttonText.getFont().getName(), fontSize ));
                break;
            }
            case 3:{
                buttonText.setText("Execute");
                buttonText.setFont( new Font(buttonText.getFont().getName(), 0.8*fontSize ));
                break;
            }           
            case 4:{
                buttonText.setText("Set");
                buttonText.setFont( new Font(buttonText.getFont().getName(), fontSize ));
                break;
            }           
        }
        th=buttonText.getLayoutBounds().getHeight();
        height=Math.max(height,th);
        this.height=height;
        tw=buttonText.getLayoutBounds().getWidth();
        ih=inverseSign.getLayoutBounds().getHeight();
        inverseSign.getLayoutBounds().getWidth();
        oh=overStrike.getLayoutBounds().getHeight();
        ow=overStrike.getLayoutBounds().getWidth();
        width=Math.max(width,tw);
        this.width=width;
        setTranslateX(xb);
        setTranslateY(yb);
        xb=(width-tw)/4;
        yb=height/2+th/4;
        rect = new Rectangle(0,0,width,height);
        ds = new DropShadow(); 
        rect.setFill(Color.TRANSPARENT);
        rect.setArcWidth(Math.min(fontSize,12));
        rect.setArcHeight(Math.min(fontSize,12));        
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(0.8);    
        entersvg = new SVGPath();
        switch (type){
            case 0:{
                inverseSign.setTranslateX(xb+(width)/3);
                inverseSign.setTranslateY(yb-height/3);
                this.getChildren().add(inverseSign);        
                break;
            }
            case 1:{
                inverseSign.setTranslateX(xb+(width)/3);
                inverseSign.setTranslateY(yb-height/3);
                yb=yb+fontSize/4;
                this.getChildren().add(inverseSign);        
                break;
            }
            case 2:{
                xb=xb+(width-tw)/4;
                yb=yb+th/8;
                overStrike.setFont( new Font(buttonText.getFont().getName(), fontSize ));
                overStrike.setTranslateX(xb+(tw-ow)/2);
                overStrike.setTranslateY(yb-0.8*height);
                this.getChildren().add(overStrike);        
                break;
            }
            case 3:{
                xb=xb+(width-tw)/4;
                yb=yb-0.2*height;
                ih=yb+height/4;
                entersvg.setContent(
                        " M"+xb+","+ih+" L"+(xb+2*ow)+","+(ih-oh/2)
                        +" L"+(xb+2*ow)+","+(ih+oh/2) +" z"
                        +" M"+xb+","+ih+" L"+(xb+2*ow)+","+(ih+oh/2)
                        +"M"+(xb+2*ow)+","+ih+" L"+(xb+tw-ow)+","+ih
                        +" M"+(xb+tw)+","+(ih-oh/2)
                        +" C"+(xb+tw)+","+(ih)
                        +" "+(xb+tw)+","+(ih)
                        +" "+(xb+tw-ow)+","+(ih)
                );
                entersvg.setFill(null);
                entersvg.setStroke(Color.BLACK);
                this.getChildren().add(entersvg);        
                break;
            }           
            case 4:{
                xb=xb+(width-tw)/4;
//                yb=yb-0.2*height;
                ih=yb+height/4;       
                rect.setArcHeight(height*0.3-2);
                rect.setArcWidth(width*0.3-2);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(5.0);
                rect.setEffect(is);
                rect.setFill(Color.LIGHTGREEN);
                break;
            }           
        }
        buttonText.setTranslateX(xb);
        buttonText.setTranslateY(yb); 
        viewOnly=false;
        ds.setOffsetY(3.0f);        
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!viewOnly&&!event.isSynthesized()&&!wasPressed){
                    //left button = touched = fire event to operation box
                    if (event.isPrimaryButtonDown()){
                        setEnteredView();
                        changes.firePropertyChange(paneType,eventName[type],null);                    
                    }
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
                    //  element is touched - do same as mouse entered. 
                    setEnteredView();
                    changes.firePropertyChange(paneType,eventName[type],null);   
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
        this.getChildren().addAll(rect,buttonText);        
    }
    private void setEnteredView(){
        wasPressed=true;
        rect.setStroke(Color.WHITE);
        rect.setEffect(ds);
        switch (type){
            case 0:{
            }
            case 1:{
                inverseSign.setEffect(ds);
                inverseSign.setFill(Color.WHITE);
                break;
            }
            case 2:{
                overStrike.setEffect(ds);
                overStrike.setFill(Color.WHITE);
                break;
            }
            case 3:{
                entersvg.setEffect(ds);
                entersvg.setStroke(Color.WHITE);
                break;
            }           
            case 4:{
                rect.setFill(Color.LIGHTGREEN);
                break;
            }           
        }     
        buttonText.setFill(Color.WHITE);
        buttonText.setEffect(ds);
    }
    public void setExitedView(){
        wasPressed=false;
        buttonText.setEffect(null);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);        
        rect.setEffect(null);
        switch (type){
            case 0:{
            }
            case 1:{
                inverseSign.setEffect(null);
                inverseSign.setFill(Color.BLACK);
                break;
            }
            case 2:{
                overStrike.setEffect(null);
                overStrike.setFill(Color.BLACK);
                break;
            }
            case 3:{
                entersvg.setEffect(null);
                entersvg.setStroke(Color.BLACK);
                break;
            }           
            case 4:{
                rect.setStroke(Color.BLACK);        
                rect.setEffect(is);
                rect.setFill(Color.LIGHTGREEN);
                break;
            }           
        }       

        buttonText.setFill(Color.BLACK);
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
