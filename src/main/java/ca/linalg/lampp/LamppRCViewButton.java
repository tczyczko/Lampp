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
 **  @file   LamppRCViewButton.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief button to select whether row or column operations are to be performed
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

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * button to select whether row or column operations are to be performed
 */
public class LamppRCViewButton extends Group implements LamppGlobal{
    private final double height;
    private final double width;
    private final Rectangle rect;
    private boolean row=true;
    private final Text buttonText;
    private final double th,tw;
    private final double xr,yr,rw,rh,cw,ch,xc,yc;
     /**
     * @param xb the x coordinate of the origin
     * @param yb the y coordinate of the origin
     * @param width the preferred width
     * @param height the preferred height
     * @param fontSize size of arc width/height (same as fontSize for vector buttons)
     */    
    public LamppRCViewButton (double xb,double yb, double width, double height,
            final String buttonName, 
            final double fontSize){
        super();
        this.height=height;
        this.width=width;
        rw=BUTTONWIDTH;
        cw=rw/2;
        ch=BUTTONHEIGHT;
        rh=ch/2;
        buttonText=new Text(buttonName);
        buttonText.setFont( new Font(buttonText.getFont().getName(), fontSize ));
        th=buttonText.getLayoutBounds().getHeight();
        tw=buttonText.getLayoutBounds().getWidth();
        xc=(width-cw)/2;
        xr=(width-rw)/2;
        yr=(height-rh)/2-th/4;
        yc=(height-ch)/2-th/4;
        rect = new Rectangle(xr,yr,rw,rh);
        rect.setFill(Color.TRANSPARENT);
        rect.setArcWidth(fontSize);
        rect.setArcHeight(fontSize);        
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(0.9);    
        rect.setTranslateX(xr);
        rect.setTranslateY(yr);



        setTranslateX(xb);
        setTranslateY(yb);
        xb=(width-tw)/2;
        yb=height/2+th/4;
        buttonText.setTranslateX(xb);
        buttonText.setTranslateY(yb);
        this.getChildren().addAll(rect,buttonText);  
    }

    public void toggleRC(){
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

    public void setLeftView(){
        buttonText.setFill(Color.WHITE);buttonText.setStroke(Color.WHITE);rect.setFill(Color.BLUE);
    }
    public void setRightView(){
        buttonText.setFill(Color.WHITE);buttonText.setStroke(Color.WHITE);rect.setFill(Color.GREY);
    }
    public void setText(String name){
        buttonText.setText(name);
        buttonText.setTranslateX((width-buttonText.getLayoutBounds().getWidth())/2);
        buttonText.setTranslateY(height/2+(buttonText.getLayoutBounds().getHeight())/4);
    }


//there should be no need to overwrite the following methods    


    public double getWidth(){return width;}
    public double getHeight(){return height;}


    
}

