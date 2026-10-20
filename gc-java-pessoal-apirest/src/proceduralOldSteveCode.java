public class proceduralOldSteveCode {
    public static String fabricarPicareta(String material, int durabilidade, int forca) {
        System.out.println("\n=== FABRICANDO PICARETA ===");
        System.out.println("Material: " + material);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Força: " + forca);

        if (durabilidade <= 0 || forca <= 0) {
            System.out.println(" Picareta inválida!");
            return null;
        }

        System.out.println("Picareta de " + material + " fabricada com sucesso!");
        return "Picareta de " + material + " (D: " + durabilidade + ", F: " + forca + ")";
    }

    public static int minerar(String material, int durabilidade, int forca, int blocos) {
        System.out.println("\n=== MINERANDO COM PICARETA DE " + material.toUpperCase() + " ===");
        System.out.println("Durabilidade atual: " + durabilidade);
        System.out.println("Força: " + forca);
        System.out.println("Blocos a minerar: " + blocos);

        if (durabilidade <= 0) {
            System.out.println("Picareta quebrada!");
            return 0;
        }

        int blocosMinerados = 0;
        int durabilidadeRestante = durabilidade;

        for (int i = 0; i < blocos; i++) {
            if (durabilidadeRestante <= 0) {
                System.out.println(" Picareta quebrou no bloco " + (i + 1));
                break;
            }
            durabilidadeRestante--;
            blocosMinerados++;
        }

        System.out.println("\nMinerados " + blocosMinerados + " blocos");
        System.out.println("Durabilidade restante: " + durabilidadeRestante);

        if (durabilidadeRestante <= 0) {
            System.out.println(" A picareta de " + material + " quebrou completamente!");
        }

        return durabilidadeRestante;
    }

    public static int repararPicareta(String material, int durabilidadeAtual, int quantidade) {
        System.out.println("\n=== REPARANDO PICARETA DE " + material.toUpperCase() + " ===");
        System.out.println("Durabilidade atual: " + durabilidadeAtual);
        System.out.println("Quantidade a reparar: " + quantidade);

        int durabilidadeNova = durabilidadeAtual + quantidade;
        int limiteMaximo = 0;

        switch (material.toLowerCase()) {
            case "madeira": limiteMaximo = 50; break;
            case "pedra": limiteMaximo = 70; break;
            case "ferro": limiteMaximo = 100; break;
            case "ouro": limiteMaximo = 30; break;
            default: limiteMaximo = 60;
        }

        if (durabilidadeNova > limiteMaximo) {
            System.out.println(" Reparo limitado a " + limiteMaximo);
            durabilidadeNova = limiteMaximo;
        }

        System.out.println(" Durabilidade nova: " + durabilidadeNova);
        return durabilidadeNova;
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║   VELHO STEVE - FÁBRICA DE PICARETAS MANUAL         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        String p1Mat = "madeira";
        int p1Dur = 50;
        int p1For = 5;
        String p1 = fabricarPicareta(p1Mat, p1Dur, p1For);

        String p2Mat = "pedra";
        int p2Dur = 70;
        int p2For = 8;
        String p2 = fabricarPicareta(p2Mat, p2Dur, p2For);

        String p3Mat = "ferro";
        int p3Dur = 100;
        int p3For = 12;
        String p3 = fabricarPicareta(p3Mat, p3Dur, p3For);

        System.out.println("\nPicaretas fabricadas:");
        System.out.println("  • " + p1);
        System.out.println("  • " + p2);
        System.out.println("  • " + p3);

        System.out.println("\n  Usando picaretas para minerar...");
        p3Dur = minerar(p3Mat, p3Dur, p3For, 30);
        p3Dur = minerar(p3Mat, p3Dur, p3For, 20);

        System.out.println("\n Criando picareta de ouro...");
        String p4Mat = "ouro";
        int p4Dur = 30;
        int p4For = 15;
        String p4 = fabricarPicareta(p4Mat, p4Dur, p4For);

        System.out.println("\n Reparando a picareta de ferro...");
        p3Dur = repararPicareta("ferro", p3Dur, 50);

        System.out.println("\nEstado final das picaretas:");
        System.out.println("  • " + p1);
        System.out.println("  • " + p2);
        System.out.println("  • " + p3 + " (durabilidade: " + p3Dur + ")");
        System.out.println("  • " + p4);

    }
}
