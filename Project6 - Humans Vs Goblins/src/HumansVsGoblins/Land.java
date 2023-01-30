public class Land {
    private int length;
    private int height;

    public void setLength(int length) {this.length = length;}
    public void setHeight(int height) {this.height = height;}

    public String[][] generateLand() {
        String[][] land = new String[height][length];
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                row.append("|__");
            }
            row.append("\n");
        }
        System.out.println(row);
        return land;
    }
}
