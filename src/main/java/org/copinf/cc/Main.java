package org.copinf.cc;

import org.copinf.cc.controller.MainController;
import org.copinf.cc.net.server.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class.
 */
public final class Main {

	private Main() {
	}

	/**
	 * Program entry point.
	 * @param args arguments
	 * @throws IOException IOException If an I/O error has occurred when starting the server in
	 *     standalone mode.
	 */
	public static void main(final String[] args) throws IOException {
		if (args.length == 0) {
			Logger.getGlobal().setLevel(Level.INFO);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException expected) {
					}
					new MainController().start();
				}
			});
		} else if ("-h".equals(args[0]) || "--help".equals(args[0])) {
			System.out.println(
					"Usage: ChineseCheckers [-h] [--help] [--version]\n"
				+ "\n"
				+ "A Chinese Checkers game, made in Java 8 by the CopyrightInfringement team.\n"
				+ "\n"
				+ "Options:\n"
				+ "   --version             Show ChineseCheckers version and exit.\n"
				+ "   -h, --help            Show this help message and exit.\n"
				+ "\n"
				+ "   -s <port>,            Launch the game in standalone server mode.\n"
				+ "      --server <port>    Default port is 25565.\n"
				+ "\n"
				+ "Licensed under the MIT License (MIT).\n"
				+ "Copyright (c) 2016 Clara Bringer, Antonin Décimo, Pierre Gervais, Louis Bal Dit Sollier\n"
				+ "For more info, see https://github.com/CopyrightInfringement/ChineseCheckers\n"
			);
		} else if ("--version".equals(args[0])) {
			System.out.println("ChineseCheckers v0.0.1");
		} else if ("-s".equals(args[0]) || "--server".equals(args[0])) {
			final int port = args.length >= 2 ? Integer.parseInt(args[1]) : MainController.DEFAULT_PORT;
			new Thread(new Server(port)).start();
		} else {
			System.err.println("Unrecognized arguments.");
			System.exit(1);
		}
	}
}
