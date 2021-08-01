import java.io.FileWriter; //import pentru a putea scrie in fisiere
import java.io.IOException; //import pentru exceptii
import java.util.*; //import pentru a putea folosi ArrayList
import java.util.Map.Entry; //import pentru a putea itera o mapa

public class Encryption extends CommonMethods{
	private double x0, x1, x2, a, b; //se declara variabilele
	protected double m; //se declara variabilele
	
    // Constructor
    public Encryption(double x0_0, double x1_0, double x2_0, double a, double b){
    	this.x0 = x0_0; this.x1 = x1_0; this.x2 = x2_0; this.a = a; this.b = b;
    }
    
    // Adauga cifrul si texul criptat in cate un vector si intoarce cifrul
    public List<Double> encrypt(List<Integer> input, List<Integer> cipherText) throws IOException{
    	//FileWriter writeBitXor = new FileWriter("D:\\Licenta\\BitXor.txt"); //defineste fisierul pentru realizarea figurilor 2.7 si 2.8, cu distributia caracterelor inainte si dupa XOR
    	List<Double> cipher = new ArrayList<>(input.size()+2); //initializeaza un ArrayList
        cipher.add(this.x2); //prima cheie de decriptare (este al treilea termen al conditiilor initiale) se adauga in cipher
        for(Integer bt : input) {
        	//writeBitXor.write(this.getBitXor(bt)+" "); //se adauga in fisier pentru realizarea figurilor 2.7 si 2.8, cu distributia caracterelor inainte si dupa XOR
        	this.m = this.resultAfterEncryption(bt); //calculeaza valoarea zecimal a textului criptat
        	cipherText.add((int)this.m); //adauga in vectorul cipherText textul criptat
        	double x0_new = this.a - this.x1*this.x1 - this.b*this.x2 + this.m/SCALE_FACTOR;
        	double x1_new = this.x0;
        	double x2_new = this.x1; //sistemul Henon3D
        	this.x0 = x0_new;
        	this.x1 = x1_new;
        	this.x2 = x2_new; //se actualizeaza valorile calculate cu sistemul Henon 3D
        	cipher.add(this.x2); //se adauga in cipher al treilea termen al sistemului Henon (cel actualizat)
        }
        cipher.add(this.x1); //se adauga in cipher al doilea termen al sistemului Henon (de la ultima iteratie)
        cipher.add(this.x0); //se adauga in cipher primul termen al sistemului Henon (de la ultima iteratie)
        return cipher;
    }
    
    /* Se genereaza valorile dupa XOR pentru realizarea figurilor 2.7 si 2.8, cu distributia caracterelor inainte si dupa XOR
    private int getBitXor(int bt) {
    	double sinValue = this.sinFunction(this.x1, this.x2);
    	int boxNumber = this.findBoxNumber(sinValue);
    	int inputValue = bt;
    	int byteNumber = this.findByteNumber(boxNumber);
    	return this.bitXor(inputValue, byteNumber);
    }
    */
    
    // Intoarce valoarea zecimal a caracterului din textul criptat (dupa algoritmul propus)
    private int resultAfterEncryption(int bt){ 
    	double sinValue = this.sinFunction(this.x1, this.x2); //calculeaza sin((x1-x2)/2)
    	int boxNumber = this.findBoxNumber(sinValue); //gaseste numarul cutiei
    	int inputValue = bt;
    	int byteNumber = this.findByteNumber(boxNumber); //calculeaza byte-ul corespunzator cutiei
    	int[][] permutationNumber = this.findPermutationNumber(boxNumber); //gaseste permutarea corespunzatoare cutiei
    	int[][] binaryBitXor = this.convertIntFromStringMatrix(this.convertBinary(this.bitXor(inputValue, byteNumber))); //calculeaza XOR intre valoarea zecimal a textului in clar si byte
    	int[][] resultMultiplyMatrices = this.multiplyMatrices(permutationNumber, binaryBitXor); //inmulteste permutarea cu rezultatul XOR-ului
    	return Integer.parseInt(this.convertStringFromNumberMatrix(resultMultiplyMatrices), 2); //converteste in int valoarea inmultirii de mai sus
    }
    
    // Calculeaza exponentii Lyapunov
    public void LyapunovExponents() throws IOException { //pagina 28 in licenta, v din formule este w in implementare, v' din formule este v din implementare
    	FileWriter writeLyapunov = new FileWriter("D:\\Licenta\\Lyapunov_Exponents.txt"); //se genereaza fisierul in care se salveaza exponentii Lyapunov
    	int N=3; //ordinul sistemului Henon
    	int iter = 5000; //numarul de iteratii
    	double [] lambda = new double[N]; //initializeaza vectorul in care se salveaza exponentii
    	double [] norm = new double[N]; //vectorul in care se vor pune normarile
    	double[][] v = {{1,0,0}, {0,1,0}, {0,0,1}}; //se defineste v ca matrice unitate
    	double[][] J = {{0,-2*this.x1, -this.b},{1,0,0},{0,1,0}}; //se defineste Jacobianului
    	for(int i=0;i<iter;i++) {
    		double[][] w = this.multiplyMatrices(v, J); //se face inmultirea dintre Jacobian si matricea unitate, formula (2.2)
    		// ortonormare prin metoda Gram-Schmidt pentru prima linie a lui v
    		norm[0] = Math.sqrt(w[0][0]*w[0][0] + w[0][1]*w[0][1] + w[0][2]*w[0][2]); //prima normare pentru prima linie a lui v
    		for(int j=0;j<N;j++) { //iterare dupa numarul N
    			v[0][j] = w[0][j]/norm[0]; //prima linie a lui v se normeaza la prima valoare a lui norm
    		}
    		// ortonormare prin metoda Gram-Schmidt pentru a doua linie a lui v
    		for(int j=0;j<N;j++) { //iterare dupa numarul N
    			v[1][j] = w[1][j] - (w[1][0]*v[0][0] + w[1][1]*v[0][1] + w[1][2]*v[0][2])*v[0][j]; //scrierea lui v ca numaratorul fractiei (formula (2.4))
    		}
    		norm[1] = Math.sqrt(v[1][0]*v[1][0] + v[1][1]*v[1][1] + v[1][2]*v[1][2]); //a doua normare pentru a doua linie a lui v
    		for(int j=0;j<N;j++) { //iterare dupa numarul N
    			v[1][j] = v[1][j]/norm[1]; //a doua linie a lui v se normeaza la a doua valoare a lui norm
    		}
    		// ortonormare prin metoda Gram-Schmidt pentru a treia linie a lui v
    		for(int j=0;j<N;j++) { //iterare dupa numarul N
    			v[2][j] = w[2][j] - (w[2][0]*v[1][0] + w[2][1]*v[1][1] + w[2][2]*v[1][2])*v[1][j] - (w[2][0]*v[0][0] + w[2][1]*v[0][1] + w[2][2]*v[0][2])*v[0][j];  //scrierea lui v ca numaratorul fractiei (formula (2.4))
    		}
    		norm[2] = Math.sqrt(v[2][0]*v[2][0] + v[2][1]*v[2][1] + v[2][2]*v[2][2]); //a treia normare pentru a treia linie a lui v
    		for(int j=0;j<N;j++) { //iterare dupa numarul N 
    			v[2][j] = v[2][j]/norm[2]; //a treia linie a lui v se normeaza la a treia valoare a lui norm
    		}
    		
    		for(int j=0;j<N;j++) { //iterare dupa numarul N
    			lambda[j] += Math.log(norm[j]); //se aduna la fiecare lambda valoarea normata conform formulei (2.1)
    		}
    		double x0_new = this.a - this.x1*this.x1 - this.b*this.x2;
        	double x1_new = this.x0;
        	double x2_new = this.x1; //sistemul Henon 3D
        	this.x0 = x0_new;
        	this.x1 = x1_new;
        	this.x2 = x2_new; //se actualizeaza valorile calculate cu sistemul Henon 3D
        	J[0][1] = (-2)*this.x1; //se actualizeaza termentul Jacobianului care il contine pe x1 cu noua valoare
    	}
    	for(int j=0;j<N;j++) { //iterare dupa numarul N
    		lambda[j] = lambda[j]/(Math.log(2)/Math.log(2))/iter; //formula (2.1) -> log2(2)
    	}
    	writeLyapunov.write(lambda[0] + " " + lambda[1] + " " + lambda[2]); //se scriu in fisier exponentii Lyapunov
    	writeLyapunov.close(); //se inchide fisierul
    }
    
    // Claculeaza valorile pentru diagrama de bifurcatie
    public void bifurcationDiagram() throws IOException {
    	FileWriter writeBifurcation = new FileWriter("D:\\Licenta\\Bifurcation_Diagram.txt"); //se genereaza fisierul in care se salveaza valorile pentru realizarea diagramei de bifurcatie
    	int iter = 5000; //numarul de iteratii
    	double a; //se declara parametrul de bifurcatie a
    	LinkedHashMap<Double, ArrayList<Double>> bifurcation = new LinkedHashMap<>(); //se creeaza o mapa pentru a corela o valoare a lui a cu toate valorile lui x0 pentru acel a
    	for(a=0.001;a<2;a+=0.001) { //se itereaza a de la 0 la 2 exclusiv, cu pas de 0.001
        	ArrayList<Double> x0 = new ArrayList<>();
        	ArrayList<Double> x1 = new ArrayList<>();
        	ArrayList<Double> x2 = new ArrayList<>(); //se creeaza trei liste pentru x0, x1 si x2
        	x0.add(this.x0);
        	x1.add(this.x1);
        	x2.add(this.x2); //se adauga in liste x0, x1 si x2
	    	for(int j=1;j<iter;j++) {
	    		x0.add(a-x1.get(j-1)*x1.get(j-1)-this.b*x2.get(j-1));
	    		x1.add(x0.get(j-1));
	    		x2.add(x1.get(j-1)); //se calculeaza x0, x1 si x2 cu sistemul Henon 3D
	    	}
	    	for(int j=0;j<x0.size()-2000;j++) {
	    		x0.remove(j); //se sterg primele 2000 de valori ale lui x0 (pana se stabilizeaza sistemul)
	    		j--; //se scade j pentru ca, daca am sters un element, urmatorul se incarca pe pozitia curenta si la urmatorul j se sare peste el
	    	}
	    	bifurcation.put(a, x0); //se adauga perechea a si vectorul x0 in mapa
    	}
    	for (Entry<Double, ArrayList<Double>> entry : bifurcation.entrySet()) { //iterare dupa mapa
    		for(int j=0;j<entry.getValue().size();j++) { //iterare dupa valorile lui x0 asociate unui a
    			writeBifurcation.write(entry.getValue().get(j) + " "); //se scriu valorile in fisier
    		}
    		writeBifurcation.write("\n");
    		writeBifurcation.write("\n");
    	}
    	writeBifurcation.close(); //se inchide fisierul
    }
    
    // Calculeaza a, b si factorul pentru atacul din Capitolul III
    public void findABfactor(List<Integer> cipherText, List<Double> cipher) throws IOException {
    	double factor, a, b; //se declara variabilele
    	double sumA = 0, sumB = 0, sumFactor = 0, count = 0; //se initializeaza variabilele
    	double c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0; //se initializeaza variabilele
    	for(int i=0;i<cipherText.size()-3;i++) {
    		if(((int)cipherText.get(i) != (int)cipherText.get(i+2)) && ((int)cipherText.get(i) != 0)) { //se verifica conditiile pentru ca numitorii sa fie diferiti de 0
        		c1 = cipher.get(i+4) - ((double)cipherText.get(i+1)/(double)cipherText.get(i))*cipher.get(i+3) + cipher.get(i+2)*cipher.get(i+2) - ((double)cipherText.get(i+1)/(double)cipherText.get(i))*cipher.get(i+1)*cipher.get(i+1);
            	c2 = cipher.get(i+5) - ((double)cipherText.get(i+2)/(double)cipherText.get(i))*cipher.get(i+3) + cipher.get(i+3)*cipher.get(i+3) - ((double)cipherText.get(i+2)/(double)cipherText.get(i))*cipher.get(i+1)*cipher.get(i+1);
            	c3 = cipher.get(i+1) - ((double)cipherText.get(i+1)/(double)cipherText.get(i))*cipher.get(i);
            	c4 = cipher.get(i+2) - ((double)cipherText.get(i+2)/(double)cipherText.get(i))*cipher.get(i);
            	c5 = 1 - (double)cipherText.get(i+1)/(double)cipherText.get(i);
            	c6 = 1 - (double)cipherText.get(i+2)/(double)cipherText.get(i); //notatiile de la algoritmul din capitolul III.2
            	b = (c2*c5 - c1*c6) / (c3*c6 - c4*c5); //formula 3.10
            	a = (c2+b*c4)/c6; //formula 3.11
            	factor = (double)cipherText.get(i) / (cipher.get(i+3)+cipher.get(i+1)*cipher.get(i+1)+b*cipher.get(i)-a); //formula 3.7
            	sumA += a;
            	sumB += b;
            	sumFactor += factor; //adunarea valorilor curente pentru ca la final sa se realizeze media aritmetica
            	count++; //numararea iteratiilor (cate valori de a, b si factor se calculeaza) pentru ca la final sa se realizeze media aritmetica
    		}
    	}
    	FileWriter writeABfactor = new FileWriter("D:\\Licenta\\A_B_Factor.txt"); //se genereaza fisierul in care se exporta valorile a, b si factor
    	writeABfactor.write("a = " + sumA/count + "\n"); 
    	writeABfactor.write("b = " + sumB/count + "\n");
    	writeABfactor.write("factor = " + sumFactor/count + "\n"); //se scriu in fisier cele 3 valori calculate ca medie aritmetica
    	writeABfactor.close(); //se inchide fisierul
    }
}