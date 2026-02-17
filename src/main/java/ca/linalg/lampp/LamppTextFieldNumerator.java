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
 **  @file   LamppTextFieldNumerator.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief text field for integer numerator of rational number
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

import javafx.scene.control.TextField;

/**
 * text field for integer numerator of rational number
 */
public class LamppTextFieldNumerator extends TextField {
    private static int max_chars=8;

    public LamppTextFieldNumerator(){super("0");}
    public LamppTextFieldNumerator(String a){
        super(a);
        if( !isInteger(a,10)) super.setText("0");
    }
    
    public void replaceText(int start, int end, String text) {
        String oldValue = getText();
        if (!text.matches("[a-z]") && !text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
            super.replaceText(start, end, text);
        }
        if (getText().length() > max_chars ) {
            setText(oldValue);
        }
    }

    public void replaceSelection(String text) {
        String oldValue = getText();
        if (!text.matches("[a-z]") && !text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
            super.replaceSelection(text);
        }
        if (getText().length() > max_chars ) {
            setText(oldValue);
        }
    } 
    
   
    public boolean isValid(){
        if(!isInteger(getText(),10))return false;
        else return (Long.valueOf(getText())>=0);
    }     
    
    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    
    public long getLong(){    
        return ( Long.valueOf(getText()) );
    }
    
    public void setMaxChars(int x){max_chars=x;}
    public void reset(){super.setText("0");}
}
