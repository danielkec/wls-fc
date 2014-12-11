FeedCombiner
======

####Compilation
FeedCombiner is standard webapp maven project(tested on Apache Maven 3.2.1,Oracle Java 1.8 and 1.7).
After unpacking just run in the same folder as pom.xml is:
```
mvn install
```
When successfull bulding and testing is finished, packaged war file is prepared at:
```
pom.xml/../target/feedcombiner-1.0-SNAPSHOT.war
```

####Runing
After deploying war file on the GlassFish server (tested on GlassFish 4.1 Web Profile),
visit:
```
${server_ip:port}/feedcombiner/rest/expose
```
Expose rest endpoint returns html UI with overview of the all combined feeds(there are two mock ones after start). The overview page have means to create, delete, modify(have to be confirmed by update button) combined feeds. Every combined feed have hyperlinks to rest endpoints returnig their feed in HTML, JSON or ATOM format. 

