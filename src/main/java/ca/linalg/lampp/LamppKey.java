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
 **  @file   LamppKey.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief key press handling used for software touchscreen keyboard
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
	
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * key press handling used for software touchscreen keyboard
 */
 final class LamppKey {
        private final KeyCode keyCode;
        private final BooleanProperty pressedProperty;

        public LamppKey(final KeyCode keyCode) {
            this.keyCode = keyCode;
            this.pressedProperty = new SimpleBooleanProperty(this, "pressed");
        }

        public KeyCode getKeyCode() {
            return keyCode;
        }

        public boolean isPressed() {
            return pressedProperty.get();
        }

        public void setPressed(final boolean value) {
            pressedProperty.set(value);
        }

        public Node createNode() {
            final StackPane keyNode = new StackPane();
            keyNode.setFocusTraversable(true);
            installEventHandler(keyNode);

            final Rectangle keyBackground = new Rectangle(50, 50);
            keyBackground.fillProperty().bind(
                    Bindings.when(pressedProperty)
                            .then(Color.RED)
                            .otherwise(Bindings.when(keyNode.focusedProperty())
                                               .then(Color.LIGHTGRAY)
                                               .otherwise(Color.WHITE)));
            keyBackground.setStroke(Color.BLACK);
            keyBackground.setStrokeWidth(2);
            keyBackground.setArcWidth(12);
            keyBackground.setArcHeight(12);

            final Text keyLabel = new Text(keyCode.getName());
            keyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            
            keyNode.getChildren().addAll(keyBackground, keyLabel);

            return keyNode;
        }

        private void installEventHandler(final Node keyNode) {
            // handler for enter key press / release events, other keys are
            // handled by the parent (keyboard) node handler
            final EventHandler<KeyEvent> keyEventHandler =
                    new EventHandler<KeyEvent>() {
                        public void handle(final KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                setPressed(keyEvent.getEventType()
                                               == KeyEvent.KEY_PRESSED);

//                                keyEvent.consume();
                            }
                        }    
                    };


            keyNode.setOnKeyPressed(keyEventHandler);
            keyNode.setOnKeyReleased(keyEventHandler);
            keyNode.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.isPrimaryButtonDown()){setPressed(true); mouseEvent.consume();}
            }
           });
            keyNode.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()>0){setPressed(false); keyNode.requestFocus();mouseEvent.consume();}
            }
           });
            
            keyNode.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override
                public void handle(TouchEvent touchEvent) {
                setPressed(true);
                touchEvent.consume();
                }
            });

            keyNode.setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override
                public void handle(TouchEvent touchEvent) {
                setPressed(false);
                touchEvent.consume();
                }
            });            
        }
    }  
