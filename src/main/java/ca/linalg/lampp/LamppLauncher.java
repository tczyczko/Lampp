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
 **  @file   LamppLauncher.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief alternative main to create executable fat jar file with maven shade plugin
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

/*
 * This class is needed as a secondary entry point if one is creating a 
 * non-modular fat jar file. See:
 * <https://github.com/openjfx/samples/blob/master/CommandLine/Non-modular/Maven/hellofx/pom.xml>
 * The main class would be specified in the maven shade plugin as:
 * ca.linalg.lampp.LamppLauncher
 * instead of:
 * ca.linalg.lampp.Lampp
 * this is to get around the classpath issue with jar files
 */

package ca.linalg.lampp;

public class LamppLauncher {
    
    public static void main(String[] args) {
        Lampp.main(args);
    }
}
