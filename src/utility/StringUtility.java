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

    /**
     * Formats a 2D array of objects as a table with headers and vertical dividers.
     */
    public static String formatTableWithDividers(String[] headers, Object[][] rows) {
        StringBuilder sb = new StringBuilder();
        int[] colWidths = new int[headers.length];
        // Calculate column widths
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
            for (Object[] row : rows) {
                if (row[i] != null && row[i].toString().length() > colWidths[i]) {
                    colWidths[i] = row[i].toString().length();
                }
            }
        }
        // Header
        sb.append("|");
        for (int i = 0; i < headers.length; i++) {
            sb.append(String.format(" %s %s|", headers[i], repeatString(" ", colWidths[i] - headers[i].length())));
        }
        sb.append("\n");
        // Separator
        sb.append("|");
        for (int i = 0; i < headers.length; i++) {
            sb.append(repeatString("-", colWidths[i] + 2)).append("|");
        }
        sb.append("\n");
        // Rows
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

    /**
     * Returns a green horizontal bar for a value (for bar chart display in console) with fixed length.
     */
    public static String greenBarChart(int value, int max, int width) {
        final String GREEN = "\u001B[42m";
        final String RESET = "\u001B[0m";
        int barLength = max > 0 ? (int) Math.round((double) value / max * width) : 0;
        return GREEN + repeatString(" ", barLength) + RESET + repeatString(" ", width - barLength);
    }

    /**
     * Returns the current date and time as a formatted string.
     */
    public static String getCurrentDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    /**
     * Formats a 2D array of objects as a table with headers, no vertical dividers, and horizontal lines.
     */
    public static String formatTableNoDividers(String title, String[] headers, Object[][] rows) {
        StringBuilder sb = new StringBuilder();
        int[] colWidths = new int[headers.length];
        // Calculate column widths
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
        // Top line
        sb.append(repeatString("-", totalWidth)).append("\n");
        // Title (centered)
        if (title != null && !title.isEmpty()) {
            int pad = (totalWidth - title.length()) / 2;
            sb.append(repeatString(" ", Math.max(0, pad))).append(title).append("\n");
            sb.append(repeatString("-", totalWidth)).append("\n");
        }
        // Header
        for (int i = 0; i < headers.length; i++) {
            sb.append(String.format("%-" + (colWidths[i] + 2) + "s", headers[i]));
            if (i < headers.length - 1) sb.append("");
        }
        sb.append("\n");
        sb.append(repeatString("-", totalWidth)).append("\n");
        // Rows
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