package utility;

public class StringUtility {
    //to replace the .repeat() method as it is not supported in java 8
    public static String repeatString(String str, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(str);
        }
        return result.toString();
    }

    //format table with dividers
    public static String formatTableWithDividers(String[] headers, Object[][] rows) {
        StringBuilder sb = new StringBuilder();
        int[] colWidths = new int[headers.length];
        //calculate column widths
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
            for (Object[] row : rows) {
                if (row[i] != null && row[i].toString().length() > colWidths[i]) {
                    colWidths[i] = row[i].toString().length();
                }
            }
        }
        //header
        sb.append("|");
        for (int i = 0; i < headers.length; i++) {
            sb.append(String.format(" %s %s|", headers[i], repeatString(" ", colWidths[i] - headers[i].length())));
        }
        sb.append("\n");
        //separator
        sb.append("|");
        for (int i = 0; i < headers.length; i++) {
            sb.append(repeatString("-", colWidths[i] + 2)).append("|");
        }
        sb.append("\n");
        //rows
        for (Object[] row : rows) {
            sb.append("|");
            for (int i = 0; i < headers.length; i++) {
                String cell = row[i] != null ? row[i].toString() : "";
                sb.append(String.format(" %s %s|", cell, repeatString(" ", colWidths[i] - cell.length())));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //green bar chart
    public static String greenBarChart(int value, int max, int width) {
        final String GREEN = "\u001B[42m";
        final String RESET = "\u001B[0m";
        int barLength = max > 0 ? (int) Math.round((double) value / max * width) : 0;
        return GREEN + repeatString(" ", barLength) + RESET + repeatString(" ", width - barLength);
    }

    //get current date and time
    public static String getCurrentDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    // Colored bar chart utility with perfect alignment
    public static void printColoredBarChart(String title, String[] labels, int[] values, String barColor) {
        System.out.println(title + ":");
        System.out.println("   ^");

        String reset = "\u001B[0m"; // Reset color
        int maxValue = 0;
        for (int value : values) {
            if (value > maxValue) maxValue = value;
        }

        // Print bars from top to bottom with single color using background
        for (int level = maxValue; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < values.length; i++) {
                if (values[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        // Draw axis line (6 chars per column)
        System.out.print("   +");
        for (int i = 0; i < values.length; i++) {
            System.out.print("------");
        }
        System.out.println(">");

        // Draw labels (4-char labels with proper spacing)
        System.out.print("    ");
        for (String label : labels) {
            String shortLabel = label.length() > 4 ? label.substring(0, 4) : label;
            System.out.printf("%-4s  ", shortLabel); // 4-char label with 2 spaces
        }
        System.out.println();
        System.out.println();
    }

    // Default color schemes
    public static String[] getDefaultColors() {
        return new String[]{
            "\u001B[41m", // Red background
            "\u001B[42m", // Green background
            "\u001B[43m", // Yellow background
            "\u001B[44m", // Blue background
            "\u001B[45m", // Magenta background
            "\u001B[46m", // Cyan background
            "\u001B[47m\u001B[30m" // White background with black text
        };
    }

    public static String[] getStatusColors() {
        return new String[]{
            "\u001B[41m", // Red for negative/inactive
            "\u001B[43m", // Yellow for pending/ongoing
            "\u001B[42m", // Green for positive/completed
            "\u001B[44m"  // Blue for neutral
        };
    }

    //format table with no dividers
    public static String formatTableNoDividers(String title, String[] headers, Object[][] rows) {
        StringBuilder sb = new StringBuilder();
        int[] colWidths = new int[headers.length];
        //calculate column widths
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
            for (Object[] row : rows) {
                if (row[i] != null && row[i].toString().length() > colWidths[i]) {
                    colWidths[i] = row[i].toString().length();
                }
            }
        }
        int totalWidth = 0;
        for (int w : colWidths) totalWidth += w + 2; // 2 spaces padding
        totalWidth += headers.length - 1; // spaces between columns
        //top line
        sb.append(repeatString("-", totalWidth)).append("\n");
        //title (centered)
        if (title != null && !title.isEmpty()) {
            int pad = (totalWidth - title.length()) / 2;
            sb.append(repeatString(" ", Math.max(0, pad))).append(title).append("\n");
            sb.append(repeatString("-", totalWidth)).append("\n");
        }
        //header
        for (int i = 0; i < headers.length; i++) {
            sb.append(String.format("%-" + (colWidths[i] + 2) + "s", headers[i]));
            if (i < headers.length - 1) sb.append("");
        }
        sb.append("\n");
        sb.append(repeatString("-", totalWidth)).append("\n");
        //rows
        for (Object[] row : rows) {
            for (int i = 0; i < headers.length; i++) {
                sb.append(String.format("%-" + (colWidths[i] + 2) + "s", row[i] != null ? row[i].toString() : ""));
                if (i < headers.length - 1) sb.append("");
            }
            sb.append("\n");
        }
        sb.append(repeatString("-", totalWidth)).append("\n");
        return sb.toString();
    }
} 