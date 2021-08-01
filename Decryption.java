import java.util.ArrayList; //import pentru a putea folosi ArrayList

public class Decryption extends CommonMethods{
	private double x2_0, x2_1, x2_3, a, b; //se declara variabilele
	
	// Constructor
    Decryption(double x2_0, double x2_1, double x2_3, double a, double b){
    	this.x2_0 = x2_0; this.x2_1 = x2_1; this.x2_3 = x2_3; this.a = a; this.b = b;
    }

    // Calculeaza mesajul din sistemul Henon
    private int findM1(double x2_0, double x2_1, double x2_3){
        return (int)Math.round((x2_3 + x2_1*x2_1 + b*x2_0 - a)*SCALE_FACTOR); //formula mesajului in functie de x2 -> din capitolul III.2
    }
    
    /*
    int N = 8;
    // Calculeaza cofactorul lui A[p][q] in temp[][]. n este dimensiunea actuala a lui A[][] 
    private void getCofactor(int A[][], int temp[][], int p, int q, int n){ 
        int i = 0, j = 0; 
        for(int r = 0; r < n; r++){ //iterare pe fiecare element al matricei
            for(int c = 0; c < n; c++){ 
                if(r != p && c != q){ 
                    temp[i][j++] = A[r][c]; //se copiaza in matricea temporara doar elementele care nu sunt in randul si coloana date
                    if(j == n - 1){ //randul este umplut, deci se creste indexul randului si se reseteaza indexul coloanei
                        j = 0; 
                        i++; 
                    } 
                } 
            } 
        } 
    } 
    
    // Functie recursiva pentru gasirea determinantului matricei. n este dimensiunea actuala a lui A[][]
    private int determinant(int A[][], int n){ 
        int D = 0; //se initializeaza rezultatul
        if (n == 1){ 
            return A[0][0]; //la o matrice de o linie si o coloana, determinantul este A[0][0]
        }
        int [][]temp = new int[N][N]; //vector pentru stocarea cofactorilor
        int sign = 1; //variabila pentru stocarea multiplicatorului semnelor
        for(int f = 0; f < n; f++){ //iterare pentru fiecare element din primul rand
            getCofactor(A, temp, 0, f, n); //returneaza cofactorul lui A[0][f]
            D += sign * A[0][f] * determinant(temp, n - 1); 
            sign = -sign; //termenii trebuie adaugati cu semn alternativ
        }
        return D; 
    }   
    
    // Calculeaza adjunctul lui A[N][N] in adj[N][N]. 
    private void adjoint(int A[][],int [][]adj){ 
        int sign = 1; 
        int [][]temp = new int[N][N]; //temp este folosit pentru a stoca cofactorii lui A[][] 
        for(int i = 0; i < N; i++){ 
            for(int j = 0; j < N; j++){ 
                getCofactor(A, temp, i, j, N); //returneaza cofactorul lui A[i][j]
                sign = ((i + j) % 2 == 0)? 1: -1; // semnul lui adj[j][i] este pozitiv daca suma indicilor liniilor si coloanelor este para
                adj[j][i] = (sign)*(determinant(temp, N-1)); //se schimba liniile cu coloanele pentru a obtine transpunerea matricei cofactorului
            } 
        } 
    }       
    
    // Calculeaza si returneaza inversa
    private boolean inverse(int A[][], int [][]inverse){ 
        int det = determinant(A, N); //calculeaza determinantul lui A[][] 
        int [][]adj = new int[N][N]; 
        adjoint(A, adj); //calculeaza adjunctul
        for(int i = 0; i < N; i++){ 
            for(int j = 0; j < N; j++){ 
                inverse[i][j] = (int)(adj[i][j]/(float)det); //calculeaza inversul folosind formula "inv(A) = adj(A)/det(A)"
            }
        }
        return true; 
    }
    
    // Returneaza inversa permutarii
    private int[][] findInversePermutationNumber(int boxNumber){
        int[][] A = this.findPermutationNumber(boxNumber); // A este permutarea folosita la criptare, pentru care trebuie sa se gaseasca inverasa
        int [][]inv = new int[N][N]; //stocheaza inversa lui A[][]
        inverse(A, inv);
        return inv; // returneaza inversa
    }
    */
    
    // Alegere inversa permutarii in functie de numarul cutiei
    protected int[][] findInversePermutationNumber(int boxNumber){
        ArrayList<int[][]> permutations = new ArrayList<>(); //creeaza ArrayList
        int[][] matrix1 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        int[][] matrix2 = {{1,0,0,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,0,0,1}};
        int[][] matrix3 = {{0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,0,0,1}};
        int[][] matrix4 = {{0,0,0,0,0,0,0,1}, {1,0,0,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,1,0}};
        int[][] matrix5 = {{0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,1}};
        int[][] matrix6 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,1,0}, {0,0,0,0,1,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        int[][] matrix7 = {{0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}};
        int[][] matrix8 = {{0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,0,0,0,0,1}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        int[][] matrix9 = {{0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,0,1,0,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {1,0,0,0,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}};
        int[][] matrix10 = {{0,0,0,0,0,1,0,0}, {0,0,0,0,0,0,0,1}, {0,0,0,0,0,0,1,0}, {0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,0}, {0,0,1,0,0,0,0,0}, {0,1,0,0,0,0,0,0}, {1,0,0,0,0,0,0,0}};
        permutations.add(matrix1); permutations.add(matrix2); permutations.add(matrix3); permutations.add(matrix4); permutations.add(matrix5); permutations.add(matrix6); permutations.add(matrix7); permutations.add(matrix8); permutations.add(matrix9); permutations.add(matrix10); //adauga matricile in "permutations"
        return permutations.get(boxNumber-1); //permutarea finala ia valoarea permutarii corespunzatoare;
    }
    
    // Intoarce valoarea zecimal a caracterului din textul decriptat (dupa algoritmul propus)
    protected int resultAfterDecryption(){ 	
    	double sinValue = this.sinFunction(this.x2_1, this.x2_0); //calculeaza sin((x2_1-x2_0)/2)
    	int boxNumber = this.findBoxNumber(sinValue); //gaseste numarul cutiei
    	int[][] inversePermutationNumber = this.findInversePermutationNumber(boxNumber); //gaseste inversa permutarii corespunzatoare cutiei
    	int[][] binaryM1 = this.convertIntFromStringMatrix(this.convertBinary(this.findM1(this.x2_0, this.x2_1, this.x2_3))); //calculeaza mesajul din sistemul Henon
    	int multiplyPermutationM1 = Integer.parseInt(this.convertStringFromNumberMatrix(this.multiplyMatrices(inversePermutationNumber, binaryM1)), 2); //face inmultirea dintre permutare si mesaj
    	int byteNumber = this.findByteNumber(boxNumber); //calculeaza byte-ul corespunzator cutiei
    	return this.bitXor(multiplyPermutationM1, byteNumber); //calculeaza XOR intre valoarea inmultirii dintre permutare si mesaj si byte
    }
}