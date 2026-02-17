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
 **  @file   LamppButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Standard for buttons used by Lampp.
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
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Standard for buttons used by Lampp.
 */
public class LamppButton extends Group{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private final DropShadow ds;
    public boolean viewOnly;
    public boolean wasPressed=false;
    private final boolean borderRect;
    private final Text buttonText;
    private final SVGPath leftsvg;
    private final SVGPath rightsvg;
    private final Rectangle rect;
    private final double x,y;
    private double th,tw;
    private final Text bName,sub1Name,sub2Name;
    private int numStrings;
    private final Text xSign=new Text("\u00D7");
    private final Text abSpace=new Text(": ");
    private String buttonName;
    
    /**
     * @param xb the x coordinate of the bottom left of the button
     * @param yb the y coordinate of the bottom left of button
     * @param width the preferred width
     * @param height the preferred height
     * @param buttonName string name of button and fired event text
     * @param fontSize font size of button name
     * @param leftLine if true draw line just inside left boundary of the button
     * @param rightLine if true draw line just inside right boundary of the button
     * @param borderRect true - border with curved edges drawn around button
     * @param bs if not empty, ": "+bs added to buttonName 
     * @param sub1 if not empty, subscript added at end of button name 
     * @param sub2 if not empty, "x"+sub2 subscript added at end of button name 
     */    
    public LamppButton (double xb,double yb, double width, double height,
            final String buttonName, final double fontSize,
            final boolean leftLine, final boolean rightLine,
            boolean borderRect,
            final String bs,
            final String sub1,
            final String sub2){
        super();
        double xspace=5;
        double yspace=5;
        numStrings=1;
        this.buttonName=buttonName;
        if(bs.length()>0){
            numStrings++;
            if(sub1.length()>0){
                numStrings++;
                if(sub2.length()>0){
                    numStrings++;
                }
            }
        }
        bName=new Text(bs);
        sub1Name=new Text(sub1);
        sub2Name=new Text(sub2);
        this.borderRect=borderRect;
        buttonText=new Text(buttonName);
        buttonText.setFont( new Font(buttonText.getFont().getName(), fontSize ));
        bName.setFont( new Font(buttonText.getFont().getName(), fontSize ));
        abSpace.setFont( new Font(buttonText.getFont().getName(), fontSize ));
        sub1Name.setFont( new Font(buttonText.getFont().getName(), fontSize/2 ));
        sub2Name.setFont( new Font(buttonText.getFont().getName(), fontSize/2 ));
        xSign.setFont( new Font(buttonText.getFont().getName(), fontSize/2 ));
        th=buttonText.getLayoutBounds().getHeight();
        height=Math.max(height,th);
        this.height=height;
        tw=buttonText.getLayoutBounds().getWidth();
        switch (numStrings){
            case 1:{break;}
            case 2:{
                tw=tw+abSpace.getLayoutBounds().getWidth();
                tw=tw+bName.getLayoutBounds().getWidth();
                break;
            }
            case 3:{
                tw=tw+sub1Name.getLayoutBounds().getWidth();
                break;
            }
            case 4:{
                tw=tw+xSign.getLayoutBounds().getWidth();
                tw=tw+sub2Name.getLayoutBounds().getWidth();
                break;
            }           
        };
        width=Math.max(width,tw);
        this.width=width;
        setTranslateX(xb);
        setTranslateY(yb);
        if(borderRect)xb=(width-tw)/2;
        else xb=2*xspace;
        yb=height/2+th/2-fontSize/4;
        if (borderRect)yb=height/2+th/4;
        y=yb;
        x=xb;
        buttonText.setTranslateX(xb);
        buttonText.setTranslateY(yb);
        abSpace.setVisible(false);
        bName.setVisible(false);
        sub1Name.setVisible(false);
        sub2Name.setVisible(false);
        xSign.setVisible(false);
        this.getChildren().addAll(abSpace,bName);
        this.getChildren().add(sub1Name);
        this.getChildren().addAll(xSign,sub2Name);
        if(numStrings>=2){
            tw=xb+buttonText.getLayoutBounds().getWidth();
            abSpace.setTranslateX(tw);
            abSpace.setTranslateY(yb);
            tw=tw+abSpace.getLayoutBounds().getWidth();
            bName.setTranslateX(tw);
            bName.setTranslateY(yb);
            abSpace.setVisible(true);
            bName.setVisible(true);
        }
        if(numStrings>=3){
            tw=tw+bName.getLayoutBounds().getWidth();
            sub1Name.setTranslateX(tw);
            sub1Name.setTranslateY(height);
            sub1Name.setVisible(true);
        }
        if(numStrings>=4){
            tw=tw+sub1Name.getLayoutBounds().getWidth();
            xSign.setTranslateX(tw);
            xSign.setTranslateY(height);
            tw=tw+xSign.getLayoutBounds().getWidth();
            sub2Name.setTranslateX(tw);
            sub2Name.setTranslateY(height);
            sub2Name.setVisible(true);
            xSign.setVisible(true);
        } 
        rect = new Rectangle(0,0,width,height);
        ds = new DropShadow(); 
        leftsvg = new SVGPath();
        leftsvg.setContent("M0,"+yspace+" L0,"+(height-yspace));
        leftsvg.setFill(null);
        leftsvg.setStroke(Color.BLACK);
        rightsvg = new SVGPath();
        rightsvg.setContent("M"+width+","+yspace+" L"+width+","+(height-yspace));
        rightsvg.setFill(null);
        rightsvg.setStroke(Color.BLACK);
        rightsvg.setVisible(rightLine);
        leftsvg.setVisible(leftLine);
        rect.setFill(Color.TRANSPARENT);
        rect.setArcWidth(Math.min(fontSize,12));
        rect.setArcHeight(Math.min(fontSize,12));        
        if(borderRect){rect.setStroke(Color.BLACK);}
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
                        changes.firePropertyChange(buttonName,buttonName,null);
                        setEnteredView();
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
                    changes.firePropertyChange(buttonName,buttonName,null);          
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
        this.getChildren().addAll(rect,leftsvg,rightsvg,buttonText);        
    }
        
    private void setEnteredView(){
        wasPressed=true;
        if(borderRect){buttonText.setFill(Color.WHITE);rect.setFill(Color.BLUE);}
        buttonText.setEffect(ds);
        abSpace.setEffect(ds);
        bName.setEffect(ds);
        sub1Name.setEffect(ds);
        sub2Name.setEffect(ds);
        xSign.setEffect(ds);
    }
    public void setExitedView(){
        wasPressed=false;
        buttonText.setEffect(null);
        abSpace.setEffect(null);
        bName.setEffect(null);
        sub1Name.setEffect(null);
        sub2Name.setEffect(null);
        xSign.setEffect(null);
        if(borderRect){buttonText.setFill(Color.BLACK);rect.setFill(Color.TRANSPARENT);}
    }

//there should be no need to overwrite the following methods    
    
    public boolean wasPressed(){
        return wasPressed;
    }
    public void setName(final String buttonName, final String bs, final String sub1, final String sub2){
        abSpace.setVisible(false);
        bName.setVisible(false);
        sub1Name.setVisible(false);
        sub2Name.setVisible(false);
        xSign.setVisible(false);
        numStrings=1;
        this.buttonName=buttonName;
        if(bs.length()>0){
            numStrings++;
            if(sub1.length()>0){
                numStrings++;
                if(sub2.length()>0){
                    numStrings++;
                }
            }
        }
        bName.setText(bs);
        sub1Name.setText(sub1);
        sub2Name.setText(sub2);
        buttonText.setText(buttonName);
        tw=x;
        if(numStrings>=2){
            tw=tw+buttonText.getLayoutBounds().getWidth();
            abSpace.setTranslateX(tw);
            abSpace.setTranslateY(y);
            tw=tw+abSpace.getLayoutBounds().getWidth();
            bName.setTranslateX(tw);
            bName.setTranslateY(y);
            abSpace.setVisible(true);
            bName.setVisible(true);
        }
        if(numStrings>=3){
            tw=tw+bName.getLayoutBounds().getWidth();
            sub1Name.setTranslateX(tw);
            sub1Name.setTranslateY(height);
            sub1Name.setVisible(true);
        }
        if(numStrings>=4){
            tw=tw+sub1Name.getLayoutBounds().getWidth();
            xSign.setTranslateX(tw);
            xSign.setTranslateY(height);
            tw=tw+xSign.getLayoutBounds().getWidth();
            sub2Name.setTranslateX(tw);
            sub2Name.setTranslateY(height);
            sub2Name.setVisible(true);
            xSign.setVisible(true);
        }
    }
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public void fire(String s){changes.firePropertyChange(s,s,null);}
    public void fire(){changes.firePropertyChange(buttonName,buttonName,null);}
    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
    }    
}
