import java.util.*; //import pentru a putea folosi ArrayList

public abstract class CommonMethods{
    protected static final int gen = 7; //se initializeaza generatorul
    protected static final double SCALE_FACTOR = 100000; //se initializeaza factorul de scala
    
    // Calculeaza functia sin((x1-x2)/2)
    protected double sinFunction(double x1, double x2){
        return Math.sin((x1 - x2)/2);
    }
    
    // Gaseste numarul cutiei conform tabelului III
    protected int findBoxNumber(double sinValue){
        if(sinValue>=-1.0 && sinValue<-0.8){return 1;}
        else if(sinValue>=-0.8 && sinValue<-0.6){return 2;}
        else if(sinValue>=-0.6 && sinValue<-0.4){return 3;}
        else if(sinValue>=-0.4 && sinValue<-0.2){return 4;}
        else if(sinValue>=-0.2 && sinValue<0.0){return 5;}
        else if(sinValue>=0.0 && sinValue<0.2){return 6;}
        else if(sinValue>=0.2 && sinValue<0.4){return 7;}
        else if(sinValue>=0.4 && sinValue<0.6){return 8;}
        else if(sinValue>=0.6 && sinValue<0.8){return 9;}
        else if(sinValue>=0.8 && sinValue<1.0){return 10;}
        else return 0;
    }
    
    // Genereaza cei 10 bytes conform formulei (2.5); byte-ul trebuie sa corespunda cu numarul cutiei
    protected int findByteNumber(int boxNumber){
        return (int)((Math.pow(gen, boxNumber))%255); //byte(i)=(gen^(i))%255
    }
    
    // Calculeaza XOR intre 2 numere
    protected int bitXor(int k, int l) {
    	return k ^ l;
    }
    
    // Converteste numere din zecimal in binar
    protected String convertBinary(int num){
        StringBuilder sb = new StringBuilder(); //se construieste o variabila sb
        while (sb.length() < 8 - Integer.toBinaryString(num).length()){ //daca lungimea in binar e mai mica decat 8
            sb.append('0'); //se completeaza cu 0-uri
        }
        sb.append(Integer.toBinaryString(num)); //se adauga numarul in binar dupa 0-uri
        return sb.toString();
    }
    
    // Transforma String in matrice de int 8x1
    protected int[][] convertIntFromStringMatrix(String matrix){
        String[] str = matrix.split(""); //pune in vectorul str fiecare caracter separat
        int[][] ch = {{0},{0},{0},{0},{0},{0},{0},{0}}; //se initializeaza un vector int de 8 linii si 1 coloana
        for(int i=0;i<8;i++){
            for(int j=0;j<1;j++){
                ch[i][j] = Integer.parseInt(str[i]); //pune caracterele convertite in Integer in vector
            }
        }
        return ch;
    }
        
    // Transforma matrice de int 8x1 in String
    protected String convertStringFromNumberMatrix(int[][] matrix){
        String word = ""; //initializeaza un string
        for(int i=0;i<8;i++) {
            for(int j=0;j<1;j++){
                word += matrix[i][j]; //adauga fiecare element din matrice in "word"
            }
        }
        return word;
    }
    
    // Alege permutarea in functie de numarul cutiei
    protected int[][] findPermutationNumber(int boxNumber){
        ArrayList<int[][]> permutations = new ArrayList<>(); //creeaza un ArrayList
        int[][] matrix1 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        int[][] matrix2 = {{1,0,0,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,0,0,1}};
        int[][] matrix3 = {{0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,0,0,1}};
        int[][] matrix4 = {{0,1,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,0,0,1}, {1,0,0,0,0,0,0,0}};
        int[][] matrix5 = {{0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,1}};
        int[][] matrix6 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,1,0,0,0,0}, {0,1,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        int[][] matrix7 = {{0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}};
        int[][] matrix8 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}};
        int[][] matrix9 = {{0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        int[][] matrix10 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}};
        permutations.add(matrix1); permutations.add(matrix2); permutations.add(matrix3); permutations.add(matrix4); permutations.add(matrix5); permutations.add(matrix6); permutations.add(matrix7); permutations.add(matrix8); permutations.add(matrix9); permutations.add(matrix10); //adauga matricile in "permutations"
        return permutations.get(boxNumber-1); //permutarea finala ia valoarea permutarii corespunzatoare
    }
    
    // Face inmultirea intre doua matrici int
    protected int[][] multiplyMatrices(int[][] m, int[][] n){
    	int[][] product = new int[m.length][n[0].length]; //se initializeaza o matrice cu numarul de linii al primei matrici si numarul de coloane al celei de-a doua [r_m,c_n]
        for(int i = 0; i < m.length; i++) { //se parcurg liniile primei matrici [r_m]
        	for (int k = 0; k < m[0].length; k++) { //se parcurg coloanele primei matrici [c_m]
        		for (int j = 0; j < n[0].length; j++) { //se parcurg coloanele celei de-a doua matrici [c_n]
        			product[i][j] += m[i][k] * n[k][j]; //se face inmultirea conform teoriei inmultirii matricilor
        		}
        	}
        }
        return product;
    }
    
    // Face inmultirea intre doua matrici double
    // metoda supraincarcata (se utilizeaza la calculul exponentilor Lyapunov)
    protected double[][] multiplyMatrices(double[][] m, double[][] n){
    	double[][] product = new double[m.length][n[0].length]; //se initializeaza o matrice cu numarul de linii al primei matrici si numarul de coloane al celei de-a doua [r_m,c_n]
        for(int i = 0; i < m.length; i++) { //se parcurg liniile primei matrici [r_m]
        	for (int k = 0; k < m[0].length; k++) { //se parcurg coloanele primei matrici [c_m]
        		for (int j = 0; j < n[0].length; j++) { //se parcurg coloanele celei de-a doua matrici [c_n]
        			product[i][j] += m[i][k] * n[j][k]; //se face inmultirea conform teoriei inmultirii matricilor
        		}
        	}
        }
        return product;
    }
}