
/**
 * Assignment: Sorting Countries
 * Authors: Vlad Surdu & Sean Yang
 * Teacher: Ms Krasteva
 * Course: ICS4UO
 * Date: March 25, 2022
*/

package Sorting_Countries;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * <h2>Course Info:</h2>
 * ICS4U0 with Krasteva, V.
 * 
 * @author Vlad Surdu & Sean Yang
 */
public class SortingCountries {

    /** Stores the contries whose names have more than 1 word */
    ArrayList<String> countryExceptionsList;

    /** Stores all country names */
    ArrayList<String> countries;

    /** Stores all country capital names */
    ArrayList<String> capitals;

    /** Stores the populations of the country capitals */
    ArrayList<Integer> capitalPopulations;

    /** Stores the populations of the entire countries */
    ArrayList<Integer> countryPopulations;

    /**
     * Markbook Constructor
     * 
     * @throws IOException
     * 
     *                     <p>
     *                     Instantiates parallel ArrayLists with file contents
     *                     </p>
     */
    public SortingCountries() throws IOException {
        countries = new ArrayList<String>();
        capitals = new ArrayList<String>();
        capitalPopulations = new ArrayList<Integer>();
        countryPopulations = new ArrayList<Integer>();

        Scanner countryExceptionsFile = new Scanner(new File("CountryExceptions.txt"));
        countryExceptionsList = new ArrayList<String>();

        while (countryExceptionsFile.hasNext()) {
            countryExceptionsList.add(countryExceptionsFile.nextLine());
        }
        countryExceptionsFile.close();

        Scanner rawDataFile = new Scanner(new File("RawData.txt"));
        while (rawDataFile.hasNext()) {
            String[] parsedData = parseData(rawDataFile.nextLine());

            countries.add(parsedData[0]);
            capitals.add(parsedData[1]);
            capitalPopulations.add(Integer.parseInt(parsedData[2]));
            countryPopulations.add(Integer.parseInt(parsedData[3]));
        }
        rawDataFile.close();
    }

    /**
     * parseData()
     * 
     * <p>
     * Parses raw data of a country
     * </p>
     * 
     * @param data (Raw data String containing the information of a country)
     * @return parsedData in form of array
     */
    private String[] parseData(String data) {
        String[] parseArr = new String[4];
        if (data.indexOf("Vatican City") == -1 && data.indexOf("Western Sahara") == -1) {
            int countryEndIndex = -1;
            for (String exceptionCountry : countryExceptionsList) {
                int index = data.indexOf(exceptionCountry);

                if (index > -1) {
                    countryEndIndex = index + exceptionCountry.length();
                    parseArr[0] = exceptionCountry;

                }
            }

            String[] splitData;
            if (countryEndIndex != -1) {
                splitData = data.substring(countryEndIndex).split("\\s+");
            } else {
                splitData = data.split("\\s+");

                parseArr[0] = splitData[0];
                splitData[0] = "";
            }
            parseArr[1] = "";

            parseArr[3] = splitData[splitData.length - 1].replace(",", "");
            splitData[splitData.length - 1] = "";
            parseArr[2] = splitData[splitData.length - 2].replace(",", "");
            splitData[splitData.length - 2] = "";

            for (String word : splitData) {
                if (!word.equals("")) {
                    parseArr[1] += word + " ";
                }
            }
            parseArr[1] = parseArr[1].trim();

            return parseArr;
        }
        if (data.indexOf("Western Sahara") == -1) {
            String[] splitData = data.split("\\s+");
            parseArr[0] = splitData[0] + " " + splitData[1];
            parseArr[1] = splitData[0] + " " + splitData[1];
            parseArr[2] = splitData[2].replace(",", "");
            parseArr[3] = splitData[2].replace(",", "");
            return parseArr;
        }
        String[] splitData = data.split("\\s+");
        parseArr[0] = splitData[0] + " " + splitData[1];
        parseArr[1] = "(none)";
        parseArr[2] = "0";
        parseArr[3] = splitData[splitData.length - 1].replace(",", "");

        return parseArr;
    }

    /**
     * sortByCountryPopulation()
     * 
     * <p>
     * Sorts countries by populations
     * </p>
     */
    public void sortByCountryPopulation() {
        for (int s = 0; s < countryPopulations.size() - 1; s++) {
            int m = s;
            for (int f = s + 1; f < countryPopulations.size(); f++) {
                if (countryPopulations.get(f) > countryPopulations.get(m)) {
                    m = f;
                }
            }

            if (m != s) {
                swapIndexes(m, s);
            }
        }
    }

    /**
     * sortByCountryPopulation()
     * 
     * <p>
     * Sorts countries alphabetically
     * </p>
     */
    public void sortByCountryName() {
        for (int s = 0; s < countries.size() - 1; s++) {
            int m = s;
            for (int f = s + 1; f < countryPopulations.size(); f++) {
                if (countries.get(f).compareTo(countries.get(m)) < 0) {
                    m = f;
                }
            }

            if (m != s) {
                swapIndexes(m, s);
            }
        }
    }

    /**
     * swapIndexes()
     * 
     * <p>
     * Aids in the swapping of elements in ArrayLists. Needed during sorting
     * </p>
     */
    private void swapIndexes(int i, int j) {
        String temp = countries.get(i);
        countries.set(i, countries.get(j));
        countries.set(j, temp);

        temp = capitals.get(i);
        capitals.set(i, capitals.get(j));
        capitals.set(j, temp);

        int tempInt = capitalPopulations.get(i);
        capitalPopulations.set(i, capitalPopulations.get(j));
        capitalPopulations.set(j, tempInt);

        tempInt = countryPopulations.get(i);
        countryPopulations.set(i, countryPopulations.get(j));
        countryPopulations.set(j, tempInt);
    }

    /**
     * toString()
     * 
     * <p>
     * Outputs data stored in ArrayLists
     * </p>
     * 
     * @return str (String that contains the data from ArrayLists)
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < countries.size(); i++) {
            str += countries.get(i) + " ";
            str += countryPopulations.get(i) + " ";
            str += capitals.get(i) + " ";
            str += capitalPopulations.get(i) + "\n";
        }
        return str;
    }

    /**
     * outputDataToFile()
     * 
     * <p>
     * ouputs data to specific file
     * </p>
     * 
     * @param path (path of file)
     * @throws IOException
     */
    public void outputDataToFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        for (int i = 0; i < countries.size(); i++) {

            String spaces = "";

            int j = 40 - countries.get(i).length();
            while (j > 0) {
                spaces += " ";
                j--;
            }

            writer.write(countries.get(i) + spaces + countryPopulations.get(i));
            writer.write("\n");
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        SortingCountries sC = new SortingCountries();
        System.out.println(sC.toString());
        sC.sortByCountryPopulation();
        sC.outputDataToFile("sortedByPopulation.txt");
        sC.sortByCountryName();
        sC.outputDataToFile("sortedByCountry.txt");
    }
}