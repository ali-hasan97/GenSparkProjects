
public class HangmanArt {
    public enum head {
        ONE (String.format("     |%n")),
        TWO (String.format(" O   |%n"));
        private final String art;
        head(String art) {
            this.art = art;
        }
    }

    public enum body {
        ONE (String.format("     |%n")),
        TWO (String.format("---  |%n")),
        THREE (String.format(" |   |%n")),
        FOUR (String.format("/    |%n")),
        FIVE (String.format("/ \\  |%n"));
        private final String art;
        body(String art) {
            this.art = art;
        }
    }
    public static String hangmanArt(int wrongCount){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(" +---+%n"));
        switch (wrongCount) {
            case 0:
                sb.append(head.ONE.art);
                sb.append(body.ONE.art);
                sb.append(body.ONE.art);
                sb.append(body.ONE.art);
                break;
            case 1:
                sb.append(head.TWO.art);
                sb.append(body.ONE.art);
                sb.append(body.ONE.art);
                sb.append(body.ONE.art);
                break;
            case 2:
                sb.append(head.TWO.art);
                sb.append(body.TWO.art);
                sb.append(body.ONE.art);
                sb.append(body.ONE.art);
                break;
            case 3:
                sb.append(head.TWO.art);
                sb.append(body.TWO.art);
                sb.append(body.THREE.art);
                sb.append(body.ONE.art);
                break;
            case 4:
                sb.append(head.TWO.art);
                sb.append(body.TWO.art);
                sb.append(body.THREE.art);
                sb.append(body.FOUR.art);
                break;
            case 5:
                sb.append(head.TWO.art);
                sb.append(body.TWO.art);
                sb.append(body.THREE.art);
                sb.append(body.FIVE.art);
                break;
        }
        sb.append(String.format("    ===%n"));
        return sb.toString();
    }
}
