import java.util.*; //import pentru a putea folosi ArrayList
import java.util.stream.Collectors; //import pentru realizarea citirii
import java.io.File; //import pentru citirea si scrierea fisierelor de imagine
import java.io.IOException; //import pentru tratarea erorilor
import java.time.Duration; //import pentru calcularea timpului de executie
import java.time.Instant; //import pentru calcularea timpului de executie
import java.awt.image.BufferedImage; //import pentru pastrarea imaginii, stocarea in RAM
import javax.imageio.ImageIO; //import pentru realizarea operatiilor de scriere si citire (clasa cu metode statice)
import java.io.FileWriter; //import pentru scrierea in fisiere

public class Main{
    public static void main(String [] args) throws IOException{
        boolean tr = true; //conditia cu ajutorul careia se face citirea (cat timp e adevarata)
    	double x0_0, x1_0, x2_0, a, b; //se declara variabilele cheii de criptare
        List<Double> cipher = new ArrayList<>(); //se creeaza vectorul in care se adauga valorile lui x2 (cifrul)
        List<Integer> cipherText = new ArrayList<>(); //se creeaza vectorul in care se adauga textul criptat
        while(tr == true){ //citire continua pana cand tr devine false
            System.out.println("Alege: bifurcatieLyapunov / text / imagine / stop"); //meniu de alegere a comenzii in consola
            Scanner sc = new Scanner(System.in); //se initializeaza instanta folosita pentru citirea din consola
            String choose = sc.nextLine(); //se citeste comanda
            switch(choose){ //structura switch-case pentru verificarea comenzii
            	case "bifurcatieLyapunov": //comanda pentru calcularea exponentilor Lyapunov si a valorilor pentru diagrama de bifurcatie
            		System.out.print("Introduceti cheia: \nx0(0) = ");
            		x0_0 = sc.nextDouble();
            		System.out.print("x1(0) = ");
            		x1_0 = sc.nextDouble();
            		System.out.print("x2(0) = ");
            		x2_0 = sc.nextDouble();
            		System.out.print("a = ");
            		a = sc.nextDouble();
            		System.out.print("b = ");
            		b = sc.nextDouble(); //se introduc in consola parametrii cheii
            		Encryption enc1 = new Encryption(x0_0, x1_0, x2_0, a, b); //se creeaza o instanta de Encryption cu ajutorul constructorului
            		enc1.LyapunovExponents(); //se apeleaza metoda care calculeaza exponentii Lyapunov
            		enc1.bifurcationDiagram(); //se apeleaza metoda care calculeaza valorile pentru diagrama de bifurcatie
            		System.out.println("Actiunea realizata cu succes! \n");
            		break;
            		
            	case "text": //comanda pentru criptarea si decriptarea unui text 
            		int nrTexts = 330; //numarul de texte 
            		System.out.print("Introduceti cheia: \nx0(0) = ");
            		x0_0 = sc.nextDouble();
            		System.out.print("x1(0) = ");
            		x1_0 = sc.nextDouble();
            		System.out.print("x2(0) = ");
            		x2_0 = sc.nextDouble();
            		System.out.print("a = ");
            		a = sc.nextDouble();
            		System.out.print("b = ");
            		b = sc.nextDouble(); //se introduc in consola parametrii cheii
            		Instant startTimeDecryptImage = Instant.now(); //se porneste cronometrul pentru a calcula timpul de executie al programului 
            		File myObj2 = new File("D:\\Licenta\\Plain_Text.txt"); //fisierul din care se citeste textul in clar
            		FileWriter writeCipherT = new FileWriter("D:\\Licenta\\Cipher.txt"); //fisierul in care se scrie cifrul
            		FileWriter writePlainTextT = new FileWriter("D:\\Licenta\\Plain_Text_ASCII.txt"); //fisierul in care se scriu valorile zecimal ale textului in clar
            		FileWriter writeCipherTextT = new FileWriter("D:\\Licenta\\Cipher_Text.txt"); //fisierul in care se scrie textul dupa criptare
            		FileWriter writePlainTextAtferDecryptionT = new FileWriter("D:\\Licenta\\Atfer_Decryption_Plain_Text.txt"); //fisierul in care se scrie textul dupa decriptare
            		Scanner myReader2 = new Scanner(myObj2); //se initializeaza instanta folosita pentru cititrea din fisier
            	    while (myReader2.hasNextLine()) { //citire cat timp se poate
            	    	String data = myReader2.nextLine(); //se citeste fisierul
            	    	for(int i=0;i<nrTexts;i++) { //iterare dupa numarul de texte
            	    		String strcr = data.substring(data.length()/nrTexts*i, data.length()/nrTexts*(i+1)); //in strcr se afla toate caracterele dintr-un text
                    	    List<Integer> input = strcr.chars().mapToObj(c -> (int)c).collect(Collectors.toList()); //se mapeaza fiecare char pe un Integer, apoi se colecteaza intr-o lista (se utilizeaza codurile ASCII)
	    	                for(Integer bt : input) { //iterare dupa valorile zecimal ale textului in clar
	    	                	writePlainTextT.write(bt + " "); //se scriu in fisier valorile zecimal ale textului in clar
	    	                }
	    	                //Criptarea
	    	                Encryption enc = new Encryption(x0_0, x1_0, x2_0, a, b); //se creeaza o instanta de Encryption cu ajutorul constructorului
	    	                for(Double d : enc.encrypt(input, cipherText)){ //iterare dupa  valorile cifrului
	    	                  	cipher.add(d); //se adauga valorile cifrului in vectorul dedicat
	    	                }
	    	                for(int j=3;j<cipher.size();j++) { //iterare dupa valorile cifrului, dar incepand cu a 3-a pozitie
	    	                	writeCipherT.write(cipher.get(j) + " ");  //se scrie in fisier cifrul
	    	                }
	    	                writeCipherT.write("\n");
	    	                writeCipherT.write("\n");
	    	                for(Integer d : cipherText) { //iterare dupa valorile textului criptat
	    	                  	writeCipherTextT.write(d + " "); //se scrie in fisier textul criptat
	    	                }
	    	                // Decriptarea
	                		for(int j=0;j<cipher.size()-3;j++) { //iterare dupa valorile cifrului
	                			Decryption dec = new Decryption(cipher.get(j), cipher.get(j+1), cipher.get(j+3), a, b); //se creeaza o instanta de Decryption cu ajutorul constructorului
	                			writePlainTextAtferDecryptionT.write(dec.resultAfterDecryption() + " "); //se scrie in fisier textul decriptat
	                		}
	                		enc.findABfactor(cipherText, cipher); //se apeleaza metoda care calculeaza a, b si factorul (pentru atac)
	                		cipher.clear(); //se goleste vectorul cu cifrul
	                		cipherText.clear(); //se goleste vectorul cu textul criptat
            	    	} 
            	    }
            	    myReader2.close(); 
            	    writeCipherT.close();
            	    writePlainTextT.close();
            	    writeCipherTextT.close();
            	    writePlainTextAtferDecryptionT.close(); //se inchid fisierele
            	    Instant endTimeDecryptImage = Instant.now(); //se opreste cronometrul pentru a calcula timpul de executie al programului 
                	Duration intervalImage = Duration.between(startTimeDecryptImage, endTimeDecryptImage); //se calculeaza timpul de executie al programului 
                	System.out.println("Execution time: " + intervalImage.getSeconds() + " seconds"); //se afiseaza timpul de executie al programului 
            	    System.out.println("Actiunea realizata cu succes! \n");
            		break;
            		
            	case "imagine": //comanda pentru criptarea si decriptarea unei imagini
            		int p_c, r_c, g_c, b_c, a_c=0; //declarare pixel (p_c) si componente pixel (red, green, blue, alpha)
            		int height=0, width=0; //se initializeaza dimensiunile imaginii
            		System.out.print("Introduceti cheia: \nx0(0) = ");
            		x0_0 = sc.nextDouble();
            		System.out.print("x1(0) = ");
            		x1_0 = sc.nextDouble();
            		System.out.print("x2(0) = ");
            		x2_0 = sc.nextDouble();
            		System.out.print("a = ");
            		a = sc.nextDouble();
            		System.out.print("b = ");
            		b = sc.nextDouble(); //se introduc in consola parametrii cheii
            		BufferedImage img = null; //se initializeaza imaginea
            		File f = null; //se initializeaza fisierele in care vom scrie
            		FileWriter writeCipherI = new FileWriter("D:\\Licenta\\Cipher.txt"); //fisierul in care se scrie cifrul
            		FileWriter writePlainTextI = new FileWriter("D:\\Licenta\\Plain_Text_ASCII.txt"); //fisierul in care se scriu valorile zecimal ale textului in clar
            		FileWriter writeCipherTextI = new FileWriter("D:\\Licenta\\Cipher_Text.txt"); //fisierul in care se scrie textul dupa criptare
            		FileWriter writePlainTextAtferDecryptionI = new FileWriter("D:\\Licenta\\Atfer_Decryption_Plain_Text.txt"); //fisierul in care se scrie textul dupa decriptare
            		FileWriter writeMetrics = new FileWriter("D:\\Licenta\\Metrics.txt"); //fisierul in care se scriu metricile de performanta
            		// Citirea imaginii
            		try {
            			f = new File("D:\\Licenta\\Color_Image.png"); //fisierul din care se citeste imaginea color
            			img = ImageIO.read(f); //citire imagine
            		}catch(IOException e) {
            			System.out.println("Error: "+e);
            		}
            		List<Integer> input = new ArrayList<>(); //se creeaza vectorul in care se adauga valorile mesajului în clar
            		List<Integer> input2 = new ArrayList<>(); //se creeaza vectorul in care se adauga valorile mesajului în clar cu un pixel schimbat (pentru metricile de performanta)
            		List<Integer> cipherText2 = new ArrayList<>(); //se creeaza vectorul in care se adauga valorile mesajului criptat (pentru metricile de performanta)
            		List<Double> cipher2 = new ArrayList<>(); //se creeaza vectorul in care se adauga valorile cifrului (pentru metricile de performanta)
            		width = img.getWidth();
            		height = img.getHeight(); //setare dimensiuni imagini
            		for(int y=0 ; y<height ; y++) { //iterare dupa "height" 
            			for(int x=0 ; x<width ; x++) { //iterare dupa "width"
            				p_c = img.getRGB(x, y); //setare valoare pixel
            				a_c = (p_c >> 24) & 0xff; //setare valoare alpha
            				r_c = (p_c >> 16) & 0xff; //setare valoare red
            				g_c = (p_c >> 8) & 0xff; //setare valoare green
            				b_c = p_c & 0xff; //setare valoare blue
            				int avg = (r_c+g_c+b_c)/3; //se calculeaza media celor trei pixeli
            				input.add(avg); //se adauga valoarea medie a pixelului in vectorul cu mesajul in clar
            				if((width == 0) && (height == 0)) {
            					input2.add(0);
            				} else {
            					input2.add(avg); //se adauga valoarea medie a pixelului in vectorul cu mesajul in clar, dar cu un pixel schimbat
            				}
            				p_c = (a_c<<24) | (avg<<16) | (avg<<8) | avg; //se seteaza pixelul cu valoarea mediei pentru red, green si blue pentru a face imaginea alb-negru
            				img.setRGB(x, y, p_c); //se seteaza imaginea pixel cu pixel cu noua valoare
            			}
            		}
            		// Scrierea imaginii alb_negru
            		try {
            			f = new File("D:\\Licenta\\Plain_Image.png"); //fisierul in care se scrie imaginea alb-negru
            			ImageIO.write(img, "png", f); //scriere imagine alb-negru
            		}catch(IOException e) {
            			System.out.println("Error: "+e);
            		}
            		for(Integer bt : input) { //iterare dupa valorile zecimal ale mesajului in clar
            			writePlainTextI.write(bt + " "); //se scriu in fisier valorile zecimal ale mesajului in clar
                    }
            		//Criptarea
            		Encryption enc = new Encryption(x0_0, x1_0, x2_0, a, b); //se creeaza o instanta de Encryption cu ajutorul constructorului
            		for(Double d : enc.encrypt(input, cipherText)){ //iterare dupa  valorile cifrului
	                  	cipher.add(d); //se agauga valorile cifrului in vectorul dedicat
	                }
            		// Criptarea pentru imaginea cu un pixel schimbat
            		for(Double d : enc.encrypt(input2, cipherText2)){ //iterare dupa  valorile cifrului
	                  	cipher2.add(d); //se adauga valorile cifrului in vectorul dedicat
	                }
	                for(int j=3;j<cipher.size();j++) { //iterare dupa valorile cifrului, dar incepand cu a 3-a pozitie
	                	writeCipherI.write(cipher.get(j) + " ");  //se scrie in fisier cifrul
	                }
                    for(Integer d : cipherText) { //iterare dupa valorile textul criptat
                    	writeCipherTextI.write(d + " "); //se scrie in fisier textul criptat
                    }
                    for(int y=0 ; y<height ; y++) { //iterare dupa "height" 
            			for(int x=0 ; x<width ; x++) { //iterare dupa "width" 
            				p_c = (a_c<<24) | (cipherText.get(y*width+x)<<16) | (cipherText.get(y*width+x)<<8) | cipherText.get(y*width+x); //se seteaza pixelul cu valoarea mesajului criptat
            				img.setRGB(x, y, p_c); // setarea imaginii pixel cu pixel cu noua valoare
            			}
            		}
                    // Scrierea imaginii criptate
            		try {
            			f = new File("D:\\Licenta\\Encrypted_Image.png"); //fisierul in care se scrie imaginea criptata
            			ImageIO.write(img, "png", f); //scriere imagine criptata
            		}catch(IOException e) {
            			System.out.println("Error: "+e);
            		}
                    // Calcularea metricilor de performanta
                    double sumMAE = 0, sumNPCR = 0, sumUACI = 0; //se initializeaza variabilele folosite
                    for(int i=0;i<input.size();i++) {
                    	sumMAE += Math.abs(cipherText.get(i)-input.get(i)); //se calculeaza suma tuturor diferentelor intre pixelul imaginii criptate si imaginii in clar
                    }
                    double MAE = (1/(double)(height*width)) * sumMAE; //Mean Absolute Error // formula (5.1)
                    for(int i=0;i<input.size();i++) {
                    	sumUACI += (Math.abs(cipherText2.get(i)-cipherText.get(i))/(double)255);  //se calculeaza suma tuturor diferentelor intre pixelul imaginii criptate cu un pixel schimbat si imaginii criptate originale
                    	if(cipherText.get(i) == cipherText2.get(i)) { //daca pixelii dintre imaginea criptata cu un pixel schimnbat si imaginea criptata originala nu sunt egali se aduna 1
                    		sumNPCR += 0;
                    	} else {
                    		sumNPCR += 1;
                    	}
                    }
                    double NPCR = sumNPCR*100/((double)(height*width)); //Number of Pixels Change Rate // formula (5.2)
                    double UACI = sumUACI*100/((double)(height*width)); //Unified Average Changing Intensity // formula (5.3)
                    writeMetrics.write("MAE = " + MAE + "\n");
                    writeMetrics.write("NPCR = " + NPCR + "%\n");
                    writeMetrics.write("UACI = " + UACI + "%"); //se scriu in fisier metricile de performanta
            		// Decriptare
                    for(int y=0 ; y<height ; y++) { //iterare dupa "height" 
                		for(int x=0 ; x<width ; x++) { //iterare dupa "width" 
                			Decryption dec = new Decryption(cipher.get(y*width+x), cipher.get(y*width+x+1), cipher.get(y*width+x+3), a, b); //se creeaza o instanta de Decryption cu ajutorul constructorului
                			writePlainTextAtferDecryptionI.write(dec.resultAfterDecryption() + " "); //se scrie in fisier textul decriptat
                            p_c = (a_c<<24) | (dec.resultAfterDecryption()<<16) | (dec.resultAfterDecryption()<<8) | dec.resultAfterDecryption(); //se seteaza pixelul cu valoarea mesajului decriptat
                            img.setRGB(x, y, p_c); //se seteaza imaginea pixel cu pixel cu noua valoare
                		}
                    }
                    cipher.clear(); //se goleste vectorul cu cifrul
                    cipherText.clear(); //se goleste vectorul cu textul criptat
                	// Scrierea imaginii decriptate
                	try {
                		f = new File("D:\\Licenta\\Decrypted_Image.png"); //fisierul in care se scrie imaginea decriptata
                		ImageIO.write(img, "png", f); //scriere imagine decriptata
                	}catch(IOException e) {
                		System.out.println("Error: "+e);
                	}
            	    writeCipherI.close();
            	    writePlainTextI.close();
            	    writeCipherTextI.close();
            	    writePlainTextAtferDecryptionI.close();
            	    writeMetrics.close(); //se inchid fisierele
            	    System.out.println("Actiunea realizata cu succes! \n");
            		break;

            	case "stop": //comanda pentru oprirea executiei programului 
                    tr = false; //tr ia valoarea fals si astfel se opreste citirea
                    System.out.println("Actiunea realizata cu succes!");
                    break;
                    
                default: //ramura default in cazul in care textul comenzii este scris gresit
                	System.out.println("Comanda gresita! \n"); //se cere reintroducerea unei comenzi valide
            }
        }
    }
}