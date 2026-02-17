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
 **  @file   LamppGlobal.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief variables used extensively throughout Lampp
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

/**
 * variables used extensively throughout Lampp
 * @version 1.0
 */
public interface LamppGlobal {
	//version number
	String LAMPPVERSION="1.0";
	
	//default level
	int LEVEL=1;
	
	//maximum number of rows and columns that can be manidisplayed and manipulated
    int MAX_ROWS = 10;
    int MAX_COLS = 10;

    //maximum number operations user may perform (plus one for base matrix storage purposes)
    int MAX_OPS=26;
    
    // preferred size of matrix element view container
    int VIEW_SIZE=100;

    // max/min values for integer displays (LamppShowElement, LamppOpElement classes)
    long CEILING=9999999;
    long FLOOR=-9999999;

    //default Button and LamppButton width/height
    int BUTTONWIDTH=(4*VIEW_SIZE)/10;
    int BUTTONHEIGHT=(4*VIEW_SIZE)/10;

    //default font specs
    double ALLOWEDFONTSIZES[]={6,8,9,10,11,12,14,16,18,20,22,24,26,28,30,36,48,72};
    
    //Operating System ("desktop", "android", "ios")
    String OS=System.getProperty("javafx.platform", "desktop") ;
    
    // names of selected field
    String FIELDS[]={"Rational","Gaussian","Integer Mod "};
    
    int MODULUS_SIZE = 3;

}
