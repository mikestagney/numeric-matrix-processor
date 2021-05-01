package processor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean keepRunning = true;
        int choice;

        while (keepRunning) {
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix by a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse matrix");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case (0): {
                    keepRunning = false;
                    break;
                }
                case (1): {
                    addMatrices(input);
                    break;
                }
                case (2): {
                    multiplyByConstant(input);
                    break;
                }
                case (3): {
                    multiplyMatrices(input);
                    break;
                }
                case (4): {
                    transposeMatrix(input);
                    break;
                }
                case (5): {
                    findDeterminate(input);
                    break;
                }
                case (6): {
                    findInverse(input);
                    break;
                }
            }
        }
    }
    static void findInverse(Scanner input) {
        double[][] matrix = createMatrix(input, "");

        double[][] workingMatrix = matrixMinorsAndCofactors(matrix);

        double[][] transposeMatrix = new double[workingMatrix[0].length][workingMatrix.length];
        for (int row = 0; row < workingMatrix.length; row++) {
                for (int col = 0; col < workingMatrix[0].length; col++) {
                transposeMatrix[col][row] = workingMatrix[row][col];
            }
        }

        double determinate = calculateDeterminate(matrix);
        if (determinate == 0.0) {
            System.out.println("This matrix doesn't have an inverse.");
            return;
        } else {
            for (int row = 0; row < transposeMatrix.length; row++) {
                for (int col = 0; col < transposeMatrix[0].length; col++) {
                    transposeMatrix[col][row] *= 1 / determinate;
                }
            }
        }
        printMatrix(transposeMatrix);
    }
    static double[][] matrixMinorsAndCofactors(double[][] matrix) {

        double[][] minorMatrix = new double[matrix.length][matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                double[][] holder = matrixReducerTwoIndexes(matrix, row, col);
                minorMatrix[row][col] = calculateDeterminate(holder) * Math.pow(-1, row + col);
            }
        }
        return minorMatrix;
    }

    static double[][] matrixReducerTwoIndexes(double[][] matrix, int rowToSkip, int colToSkip) {
        double[][] reducedMatrix = new double[matrix.length - 1][matrix[0].length - 1];

        int colIncrement = 0;
        int rowIncrement = 0;
        for (int col = 0; col < reducedMatrix[0].length; col++) {
            for (int row = 0; row < reducedMatrix.length; row++) {
                if (col >= colToSkip) {
                    colIncrement = 1;
                }
                if (row >= rowToSkip) {
                    rowIncrement = 1;
                }
                reducedMatrix[row][col] = matrix[row + rowIncrement][col + colIncrement];
                colIncrement = 0;
                rowIncrement = 0;
            }
        }
        return reducedMatrix;
    }

    static void findDeterminate(Scanner input) {
        double[][] matrix = createMatrix(input, "");

        if (matrix.length != matrix[0].length) {
            System.out.println("Error, not a square matrix!");
            return;
        }
        double determinate = calculateDeterminate(matrix);

        System.out.println("The result is :");
        System.out.println(determinate);
    }

    static double calculateDeterminate(double[][] matrix) {
        double determinate = 0.0;
        //could put in a base case -> if matrix.length == 1 return matrix[0][0]
        if (matrix.length == 1) {
            return matrix[0][0];
        }/*
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1]
                        - matrix[1][0] * matrix[0][1];
        }*/
         else {
            for (int i = 0; i < matrix.length; i++) {
                double[][] holder = matrixReducer(matrix, i);

                determinate += Math.pow(-1, i) * calculateDeterminate(holder) * matrix[0][i];

            }
        }
        return determinate;
    }               //MatrixReducer -- this is called the minor
    static double[][] matrixReducer(double[][] matrix, int index) {
        double[][] reducedMatrix = new double[matrix.length - 1][matrix[0].length - 1];

            int increment = 0;
            for (int col = 0; col < reducedMatrix[0].length; col++) {
                for (int row = 0; row < reducedMatrix.length; row++) {
                    if (col >= index) {
                        increment++;
                    }
                    reducedMatrix[row][col] = matrix[row + 1][col + increment];
                    increment = 0;
                }
            }
        return reducedMatrix;
    }


    static void transposeMatrix(Scanner input) {
        int choice;
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: ");
        choice = input.nextInt();

        switch (choice) {
            case (1): {
                mainDiagonalTranspose(input);
                break;
            }
            case (2): {
                sideDiagonalTranspose(input);
                break;
            }
            case (3): {
                verticalLineTranspose(input);
                break;
            }
            case (4): {
                horizontalLineTranspose(input);
                break;
            }
        }
    }

    static void mainDiagonalTranspose(Scanner input) {
        double[][] firstMatrix = createMatrix(input, "");
        double[][] transposeMatrix = new double[firstMatrix[0].length][firstMatrix.length];

        for (int row = 0; row < firstMatrix.length; row++) {
            for (int col = 0; col < firstMatrix[0].length; col++) {
                transposeMatrix[col][row] = firstMatrix[row][col];
            }
        }
        printMatrix(transposeMatrix);
    }
    static void sideDiagonalTranspose(Scanner input) {
        double[][] firstMatrix = createMatrix(input, "");
        double[][] transposeMatrix = new double[firstMatrix[0].length][firstMatrix.length];

        for (int newRow = 0, oldRow = firstMatrix.length - 1 ; newRow < firstMatrix.length && oldRow >= 0; newRow++, oldRow--) {
            for (int newCol = 0, oldCol = firstMatrix[0].length - 1; newCol < firstMatrix[0].length && oldCol >=0; newCol++, oldCol--) {
                transposeMatrix[newCol][newRow] = firstMatrix[oldRow][oldCol];
            }
        }
        printMatrix(transposeMatrix);
    }
    static void verticalLineTranspose(Scanner input) {
        double[][] firstMatrix = createMatrix(input, "");
        double[][] transposeMatrix = new double[firstMatrix[0].length][firstMatrix.length];

        for (int row = 0; row < firstMatrix.length; row++) {
            for (int newCol = 0, oldCol = firstMatrix[0].length - 1; newCol < firstMatrix[0].length && oldCol >= 0; newCol++, oldCol--) {
                transposeMatrix[row][newCol] = firstMatrix[row][oldCol];
            }
        }
        printMatrix(transposeMatrix);
    }
    static void horizontalLineTranspose(Scanner input) {
        double[][] firstMatrix = createMatrix(input, "");
        double[][] transposeMatrix = new double[firstMatrix[0].length][firstMatrix.length];

        for (int newRow = 0, oldRow = firstMatrix.length - 1 ; newRow < firstMatrix.length && oldRow >= 0; newRow++, oldRow--) {
            for (int col = 0; col < firstMatrix[0].length; col++) {
                transposeMatrix[newRow][col] = firstMatrix[oldRow][col];
            }
        }
        printMatrix(transposeMatrix);
    }

    static void addMatrices(Scanner input) {
        double[][] firstMatrix = createMatrix(input, " first");
        double[][] secondMatrix = createMatrix(input, " second");

        if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
            System.out.println("The operation cannot be performed.");
            return;
        }
        double[][] matrixAdded = new double[firstMatrix.length][firstMatrix[0].length];

        for (int row = 0; row < firstMatrix.length; row++) {
            for (int col = 0; col < firstMatrix[0].length; col++) {
                matrixAdded[row][col] = firstMatrix[row][col] + secondMatrix[row][col];
            }
        }
        printMatrix(matrixAdded);
    }

    static void multiplyByConstant(Scanner input) {
        double[][] firstMatrix = createMatrix(input, " first");

        System.out.println("Enter constant: ");
        double constant = input.nextFloat();

        double[][] matrixAdded = new double[firstMatrix.length][firstMatrix[0].length];

        for (int row = 0; row < firstMatrix.length; row++) {
            for (int col = 0; col < firstMatrix[0].length; col++) {
                matrixAdded[row][col] = firstMatrix[row][col] * constant;
            }
        }
        printMatrix(matrixAdded);
    }

    static void multiplyMatrices(Scanner input) {
        double[][] firstMatrix = createMatrix(input, " first");
        double[][] secondMatrix = createMatrix(input, " second");

        if (firstMatrix[0].length != secondMatrix.length) {
            System.out.println("The operation cannot be performed.");
            return;
        }
        double[][] matricesMultiplied = new double[firstMatrix.length][secondMatrix[0].length];

        for (int finalRow = 0; finalRow < firstMatrix.length; finalRow++) {
            for (int finalCol = 0; finalCol < secondMatrix[0].length; finalCol++) {
                double dotProduct = 0.0d;
                for (int pointer = 0; pointer < firstMatrix[0].length; pointer++) {
                    dotProduct += firstMatrix[finalRow][pointer] * secondMatrix[pointer][finalCol];
                }
                matricesMultiplied[finalRow][finalCol] = dotProduct;
            }
        }
        printMatrix(matricesMultiplied);
    }

    public static double[][] createMatrix(Scanner input, String orderName) {
        System.out.printf("Enter size of%s matrix: %n", orderName);
        int RowNum = input.nextInt();
        int ColNum = input.nextInt();

        double[][] matrix = new double[RowNum][ColNum];

        System.out.printf("Enter%s matrix: %n", orderName);
        for (int row = 0; row < RowNum; row++) {
            for (int col = 0; col < ColNum; col++) {
                matrix[row][col] = input.nextFloat();
            }
        }
        return matrix;
    }

    public static void printMatrix(double[][] matrix) {
        System.out.println("The result is:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }

}
