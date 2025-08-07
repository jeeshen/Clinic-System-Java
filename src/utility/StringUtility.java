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