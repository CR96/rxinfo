package util;

/**
 * Created by corey on 9/30/17.
 */

public class NdcUtils {
    // 10-digit NDCs can appear in one of three formats:
    // 4-4-2
    // 5-3-2
    // 5-4-1

    // UPCs determined from barcodes do not contain any dashes.
    // The FDA database only contains properly formatted NDCs, so
    // so the database must be queried for each of the three possible
    // constructions.

    public static String[] getPossibleNdcs(String baseNdc) {

        String fourFourTwo = baseNdc.substring(0, 4) + "-" +
                baseNdc.substring(4, 8) + "-" +
                baseNdc.substring(8, 10);

        String fiveThreeTwo = baseNdc.substring(0, 5) + "-" +
                baseNdc.substring(5, 8) + "-" +
                baseNdc.substring(8, 10);

        String fiveFourOne = baseNdc.substring(0, 5) + "-" +
                baseNdc.substring(5, 9) + "-" +
                baseNdc.substring(9, 10);

        return new String[]{fourFourTwo, fiveThreeTwo, fiveFourOne};
    }

    // Remove the first and last digits from a UPC read from a barcode, if needed.
    public static String upcToNdc(String upc) {
        try {
            if (upc.length() > 10) { // Contains system character and check digit
                return upc.substring(1, upc.length() - 1)
                        .substring(0, upc.length() - 2); // Remove first and last digit
            } else {
                return upc;
            }
        } catch (IndexOutOfBoundsException e) {
            // Something's weird with this barcode, just leave it as is
            return upc;
        }
    }
}
