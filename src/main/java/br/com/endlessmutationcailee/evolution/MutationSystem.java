package br.com.endlessmutationcailee.evolution;

/** Fórmulas estáveis para progressão lógica sem teto de nível predefinido. */
public final class MutationSystem {
    private MutationSystem() {}

    public static double scaleFor(double muscleMass, double maxVisualScale) {
        if (!Double.isFinite(muscleMass) || muscleMass < 0) muscleMass = 0;
        double scale = 1.0D + Math.log1p(muscleMass) * 0.22D;
        return Math.max(1.0D, Math.min(maxVisualScale, scale));
    }

    public static int levelFor(double energy) {
        if (!Double.isFinite(energy) || energy <= 0) return 0;
        return (int)Math.min(Integer.MAX_VALUE, Math.floor(Math.log1p(energy) / Math.log(2.0D)));
    }
}
