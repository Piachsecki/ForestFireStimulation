import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Random;

public class BurningForestSimulation {
    public static int treesCountOnMap;
    public static int treesBeingInfected;

    private final String[][] map;
    private final int size;
    private final Double forestation;

    public BurningForestSimulation(int size, Double forestation) {
        this.size = size;
        this.forestation = BigDecimal.valueOf(forestation).setScale(3, RoundingMode.HALF_UP).doubleValue();
        this.map = new String[size][size];
    }

    public static void main(String[] args) {
        try {


            File outputFile = new File("myOutputFile.txt");
            BufferedWriter writer = new BufferedWriter(
                    new BufferedWriter(
                            new FileWriter(outputFile)));
            try {


                for (
                        double number = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP).doubleValue();
                        number <= 1.05;
                        number += 0.05) {
                    BurningForestSimulation burningForestSimulation = new BurningForestSimulation(10, number);

                    extracted(burningForestSimulation, writer, outputFile);
                }
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        burningForestSimulation.makeSimulation();
//        burningForestSimulation.print_map();


    }

    private static void extracted(BurningForestSimulation burningForestSimulation,
                                  BufferedWriter writer,
                                  File outputFile) throws IOException {


        burningForestSimulation.map_initialization();
        burningForestSimulation.print_map();

        for (int i = 0; i < 10; i++) {
            burningForestSimulation.makeSimulation();
            writer.write("For the forestation = " + burningForestSimulation.forestation + " " + (i + 1) + "try ");
            writer.write("TreesCountOnMap: " + treesCountOnMap);
            writer.write(" treesBeingInfected: " + treesBeingInfected);
            if(i == 9 && burningForestSimulation.forestation != 0){
                writer.newLine();
                double d = (double)treesBeingInfected/treesCountOnMap;
                writer.write("Percentage of trees that were burnt during the process: " + (d*100) + "%");
            }
            writer.newLine();
            writer.flush();
        }
        System.out.println();
        burningForestSimulation.print_map();
        System.out.println();


    }


    public void makeSimulation() {
        int indexOfRowCurrentlyBurning = 0;
        this.fire_initialization();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ("B".equals(map[i][j])) {
                    indexOfRowCurrentlyBurning = i;
//                    System.out.println("indexOfRowCurrentlyBurning = " + i);
                    break;
                }
            }
        }


        for (int i = indexOfRowCurrentlyBurning + 1; i < indexOfRowCurrentlyBurning + 2; i++) {
            if (indexOfRowCurrentlyBurning == size - 1) {
                return;
            }
            for (int j = 0; j < size; j++) {
                if (j == 0) {
                    if ("T".equals(map[i][j])) {
                        if ("B".equals(map[i - 1][j]) || "B".equals(map[i - 1][j + 1]) || "B".equals(map[i][j + 1])) {
                            map[i][j] = "B";
                            treesBeingInfected++;
                        }
                    }
                } else if (j == size - 1) {
                    if ("T".equals(map[i][j])) {
                        if ("B".equals(map[i - 1][j]) || "B".equals(map[i - 1][j - 1]) || "B".equals(map[i][j - 1])) {
                            map[i][j] = "B";
                            treesBeingInfected++;
                        }
                    }
                } else if ("T".equals(map[i][j])) {
                    if ("B".equals(map[i - 1][j - 1]) ||
                            "B".equals(map[i - 1][j]) ||
                            "B".equals(map[i - 1][j + 1]) ||
                            "B".equals(map[i][j + 1]) ||
                            "B".equals(map[i][j - 1])) {

                        map[i][j] = "B";
                        treesBeingInfected++;
                    }


                }


            }
//            System.out.println("Amount of trees on the beginning: " + treesCountOnMap);
//            System.out.println("Amount of infected trees after this turn: " + treesBeingInfected);
        }


    }

    public void map_initialization() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Random treeGenerator = new Random();
                double treeObject = treeGenerator.nextDouble();
                if (treeObject <= forestation) {
                    map[i][j] = "T";
                    treesCountOnMap++;
                } else {
                    map[i][j] = "X";
                }
            }
        }

    }

    public void fire_initialization() {
        for (int i = 0; i < size; i++) {
            String tempValue = "T";
            if (Objects.equals(map[0][i], "T")) {
                treesBeingInfected++;
                map[0][i] = "B";
            }
        }

    }

    public void print_map() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%s ", map[i][j]);
            }
            System.out.println();
        }

    }

}