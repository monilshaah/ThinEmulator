------------------------------------------------------------
 Project ThinEmulator
------------------------------------------------------------

This README is intended to provide brief overview of Project ThinEmulator.

 What is Project ThinEmulator:
-------------------------------

Project ThinEmulator is an initiative to enable cloud-based smartphone emulator platform. It enables user to create and use smartphone emulators on-the-go for various purposes such as testing of smartphone applications. These emulators can be remotely accessed from any modern web browser which supports HTML5/JavaScript without installing any libraries. 

Current Version: ThinEmulator-0.0.1
------------------------------------
 - Overview:

This is alpha version which is under heavy development. As of now, it fully supports HTML5-based remote access for emulator through Google Chrome. Current version provisions only android emulator and includes full interactive support for keyboard input, whereas interactive mouse support is limited to some operations. 

- Basic Operation Supported:

1. Create emulator of preconfigured devices.
2. Start emulator
3. Stop emulator
4. Delete emulator
5. Upload and install APK files for testing

Compiling and Installing Steps:
--------------------------------

ThinEmulator is built using Gradle. Building ThinEmulator compiles all classes and packages them into a deployable .jar file. This .jar file can be executed using java -jar command.

1. Run gradle build
   
    $ ./gradlew clean build

    Gradle will download any needed dependencies for building the .jar file.
    Once all dependencies have been downloaded, the .jar file will be
    created in the build/libs/ subdirectory of the current directory.
    
2. Build gaucamole client design for ThinEmulator (https://github.com/monilshaah/Gaucamole-Client-for-ThinEmulator) to communicate with guacd proxy of guacamole server.

    $ Gaucamole-Client-for-ThinEmulator$ mvn package
    
    Maven will download any needed dependencies for building the .jar file.
    Once all dependencies have been downloaded, the .war file will be
    created in the target/ subdirectory of the current directory.
    
    Copy the .war file as directed in the instructions provided with
    your servlet container. Apache Tomcat, Jetty, and other servlet containers
    have specific and varying locations that .war files must be placed for
    the web application to be deployed.
    
    You will likely need to do this as root.

3. Run .jar file created in step 1 using java

    $ java -jar /build/libs/ThinEmulator-0.0.1.jar
    
    This will start the server on port 9000.

Dependencies:
--------------

-- Android SDK

In current version, this platform depends on Android SDK for provisioning of emulator platform.

-- Guacamole libraries

ThinEmulator depends on Guacamole libraries (https://github.com/glyptodon) to provide HTML5-based remote access. 
- Guacamole server (https://github.com/glyptodon/guacamole-server) is server-side deploy to provide guacd proxy daemon. 
- Guacamole client (https://github.com/glyptodon/guacamole-client) is a HTML5/JavaScript web application. It uses guacd proxy to provide access for remote desktop using VNC protocol.

-- Fastdroid VNC server for Android

This is one of google’s open-source projec. It provisions android VNC server that is used by guacamole client embedded in ThinEmulator for provisioning HTML5-based remote access android emulator.
https://code.google.com/p/fastdroid-vnc/

Reporting problems
------------------------------------------------------------

Please report any bugs encountered by opening a new ticket at issue tracking system hosted at:
https://github.com/monilshaah/ThinEmulator/issues
