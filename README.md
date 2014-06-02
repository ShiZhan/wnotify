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

[download](http://goo.gl/N62TKr)

How to use
==========
`wNotify [-vh] [-c cycle|-s selector] [URL]`

* -h:  show help information
* -v:  show version
* URL: target web page
* -c:  content sampling cycle
* -s:  a JQuery-style selector for designating content checking area
* -d:  show diff information

output:

    Time                       Checksum
    -------------------------  --------------------------------
    2014/06/01 19:34:31 -0500; 0e8656e4a8e336980544fd173e906c67
    2014/06/01 19:34:38 -0500; 0865870d43f021c7ca4b9fc3f65bd7b7

Use Ctrl+C to stop

[Zhan.Shi](http://shizhan.github.io/) (c) 2014 [Apache License Version 2.0](http://www.apache.org/licenses/)
