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
 **  @file   LamppBrowser.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Creates the web browser invoked by the mouse help button on the bottom right of the Lampp basic scene
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



import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

// try and remove webkit problem *********************************************************

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 * Creates the web browser invoked by the mouse help button on the bottom right of the Lampp basic scene
 * Uses the javafx webkit.
 * 
 * @version 1.0
 */
public class LamppBrowser extends Region {
 String path;
 
// try and remove webkit problem **********************************************************
/*
*/ 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
// ****************************************************************************************

 
    public LamppBrowser(final String helpURL) {
        //apply the styles
		
// try and remove webkit problem ***********************************************************
/* 
*/ 

        getStyleClass().add("browser");
        // load the web page
 
        webEngine.load(helpURL);
         //add the web view to the scene
        getChildren().add(browser);
// *****************************************************************************************
 
    }
    
    public String getPath(){return path;}
    
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
		
	// try and remove webkit problem *******************************************************
 /*
 */		
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }

}
