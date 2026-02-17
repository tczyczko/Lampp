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
 **  @file   LamppHelpPane.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Animates a ScrollPane on and off screen into a quadrant from an origin point or line.
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
	
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Animates a ScrollPane on and off screen into a quadrant from an origin point or line.
 * generates a mouse button for control
 * ScrollPane content is usually a LamppBrowserWrapper set for a certain URL
 */
public class LamppHelpPane extends ScrollPane {
  /** Animates a node on and off screen into a quadrant from an origin point or line. */

    /** @return a control button to hide and show the LamppSlidingPane */
    public Button getControlButton() { return controlButton; }
    private final Button controlButton;
    private double expandedWidth,expandedHeight;
    private final double x,y;
    private final String buttonExpand, buttonCollapse;
    boolean waspressed=false;
    final int quadrant;
    public final double buttonWidth, buttonHeight;

    /**
     * @param x1 the x coordinate of the origin
     * @param y1 the y coordinate of the origin
     * @param wi the preferred width if > content width
     * @param he the preferred height if > content height
     * @param quadrant (1,2,3 or 4) to open into from origin
     * @param diagonal true - open diagonally  false - open from a line through the origin parallel to an axis
     * @param startClosed true - open at start, else closed at start
     * @param buttonSkinNameSuffix usually "" or "-50" (for 100 or 50 point sized button skin images)
     * @param content Pane containing the content viewed in this pane
     */
    LamppHelpPane(double x1, double y1, double wi, double he,
            final int quadrant, final boolean diagonal, final boolean startClosed,
            final String buttonSkinNameSuffix,
            Pane content) {
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //no scroll bars
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        setPannable(true); //allow mouse panning
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
        expandedWidth=Math.max(Math.abs(wi),content.getPrefWidth());
        expandedHeight=Math.max(Math.abs(he),content.getPrefHeight());
        this.setMinHeight(0);  
        this.setMinWidth(0);
        this.setPrefWidth(expandedWidth);
        this.setPrefHeight(expandedHeight);
        this.quadrant=quadrant;
        if(expandedHeight>y1)y1=expandedHeight+1;
        x=x1;
        y=y1;
      // create a bar to hide and show.
//        content.setTranslateX(x);
        setContent(content);      
        // create a button to hide and show the sidebar.
        if(quadrant==1||quadrant==4){
            if(buttonSkinNameSuffix.length()==0){
                buttonExpand="lampp-show-expand";
                buttonCollapse="lampp-hide-collapse";
            }
            else {
                buttonExpand="lampp-show-expand"+buttonSkinNameSuffix;
                buttonCollapse="lampp-hide-collapse"+buttonSkinNameSuffix;
            }
        }
        else{
            if(buttonSkinNameSuffix.length()!=0){
                buttonExpand="lampp-show-expand-r".concat(buttonSkinNameSuffix);
                buttonCollapse="lampp-hide-collapse-r".concat(buttonSkinNameSuffix);
            }
            else{
                buttonExpand="lampp-show-expand-r";
                buttonCollapse="lampp-hide-collapse-r";
            }
        }
        controlButton = new Button();
        controlButton.getStyleClass().add(buttonCollapse);
        controlButton.setStyle("-fx-background-color: transparent;");
        controlButton.setTranslateX(x);
        controlButton.setTranslateY(y); 
        Image mouse = new Image(buttonExpand.substring(6).concat(".png"));  
        Scene snapScene = new Scene(controlButton);  
        snapScene.snapshot(null);  
        buttonWidth=mouse.getWidth()+controlButton.getWidth();
        buttonHeight=mouse.getHeight()+controlButton.getHeight();
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
                controlButton.setTranslateX(x-buttonWidth);
        }
        // apply the animations when the button is pressed. 
        controlButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent actionEvent) {
          // create an animation to hide sidebar.
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
          hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
              setVisible(false);
              controlButton.getStyleClass().remove(buttonCollapse);
              controlButton.getStyleClass().add(buttonExpand);
            }
          });
  
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
          showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
              controlButton.getStyleClass().add(buttonCollapse);
              controlButton.getStyleClass().remove(buttonExpand);
            }
          });
  
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
        
      });
      if(startClosed)controlButton.fire();
    }
    public double getButtonWidth(){return buttonWidth;}    
    public double getButtonHeight(){return buttonHeight;}    
    public boolean isOpen(){return !waspressed;}
  }
