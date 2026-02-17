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
 **  @file   LamppSlidingPaneV.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Animates a node on and off screen up,down,left,right or diagonally
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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

/**
 *
 * Animates a node on and off screen up,down,left,right or diagonally
 * creates a collapsing pane
 * will grow from a point or line depending on diagonal (true or false)
 * controlled with a vertical button 
 * @return a vertical control button (LamppVerticalButton) to hide and show the LamppSlidingPane
 */
public class LamppSlidingPaneV extends ScrollPane  implements PropertyChangeListener{
    public LamppVerticalButton getVerticalButton() { return verticalButton; }
    private final LamppVerticalButton verticalButton;


    private double expandedWidth,expandedHeight;
    private final double x,y;
    
    boolean waspressed=false;
    String paneName;
    private final boolean diagonal;
    final int quadrant;
/**    
 * @param x1 x coordinate of node origin
 * @param y1 y coordinate of node origin
 * @param quadrant quadrant pane grows into form origin or line through origin
 * @param diagonal pane grows diagonally into quadrant
 * @param startopen pane starts open
 * @param paneName string used in control button
 * @param bwidth  button width
 * @param bheight button height
 * @param fontSize button font size
 * @param tline button has top line drawn
 * @param  bline button has bottom line drawn
 * @param xspace x spacing added to each character in vertical string
 * @param yspace y spacing added to each character in vertical string
 * @param paneContent node with content of pane controlled by button
 *   expands from x1,y1 into selected quadrant
 *   ie. to expand to the left OR
 *   to expand diagonally up to the right
 *   set quadrant = 2
 *   x1,y1 is used to position this node
 *   and are the intial coordinates of the button
 *   EXCEPT in quadrant 4, when button is shifted left
 *   by the button width to keep from being covered
 *  
 *          2|1
 *          -+-    
 *          3|4    
 *    
 */
    LamppSlidingPaneV(double x1, double y1, double wi, double he,
            final int quadrant, final boolean diagonal, final boolean startopen,
            String paneName, final double bwidth, final double bheight,
            final double fontSize,final boolean tline, final boolean bline,
            final double xspace,final double yspace,
            Node paneContent) {
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //no scroll bars
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        setPannable(false); //allow mouse panning
        //expands from x1,y1 into selected quadrant
        // ie. to expand to the left OR
        //     to expand diagonally up to the right
        //     set quadrant = 2
        // x1,y1 is used to position this node
        //     and are the intial coordinates of the button
        //     EXCEPT in quadrant 4, when button is shifted left
        //     by the button width to keep from being covered
        //   
        //         2|1
        //         -+-    
        //         3|4    
        //   
        
        expandedWidth=Math.abs(wi);
        expandedHeight=Math.abs(he);

        this.setMinHeight(0);  
        this.setMinWidth(0);
        this.setPrefWidth(expandedWidth);
        this.setPrefHeight(expandedHeight);
        this.paneName=paneName;
        this.diagonal=diagonal;
        this.quadrant=quadrant;
        if(!startopen){
            setVisible(false);
            if(diagonal){
            setPrefWidth(0);
            setPrefHeight(0);
            }
            
            waspressed=true;
        }        
        
        x=x1;
        y=y1;

      // create a bar to hide and show.
        setContent(paneContent);        
        verticalButton = new LamppVerticalButton(paneName,fontSize,bwidth,bheight,xspace,yspace,tline,bline);
        verticalButton.addPropertyChangeListener((PropertyChangeListener)this);
        verticalButton.setTranslateX(x);
        verticalButton.setTranslateY(y);        
      
        switch (quadrant){
            case 1:
                setTranslateY(y-expandedHeight);
                setTranslateX(x);
                break;
            case 2:
                setTranslateY(y-expandedHeight);
                setTranslateX(x - expandedWidth );
                break;
            case 3:
                setTranslateY(y);
                setTranslateX(x - expandedWidth);
                break;
            default:
                setTranslateY(y);
                setTranslateX(x);
                verticalButton.setTranslateX(x-verticalButton.getWidth());
        }

        
    }
    //methods
    public void propertyChange(PropertyChangeEvent evt){
     // apply the animations when the button is pressed.
        String text=evt.getPropertyName();
        if(text.equals(paneName)){ 
            final Animation hideSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                protected void interpolate(double frac) {
                    final double curHeight = expandedHeight * (1.0 - frac);
                    final double curWidth = expandedWidth * (1.0 - frac);
                    if(!diagonal){
                        switch (quadrant){
                            case 1:
                                setPrefHeight(curHeight);
                                setTranslateY(y - curHeight );                                
                                break;
                            case 2:
                                setPrefWidth(curWidth);
                                setTranslateX(x - curWidth );
                                break;
                            case 3:
                                setPrefWidth(curWidth);
                                setTranslateX(x  - curWidth );
                                break;
                            default:
                                setPrefWidth(curWidth); 
                        }
                    }
                    else{
                        switch (quadrant){
                            case 1:
                                setPrefHeight(curHeight);
                                setTranslateY(y - curHeight );
                                setPrefWidth(curWidth);
                                break;
                            case 2:
                                setPrefHeight(curHeight);
                                setTranslateY(y - curHeight );
                                setPrefWidth(curWidth);
                                setTranslateX(x - curWidth );
                                break;
                            case 3:
                                setPrefHeight(curHeight);
                                setPrefWidth(curWidth);
                                setTranslateX(x  - curWidth );
                                break;
                            case 4:
                                setPrefHeight(curHeight);
                                setPrefWidth(curWidth);
                        }  
                    }
                }
            };
          // create an animation to show a sidebar.
            final Animation showSidebar = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                protected void interpolate(double frac) { 
                    final double curHeight = expandedHeight * frac;
                    final double curWidth = expandedWidth * frac;
                    if(!diagonal){
                        switch (quadrant){
                            case 1:
                                setPrefHeight(curHeight);
                                setTranslateY(y - curHeight );
                                break;
                            case 2:
                                setPrefWidth(curWidth);
                                setTranslateX(x - curWidth );
                                break;
                            case 3:
                                setPrefWidth(curWidth);
                                setTranslateX(x  - curWidth );
                                break;
                            default:
                                setPrefWidth(curWidth); 
                        }
                    }
                    else{
                        switch (quadrant){
                            case 1:
                                setPrefHeight(curHeight);
                                setTranslateY(y - curHeight );
                                setPrefWidth(curWidth);
                                break;
                            case 2:
                                setPrefHeight(curHeight);
                                setTranslateY(y - curHeight );
                                setPrefWidth(curWidth);
                                setTranslateX(x - curWidth );
                                break;
                            case 3:
                                setPrefHeight(curHeight);
                                setPrefWidth(curWidth);
                                setTranslateX(x - curWidth );
                                break;
                            default:
                                setPrefHeight(curHeight);
                                setPrefWidth(curWidth);
                        }  
                    }               
                
                }
            };
            if (waspressed) {
              setVisible(true);
              showSidebar.play();
            } else {
              hideSidebar.play();
            }
           if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if(!waspressed)setVisible(false);
            else setVisible(true);
            waspressed=!waspressed;
          }         
          waspressed=!waspressed;
        }
// test for other event names        
//        else if(text.equals("othername")){}
        
    }    
    public boolean isOpen(){return !waspressed;}
}
