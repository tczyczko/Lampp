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
 **  @file   LamppViewGaussian.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Gaussian field extends rationals with i=sqrt(-1)
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
import javafx.scene.text.TextAlignment;

/**
 * display gaussian element (complex with rational coefficients)
 *
 *   Gaussian field extends rationals with i=sqrt(-1)
 *   forms are displayed dependent on size of numerators and denominators
 *   container is at least as wide as the largest of the numerators and denominators + 2*iSymbolWidth
 *   cases 3-6 are used if largest of numerators + largest of denominators <= MAXCHAR-2*iSymbolWidth
 *   ie. will not display any number with numerator or denominator greater in length than MAXCHAR-3
 *   or greater than CEILING or less than FLOOR
 *   in such cases: no change of number and goodValue is set to false - should be tested and reset by setting method
 */
public class LamppViewGaussian extends LamppViewElement {
    // FLOOR and CEILING defined in LamppGlobal, implemented in LamppShowElement
    static LamppElementGaussian swap = new LamppElementGaussian();
    private LamppElementGaussian g;
    static NumberFormat formatter = new DecimalFormat("0.###E00");

static boolean dbug=false;

    private double fontSize;

    private int caseNumber;
    private boolean largeNumbers;
    private int smallMaxChar;
    private boolean rint,iint,r0,i0,rneg,ineg;
    private long rnum,rden,inum,iden;
    private String rns,rds,ins,ids;

    private double lineY[]=new double [3];
    private double lineX[]=new double [2];
    
    private double textY[]=new double [15];
    private double textX[]=new double [4];
    
    
    private Line realLine, imaginaryLine;
    private double realLineWidth;
    private double imaginaryLineWidth;

    
    private Text realNumeratorText;
    private double realNumeratorTextWidth;
    private double textHeight;    
    
    private Text realDenominatorText;
    private double realDenominatorTextWidth;

    private Text imaginaryNumeratorText;
    private double imaginaryNumeratorTextWidth;
  
    private Text imaginaryDenominatorText;
    private double imaginaryDenominatorTextWidth;

    private Text imaginaryBinaryOp=new Text("+");
    private double iboWidth;
    
    private final Text realNeg = new Text("-");
    private double negWidth;
    
    private final Text imaginarySymbol=new Text("i");
    private double iWidth,iHeight;

    double doubleNumber;
    
    private boolean create=true;
    /*
    Gaussian field extends rationals with i=sqrt(-1)
    forms are displayed dependent on size of numerators and denominators
    container is at least as wide as the largest of the numerators and denominators + 2*iSymbolWidth
    cases 3-6 are used if largest of numerators + largest of denominators <= MAXCHAR-2*iSymbolWidth
    ie. will not display any number with numerator or denominator greater in length than MAXCHAR-3
    or greater than CEILING or less than FLOOR
    in such cases: no change of number and goodValue is set to false - should be tested and reset by setting method
    

    */    
    public LamppViewGaussian(){
    this(0,0,100,100,8,LamppElementGaussian.zero,1,1,12);
    }

    public LamppViewGaussian(double xx, double yy,
        double wi, double he, int maxC,
        LamppElementGaussian gg,int row, int column, double fntSiz)
    {
        super(xx,yy,wi,he,maxC,row,column);
        g = gg;
        matrixPosition = new int[]{row, column};
        maxChar=maxC;
        this.x=xx;
        this.y=yy;
        width=wi;
        height=he;    
        rect = new Rectangle(x,y,width,height);   
        fontSize=LamppViewElement.bestFontSize(fntSiz);   
        common();
        smallMaxChar=(int)((maxChar-2)/2);
        xCenter=x + (width/2);
        yCenter=y + (height/2);
        realNeg.setFont(Font.font(1.2*fontSize));
        negWidth=realNeg.getLayoutBounds().getWidth();
        realNeg.getLayoutBounds().getHeight();
        imaginaryBinaryOp.setFont(Font.font(1.2*fontSize));
        imaginaryBinaryOp.setTextAlignment(TextAlignment.CENTER);
        imaginarySymbol.setFont(Font.font(1.2*fontSize));
        iWidth=imaginarySymbol.getLayoutBounds().getWidth();
        iHeight=imaginarySymbol.getLayoutBounds().getHeight();
        both();
        this.getChildren().addAll(rect
                ,realNumeratorText,realDenominatorText,realLine,realNeg
                ,imaginaryNumeratorText,imaginaryDenominatorText,imaginaryLine,
                imaginaryBinaryOp,imaginarySymbol);        
        create=false;
    }
    // Methods

    // following methods should be overwritten for new classes
    

    
    private void both(){
        largeNumbers=false;
        rint=g.getReal().isInteger();
        iint=g.getImaginary().isInteger();
        r0=g.getReal().isZero();
        i0=g.getImaginary().isZero();
        rneg=g.getReal().isNegative();
        ineg=g.getImaginary().isNegative();
        if(ineg){imaginaryBinaryOp.setText("-");}
        else imaginaryBinaryOp.setText("+");
        iboWidth=imaginaryBinaryOp.getLayoutBounds().getWidth();
        imaginaryBinaryOp.getLayoutBounds().getHeight();
        realNeg.setVisible(rneg);
        rnum=Math.abs(g.getReal().getNumerator());
        rden=g.getReal().getDenominator();
        inum=Math.abs(g.getImaginary().getNumerator());
        iden=g.getImaginary().getDenominator();
        rns=String.valueOf(rnum);
        rds=String.valueOf(rden);
        ins=String.valueOf(inum);
        ids=String.valueOf(iden);
        if(inum==1&&iden==1){ins="";ids="";}
        largeNumbers=false;
        int inc=0;
        if(rns.length()>smallMaxChar||rds.length()>smallMaxChar||
                ins.length()>smallMaxChar||ids.length()>smallMaxChar) {largeNumbers=true;}
        if(largeNumbers)inc=4;
        setGoodValue(true);
        if(rnum>CEILING||rden>CEILING||inum>CEILING||iden>CEILING||
                rns.length()>maxChar||rds.length()>maxChar||
                ins.length()>maxChar||ids.length()>maxChar
                ){
            setGoodValue(false);
            rns=formatter.format(Math.abs(g.getReal().toDouble()));
            ins=formatter.format(Math.abs(g.getImaginary().toDouble()));
            caseNumber=9;
        }
        else if(r0&&i0){caseNumber=1;ineg=false;}
        else if( rint&&       !r0&& i0){caseNumber=1;ineg=false;}
        else if(        iint&& r0&&!i0){caseNumber=2;rneg=false;}
        else if(!rint       &&!r0&& i0){caseNumber=3;ineg=false;}
        else if(       !iint&& r0&&!i0){caseNumber=4;rneg=false;}
        else {
            if( rint&& iint){caseNumber=5+inc;}
            else if( rint){caseNumber=6+inc;}
            else if( iint){caseNumber=7+inc;}
            else {caseNumber=8+inc;}
        }
        if(caseNumber==1||caseNumber==3)imaginarySymbol.setVisible(false);
        else imaginarySymbol.setVisible(true);
        if(create){
            realNumeratorText=new Text(rns);
            realNumeratorText.setFont(Font.font(fontSize));
            textHeight=realNumeratorText.getLayoutBounds().getHeight();
        }
        else realNumeratorText.setText(rns);
        realNumeratorTextWidth=realNumeratorText.getLayoutBounds().getWidth();
        realNumeratorText.setVisible(true);
        
        if(create){
            realDenominatorText=new Text(rds);
            realDenominatorText.setFont(Font.font(fontSize));
        }
        else realDenominatorText.setText(rds);
        
        realDenominatorTextWidth=realDenominatorText.getLayoutBounds().getWidth();
        realDenominatorText.setVisible(true);
        if(create){
            imaginaryNumeratorText=new Text(ins);
            imaginaryNumeratorText.setFont(Font.font(fontSize));
        }
        else imaginaryNumeratorText.setText(ins);
        
        
        imaginaryNumeratorTextWidth=imaginaryNumeratorText.getLayoutBounds().getWidth();
        imaginaryNumeratorText.setVisible(true);
        
        if(create){
            imaginaryDenominatorText=new Text(ids);
            imaginaryDenominatorText.setFont(Font.font(fontSize));
        }
        else imaginaryDenominatorText.setText(ids);
        
        
        imaginaryDenominatorTextWidth=imaginaryDenominatorText.getLayoutBounds().getWidth();
        imaginaryDenominatorText.setVisible(true);
        realLineWidth=Math.max(realNumeratorTextWidth,realDenominatorTextWidth);
        imaginaryLineWidth=Math.max(imaginaryNumeratorTextWidth,imaginaryDenominatorTextWidth);
        if(caseNumber>4&&caseNumber<9){
            lineX[0]=xCenter-iboWidth-iWidth-(realLineWidth+imaginaryLineWidth)/2;
            lineX[1]=lineX[0]+2*iboWidth+realLineWidth;
            textX[0]=lineX[0]+(realLineWidth-realNumeratorTextWidth)/2;
            textX[1]=lineX[0]+(realLineWidth-realDenominatorTextWidth)/2;
            textX[2]=lineX[1]+(imaginaryLineWidth-imaginaryNumeratorTextWidth)/2;
            textX[3]=lineX[1]+(imaginaryLineWidth-imaginaryDenominatorTextWidth)/2;
        }
        else {
            textX[0]=xCenter-realNumeratorTextWidth/2;
            textX[1]=xCenter-realDenominatorTextWidth/2;
            textX[2]=xCenter-imaginaryNumeratorTextWidth/2;
            textX[3]=xCenter-imaginaryDenominatorTextWidth/2;
            lineX[0]=xCenter-realLineWidth/2;
            lineX[1]=xCenter-imaginaryLineWidth/2;
        }
        realNumeratorText.setX(textX[0]);
        realDenominatorText.setX(textX[1]);
        imaginaryNumeratorText.setX(textX[2]);
        imaginaryDenominatorText.setX(textX[3]);
        textY[8]=height/4;
        lineY[0]=yCenter;
        lineY[1]=yCenter-textY[8];
        lineY[2]=yCenter+textY[8];
        textY[7]=textHeight/2;
        textY[0]=yCenter;
        textY[1]=yCenter-textY[7];
        textY[2]=yCenter+textHeight-textY[7];
        if(caseNumber<9){
            lineY[0]=yCenter-textHeight/4;
            lineY[1]=lineY[0];
        }
        else if(caseNumber==9){
            lineY[0]=yCenter-height/6+textHeight/2;
            lineY[1]=yCenter+height/6-textHeight/4;
        }
        else if(caseNumber==10){
            lineY[0]=yCenter-height/8;
            lineY[1]=yCenter+height/8;
        }
        else if(caseNumber==11){
            lineY[0]=yCenter-height/8;
            lineY[1]=yCenter+height/6;
        }
        else if(caseNumber==12){
            lineY[0]=yCenter-height/5;
            lineY[1]=yCenter+height/5;
        }
        textY[3]=lineY[0];
        textY[4]=lineY[1]+textHeight/4;      
        textY[5]=lineY[0];
        textY[6]=lineY[1]-textHeight/4;
        textY[7]=lineY[1]+3*textHeight/4;
        textY[8]=lineY[0]-textHeight/4;
        textY[9]=lineY[0]+3*textHeight/4;
        textY[10]=lineY[1]+textHeight/4;
        textY[11]=lineY[0]-textHeight/4;
        textY[12]=lineY[0]+3*textHeight/4;
        textY[13]=lineY[1]-textHeight/4;
        textY[14]=lineY[1]+3*textHeight/4;
        imaginaryBinaryOp.setY(lineY[0]+textHeight/4);
        realNeg.setY(lineY[0]+textHeight/4);
        imaginaryBinaryOp.setX(lineX[1]-3*iboWidth/2);
        realNeg.setX(lineX[0]-3*negWidth/2);
        imaginarySymbol.setY(lineY[0]+textHeight/4);
        if(inum==1&&caseNumber==2){imaginarySymbol.setX(textX[0]);imaginaryBinaryOp.setX(lineX[0]-3*negWidth/2);}
        else if(caseNumber==10)imaginarySymbol.setX(lineX[1]+imaginaryLineWidth+iWidth);
        else if(caseNumber==12)imaginarySymbol.setX(lineX[1]+imaginaryLineWidth+iWidth);
        else imaginarySymbol.setX(lineX[1]+imaginaryLineWidth+iWidth/2);
        if(caseNumber==4){
            imaginarySymbol.setX(lineX[1]+imaginaryLineWidth+iWidth);
        }
        else if(caseNumber>4&&caseNumber<9){imaginarySymbol.setX(lineX[1]+imaginaryLineWidth+iWidth);}
        else if(caseNumber==9){imaginarySymbol.setY(textY[4]);realNeg.setY(textY[3]);imaginaryBinaryOp.setY(textY[4]);}
        else if(caseNumber==10){imaginarySymbol.setY(lineY[1]+iHeight/4);realNeg.setY(textY[5]);imaginaryBinaryOp.setY(lineY[1]+textHeight/4);}
        else if(caseNumber==11){imaginarySymbol.setY(lineY[1]+iHeight/4);realNeg.setY(lineY[0]+textHeight/4);imaginaryBinaryOp.setY(lineY[1]+textHeight/4);}
        else if(caseNumber==12){imaginarySymbol.setY(lineY[1]+iHeight/4);realNeg.setY(lineY[0]+textHeight/4);imaginaryBinaryOp.setY(lineY[1]+textHeight/4);}
        realNumeratorText.setY(textY[0]);
        imaginaryNumeratorText.setY(textY[0]);
        if(create){
            realLine=new Line(lineX[0],lineY[0],lineX[0]+realLineWidth,lineY[0]);
            imaginaryLine=new Line(lineX[1],lineY[1],lineX[1]+imaginaryLineWidth,lineY[1]);
        }
        else{
            realLine.setStartX(lineX[0]);
            realLine.setStartY(lineY[0]);
            realLine.setEndX(lineX[0]+realLineWidth);
            realLine.setEndY(lineY[0]);
            imaginaryLine.setStartX(lineX[1]);
            imaginaryLine.setStartY(lineY[1]);
            imaginaryLine.setEndX(lineX[1]+imaginaryLineWidth);
            imaginaryLine.setEndY(lineY[1]);
        }
        realLine.setVisible(false);
        imaginaryLine.setVisible(false);
        realNumeratorText.setVisible(true);
        realDenominatorText.setVisible(false);
        imaginaryNumeratorText.setVisible(true);
        imaginaryDenominatorText.setVisible(false);
        if(caseNumber==2||caseNumber==4){
            realNumeratorText.setVisible(false);
            if(ineg)imaginaryBinaryOp.setVisible(true);
            else imaginaryBinaryOp.setVisible(false);
        }
        else if(caseNumber==1||caseNumber==3){
            imaginaryNumeratorText.setVisible(false);
            imaginaryBinaryOp.setVisible(false);
        }
        if(caseNumber==8||caseNumber==12){
            realDenominatorText.setVisible(true);
            imaginaryDenominatorText.setVisible(true);
            realLine.setVisible(true);
            imaginaryLine.setVisible(true);
        }
        else if(caseNumber==3||caseNumber==7||caseNumber==11){
            realLine.setVisible(true);
            realDenominatorText.setVisible(true);
        }
        else if(caseNumber==4||caseNumber==6||caseNumber==10){
            imaginaryDenominatorText.setVisible(true);
            imaginaryLine.setVisible(true);
        }
        switch (caseNumber){
            case 1:{
                textX[0]=textY[0];
                break;
            }
            case 2:{
                textX[1]=textY[0];
                break;
            }
            case 3:{
                textX[0]=textY[1];
                textX[2]=textY[2];
                break;
            }
            case 4:{
                textX[1]=textY[1];
                textX[3]=textY[2];
                break;
            }
            case 5:{
                textX[0]=textY[0];
                textX[1]=textY[0];
                break;
            }
            case 6:{
                textX[0]=textY[0];
                textX[1]=textY[1];
                textX[3]=textY[2];
                break;
            }
            case 7:{
                textX[0]=textY[1];
                textX[1]=textY[0];
                textX[2]=textY[2];
                break;
            }
            case 8:{
                textX[0]=textY[1];
                textX[1]=textY[1];
                textX[2]=textY[2];
                textX[3]=textY[2];
                break;
            }
            case 9:{
                textX[0]=textY[3];
                textX[1]=textY[4];
                break;
            }
            case 10:{
                textX[0]=textY[5];
                textX[1]=textY[6];
                textX[3]=textY[7];
                break;
            }
            case 11:{
                textX[0]=textY[8];
                textX[1]=textY[10];
                textX[2]=textY[9];
                break;
            }
            case 12:{
                textX[0]=textY[11];
                textX[1]=textY[13];
                textX[2]=textY[12];
                textX[3]=textY[14];
                break;
            }
        }
        realLine.setStrokeWidth(fontSize*0.02);
        realLine.setFill(null);
        realLine.setStroke(Color.BLACK);
        realLine.setStrokeType(StrokeType.OUTSIDE);
        imaginaryLine.setStrokeWidth(fontSize*0.02);
        imaginaryLine.setFill(null);
        imaginaryLine.setStroke(Color.BLACK);
        imaginaryLine.setStrokeType(StrokeType.OUTSIDE);
        realNumeratorText.setY(textX[0]);
        realDenominatorText.setY(textX[2]);
        imaginaryNumeratorText.setY(textX[1]);
        imaginaryDenominatorText.setY(textX[3]);
    }
    public LamppElementGaussian getViewElement(){
        return g;
    }
    public void setViewElement(Object element){
       LamppElementGaussian test=new LamppElementGaussian((LamppElementGaussian)element);
    //test for valid object viewing parameters    
        if(test.getReal().getNumerator()>CEILING||test.getReal().getNumerator()<FLOOR||
                test.getReal().getDenominator()>CEILING||String.valueOf(test.getReal().getNumerator()).length()>maxChar||
                String.valueOf(test.getReal().getDenominator()).length()>maxChar) {setGoodValue(false);return;}
        if(test.getImaginary().getNumerator()>CEILING||test.getImaginary().getNumerator()<FLOOR||
                test.getImaginary().getDenominator()>CEILING||String.valueOf(test.getImaginary().getNumerator()).length()>maxChar||
                String.valueOf(test.getImaginary().getDenominator()).length()>maxChar) {setGoodValue(false);return;}
        g=test;
        setGoodValue(true);
        both();
        setExitedView();
    }
    public void setFontSize(double fntSiz){
        if(fntSiz>fontSize){
            
        }
        else{fontSize=fntSiz;}
        both();
    }
    public boolean isZero(){return g.isZero();}
    public void additiveInverse(){g.additiveInverse();setViewElement(g);}
    public void multiplicativeInverse(){g.multiplicativeInverse();setViewElement(g);}
    public void conjugate(){g.conjugate();setViewElement(g);}
    public void op1(Object so){
        swap.equals((LamppElementGaussian)((LamppViewGaussian)so).getViewElement());
        ((LamppViewGaussian)so).setViewElement(g);
        g.equals(swap);
        setViewElement(g);
    }
    public void op2(Object so){
        swap.equals((LamppElementGaussian)((LamppViewGaussian)so).getViewElement());
        g.product(swap);
        setViewElement(g);
    }
    public void op3(Object so, Object mo){
        swap.equals((LamppElementGaussian)((LamppViewGaussian)so).getViewElement());
        swap.product((LamppElementGaussian)((LamppViewGaussian)mo).getViewElement());
        g.sum(swap);
        setViewElement(g);
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
        realNumeratorText.setStroke(Color.WHITE);
        realDenominatorText.setStroke(Color.WHITE);
        imaginaryNumeratorText.setStroke(Color.WHITE);
        imaginaryDenominatorText.setStroke(Color.WHITE);
        imaginaryBinaryOp.setStroke(Color.WHITE);
        realNeg.setStroke(Color.WHITE);
        realLine.setStroke(Color.WHITE);
        imaginaryLine.setStroke(Color.WHITE);
        imaginarySymbol.setStroke(Color.WHITE);
        realNumeratorText.setFill(Color.WHITE);
        realDenominatorText.setFill(Color.WHITE);
        imaginaryNumeratorText.setFill(Color.WHITE);
        imaginaryDenominatorText.setFill(Color.WHITE);
        realLine.setFill(Color.WHITE);
        imaginaryLine.setFill(Color.WHITE);
        imaginaryBinaryOp.setFill(Color.WHITE);
        realNeg.setFill(Color.WHITE);
        imaginarySymbol.setFill(Color.WHITE);
        realLine.setStrokeWidth(1);
        imaginaryLine.setStrokeWidth(1);
    }
    /**
     *
     */
    @Override
    public void setRightSelected(){
        rightSelected=true;
        leftSelected=inputSelected=false;
        if(isGoodValue())rect.setFill(Color.GREY);
        else rect.setFill(Color.RED);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);           
        rect.setEffect(null);   
        rect.setStrokeWidth(1);                  
        rect.setArcHeight(0);
        rect.setArcWidth(0);
        realNumeratorText.setStroke(Color.WHITE);
        realDenominatorText.setStroke(Color.WHITE);
        imaginaryNumeratorText.setStroke(Color.WHITE);
        imaginaryDenominatorText.setStroke(Color.WHITE);
        imaginaryBinaryOp.setStroke(Color.WHITE);
        realNeg.setStroke(Color.WHITE);
        realLine.setStroke(Color.WHITE);
        imaginaryLine.setStroke(Color.WHITE);
        imaginarySymbol.setStroke(Color.WHITE);
        realNumeratorText.setFill(Color.WHITE);
        realDenominatorText.setFill(Color.WHITE);
        imaginaryNumeratorText.setFill(Color.WHITE);
        imaginaryDenominatorText.setFill(Color.WHITE);
        realLine.setFill(Color.WHITE);
        imaginaryLine.setFill(Color.WHITE);
        imaginaryBinaryOp.setFill(Color.WHITE);
        realNeg.setFill(Color.WHITE);
        imaginarySymbol.setFill(Color.WHITE);
        realLine.setStrokeWidth(1);
        imaginaryLine.setStrokeWidth(1);
    }    

    /**
     *
     */
    @Override
    public void setInputSelected(){
        inputSelected=true;
        rightSelected=leftSelected=false;
        realNumeratorText.setStroke(null);
        realDenominatorText.setStroke(null);
        imaginaryNumeratorText.setStroke(null);
        imaginaryDenominatorText.setStroke(null);
        imaginaryBinaryOp.setStroke(null);
        realNeg.setStroke(null);
        imaginarySymbol.setStroke(null);
        rect.setArcHeight(height*0.3-2*strokeWidth);
        rect.setArcWidth(width*0.3-2*strokeWidth);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(strokeWidth);
        rect.setEffect(is);
        if(isGoodValue())rect.setFill(Color.LIGHTGREEN);
        else rect.setFill(Color.RED);
        realNumeratorText.setEffect(ds);
        realDenominatorText.setEffect(ds);
        imaginaryNumeratorText.setEffect(ds);
        imaginaryDenominatorText.setEffect(ds);
        realLine.setEffect(ds);
        imaginaryLine.setEffect(ds);
        imaginaryBinaryOp.setEffect(ds);
        realNeg.setEffect(ds);
        imaginarySymbol.setEffect(ds);
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
        
        realNumeratorText.setStroke(null);
        realDenominatorText.setStroke(null);
        imaginaryNumeratorText.setStroke(null);
        imaginaryDenominatorText.setStroke(null);
        imaginaryBinaryOp.setStroke(null);
        realNeg.setStroke(null);
        realLine.setStroke(Color.BLACK);
        imaginaryLine.setStroke(Color.BLACK);
        imaginarySymbol.setStroke(null);
        
        realNumeratorText.setFill(Color.BLACK);
        realDenominatorText.setFill(Color.BLACK);
        imaginaryNumeratorText.setFill(Color.BLACK);
        imaginaryDenominatorText.setFill(Color.BLACK);
        realLine.setFill(null);
        imaginaryLine.setFill(null);
        imaginaryBinaryOp.setFill(Color.BLACK);
        realNeg.setFill(Color.BLACK);
        imaginarySymbol.setFill(Color.BLACK);
        
        realLine.setStrokeWidth(fontSize*0.02);
        imaginaryLine.setStrokeWidth(fontSize*0.02);
        
        realNumeratorText.setEffect(null);
        realDenominatorText.setEffect(null);
        imaginaryNumeratorText.setEffect(null);
        imaginaryDenominatorText.setEffect(null);
        realLine.setEffect(null);
        imaginaryLine.setEffect(null);
        imaginaryBinaryOp.setEffect(null);
        realNeg.setEffect(null);
        imaginarySymbol.setEffect(null);
    }
    /**
     *
     */
    @Override
    public void setExitedView(){
        realNumeratorText.setFill(Color.BLACK);
        realNumeratorText.setStroke(null);
        realLine.setFill(Color.BLACK);
        realLine.setStroke(Color.BLACK);
        realDenominatorText.setFill(Color.BLACK);
        realDenominatorText.setStroke(null);
        imaginaryNumeratorText.setFill(Color.BLACK);
        imaginaryNumeratorText.setStroke(null);
        imaginaryLine.setFill(Color.BLACK);
        imaginaryLine.setStroke(Color.BLACK);
        imaginaryDenominatorText.setFill(Color.BLACK);
        imaginaryDenominatorText.setStroke(null);
        realNeg.setFill(Color.BLACK);
        imaginarySymbol.setFill(Color.BLACK);
        realNeg.setStroke(null);
        imaginarySymbol.setStroke(null);
        realLine.setStrokeWidth(fontSize*0.02);
        imaginaryLine.setStrokeWidth(fontSize*0.02);


        if(leftSelected||rightSelected){
            realNumeratorText.setStroke(Color.WHITE);
            realDenominatorText.setStroke(Color.WHITE);
            imaginaryNumeratorText.setStroke(Color.WHITE);
            imaginaryDenominatorText.setStroke(Color.WHITE);
            imaginaryBinaryOp.setStroke(Color.WHITE);
            realNeg.setStroke(Color.WHITE);
            realLine.setStroke(Color.WHITE);
            imaginaryLine.setStroke(Color.WHITE);
            imaginarySymbol.setStroke(Color.WHITE);
            realNumeratorText.setFill(Color.WHITE);
            realDenominatorText.setFill(Color.WHITE);
            imaginaryNumeratorText.setFill(Color.WHITE);
            imaginaryDenominatorText.setFill(Color.WHITE);
            realLine.setFill(Color.WHITE);
            imaginaryLine.setFill(Color.WHITE);
            imaginaryBinaryOp.setFill(Color.WHITE);
            realNeg.setFill(Color.WHITE);
            imaginarySymbol.setFill(Color.WHITE);
            realLine.setStrokeWidth(1);
            imaginaryLine.setStrokeWidth(1);
        }
        if(caseNumber==1||caseNumber==3)imaginarySymbol.setVisible(false);
        else imaginarySymbol.setVisible(true);
        realNeg.setVisible(rneg);
        realNumeratorText.setVisible(true);
        realDenominatorText.setVisible(true);
        imaginaryNumeratorText.setVisible(true);
        imaginaryDenominatorText.setVisible(true);
        imaginaryBinaryOp.setVisible(true);
        realLine.setVisible(false);
        imaginaryLine.setVisible(false);
        imaginaryBinaryOp.setVisible(true);
        if(caseNumber==2||caseNumber==4){
            realNumeratorText.setVisible(false);
            realDenominatorText.setVisible(false);
            if(!ineg)imaginaryBinaryOp.setVisible(false);
            if(caseNumber==2)imaginaryDenominatorText.setVisible(false);
        }
        else if(caseNumber==1||caseNumber==3){
            imaginaryNumeratorText.setVisible(false);
            imaginaryDenominatorText.setVisible(false);
            imaginaryBinaryOp.setVisible(false);
            if(caseNumber==1)realDenominatorText.setVisible(false);
        }
        else if(caseNumber==9||caseNumber==5){
            realDenominatorText.setVisible(false);
            imaginaryDenominatorText.setVisible(false);
        }
        if(caseNumber==8||caseNumber==12){
            realLine.setVisible(true);
            imaginaryLine.setVisible(true);
        }
        else if(caseNumber==3||caseNumber==7||caseNumber==11){
            realLine.setVisible(true);
            imaginaryDenominatorText.setVisible(false);
        }
        else if(caseNumber==4||caseNumber==6||caseNumber==10){
            realDenominatorText.setVisible(false);
            imaginaryLine.setVisible(true);
        }

    }
    
}
