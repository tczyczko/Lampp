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
 **  @file   LamppViewText.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief display a text element (also used with boolean values)
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


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * display a text element (also used with boolean values)
 */
public class LamppViewText extends LamppViewElement{
    
    public Text listText;
    public double xText,yText;
    public double textWidth;
    public double textHeight;
    
    public Text listText2;
    public double xText2,yText2;
    public double textWidth2;
    public double textHeight2;

// Constructors  
    public LamppViewText(){
        this(0, 0, 100, 100, 8, "0" ,0, 0,12);
    }
    public LamppViewText(double xx, double yy,
            double wi, double he, int maxC,
            String listString,int row, int column, double bgFntSiz){
        super(xx, yy,wi, he, maxC,row, column);
        this.matrixPosition = new int[]{row, column};
        maxChar=maxC;
        x=xx;
        y=yy;

        double bigFontSize=LamppViewElement.bestFontSize(bgFntSiz);    
        
        
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        listText = new Text(listString);
        listText.setFont(Font.font(bigFontSize));
        textWidth=listText.getLayoutBounds().getWidth();
        textHeight=listText.getLayoutBounds().getHeight();       
        if (textWidth+10<wi)width=wi;
        else width=textWidth+10;
        if (textHeight+10<he)height=he;
        else height=textHeight+10;
        xCenter=x + (width/2);
        yCenter=y + (height/2);
        xText=( xCenter -  (textWidth / 2) );
        yText=( yCenter + (textHeight / 4) );
        listText.setFill(Color.BLACK);   
        listText.setX( xText );
        listText.setY( yText ) ;
        listText2 = new Text(listString);
        listText2.setFont(Font.font(bigFontSize));
        textWidth2=textWidth;
        textHeight2=textHeight;
        xText2=xText;
        yText2=yText;
        listText2.setX( xText2 );
        listText2.setY( yText2 ) ;
        listText2.setFill(Color.RED);      
        listText2.setVisible(false);
        
        rect = new Rectangle(x,y,width,height);        
        common();
        this.getChildren().addAll(rect,listText,listText2);
    }
    
// Methods
    
    // following methods should be overwritten for new classes
    public Object getViewElement(){
        return listText;
    }

    /**
     *
     * @param listString
     */
        @Override
    public void setViewElement(Object element){
        String listString =(String)element;
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        listText.setText(listString);
        textWidth=listText.getLayoutBounds().getWidth();
        textHeight=listText.getLayoutBounds().getHeight();
               
        double fontWidthFactor=textWidth/width;
        double fontHeightFactor=textHeight/height;
        if(fontWidthFactor<=1){fontWidthFactor=0;}
        if(fontHeightFactor<=1){fontHeightFactor=0;}
        fontWidthFactor = 1/Math.max(fontWidthFactor,fontHeightFactor);
        if (fontWidthFactor!=0){
            fontHeightFactor=listText.getFont().getSize()*fontWidthFactor;
            fontHeightFactor=bestFontSize(fontHeightFactor);
            listText.setFont( new Font(listText.getFont().getName(), fontHeightFactor ));
            textWidth=listText.getLayoutBounds().getWidth();
            textHeight=listText.getLayoutBounds().getHeight();
        }        
       
        xText=( xCenter -  (textWidth / 2) );
        yText=( yCenter + (textHeight / 4) );
        listText.setX( xText );
        listText.setY( yText ) ; 
        
        listText2.setText(listString);
        textWidth2=listText2.getLayoutBounds().getWidth();
        textHeight2=listText2.getLayoutBounds().getHeight();
        
        
        fontWidthFactor=textWidth2/width;
        fontHeightFactor=textHeight2/height;
        if(fontWidthFactor<=1){fontWidthFactor=0;}
        if(fontHeightFactor<=1){fontHeightFactor=0;}
        fontWidthFactor = 1/Math.max(fontWidthFactor,fontHeightFactor);
        if (fontWidthFactor!=0){
            fontHeightFactor=listText2.getFont().getSize()*fontWidthFactor;
            fontHeightFactor=bestFontSize(fontHeightFactor);
            listText2.setFont( new Font(listText2.getFont().getName(), fontHeightFactor ));
            textWidth2=listText2.getLayoutBounds().getWidth();
            textHeight2=listText2.getLayoutBounds().getHeight();
        }    
        
     
        xText2=( xCenter -  (textWidth2 / 2) );
        yText2=( yCenter + (textHeight2 / 4) );
        listText2.setX( xText2 );
        listText2.setY( yText2 ) ;              
}

    /**
     *
     * @param fontSize
     */
    @Override
    public void setFontSize(double fontSize){
//        listText.setFont( Font.font(listText.getFont().getName(), FontWeight.BOLD, fontSize ));   
        listText.setFont( Font.font(listText.getFont().getName(), fontSize ));   
        textWidth=listText.getLayoutBounds().getWidth();
        textHeight=listText.getLayoutBounds().getHeight();
        double fontWidthFactor=textWidth/width;
        double fontHeightFactor=textHeight/height;
        if(fontWidthFactor<=1){fontWidthFactor=0;}
        if(fontHeightFactor<=1){fontHeightFactor=0;}
        fontWidthFactor = 1/Math.max(fontWidthFactor,fontHeightFactor);
        if (fontWidthFactor!=0){
            fontHeightFactor=listText.getFont().getSize()*fontWidthFactor;
            fontHeightFactor=bestFontSize(fontHeightFactor);
            listText.setFont( new Font(listText.getFont().getName(), fontHeightFactor ));
            textWidth=listText.getLayoutBounds().getWidth();
            textHeight=listText.getLayoutBounds().getHeight();
        }        
        xText=( xCenter -  (textWidth / 2) );
        yText=( yCenter + (textHeight / 4) );
        listText.setX( xText );
        listText.setY( yText ) ;      
        listText2.setFont( new Font(listText.getFont().getName(), fontSize ));   
        textWidth2=listText2.getLayoutBounds().getWidth();
        textHeight2=listText2.getLayoutBounds().getHeight();  
        fontWidthFactor=textWidth2/width;
        fontHeightFactor=textHeight2/height;
        if(fontWidthFactor<=1){fontWidthFactor=0;}
        if(fontHeightFactor<=1){fontHeightFactor=0;}
        fontWidthFactor = 1/Math.max(fontWidthFactor,fontHeightFactor);
        if (fontWidthFactor!=0){
            fontHeightFactor=listText2.getFont().getSize()*fontWidthFactor;
            fontHeightFactor=bestFontSize(fontHeightFactor);
            listText2.setFont( new Font(listText2.getFont().getName(), fontHeightFactor ));
            textWidth2=listText2.getLayoutBounds().getWidth();
            textHeight2=listText2.getLayoutBounds().getHeight();
        }
        xText2=( xCenter -  (textWidth2 / 2) );
        yText2=( yCenter + (textHeight2 / 4) );
        listText2.setX( xText2 );
        listText2.setY( yText2 ) ; 
    }

    /**
     *
     */
    @Override
    public void setEnteredView(){
        listText.setVisible(false);
        listText2.setVisible(true);  
    }
    /**
     *
     */
    @Override
    public void setExitedView(){
        listText2.setVisible(false);
        listText.setVisible(true); 
    }

    /**
     *
     */
    @Override
    public void setLeftSelected(){
        leftSelected=true;
        rightSelected=inputSelected=false;
        listText.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        rect.setEffect(null);          
        rect.setFill(Color.BLUE);        
        rect.setArcHeight(0);
        rect.setArcWidth(0);
    }

    /**
     *
     */
    @Override
    public void setRightSelected(){
        rightSelected=true;
        leftSelected=inputSelected=false;
        listText.setFill(Color.BLACK);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        rect.setFill(Color.GREY);
        rect.setEffect(null);   
        rect.setStrokeWidth(1);            
        listText.setEffect(null);        
        rect.setArcHeight(0);
        rect.setArcWidth(0);
    }    

    /**
     *
     */
    @Override
    public void setInputSelected(){
        inputSelected=true;
        rightSelected=leftSelected=false;
        listText.setEffect(ds);
        rect.setArcHeight(height*0.3-2*strokeWidth);
        rect.setArcWidth(width*0.3-2*strokeWidth);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(strokeWidth);
        rect.setEffect(is);
        rect.setFill(Color.LIGHTGREEN);
        listText.setFill(Color.BLACK);
    }   

    /**
     *
     */
    @Override
    public void setClearSelected(){
        inputSelected=rightSelected=leftSelected=false;
        rect.setEffect(null);        
        listText.setEffect(null);        
        rect.setFill(Color.TRANSPARENT);
        rect.setStrokeWidth(0);            
        listText.setFill(Color.BLACK);
        rect.setArcHeight(0);
        rect.setArcWidth(0);
    }
    
}
