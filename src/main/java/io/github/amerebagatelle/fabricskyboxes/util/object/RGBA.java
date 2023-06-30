package io.github.amerebagatelle.fabricskyboxes.util.object;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.amerebagatelle.fabricskyboxes.util.Utils;
import net.minecraft.util.math.MathHelper;

public class RGBA {
    public static final RGBA DEFAULT = new RGBA(.0F, .0F, .0F, .0F);
    public static final Codec<RGBA> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Utils.getClampedFloat(0.0F, 1.0F).fieldOf("red").forGetter(RGBA::getRed),
            Utils.getClampedFloat(0.0F, 1.0F).fieldOf("green").forGetter(RGBA::getGreen),
            Utils.getClampedFloat(0.0F, 1.0F).fieldOf("blue").forGetter(RGBA::getBlue),
            Utils.getClampedFloat(0.0F, 1.0F).optionalFieldOf("alpha", 1.0F).forGetter(RGBA::getAlpha)
    ).apply(instance, RGBA::new));
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public RGBA(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public RGBA(float red, float green, float blue) {
        this(red, green, blue, 1);
    }

    public float getRed() {
        return this.red;
    }

    public float getBlue() {
        return this.blue;
    }

    public float getGreen() {
        return this.green;
    }

    public float getAlpha() {
        return this.alpha;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RGBA rgba) {
            return this.red == rgba.red && this.green == rgba.green && this.blue == rgba.blue && this.alpha == rgba.alpha;
        }
        return super.equals(obj);
    }
}
