chinese-checkers:
	cd src/main/java && javac org/copinf/cc/Main.java && java org.copinf.cc.Main

PORT=8888

server:
	cd src/main/java && javac org/copinf/cc/net/server/Server.java && java org.copinf.cc.net.server.Server $(PORT)
