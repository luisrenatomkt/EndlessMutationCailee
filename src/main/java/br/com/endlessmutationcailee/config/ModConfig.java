package br.com.endlessmutationcailee.config;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public final class ModConfig {
    private static Configuration config;
    public static int searchRadius = 24;
    public static int searchInterval = 40;
    public static double initialSpeed = 0.28D;
    public static double initialAttack = 4.0D;
    public static double growthPerPrey = 1.0D;
    public static double maxVisualScale = 6.0D;

    private ModConfig() {}

    public static void init(File file) {
        config = new Configuration(file);
        sync();
    }

    public static void sync() {
        searchRadius = config.getInt("searchRadius", "hunting", 24, 4, 128, "Raio de busca por animais.");
        searchInterval = config.getInt("searchIntervalTicks", "hunting", 40, 5, 400, "Intervalo entre buscas.");
        initialSpeed = config.get("entity", "initialSpeed", 0.28D, "Velocidade inicial.").getDouble(0.28D);
        initialAttack = config.get("entity", "initialAttack", 4.0D, "Ataque inicial.").getDouble(4.0D);
        growthPerPrey = config.get("evolution", "growthPerPrey", 1.0D, "Energia base por presa consumida.").getDouble(1.0D);
        maxVisualScale = config.get("evolution", "maxVisualScale", 6.0D, "Limite visual de segurança; não limita a evolução lógica.").getDouble(6.0D);
        if (searchRadius < 4) searchRadius = 4;
        if (searchInterval < 5) searchInterval = 5;
        if (!Double.isFinite(initialSpeed) || initialSpeed <= 0) initialSpeed = 0.28D;
        if (!Double.isFinite(initialAttack) || initialAttack < 0) initialAttack = 4.0D;
        if (!Double.isFinite(growthPerPrey) || growthPerPrey <= 0) growthPerPrey = 1.0D;
        if (!Double.isFinite(maxVisualScale) || maxVisualScale < 1) maxVisualScale = 6.0D;
        if (config.hasChanged()) config.save();
    }
}
