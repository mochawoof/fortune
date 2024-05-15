import java.net.URL;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
/**
 * fortune v1.0
 * Fortunes from http://bxr.su/OpenBSD/games/fortune/
 */
class Main {
	public static void main(String[] args) {
		String[] files = new String[] {"fortunes.txt", "fortunes-o.real.txt", "limerick.txt"};
		//Set files if provided, or list them
		if (args.length > 0) {
			String arg = args[0].toLowerCase();
			if (arg.endsWith("help") || arg.equals("/?")) {
				System.out.println("fortune v1.0");
				System.out.println();
				System.out.println("Commands");
				System.out.println("help: Shows this.");
				System.out.println("list: Lists available built-in fortune files.");
				System.out.println("[filenames]: Any number of filenames to choose fortunes from.");
				return;
			} else if (arg.equals("list")) {
				for (String f : files) {
					System.out.println(f);
				}
				return;
			}
			files = args;
		}
		
		//Grab streams for each file
		InputStream[] fileBuffs = new InputStream[files.length];
		int numBytes = 0;
		for (int i=0; i < files.length; i++) {
			try {
				URL resource = Main.class.getResource(files[i]);
				if (resource == null) {
					resource = new File(files[i]).toURI().toURL();
				}
				fileBuffs[i] = resource.openStream();
				numBytes += fileBuffs[i].available();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Random byte which we will grab the whole fortune from
		int rand = (int) (Math.random() * numBytes - 1);
		int onByte = 0;	

		for (InputStream buff : fileBuffs) {
			String fortune = "";
			int b;
			try {
				while ((b = buff.read()) != -1) {
					if ((char) b == '%') {
						if (rand < onByte && rand > onByte - fortune.length()) {
							fortune = fortune.trim();
							System.out.println(fortune);
							return;
						}
						fortune = "";
					} else {
						fortune += (char) b;
					}
					onByte++;
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		
	}
}