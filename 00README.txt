Github documentation and hosting, stored in:
C:\Users\tom\Documents\GitHub\LAMPP_HTML

LAMPP is two things:

Linear Algebra Matrix aPP: an application that runs on Linux, Windows, Android (soon?), and iOS (eventually?). It is used to work out LA problems and exercises. The UI was designed to resemble an old-fashioned lecture room whiteboard.

Linear Algebra for Mouse, Pad and Pen: a textbook on Linear Algebra designed for high school students and people who have been away from mathematics for awhile. Knowledge of calculus or analytic geometry is not assumed.

The two are designed to be used together in that the LAMPP app capabilities are unlocked from keys given in the book. For example, matrix inverses are evaluated using row operations before the necessary information is given to allow LAMPP to calculate inverses with one button.

 The LAMPP app is designed to use several different number fields: the rational, the gaussian (complex with rational coefficients) and integers modulus a specified prime. Initially students use row operations to accomplish tasks. A limited graphing capability is also included to give a "rough" idea of how one might associate geometry with matrices. The impetus is to eliminate the mistake-prone arithmetic used when doing matrix calculations by hand.
 
 HTML files kept here are intended as an extra source of pictures and animations to illustrate some of the concepts introduced in the LAMPP book. This site may be accessed by a browser or by activating the mouse icon on the lower right of the LAMPP app screen.
  
 After installing Maven, a JDK and JavaFX, the simplest two commands (to create a jar file and run it without warnings in Linux) are:
mvn package
java  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.graphics,javafx.web -jar ./target/Lampp-0.0.1-SNAPSHOT.jar

TODO: set up easy download and setup of working program
    : create native image versions for Linux and Windows
    : create versions for Mac
    : create Android version and despatch to Google store
    : create iPad version and despatch to Apple store


Minimum source directory structure and files needed to create Lampp.
Note that index.html is a copy of linalg.html for default access by a browser
       
+---src
|   \---main
|       +---java
|       |   \---ca
|       |       \---linalg
|       |           \---lampp
|       |                   Lampp.java
|       |                   LamppAMCEButton.java
|       |                   LamppBrowser.java
|       |                   LamppBrowserWrapper.java
|       |                   LamppButton.java
|       |                   LamppConvexPolygon.java
|       |                   LamppCuboid.java
|       |                   LamppEditList.java
|       |                   LamppElementGaussian.java
|       |                   LamppElementIntModP.java
|       |                   LamppElementRational.java
|       |                   LamppElementRational3tuple.java
|       |                   LamppGlobal.java
|       |                   LamppHelpPane.java
|       |                   LamppKey.java
|       |                   LamppKeyboard.java
|       |                   LamppLauncher.java
|       |                   LamppMatrixButton.java
|       |                   LamppOpButton.java
|       |                   LamppOpSelect.java
|       |                   LamppRCSelectButton.java
|       |                   LamppRCViewButton.java
|       |                   LamppScrollPane.java
|       |                   LamppSlidingPaneH.java
|       |                   LamppSlidingPaneV.java
|       |                   LamppTextFieldDenominator.java
|       |                   LamppTextFieldNumerator.java
|       |                   LamppVectorButton.java
|       |                   LamppVerticalButton.java
|       |                   LamppViewElement.java
|       |                   LamppViewGaussian.java
|       |                   LamppViewIntModP.java
|       |                   LamppViewRational.java
|       |                   LamppViewText.java
|       |                   
|       \---resources
|           |   hide-collapse-50.png
|           |   hide-collapse-r-50.png
|           |   hide-collapse-r.png
|           |   hide-collapse.png
|           |   icon.png
|           |   Lampp.css
|           |   show-expand-50.png
|           |   show-expand-r-50.png
|           |   show-expand-r.png
|           |   show-expand.png
|           |   
|           \---html
|                   index.html
|                   linalg.css
|                   linalg.html
                    img.tar.gz   ---  needs to be uncompressed here [tar -xvzf img.tar.gz] or [7-Zip] --- too many files for GitHub directory
                                 ---  other html and png files
|
+---android
|       Lampp.apk
|
+---shade
|       Lampp-project.jar  ---  too large to include in GitHub directory -- see 000readme.txt for fatpom file to create with maven
|       


BASH commands for uncompressing png files used in help html file
cd to created Lampp directory
PATH_TO_PROJ=$PWD
cd $PATH_TO_PROJ/src/main/resources/html
tar -xvzf img.tar.gz
cd $PATH_TO_PROJ/target/classes/html
tar -xvzf img.tar.gz
cd $PATH_TO_PROJ

* in the java source code for each class, use the following package instruction to set the directory tree:
package ca.linalg.lampp;
* the OS directory structure must conform to this specification given in the code header.


* follow directory structure for IntelliJ IDEa IdeaProjects
* choose project directory, for example:
set PATH_TO_PROJ=%cd%

* to edit, compile and execute
* cd to the project directory top level
* echo %PATH_TO_PROJ%
cd %PATH_TO_PROJ%

* to edit source files, use the files in: %PATH_TO_PROJ%\src\main\java\ca\linalg\lampp

* if not already set as environment variable (echo %PATH_TO_FX%) to where the javafx library jar files are stored:
set PATH_TO_FX="C:\Program Files\Java\javafx-sdk-21\lib"

********* According to advice from the INTERNET:
* Android requires a fork of Graalvm due to some minor change that the Graal team didn't want to include upstream. There is also something broken with the bundled config of newer JavaFX versions, so I usually use
*     Windows/Linux/macOS: BellSoft Liberica NIK-full 23 (JDK 21)) + GraalVM Maven plugin
*     Android & iOS: Gluon 22.1.0.1 (JDK17) + Gluon Maven plugin
* The Gluon one works for Linux as well, but I find the NIK a bit easier to use as it's closer to the stock GraalVM.
********* 



* to build and execute from the command line interface (CLI):
* from Lampp directory (eg. in Windows "cd %PATH_TO_PROJ%"):
javac -d target\classes --module-path "C:\Program Files\Java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.web src\main\java\ca\linalg\lampp\*.java
java -cp target\classes --module-path "C:\Program Files\Java\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.web  ca.linalg.lampp.Lampp

*
* or
javac -d target\classes --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.web src\main\java\ca\linalg\lampp\*.java
java -cp target\classes --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp
java -cp target/classes --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp
*
* NOTE: the file that lists the dependency order necessary to initially compile Lampp: javac_compile_order.txt
*
* subdirectory that contains the java source files: src\main\java\ca\linalg\lampp
* subdirectory that will contain the class files: target\classes\ca\linalg\lampp 
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




### Windows - use zulu java binary with builtin javafx modules (no need to add-modules or specify PATH_TO_FX)

    set JAVA_HOME="C:\Program Files\Java\zulu17.40.19-ca-fx-jdk17.0.6-win_x64\bin"
    cd %PATH_TO_PROJ%
    set PATH_TO_FX="C:\Program Files\Java\javafx-sdk-21\lib"
    set out="target\classes"

    dir /s /b src\main\java\ca\lampp\*.java > sources.txt

    "C:\Program Files\Java\zulu17.40.19-ca-fx-jdk17.0.6-win_x64\bin\javac.exe" -d %out% @sources.txt
    del sources.txt

    "C:\Program Files\Java\zulu17.40.19-ca-fx-jdk17.0.6-win_x64\bin\java.exe" -cp %PATH_TO_PROJ%\target\classes ca.linalg.lampp.Lampp
    "C:\Program Files\Java\zulu17.40.19-ca-fx-jdk17.0.6-win_x64\bin\java.exe" -cp %PATH_TO_PROJ%\target\classes ca.linalg.lampp.LamppLauncher

    cd %PATH_TO_PROJ%\target

    "C:\Program Files\Java\zulu17.40.19-ca-fx-jdk17.0.6-win_x64\bin\jar" --create --file=lampp.jar --main-class=ca.linalg.lampp.Lampp -C classes .
    "C:\Program Files\Java\zulu17.40.19-ca-fx-jdk17.0.6-win_x64\bin\java.exe" -jar lampp.jar

### Windows - not using zulu
### java --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.web -jar lampp.jar



* Using the Maven shade plugin, a jar file was created in the shade subdirectory. A shade jar is a single, self-contained "uber JAR" that includes all of the JavaFX dependencies.
* The shade plugin IS NOT THE SAME as the standard Maven plugin, which creates a jar file that STILL NEEDS JavaFX modules explicitly given when the jar is executed.
* using maven-shade-plugin
mvn clean package -Pshade
* Executing this jar does not require listing the JavaFX modules used by Lampp.
* TO use this LAMPP JAR file:
cd $PATH_TO_PROJ/shade
java -jar --enable-native-access=ALL-UNNAMED Lampp-project.jar
* jlink and/or jpackage should be used instead of fat jars (shade) 

* using Eclipse to create jar file, then run:
set PATH_TO_PROJ="%cd%\eclipse-workspace\Lampp"
cd %PATH_TO_PROJ%
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.web -jar target\Lampp-0.0.1-SNAPSHOT.jar
* run the batch file run_jar.bat
* this has been set up in Eclipse to be executed as an external tool: run_jar_Lampp
* will work as long as there is only one jar file in the target directory


* OR, use the MAVEN pom.xml file to build and run:
mvn clean javafx:run
* OR run:
mvn gluonfx:run



* MAVEN creates the subdirectory: target

* use MAVEN to create a jar
mvn clean install
* then run (eg. Windows):
cd target
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.web -jar Lampp-0.0.1-SNAPSHOT.jar
cd ..
* then run (eg. Linux):
cd target
java --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.web -jar Lampp-0.0.1-SNAPSHOT.jar
cd ..

* use MAVEN to build an executable jar:
mvn package
* execute with:
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.graphics,javafx.web -jar .\target\Lampp-0.0.1-SNAPSHOT.jar
java  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.graphics,javafx.web -jar ./target/Lampp-0.0.1-SNAPSHOT.jar

~/JDKs/graalvm-jdk-25.0.2+10.1/bin/java -cp target/classes  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path /home/JDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp

# Any javafx higher than 22.0.2 and up to 27 (so far) will NOT allow animated gifs in webview. Possibly this will NEVER be fixed as webkit is being deprecated in favor of forcing developers to access the machine's (phone's) default browser.
cd ./Lampp
~/JDKs/graalvm-jdk-25.0.2+10.1/bin/java -cp target/classes  --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web --module-path /home/JDKs/javafx-sdk-22.0.2/lib --add-modules javafx.controls,javafx.web ca.linalg.lampp.Lampp


* to create an executable jar for windows:
rename pom.xml to old_pom.xml
rename fatpom.xml to pom.xml
mvn package
* the new file will be in the shade subdirectory
* REMEMBER to switch pom files back
* TO run fat jar file:
cd ~/Lampp/shade
java --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED,javafx.graphics,javafx.web -jar Lampp-project.jar
* or, accepting warnings
java  --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED -jar shade/Lampp-project.jar


* To create a native image (on Linux only), execute the following command:
mvn gluonfx:build gluonfx:nativerun



Header template and package name for java files (change @file and @brief for each file):



License: GNU General Public License v3.0 , or (at your option) any later version.
gpl30	http://www.gnu.org/licenses/gpl.txt 

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
 **  @copyright 2003, 2026 Thomas Czyczko
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











LINUX version:


LamppDir=~/Lampp
HomeDir=~
LamppJavac="$HomeDir/JDKs/graalvm-jdk-25.0.2+10.1/bin/javac"
LamppJava="$HomeDir/JDKs/graalvm-jdk-25.0.2+10.1/bin/java  --sun-misc-unsafe-memory-access=allow"
LamppPathToFX="$HomeDir/JDKs/javafx-sdk-22.0.2/lib"
LamppJavaModules="javafx.controls,javafx.graphics"
LamppJavaModulesWeb="javafx.controls,javafx.graphics,javafx.web"
LamppCP="target/classes"
LamppModulePath="$HomeDir/JDKs/javafx-sdk-22.0.2/lib"
LamppSrc="$LamppDir/Lampp/src/main/java/ca/linalg/lampp/"

cd $LamppDir

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModulesWeb $LamppSrc"*.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModulesWeb $LamppSrc"LamppKey.java"
$LamppJavac -d $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppGlobal.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppCallDefaultBrowser.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModulesWeb $LamppSrc"LamppBrowser.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModulesWeb $LamppSrc"LamppBrowserWrapper.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppButton.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppAMCEButton.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewElement.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewText.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppTextFieldNumerator.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppTextFieldDenominator.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppHelpPane.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppKey.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppKeyboard.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppMatrixButton.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppOpButton.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppOpSelect.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppRCSelectButton.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppRCViewButton.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppVectorButton.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppVerticalButton.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppEditList.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppElementRational.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppElementRational3tuple.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppElementGaussian.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppElementIntModP.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppScrollPane.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppSlidingPaneH.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppSlidingPaneV.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppConvexPolygon.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppCuboid.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewElement.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewText.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewRational.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewIntModP.java"
$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModules $LamppSrc"LamppViewGaussian.java"

$LamppJavac -d $LamppCP -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModulesWeb $LamppSrc"Lampp.java"

$LamppJava -cp $LamppCP --module-path $LamppModulePath --add-modules $LamppJavaModulesWeb --enable-native-access=ALL-UNNAMED --enable-native-access=javafx.graphics --enable-native-access=javafx.web ca.linalg.lampp.Lampp




THE ENTIRE CONTENTS of    $LamppDir"/src/main/resources"
should be copied to       $LamppDir"/target/classes"
11 files and the directory html

cp -a $LamppDir"/src/main/resources"/. $LamppDir"/target/classes"/



USE THE HELLOGLUON directory to create Lampp with free gluon maven plugin.
cd ~/HelloGluon


