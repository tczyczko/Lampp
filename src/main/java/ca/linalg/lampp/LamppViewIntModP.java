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
 **  @file   LamppViewIntModP.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief display integer modulus a prime
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


import javafx.scene.text.Font;


/**
 * display integer modulus a prime power
 */
public class LamppViewIntModP extends LamppViewText {
    static LamppElementIntModP swap = new LamppElementIntModP();
    LamppElementIntModP limpe;
  // Constructors  
    public LamppViewIntModP(){
        this(0, 0, 100, 100, 8, LamppElementIntModP.one ,0, 0, 12);
    }
    public LamppViewIntModP(double xx, double yy,
            double wi, double he, int maxC,
            LamppElementIntModP limpe,int row, int column,double bigFntSiz){
        super(xx, yy,wi, he, maxC,String.valueOf(limpe.getResidue()),row, column, bigFntSiz);
        setViewElement(limpe);
    }


// Methods
    
    // following methods should be overwritten for new classes

    /**
     *
     * @return
     */
        @Override
    public Object getViewElement(){
        return limpe;
    }

    /**
     *
     * @param listString
     */
        @Override
    public void setViewElement(Object element){
        String listString =""+((LamppElementIntModP)element).getResidue();
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        listText.setText(listString);
        textWidth=listText.getLayoutBounds().getWidth();
        textHeight=listText.getLayoutBounds().getHeight();
        String listString2 =""+LamppElementIntModP.getModulus();
        listText2.setText(listString2);
        textWidth2=listText2.getLayoutBounds().getWidth();
        textHeight2=listText2.getLayoutBounds().getHeight();
        limpe=new LamppElementIntModP((LamppElementIntModP)element);
        double fontWidthFactor=textWidth2/width;
        double fontHeightFactor=textHeight2/height;
        if(fontWidthFactor<=1){fontWidthFactor=0;}
        if(fontHeightFactor<=1){fontHeightFactor=0;}
        fontWidthFactor = 1/Math.max(fontWidthFactor,fontHeightFactor);
        if (fontWidthFactor!=0){
            fontHeightFactor=listText.getFont().getSize()*fontWidthFactor;
            fontHeightFactor=LamppViewElement.bestFontSize(fontHeightFactor);
            listText.setFont( new Font(listText.getFont().getName(), fontHeightFactor ));
            listText2.setFont( new Font(listText.getFont().getName(), fontHeightFactor ));
            textWidth=listText.getLayoutBounds().getWidth();
            textHeight=listText.getLayoutBounds().getHeight();
            textWidth2=listText2.getLayoutBounds().getWidth();
            textHeight2=listText2.getLayoutBounds().getHeight();
        }        
       
        xText=( xCenter -  (textWidth / 2) );
        yText=( yCenter + (textHeight / 4) );
        listText.setX( xText );
        listText.setY( yText ) ; 
        xText=( xCenter -  (textWidth2 / 2) );
        yText=( yCenter + (textHeight2 / 4) );
        listText2.setX( xText );
        listText2.setY( yText ) ;              
}
    public boolean isZero(){return limpe.isZero();}
    public void additiveInverse(){limpe.additiveInverse();setViewElement(limpe);}
    public void multiplicativeInverse(){limpe.multiplicativeInverse();setViewElement(limpe);}
 
    public void op1(Object so){
        swap.equals((LamppElementIntModP)((LamppViewIntModP)so).getViewElement());
        ((LamppViewIntModP)so).setViewElement(limpe);
        limpe.equals(swap);
        setViewElement(limpe);
    }
    public void op2(Object so){
        swap.equals((LamppElementIntModP)((LamppViewIntModP)so).getViewElement());
        limpe.product(swap);
        setViewElement(limpe);
    }
    public void op3(Object so, Object mo){
        swap.equals((LamppElementIntModP)((LamppViewIntModP)so).getViewElement());
        swap.product((LamppElementIntModP)((LamppViewIntModP)mo).getViewElement());
        limpe.sum(swap);
        setViewElement(limpe);
    }
      
}
