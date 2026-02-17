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
 **  @file   LamppOpSelect.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Button for requesting a selected operation (row or column) to be executed
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

//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeSupport;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Button for requesting a selected operation (row or column) to be executed
 */
public class LamppOpSelect extends Group implements LamppGlobal{
//    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    static DropShadow ds= new DropShadow();
    static InnerShadow is= new InnerShadow();
    private final Text t1,t2,t3;
    private final Circle c1,c2,c3;
    private final double y1,y2,y3,y1h,y2h,y3h;
    private Integer test=1;
//    public final static String eventName="opSelected";
    public Integer selected=1;
    public LamppOpSelect (final double x, final double y1, final double y2, final double y3,
            final double w, final double h,
        final double fontSize){
        double hw=w/2;
        double hh=h/2;
        double rad=Math.min(hw,hh);
        t1=new Text("1");
        t2=new Text("2");
        t3=new Text("3");
        Font fnt=new Font(t1.getFont().getName(),fontSize);
        t1.setFont( fnt);
        t2.setFont( fnt);
        t3.setFont( fnt);
        double cx=x+(w-t1.getLayoutBounds().getWidth())/2;
        double cy=y1+(h+t1.getLayoutBounds().getHeight())/2-t1.getLayoutBounds().getHeight()/4;
        t1.setTranslateX(cx);
        t1.setTranslateY(cy);
        cx=x+(w-t2.getLayoutBounds().getWidth())/2;
        cy=y2+(h+t2.getLayoutBounds().getHeight())/2-t1.getLayoutBounds().getHeight()/4;
        t2.setTranslateX(cx);
        t2.setTranslateY(cy);
        cx=x+(w-t3.getLayoutBounds().getWidth())/2;
        cy=y3+(h+t3.getLayoutBounds().getHeight())/2-t1.getLayoutBounds().getHeight()/4;
        t3.setTranslateX(cx);
        t3.setTranslateY(cy);
        c1=new Circle(x+hw,y1+hh,rad);
        c2=new Circle(x+hw,y2+hh,rad);
        c3=new Circle(x+hw,y3+hh,rad);
        this.y1=y1;
        y1h=y1+h;
        this.y2=y2;
        y2h=y2+h;
        this.y3=y3;
        y3h=y3+h;
        is.setWidth(BUTTONWIDTH/2);
        is.setHeight(BUTTONHEIGHT/2);
        is.setOffsetX(BUTTONWIDTH/8);
        is.setOffsetY(BUTTONHEIGHT/8);
        is.setRadius(VIEW_SIZE/2);
        is.setColor(Color.color(0.8f, 0.8f, 0.8f));
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        c1.setFill(Color.LIGHTBLUE);
        c1.setStroke(Color.GREY);
        c1.setStrokeWidth(2);
        t1.setFill(Color.BLACK);
        c2.setFill(Color.LIGHTGREY);
        c2.setStroke(Color.GREY);
        c2.setEffect(ds);
        t2.setFill(Color.WHITE);
        c3.setFill(Color.LIGHTGREY);
        c3.setStroke(Color.GREY);
        c3.setEffect(ds);
        t3.setFill(Color.WHITE);

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!event.isSynthesized()){
                    //left button = touched = fire event to operation box
                    if (event.isPrimaryButtonDown()){
                        test=inButton(event.getY());
                        if(test!=null){
                            setPressed(test);
                        }
                    }
                }
                event.consume();
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isSynthesized()){
                    if(test!=null){
//                        changes.firePropertyChange(eventName,test,null);
                        setSelected(test);
                    }
                }
                event.consume();
            }
        });
        setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                //  element is touched - do same as mouse entered. 
                test=inButton(event.getTouchPoint().getY());
                if(test!=null){
                    setPressed(test);
                }
                event.consume();
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override 
            public void handle(TouchEvent event) {
                if(test!=null){
//                    changes.firePropertyChange(eventName,test,null);          
                    setSelected(test);}
                event.consume();
            }
        });   
        this.getChildren().addAll(c1,c2,c3,t1,t2,t3);              
    }
    private Integer inButton(double y){
        Integer ret;
        if(y1<=y&&y<=y1h)ret=1;
        else if(y2<=y&&y<=y2h)ret=2;
        else if(y3<=y&&y<=y3h)ret=3;
        else return null;
        if(ret.equals(selected))return null;
        return ret;
    }
    public void setSelected(Integer i){
        if(i.equals(selected))return;
        switch(i){
            case 1:{
                c1.setFill(Color.LIGHTBLUE);
                c1.setStroke(Color.GREY);
                c1.setStrokeWidth(2);
                c1.setEffect(null);
                t1.setFill(Color.BLACK);
                t1.setEffect(null);
                c2.setFill(Color.LIGHTGREY);
                c2.setStroke(Color.GREY);
                c2.setEffect(ds);
                t2.setFill(Color.WHITE);
                c3.setFill(Color.LIGHTGREY);
                c3.setStroke(Color.GREY);
                c3.setEffect(ds);
                t3.setFill(Color.WHITE);
                        break;
            }
            case 2:{
                c2.setFill(Color.LIGHTBLUE);
                c2.setStroke(Color.GREY);
                c2.setStrokeWidth(2);
                c2.setEffect(null);
                t2.setFill(Color.BLACK);   
                t2.setEffect(null);
                c1.setFill(Color.LIGHTGREY);
                c1.setStroke(Color.GREY);
                c1.setEffect(ds);
                t1.setFill(Color.WHITE);
                c3.setFill(Color.LIGHTGREY);
                c3.setStroke(Color.GREY);
                c3.setEffect(ds);
                t3.setFill(Color.WHITE);
                break;
            }
            case 3:{
                c3.setFill(Color.LIGHTBLUE);
                c3.setStroke(Color.GREY);
                c3.setStrokeWidth(2);
                c3.setEffect(null);
                t3.setFill(Color.BLACK);   
                t3.setEffect(null);
                c1.setFill(Color.LIGHTGREY);
                c1.setStroke(Color.GREY);
                c1.setEffect(ds);
                t1.setFill(Color.WHITE);
                c2.setFill(Color.LIGHTGREY);
                c2.setStroke(Color.GREY);
                c2.setEffect(ds);
                t2.setFill(Color.WHITE);
                break;
            }
        }
        selected=i;
    }
    public void setPressed(Integer i){
        if(i.equals(selected))return;
        switch(i){
            case 1:{
                c1.setFill(Color.LIGHTBLUE);   
                c1.setStroke(Color.GREY);
                c1.setEffect(is);
                t1.setFill(Color.GREY);
                break;
            }
            case 2:{
                c2.setFill(Color.LIGHTBLUE);   
                c2.setStroke(Color.GREY);
                c2.setEffect(is);
                t2.setFill(Color.GREY);
                break;
            }
            case 3:{
                c3.setFill(Color.LIGHTBLUE);   
                c3.setStroke(Color.GREY);
                c3.setEffect(is);
                t3.setFill(Color.GREY);
                break;
            }
        }
    }
//    public void addPropertyChangeListener(PropertyChangeListener l){
//        changes.addPropertyChangeListener(l);
//    }
//    public void removePropertyChangeListener(PropertyChangeListener l){
//        changes.removePropertyChangeListener(l);
//    }    
}
