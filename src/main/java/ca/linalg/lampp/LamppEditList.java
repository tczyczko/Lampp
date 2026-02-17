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
 **  @file   LamppEditList.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Convex polygon used in graphing a matrix of rational elements representing points in 3D
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



import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 * an editable list for user selections
 */
public class LamppEditList extends Group 

    {
    
    private final double height;
    private final double width;
    double x,y;
    double tx,ty;
    
    char sb[] = new char[30];
    String list;
    String tempList;
    double charTabs[]= new double [30];
    double tabRegions[]= new double [30];
    int numChar;
    int maxChar=8;
    double caretPosition;
    boolean caretSet=false;
    boolean paintBox=false;
    boolean rangeSelected=false;
    int fromIndex,toIndex;
    char keyChar;
    double xStart;
//Constructors
    public LamppEditList(double ex,double ey, 
            double wi, double hi, String list, double efontSize, int maxC){
        x=ex;
        y=ey;
        width=wi;
        height=hi;
        if(0<maxC)maxChar=maxC;
        this.list=list;
        if(list==null){
                numChar=0;
                tempList=null;
        }
        else{
            numChar = list.length();
            tempList=list;
            for(int i=0; i<numChar;i++){
               sb[i]=list.charAt(i);
            }
        }
        new Rectangle(0,0,width,height);
        setCaretTabs();
    }


	public void setEditList(){
            if(numChar!=0)
                    numChar = Math.min(list.length(),maxChar);
            setList(list);
            for(int i=0; i<numChar;i++)
                    sb[i]=list.charAt(i);
            setCaretTabs();
	}
	public void setEditList(String s){
            setList(s);
            tempList=s;
            numChar = Math.min(list.length(),maxChar);
            for(int i=0; i<numChar;i++)
                    sb[i]=list.charAt(i);
            setCaretTabs();
	}
	void setCaretTabs(){
		charTabs[0]=xStart;
		for (int i=1;i<=numChar;i++)
//			charTabs[i]=charTabs[i-1]+
//				fm.charWidth(listString.charAt(i-1));
		if(charTabs[numChar]>=width)charTabs[numChar]=width-1;
		for(int i=0;i<numChar;i++)
//			tabRegions[i]=charTabs[i]+
//				(fm.charWidth(listString.charAt(i))/2);
		tabRegions[numChar]=width;
	}
        
        /*
        @Override
        public void mouseClicked(MouseEvent me){
        }
        @Override
        public void mouseEntered(MouseEvent me){
        setCursor(Cursor.getPredefinedCursor(
        Cursor.TEXT_CURSOR));
        }
        @Override
        public void mouseExited(MouseEvent me){
        setCursor(Cursor.getDefaultCursor());
        }
        @Override
        public void mousePressed(MouseEvent me){
        boolean done;
        int x;
        x=me.getX();
        done=false;
        rangeSelected=false;
        for(caretPosition=0;!done;caretPosition++){
        if(x<=tabRegions[caretPosition])done=true;
        }
        if(!caretSet){
        requestFocus();
        }
        caretSet=true;
        caretPosition--;
        repaint();
        }
        @Override
        public void mouseReleased(MouseEvent me){
        }
        @Override
        public void mouseDragged(MouseEvent me){
        if(numChar==0)return;
        if(!caretSet)return;
        if(!rangeSelected){
        fromIndex=caretPosition;
        toIndex=caretPosition;
        rangeSelected=true;
        }
        else{
        int x=me.getX();
        boolean done=false;
        if(x<0||x>width)return;
        for(toIndex=0;!done;toIndex++){
        if(x<=tabRegions[toIndex])done=true;
        }
        toIndex--;
        }
        repaint();
        
        }
        @Override
        public void mouseMoved(MouseEvent me){
        }*/        

        /*
        public void keyPressed(KeyEvent ke){
        if(!caretSet)return;
        keyChar=ke.getKeyChar();
        if(isValid(keyChar)){
        if(rangeSelected)deleteRange();
        if(caretPosition==maxChar)return;
        if(numChar<maxChar)numChar++;
        for(int i=numChar;i>caretPosition;i--)sb[i]=sb[i-1];
        sb[caretPosition]=filterChar(keyChar);
        caretPosition++;
        listString=new String(sb,0,numChar);
        setEditList();
        paintBox=true;
        repaint();
        return;
        }
        int key=ke.getKeyCode();
        switch (key){
        case KeyEvent.VK_ENTER:
        if(rangeSelected)deleteRange();
        if(listString==null)break;
        if(!isValid(listString))listString=
        tempList;
        caretSet=false;
        paintBox=false;
        transferFocus();
        setEditList();
        if(tempList!=null)tempList=new String(listString);
        break;
        case KeyEvent.VK_BACK_SPACE:
        if(rangeSelected){
        deleteRange();
        return;
        }
        if(caretPosition==0)return;
        caretPosition--;
        for(int i=caretPosition;i<numChar-1;i++)sb[i]=sb[i+1];
        numChar--;
        if(numChar==0)
        listString=null;
        else
        listString=new String(sb,0,numChar);
        setEditList();
        paintBox=true;
        break;
        case KeyEvent.VK_LEFT:
        if(rangeSelected){
        deleteRange();
        return;
        }
        if(caretPosition==0)return;
        caretPosition--;
        break;
        case KeyEvent.VK_RIGHT:
        if(rangeSelected){
        deleteRange();
        return;
        }
        if(caretPosition==numChar)return;
        caretPosition++;
        break;
        case KeyEvent.VK_DELETE:
        if(rangeSelected){
        deleteRange();
        return;
        }
        if(caretPosition==numChar)return;
        numChar--;
        if(numChar==0)
        listString=null;
        else{
        for(int i=caretPosition;i<numChar;i++)sb[i]=sb[i+1];
        listString=new String(sb,0,numChar);
        }
        setEditList();
        paintBox=true;
        break;
        default:
        return;
        }
        repaint();
        }
        @Override
        public void keyReleased(KeyEvent ke){
        }
        @Override
        public void keyTyped(KeyEvent ke){
        }*/	
        
        boolean isValid(char c){
// check input character
		if(c > 47 && c < 58)return true;
		else if(c >= 'a' && c <= 'z')return true;
		else if(c >= 'A' && c <= 'Z')return true;
		return false;
	}
	boolean isValid(String s){
// check output string
		return true;
	}

	public boolean isValid(){
		return !caretSet;
	}
        
        /*        @Override
        public void focusGained(FocusEvent fe){
        }
        @Override
        public void focusLost(FocusEvent fe){
        if(caretSet){
        caretSet=false;
        rangeSelected=false;
        if(isOkay())paintBox=false;
        repaint();
        }
        }
        @Override
        public void paint(Graphics g){
        update(g);
        }
        @Override
        public void update(Graphics g){
        if(gb==null){
        buffer=createImage(width,height);
        gb=buffer.getGraphics();
        gb.setFont(f);
        }
        gb.setColor(Color.white);
        gb.fillRect(0,0,width,height);
        gb.setColor(Color.black);
        if(caretSet||paintBox){
        gb.setColor(Color.lightGray);
        gb.fillRect(0,0,width,height);
        gb.setColor(Color.black);
        }
        if(listString!=null){
        if(rangeSelected){
        gb.setColor(Color.yellow);
        if(fromIndex<toIndex){
        gb.fillRect(charTabs[fromIndex]
        ,0,charTabs[toIndex]-
        charTabs[fromIndex],height);
        }
        else if(toIndex<fromIndex){
        gb.fillRect(charTabs[toIndex]
        ,0,charTabs[fromIndex]-
        charTabs[toIndex],height);
        }
        gb.setColor(Color.black);
        }
        gb.drawString(listString,
        xStart,(height-fm.getDescent()));
        }
        else{
        gb.drawRect(0,0,width-1,height-1);
        }
        if(caretSet){
        gb.setColor(Color.blue);
        gb.drawLine(charTabs[caretPosition],0,
        charTabs[caretPosition],height);
        gb.setColor(Color.black);
        }
        if(buffer!=null)g.drawImage(buffer,0,0,null);
        }*/
        
        
        
	void deleteRange(){
		if(toIndex<fromIndex){
			int temp=toIndex;
			toIndex=fromIndex;
			fromIndex=temp;
			}
		if(numChar+fromIndex-toIndex==0){
			numChar=0;
			list=null;
			caretPosition=0;
			}
		else if(toIndex!=fromIndex){
			numChar=numChar+fromIndex-toIndex;
			if(fromIndex!=numChar){
				for(int j=0;j<numChar-fromIndex;j++){
					sb[fromIndex+j]=sb[toIndex+j];
					}
				}
			list=new String(sb,0,numChar);
			caretPosition=fromIndex;
			}
		setEditList();
		paintBox=true;
		rangeSelected=false;
//		repaint();
	}
	public boolean isOkay(){
//		if(rangeSelected)deleteRange();
		rangeSelected=false;
		if(list==null)return false;
		if(!isValid(list)){
				list=tempList;
				setEditList();
				return false;
				}
		caretSet=false;
		paintBox=false;
//		transferFocus();
		setEditList();
		if(tempList!=null)tempList=new String(list);
		return true;
	}

    void setList(String s){
	list=s;
	if(list==null)
		xStart=width/2;
//	else
//		xStart=((width-fm.stringWidth(list))/2);
    }        
}
