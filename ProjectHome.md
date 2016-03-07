# Summary #
The primary motivation for this project is to have a simple way to send remote commands to network enabled devices.

## Components ##
This project is composed of three different applications:
  * Mobile application
    * client application that sends remote commands
    * communications with web application (NOT IMPLEMENTED)
    * currently just the android
  * Server application
    * runs on remote devices
    * runs commands sent by mobile application
    * runs command sent by web application (NOT IMPLEMENTED)
  * Web application (NOT IMPLEMENTED)
    * multiple user accounts
    * client application that sends remote commands and/or communications with mobile application
    * allows commands to be sent to devices inside private networks

### It currently support the following: ###
  * wake-on-lan
  * sleep for both Windows and Linux

Only been tested on Windows XP and Ubuntu 10.10

### For sleep to work you will need run the "mote-control-server" app on startup. ###
ex: `java -jar mote-control-server.jar`

**WARNING**: the mote-control-server app is currently extremely insecure and should only be used for testing and on private networks. This should be fixed soon

# How to Improve #
  * Combine with other remote control projects that support Remote mouse/keyboard control
  * implement authentication and encryption for server app
  * Add other device types to user interface and schema
  * Add new commands eg(run custom command, run backup now, purge files now..etc)
  * Create webapp to store devices for multiple users
  * Add relay support to server app (for listening on the network for other local devices and issing wake on lan command)
  * Add status support to server app
---
# Help wanted #
If anyone who wants to help with this project, please contact a project owner.