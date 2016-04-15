chinese-checkers:
	cd src/main/java && javac org/copinf/cc/Main.java
	cp -Rn src/main/resources/ src/main/java; true
	cd src/main/java && java org.copinf.cc.Main

PORT=25565

server:
	cd src/main/java && javac org/copinf/cc/net/server/Server.java && java org.copinf.cc.net.server.Server $(PORT)

clean:
	cd src/main/java && find . -maxdepth 1 ! -name "org" ! -name "resources" ! -name "." -delete
	find src -name "*.class" -delete
