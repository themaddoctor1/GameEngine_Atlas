
package math;

public class Matrix {
    
    private double[][] values;
    public final int ROWS, COLS;

    public Matrix(int r, int c) {
        values = new double[r][c];
        ROWS = r;
        COLS = c;
    }

    public Matrix(int s) {
        this(s, s);
    }

    public Matrix(Matrix m) {
        this(m.ROWS, m.COLS);

        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLS; j++)
                values[i][j] = m.values[i][j];
    }

    public Matrix() {
        this(2);
    }

    public Matrix add(Matrix m) {
        Matrix res = new Matrix(this);

        for(int i = 0; i < values.length; i++)
            for(int j = 0; j < values[i].length; j++)
                res.values[i][j] += m.values[i][j];
    
        return res; 
    }

    public Matrix sub(Matrix m) {
        Matrix res = new Matrix(this);

        for(int i = 0; i < values.length; i++)
            for(int j = 0; j < values[i].length; j++)
                res.values[i][j] -= m.values[i][j];

        return res;
    }
    
    public Matrix mul(double d) {
        Matrix res = new Matrix(this);

        for(int i = 0; i < values.length; i++)
            for(int j = 0; j < values[i].length; j++)
                res.values[i][j] *= d;
    
        return res; 
    }
    
    public Matrix mul(Matrix m) {
        Matrix res = new Matrix(ROWS, m.COLS);
        
        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < m.COLS; j++) {
                double d = 0;
                for(int k = 0; k < COLS; k++)
                    d += get(i, k) * m.get(k, j);
                
                res.values[i][j] = d;
            }
        
        return res;
    }

    public double determinant() {
        if(ROWS > 2) {
            double total = 0;

            for(int i = 0; i < COLS; i++) {
                Matrix m = new Matrix(ROWS-1, COLS-1);

                for(int j = 1; j < ROWS; j++) {
                    for(int k = 0; k < i; k++)
                        m.values[j-1][k] = values[j][k];

                    for(int k = i+1; k < COLS; k++)
                        m.values[j-1][k-1] = values[j][k];

                }
                
                total += get(0, 1) * m.determinant() * (i%2 == 0 ? 1 : -1);
            }

            return total;
        } else {
            return values[0][0] * values[1][1] - values[0][1] * values[1][0];
        }
    }

    public Matrix transpose() {
        Matrix m = new Matrix(COLS, ROWS);

        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLS; j++)
                m.values[j][i] = values[i][j];

        return m;
    }
    
    public Matrix solveSystem() {
        Matrix res = new Matrix(this);
        
        //System.out.println(res);

        for(int i = 0; i < ROWS; i++) {
            
            //First, ensure a non-zero value in the variable's position.
            for(int j = i+1; res.get(i, i) == 0 && j < ROWS; j++)
                res.swapRows(j, i); 

            for(int j = 0; j < i; j++)
                res.addRows(j, i, -res.get(i, j));
           
            //Makes sure (i, i) = 1
            res.multRow(i, 1.0 / res.get(i, i));
            
            for(int j = 0; j < i; j++)
                res.addRows(i, j, -res.get(j, i));

            //System.out.println("Changes to row " + i + ": \n" + res);
        }

        return res;
    }
    

    public double get(int r, int c) {
        return values[r][c];
    }


    public void set(int r, int c, double v) {
        values[r][c] = v;
    }
    
    public void multRow(int idx, double v) {
        //System.out.println("Multiply row " + idx + " by " + v + ".");
        for(int i = 0; i < COLS; i++)
            values[idx][i] *= v; 
    }
    
    public void multCol(int idx, double v) {
        //System.out.println("Multiply col " + idx + " by " + v + ".");
        for(int i = 0; i < ROWS; i++)
            values[i][idx] *= v; 
    }

    public void swapRows(int src, int dst) {
        //System.out.println("Swap rows " + src + " and " + dst + ".");
        for(int i = 0; i < COLS; i++) {
            double tmp = get(src, i);
            set(src, i, get(dst, i));
            set(dst, i, tmp);
        }
        
    }
    
    public void swapCols(int src, int dst) {
        //System.out.println("Swap cols " + src + " and " + dst + ".");
        for(int i = 0; i < ROWS; i++) {
            double tmp = get(i, src);
            set(i, src, get(i, dst));
            set(i, dst, tmp);
        }
        
    }

    public void addRows(int src, int dst, double mult) {
        //System.out.println("Add " + mult + " times row " + src + " to row " + dst + ".");
        for(int i = 0; i < COLS; i++) {
            values[dst][i] += values[src][i] * mult;
        }
    }
    
    public void addCols(int src, int dst, double mult) {
        //System.out.println("Add " + mult + " times col " + src + " to col " + dst + ".");
        for(int i = 0; i < ROWS; i++) {
            values[i][dst] += values[i][src] * mult;
        }
    }
    
    public String toString() {
        String res = "MATRIX:\n";

        for(int i = 0; i < ROWS; i++)
            for(int j = 0; j < COLS; j++)
                res += ("    (" + i + ", " + j + ") = " + get(i, j) + "\n");

        return res + "END MATRIX";
    }

    
}
