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
 **  @file   LamppKeyboard.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief software keyboard for use in touchscreen (also works with mouse in non-mobile device versions)
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
	

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;

/**
 *  software keyboard for use in touchscreen (also works with mouse in non-mobile device versions)
 */



public class LamppKeyboard{
    private final VBox root ;

    public LamppKeyboard(ReadOnlyObjectProperty<Node> target) {
        this.root = new VBox(4);
        root.setPadding(new Insets(10));
        root.getStylesheets().add("Lampp.css");
        root.getStyleClass().add("lampp-keyboard");
        // Data for numeral buttons
        final String[] numerals = new String[] {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" 
        };
        final KeyCode[] codes = new KeyCode[] {
            KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3,
            KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7,
            KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0
        };


        final Button delete = createNonshiftableButton("Del", KeyCode.DELETE, false, target);
        final Button select = createNonshiftableButton("Sel", KeyCode.A, true, target);
        // Cursor keys, with graphic instead of text
        double t1,t2,t3,tf;
        tf=24;
        t1=2*tf/3;
        t2=tf/12;
        t3=(t1+t2)/2;
        final Button cursorLeft = createCursorKey(KeyCode.LEFT, false, target, t1, t2, t1, t1, t2, t3);
        final Button cursorRight = createCursorKey(KeyCode.RIGHT, false, target, t2, t2, t2, t1, t1, t3);

    // build layout

      HBox hbox = new HBox(4);
      hbox.setAlignment(Pos.CENTER);
      for (int k = 0; k < 3; k++) {
        hbox.getChildren().add( createNonshiftableButton(numerals[k], codes[k], false, target));
      }
      hbox.getChildren().addAll(delete);
      root.getChildren().add(hbox);
      hbox = new HBox(4);
      for (int k = 3; k < 6; k++) {
        hbox.getChildren().add( createNonshiftableButton(numerals[k], codes[k], false, target));
      }
      hbox.getChildren().addAll(select);
      root.getChildren().add(hbox);
      hbox = new HBox(3);
      for (int k = 6; k < 9; k++) {
        hbox.getChildren().add( createNonshiftableButton(numerals[k], codes[k], false, target));
      }
      root.getChildren().add(hbox);
      hbox = new HBox(3);
      final Button zero=createNonshiftableButton(numerals[9], codes[9], false, target);
      hbox.getChildren().add( cursorLeft);
      hbox.getChildren().add( zero);
      hbox.getChildren().add( cursorRight);

      root.getChildren().add(hbox);




    }
    public LamppKeyboard() {
        this(null);
    }
        

    // Creates a button with fixed text not responding to Shift
    private Button createNonshiftableButton(final String text, final KeyCode code, final boolean ctrl, final ReadOnlyObjectProperty<Node> target) {
        StringProperty textProperty = new SimpleStringProperty(text);
        Button button = createButton(textProperty, code, ctrl, target);
        return button;
    }
  
    // Creates a button with mutable text, and registers listener with it
    private Button createButton(final ObservableStringValue text, final KeyCode code, final boolean ctrl, final ReadOnlyObjectProperty<Node> target) {
        final Button button = new Button();
        button.textProperty().bind(text);
        
        // Important not to grab the focus from the target:
        button.setFocusTraversable(false);
    
        // Add a style class for css:
        button.getStyleClass().add("lampp-keyboard-button");
        button.getStyleClass().add("lampp-text-field");
        button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            final Node targetNode ;
            if (target != null) {
                targetNode = target.get();
            } else {
                targetNode = view().getScene().getFocusOwner();
            }

            if (targetNode != null) {
                final String character;
                if (text.get().length() == 1) {
                    character = text.get();
                } else {
                    character = KeyEvent.CHAR_UNDEFINED;
                }
                final KeyEvent keyPressEvent = createKeyEvent(button, targetNode, KeyEvent.KEY_PRESSED, character, code, ctrl);
                targetNode.fireEvent(keyPressEvent);
                final KeyEvent keyReleasedEvent = createKeyEvent(button, targetNode, KeyEvent.KEY_RELEASED, character, code, ctrl);
                targetNode.fireEvent(keyReleasedEvent);
                if (character == null ? KeyEvent.CHAR_UNDEFINED != null : !character.equals(KeyEvent.CHAR_UNDEFINED)) {
                    final KeyEvent keyTypedEvent = createKeyEvent(button, targetNode, KeyEvent.KEY_TYPED, character, code, ctrl);
                    targetNode.fireEvent(keyTypedEvent);
                }
            }
        }
    });
    return button;
    }

    // Utility method to create a KeyEvent from the Modifiers
    private KeyEvent createKeyEvent(Object source, EventTarget target,
            EventType<KeyEvent> eventType, String character, KeyCode code,
            boolean ctrl) {
        return new KeyEvent(source, target, eventType, character, code.toString(),
            code, false, ctrl,
            false, false);
    }
  
    // Utility method for creating cursor keys:
    private Button createCursorKey(KeyCode code, boolean ctrl, ReadOnlyObjectProperty<Node> target, Double... points) {
        Button button = createNonshiftableButton("", code, ctrl, target);
    
        //    final Node graphic = PolygonBuilder.create().points(points).build();
        final Node graphic = new Polygon();
        for (int i = 0; i < points.length; ++i) {
            ((Polygon)graphic).getPoints().addAll(points[i]);    
        }  
        graphic.setStyle("-fx-fill: -fx-mark-color;");
        button.setGraphic(graphic);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button ;
    }
  
    public Node view() {
        return root ;
    }
        
}



