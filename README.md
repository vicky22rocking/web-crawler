# web-crawler
web crawler is program which parse the world wide web in mathodical and repeated manner.


Steps to run the web-crawler application 

1> Clone or download the project into local desktop
https://github.com/vicky22rocking/web-crawler.git

2> Prerequisite  for creating the applciation jar
Apache maven version >3.0 should be installed in system .

3> Unzip the downlaoded prject .

4> Visit the  source directory of the project ,where pom.xml resides

5> Run the command 

mvn clean install

6> Target directory will be created ,where application jar resides.

Example:-/Users/vickysehgal/Documents/IMPDATA/web-crawler/target

7>As this appilcation is using maven shade plugin ,an executable jar will be created named as 

web-crawler-1.0-SNAPSHOT-shaded.jar

8> run the application using below command with command line argument to specify the website need to crawl

java -jar web-crawler-1.0-SNAPSHOT-shaded.jar  http://www.prudential.co.uk/ 

9> Applicationn will start crawling




