wNotify
=======
iNotify-like program for capturing change of Web content.

Build
=====
Deploy [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [sbt](http://www.scala-sbt.org/)

1. Start sbt in project root
2. Run "package"
3. Use the scripts: wnotify (*NIX), wnotify (Windows) to run

Or use "sbt assembly" to build JAR file for running directly from command line:

`java -jar wnotify-assembly-1.0.jar [options]`

Or simply download the JAR file from the link below:

[download](http://goo.gl/N62TKr)

How to use
==========

    `wNotify [-hvcse] [URL]`

    * -h:  show help information
    * -v:  show version
    * -c [cycle]:     content sampling cycle
    * -s [selector]:  a JQuery-style selector for designating content checking area
    * -e [program]:   call an external program while content changes
    * URL: target web page

Use Ctrl+C to stop

TIP:

1. The selector can be found though Google Chrome Browser by using its "Inspect Element"
2. default checking cycle is 1 hour

[Zhan.Shi](http://shizhan.github.io/) (c) 2014 [Apache License Version 2.0](http://www.apache.org/licenses/)
