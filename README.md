# LAMPP - A Package for Introducing Linear Algebra
**LAMPP** is two things:

**Linear Algebra Matrix aPP**: an application that runs on Linux, Windows, Android (soon?), and iOS (eventually?). It is used to work out LA problems and exercises. The UI was designed to resemble an old-fashioned lecture room whiteboard.

**Linear Algebra for Mouse, Pad and Pen**: a textbook on Linear Algebra designed for high school students and people who have been away from mathematics for awhile. Knowledge of calculus or analytic geometry is not assumed.

The two are designed to be used together in that the LAMPP app capabilities are unlocked from keys given in the book. For example, matrix inverses are evaluated using row operations before the necessary information is given to allow LAMPP to calculate inverses with one button.

 The LAMPP app is designed to use several different number fields: the rational, the gaussian (complex with rational coefficients) and integers modulus a specified prime. Initially students use row operations to accomplish tasks. A limited graphing capability is also included to give a "rough" idea of how one might associate geometry with matrices. The impetus is to eliminate the mistake-prone arithmetic used when doing matrix calculations by hand.

 HTML files kept here are intended as an extra source of pictures and animations to illustrate some of the concepts introduced in the LAMPP book. This site may be accessed by a browser or by activating the mouse icon on the lower right of the LAMPP app screen. 

[![AGPL logo](LICENSE/agplv3-with-text-100x42.png)](LICENSE/agpl-3.0.html)



## Thank You!

Please ⭐️ this repo and share it with others

## Screenshots



|                    Lampp with 4X4 matrix                     |            Help Browser Panel Selected            |
| :----------------------------------------------------------: | :-----------------------------------------------: |
| ![4X4 Rational Matrix](screenshots/Lampp1_4X4_rational_matrix.png) | ![Help Browser](screenshots/Lampp2_HelpMouse.png) |

|              Edit Panel Opened              |             Rows and Row Operation Selected              |
| :-----------------------------------------: | :------------------------------------------------------: |
| ![Edit Matrix](screenshots/Lampp3_Edit.png) | ![Operations Selected](screenshots/Lampp4_Operation.png) |

|             4X4 Rational Matrix Shown as Graph             |         Number Field Selections          |
| :--------------------------------------------------------: | :--------------------------------------: |
| ![4X4 Rational Matrix Graph](screenshots/Lampp5_Graph.png) | ![Fields](screenshots/Lampp6_Fields.png) |

## Requirements 🔧

* Java 22 or higher, JavaFX 20.0.2 at highest (for HTML animated GIFs to show properly in Browser Help Panel.) Actually, Java 8 would probably compile and execute without need for separate JavaFX.

## Installation 🔌
1. Press the **Fork** button (top right the page) to save copy of this project on your account.

2. Download the repository files (project) from the download section or clone this project by typing in the bash the following command:

       git clone https://github.com/tczyczko/Lampp.git

3. Use the POM file with Maven to run or build the java program. See 000README.txt for specific and more detailed setup instructions. 

4. The accompanying textbook (a work in progress) is given as a PDF, an epub and HTML files. It is written in ![LaTeX](LICENSE/latex-logo.png) , and processed using [LaTeXML](https://math.nist.gov/~BMiller/LaTeXML/), so it uses the most basic and necessary LaTeX that can be processed into epub and HTML by LaTeXML. 

5. An old Android apk file is included that was created long ago using Gluon and Java 8. It can still be side-loaded on many Android tablets.

## Contributing 💡
If you want to contribute to this project (by adding other number fields perhaps? Or suggesting problems that need the Integer Modulus a Prime number field?), your pull request will be welcomed.
If you find any issue just put it in the repository issue section. Suggestions for directions for further development will also be welcomed. Remember, though, Lampp is for very low level (though strictly correct) instruction and it should allow a student to "play" with the ideas of elementary Linear Algebra which should, really, be introduced even before calculus.
