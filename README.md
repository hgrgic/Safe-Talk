#Safe-Talk encryption utility
This application is a small encryption utility tool.
It is using RSA & AES encryption protocols to generate secure keys and encrypt respective files.

In order to start application
* Generate ST-CLI.jar file by running gradle build script on that module.
* Use one of the following commands:
    * To encrypt a file :
      `java -jar ST-CLI.jar -e /path/to/your/file -pk /path/to/desired/public/key
      `
    * To decrypt a file: `java -jar ST-CLI.jar -d -i /path/to/input/file`
    
Application when first started will automatically check you local configuration and generate personal private and public 
keys. These files are going to be locates in you `home-directory/Installs/safe-talk/personal` directory.

**You should never share your private key!**

Logs are currently generated in the directory where your .jar file is located.


