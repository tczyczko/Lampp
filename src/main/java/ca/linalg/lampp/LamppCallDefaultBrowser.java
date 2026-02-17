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
 **  @file   LamppCallDefaultBrowser.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Calls the system browser for a help page invoked by the mouse help button on the bottom right of the Lampp basic scene
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



import java.io.IOException;

/**
 * Calls the system web browser when invoked by the mouse help button on the bottom right of the Lampp basic scene
 * Does NOT use the javafx webkit in order that a native image can be generated with GraalVM and mobile Android and iOS images can be made with GluonFX.
 *
 * Use of LamppBrowser.java and LamppBrowserWrapper.java is preferable (looks cuter) and is used by default on desktops/laptops.
 *
 * 
 * @version 1.0
 */
public class LamppCallDefaultBrowser implements LamppGlobal{

    private static final String[] browsers = {"google-chrome", "firefox", "mozilla", "epiphany",
        "konqueror", "netscape", "opera", "links", "lynx", "chromium", "brave-browser"};
        
    private static final String helpURL1P="https://www.rottentomatoes.com";

    public void browse() {
//        System.out.println("Attempting to open "+helpURL1P+" in the system default browser: ");
//        System.out.println(url);
        try {
            if (isMacOperatingSystem()) {
                openUrlInDefaultMacOsBrowser(helpURL1P);
            } else if (isWindowsOperatingSystem()) {
                openUrlInDefaultWindowsBrowser(helpURL1P);
            } else {
                openUrlInDefaultUnixBrowser(helpURL1P);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browse(String url) {
//        System.out.println("Attempting to open "+url+" in the system default browser: ");
//        System.out.println(url);
        try {
            if (isMacOperatingSystem()) {
                openUrlInDefaultMacOsBrowser(url);
            } else if (isWindowsOperatingSystem()) {
                openUrlInDefaultWindowsBrowser(url);
            } else {
                openUrlInDefaultUnixBrowser(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMacOperatingSystem() {
        return getOperatingSystemName().startsWith("Mac OS");
    }

    private boolean isWindowsOperatingSystem() {
        return getOperatingSystemName().startsWith("Windows");
    }

    private String getOperatingSystemName() {
        return System.getProperty("os.name");
    }

    private void openUrlInDefaultMacOsBrowser(String url) throws IOException {
//        System.out.println("Attempting to open "+url+" in the default browser now...");
        Runtime.getRuntime().exec(new String[]{"open", url});
    }

    private void openUrlInDefaultWindowsBrowser(String url) throws IOException {
//        System.out.println("Attempting to open "+url+" in the default browser now...");
        Runtime.getRuntime().exec(new String[]{"rundll32", String.format("url.dll,FileProtocolHandler %s", url)});
    }

    private void openUrlInDefaultUnixBrowser(String url) throws Exception {
        String browser = null;
        for (String b : browsers) {
            if (browser == null && Runtime.getRuntime().exec(new String[]{"which", b}).getInputStream().read() != -1) {
//                System.out.println("Attempting to open "+url+" in the default browser now...");
                Runtime.getRuntime().exec(new String[]{browser = b, url});
            }
        }
        if (browser == null) {
            throw new Exception("No web browser found");
        }
    }
}

