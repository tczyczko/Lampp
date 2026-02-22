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
 **  @file   Lampp.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Linear Algebra Matrix aPP, for learning basic matrix operations
 **  @copyright 2026 Thomas Czyczko
 **  This source code is part of the Lampp project. It includes a Linear Algebra Matrix aPP 
 **  program, to accompany the book Linear Algebra for Mouse, Pen and Pad.
 **  Copyright (C) 2003, 2026 Thomas Czyczko
 **  <https://github.com/ca/linalg/lampp> All Rights Reserved.
 **
 **  This program, Lampp, is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the 
 **  Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 **  
 **  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 **  See the GNU Affero General Public License for more details.
 **  
 **  You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>. 
 **
 *************************************************************************/

package ca.linalg.lampp;

/*
* follow directory structure for IntelliJ IDEa IdeaProjects
* to edit source files, use the files in: C:\Users\tom\Lampp\src\main\java\ca\linalg\lampp

* to build and execute from the command line interface (CLI) using Java SDK 21 and Javafx 20:
* from Lampp directory (eg. in Windows "cd C:\Users\tom\Lampp"):
javac -d target\classes --module-path "c:\program files\java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.graphics,javafx.web src\main\java\ca\linalg\lampp\*.java
java -cp C:\Users\tom\Lampp\target\classes\ca\linalg\lampp --module-path "C:\Program Files\Java\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.graphics,javafx.web  ca.linalg.lampp.Lampp
*
* or
javac -d target\classes --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.graphics,javafx.web src\main\java\ca\lampp\*.java
java -cp C:\Users\tom\Lampp\target\classes\ca\linalg\lampp --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.graphics,javafx.web ca.linalg.lampp.Lampp
*
* NOTE: the file that lists the dependency order necessary to initially compile Lampp: javac_compile_order.txt
*
* subdirectory that contains the java source files: src\main\java\ca\lampp
* subdirectory that will contain the class files: target\classes\ca\lampp 
* subdirectory that contains the html help file(s): src\main\resources\html
*                   the html subdirectory must contain at least one file: Linalg.html
* subdirectory used for data storage by the Lampp app: matrices
* the following 10 files should be available in the Lampp directory and the subdirectory: src\main\resources
*     hide-collapse.png
*     hide-collapse-50.png
*     hide-collapse-r.png
*     hide-collapse-r-50.png
*     icon.png
*     lamp_icon.png
*     show-expand.png
*     show-expand-50.png
*     show-expand-r.png
*     show-expand-r-50.png
*     
* NOTE: the file that contains the user level information is created by Lampp:  lamppdata.txt
*  
*/

/*
 * LAMP came about because I wanted to learn Java and I thought it would be
 * nice to have a chunk of code to help with marking linear algebra tests
 * and assignments. There is lots of code that I would do differently now.
 * There is also lots of code that I would have done differently then except
 * I am not too concerned with performance issues. LAMPP was started when Java
 * was 1.0 but was mostly created on version 1.3 under both Windows 98 (blah)
 * and Debian Linux (yeah). It was written during my copious spare time (snort).
 * It has been tested using Windows 95,98,NT,XT with Sun Java runtime 
 * and Debian Linux using the Blackdown port of Java.
 * The graphing capability was done in such a bizarre fashion mainly for fun.
 * Other things were done in a bizarre fashion for fun.
 * Some bizarre things were done for what were, I am sure, only bizarre reasons.
 * The documentation is poor and consists mostly of clues on how to add
 * new fields.
 * A new version is planned which will be written better and which will be
 * tightly coupled with LaTeX (all elements of a field will be represented
 * by LaTeX strings). A standalone (non-applet) version exists which
 * has the capability of saving and opening matrices stored as LaTeX
 * code which can be easily included in a LaTeX document. (-nah.)
 * As I said, I am not worried about performance as much
 * as appearance and generality. 
 * It was a LOT of work and I hope it gives budding mathematicians insight
 * and pleasure. Hopefully they will all go on to enlighten us.
 *                             Tom Czyczko, 2003
 *
 * Under JDK 8 and Gluon, got it to work on Android, but it no longer does because of Webkit
 * Am rewriting in Flutter and Dart for Lampp to be more cross-platform and to improve the interface
 *                             Thomas Czyczko, 2026
 *
 *   Any system solved is assured to be all wrong.
 *   Fields contain scalars that really don't belong.
 *   The graphs are cubist paintings of my mother's cat,
 *   Her motorbike and helmet, and poor ones at that.
 *   Use Lampp, if you insist, and I'll bet a zero buck,
 *   If your answer is correct, it was only monkey luck.
 */


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate; // import the LocalDate class;
import java.time.LocalTime; // import the LocalTime class
import java.util.Optional;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
//import javafx.scene.Parent; //Only needed for changing scenes using fxml?
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Scanner; // Import the Scanner class to read text files


/**
* This class creates an application to aid in learning linear algebra.
* 
* Lampp, the Linear Algebra Matrix aPP (named to coincide with the text: Linear Algebra
* for Mouse, Pen and Pad) is an application program written in JavaFX. It is
* designed to be cross-platform and work on many devices.
* Lampp also provides a convenient portal, using Webkit, to the textbook’s
* website where more dynamic demonstrations and further graphic examples of
* key concepts are to be made available.
* Lampp was written in such a way that it
* is difficult to use correctly if one does not understand what fields and row
* operations are. The user begins by first choosing a predefined field. A matrix of
* up to 10 by 10 scalars may then be created and the user then selects which row
* (or column) operations they wish to perform upon the matrix. Multiplication and
* addition of field scalars are then facilitated. Predefined fields include rational
* numbers, complex with rational coefficients (called Gaussian) and integers modulo
* a selected prime. Matrix multiplication and addition are accomplished by showing
* every intermediate step so that a student (or instructor) may check a problem
* worked out by hand.
* As the student works their way through the text, keys become available
* to unlock further capabilities of Lampp. For example, when the student is at
* the point where there is a need to easily create the transform of a matrix, a
* hidden feature in Lampp is discussed in the text showing how to activate a feature
* so that simple operations on matrices may be
* performed without having to accomplish every intermediate step.
* Lampp was designed strictly as a teaching aid for beginners and should not be used for serious computation.
* Lampp comes with no guarantee to accuracy.
* Matrices are stored as text files in LaTeX form (eg. matrixname.tex) so Lampp may be used to help create 
* test problems or solution keys
*
*/
public class Lampp extends Application implements PropertyChangeListener, LamppGlobal {
    private final PropertyChangeSupport changes= new PropertyChangeSupport(this);
    private Pane root ;
    //set production or test for location of help html files
    //production uses absolute URL string
    //test should use html subdirectory of project's src.main.resources directory
    private final boolean production=false;
    private final String helpURL1P="http://www.artmath.ca/Linalg/index.html";
    private final String helpURL1T="/html/index.html";
    private String helpURL1;
	//name of text file used to save tool access level and access previously stored matrix values
	//the matrix values are written in LaTeX form so this file can be editted to include
    //matrices in documents
	private final String lamppData="lamppdata.txt";

    private static int numRows=1, numCols=2;
    private static final Text mcText=new Text("A");
    //top left matrix name
    private static final String topLeft="A";
    //bottom left matrix name
    private static final String bottomLeft="B";
    //bottom right matrix name
    private static final String bottomRight="C";
    //Row operations selected (or column operations)
    private static boolean rowOperation=true;      
    //row or column left selected
    private static boolean leftSelected=false;      
    private static int leftVector=-1;      
    //row or column left selected
    private static boolean rightSelected=false;      
    private static int rightVector=-1;      
    //number of current field (default "Rational" 0      "Gaussian" 1 "IntModP" 2)
    private static int field=0;      
    private static double width;
    private static double height;
    private LamppViewElement matrix[][];
    private LamppViewElement opElement2,opElement3;
    private static final double vpos[]=new double [MAX_ROWS+1];
    private static final double hpos[]=new double [MAX_COLS+1];
    private LamppVectorButton vButtons[],hButtons[];
    //user-selected operation storage
    private final int ops[][]=new int[3][MAX_OPS];
    private final boolean opTypes[]=new boolean[MAX_OPS];
    private LamppElementRational opsR[]=new LamppElementRational[MAX_OPS];
    private LamppElementGaussian opsG[]=new LamppElementGaussian[MAX_OPS];
    private LamppElementIntModP opsIMP[]=new LamppElementIntModP[MAX_OPS];
    // editted values sent to matrix
    private static LamppElementRational lre=new LamppElementRational();
    private static LamppElementGaussian lge=new LamppElementGaussian();
    private static LamppElementIntModP  lie=new LamppElementIntModP();
    //base [][][0] and executed operations [][][1] to MAX_OPS] matrices
    private static LamppElementRational mRat[][][]=new LamppElementRational [MAX_ROWS][MAX_COLS][MAX_OPS];
    private static LamppElementGaussian mGau[][][]=new LamppElementGaussian [MAX_ROWS][MAX_COLS][MAX_OPS];
    private static LamppElementIntModP mInt[][][]=new LamppElementIntModP [MAX_ROWS][MAX_COLS][MAX_OPS];
    private static LamppRCViewButton vOpButtons[]=new LamppRCViewButton[7];
    private static LamppRCSelectButton lrcb;
    private static final String paneType[]={"op","edit"};    
    double elementFontSize=18;
    double vecFontSize=16;
    double buttonFontSize=24;
    double bigButtonFontSize=32;
    private LamppOpButton lob;
    private LamppOpSelect los,lfs;
    private long lren=0;
    private long lred=1;
    private long lrein=0;
    private long lreid=1;
    private long modulus=2L;
    private long numSelectedRows=1L;
    private long numSelectedCols=2L;
    private boolean realneg=false;
    private boolean imagneg=false;
    private static ScrollPane matrixPane;
    private Pane matPane;
    private LamppAMCEButton epi,eai,ecc,eoe,efe,mcfe;
    private static final String btextSegment=" Segments ";
    private static final String btextH="H";
    private static final String btextS="S";
    private static final Text rminus=new Text("-");
    private static final Text iminus=new Text("-");
    private static final Text iplus=new Text("+");
    private static final Text isym=new Text("i");
    private static LamppTextFieldNumerator ntf;
    private static LamppTextFieldDenominator dtf;
    private static LamppTextFieldNumerator intf;
    private static LamppTextFieldDenominator idtf,modtf;
    private static LamppTextFieldDenominator cbcol;
    private static LamppTextFieldDenominator cbrow;
    private static boolean goodTableaux=true;
    private LamppSlidingPaneV lspv;
    private LamppSlidingPaneH lsph;
    private LamppSlidingPaneH lspf;
    private LamppSlidingPaneH lspmc;
    private LamppSlidingPaneH lspg;
    private Pane opPane;
    private Pane ePane;
    private Pane gPane;
    private LamppButton lbf;
    private LamppButton lbe;
    private LamppButton lbm;
    private LamppButton lbg;
    private LamppVerticalButton lvb;
    private String mod;
    private Line rLine,iLine;
    
    
    private static boolean graphShowing=false;
    private Canvas graph;
    private GraphicsContext gc;
    private LamppCuboid rP[]=new LamppCuboid[2];
    private int pin[]=new int[MAX_ROWS];
    Color color[]={Color.rgb(255,0,0), Color.rgb(0,255,0),
                          Color.rgb(0,0,255), Color.rgb(255,255,0),
                          Color.rgb(255,0,255), Color.rgb(0,255,255),
                          Color.rgb(200,125,10), Color.rgb(125,200,10),
                          Color.rgb(100,50,50), Color.rgb(70,70,120)};
    static double [] xy =new double [2];
    double xyPoints[][][][];
    double boxOutline[][][]=new double [2][8][2];
    double rectangleOutline[][][]=new double [2][16][2];
    static LamppElementRational expandFactor0=new LamppElementRational(2L);
    static LamppElementRational expandFactor1=new LamppElementRational(5L);
    static LamppElementRational shrinkFactor0=new LamppElementRational(1L,5L);
    static LamppElementRational shrinkFactor1=new LamppElementRational(1L,2L);
    LamppElementRational gl=new LamppElementRational();
    LamppElementRational gx=new LamppElementRational();
    LamppElementRational originX=new LamppElementRational(-10L);
    LamppElementRational originY=new LamppElementRational(-10L);
    LamppElementRational diagonalX=new LamppElementRational(10L);
    LamppElementRational diagonalY=new LamppElementRational(10L);
    LamppElementRational gy=new LamppElementRational();
    static int maxZooms=37;
    private static final String expandS="*";
    private static final String shrinkS="/";
    boolean draw[][];
    boolean ab[]={false,false};
    double hinc=30;
    double winc[]={4,0};
    int solidPlane=-1;
    boolean cyclePlanes=false;
    boolean cycleColumns=true;
    boolean cspwf[]={true,true};
    boolean csswf[]={true,true};
    int solidPoly[]={-1,-1};
    boolean cyclePolys[]={false,false};
    int numberZooms=9;
    double width0,height0;
    int columnOffset=0;
    int solidLine=-1;
    boolean cycleLines=false;
    static double graphWidth,graphHeight;
    static long zooms[][]=new long[maxZooms][2];
    static int graphType=0;
    Button incb,decb,cycleb,solidb,wireb,segvisb,cyclepb,segvisb2,cyclepb2;   
    int oneGraph=0;
    Label label;
    String lorp="Lines ";
    boolean [] showSegments;
    boolean wireframe[]={false,false};
    Image check;
    private static final String checked="lampp-checked";
    private static final String unchecked="lampp-unchecked";
    private static final Text graphText1=new Text("Graphing is restricted to Rational matrices with certain column sizes.");
    private static final Text graphText2=new Text("Please see the relevant textbook section for more info.");
    boolean showpoly[][]=new boolean [2][];
	LocalDate lastDate = LocalDate.now(); // Create a date object with current date to use in file lamppdata.txt
	LocalTime lastTime = LocalTime.now(); // Get current time to use in file lamppdata.txt
    File userData = new File(lamppData);
	int level=LEVEL;
    @Override
    public void start(Stage primaryStage) {
/*        
        for (int i=0;i<MAX_ROWS;i++){
        for (int j=0;j<MAX_COLS;j++){
        for (int k=0;k<MAX_OPS;k++){
        mRat[i][j][k]=new LamppElementRational();
        mGau[i][j][k]=lge; 
        mInt[i][j][k]=lie;
        }}}
        
*/

		if (userData.exists()) {  //read in old data
			getLamppState();
		} else {  // The file does not exist. Create new one.
			try {
				if (userData.createNewFile()) {
					setLamppState();
				} 		
			} catch (IOException e) {
				System.out.println("An error occurred trying to access lamppdata.txt file to get state LEVEL.");
				e.printStackTrace();
			}		
		}

// end test IO stuff **************************************************************************
        
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(0);
        primaryStage.setY(0);
        width=bounds.getWidth();
        height=bounds.getHeight();
        switch ( OS ) {
            case "android": {
                //always use production URLs for help file URLs
                helpURL1 = helpURL1P;
                break;
            }
            case "ios": {
                //always use production URLs for help file URLs
                helpURL1 = helpURL1P;
                break;
            }
            default : {
                width=Math.min(width,(MAX_ROWS+1.5)*VIEW_SIZE);
                height=Math.min(height,(MAX_COLS+1)*VIEW_SIZE);
                if(production){
                    helpURL1 = helpURL1P;
                }
                else{
					
//System.out.println(" helpURL1T="+helpURL1T);					
					
                    helpURL1 = getClass().getResource(helpURL1T).toExternalForm();
//System.out.println(" helpURL1="+helpURL1);					
					
					
                }
            }
        }
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        root = new Pane();
        root.setPadding(new Insets(0, 0, 0, 0));
        Scene scene = new Scene(root, width, height);
//        scene.getStylesheets().add("Lampp.css");
		scene.getStylesheets().add(getClass().getResource("/Lampp.css").toExternalForm());
		

        LamppElementIntModP.setModulus(2);        
        numCols=4;
        numRows=4;
        field=0; 

        numSelectedRows=numRows;
        numSelectedCols=numCols;
        matrixPane=new ScrollPane();
        matrixPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //no scroll bars
        matrixPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        matPane=createMatrixPane(elementFontSize,vecFontSize);
        matrixPane.setContent(matPane);
        matrixPane.setStyle("-fx-background-color:transparent;");
        double yshift=20;
        LamppBrowserWrapper bw = new LamppBrowserWrapper(helpURL1,width-VIEW_SIZE-750,height-VIEW_SIZE-500);
        LamppHelpPane helpPane = new LamppHelpPane(width-VIEW_SIZE,height-VIEW_SIZE,200,350, 2, true, true, "" ,bw);
        Button helpPaneButton=  helpPane.getControlButton(); 
        helpPaneButton.setTranslateY(height-helpPane.getButtonHeight()-yshift);
        helpPaneButton.setTranslateX(width-helpPane.getButtonWidth());
        lrcb=new LamppRCSelectButton(width-VIEW_SIZE/2-yshift,0,VIEW_SIZE/2,VIEW_SIZE,vecFontSize);
        lrcb.addPropertyChangeListener(this);
        opPane=createOperationPane(elementFontSize,vecFontSize);
        lspv=new LamppSlidingPaneV(width-VIEW_SIZE/2-yshift,VIEW_SIZE/2,5*VIEW_SIZE,5*VIEW_SIZE,3,false,false,
        "Operation",VIEW_SIZE/2,3*VIEW_SIZE,buttonFontSize,true,true,10,-5,opPane);
        lvb=lspv.getVerticalButton();
        lvb.setTranslateX(width-VIEW_SIZE/2-yshift);
        lvb.setTranslateY(2*VIEW_SIZE);
        lob=new LamppOpButton(width-VIEW_SIZE/2-yshift,0,VIEW_SIZE/2,VIEW_SIZE/2,buttonFontSize);    
        lob.addPropertyChangeListener(this);
        storeMatrix();
        double xstart=hpos[0];
        double xwidth=3.5*VIEW_SIZE;
        mod="";
        if(field==2)mod=String.valueOf(LamppElementIntModP.modulus);
        Pane fPane=createFieldPane(elementFontSize,5*VIEW_SIZE,height-6*VIEW_SIZE/2);
        lspf=new LamppSlidingPaneH(xstart,height-VIEW_SIZE,5*VIEW_SIZE,height-3*VIEW_SIZE/2,1,false,false,
                "Field",xwidth,BUTTONHEIGHT,buttonFontSize,false,false,false,FIELDS[field]+mod,"","",fPane);
        lbf=lspf.getButton();
    //        lbf.addPropertyChangeListener(this);
        xstart=xstart+xwidth;
        xwidth=3*VIEW_SIZE;
        Pane mcPane=createMatrixChoicePane(elementFontSize,5*VIEW_SIZE,height-6*VIEW_SIZE/2);
        lspmc=new LamppSlidingPaneH(xstart,height-VIEW_SIZE,5*VIEW_SIZE,height-3*VIEW_SIZE/2,1,false,false,
                "Matrix",xwidth,BUTTONHEIGHT,buttonFontSize,true,false,false,"A",""+numRows,""+numCols,mcPane);
        lbm=lspmc.getButton();
    //        lbm.addPropertyChangeListener(this);
        xstart=xstart+xwidth;
        xwidth=1.5*VIEW_SIZE;
        ePane=createEditPane(elementFontSize,6*VIEW_SIZE,height-3*VIEW_SIZE/2);
        lsph=new LamppSlidingPaneH(3*VIEW_SIZE+BUTTONWIDTH,height-VIEW_SIZE,6*VIEW_SIZE,height-3*VIEW_SIZE/2,1,false,false,
                "Edit",xwidth,BUTTONHEIGHT,buttonFontSize,true,true,false,"","","",ePane);
        lbe=lsph.getButton();
        lbe.setTranslateX(xstart);
        lbe.setTranslateY(height-VIEW_SIZE);
        zooms[0][0]=100;
        zooms[0][1]=1;
        for(int i=1;i<10;i++){zooms[i][0]=zooms[i-1][0]-10;zooms[i][1]=1;}
        for(int i=10;i<19;i++){zooms[i][0]=zooms[i-1][0]-1;zooms[i][1]=1;}
        zooms[19][0]=9;zooms[19][1]=10;
        for(int i=20;i<28;i++){zooms[i][0]=zooms[i-1][0]-1;zooms[i][1]=10;}
        for(int i=28;i<maxZooms;i++){zooms[i][0]=1;zooms[i][1]=zooms[i-1][1]+10;}
        for(int i=0;i<MAX_ROWS;i++)pin[i]=i;
        xstart=xstart+xwidth;
        graphWidth=width-2*VIEW_SIZE;
        graphHeight=height-VIEW_SIZE;
        rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
        if(graphType==3||graphType==4)rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
        if(graphType==4){rP[1]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);rP[1].setColumnOffset(4);}
        setShowpoly();
        segvisb=new Button(btextS);
        segvisb2=new Button(btextS);
        gPane=createGraphPane();
        lspg=new LamppSlidingPaneH(hpos[0],height-VIEW_SIZE,width-2*VIEW_SIZE,height-VIEW_SIZE,1,false,false,
               "Graph",xwidth,BUTTONHEIGHT,buttonFontSize,true,false,false,"","","",gPane);
        xwidth=VIEW_SIZE;
        lbg=lspg.getButton();
        lbg.setTranslateX(xstart);
        lbg.setTranslateY(height-VIEW_SIZE);
        lbg.addPropertyChangeListener(this);
        lob.reset();
        storeMatrix();
        lob.fire();             
        setGraphType();   
        changeGraphPane(gPane);
        lspg.setContent(gPane);
        setShowpoly();
        resetGraphButtons(true);
        drawGraph();
        mcfe.setExitedView();


//*************************************************************************************************************************************


LamppButton lbA=new LamppButton((VIEW_SIZE/2-BUTTONWIDTH)/2,(VIEW_SIZE/2-BUTTONHEIGHT)/2,BUTTONWIDTH,BUTTONHEIGHT,"A",bigButtonFontSize,false,false,true,"","",""); 

LamppButton lbB=new LamppButton((VIEW_SIZE/2-BUTTONWIDTH)/2,height-VIEW_SIZE,BUTTONWIDTH,BUTTONHEIGHT,"B",bigButtonFontSize,false,false,true,"","",""); 

LamppButton lbC=new LamppButton(width-VIEW_SIZE/2-yshift,height-2*VIEW_SIZE,BUTTONWIDTH,BUTTONHEIGHT,"C",bigButtonFontSize,false,false,true,"","",""); 


root.getChildren().addAll(matrixPane,lrcb,lbA,lbB,lbC,lspv,lvb,lob,lbe,lsph,lbm,lspmc,lspf,lbf,lspg,lbg,helpPane,helpPaneButton);



//*******************************************************************************************************************************************

        primaryStage.getIcons().add(new Image(Lampp.class.getResourceAsStream("/icon.png")));
        primaryStage.setTitle("Linear Algebra Matrix Practice Program");
        primaryStage.setScene(scene);
        primaryStage.show();
		primaryStage.setOnCloseRequest(e -> closeLampp());
    }
    
    
//*******************************************************************************************************************************************
    void setShowpoly(){
        if(graphType==3||graphType==4){
            showpoly[0]=new boolean[rP[0].size+1];
            for (int i=0;i<rP[0].size+1;i++){showpoly[0][i]=true;}
        }
        if(graphType==4){
            showpoly[1]=new boolean[rP[1].size+1];
            for (int i=0;i<rP[1].size+1;i++){
                showpoly[1][i]=true;
            }
        }
    }
    
    double wi(double x,int ig){
       return width0*x+winc[ig];
    }
    double hi(double y){
       return height0*y+hinc;
    }
    void setBoxOutline(){
        for(int ig=0;ig<=oneGraph;ig++){
            xy=rP[ig].xyRatios(rP[ig].W3);
            boxOutline[0][0][ig]=wi(xy[0],ig);
            boxOutline[1][0][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].W0);
            boxOutline[0][1][ig]=wi(xy[0],ig);
            boxOutline[1][1][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].W1);
            boxOutline[0][2][ig]=wi(xy[0],ig);
            boxOutline[1][2][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].W5);
            boxOutline[0][3][ig]=wi(xy[0],ig);
            boxOutline[1][3][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].W2);
            boxOutline[0][4][ig]=wi(xy[0],ig);
            boxOutline[1][4][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].W4);
            boxOutline[0][5][ig]=wi(xy[0],ig);
            boxOutline[1][5][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].diagonal);
            boxOutline[0][6][ig]=wi(xy[0],ig);
            boxOutline[1][6][ig]=hi(xy[1]);
            xy=rP[ig].xyRatios(rP[ig].origin);
            boxOutline[0][7][ig]=wi(xy[0],ig);
            boxOutline[1][7][ig]=hi(xy[1]);
        }
    }
    void setxyPoints(){
        xyPoints=new double [oneGraph+1][][][];
        for(int ig=0;ig<=oneGraph;ig++){ 
            xyPoints[ig]=new double [rP[ig].getSize()][2][];
            for(int i=0; i<rP[ig].getSize();i++){
                xyPoints[ig][i][0]=new double[rP[ig].polygon[i].size];
                xyPoints[ig][i][1]=new double[rP[ig].polygon[i].size];
                for(int j=0;j<rP[ig].polygon[i].size;j++){
                    xy=rP[ig].xyRatios(rP[ig].project(rP[ig].polygon[i].vertex[j]));
                    xyPoints[ig][i][0][j]=wi(xy[0],ig);
                    xyPoints[ig][i][1][j]=hi(xy[1]);	 
                }
            }
        }
    }
    private void resetGraphButtons(boolean noZoomReset){
        if(noZoomReset){
            numberZooms=9;
            originX.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
            originX.additiveInverse();
            originY.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
            originY.additiveInverse();
            diagonalX.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
            diagonalY.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
            label.setText(""+originX+" to "+diagonalY);
        }
        cycleb.setText(lorp);
        cycleLines=false;
        cyclePlanes=false;
        wireframe[0]=false;
        solidLine=-1;
        if(graphType==3||graphType==4){
            lorp="Planes";
            cspwf[0]=true;
            csswf[0]=true;
            cspwf[1]=true;
            csswf[1]=true;
            wireframe[0]=false;
            wireframe[1]=false;
            cyclePolys[0]=false;
            cyclePolys[1]=false;
            cyclePlanes=false;
            solidPlane=-1;
            solidPoly[0]=-1;
            solidPoly[1]=-1;
            cyclepb.setText(btextSegment);
            cycleb.setText(lorp);
            solidb.setVisible(true);
            wireb.setVisible(true);
            segvisb.setVisible(true);
            segvisb.setText(btextS);
            cyclepb.setVisible(true); 
            if(graphType==4){
                segvisb2.setVisible(true);
                segvisb2.setText(btextS);
                cyclepb2.setText(btextSegment);
                cyclepb2.setVisible(true); 
            }
            else{
                segvisb2.setVisible(false);
                cyclepb2.setVisible(false); 
            }
        }
        else{
            lorp="Lines";
            cycleb.setText(lorp);
            solidb.setVisible(false);
            wireb.setVisible(false);
            segvisb.setVisible(false);
            cyclepb.setVisible(false); 
            segvisb2.setVisible(false);
            cyclepb2.setVisible(false); 
        }
        setShowpoly();
    }
    private void setGraphType(){      
        // 0 == no graph
        // 1 == lines for cols 1-3
        // 2 == lines for cols 1-3 and 4-6
        // 3 == planes for cols 1-4
        // 4 == planes for cols 1-4 and 5-8
        // 5 == oriented 2-D vectors (line segments) cols 1-2
        // 6 == oriented 2-D vectors (line segments) cols 1-2 and 3-4
        // 7 == oriented 3-D bivectors (triangles) cols 1-3
        // 8 == oriented 3-D bivectors (triangles) cols 1-3 and 4-6
        graphType=0;
        columnOffset=0;
        oneGraph=0;
        if(field==0){
            if(numCols==3)graphType=1;
            else if(numCols==6){graphType=2;columnOffset=3;oneGraph=1;}
            else if(numCols==4)graphType=3;
            else if(numCols==8){graphType=4;columnOffset=4;oneGraph=1;}
        }
    }
    private Pane createGraphPane() {
        Pane graphPane = new Pane();
        winc[0]=4;
        width0=graphWidth-2*hinc;
        height0=graphHeight-VIEW_SIZE;
        if(height0>width0){
            hinc=hinc+(height0-width0)/2;
            height0=width0;
        }
        else{
            winc[0]=winc[0]+(width0-height0)/2;
            width0=height0;
        }
        if(graphType%2==1){oneGraph=0;}
        else {oneGraph=1;width0=graphWidth/2-winc[0];height0=width0;winc[1]=winc[0]+width0;}
        double xSpace=5;
        double ySpace=5;
        graphPane.setPrefSize(graphWidth,graphHeight);
        graph = new Canvas(graphWidth,graphHeight-BUTTONHEIGHT-2*ySpace);
        gc = graph.getGraphicsContext2D();
        double x1,y1;
        graphPane.getChildren().add(graph);
        double awf=LamppViewElement.bestFontSize(elementFontSize);
        label=new Label(""+originX+" to "+diagonalY);
        label.setAlignment(Pos.CENTER);
        label.setTranslateY(graphHeight-BUTTONHEIGHT-2*ySpace);
        label.setFont( new Font(label.getFont().getName(), awf ));  
        incb= new Button(expandS);
        incb.setTranslateX(xSpace);
        y1=graphHeight-BUTTONHEIGHT-2*ySpace;
        incb.setTranslateY(y1);
        incb.setFont( new Font(incb.getFont().getName(), awf ));  
        graphText1.setFont( new Font(incb.getFont().getName(), awf ));  
        graphText2.setFont( new Font(incb.getFont().getName(), awf ));  
        incb.setPrefSize(BUTTONWIDTH,BUTTONHEIGHT);
        incb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(graphType==0)return;
                if(numberZooms>0)numberZooms--;
                originX.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                originX.additiveInverse();
                originY.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                originY.additiveInverse();
                diagonalX.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                diagonalY.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                label.setText(""+originX+" to "+diagonalY);
                if (graphType>2&&graphType<5){
                    rP[0].zoom(originX,diagonalY);
                    if(solidPoly[0]>rP[0].getSize())solidPoly[0]=rP[0].getSize();
                    if(graphType==4){rP[1].zoom(originX,diagonalY);rP[1].setColumnOffset(4);if(solidPoly[1]>rP[1].getSize())solidPoly[1]=rP[1].getSize();}
                    setxyPoints();
                }                    
                drawGraph();
            }
        });
        double bw=getNodeDimensions(incb).getWidth();
        graphPane.getChildren().add(incb);
        decb= new Button(shrinkS);
        x1=2*xSpace+bw;
        decb.setTranslateX(x1);
        decb.setTranslateY(y1);
        decb.setPrefSize(BUTTONWIDTH,BUTTONHEIGHT);
        decb.setFont( new Font(incb.getFont().getName(), awf ));  
        decb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(graphType==0)return;
                if(numberZooms<maxZooms-1)numberZooms++;
                originX.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                originX.additiveInverse();
                originY.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                originY.additiveInverse();
                diagonalX.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                diagonalY.setRational(zooms[numberZooms][0],zooms[numberZooms][1]);
                label.setText(""+originX+" to "+diagonalY);
                if (graphType>2&&graphType<5){
                    rP[0].zoom(originX,diagonalY);
                    if(solidPoly[0]>rP[0].getSize())solidPoly[0]=rP[0].getSize();
                    if(graphType==4){rP[1].zoom(originX,diagonalY);rP[1].setColumnOffset(4);if(solidPoly[1]>rP[1].getSize())solidPoly[1]=rP[1].getSize();}
                    setxyPoints();
                }                    
                drawGraph();
            }
        });
        graphPane.getChildren().add(decb);
        x1=4*xSpace+3*bw;
        label.setTranslateX(x1);
        x1=2*getNodeDimensions(label).getWidth()+x1+2*xSpace;
        graphPane.getChildren().add(label);
        cycleb= new Button(lorp);
        cycleb.setFont( new Font(incb.getFont().getName(), awf ));  
        bw=getNodeDimensions(cycleb).getWidth();
        cycleb.setMinWidth(bw);
        cycleb.setTranslateX(graphWidth-2*xSpace-bw);
        cycleb.setTranslateY(y1);
        cycleb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(graphType==0)return;
                if(graphType<3){
                    solidLine++;
                    if(solidLine==numRows){
                       cycleb.setText(lorp);
                       cycleLines=false;
                       solidLine=-1;
                    }
                    else{
                       cycleLines=true;          
                       cycleb.setText(""+(solidLine+1));
                    }
                }
                else if(graphType<5){
                    csswf[0]=true;
                    csswf[1]=true;
                    cyclePlanes=true;
                    wireframe[0]=false;
                    wireframe[1]=false;
                    cyclePolys[0]=false;
                    cyclePolys[1]=false;
                    solidPoly[0]=-1;
                    solidPoly[1]=-1;
                    solidPlane=(solidPlane+1)%rP[0].m;
                    if(solidPlane==0){cspwf[0]=!cspwf[0];cspwf[1]=!cspwf[1];}
                    cycleb.setText(""+(solidPlane+1));
                    cyclepb.setText(btextSegment);                
                    cyclepb2.setText(btextSegment);
                }
                segvisb.setText(btextS);
                segvisb2.setText(btextS);
                setShowpoly();
                drawGraph();
            }
        });
        graphPane.getChildren().add(cycleb);
        //add buttons for solid, wire, segment, viewsegment
        solidb= new Button("Solid");  
        solidb.setFont( new Font(incb.getFont().getName(), awf ));
        solidb.setTranslateX(x1);
        solidb.setTranslateY(y1);
        solidb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(graphType==0)return;
                cspwf[0]=true;
                csswf[0]=true;
                cspwf[1]=true;
                csswf[1]=true;
                wireframe[0]=false;
                wireframe[1]=false;
                cyclePolys[0]=false;
                cyclePolys[1]=false;
                cyclePlanes=false;
                solidPlane=-1;
                solidPoly[0]=-1;
                solidPoly[1]=-1;
                cycleb.setText(lorp);
                cyclepb.setText(btextSegment);
                cyclepb2.setText(btextSegment);
                segvisb.setText(btextS);
                segvisb2.setText(btextS);
                setShowpoly();
                drawGraph();
            }
        });
        x1=getNodeDimensions(solidb).getWidth()+x1+xSpace;
        graphPane.getChildren().add(solidb);
        wireb= new Button("Wire");
        wireb.setFont( new Font(incb.getFont().getName(), awf ));
        wireb.setTranslateX(x1);
        wireb.setTranslateY(y1);
        wireb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(graphType==0)return;
                cspwf[0]=true;
                csswf[0]=true;
                cspwf[1]=true;
                csswf[1]=true;
                wireframe[0]=true;
                wireframe[1]=true;
                cyclePolys[0]=false;
                cyclePolys[1]=false;
                cyclePlanes=false;
                solidPlane=-1;
                solidPoly[0]=-1;
                solidPoly[1]=-1;
                cycleb.setText(lorp);
                cyclepb.setText(btextSegment);
                cyclepb2.setText(btextSegment);
                segvisb.setText(btextS);
                segvisb2.setText(btextS);
                setShowpoly();
                drawGraph();
            }
        });
        x1=getNodeDimensions(wireb).getWidth()+x1+4*xSpace;
        graphPane.getChildren().add(wireb);
        cyclepb=new Button(btextSegment);
        cyclepb.setFont( new Font(incb.getFont().getName(), awf ));
        bw=getNodeDimensions(cyclepb).getWidth();
        cyclepb.setMinWidth(bw);
        cyclepb.setTranslateX(x1);
        cyclepb.setTranslateY(y1);
        cyclepb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            if(graphType==0)return;
            if(rP[0].size==0)return;
            cspwf[0]=true;
            solidPlane=-1;
            cyclePlanes=false;
            wireframe[0]=false;
            cyclePolys[0]=true;
            solidPoly[0]=(solidPoly[0]+1)%rP[0].size;
            if(solidPoly[0]==0)csswf[0]=!csswf[0];
            cyclepb.setText(""+(solidPoly[0]+1) + " of " + rP[0].size);
                if(!showpoly[0][solidPoly[0]])segvisb.setText(btextH);
                else segvisb.setText(btextS);
            cycleb.setText(lorp);
            drawGraph();
            }
        });
        x1=bw+x1;
        graphPane.getChildren().add(cyclepb);
        segvisb.setFont( new Font(incb.getFont().getName(), awf ));
        segvisb.setTranslateX(x1);
        segvisb.setTranslateY(y1);
        x1=getNodeDimensions(segvisb).getWidth()+x1+3*xSpace;
        segvisb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

//*******************************************************************************
                if(solidPoly[0]<0)return;
                showpoly[0][solidPoly[0]]=!showpoly[0][solidPoly[0]];
                if(!showpoly[0][solidPoly[0]])segvisb.setText(btextH);
                else segvisb.setText(btextS);
                drawGraph();
            }
        });
        graphPane.getChildren().add(segvisb);
        cyclepb2=new Button(btextSegment);
        cyclepb2.setFont( new Font(incb.getFont().getName(), awf ));
        bw=getNodeDimensions(cyclepb2).getWidth();
        cyclepb2.setMinWidth(bw);
        cyclepb2.setTranslateX(x1);
        cyclepb2.setTranslateY(y1);
        cyclepb2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(graphType==0)return;
                if(rP[1].size==0)return;
                cspwf[1]=true;
                solidPlane=-1;
                cyclePlanes=false;
                wireframe[1]=false;
                cyclePolys[1]=true;                
                solidPoly[1]=(solidPoly[1]+1)%rP[1].size;
                if(solidPoly[1]==0)csswf[1]=!csswf[1];
                cyclepb2.setText(""+(solidPoly[1]+1) + " of " + rP[1].size);
                if(!showpoly[1][solidPoly[1]])segvisb2.setText(btextH);
                else segvisb2.setText(btextS);
                cycleb.setText(lorp);
                drawGraph();
            }
        });
        x1=bw+x1;
        graphPane.getChildren().add(cyclepb2);
        segvisb2.setFont( new Font(incb.getFont().getName(), awf ));
        segvisb2.setTranslateX(x1);
        segvisb2.setTranslateY(y1);
        segvisb2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
 //*************************************************************************************   
                if(solidPoly[1]<0)return;
                showpoly[1][solidPoly[1]]=!showpoly[1][solidPoly[1]];
                if(!showpoly[1][solidPoly[1]])segvisb2.setText(btextH);
                else segvisb2.setText(btextS);
                drawGraph();
            }
        });
        graphPane.getChildren().add(segvisb2);
        if(graphType==3||graphType==4){
            lorp="Planes";
            cycleb.setText(lorp);
            solidb.setVisible(true);
            wireb.setVisible(true);
            segvisb.setVisible(true);
            cyclepb.setVisible(true); 
            if(graphType==4){
                segvisb2.setVisible(true);
                cyclepb2.setVisible(true); 
            }
            else{
                segvisb2.setVisible(false);
                cyclepb2.setVisible(false); 
            }
        }
        else{
            lorp="Lines";
            cycleb.setText(lorp);
            solidb.setVisible(false);
            wireb.setVisible(false);
            segvisb.setVisible(false);
            cyclepb.setVisible(false); 
            segvisb2.setVisible(false);
            cyclepb2.setVisible(false); 
        }


//  0------10-------1
//  |       |       |
//  |      11       |
//  |               |
//  |      12       |
//  |       |       |
//  4-5   6-+-7   8-9        
//  |       |       |
//  |      13       |
//  |               |     
//  |      14       |
//  |       |       |      
//  3------15-------2
//        
        for(int ig=0;ig<=oneGraph;ig++){
            rectangleOutline[0][0][ig]=winc[ig];
            rectangleOutline[1][0][ig]=hinc;
            rectangleOutline[0][1][ig]=winc[ig]+width0;
            rectangleOutline[1][1][ig]=hinc;
            rectangleOutline[0][2][ig]=winc[ig]+width0;
            rectangleOutline[1][2][ig]=hinc+height0;
            rectangleOutline[0][3][ig]=winc[ig];
            rectangleOutline[1][3][ig]=hinc+height0;
            rectangleOutline[0][4][ig]=winc[ig];
            rectangleOutline[1][4][ig]=hinc+height0/2;
            rectangleOutline[0][5][ig]=winc[ig]+hinc/2;
            rectangleOutline[1][5][ig]=hinc+height0/2;
            rectangleOutline[0][6][ig]=winc[ig]+(width0)/2-hinc/2;
            rectangleOutline[1][6][ig]=hinc+height0/2;
            rectangleOutline[0][7][ig]=winc[ig]+(width0)/2+hinc/2;
            rectangleOutline[1][7][ig]=hinc+height0/2;
            rectangleOutline[0][8][ig]=winc[ig]+width0-hinc/2;
            rectangleOutline[1][8][ig]=hinc+height0/2;
            rectangleOutline[0][9][ig]=winc[ig]+width0;
            rectangleOutline[1][9][ig]=hinc+height0/2;
            rectangleOutline[0][10][ig]=winc[ig]+width0/2;
            rectangleOutline[1][10][ig]=hinc;
            rectangleOutline[0][11][ig]=winc[ig]+width0/2;
            rectangleOutline[1][11][ig]=hinc+hinc/2;
            rectangleOutline[0][12][ig]=winc[ig]+width0/2;
            rectangleOutline[1][12][ig]=(hinc+height0)/2;
            rectangleOutline[0][13][ig]=winc[ig]+width0/2;
            rectangleOutline[1][13][ig]=(3*hinc+height0)/2;
            rectangleOutline[0][14][ig]=winc[ig]+width0/2;
            rectangleOutline[1][14][ig]=hinc/2+height0;
            rectangleOutline[0][15][ig]=winc[ig]+width0/2;
            rectangleOutline[1][15][ig]=hinc+height0;
        }
        draw=new boolean [numRows][2];
        drawGraph();
        graphText1.setTranslateY(2*awf); 
        graphText2.setTranslateY(4*awf);  
        if(graphType<1){
        graphText1.setVisible(true); 
        graphText2.setVisible(true);
        }
        else{
        graphText1.setVisible(false); 
        graphText2.setVisible(false);
        }
        graphPane.getChildren().add(graphText1);
        graphPane.getChildren().add(graphText2);
        return graphPane;
    }
    private void changeGraphPane(Pane graphPane) {
        graphText1.setVisible(false); 
        graphText2.setVisible(false);  
        winc[0]=4;
        width0=graphWidth-2*hinc;
        height0=graphHeight-VIEW_SIZE;
        if(height0>width0){
            hinc=hinc+(height0-width0)/2;
            height0=width0;
        }
        else{
            winc[0]=winc[0]+(width0-height0)/2;
            width0=height0;
        }
        if(graphType%2==1){oneGraph=0;}
        else {oneGraph=1;width0=graphWidth/2-winc[0];height0=width0;winc[1]=winc[0]+width0;}
        if(graphType==0){
            graphText1.setVisible(true); 
            graphText2.setVisible(true);  
        }
        else if(graphType<3){
            for(int ig=0;ig<=oneGraph;ig++){
                rectangleOutline[0][0][ig]=winc[ig];
                rectangleOutline[1][0][ig]=hinc;
                rectangleOutline[0][1][ig]=winc[ig]+width0;
                rectangleOutline[1][1][ig]=hinc;
                rectangleOutline[0][2][ig]=winc[ig]+width0;
                rectangleOutline[1][2][ig]=hinc+height0;
                rectangleOutline[0][3][ig]=winc[ig];
                rectangleOutline[1][3][ig]=hinc+height0;
                rectangleOutline[0][4][ig]=winc[ig];
                rectangleOutline[1][4][ig]=hinc+height0/2;
                rectangleOutline[0][5][ig]=winc[ig]+hinc/2;
                rectangleOutline[1][5][ig]=hinc+height0/2;
                rectangleOutline[0][6][ig]=winc[ig]+(width0)/2-hinc/2;
                rectangleOutline[1][6][ig]=hinc+height0/2;
                rectangleOutline[0][7][ig]=winc[ig]+(width0)/2+hinc/2;
                rectangleOutline[1][7][ig]=hinc+height0/2;
                rectangleOutline[0][8][ig]=winc[ig]+width0-hinc/2;
                rectangleOutline[1][8][ig]=hinc+height0/2;
                rectangleOutline[0][9][ig]=winc[ig]+width0;
                rectangleOutline[1][9][ig]=hinc+height0/2;
                rectangleOutline[0][10][ig]=winc[ig]+width0/2;
                rectangleOutline[1][10][ig]=hinc;
                rectangleOutline[0][11][ig]=winc[ig]+width0/2;
                rectangleOutline[1][11][ig]=hinc+hinc/2;
                rectangleOutline[0][12][ig]=winc[ig]+width0/2;
                rectangleOutline[1][12][ig]=(hinc+height0)/2;
                rectangleOutline[0][13][ig]=winc[ig]+width0/2;
                rectangleOutline[1][13][ig]=(3*hinc+height0)/2;
                rectangleOutline[0][14][ig]=winc[ig]+width0/2;
                rectangleOutline[1][14][ig]=hinc/2+height0;
                rectangleOutline[0][15][ig]=winc[ig]+width0/2;
                rectangleOutline[1][15][ig]=hinc+height0;
            }
        }
        else if (graphType<5){
            if(graphType==3||graphType==4)rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
            if(graphType==4){rP[1]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);rP[1].setColumnOffset(4);}
            setBoxOutline();
            setxyPoints();
            setShowpoly();
        }
        draw=new boolean [numRows][2];
        resetGraphButtons(false);
        if (graphType>2&&graphType<5){
            rP[0].zoom(originX,diagonalY);
            if(graphType==4){rP[1].zoom(originX,diagonalY);rP[1].setColumnOffset(4);}
            setxyPoints();
        }     
        drawGraph();
    }    
    private void drawGraph() {
        //determine graph type, draw if allowed *******************************************************************************
        if(graphType==0){gc.clearRect(0, 0, graphWidth,graphHeight);return;}//only graph rational numbers (for now)
        gl.equals(diagonalX);
        gl.difference(originX);
        gc.clearRect(0, 0, graphWidth,graphHeight);
        gc.setFill(Color.BLACK);     
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        //draw lines
        if(graphType<3){
            xyPoints=new double [2][numRows][4][1]; 
            for(int ig=0;ig<=oneGraph;ig++){
                for(int i=0; i<numRows;i++){
                   draw[i][ig]=xypt(i,ig);
                }
                gc.strokeLine(rectangleOutline[0][4][ig],rectangleOutline[1][4][ig],rectangleOutline[0][9][ig],
                           rectangleOutline[1][9][ig]);
                gc.strokeLine(rectangleOutline[0][10][ig],rectangleOutline[1][10][ig],rectangleOutline[0][15][ig],
                          rectangleOutline[1][15][ig]);
                gc.setLineWidth(3);
                for(int i=0;i<numRows;i++){
                    if(draw[i][ig]){
                        if(!cycleLines){
                            gc.setStroke(color[pin[i]]);
                            gc.setFill(color[pin[i]]);
                            gc.strokeLine(xyPoints[ig][i][0][0],xyPoints[ig][i][1][0],
                              xyPoints[ig][i][2][0],xyPoints[ig][i][3][0]);
                            gc.strokeLine(xyPoints[ig][i][0][0]+1,xyPoints[ig][i][1][0],
                              xyPoints[ig][i][2][0]+1,xyPoints[ig][i][3][0]);
                            gc.strokeLine(xyPoints[ig][i][0][0]-1,xyPoints[ig][i][1][0],
                              xyPoints[ig][i][2][0]-1,xyPoints[ig][i][3][0]);
                            gc.strokeLine(xyPoints[ig][i][0][0],xyPoints[ig][i][1][0]+1,
                              xyPoints[ig][i][2][0],xyPoints[ig][i][3][0]+1);
                            gc.strokeLine(xyPoints[ig][i][0][0]-1,xyPoints[ig][i][1][0]-1,
                              xyPoints[ig][i][2][0],xyPoints[ig][i][3][0]-1);
                        }
                        else if(solidLine==i){
                            gc.setStroke(color[pin[i]]);
                            gc.setFill(color[pin[i]]);
                            gc.strokeLine(xyPoints[ig][i][0][0],xyPoints[ig][i][1][0],
                              xyPoints[ig][i][2][0],xyPoints[ig][i][3][0]);
                            gc.strokeLine(xyPoints[ig][i][0][0]+1,xyPoints[ig][i][1][0],
                              xyPoints[ig][i][2][0]+1,xyPoints[ig][i][3][0]);
                            gc.strokeLine(xyPoints[ig][i][0][0]-1,xyPoints[ig][i][1][0],
                              xyPoints[ig][i][2][0]-1,xyPoints[ig][i][3][0]);
                            gc.strokeLine(xyPoints[ig][i][0][0],xyPoints[ig][i][1][0]+1,
                              xyPoints[ig][i][2][0],xyPoints[ig][i][3][0]+1);
                            gc.strokeLine(xyPoints[ig][i][0][0]-1,xyPoints[ig][i][1][0]-1,
                              xyPoints[ig][i][2][0],xyPoints[ig][i][3][0]-1);
                        }
                    }
                }
                gc.setFill(Color.BLACK);     
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                int j;
                for(int i=0;i<4;i++){
                    j=(i+1)%4;
                    gc.strokeLine(rectangleOutline[0][i][ig],rectangleOutline[1][i][ig],rectangleOutline[0][j][ig],
                               rectangleOutline[1][j][ig]);
                }
                for(int i=4;i<15;i=i+2){
                    j=i+1;
                    gc.strokeLine(rectangleOutline[0][i][ig],rectangleOutline[1][i][ig],rectangleOutline[0][j][ig],
                               rectangleOutline[1][j][ig]);
                }        
            }
        }
        //draw planes
        else if(graphType<5){
            for(int ig=0;ig<=oneGraph;ig++){
                gc.setFill(Color.GRAY);     
                gc.setStroke(Color.GRAY);
                for(int i=1;i<6;i=i+2){
                    gc.strokeLine(boxOutline[0][7][ig],boxOutline[1][7][ig],boxOutline[0][i][ig],
                    boxOutline[1][i][ig]);
                }
                gc.setFill(Color.BLACK);     
                gc.setStroke(Color.BLACK);
                for(int i=0;i<rP[ig].size;i++){
                    gc.setFill(color[rP[ig].polygon[i].planeIndex]);
                    if(wireframe[ig])gc.strokePolygon(xyPoints[ig][i][0],xyPoints[ig][i][1],
                        rP[ig].polygon[i].size);
                    else if(cyclePlanes){
                        if(rP[ig].polygon[i].planeIndex==solidPlane)gc.fillPolygon(xyPoints[ig][i][0],xyPoints[ig][i][1],
                                rP[ig].polygon[i].size);
                        else if(cspwf[ig])gc.strokePolygon(xyPoints[ig][i][0],xyPoints[ig][i][1],
                            rP[ig].polygon[i].size);
                    }
                    else if(cyclePolys[ig]){
                        
                        if(i>rP[ig].size){setShowpoly();}
                        
                        
                        if(showpoly[ig][i]){
                            if(i<=solidPoly[ig])gc.fillPolygon(xyPoints[ig][i][0],xyPoints[ig][i][1],
                                    rP[ig].polygon[i].size);
                            else if(csswf[ig])gc.strokePolygon(xyPoints[ig][i][0],xyPoints[ig][i][1],
                                    rP[ig].polygon[i].size);
                        }
                    }
                    else gc.fillPolygon(xyPoints[ig][i][0],xyPoints[ig][i][1],
                        rP[ig].polygon[i].size);
                }                
                gc.setFill(Color.LIGHTGRAY);     
                gc.setStroke(Color.LIGHTGRAY);
                gc.setLineWidth(1);
                for(int i=0;i<6;i++){
                    int j=(i+1)%6;
                    gc.strokeLine(boxOutline[0][i][ig],boxOutline[1][i][ig],boxOutline[0][j][ig],
                              boxOutline[1][j][ig]);
                }
                for(int i=0;i<6;i=i+2){
                    gc.strokeLine(boxOutline[0][6][ig],boxOutline[1][6][ig],boxOutline[0][i][ig],
                              boxOutline[1][i][ig]);
                }                
                gc.setFill(Color.BLACK);     
                gc.setStroke(Color.BLACK);
            }
        }
    }   
    boolean xypt(int i,int ig){
        ab[0]=mRat[i][columnOffset*ig][lob.getExecuted()].isZero();
        ab[1]=mRat[i][columnOffset*ig+1][lob.getExecuted()].isZero();
        if(ab[0] & ab[1])return false;
        if(!ab[0] & ab[1]){
            xyPoints[ig][i][0][0]=xpt(i,originY,ig); 
            if(xyPoints[ig][i][0][0]<0)return false;
            xyPoints[ig][i][1][0]=rectangleOutline[1][0][ig]; 
            xyPoints[ig][i][2][0]=xyPoints[ig][i][0][0]; 
            xyPoints[ig][i][3][0]=rectangleOutline[1][2][ig]; 
            return true;
        }
        if(ab[0] & !ab[1]){
            xyPoints[ig][i][0][0]=rectangleOutline[0][0][ig];
            xyPoints[ig][i][1][0]=ypt(i,originX,ig);
            if(xyPoints[ig][i][1][0]<0)return false;
            xyPoints[ig][i][2][0]=rectangleOutline[0][2][ig];
            xyPoints[ig][i][3][0]=xyPoints[ig][i][1][0];
            return true;
        }
        gx.equals(mRat[i][columnOffset*ig][lob.getExecuted()]);
        gx.quotient(mRat[i][1+columnOffset*ig][lob.getExecuted()]);
        xyPoints[ig][i][0][0]=rectangleOutline[0][0][ig];
        xyPoints[ig][i][1][0]=ypt(i,originX,ig);
        if(gx.gt(LamppElementRational.zero)){
            // negative slope
            if(ab[1])return false;
            if(xyPoints[ig][i][1][0]<0){
                xyPoints[ig][i][0][0]=xpt(i,diagonalY,ig);
                if(ab[1])return false;
                xyPoints[ig][i][1][0]=rectangleOutline[1][0][ig];
            }
            if(xyPoints[ig][i][1][0]<0)return false;
            xyPoints[ig][i][2][0]=rectangleOutline[0][2][ig];
            xyPoints[ig][i][3][0]=ypt(i,diagonalX,ig);
            if(ab[0])return false;
            if(xyPoints[ig][i][3][0]>0)return true;
            xyPoints[ig][i][3][0]=rectangleOutline[1][2][ig];
            xyPoints[ig][i][2][0]=xpt(i,originY,ig);
            if(ab[0])return false;
            if(xyPoints[ig][i][2][0]>0)return true;
        }
        else{
            // positive slope
            if(ab[0])return false;
            if(xyPoints[ig][i][1][0]<0){
            xyPoints[ig][i][0][0]=xpt(i,originY,ig);
            if(ab[1])return false;
            xyPoints[ig][i][1][0]=rectangleOutline[1][3][ig];
            }
            if(xyPoints[ig][i][1][0]<0)return false;
            xyPoints[ig][i][2][0]=rectangleOutline[0][2][ig];
            xyPoints[ig][i][3][0]=ypt(i,diagonalX,ig);
            if(ab[1])return false;
            if(xyPoints[ig][i][3][0]>0)return true;
            xyPoints[ig][i][3][0]=rectangleOutline[1][1][ig];
            xyPoints[ig][i][2][0]=xpt(i,diagonalY,ig);
            if(ab[0])return false;
            if(xyPoints[ig][i][2][0]>0)return true;
        }
        return false;
    }
    int xpt(int i,LamppElementRational Y,int gt){
        gx.equals(Y);
        gx.product(mRat[i][1+columnOffset*gt][lob.getExecuted()]);
        gx.difference(mRat[i][2+columnOffset*gt][lob.getExecuted()]);
        gx.quotient(mRat[i][columnOffset*gt][lob.getExecuted()]);
        gx.additiveInverse();
        gx.difference(originX);
        gx.quotient(gl);
        ab[0]=gx.isZero();
        ab[1]=gx.isUnity();
        if(!gx.isOnUnity())return -1;
        gx.product((long)width0);
        int xx=gx.toInt();
        return xx+(int)winc[gt];
    }
    int ypt(int i,LamppElementRational X,int gt){
        gy.equals(X);
        gy.product(mRat[i][columnOffset*gt][lob.getExecuted()]);
        gy.difference(mRat[i][2+columnOffset*gt][lob.getExecuted()]);
        gy.quotient(mRat[i][1+columnOffset*gt][lob.getExecuted()]);
        gy.sum(originY);
        gy.sum(gl);
        gy.quotient(gl);
        ab[0]=gy.isZero();
        ab[1]=gy.isUnity();
        if(!gy.isOnUnity())return -1;
        gy.product((long)height0);
        int yy=gy.toInt();
        return yy+(int)hinc;
    }
    void zoom(LamppElementRational factor){
        originX.product(factor);
        diagonalX.product(factor);
        originY.product(factor);
        diagonalY.product(factor);
    }    

    private Pane createMatrixChoicePane(final double fontSize,final double w,final double h) {
        final Pane matrixChoicePane = new Pane();
        final Pane keybPane = new Pane();
        matrixChoicePane.setPrefSize(w,h);
        double xSpace=5;
        double ySpace=5;
        double x1,x2,x3,x4,x5,y1,y2,mw;
        double awf=LamppViewElement.bestFontSize(Math.min(BUTTONWIDTH,BUTTONHEIGHT));
        mcfe=new LamppAMCEButton(w-2*BUTTONWIDTH-2*xSpace,2*ySpace,2*BUTTONWIDTH,3*BUTTONHEIGHT/2,awf,"Set Matrix",4);
        mcfe.addPropertyChangeListener(this);    
        matrixChoicePane.getChildren().add(mcfe);
        final Text mName=new Text("Matrix: ");
        mName.setFont( new Font(mcText.getFont().getName(), awf ));
        final Text xSign=new Text("\u00D7");
        xSign.setFont( new Font(mcText.getFont().getName(), awf ));
        mcText.setText(topLeft);
        mcText.setFont( new Font(mcText.getFont().getName(), awf ));       
        cbrow=new LamppTextFieldDenominator(""+numRows);
        cbrow.setFont(new Font(fontSize));
        cbrow.applyCss();
        cbrow.layout();
        cbrow.setPrefColumnCount(2);
        cbrow.setAlignment(Pos.BASELINE_CENTER);
        mw=getNodeDimensions(cbrow).getWidth();
        cbrow.lengthProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable,
                   Number oldValue, Number newValue) {
               if (newValue.intValue() > oldValue.intValue()) {
                   // Check if the new character is greater than 2
                   if (cbrow.getText().length() >= 2) {
                       // if it's 3rd character then just setText to previous
                       // one
                       cbrow.setText(cbrow.getText().substring(0, 2));
                   }
               }
           }
        }
        );   
        //if enter keyed for new value in LamppTextFieldDenominator tf
        cbrow.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!cbrow.isValid()){
                    cbrow.setText(String.valueOf(numSelectedRows));
                }
                else{
                    cbrow.setText(cbrow.getText().replaceFirst("^0+(?!$)", ""));
                    if(MAX_ROWS>=cbrow.getLong()){
                        numSelectedRows=cbrow.getLong();
                    }
                    else{
                        numSelectedRows=numRows;
                        cbrow.setText(String.valueOf(numSelectedRows));
                    }
                }
            }
        }
        );
        //if LamppTextFieldDenominator tf loses focus when editted, check if good value.
        cbrow.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue){       
        //            System.out.println("Textfield has focus");
                }
                else{
                    if(!cbrow.isValid()){
                        cbrow.setText(String.valueOf(numRows));
                    }
                    else{
                        cbrow.setText(cbrow.getText().replaceFirst("^0+(?!$)", ""));
                        if(cbrow.getLong()<=MAX_ROWS){
                            numSelectedRows=cbrow.getLong();
                        }
                        else{
                            numSelectedRows=numRows;
                            cbrow.setText(String.valueOf(numSelectedRows));
                        }
            //            System.out.println("Textfield out of focus");
                    }
                }
            }
        }
        );
        matrixChoicePane.getChildren().add(cbrow);
        cbcol=new LamppTextFieldDenominator(""+numCols);
        cbcol.setFont(new Font(fontSize));
        cbcol.applyCss();
        cbcol.layout();
        cbcol.setPrefColumnCount(2);
        cbcol.setAlignment(Pos.BASELINE_CENTER);
        cbcol.lengthProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable,
                   Number oldValue, Number newValue) {
               if (newValue.intValue() > oldValue.intValue()) {
                   // Check if the new character is greater than 2
                   if (cbcol.getText().length() >= 2) {
                       // if it's 3rd character then just setText to previous
                       // one
                       cbcol.setText(cbcol.getText().substring(0, 2));
                   }
               }
           }
        }
        );   
        //if enter keyed for new value in LamppTextFieldDenominator tf
        cbcol.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!cbcol.isValid()){
                    cbcol.setText(String.valueOf(numSelectedCols));
                }
                else{
                    cbcol.setText(cbcol.getText().replaceFirst("^0+(?!$)", ""));
                    if(MAX_COLS>=cbcol.getLong()){
                        numSelectedCols=cbcol.getLong();
                    }
                    else{
                        numSelectedCols=numCols;
                        cbcol.setText(String.valueOf(numSelectedCols));
                    }
                }
            }
        }
        );
        //if LamppTextFieldDenominator tf loses focus when editted, check if good value.
        cbcol.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue){       
        //            System.out.println("Textfield has focus");
                }
                else{
                    if(!cbcol.isValid()){
                        cbcol.setText(String.valueOf(numCols));
                    }
                    else{
                        cbcol.setText(cbcol.getText().replaceFirst("^0+(?!$)", ""));
                        if(cbcol.getLong()<=MAX_COLS){
                            numSelectedCols=cbcol.getLong();
                        }
                        else{
                            numSelectedCols=numCols;
                            cbcol.setText(String.valueOf(numSelectedCols));
                        }
            //            System.out.println("Textfield out of focus");
                    }
                }
            }
        }
        );
        matrixChoicePane.getChildren().add(cbcol);
        y1=mName.getLayoutBounds().getHeight()+2*ySpace;
        y2=2*ySpace+BUTTONHEIGHT/4;
        x1=2*xSpace;
        x2=mName.getLayoutBounds().getWidth()+x1;
        x3=mcText.getLayoutBounds().getWidth()+x2+2*xSpace;
        x4=mw+x3+xSpace;
        x5=xSign.getLayoutBounds().getWidth()+x4+xSpace;
        mName.setTranslateX(x1);
        mName.setTranslateY(y1);
        mcText.setTranslateX(x2);
        mcText.setTranslateY(y1);
        cbrow.setTranslateX(x3);
        cbrow.setTranslateY(y2);
        xSign.setTranslateX(x4);
        xSign.setTranslateY(y1);
        cbcol.setTranslateX(x5);
        cbcol.setTranslateY(y2);
        matrixChoicePane.getChildren().addAll(mcText,mName,xSign);   
        LamppKeyboard keyb = new LamppKeyboard();
        keybPane.getChildren().add(keyb.view());
        keybPane.setTranslateY(y2+VIEW_SIZE+ySpace);
        matrixChoicePane.getChildren().add(keybPane);    
        return matrixChoicePane;
    }    
    private Pane createFieldPane(final double fontSize,final double w,final double h) {
        final Pane fieldPane = new Pane();
        final Pane keybPane = new Pane();
        fieldPane.setPrefSize(w,h);
        double xSpace=5;
        double ySpace=5;
        double y1,y2,y3,y4,mW;
        double awf=LamppViewElement.bestFontSize(Math.min(BUTTONWIDTH,BUTTONHEIGHT));
        y1=(VIEW_SIZE-BUTTONHEIGHT)/2;
        y2=y1+VIEW_SIZE+ySpace;
        y3=y2+VIEW_SIZE+ySpace;
        y4=y3+VIEW_SIZE+ySpace;
        lfs=new LamppOpSelect(0,y1,y2,y3,BUTTONWIDTH,BUTTONHEIGHT,buttonFontSize);
        fieldPane.getChildren().add(lfs);
        String qSymbol="Rational [\u211a]";
        String gSymbol="Gaussian [\u211a(i)]";
        String impSymbol="Int Mod Prime";
        String impSymbol2="Power [\u2124/p\u207f\u2124]";
        switch ( OS ) {
            case "android": {
                qSymbol="Rational";
                gSymbol="Gaussian";
                impSymbol2="Power";
                break;
            }
            case "ios": {
                break;
            }
            default : {
            }
        }
        final Text tf1=new Text(BUTTONWIDTH+xSpace,y1+awf,qSymbol);
        tf1.setFont( new Font(tf1.getFont().getName(), awf ));
        fieldPane.getChildren().add(tf1);
        final Text tf2=new Text(BUTTONWIDTH+xSpace,y2+awf,gSymbol);
        tf2.setFont( new Font(tf1.getFont().getName(), awf ));
        fieldPane.getChildren().add(tf2);
        final Text tf3=new Text(BUTTONWIDTH+xSpace,y3+awf,impSymbol);
        tf3.setFont( new Font(tf1.getFont().getName(), awf ));
        fieldPane.getChildren().add(tf3);
        final Text tf4=new Text(BUTTONWIDTH+xSpace,y3+2*awf+ySpace,impSymbol2);
        tf4.setFont( new Font(tf1.getFont().getName(), awf ));
        fieldPane.getChildren().add(tf4);
        efe=new LamppAMCEButton(w-2*BUTTONWIDTH-2*xSpace,2*ySpace,2*BUTTONWIDTH,3*BUTTONHEIGHT/2,awf,"Set Field",4);
        efe.addPropertyChangeListener(this);    
        fieldPane.getChildren().add(efe);
        modtf=new LamppTextFieldDenominator(String.valueOf(LamppElementIntModP.getModulus()));
        modtf.setFont(new Font(awf));
        modtf.applyCss();
        modtf.layout();
        modtf.setPrefColumnCount(3);
        modtf.setAlignment(Pos.BASELINE_CENTER);
        mW=getNodeDimensions(modtf).getWidth();
        modtf.setTranslateY(y3);
        modtf.setTranslateX(w-mW-2*xSpace);
        modtf.lengthProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable,
                   Number oldValue, Number newValue) {
               if (newValue.intValue() > oldValue.intValue()) {
                   // Check if the new character is greater than MODULUS_SIZE
                   if (modtf.getText().length() >= MODULUS_SIZE) {

                       // if it's 11th character then just setText to previous
                       // one
                       modtf.setText(modtf.getText().substring(0, MODULUS_SIZE));
                   }
               }
           }
        }
        );   
        //if enter keyed for new value in LamppTextFieldDenominator tf
        modtf.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!modtf.isValid()){
                    modtf.setText(String.valueOf(modulus));
                }
                else{
                    modtf.setText(modtf.getText().replaceFirst("^0+(?!$)", ""));
                    if(LamppElementIntModP.isValidModulus(modtf.getLong())){
                        modulus=modtf.getLong();
                        LamppElementIntModP.setModulus(modulus);
                    }
                    else{
                        modulus=LamppElementIntModP.getModulus();
                        modtf.setText(String.valueOf(modulus));
                    }
                }
            }
        }
        );
        //if LamppTextFieldDenominator tf loses focus when editted, check if good value.
        modtf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue){       
        //            System.out.println("Textfield has focus");
                }
                else{
                    if(!modtf.isValid()){
                        modtf.setText(String.valueOf(modulus));
                    }
                    else{
                        modtf.setText(modtf.getText().replaceFirst("^0+(?!$)", ""));
                        if(LamppElementIntModP.isValidModulus(modtf.getLong())){
                            modulus=modtf.getLong();
                            LamppElementIntModP.setModulus(modulus);
                        }
                        else{
                            modulus=LamppElementIntModP.getModulus();
                            modtf.setText(String.valueOf(modulus));
                        }
            //            System.out.println("Textfield out of focus");
                    }
                }
            }
        }
        );
        fieldPane.getChildren().add(modtf);
        final CheckBox cb = new CheckBox("Negatives");
        final Tooltip tooltip = new Tooltip("Press Set to enforce checked/unchecked value");
        tooltip.setFont(new Font("Arial", 16));
        cb.setTooltip(tooltip);
        cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             if(cb.isSelected())LamppElementIntModP.setZeroMiddle();
             else LamppElementIntModP.setZeroStart();
          }
        });
        cb.setTranslateY(y3+modtf.getHeight());
        cb.setTranslateX(w-mW-2*xSpace);
        fieldPane.getChildren().add(cb);    
        LamppKeyboard keyb = new LamppKeyboard();
        keybPane.getChildren().add(keyb.view());
        keybPane.setTranslateY(y3+VIEW_SIZE+ySpace);
        fieldPane.getChildren().add(keybPane);    
        return fieldPane;
    }    
//*******************************************************************************************************************************************
        
    
    public Dimension2D getNodeDimensions(Node control) {
         Stage stage = new Stage();
         Group root = new Group();
         root.getChildren().add(control);
         stage.setScene(new Scene(root));
         stage.show();
         stage.close();
     return new Dimension2D(control.getLayoutBounds().getWidth(),control.getLayoutBounds().getHeight());    
    } 
    public void setMatrixElements(){
        //set operation elements
        switch (field){
            //Rationals
            case 0:{
                lre.setRational(lren,lred);
                if(realneg)lre.additiveInverse();
                if(!lre.isZero()){
                    opElement2.setViewElement(lre);
                    opElement3.setViewElement(lre);
                }
                break;
            }
            //Gaussian
            case 1:{
                lge.setGaussian(lren,lred,lrein,lreid);
                if(!lge.isZero()){
                    opElement2.setViewElement(lge);
                    opElement3.setViewElement(lge);
                }
                break;
            }
            //Integer Modulus a Prime (IntModP)
            case 2:{
                lie.setIntModP(lren);
                if(!lie.isZero()){
                    opElement2.setViewElement(lie);
                    opElement3.setViewElement(lie);
                }
                break;
            }
        }        
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                switch (field){
                    //Rationals
                    case 0:{
                        if(matrix[i][j].isInputSelected()){
                            matrix[i][j].setViewElement(lre);   
                            mRat[i][j][lob.getExecuted()].equals(lre);
                            matrix[i][j].setClearSelected();
                        }     
                        break;
                    }
                    //Gaussian
                    case 1:{
                        if(matrix[i][j].isInputSelected()){
                            matrix[i][j].setViewElement(lge);
                            mGau[i][j][lob.getExecuted()].equals(lge);
                            matrix[i][j].setClearSelected();
                        }
                        break;
                    }
                    //Integer Modulus a Prime (IntModP)
                    case 2:{
                        if(matrix[i][j].isInputSelected()){
                            matrix[i][j].setViewElement(lie);
                            mInt[i][j][lob.getExecuted()].equals(lie);
                            matrix[i][j].setClearSelected();
                        }
                        break;
                    }
                }        
            }
        }
    }
    public void setRealNumerator(long x){lren=x;}
    public void setRealDenominator(long x){lred=x;}
    public void setImagNumerator(long x){lrein=x;}
    public void setImagDenominator(long x){lreid=x;}

    private void changeEditPane(final double fontSize,final double w,final double h){
        double xSpace=5;
        double ySpace=5;
        double yE,xE,yH,xW;
        xW=ntf.getLayoutBounds().getWidth();
        xE=(w-xW)/2;
        yE=ntf.getLayoutBounds().getHeight();
        ySpace=yE/8;
        lren=0;
        lrein=0;
        lred=1;
        lreid=1;
        realneg=false;
        imagneg=false;
        ntf.setText(String.valueOf(lren));
        dtf.setText(String.valueOf(Math.abs(lred)));
        intf.setText(String.valueOf(lrein));
        idtf.setText(String.valueOf(Math.abs(lreid)));
        
        if(field==2){
            ntf.setTranslateX(xE);
            ntf.setTranslateY(VIEW_SIZE/2);
        }
        else {
            if(field==1){xE=w/2-xW-xW/2;}   
            rLine.setStartX(xE);
            rLine.setStartY(VIEW_SIZE/2+ySpace);
            rLine.setEndX(xE+xW);
            rLine.setEndY(VIEW_SIZE/2+ySpace);
            rminus.setTranslateX(xE-2*fontSize);
            ntf.setTranslateY(VIEW_SIZE/2-yE);
            ntf.setTranslateX(xE);
            dtf.setTranslateY(VIEW_SIZE/2+2*ySpace);
            dtf.setTranslateX(xE);
        }        
        switch (field){
            //Rationals
            case 0:{
                rminus.setVisible(false);
                iminus.setVisible(false);
                iplus.setVisible(false);
                rLine.setVisible(true);
                dtf.setVisible(true);
                iLine.setVisible(false);
                ecc.setVisible(false);
                isym.setVisible(false);
                intf.setVisible(false);
                idtf.setVisible(false);
                break;
            }
            //Gaussian
            case 1:{
                rminus.setVisible(false);
                iminus.setVisible(false);
                iplus.setVisible(true);
                rLine.setVisible(true);
                dtf.setVisible(true);
                iLine.setVisible(true);
                ecc.setVisible(true);
                isym.setVisible(true);
                intf.setVisible(true);
                idtf.setVisible(true);
               break;
            }
            //Integer Modulus a Prime (IntModP)
            case 2:{
                dtf.setVisible(false);
                rminus.setVisible(false);
                iminus.setVisible(false);
                iplus.setVisible(false);
                rLine.setVisible(false);
                iLine.setVisible(false);
                ecc.setVisible(false);
                isym.setVisible(false);
                intf.setVisible(false);
                idtf.setVisible(false);
                break;
            }
        }          
    }
    
    private Pane createEditPane(final double fontSize,final double w,final double h) {
        Pane editPane = new Pane();
        final Pane keyboardPane = new Pane();
        editPane.setPrefSize(w,h);
        double xSpace=5;
        double ySpace=5;
        double yE,xE,yH,xW;
        LamppKeyboard keyboard = new LamppKeyboard();
        keyboardPane.getChildren().add(keyboard.view());
        keyboardPane.setTranslateY(2*VIEW_SIZE);
        editPane.getChildren().add(keyboardPane);
        double aw=LamppViewElement.bestFontSize(Math.min(BUTTONWIDTH,BUTTONHEIGHT));
        epi=new LamppAMCEButton(BUTTONWIDTH,5*VIEW_SIZE/4,3*BUTTONWIDTH/2,BUTTONHEIGHT,aw,paneType[1],0);
        eai=new LamppAMCEButton(5*BUTTONWIDTH/2+xSpace,5*VIEW_SIZE/4,3*BUTTONWIDTH/2,BUTTONHEIGHT,aw,paneType[1],1);
        eoe=new LamppAMCEButton(8*BUTTONWIDTH+2*xSpace,5*VIEW_SIZE/4,2*BUTTONWIDTH,3*BUTTONHEIGHT/2,aw,paneType[1],4);
        epi.addPropertyChangeListener(this);
        eai.addPropertyChangeListener(this);
        eoe.addPropertyChangeListener(this);
        ecc=new LamppAMCEButton(4*BUTTONWIDTH+2*xSpace,5*VIEW_SIZE/4,3*BUTTONWIDTH/2,BUTTONHEIGHT,aw,paneType[1],2);
        ecc.addPropertyChangeListener(this);
        if(field==1)ecc.setVisible(true);
        else ecc.setVisible(false);
        editPane.getChildren().addAll(epi,eai,ecc,eoe);
        lre=new LamppElementRational(lren,lred);
        ntf=new LamppTextFieldNumerator(String.valueOf(lren));
        dtf=new LamppTextFieldDenominator(String.valueOf(Math.abs(lred)));
        intf=new LamppTextFieldNumerator(String.valueOf(lrein));
        idtf=new LamppTextFieldDenominator(String.valueOf(Math.abs(lreid)));
        ntf.setPrefColumnCount(8);
        ntf.setAlignment(Pos.BASELINE_CENTER);
        ntf.setFont(new Font(fontSize));
        ntf.applyCss();
        ntf.layout();
        xW=getNodeDimensions(ntf).getWidth();
        xE=(w-xW)/2;
        if(field==2){
            ntf.setTranslateX(xE);
            ntf.setTranslateY(VIEW_SIZE/2);
        }
        yE=getNodeDimensions(ntf).getHeight();
        ySpace=yE/8;
        editPane.getChildren().add(ntf);
        //if enter keyed for new value in LamppTextFieldNumerator tf
        ntf.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!ntf.isValid()){
                    if(!ntf.getText().equalsIgnoreCase("0"))
                    ntf.setText(String.valueOf(Math.abs(lren)));
                }
                else{
                    ntf.setText(ntf.getText().replaceFirst("^0+(?!$)", ""));
                    setRealNumerator(ntf.getLong());
                }
            }
        }
        );
    //if LamppTextFieldNumerator tf loses focus when editted, check if good value.
        ntf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue){       
    //            System.out.println("Textfield on focus");
                }
                else {
                if(!ntf.isValid()){
                    if(!ntf.getText().equalsIgnoreCase(""))ntf.setText("0");
                    else ntf.setText(String.valueOf(Math.abs(lren)));
                }
                else{
                    ntf.setText(ntf.getText().replaceFirst("^0+(?!$)", ""));
                    setRealNumerator(ntf.getLong());
                }
    //            System.out.println("Textfield out focus");
                }
            }
        }
        );
        if(field==1){xE=w/2-xW-xW/2;}    
        rLine=new Line(xE,VIEW_SIZE/2+ySpace,xE+xW,VIEW_SIZE/2+ySpace);
        rminus.setFont(new Font(3*fontSize));
        rminus.setVisible(realneg);
        rminus.setTranslateX(xE-2*fontSize);
        rminus.setTranslateY(VIEW_SIZE/2+fontSize+ySpace/2);
        if(field!=2){
            ntf.setTranslateY(VIEW_SIZE/2-yE);
            ntf.setTranslateX(xE);
        }
        dtf.setPrefColumnCount(8);
        dtf.setAlignment(Pos.BASELINE_CENTER);
        dtf.setTranslateY(VIEW_SIZE/2+2*ySpace);
        dtf.setFont(new Font(fontSize));
        dtf.setTranslateX(xE);
        //if enter keyed for new value in LamppTextFieldDenominator tf
        dtf.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!dtf.isValid()){
                    dtf.setText(String.valueOf(Math.abs(lred)));
                }
                else{
                    dtf.setText(dtf.getText().replaceFirst("^0+(?!$)", ""));
                    setRealDenominator(dtf.getLong());
                }
            }
        }
        );
        //if LamppTextFieldDenominator tf loses focus when editted, check if good value.
        dtf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue)
                {       
        //            System.out.println("Textfield has focus");
                }
                else
                {
                if(!dtf.isValid()){dtf.setText(String.valueOf(Math.abs(lred)));}
                else{dtf.setText(dtf.getText().replaceFirst("^0+(?!$)", ""));
                setRealDenominator(dtf.getLong());}
        //            System.out.println("Textfield out of focus");
                }
            }
        }
        );
        editPane.getChildren().add(rminus);
        editPane.getChildren().add(rLine);
        editPane.getChildren().add(dtf);

        //if enter keyed for new value in LamppTextFieldNumerator tf
        intf.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!intf.isValid()){
                    if(!intf.getText().equalsIgnoreCase("0"))
                    intf.setText(String.valueOf(Math.abs(lrein)));
                }
                else{
                    intf.setText(intf.getText().replaceFirst("^0+(?!$)", ""));
                    setImagNumerator(intf.getLong());
                }
            }
        }
        );
    //if LamppTextFieldNumerator tf loses focus when editted, check if good value.
        intf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue){       
    //            System.out.println("Textfield on focus");
                }
                else {
                if(!intf.isValid()){
                    if(!intf.getText().equalsIgnoreCase(""))intf.setText("0");
                    else intf.setText(String.valueOf(Math.abs(lrein)));
                }
                else{
                    intf.setText(intf.getText().replaceFirst("^0+(?!$)", ""));
                    setImagNumerator(intf.getLong());
                }
    //            System.out.println("Textfield out focus");
                }
            }
        }
        );
        //if enter keyed for new value in LamppTextFieldDenominator tf
        idtf.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent) {
                if(!idtf.isValid()){
                    idtf.setText(String.valueOf(Math.abs(lreid)));
                }
                else{
                    idtf.setText(idtf.getText().replaceFirst("^0+(?!$)", ""));
                    setImagDenominator(idtf.getLong());
                }
            }
        }
        );
        //if LamppTextFieldDenominator tf loses focus when editted, check if good value.
        idtf.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue)
                {       
        //            System.out.println("Textfield has focus");
                }
                else
                {
                if(!idtf.isValid()){idtf.setText(String.valueOf(Math.abs(lreid)));}
                else{idtf.setText(idtf.getText().replaceFirst("^0+(?!$)", ""));
                setImagDenominator(idtf.getLong());}
        //            System.out.println("Textfield out of focus");
                }
            }
        }
        );
        xW=xW/2;
        xE=xE-xW;
        iplus.setTranslateX(xE+xW+fontSize/2);
        iminus.setTranslateX(xE+xW+fontSize/2);
        iminus.setTextAlignment(TextAlignment.CENTER);
        xE=xE+xW+3*fontSize;
        iLine=new Line(xE,VIEW_SIZE/2+ySpace,xE+2*xW,VIEW_SIZE/2+ySpace);
        intf.setPrefColumnCount(8);
        intf.setAlignment(Pos.BASELINE_CENTER);
        intf.setFont(new Font(fontSize));
        idtf.setPrefColumnCount(8);
        idtf.setAlignment(Pos.BASELINE_CENTER);
        idtf.setFont(new Font(fontSize));
        intf.setTranslateX(xE);
        idtf.setTranslateX(xE);
        idtf.setTranslateY(VIEW_SIZE/2+2*ySpace);
        intf.setTranslateY(VIEW_SIZE/2-yE);
        iminus.setVisible(imagneg);
        iplus.setVisible(!imagneg);
        iminus.setFont(new Font(3*fontSize));
        iplus.setTranslateY(VIEW_SIZE/2+fontSize+ySpace/2);
        iplus.setFont(new Font(3*fontSize));
        iminus.setTranslateY(VIEW_SIZE/2+fontSize+ySpace/2);
        isym.setFont(new Font(3*fontSize));
        isym.setTranslateX(xE+2*xW+fontSize/2);
        isym.setTranslateY(VIEW_SIZE/2+fontSize+ySpace/2);
        editPane.getChildren().add(iminus);
        editPane.getChildren().add(iplus);
        editPane.getChildren().add(iLine);
        editPane.getChildren().add(intf);
        editPane.getChildren().add(idtf);
        editPane.getChildren().add(isym);
        switch (field){
            //Rationals
            case 0:{
                rminus.setVisible(realneg);
                iminus.setVisible(false);
                iplus.setVisible(false);
                rLine.setVisible(true);
                iLine.setVisible(false);
                ecc.setVisible(false);
                isym.setVisible(false);
                intf.setVisible(false);
                idtf.setVisible(false);
                break;
            }
            //Gaussian
            case 1:{
                rminus.setVisible(realneg);
                iminus.setVisible(imagneg);
                iplus.setVisible(!imagneg);
                rLine.setVisible(true);
                iLine.setVisible(true);
                ecc.setVisible(true);
                isym.setVisible(true);
                intf.setVisible(true);
                idtf.setVisible(true);
               break;
            }
            //Integer Modulus a Prime (IntModP)
            case 2:{
                rminus.setVisible(false);
                iminus.setVisible(false);
                iplus.setVisible(false);
                rLine.setVisible(false);
                iLine.setVisible(false);
                ecc.setVisible(false);
                isym.setVisible(false);
                intf.setVisible(false);
                idtf.setVisible(false);
                break;
            }
        }           

        /*
    // create elements depending on field
        switch (field){
            //Rationals
            case 0:{
                break;
            }
            //Gaussian
            case 1:{
               break;
            }
            //Integer Modulus a Prime (IntModP)
            case 2:{
                break;
            }
        }  
        */
        return editPane;
    }
    
    
    private Pane createOperationPane(double fontSize,double vfontSize) {
        final Pane operationPane = new Pane();
        double w=5*VIEW_SIZE;
        double h=5*VIEW_SIZE;
        operationPane.setPrefSize(w,h);
        double xSpace=5;
        double ySpace=BUTTONHEIGHT/2;
        double yE,xE,yH,xW;
    // create elements depending on field
        switch (field){
            //Rationals
            case 0:{
                opElement2=new LamppViewRational(1.5*VIEW_SIZE+BUTTONWIDTH/2,VIEW_SIZE+ySpace,VIEW_SIZE,VIEW_SIZE,8,new LamppElementRational(1),1,1,fontSize);
                opElement3= new LamppViewRational(2.5*VIEW_SIZE+BUTTONWIDTH/2,2*(VIEW_SIZE+ySpace),VIEW_SIZE,VIEW_SIZE,8,new LamppElementRational(1),1,1,fontSize);
                break;
            }
            //Gaussian
            case 1:{
                opElement2= new LamppViewGaussian(1.5*VIEW_SIZE+BUTTONWIDTH/2,VIEW_SIZE+ySpace,VIEW_SIZE,VIEW_SIZE,8,new LamppElementGaussian(LamppElementGaussian.one),1,1,fontSize);
                opElement3= new LamppViewGaussian(2.5*VIEW_SIZE+BUTTONWIDTH/2,2*(VIEW_SIZE+ySpace),VIEW_SIZE,VIEW_SIZE,8,new LamppElementGaussian(LamppElementGaussian.one),1,1,fontSize);
                break;
            }
            //Integer Modulus a Prime (IntModP)
            case 2:{
                opElement2= new LamppViewIntModP(1.5*VIEW_SIZE+BUTTONWIDTH/2,VIEW_SIZE+ySpace,VIEW_SIZE,VIEW_SIZE,8,new LamppElementIntModP(LamppElementIntModP.one),1,1,fontSize);
                opElement3= new LamppViewIntModP(2.5*VIEW_SIZE+BUTTONWIDTH/2,2*(VIEW_SIZE+ySpace),VIEW_SIZE,VIEW_SIZE,8,new LamppElementIntModP(LamppElementIntModP.one),1,1,fontSize);
                break;
            }
        }  
        opElement2.setViewOnly(true);
        opElement3.setViewOnly(true);
        opElement2.toggleInputSelected();
        opElement3.toggleInputSelected();
        operationPane.getChildren().addAll(opElement2,opElement3);
        Polygon leftArrow2 = new Polygon();
        double aw=BUTTONWIDTH+0.0;
        double h4=BUTTONHEIGHT/4.0;
        double h2=BUTTONHEIGHT/2.0;
        double h34=3*BUTTONHEIGHT/4.0;
        double w4=BUTTONWIDTH/4.0;
        double w2=BUTTONWIDTH/2.0;
        double w516=5*BUTTONWIDTH/16.0;
        double w8=BUTTONWIDTH/8.0;
        double w332=3*BUTTONWIDTH/32.0;
        leftArrow2.getPoints().addAll(new Double[]{
            0.0,h2,
            w4,h4,
            w2,h4,
            w516,h2-w332,
            aw,h2-w332,
            aw,h2+w332,
            w516,h2+w332,
            w2,h34,
            w4,h34
        });
        Polygon leftArrow3 = new Polygon();
        leftArrow3.getPoints().addAll(new Double[]{
            0.0,h2,
            w4,h4,
            w2,h4,
            w516,h2-w332,
            aw,h2-w332,
            aw,h2+w332,
            w516,h2+w332,
            w2,h34,
            w4,h34
        });
        Polygon leftRightArrow = new Polygon();
        leftRightArrow.getPoints().addAll(new Double[]{
            0.0,h2,
            w4,h4,
            w2,h4,
            w516,h2-w332,
            aw-w516+w332,h2-w332,
            aw-w2+w332,h4,
            aw-w4+w332,h4,
            aw+w332,h2,
            aw-w4+w332,h34,
            aw-w2+w332,h34,
            aw-w516+w332,h2+w332,
            w516,h2+w332,
            w2,h34,
            w4,h34
        });
        leftArrow3.setFill(Color.BLACK);
        leftRightArrow.setFill(Color.BLACK);
        Text star1=new Text("*");
        Text star2=new Text("*");
      
        double xstart=aw+xSpace;
        double vpos=(VIEW_SIZE-BUTTONHEIGHT)/2;
        double y1=0,y2=0,y3=0;
        for (int i=0;i<7;i++){
            switch(i){
                case 0:{
                    leftRightArrow.setTranslateX(xstart+3*w2);
                    leftRightArrow.setTranslateY(vpos);
                    y1=vpos;
                    break;
                }
                case 1:{
                    vpos=vpos+VIEW_SIZE+ySpace;
                    leftArrow2.setTranslateX(xstart+3*w2);
                    leftArrow2.setTranslateY(vpos);
                    y2=vpos;
                    break;
                }
                case 2:{
                    vpos=vpos+VIEW_SIZE+ySpace;
                    leftArrow3.setTranslateX(xstart+3*w2);
                    leftArrow3.setTranslateY(vpos);
                    y3=vpos;
                    break;                
                }
                case 3:{
                    xstart=1.5*VIEW_SIZE+w2;
                    break;                
                }
                case 4:{
                    xstart=2.5*VIEW_SIZE+2*aw+xSpace;
                    vpos=(VIEW_SIZE-BUTTONHEIGHT)/2+VIEW_SIZE+ySpace;
                    break;                
                }
                case 5:{
                    xstart=1.5*VIEW_SIZE+w2;
                    vpos=(VIEW_SIZE-BUTTONHEIGHT)/2;
                    break;                
                }
                case 6:{
                    xstart=3.5*VIEW_SIZE+2*aw+xSpace;
                    vpos=y3;
                    break;                
                }
            }
            vOpButtons[i]=new LamppRCViewButton(xstart,vpos,BUTTONWIDTH,BUTTONHEIGHT,"1",vfontSize);
            if(i<5)vOpButtons[i].setLeftView();
            else vOpButtons[i].setRightView();
            operationPane.getChildren().add(vOpButtons[i]);
        }
        operationPane.getChildren().addAll(leftArrow2,leftArrow3,leftRightArrow);
        
        los=new LamppOpSelect(0,y1,y2,y3,BUTTONWIDTH,BUTTONHEIGHT,buttonFontSize);
//        los.addPropertyChangeListener(this);
        operationPane.getChildren().add(los);
        
        aw=LamppViewElement.bestFontSize(Math.min(BUTTONWIDTH,BUTTONHEIGHT));
        star1.setFont( new Font(star1.getFont().getName(), aw ));
        star2.setFont( new Font(star1.getFont().getName(), aw ));
        h2=0.9*star1.getLayoutBounds().getHeight()/2;
        w2=star1.getLayoutBounds().getWidth();        
        Text plus=new Text("+");
        plus.setFont( new Font(star1.getFont().getName(), aw ));
        h4=plus.getLayoutBounds().getHeight()/4;
        w4=plus.getLayoutBounds().getWidth(); 
        star1.setTranslateX(2.5*VIEW_SIZE+BUTTONWIDTH+xSpace);
        star2.setTranslateX(3.5*VIEW_SIZE+BUTTONWIDTH+xSpace);
        plus.setTranslateX(2.5*VIEW_SIZE-BUTTONWIDTH/2-xSpace);
        star1.setTranslateY(1.5*VIEW_SIZE+ySpace+h2);
        star2.setTranslateY(2*(VIEW_SIZE+ySpace)+VIEW_SIZE/2+h2);
        plus.setTranslateY(2*(VIEW_SIZE+ySpace)+VIEW_SIZE/2+h4);
        operationPane.getChildren().addAll(star1,star2,plus);
        setLeftOpPane("");
        setRightOpPane("");
        LamppAMCEButton pi=new LamppAMCEButton(BUTTONWIDTH,h-VIEW_SIZE,3*BUTTONWIDTH/2,BUTTONHEIGHT,aw,paneType[0],0);
        LamppAMCEButton ai=new LamppAMCEButton(5*BUTTONWIDTH/2+xSpace,h-VIEW_SIZE,3*BUTTONWIDTH/2,BUTTONHEIGHT,aw,paneType[0],1);
        LamppAMCEButton cc=new LamppAMCEButton(4*BUTTONWIDTH+2*xSpace,h-VIEW_SIZE,3*BUTTONWIDTH/2,BUTTONHEIGHT,aw,paneType[0],2);
        LamppAMCEButton oe=new LamppAMCEButton(8*BUTTONWIDTH,h-VIEW_SIZE,3*BUTTONWIDTH,3*BUTTONHEIGHT/2,aw,paneType[0],3);
        pi.addPropertyChangeListener(this);
        ai.addPropertyChangeListener(this);
        oe.addPropertyChangeListener(this);
        cc.addPropertyChangeListener(this);
        operationPane.getChildren().addAll(pi,ai,cc,oe);
        cc.setVisible(false);
        if(field==1){cc.setVisible(true);
        }
        Line l1=new Line(BUTTONWIDTH/2,y2+BUTTONHEIGHT/2-(VIEW_SIZE+ySpace)/2,w-BUTTONWIDTH/2,y2+BUTTONHEIGHT/2-(VIEW_SIZE+ySpace)/2);
        Line l2=new Line(BUTTONWIDTH/2,y3+BUTTONHEIGHT/2-(VIEW_SIZE+ySpace)/2,w-BUTTONWIDTH/2,y3+BUTTONHEIGHT/2-(VIEW_SIZE+ySpace)/2);
        Line l3=new Line(BUTTONWIDTH/2,y3+BUTTONHEIGHT/2+(VIEW_SIZE+ySpace)/2,w-BUTTONWIDTH/2,y3+BUTTONHEIGHT/2+(VIEW_SIZE+ySpace)/2);
        l1.setStrokeWidth(0.1);
        l2.setStrokeWidth(0.1);
        l3.setStrokeWidth(0.1);
        operationPane.getChildren().addAll(l1,l2,l3);
        return operationPane;      
    }  
    private void toggleOpPane(){
        for (int i=0;i<7;i++){vOpButtons[i].toggleRC();}
    }
    private void setLeftOpPane(String v){
        for (int i=0;i<5;i++){vOpButtons[i].setText(v);}
    }
    private void setRightOpPane(String v){
        for (int i=5;i<7;i++){vOpButtons[i].setText(v);}
    }
    
   private Pane createMatrixPane(double fontSize,double vfontSize) {
    if(numRows+numCols<3){numRows=1;numCols=2;}
// create a matrix of elements depending on field
    switch (field){
        //Rationals
        case 0:{
            matrix= new LamppViewRational[numRows][numCols];
            break;
        }
        //Gaussian
        case 1:{
            fontSize=LamppViewElement.bestFontSize(4*fontSize/5);
            matrix= new LamppViewGaussian[numRows][numCols];
            break;
        }
        //Integer Modulus a Prime (IntModP)
        case 2:{
            matrix= new LamppViewIntModP[numRows][numCols];
            break;
        }
    }    
    final Pane matrixPane = new Pane();
    double w=width-VIEW_SIZE;
    double h=height-3*VIEW_SIZE/2;
    matrixPane.setPrefSize(w,h+VIEW_SIZE/2);
    double yE,xE,yH,xW;
    double rH=Math.min(VIEW_SIZE,h/numRows);
    double cW=Math.min(VIEW_SIZE,w/numCols);
    hpos[0]=vpos[0]=xE=VIEW_SIZE/2;    
    for(int j=0; j<numCols;j++){
        yE=VIEW_SIZE/2;
        xW=cW;
        for(int i=0;i<numRows;i++){
            yH=rH;
            switch (field){
                //Rationals
                case 0:{
                    if(numCols==3||numCols==4||numCols==6||numCols==8){
                        if(j>i)matrix[i][j]=new LamppViewRational(xE,yE,xW,yH,8,new LamppElementRational(0,1),i,j,fontSize);
                        else if(i==j)matrix[i][j]=new LamppViewRational(xE,yE,xW,yH,8,new LamppElementRational(1,1),i,j,fontSize);
                        else if((i%3)==(j+1)%2)matrix[i][j]=new LamppViewRational(xE,yE,xW,yH,8,new LamppElementRational(0,1),i,j,fontSize);
                        else matrix[i][j]=new LamppViewRational(xE,yE,xW,yH,8,new LamppElementRational(1,1),i,j,fontSize);
                    }
                    else{
                        if(i==j)matrix[i][j]=new LamppViewRational(xE,yE,xW,yH,8,new LamppElementRational(1,1),i,j,fontSize);
                        else matrix[i][j]=new LamppViewRational(xE,yE,xW,yH,8,new LamppElementRational(0,1),i,j,fontSize);
                    }
                    break;
                }
                //Gaussian
                case 1:{
                    if(i==j)matrix[i][j]=new LamppViewGaussian(xE,yE,xW,yH,8,new LamppElementGaussian(1,1,0,1),i,j,fontSize);
                    else matrix[i][j]=new LamppViewGaussian(xE,yE,xW,yH,8,new LamppElementGaussian(0,1,0,1),i,j,fontSize);
                    break;
                }
                //Integer Modulus a Prime (IntModP)
                case 2:{
                    if(i==j)matrix[i][j]=new LamppViewIntModP(xE,yE,xW,yH,8,new LamppElementIntModP(1),i,j,fontSize);
                    else matrix[i][j]=new LamppViewIntModP(xE,yE,xW,yH,8,new LamppElementIntModP(),i,j,fontSize);
                    break;
                }
            }
            matrix[i][j].addPropertyChangeListener(this);            
            matrixPane.getChildren().add(matrix[i][j]);
            yE=yH+yE;
            vpos[i+1]=yE;
        }
        xE=xW+xE;
        hpos[j+1]=xE;
    }
    vButtons=new LamppVectorButton[numRows];
    hButtons=new LamppVectorButton[numCols];
    double ystart=(VIEW_SIZE/2-BUTTONHEIGHT)/2;
    double xstart=(VIEW_SIZE/2-BUTTONWIDTH)/2;
    for (int i=0;i<numRows;i++){
        vButtons[i]=new LamppVectorButton(xstart,(vpos[i+1]+vpos[i]-(VIEW_SIZE-BUTTONHEIGHT)/2)/2,BUTTONWIDTH,BUTTONHEIGHT/2,String.valueOf(i+1),vfontSize,true);
        vButtons[i].addPropertyChangeListener(this);
        vButtons[i].setViewOnly(!rowOperation);
        matrixPane.getChildren().add(vButtons[i]);
    }
    for (int i=0;i<numCols;i++){
        hButtons[i]=new LamppVectorButton(hpos[i]+(VIEW_SIZE-BUTTONWIDTH/2)/2,ystart,BUTTONWIDTH/2,BUTTONHEIGHT,String.valueOf(i+1),vfontSize,true);
        hButtons[i].addPropertyChangeListener(this);
        hButtons[i].setViewOnly(rowOperation);
        matrixPane.getChildren().add(hButtons[i]);
    }
    
    return matrixPane;    
/*   template for working with different field cases 
    switch (field){
        //Rationals
        case 0:{
            break;
        }
        //Gaussian
        case 1:{
            break;
        }
        //Integer Modulus a Prime (IntModP)
        case 2:{
            break;
        }
    }
 */   
  }
    private long rowHeight(int row){
       //set varying height sizes of elements depending on how many rows on screen
       long eSize=VIEW_SIZE;
       switch (numRows){
           case 7:{if(row==0||row==6)eSize=100;else eSize=99; break;}
           case 8:{if(row==7)eSize=86;else eSize=87; break;}
           case 9:{if(row==0||row==8)eSize=78;else eSize=77; break;}
           case 10:{if(row<3||row>8)eSize=70;else eSize=69; break;}
       }
       return eSize;
    }
    private long columnWidth(int column){
       //set varying width sizes of elements depending on how many columns on screen
       long eSize=VIEW_SIZE;
       return eSize;
    }  
    private void removeMatrix(){
//        setClearAll();
        for(int j=0; j<numCols;j++){
            for(int i=0;i<numRows;i++){
                matrix[i][j].removePropertyChangeListener(this);  
            }
        }    
        for(int v=0;v<numRows;v++){
            vButtons[v].removePropertyChangeListener(this); 
        }
        for(int i=0;i<numCols;i++){
            hButtons[i].removePropertyChangeListener(this); 
        }
//        matrixPane.getChildren().clear();
    }
   
    private void toggleLeftSelected(int v){
        if(rowOperation){if(matrix[v][0].isRightSelected()){setRightOpPane("");}}    
        if(!rowOperation){if(matrix[0][v].isRightSelected()){setRightOpPane("");}}  
        if(rowOperation){if(!matrix[v][0].isLeftSelected()){setClearLeftAll();setLeftOpPane(""+(v+1));}else setLeftOpPane("");}    
        if(!rowOperation){if(!matrix[0][v].isLeftSelected()){setClearLeftAll();setLeftOpPane(""+(v+1));}else setLeftOpPane("");}  
        setClearInputAll();
        if(rowOperation){
            for(int i=0;i<numCols;i++){
                matrix[v][i].toggleLeftSelected();
            }
            for(int i=0;i<numRows;i++)if(i!=v){
                vButtons[i].setClearLeft();
            }
            leftSelected=vButtons[v].isLeftSelected();
        }
        else{
            for(int i=0;i<numRows;i++){
                matrix[i][v].toggleLeftSelected();
            }
            for(int i=0;i<numCols;i++)if(i!=v&&hButtons[i].isLeftSelected())hButtons[i].setClearSelected();
            leftSelected=hButtons[v].isLeftSelected();
        }
        if(leftSelected)leftVector=v;
    }
    private void toggleRightSelected(int v){
        if(rowOperation){if(matrix[v][0].isLeftSelected()){setLeftOpPane("");}}    
        if(!rowOperation){if(matrix[0][v].isLeftSelected()){setLeftOpPane("");}}  
        if(rowOperation){if(!matrix[v][0].isRightSelected()){setClearRightAll();setRightOpPane(""+(v+1));}else setRightOpPane("");}
        if(!rowOperation){if(!matrix[0][v].isRightSelected()){setClearRightAll();setRightOpPane(""+(v+1));}else setRightOpPane("");}   
        setClearInputAll();
        if(rowOperation){
            for(int i=0;i<numCols;i++){
                matrix[v][i].toggleRightSelected();
            }
            for(int i=0;i<numRows;i++)if(i!=v&&vButtons[i].isRightSelected())vButtons[i].setClearSelected();
            rightSelected=vButtons[v].isRightSelected();
        }
        else{
            for(int i=0;i<numRows;i++){
                matrix[i][v].toggleRightSelected();
            }
            for(int i=0;i<numCols;i++)if(i!=v&&hButtons[i].isRightSelected())hButtons[i].setClearSelected();
            rightSelected=hButtons[v].isRightSelected();
        }
        if(rightSelected)rightVector=v;        
    }
    private void setClearSelected(int v){
        if(rowOperation){
            for(int i=0;i<numCols;i++){
                matrix[v][i].setClearSelected();
            }
        }
        else{
            for(int i=0;i<numRows;i++){
                matrix[i][v].setClearSelected();
            }
        }
    }
    private void setClearAll(){
        setVClearAll();
        setClearInputAll();
    }
    private void setVClearAll(){
        setClearLeftAll();
        setClearRightAll();
        for(int v=0;v<numRows;v++){
            vButtons[v].setClearSelected();
        }
        for(int i=0;i<numCols;i++){
            hButtons[i].setClearSelected();
        }
    }
    private void setVClearRightAll(){
        setClearRightAll();
        for(int v=0;v<numRows;v++){
            if(vButtons[v].isRightSelected())vButtons[v].setClearSelected();
        }
        for(int i=0;i<numCols;i++){
            if(hButtons[i].isRightSelected())hButtons[i].setClearSelected();
        }
    }
    private void setClearLeftAll(){
        leftSelected=false;
        leftVector=-1;
        setLeftOpPane("");
        for(int v=0;v<numRows;v++){
            for(int i=0;i<numCols;i++){
                if(matrix[v][i].isLeftSelected())matrix[v][i].setClearSelected();
            }
        }
    }
    private void setClearRightAll(){
        rightSelected=false;
        rightVector=-1;
        setRightOpPane("");
        for(int v=0;v<numRows;v++){
            for(int i=0;i<numCols;i++){
                if(matrix[v][i].isRightSelected())matrix[v][i].setClearSelected();
            }
        }
    }
     private void setClearInputAll(){
        for(int v=0;v<numRows;v++){
            for(int i=0;i<numCols;i++){
                if(matrix[v][i].isInputSelected())matrix[v][i].toggleInputSelected();
            }
        }
    }
     

     
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        String text=evt.getPropertyName();
        if(  text.equals(LamppViewElement.eventName[1])){
            //send value to operation pane
            LamppViewElement srce = (LamppViewElement)evt.getSource();
            if(!matrix[srce.getMatrixRow()][srce.getMatrixColumn()].isZero()){
                opElement2.setViewElement(matrix[srce.getMatrixRow()][srce.getMatrixColumn()].getViewElement());
                opElement3.setViewElement(matrix[srce.getMatrixRow()][srce.getMatrixColumn()].getViewElement());
             }
        } 
        else if(text.equals(LamppViewElement.eventName[0])){ 
            //send value to edit pane
            LamppViewElement srce = (LamppViewElement)evt.getSource();
            switch (field){
                //Rationals
                case 0:{
                    lre.equals(mRat[srce.getMatrixRow()][srce.getMatrixColumn()][lob.getExecuted()]);
                    realneg=lre.isNegative();
                    lren=Math.abs(lre.getNumerator());
                    lred=lre.getDenominator();
                    ntf.setText(String.valueOf(lren));
                    dtf.setText(String.valueOf(lred));
                    rminus.setVisible(realneg);
                    break;
                }
                //Gaussian
                case 1:{
                    lge.equals(mGau[srce.getMatrixRow()][srce.getMatrixColumn()][lob.getExecuted()]);
                    realneg=lge.getReal().isNegative();
                    imagneg=lge.getImaginary().isNegative();
                    lren=lge.getReal().getNumerator();
                    lred=lge.getReal().getDenominator();
                    ntf.setText(String.valueOf(Math.abs(lren)));
                    dtf.setText(String.valueOf(lred));
                    lrein=lge.getImaginary().getNumerator();
                    lreid=lge.getImaginary().getDenominator();
                    intf.setText(String.valueOf(Math.abs(lrein)));
                    idtf.setText(String.valueOf(lreid));
                    rminus.setVisible(realneg);
                    iminus.setVisible(imagneg);
                    iplus.setVisible(!imagneg);
                    break;
                }
                //Integer Modulus a Prime (IntModP)
                case 2:{
                    lie.equals(mInt[srce.getMatrixRow()][srce.getMatrixColumn()][lob.getExecuted()]);
                    lren=Math.abs(lie.getResidue());
                    ntf.setText(String.valueOf(lren));
                    break;
                }
            } 
        }
        else if(text.equals(LamppViewElement.eventName[2])){
            // input selected for matrix element
            setVClearAll();
        }
        else if(text.equals(LamppVectorButton.eventName[0])){
            // Left selected for matrix row or column
            toggleLeftSelected(((LamppVectorButton)evt.getSource()).getMatrixPosition());
        }
        else if(text.equals(LamppVectorButton.eventName[2])){
            // Right selected for matrix row or column
            toggleRightSelected(((LamppVectorButton)evt.getSource()).getMatrixPosition());
        }
        else if(text.equals(LamppRCSelectButton.eventName)){
            // toggle row/column operations
            rowOperation=!rowOperation;
            setClearAll();
            toggleOpPane();
            for(int v=0;v<numRows;v++){
                vButtons[v].setViewOnly(!rowOperation);
            }
            for(int i=0;i<numCols;i++){
                hButtons[i].setViewOnly(rowOperation);
            }
        }
        else if (text.equals(paneType[0])){
            //operation pane button events
            // no execution if more than allowed number of operations
            if(!lob.canAdd()||!goodTableaux)return;
            
            String old=(String)evt.getOldValue();
            if(old.equals(LamppAMCEButton.eventName[0])){
                //additive inverse
                opElement2.additiveInverse();
                opElement3.additiveInverse();
            }
            else if(old.equals(LamppAMCEButton.eventName[1])){
                //multiplicative inverse
                opElement2.multiplicativeInverse();
                opElement3.multiplicativeInverse();
            }
            else if(old.equals(LamppAMCEButton.eventName[2])){
                //complex conjugate
                opElement2.conjugate();
                opElement3.conjugate();
            }
            else if(old.equals(LamppAMCEButton.eventName[3])){
                //execute selected row or column op
                if(!leftSelected)return;
                if (rightSelected||(los.selected==2)){
                    if(lob.canAdd()){
                        lob.opAdded();
                        op();
                        storeMatrix();    
                    }
                }
                if (graphType>2&&graphType<5){
                    if(graphType==3||graphType==4)rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
                    if(graphType==4){rP[1]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);rP[1].setColumnOffset(4);}
                    setxyPoints();
                }
                drawGraph();               
            }
        }
        else if (text.equals(paneType[1])){
            //edit pane 
            String old=(String)evt.getOldValue();
            //this is here to force any focussed textfield with new values to lose focus and set long value
            root.requestFocus();
            if(old.equals(LamppAMCEButton.eventName[0])){
                //additive inverse
                switch (field){
                    case 0:{
                        realneg=!realneg;
                        rminus.setVisible(realneg);
                        break;
                    }
                    case 1:{
                        realneg=!realneg;
                        rminus.setVisible(realneg);
                        imagneg=!imagneg;
                        iplus.setVisible(!imagneg);
                        iminus.setVisible(imagneg);
                        lge.additiveInverse();
                        lren=-lren;
                        lrein=-lrein;
                        break;
                    }
                    case 2:{
                        lie.setIntModP(lren);
                        lie.additiveInverse();
                        lren=lie.getResidue();
                        ntf.setText(String.valueOf(lren));
                        break;
                    }
                }                
            }
            else if(old.equals(LamppAMCEButton.eventName[1])){
                //multiplicative inverse
                switch (field){
                    case 0:{
                        lre.setRational(lren,lred);
                        lre.multiplicativeInverse();
                        lren=lre.getNumerator();
                        ntf.setText(String.valueOf(lren));                        
                        lred=lre.getDenominator();
                        dtf.setText(String.valueOf(lred));                        
                        break;
                    }
                    case 1:{
                        lge.setGaussian(lren,lred,lrein,lreid);
                        lge.multiplicativeInverse();
                        lren=lge.getReal().getNumerator();
                        realneg=lren<0;
                        lred=lge.getReal().getDenominator();
                        lrein=lge.getImaginary().getNumerator();
                        imagneg=lrein<0;
                        lreid=lge.getImaginary().getDenominator();
                        rminus.setVisible(realneg);
                        iplus.setVisible(!imagneg);
                        iminus.setVisible(imagneg);
                        ntf.setText(String.valueOf(Math.abs(lren)));                        
                        dtf.setText(String.valueOf(lred));                        
                        intf.setText(String.valueOf(Math.abs(lrein)));                        
                        idtf.setText(String.valueOf(lreid));                        
                        break;
                    }
                    case 2:{
                        lie.setIntModP(lren);
                        lie.multiplicativeInverse();
                        lren=lie.getResidue();
                        ntf.setText(String.valueOf(lren));                        
                        break;
                    }
                }                                
            }
            else if(old.equals(LamppAMCEButton.eventName[2])){
                //complex conjugate
                switch (field){
                    case 0:{
                        break;
                    }
                    case 1:{
                        if(lrein!=0L){
                        imagneg=!imagneg;
                        iplus.setVisible(!imagneg);
                        iminus.setVisible(imagneg); 
                        lrein=-lrein;
                        lge.conjugate();
                        }
                        break;
                    }
                    case 2:{
                        break;
                    }
                }                                
            }
            else if(old.equals(LamppAMCEButton.eventName[4])){
                //populate selected entries
                setMatrixElements();               
                if (graphType>2&&graphType<5){
                    if(graphType==3||graphType==4)rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
                    if(graphType==4){rP[1]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);rP[1].setColumnOffset(4);}
                    setxyPoints();
                    setShowpoly();
                }
            }
//            resetGraphButtons();
            drawGraph();
        }
        else if (text.equals(LamppOpButton.eventName)){
            //set matrix equal to stored value, uses lob.getExecuted() as index
                setMatrix();
                setGraphType();
                if (graphType>2&&graphType<5){
                    if(graphType==3||graphType==4)rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
                    if(graphType==4){rP[1]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);rP[1].setColumnOffset(4);}
                    if(rP[0]!=null)setBoxOutline();
                    setxyPoints();
                    setShowpoly();
                }
                resetGraphButtons(false);
                if (graphType>2&&graphType<5){
                    rP[0].zoom(originX,diagonalY);
                    if(graphType==4){rP[1].zoom(originX,diagonalY);rP[1].setColumnOffset(4);}
                    setxyPoints();
                }                     
                if(lspg.isOpen())drawGraph();
        }
        else if (text.equals("Set Field")){
            if(lspv.isOpen())lvb.fire();
            switch ( OS ) {
                case "android": {
                    field=lfs.selected-1;
                    removeMatrix();
                    matPane=createMatrixPane(elementFontSize,vecFontSize);
                    matrixPane.setContent(matPane);
                    opPane=createOperationPane(elementFontSize,vecFontSize);
                    lspv.setContent(opPane);
                    changeEditPane(elementFontSize,6*VIEW_SIZE,height-3*VIEW_SIZE/2);
                    lsph.setContent(ePane);
                    lob.reset();
                    storeMatrix();
                    lob.fire(); 
                    mod="";
                    if(field==2)mod=String.valueOf(LamppElementIntModP.modulus);
                    lbf.setName("Field",FIELDS[field]+mod,"","");              
                    lbf.fire();
                    break;
                }
                case "ios": {
    //                break;
                }
                default : {
                    //set field to new value after giving warning
                    Alert fieldAlert = new Alert(AlertType.CONFIRMATION);
                    fieldAlert.setTitle("Field Change Requested");
                    fieldAlert.setHeaderText("Changing the Field will overwrite all matrices.");
                    fieldAlert.setContentText("Ok to proceed?");
                    Optional<ButtonType> result = fieldAlert.showAndWait();
                    if (result.get() == ButtonType.OK){
                            // ... user chose OK                     
                        field=lfs.selected-1;
                        removeMatrix();
                        matPane=createMatrixPane(elementFontSize,vecFontSize);
                        matrixPane.setContent(matPane);
                        opPane=createOperationPane(elementFontSize,vecFontSize);
                        lspv.setContent(opPane);
                        changeEditPane(elementFontSize,6*VIEW_SIZE,height-3*VIEW_SIZE/2);
                        lsph.setContent(ePane);
                        lob.reset();
                        storeMatrix();
                        lob.fire(); 
                        mod="";
                        if(field==2)mod=String.valueOf(LamppElementIntModP.modulus);
                        lbf.setName("Field",FIELDS[field]+mod,"","");              
                        lbf.fire();
                    } 
                    else {
                    // ... user chose CANCEL or closed the dialog
                    } 
                }                
            }
            setGraphType();   
            changeGraphPane(gPane);
            lspg.setContent(gPane);
            setShowpoly();
            resetGraphButtons(true);
            drawGraph();
            mcfe.setExitedView();
            efe.setExitedView();
            root.requestFocus();
        }
        else if (text.equals("Set Matrix")){
            root.requestFocus();
            if(lspv.isOpen())lvb.fire();
            switch ( OS ) {
                case "android": {
                    removeMatrix();
                    numCols=(int)numSelectedCols;
                    numRows=(int)numSelectedRows;
                    matPane=createMatrixPane(elementFontSize,vecFontSize);
                    matrixPane.setContent(matPane);
                    lob.reset();
                    storeMatrix();
                    lob.fire(); 
                    lbm.setName("Matrix",topLeft,""+numRows,""+numCols);              
                    lbm.fire();
                    break;
                }
                case "ios": {
    //                break;
                }
                default : {
                    //set field to new value after giving warning
                    Alert matrixAlert = new Alert(AlertType.CONFIRMATION);
                    matrixAlert.setTitle("Matrix Change Requested");
                    matrixAlert.setHeaderText("Changing the Matrix will overwrite all entries.");
                    matrixAlert.setContentText("Ok to proceed?");
                    Optional<ButtonType> result = matrixAlert.showAndWait();
                    if (result.get() == ButtonType.OK){
                            // ... user chose OK
                        removeMatrix();
                        numCols=(int)numSelectedCols;
                        numRows=(int)numSelectedRows;
                        matPane=createMatrixPane(elementFontSize,vecFontSize);
                        matrixPane.setContent(matPane);
                        lob.reset();
                        storeMatrix();
                        lob.fire(); 
                        lbm.setName("Matrix",topLeft,""+numRows,""+numCols);              
                        lbm.fire();
                    } 
                    else {
                        // ... user chose CANCEL or closed the dialog
                    }     
                }
            }
            setGraphType();   
            changeGraphPane(gPane);
            lspg.setContent(gPane);
            setShowpoly();
            resetGraphButtons(true);
            drawGraph();
            mcfe.setExitedView();
            root.requestFocus();
        }
        else if (text.equals("Graph")){
            //do not create graph if closing
            if(!lspg.isOpen())return;
            //select graph type
            switch (field){
                case 0:{
                    if(numCols==3||numCols==6){
                        
                        break;
                    }
                    else if(numCols==4||numCols==8){
                    //graph lines or planes
                        rP[0]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);
                        if(numCols==8){rP[1]=new LamppCuboid(numRows,numCols,lob.getExecuted(),mRat,pin);rP[1].setColumnOffset(4);}  
                        setShowpoly();            
                        rP[0].zoom(originX,diagonalY);                        
                        if(numCols==8){rP[1].zoom(originX,diagonalY);rP[1].setColumnOffset(4);}
                        setxyPoints();
                        drawGraph();
                        break;
                    }
                }
                case 1:{

                }
                case 2:{
                //give message of what graphs are possible
                    
                }
            }
        }
        else {
            
System.out.println("Unregistered event: "+text);
//this is here to force no unintended editting of other pane textfields
//include this for Field and Matrix panes 
root.requestFocus();
//close all other panes if graph pane selected
//close opPane, graphPane and fieldPane if matrixPane selected
//close opPane, graphPane and MatrixPane if fieldPane selected
//close fieldPane and matrixPanes after using to select new environments
        }
    }
    private void op(){
    //routine for executing a row/column operation  
        switch (los.selected){
            case 1:{
                if(rowOperation){
                    for (int i=0;i<numCols;i++){matrix[leftVector][i].op1(matrix[rightVector][i]);}
                }
                else {
                    for (int i=0;i<numRows;i++){matrix[i][leftVector].op1(matrix[i][rightVector]);}
                }
                break;
            }
            case 2:{
                if(rowOperation){
                    for (int i=0;i<numCols;i++){matrix[leftVector][i].op2(opElement2);}
                }
                else {
                    for (int i=0;i<numRows;i++){matrix[i][leftVector].op2(opElement2);}
                }
                break;
            }
            case 3:{
                if(rowOperation){
                    for (int i=0;i<numCols;i++){matrix[leftVector][i].op3(matrix[rightVector][i],opElement3);}
                }
                else {
                    for (int i=0;i<numRows;i++){matrix[i][leftVector].op3(matrix[i][rightVector],opElement3);}
                }
            }
        }
    }
    private void storeMatrix(){
    //routine for storing a copy of the matrix after a row/column op
        int i=lob.getExecuted();
        ops[0][i]=los.selected;
        ops[1][i]=leftVector;
        ops[2][i]=rightVector;
        opTypes[i]=rowOperation;
        switch (field){
            case 0:{
                opsR[i]=(LamppElementRational)opElement2.getViewElement();
                break;
            }
            case 1:{
                opsG[i]=(LamppElementGaussian)opElement2.getViewElement();
                break;
            }
            case 2:{
                opsIMP[i]=(LamppElementIntModP)opElement2.getViewElement();
                break;
            }
        }
        for (int j=0;j<numRows;j++){
            for (int k=0;k<numCols;k++){
                switch (field){
                    case 0:{
                        if(mRat[j][k][i]==null)mRat[j][k][i]=new LamppElementRational();
                        mRat[j][k][i].equals((LamppElementRational)matrix[j][k].getViewElement());
                        break;
                    }
                    case 1:{
                        if(mGau[j][k][i]==null)mGau[j][k][i]=new LamppElementGaussian();
                        mGau[j][k][i].equals((LamppElementGaussian)matrix[j][k].getViewElement());
                        break;
                    }
                    case 2:{
                        if(mInt[j][k][i]==null)mInt[j][k][i]=new LamppElementIntModP();
                        mInt[j][k][i].equals((LamppElementIntModP)matrix[j][k].getViewElement());
                        break;
                    }
                }
                if(!matrix[j][k].isGoodValue())goodTableaux=false;
            }
        }  
    }
    private void setMatrix(){
    //routine for resetting the matrix after a row/column op is executed
        int i=lob.getExecuted();
        los.setSelected(ops[0][i]);
        if(rowOperation!=opTypes[i]){lrcb.flipToggle();}
        setVClearAll();
        leftVector=ops[1][i];
        rightVector=ops[2][i];
        if(leftVector>=0)leftSelected=true;
        if(rightVector>=0)rightSelected=true;
        if(ops[1][i]>=0){
            if(rowOperation){
                vButtons[ops[1][i]].fire(LamppVectorButton.eventName[0]);
                vButtons[ops[1][i]].setLeftSelected();
            }
            else {
                hButtons[ops[1][i]].fire(LamppVectorButton.eventName[0]);
                hButtons[ops[1][i]].setLeftSelected();
            }
        }
        if(ops[2][i]>=0){
            if(rowOperation){
                vButtons[ops[2][i]].fire(LamppVectorButton.eventName[2]);
                vButtons[ops[2][i]].setRightSelected();
            }
            else {
                hButtons[ops[2][i]].fire(LamppVectorButton.eventName[2]);
                hButtons[ops[2][i]].setRightSelected();
            }
        }
        switch (field){
            case 0:{
                opElement2.setViewElement(opsR[i]);
                opElement3.setViewElement(opsR[i]);
                break;
            }
            case 1:{
                opElement2.setViewElement(opsG[i]);
                opElement3.setViewElement(opsG[i]);
                break;
            }
            case 2:{
                opElement2.setViewElement(opsIMP[i]);
                opElement3.setViewElement(opsIMP[i]);
                break;
            }
        }
        goodTableaux=true;
        leftVector=ops[1][i];
        rightVector=ops[2][i];
        if(leftVector>=0)leftSelected=true;
        if(rightVector>=0)rightSelected=true;        
        for (int j=0;j<numRows;j++){
            for (int k=0;k<numCols;k++){
                switch (field){
                    case 0:{
                        matrix[j][k].setViewElement(mRat[j][k][i]);
                        break;
                    }
                    case 1:{
                        matrix[j][k].setViewElement(mGau[j][k][i]);
                        break;
                    }
                    case 2:{
                        matrix[j][k].setViewElement(mInt[j][k][i]);
                        break;
                    }
                }       
                if(i==(lob.latestOp-1)&&matrix[j][k].isLeftSelected())matrix[j][k].rect.setFill(Color.BLUE);
            }
        }
    }

	
	
    public void addPropertyChangeListener(PropertyChangeListener l){
        changes.addPropertyChangeListener(l);
        }
    public void removePropertyChangeListener(PropertyChangeListener l){
        changes.removePropertyChangeListener(l);
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
	
	private void closeLampp(){
		setLamppState();
	}

	private void getLamppState(){ // read existing state from lamppdata.txt in device app directory
/* begin test IO stuff **************************************************************************
  		System.out.println("File name: " + userData.getName());  //test IO
		System.out.println("Absolute path: " + userData.getAbsolutePath());  //test IO
		System.out.println("Writeable: " + userData.canWrite());  //test IO
		System.out.println("Readable " + userData.canRead());  //test IO
		System.out.println("File size in bytes " + userData.length());  //test IO
*/         
		Scanner myReader = null;
		try{
			myReader = new Scanner(userData);	
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				// parse userData from lamppdata file to set program initial state level
				if(data.substring(0,7).equals("%LEVEL ")){
					String substr = data.substring(7,8);
					try{
						level = Integer.parseInt(substr);
					}
					catch (NumberFormatException ex){
						System.out.println("Invalid String:"+substr+":");
						ex.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred trying to read from lamppdata.txt.");
			e.printStackTrace();
		} finally {
			myReader.close();
		}
	}
	
	private void setLamppState(){ // write existing state to lamppdata.txt in device app directory
		FileWriter fr = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		try {
			fr = new FileWriter(userData);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br);
			pr.println("%Lampp version "+LAMPPVERSION);
			pr.println("%last run "+lastDate.toString()+" at "+lastTime.toString());
			pr.println("%LEVEL "+level);				
			pr.println("%* execution levels");
			pr.println("%* LEVEL=1 - base: no file saving");
			pr.println("%* LEVEL=2 - matrix manipulation: read/write matrices to storage, matrix addition, matrix multiplication, transpose, inverse");
			pr.println("%* LEVEL=2 - replace M (showing matrix) with MA, MB, MC, AM, BM or CM");
			pr.println("%* LEVEL=3 - calculate determinant and send to edit pane. Multiply M by edit pane scalar value.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }  
}

