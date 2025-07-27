public class Main {
    public static void main(String[] args) {
        GenPassworder gp = new GenPassworder();
        gp.setTotal_length(14);
        for (int i = 0; i < 50; i++) {
            System.out.println(gp.start());
        }
    }
}
