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
 **  @file   LamppViewRational.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief display rational element
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



import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * display rational element
 *   forms are displayed dependent on size of numerators and denominators
 *   container is at least as wide as the largest of the numerators and denominators + 2*iSymbolWidth
 *   cases 3-6 are used if largest of numerators + largest of denominators <= MAXCHAR-2*iSymbolWidth
 *   ie. will not display any number with numerator or denominator greater in length than MAXCHAR-3
 *   or greater than CEILING or less than FLOOR
 *   in such cases: no change of number and goodValue is set to false - should be tested and reset by setting method 
 */
public class LamppViewRational extends LamppViewElement{
// FLOOR and CEILING defined in LamppGlobal, implemented in LamppViewElement
    static LamppElementRational swap = new LamppElementRational();
    private LamppElementRational r;
    double bigFontSize, smallFontSize;
    int numElements;
    double doubleNumber;
    Line bigLine, smallLine;
    double bigLineX, bigLineY;
    double smallLineX, smallLineY;
    double negSize;   
    public Text leadingInteger;
    public double xText,yText;
    public double leadingIntegerWidth;
    public double leadingIntegerHeight;   
    public Text numeratorText;
    public double numeratorTextWidth;
    public double numeratorTextHeight;       
    public Text denominatorText;
    public double denominatorTextWidth;
    public double denominatorTextHeight;   
    private Text smallNumeratorText;
    private double smallNumeratorTextWidth;
    private double smallNumeratorTextHeight;    
    private Text smallDenominatorText;
    private double smallDenominatorTextWidth;
    private double smallDenominatorTextHeight;
    private String listString;
    private Text neg;
    private double negWidth;
    
        public LamppViewRational(){
        this(0,0,100,100,8,LamppElementRational.zero,1,1,12);
        }
    
        public LamppViewRational(double xx, double yy,
            double wi, double he, int maxC,
            LamppElementRational rr,int row, int column, double bgFntSiz){
        super(xx,yy,wi,he,maxC,row,column);
        r = rr;
        matrixPosition = new int[]{row, column};
        maxChar=maxC;
        x=xx;
        y=yy;
        bigFontSize=bestFontSize(bgFntSiz);     
        listString=String.valueOf(r.toInt());
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        leadingInteger=new Text(listString);
        leadingInteger.setFont(Font.font(bigFontSize));
        leadingIntegerWidth=leadingInteger.getLayoutBounds().getWidth();
        leadingIntegerHeight=leadingInteger.getLayoutBounds().getHeight();
        listString=String.valueOf(r.getNumerator());
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        numeratorText = new Text(listString);
        numeratorText.setFont(Font.font(bigFontSize));
        numeratorTextWidth=numeratorText.getLayoutBounds().getWidth();
        numeratorTextHeight=numeratorText.getLayoutBounds().getHeight();  
        listString=String.valueOf(r.getDenominator());
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        denominatorText = new Text(listString);
        denominatorText.setFont(Font.font(bigFontSize));
        denominatorTextWidth=denominatorText.getLayoutBounds().getWidth();
        denominatorTextHeight=denominatorText.getLayoutBounds().getHeight();
        if(r.getDenominator()==1)numElements=1;
        else if (!r.isBetweenUnities()) numElements=3;
        else numElements=2;
        doubleNumber=Math.max(numeratorTextWidth,denominatorTextWidth)+10;
        if (doubleNumber<wi)width=wi;
        else width=doubleNumber;
        doubleNumber=numeratorTextHeight+denominatorTextHeight+(2*strokeWidth);  
        if (doubleNumber<he)height=he;
        else height=doubleNumber;
        smallFontSize=bestFontSize(bigFontSize/2);
        smallNumeratorText = new Text(String.valueOf(Math.abs(r.getNumerator()-(r.toInt()*r.getDenominator()))));
        smallNumeratorText.setFont(Font.font(smallFontSize));
        smallNumeratorTextWidth=smallNumeratorText.getLayoutBounds().getWidth();
        smallNumeratorTextHeight=smallNumeratorText.getLayoutBounds().getHeight();
        smallDenominatorText = new Text(String.valueOf(r.getDenominator()));
        smallDenominatorText.setFont(Font.font(smallFontSize));
        smallDenominatorTextWidth=smallDenominatorText.getLayoutBounds().getWidth();
        smallDenominatorTextHeight=smallDenominatorText.getLayoutBounds().getHeight(); 
        rect = new Rectangle(x,y,width,height);      
        xCenter=x + (width/2);
        yCenter=y + (height/2);
        xText=( xCenter -  (numeratorTextWidth / 2) );
        yText=( yCenter - (numeratorTextHeight*0.2) );
        neg = new Text("-");
        neg.setFont(Font.font(bigFontSize));
        negWidth=neg.getLayoutBounds().getWidth()/2;
        if(r.isNegative())xText=xText-(negWidth);        
        numeratorText.setFill(Color.BLACK);   
        numeratorText.setX( xText );
        numeratorText.setY( yText ) ;
        if(r.isNegative())xText=xText+(negWidth);
        bigLineX=xText;
        xText=(xCenter -  (denominatorTextWidth / 2) );
        bigLineX=Math.min(xText,bigLineX);
        bigLineY=Math.max(numeratorTextWidth,denominatorTextWidth);
        if(r.isNegative())bigLineY=bigLineY-(negWidth);
        yText=( yCenter  +(denominatorTextHeight*0.75));
        denominatorText.setFill(Color.BLACK);   
        denominatorText.setX( xText );
        denominatorText.setY( yText ) ;
        bigLine=new Line(bigLineX,yCenter,bigLineX+bigLineY,yCenter);
        bigLine.setStrokeWidth(Math.max(1, numeratorTextHeight*0.02));
        bigLine.setFill(null);
        bigLine.setStroke(Color.BLACK);
        bigLine.setStrokeType(StrokeType.OUTSIDE);
        smallLine=new Line(xText,yCenter,xText+smallDenominatorTextWidth,yCenter);
        leadingInteger.setVisible(false);
        if (numElements==1){
            xText=(xCenter- (leadingIntegerWidth / 2));
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            leadingInteger.setVisible(true);
            numeratorText.setVisible(false);
            denominatorText.setVisible(false);
            bigLine.setVisible(false);
        }
        else if (numElements==3){
            xText=xCenter- ((leadingIntegerWidth+smallDenominatorTextWidth) / 2) - negWidth;
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            xText=xText+leadingIntegerWidth+negWidth;
            smallLine=new Line(xText,yCenter,xText+smallDenominatorTextWidth,yCenter);
            smallLine.setStrokeWidth(smallNumeratorTextHeight*0.02);
            smallLine.setStrokeType(StrokeType.OUTSIDE);
            yText=( yCenter  +(smallDenominatorTextHeight*0.75));
            smallDenominatorText.setX( xText );
            smallDenominatorText.setY( yText ) ;     
            xText=xText+((smallDenominatorTextWidth-smallNumeratorTextWidth)/2);
            yText=( yCenter  -(smallDenominatorTextHeight*0.2));
            smallNumeratorText.setX( xText );
            smallNumeratorText.setY( yText ) ;            
        }
        smallNumeratorText.setVisible(false);
        smallDenominatorText.setVisible(false);
        smallLine.setVisible(false);
        common();
        this.getChildren().addAll(rect,numeratorText,denominatorText,bigLine,leadingInteger,
                smallNumeratorText,smallDenominatorText,smallLine);        
        }
// Methods
    
    // following methods should be overwritten for new classes
    public LamppElementRational getViewElement(){
        return r;
    }

    /**
     *
     * @param listString
     */
        @Override
    public void setViewElement(Object element){
        LamppElementRational test =new LamppElementRational((LamppElementRational)element);
    //test for valid object viewing parameters    
        if(test.getNumerator()>CEILING||test.getNumerator()<FLOOR||
                test.getDenominator()>CEILING||String.valueOf(test.getNumerator()).length()>maxChar||
                String.valueOf(test.getDenominator()).length()>maxChar) {
            NumberFormat formatter = new DecimalFormat("0.###E00");
            listString=formatter.format(test.toDouble());
            leadingInteger.setText(listString);
            leadingIntegerWidth=leadingInteger.getLayoutBounds().getWidth();
            leadingIntegerHeight=leadingInteger.getLayoutBounds().getHeight();
            numElements=1;
            xText=(xCenter- (leadingIntegerWidth / 2));
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            leadingInteger.setVisible(true);
            numeratorText.setVisible(false);
            denominatorText.setVisible(false);
            bigLine.setVisible(false);
            setGoodValue(false);
            return;
        }
        r=test;
        setGoodValue(true);
        listString=String.valueOf(r.getNumerator());
        numeratorText.setText(listString);
        numeratorText.setFont(Font.font(bigFontSize));
        numeratorTextWidth=numeratorText.getLayoutBounds().getWidth();
        numeratorTextHeight=numeratorText.getLayoutBounds().getHeight();
        listString=String.valueOf(r.getDenominator());
        denominatorText.setText(listString);
        denominatorText.setFont(Font.font(bigFontSize));
        denominatorTextWidth=denominatorText.getLayoutBounds().getWidth();
        denominatorTextHeight=denominatorText.getLayoutBounds().getHeight();
        double fontWidthFactor=width/Math.max(numeratorTextWidth, denominatorTextWidth);
        double fontHeightFactor=height/(numeratorTextHeight+denominatorTextHeight);
        if(fontWidthFactor>=1){fontWidthFactor=0;}
        if(fontHeightFactor>=1){fontHeightFactor=0;}
        fontWidthFactor = Math.max(fontWidthFactor,fontHeightFactor);
        Font newfont= new Font(numeratorText.getFont().getName(), bigFontSize );
        if (fontWidthFactor!=0){
            fontHeightFactor=numeratorText.getFont().getSize()*fontWidthFactor;
            bigFontSize=bestFontSize(fontHeightFactor);
            newfont= new Font(numeratorText.getFont().getName(), bigFontSize );
            numeratorText.setFont(newfont);
            numeratorTextWidth=numeratorText.getLayoutBounds().getWidth();
            numeratorTextHeight=numeratorText.getLayoutBounds().getHeight();
            leadingInteger.setFont( newfont );
            leadingIntegerWidth=leadingInteger.getLayoutBounds().getWidth();
            leadingIntegerHeight=leadingInteger.getLayoutBounds().getHeight();
            denominatorText.setFont(newfont);
            denominatorTextWidth=denominatorText.getLayoutBounds().getWidth();
            denominatorTextHeight=denominatorText.getLayoutBounds().getHeight();
        }
        listString=String.valueOf(r.toInt());
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        leadingInteger.setText(listString);
        leadingInteger.setFont( newfont );
        leadingIntegerWidth=leadingInteger.getLayoutBounds().getWidth();
        leadingIntegerHeight=leadingInteger.getLayoutBounds().getHeight();
        smallFontSize=bestFontSize(bigFontSize/2);
        newfont= new Font(numeratorText.getFont().getName(), smallFontSize );
        smallNumeratorText.setText(String.valueOf(Math.abs(r.getNumerator()-(r.toInt()*r.getDenominator()))));
        smallNumeratorText.setFont(newfont);
        smallNumeratorTextWidth=smallNumeratorText.getLayoutBounds().getWidth();
        smallNumeratorTextHeight=smallNumeratorText.getLayoutBounds().getHeight();
        smallDenominatorText.setText(String.valueOf(r.getDenominator()));
        smallDenominatorText.setFont(newfont);
        smallDenominatorTextWidth=smallDenominatorText.getLayoutBounds().getWidth();
        smallDenominatorTextHeight=smallDenominatorText.getLayoutBounds().getHeight();
        if(r.getDenominator()==1)numElements=1;
        else if (!r.isBetweenUnities()) numElements=3;
        else numElements=2;
        xText=( xCenter -  (numeratorTextWidth / 2) );
        yText=( yCenter - (numeratorTextHeight*0.2) );
        neg = new Text("-");
        neg.setFont(Font.font(bigFontSize));
        negWidth=neg.getLayoutBounds().getWidth()/2;
        if(r.isNegative())xText=xText-(negWidth);
        numeratorText.setFill(Color.BLACK);
        numeratorText.setX( xText );
        numeratorText.setY( yText ) ;
        if(r.isNegative())xText=xText+(negWidth);
        bigLineX=xText;
        xText=(xCenter -  (denominatorTextWidth / 2) );
        bigLineX=Math.min(xText,bigLineX);
        bigLineY=Math.max(numeratorTextWidth,denominatorTextWidth);
        if(r.isNegative())bigLineY=bigLineY-(negWidth);
        yText=( yCenter  +(denominatorTextHeight*0.75));
        denominatorText.setFill(Color.BLACK);
        denominatorText.setX( xText );
        denominatorText.setY( yText ) ;
        bigLine.setStartX(bigLineX);
        bigLine.setStartY(yCenter);
        bigLine.setEndX(bigLineX+bigLineY);
        bigLine.setEndY(yCenter);
        bigLine.setStrokeWidth(Math.max(1, numeratorTextHeight*0.02));
        //        bigLine.setStrokeWidth(numeratorTextHeight*0.02);
        bigLine.setFill(null);
        bigLine.setStroke(Color.BLACK);
        bigLine.setStrokeType(StrokeType.OUTSIDE);
        if (numElements==1){
            xText=(xCenter- (leadingIntegerWidth / 2));
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            leadingInteger.setVisible(true);
            numeratorText.setVisible(false);
            denominatorText.setVisible(false);
            bigLine.setVisible(false);
        }
        else if (numElements==3){
            xText=xCenter- ((leadingIntegerWidth+smallDenominatorTextWidth) / 2) - negWidth;
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            xText=xText+leadingIntegerWidth+negWidth;
            smallLine.setStartX(xText);
            smallLine.setStartY(yCenter);
            smallLine.setEndX(xText+smallDenominatorTextWidth);
            smallLine.setEndY(yCenter);
            smallLine.setStrokeWidth(smallNumeratorTextHeight*0.02);
            smallLine.setStrokeType(StrokeType.OUTSIDE);
            yText=( yCenter  +(smallDenominatorTextHeight*0.75));
            smallDenominatorText.setX( xText );
            smallDenominatorText.setY( yText ) ;
            xText=xText+((smallDenominatorTextWidth-smallNumeratorTextWidth)/2);
            yText=( yCenter  -(smallDenominatorTextHeight*0.2));
            smallNumeratorText.setX( xText );
            smallNumeratorText.setY( yText ) ;
        }
        setExitedView();       
    }

    /**
     *
     * @param fontSize
     */
    @Override
    public void setFontSize(double fontSize){
        if(fontSize==bigFontSize)return;
        Text test = new Text("-");
        Font newfont= new Font(numeratorText.getFont().getName(), fontSize );
        test.setFont(newfont);
        double testWidth=width/(test.getLayoutBounds().getWidth()*(maxChar+1));
        double testHeight=height/(test.getLayoutBounds().getHeight()*4);
        if (testWidth<1){
            if(testHeight<testWidth){
                fontSize=fontSize*testHeight;
            }
            else fontSize=fontSize*testWidth;
        }
        else if (testHeight<1){
            fontSize=fontSize*testHeight;
        }
        bigFontSize=bestFontSize(fontSize);
        newfont= new Font(numeratorText.getFont().getName(), bigFontSize );
        numeratorText.setFont(newfont);
        numeratorTextWidth=numeratorText.getLayoutBounds().getWidth();
        numeratorTextHeight=numeratorText.getLayoutBounds().getHeight();  
        leadingInteger.setFont( newfont );
        leadingIntegerWidth=leadingInteger.getLayoutBounds().getWidth();
        leadingIntegerHeight=leadingInteger.getLayoutBounds().getHeight();
        denominatorText.setFont(newfont);
        denominatorTextWidth=denominatorText.getLayoutBounds().getWidth();
        denominatorTextHeight=denominatorText.getLayoutBounds().getHeight();
        listString=String.valueOf(r.toInt());
        String.valueOf(r.getDenominator());
        if(listString.length()>maxChar){listString=listString.substring(0, maxChar-1);}
        leadingInteger.setText(listString);
        leadingInteger.setFont( newfont );
        leadingIntegerWidth=leadingInteger.getLayoutBounds().getWidth();
        leadingIntegerHeight=leadingInteger.getLayoutBounds().getHeight();
        smallFontSize=bestFontSize(bigFontSize/2);
        newfont= new Font(numeratorText.getFont().getName(), smallFontSize );
        smallNumeratorText.setText(String.valueOf(Math.abs(r.getNumerator()-(r.toInt()*r.getDenominator()))));
        smallNumeratorText.setFont(newfont);
        smallNumeratorTextWidth=smallNumeratorText.getLayoutBounds().getWidth();
        smallNumeratorTextHeight=smallNumeratorText.getLayoutBounds().getHeight();
        smallDenominatorText.setText(String.valueOf(r.getDenominator()));
        smallDenominatorText.setFont(newfont);
        smallDenominatorTextWidth=smallDenominatorText.getLayoutBounds().getWidth();
        smallDenominatorTextHeight=smallDenominatorText.getLayoutBounds().getHeight();
        if(r.getDenominator()==1)numElements=1;
        else if (!r.isBetweenUnities()) numElements=3;
        else numElements=2;
        xText=( xCenter -  (numeratorTextWidth / 2) );
        yText=( yCenter - (numeratorTextHeight*0.2) );
        neg = new Text("-");
        neg.setFont(Font.font(bigFontSize));
        negWidth=neg.getLayoutBounds().getWidth()/2;
        if(r.isNegative())xText=xText-(negWidth);        
        numeratorText.setFill(Color.BLACK);   
        numeratorText.setX( xText );
        numeratorText.setY( yText ) ;
        if(r.isNegative())xText=xText+(negWidth);
        bigLineX=xText;
        xText=(xCenter -  (denominatorTextWidth / 2) );
        bigLineX=Math.min(xText,bigLineX);
        bigLineY=Math.max(numeratorTextWidth,denominatorTextWidth);
        if(r.isNegative())bigLineY=bigLineY-(negWidth);
        yText=( yCenter  +(denominatorTextHeight*0.75));
        denominatorText.setFill(Color.BLACK);   
        denominatorText.setX( xText );
        denominatorText.setY( yText ) ;
        bigLine.setStartX(bigLineX);
        bigLine.setStartY(yCenter);
        bigLine.setEndX(bigLineX+bigLineY);
        bigLine.setEndY(yCenter);
        bigLine.setStrokeWidth(Math.max(1, numeratorTextHeight*0.02));
        bigLine.setFill(null);
        bigLine.setStroke(Color.BLACK);
        bigLine.setStrokeType(StrokeType.OUTSIDE);
        if (numElements==1){
            xText=(xCenter- (leadingIntegerWidth / 2));
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            leadingInteger.setVisible(true);
            numeratorText.setVisible(false);
            denominatorText.setVisible(false);
            bigLine.setVisible(false);
        }
        else if (numElements==3){
            xText=xCenter- ((leadingIntegerWidth+smallDenominatorTextWidth) / 2) - negWidth;
            yText=( yCenter  +(leadingIntegerHeight*0.2));
            leadingInteger.setX( xText );
            leadingInteger.setY( yText ) ;
            xText=xText+leadingIntegerWidth+negWidth;
            smallLine.setStartX(xText);
            smallLine.setStartY(yCenter);
            smallLine.setEndX(xText+smallDenominatorTextWidth);
            smallLine.setEndY(yCenter);       
            smallLine.setStrokeWidth(smallNumeratorTextHeight*0.02);
            smallLine.setStrokeType(StrokeType.OUTSIDE);
            yText=( yCenter  +(smallDenominatorTextHeight*0.75));
            smallDenominatorText.setX( xText );
            smallDenominatorText.setY( yText ) ;     
            xText=xText+((smallDenominatorTextWidth-smallNumeratorTextWidth)/2);
            yText=( yCenter  -(smallDenominatorTextHeight*0.2));
            smallNumeratorText.setX( xText );
            smallNumeratorText.setY( yText ) ;
        }
        setExitedView();       
    }

    /**
     *
     */
    @Override
    public void setEnteredView(){
            if(leftSelected||rightSelected){
                leadingInteger.setFill(Color.WHITE);
                smallLine.setFill(Color.WHITE);
                smallLine.setStroke(Color.WHITE);
                smallDenominatorText.setFill(Color.WHITE);
                smallNumeratorText.setFill(Color.WHITE);
                leadingInteger.setStroke(Color.WHITE);
            }
            else{
                leadingInteger.setFill(Color.BLUE);
                smallLine.setFill(Color.BLUE);
                smallLine.setStroke(Color.BLUE);
                smallDenominatorText.setFill(Color.BLUE);
                smallNumeratorText.setFill(Color.BLUE);
            }
        if(numElements<=2) {return;}
        leadingInteger.setVisible(true);
        smallNumeratorText.setVisible(true);
        smallDenominatorText.setVisible(true);
        smallLine.setVisible(true);
        bigLine.setVisible(false);
        numeratorText.setVisible(false);
        denominatorText.setVisible(false);
    }
    /**
     *
     */
    @Override
    public void setExitedView(){
            leadingInteger.setFill(Color.BLACK);
            leadingInteger.setStroke(null);
            smallLine.setFill(Color.BLACK);
            smallLine.setStroke(Color.BLACK);
            smallDenominatorText.setFill(Color.BLACK);
            smallNumeratorText.setFill(Color.BLACK);
            leadingInteger.setVisible(false);
            numeratorText.setVisible(false);
            denominatorText.setVisible(false);
            bigLine.setVisible(false);
            smallNumeratorText.setVisible(false);
            smallDenominatorText.setVisible(false);
            smallLine.setVisible(false);
            if(leftSelected||rightSelected){
                leadingInteger.setFill(Color.WHITE);
                smallLine.setFill(Color.WHITE);
                smallLine.setStroke(Color.WHITE);
                smallDenominatorText.setFill(Color.WHITE);
                smallNumeratorText.setFill(Color.WHITE);
                leadingInteger.setStroke(Color.WHITE);
                bigLine.setFill(Color.WHITE);
                bigLine.setStroke(Color.WHITE);
                denominatorText.setFill(Color.WHITE);
                numeratorText.setFill(Color.WHITE);
            }
        if (numElements==1){
            leadingInteger.setVisible(true);
            return;
        }
        if (numElements==2){
            numeratorText.setVisible(true);
            denominatorText.setVisible(true);
            bigLine.setVisible(true);
            return;
        }
        bigLine.setVisible(true);
        numeratorText.setVisible(true);
        denominatorText.setVisible(true);

    }
    /**
     *
     */
    @Override
    public void setLeftSelected(){
        leftSelected=true;
        rightSelected=inputSelected=false;
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        rect.setEffect(null);          
        if(isGoodValue())rect.setFill(Color.BLUE);
        else rect.setFill(Color.RED);     
        rect.setArcHeight(0);
        rect.setArcWidth(0);
        switch (numElements){
            case 1 : {leadingInteger.setStroke(Color.WHITE);leadingInteger.setFill(Color.WHITE);}
            case 2 : {smallNumeratorText.setStroke(Color.WHITE);smallNumeratorText.setFill(Color.WHITE);
            smallDenominatorText.setStroke(Color.WHITE);smallDenominatorText.setFill(Color.WHITE);
            smallLine.setStroke(Color.WHITE);smallLine.setFill(Color.WHITE);
            }
            default : {numeratorText.setStroke(Color.WHITE);numeratorText.setFill(Color.WHITE);
            denominatorText.setStroke(Color.WHITE);denominatorText.setFill(Color.WHITE);
            bigLine.setStroke(Color.WHITE);bigLine.setFill(Color.WHITE);
            }
        }
    }

    /**
     *
     */
    @Override
    public void setRightSelected(){
        rightSelected=true;
        leftSelected=inputSelected=false;
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        if(isGoodValue())rect.setFill(Color.GREY);
        else rect.setFill(Color.RED);
        rect.setEffect(null);   
        rect.setStrokeWidth(1);                  
        rect.setArcHeight(0);
        rect.setArcWidth(0);
        switch (numElements){
            case 1 : {leadingInteger.setStroke(Color.WHITE);leadingInteger.setFill(Color.WHITE);}
            case 2 : {smallNumeratorText.setStroke(Color.WHITE);smallNumeratorText.setFill(Color.WHITE);
            smallDenominatorText.setStroke(Color.WHITE);smallDenominatorText.setFill(Color.WHITE);
            smallLine.setStroke(Color.WHITE);smallLine.setFill(Color.WHITE);
            }
            default : {numeratorText.setStroke(Color.WHITE);numeratorText.setFill(Color.WHITE);
            denominatorText.setStroke(Color.WHITE);denominatorText.setFill(Color.WHITE);
            bigLine.setStroke(Color.WHITE);bigLine.setFill(Color.WHITE);
            }
        }
    }    

    /**
     *
     */
    @Override
    public void setInputSelected(){
        switch (numElements){
            case 1 : leadingInteger.setStroke(null);
            case 2 : {smallNumeratorText.setStroke(null);
            smallDenominatorText.setStroke(null);
//            smallLine.setStroke(null);
            }
            default : {numeratorText.setStroke(null);
            denominatorText.setStroke(null);
//            bigLine.setStroke(null);
            }
        }
        inputSelected=true;
        rightSelected=leftSelected=false;
        rect.setArcHeight(height*0.3-2*strokeWidth);
        rect.setArcWidth(width*0.3-2*strokeWidth);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(strokeWidth);
        rect.setEffect(is);
        if(isGoodValue())rect.setFill(Color.LIGHTGREEN);
        else rect.setFill(Color.RED);
        switch (numElements){
            case 1 : leadingInteger.setEffect(ds);
            case 2 : {smallNumeratorText.setEffect(ds);
            smallDenominatorText.setEffect(ds);
            smallLine.setEffect(ds);
            }
            default : {numeratorText.setEffect(ds);
            denominatorText.setEffect(ds);
            bigLine.setEffect(ds);
            }
        }
    }   

    /**
     *
     */
    @Override
    public void setClearSelected(){
        inputSelected=rightSelected=leftSelected=false;
        rect.setEffect(null);             
        if(isGoodValue())rect.setFill(Color.TRANSPARENT);
        else rect.setFill(Color.RED);
        rect.setStrokeWidth(0);            
        rect.setArcHeight(0);
        rect.setArcWidth(0);
        leadingInteger.setEffect(null);
        smallNumeratorText.setEffect(null);
        smallDenominatorText.setEffect(null);
        smallLine.setEffect(null);
        numeratorText.setEffect(null);
        denominatorText.setEffect(null);
        bigLine.setEffect(null);
        switch (numElements){
            case 1 : {leadingInteger.setStroke(null);leadingInteger.setFill(Color.BLACK);}
            case 2 : {smallNumeratorText.setStroke(null);smallNumeratorText.setFill(Color.BLACK);
            smallDenominatorText.setStroke(null);smallDenominatorText.setFill(Color.BLACK);
            smallLine.setStroke(Color.BLACK);smallLine.setFill(null);
            }
            default : {numeratorText.setStroke(null);numeratorText.setFill(Color.BLACK);
            denominatorText.setStroke(null);denominatorText.setFill(Color.BLACK);
            bigLine.setStroke(Color.BLACK);bigLine.setFill(null);
            }
        }
    }
    public boolean isZero(){return r.isZero();}
    public void additiveInverse(){r.additiveInverse();setViewElement(r);}
    public void multiplicativeInverse(){r.multiplicativeInverse();setViewElement(r);}
    
    public void op1(Object so){
        swap.equals((LamppElementRational)((LamppViewRational)so).getViewElement());
        ((LamppViewRational)so).setViewElement(r);
        r.equals(swap);
        setViewElement(r);
    }
    public void op2(Object so){
        swap.equals((LamppElementRational)((LamppViewRational)so).getViewElement());
        r.product(swap);
        setViewElement(r);
    }
    public void op3(Object so, Object mo){
        swap.equals((LamppElementRational)((LamppViewRational)so).getViewElement());
        swap.product((LamppElementRational)((LamppViewRational)mo).getViewElement());
        r.sum(swap);
        setViewElement(r);
    }

    
 
    
}
