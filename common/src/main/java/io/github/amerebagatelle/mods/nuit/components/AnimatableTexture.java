package io.github.amerebagatelle.mods.nuit.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.amerebagatelle.mods.nuit.util.CodecUtils;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

public class AnimatableTexture {
    public static final Codec<AnimatableTexture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Texture.CODEC.fieldOf("texture").forGetter(AnimatableTexture::getTexture),
            UVRange.CODEC.optionalFieldOf("uvRange", new UVRange(0, 0, 1, 1)).forGetter(AnimatableTexture::getUvRange),
            CodecUtils.getClampedInteger(1, Integer.MAX_VALUE).optionalFieldOf("gridColumns", 1).forGetter(AnimatableTexture::getGridColumns),
            CodecUtils.getClampedInteger(1, Integer.MAX_VALUE).optionalFieldOf("gridRows", 1).forGetter(AnimatableTexture::getGridRows),
            CodecUtils.getClampedLong(1, Integer.MAX_VALUE).optionalFieldOf("duration", 24000L).forGetter(AnimatableTexture::getDuration),
            Codec.BOOL.optionalFieldOf("interpolate", true).forGetter(AnimatableTexture::isInterpolate),
            Codec.unboundedMap(Codec.STRING, Codec.LONG).optionalFieldOf("frameDuration", new HashMap<>()).forGetter(AnimatableTexture::getFrameDuration)
    ).apply(instance, AnimatableTexture::new));

    private final Texture texture;
    private final UVRange uvRange;
    private final int gridRows;
    private final int gridColumns;
    private final long duration;
    private final boolean interpolate;
    private final Map<String, Long> frameDuration;

    private UVRange currentFrame;
    private int index;
    private long nextTime;

    public AnimatableTexture(Texture texture, UVRange uvRange, int gridColumns, int gridRows, long duration, boolean interpolate, Map<String, Long> frameDuration) {
        this.texture = texture;
        this.uvRange = uvRange;
        this.gridColumns = gridColumns;
        this.gridRows = gridRows;
        this.duration = duration;
        this.interpolate = interpolate;
        this.frameDuration = frameDuration;
    }

    public Texture getTexture() {
        return texture;
    }

    public UVRange getUvRange() {
        return uvRange;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public int getGridRows() {
        return gridRows;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isInterpolate() {
        return interpolate;
    }

    public Map<String, Long> getFrameDuration() {
        return frameDuration;
    }

    public void tick() {
        if (this.nextTime <= Util.getEpochMillis() && !Minecraft.getInstance().isPaused()) {
            // Current Frame
            this.index = (this.index + 1) % (this.gridRows * this.gridColumns);
            this.currentFrame = this.calculateNextFrameUVRange(this.index);
            this.nextTime = Util.getEpochMillis() + this.frameDuration.getOrDefault(String.valueOf(this.index + 1), this.duration);
        }
    }

    public UVRange getCurrentFrame() {
        return currentFrame;
    }

    private UVRange calculateNextFrameUVRange(int nextFrameIndex) {
        float frameWidth = 1.0F / this.gridColumns;
        float frameHeight = 1.0F / this.gridRows;
        float minU = (float) (nextFrameIndex / this.gridRows) * frameWidth;
        float maxU = minU + frameWidth;
        float minV = (float) (nextFrameIndex % this.gridRows) * frameHeight;
        float maxV = minV + frameHeight;
        return new UVRange(minU, minV, maxU, maxV);
    }
}
