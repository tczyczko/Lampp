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
 **  @file   LamppVerticalButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief vertical button for sides of screen
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
import javafx.scene.text.TextAlignment;

/**
 * vertical button for sides of screen
 */
public class LamppVerticalButton extends Group{
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private final double height;
    private final double width;
    private final DropShadow ds;
    public boolean viewOnly;
    public boolean wasPressed=false;
    private Text[] vertical;
    private SVGPath topsvg;
    private SVGPath bottomsvg;
    private String verticalString;
    private final Rectangle rect;
    
    public LamppVerticalButton(){
        this("Default",20,50,200,10,0,false,false);
    }
    public LamppVerticalButton(String str){
        this(str,20,50,200,10,0,false,false);
    }
    public LamppVerticalButton(String str, double fontSize){
        this(str,fontSize,50,200,10,0,false,false);
    }
    /**
    * @param verticalString string used in button
    * @param fontSize button font size
    * @param width  button width
    * @param height button height
    * @param xspace x spacing added to each character in vertical string
    * @param yspace y spacing added to each character in vertical string
    * @param topline button has top line drawn
    * @param  bottomline button has bottom line drawn
     */
    public LamppVerticalButton(String verticalString, double fontSize,
            double width, double height, double xspace,
            double yspace, boolean topline, boolean bottomline){
        super();
        this.verticalString=verticalString;
        this.height=height;
        this.width=width;
        rect = new Rectangle(0,0,width,height);
        //determine character box size
        ds = new DropShadow();
        vertical=new Text[verticalString.length()];
        double [][] coords=new double [2][verticalString.length()];
        double totalheight=0;

        for (int i=0;i<verticalString.length();i++){
            vertical[i]=new Text(verticalString.substring(i, i+1));
            vertical[i].setFont( new Font(vertical[i].getFont().getName(), fontSize ));
            vertical[i].setTextAlignment(TextAlignment.CENTER);
            coords[0][i]=vertical[i].getLayoutBounds().getWidth();
            coords[1][i]=vertical[i].getLayoutBounds().getHeight();
            totalheight=totalheight+coords[1][i];
            vertical[i].getText().charAt(0);
            totalheight=totalheight+yspace;
        }

        double ystart=(height-totalheight)/2+coords[1][0]/1.5;
        for (int i=0;i<verticalString.length();i++){
            vertical[i].getText().charAt(0);
            vertical[i].setY(ystart);
            vertical[i].setX((width-coords[0][i])/2);
            vertical[i].setFill(Color.BLACK);
            ystart=ystart+coords[1][i]+yspace;
            this.getChildren().add(vertical[i]);
        }
        topsvg = new SVGPath();
        topsvg.setContent("M"+xspace+",0 L"+(width-xspace)+",0");
        topsvg.setFill(null);
        topsvg.setStroke(Color.BLACK);
        bottomsvg = new SVGPath();
        bottomsvg.setContent("M"+xspace+","+(height-yspace)+" L"+(width-xspace)+","+(height-yspace));
        bottomsvg.setFill(null);
        bottomsvg.setStroke(Color.BLACK);
        topsvg.setVisible(topline);
        bottomsvg.setVisible(bottomline);
        rect.setFill(Color.TRANSPARENT);
        rect.setStrokeWidth(0);    
        viewOnly=false;
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!viewOnly&&!event.isSynthesized()&&!wasPressed){
                    //left button = touched = fire event to operation box
                    if (event.isPrimaryButtonDown()){
                        changes.firePropertyChange(verticalString,verticalString,null);
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
                    changes.firePropertyChange(verticalString,verticalString,null);          
                }
                event.consume();
            }
        });
        setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                if(!viewOnly){
                    setExitedView();
                }
                wasPressed=false;
                event.consume();
            }
        });
        this.getChildren().addAll(rect,topsvg,bottomsvg);
    }
    


//there should be no need to overwrite the following methods    

    private void setEnteredView(){
        wasPressed=true;
        for (int i=0;i<verticalString.length();i++){
            vertical[i].setEffect(ds);
        }
    }
    public void setExitedView(){
        wasPressed=false;
        for (int i=0;i<verticalString.length();i++){
            vertical[i].setEffect(null);
        }

    }   

    /**
     *
     * @return
     */
    public boolean wasPressed(){
        return wasPressed;
    }
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public void fire(String s){changes.firePropertyChange(s,s,null);}
    public void fire(){changes.firePropertyChange(verticalString,verticalString,null);}
    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
    }
}
