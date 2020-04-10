# RideShare User Service

Lombok Installation Instructions

Lombok is a library of annotations for use in Java that offers extra reflections-based functionality. We
require it to implement our logging of endpoint traffic. The Lombok dependency has to be actually installed
in your IDE directly IN ADDITION to being included in your pom dependencies to work correctly for development
purposes.

Expaination for our batch (to be removed later): It was added as a dependency in this project when it came to us, but must have
either not been used or we hadn't run into the functionality that used it yet, because I discovered in the fullstack application
challenge while I was building an H2 with Lombok that Lombok (or maybe certain annotations) does/do not work unless the library is
included in your actual IDE (It works by default in certain IDEs but not STS or Eclipse).

Steps to Install Lombok on STS:
1. CLOSE SPRING TOOLS SUITE
2. Make sure the dependency is in the pom and the project (lombok jar file is listed in 'Maven Dependencies')
3. Go to the physical file location (the directory) containing the lombok jar. This should be in .m2/org/projectlombok
   in your user's files on your computer.
4. You should see two jar files, one ending in '-src.jar' and the other without the '-src'. Open the one that does NOT
   include the '-src'. Note: If you don't see any file endings you can change that in your file explorer settings, google it.
5. This should pull up an installer with a giant chili pepper on it. DON'T CLICK ANYTHING. Wait until the installer scans
   for IDEs.
6. When it is done scanning, check to see if STS is listed in the white box under the scan bar. Note: STS IS NOT ECLIPSE ITSELF,
   if you have full Eclipse installed it will be listed in the box with a purple icon. INSTALLING LOMBOK HERE WILL DO NOTHING FOR
   THE PROJECT.
   -When finished, if STS (with a green icon) is not listed, or if the scanner pops up a message stating that it does not detect any
   compatable IDEs the process will be a bit more difficult, continue at the appendix at the bottom.
8. If STS is listed, hit the install button in the bottom right of the box, which should pull up another screen saying that
   Lombok was sucessfully installed.
9. Reopen STS 
   -If you didn't follow step 1, learn to follow directions. Also, close STS and reopen it to apply the installation.
   (A RESTART DOES NOT WORK, CLOSE WITH THE X AND OPEN IT AGAIN)
10. Go to the 'HELP' tab, and click the option at the very bottom called 'About Spring Tool Suite (version)' if a version of
   Lombok is included as installed at the bottom of the white information screen (not the bar of apps, should be called 'Envious
   Ferret' or something) then you are done. This fixes issues where annotations from Lombok don't work for no apparent reason.
11. If the installation does not seem to exist, and there are still errors (specifically the log statements in the logging
    implementation for the endpoints are not recognized by STS despite the @Log annotation), update the project. If there are still
    issues we have no current workaround but will try to discover if contacted.

Appendix:

If the installer did not find STS on your computer, you will need to perform extra steps.

1. Click the button on the left under the white box in the installer to open a very old file explorer to search for your STS manually.
2. Navigate to the location STS is installed on your computer (if you don't remember, right click the shortcut you use to open STS and
   select 'properties' at the bottom. In the properties window this brings up, the location of your installation will be the filepath
   under 'target').
3. Once in the folder (should be called something like 'sts-3.9.0.RELEASE') open that folder with the installer navigator.
4. STS should now be added to the white box in the installer with a green STS icon. Proceed from step 8 above.
5. If STS does NOT show up in the white box, or the installer gives tells you you have given them a folder with no compatable
   IDE in it, or an error is thrown during installation, continue these steps.
6. Once again MAKE SURE STS IS NOT RUNNING.
7. Copy the lombok jar you double clicked to execute the installer earlier.
8. Rename the copied jar to 'lombok.jar' and paste it in STS home directory right beside STS launcher exe in the 'sts-3.5.2-RELEASE'
   folder you previously navigated to.
9. Open STS.ini (Name of this file can be different based on the STS version) and write '-javaagent:lombok.jar' at the end of the file.
10. After saving the STS.ini, continue from step 9 above.
