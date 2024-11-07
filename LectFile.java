
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LectFile {
	// public LectFile(String emplacementA, String emplacementB) {
	// 	Scanner source = new Scanner(System.in);
	// 	source.close();
	// 	return;
	// }

	public static ArrayList<String> import_csv(String cheminFichier) {
		File file = new File(cheminFichier);
        ArrayList<String> data= new ArrayList<>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			int positionLigne = 0;
            
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				positionLigne++;
				// System.out.println(line);
				System.out.println(line);
				data.add(line);

			}
			br.close();
			fr.close();
            System.out.println(data);

		} catch (IOException e) {
			System.err.println("Fichier introuvable : " + cheminFichier);
			e.printStackTrace();
		}
        return data;
	}
}
