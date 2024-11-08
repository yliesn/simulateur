
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LectFile {
	public static ArrayList<String> import_txt(String cheminFichier) {
		File file = new File(cheminFichier);
		ArrayList<String> data = new ArrayList<>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			int positionLigne = 0;

			for (String line = br.readLine(); line != null; line = br.readLine()) {
				positionLigne++;
				data.add(line);

			}
			br.close();
			fr.close();

		} catch (IOException e) {
			System.err.println("Fichier introuvable : " + cheminFichier);
			e.printStackTrace();
		}
		return data;
	}
}
